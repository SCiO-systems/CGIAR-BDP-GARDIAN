/*
 * Copyright (C) 2019 SCiO
 *  This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 */

package com.scio.quantum.modelvalidator.process;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mongodb.DBObject;
import com.scio.quantum.modelvalidator.exceptions.InvalidDoiException;
import com.scio.quantum.modelvalidator.exceptions.InvalidModelException;
import com.scio.quantum.modelvalidator.exceptions.InvalidYearFormatException;
import com.scio.quantum.modelvalidator.models.dataset.DatasetModel;
import com.scio.quantum.modelvalidator.models.publication.ExtractedMetadata;
import com.scio.quantum.modelvalidator.models.publication.FAIR;
import com.scio.quantum.modelvalidator.models.publication.PublicationModel;
import com.scio.quantum.modelvalidator.utilities.RegexCollection;
import org.apache.camel.Exchange;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ModelValidationBean{


    public ModelValidationBean() {
    }

    public String testBean(Object inputJson){

        PublicationModel myPojo = new PublicationModel();

        Gson gson = new Gson();
        //gson.toJson(myPojo);


        /*JsonParser parser = new JsonParser();
        JsonObject model = parser.parse(inputJson).getAsJsonObject();

        model.addProperty("hasBeenTransferred","false");
        model.addProperty("hasBeenProcessed","false");*/

        //System.out.println(gson.toJson(inputJson));

        return gson.toJson(inputJson);

    }

    public Document checkDatasetModel(Exchange exchange) throws InvalidYearFormatException, InvalidDoiException {

        Object json = exchange.getIn().getBody();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String harvested = Long.toString(timestamp.getTime());
        if(json instanceof DatasetModel){

            DatasetModel dataset = (DatasetModel) json;
            datasetModel(dataset);
            dataset.getDatasetMetadata().setHasBeenTransferred("false");
            dataset.getDatasetMetadata().setStatus("new");
            dataset.getDatasetMetadata().setHasBeenHarvested(harvested);
            dataset.getDatasetMetadata().setQuantumId("");
            dataset.getDatasetMetadata().setDoiHash("");
            dataset.getDatasetMetadata().setTitleHash("");
            List<ExtractedMetadata> extractedMetadata = new ArrayList<>();
            dataset.getDatasetMetadata().setExtractedMetadata(extractedMetadata);
            FAIR fair = new FAIR();
            dataset.getDatasetMetadata().setFair(fair);
            Gson gson = new Gson();
            String jsonString = gson.toJson(dataset,DatasetModel.class);
            JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
            Document document = Document.parse(jsonObject.toString());

            return document;

        }
        return null;

    }


    public Document checkPublicationModel(Exchange exchange) throws InvalidModelException, JsonSyntaxException, InvalidYearFormatException, InvalidDoiException {

        Object json = exchange.getIn().getBody();
        //milliseconds
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String harvested = Long.toString(timestamp.getTime());
        if(json instanceof PublicationModel){

            PublicationModel publication = (PublicationModel) json;
            publicationModel(publication);
            publication.getPubMetadata().setHasBeenTransferred("false");
            publication.getPubMetadata().setStatus("new");
            publication.getPubMetadata().setHasBeenHarvested(harvested);
            publication.getPubMetadata().setQuantumId("");
            publication.getPubMetadata().setDoiHash("");
            publication.getPubMetadata().setTitleHash("");
            List<ExtractedMetadata> extractedMetadata = new ArrayList<>();
            publication.getPubMetadata().setExtractedMetadata(extractedMetadata);
            FAIR fair = new FAIR();
            publication.getPubMetadata().setFair(fair);

            Gson gson = new Gson();
            String jsonString = gson.toJson(publication,PublicationModel.class);

            JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
            Document document = Document.parse(jsonObject.toString());

            return document;

        }else{
            return null;
        }


    }

    private boolean publicationModel(PublicationModel publication) throws InvalidDoiException, InvalidYearFormatException {
        RegexCollection reco = new RegexCollection();
        boolean isValid = true;

        List<com.scio.quantum.modelvalidator.models.publication.DOI> dois = publication.getPubMetadata().getDoi();
        Iterator doisIt = dois.iterator();

        while(doisIt.hasNext()){
            com.scio.quantum.modelvalidator.models.publication.DOI doi = (com.scio.quantum.modelvalidator.models.publication.DOI) doisIt.next();

            if ((reco.checkDOI(doi.getLink()) == false)&&(!doi.getLink().isEmpty())){
                throw new InvalidDoiException("The publication titled "+publication.getPubMetadata().getTitle()+" has invalid DOI: "+doi );
            }
        }

        String issuedYear =  publication.getPubMetadata().getIssuedYear();

        if ((reco.checkYearFormat(issuedYear) == false)&&(!issuedYear.isEmpty())){
            throw new InvalidYearFormatException("The publication titled "+publication.getPubMetadata().getTitle()
                    +" has invalid issued year: "+issuedYear );
        }


        String pubYear = publication.getPubMetadata().getPubYear();

        if(pubYear != null){
            if (reco.checkYearFormat(pubYear) == false){
                throw new InvalidYearFormatException("The publication titled "+publication.getPubMetadata().getTitle()
                        +" has invalid publication year: "+pubYear );
            }
        }else{
            throw new InvalidYearFormatException("The publication titled "+publication.getPubMetadata().getTitle()
                    +" does not provide publication year: "+pubYear );
        }


        return isValid;

    }

    private boolean datasetModel(DatasetModel dataset) throws InvalidDoiException, InvalidYearFormatException {

        RegexCollection reco = new RegexCollection();
        boolean isValid = true;

        List<com.scio.quantum.modelvalidator.models.dataset.DOI> dois = dataset.getDatasetMetadata().getDoi();
        Iterator doisIt = dois.iterator();

        while(doisIt.hasNext()){
            com.scio.quantum.modelvalidator.models.dataset.DOI doi = (com.scio.quantum.modelvalidator.models.dataset.DOI) doisIt.next();

            if ((reco.checkDOI(doi.getLink()) == false)&&(!doi.getLink().isEmpty())){
                throw new InvalidDoiException("The publication titled "+dataset.getDatasetMetadata().getTitle()+" has invalid DOI: "+doi );
            }
        }

        String issuedYear =  dataset.getDatasetMetadata().getIssuedYear();

        if ((reco.checkYearFormat(issuedYear) == false)&&(!issuedYear.isEmpty())){
            throw new InvalidYearFormatException("The publication titled "+dataset.getDatasetMetadata().getTitle()
                    +" has invalid issued year: "+issuedYear );
        }


        String pubYear = dataset.getDatasetMetadata().getPubYear();

        if(pubYear != null){
            if (reco.checkYearFormat(pubYear) == false){
                throw new InvalidYearFormatException("The publication titled "+dataset.getDatasetMetadata().getTitle()
                        +" has invalid publication year: "+pubYear );
            }
        }else{
            throw new InvalidYearFormatException("The publication titled "+dataset.getDatasetMetadata().getTitle()
                    +" does not provide publication year: "+pubYear );
        }


        return isValid;

    }




}
