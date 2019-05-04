package com.scio.quantum.harvesters.process;

import com.google.gson.*;
import com.scio.quantum.harvesters.models.external.dataverse.resource.Dataverse;
import com.scio.quantum.harvesters.models.external.dataverse.resource.Field;
import com.scio.quantum.harvesters.models.external.dataverse.resource.Field_;
import com.scio.quantum.harvesters.models.external.dataverse.resource.Geospatial;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataverseDeserializer implements JsonDeserializer<Dataverse> {
    @Override
    public Dataverse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Dataverse dataverse = new Gson().fromJson(json.getAsJsonObject(), Dataverse.class);

        try {
            JsonArray fields = json.getAsJsonObject()
                    .get("datasetVersion").getAsJsonObject()
                    .get("metadataBlocks").getAsJsonObject()
                    .get("citation").getAsJsonObject()
                    .get("fields").getAsJsonArray();
            Iterator<JsonElement> fieldsIt = fields.iterator();
            List<Object> genericField = sanitizeDataverseFields(fieldsIt);
            dataverse.getDatasetVersion().getMetadataBlocks().getCitation().setFields(genericField);
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
            List<Object> genericField = new ArrayList<>();
            dataverse.getDatasetVersion().getMetadataBlocks().getCitation().setFields(genericField);
        } catch (Exception ex){
            ex.printStackTrace();
            List<Object> genericField = new ArrayList<>();
            dataverse.getDatasetVersion().getMetadataBlocks().getCitation().setFields(genericField);
        }

        try{
            JsonArray geofields = json.getAsJsonObject()
                    .get("datasetVersion").getAsJsonObject()
                    .get("metadataBlocks").getAsJsonObject()
                    .get("geospatial").getAsJsonObject()
                    .get("fields").getAsJsonArray();

            Iterator<JsonElement> geoFieldsIt = geofields.iterator();
            List<Object> geoField = sanitizeDataverseFields(geoFieldsIt);
            dataverse.getDatasetVersion().getMetadataBlocks().getGeospatial().setFields(geoField);
        } catch (IllegalArgumentException ie) {
            List<Object> geoField = new ArrayList<>();
            completeDataverseModel(dataverse, geoField);
        } catch (Exception ex){
            List<Object> geoField = new ArrayList<>();
            completeDataverseModel(dataverse, geoField);
        }

        return dataverse;
    }

    private void completeDataverseModel(Dataverse dataverse, List<Object> geoField) {
        if(dataverse.getDatasetVersion().getMetadataBlocks().getGeospatial() == null){
            Geospatial gs = new Geospatial();
            gs.setDisplayName("mock");
            gs.setFields(geoField);
            dataverse.getDatasetVersion().getMetadataBlocks().setGeospatial(gs);
        }else{
            dataverse.getDatasetVersion().getMetadataBlocks().getGeospatial().setFields(geoField);
        }
    }

    private List<Object> sanitizeDataverseFields(Iterator<JsonElement> fieldsIt) {
        List<Object> sanitizedField = new ArrayList<>();
        while(fieldsIt.hasNext()){
            JsonObject field = fieldsIt.next().getAsJsonObject();
            if(field.get("value").isJsonArray() == true){
                Field_ field_ = new Gson().fromJson(field,Field_.class);
                sanitizedField.add(field_);
            }else if(field.get("value").isJsonObject() == true){
               if(field.get("typeName").getAsString().equalsIgnoreCase("series")){
                   if(field.get("value").getAsJsonObject().get("seriesName") != null){
                       if(field.get("value").getAsJsonObject().get("seriesName").getAsJsonObject().get("value") != null) {
                           if (!field.get("value").getAsJsonObject().get("seriesName").getAsJsonObject().get("value").isJsonObject()) {
                               String seriesValue = field.get("value").getAsJsonObject().get("seriesName").getAsJsonObject().get("value").getAsString();
                               Field field_ = new Field();
                               field_.setValue(seriesValue);
                               field_.setTypeName("series");
                               field_.setMultiple(false);
                               field_.setTypeClass("primitive");
                               sanitizedField.add(field_);
                           }
                       }
                   }
               }
            }else{
                Field field_ = new Gson().fromJson(field,Field.class);
                sanitizedField.add(field_);
            }
        }
        return sanitizedField;
    }
}
