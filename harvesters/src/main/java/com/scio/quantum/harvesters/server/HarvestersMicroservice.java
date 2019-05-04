package com.scio.quantum.harvesters.server;

import com.scio.quantum.harvesters.process.CKANBean;
import com.scio.quantum.harvesters.process.DataverseBean;
import com.scio.quantum.harvesters.route.datasets.CKANRouter;
import com.scio.quantum.harvesters.route.datasets.DataverseRouter;
import com.scio.quantum.harvesters.route.publications.IFPRIRouter;
import com.scio.quantum.harvesters.route.publications.IRRIRouter;
import org.apache.camel.main.Main;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HarvestersMicroservice extends Main {

    private static final Logger logger = LoggerFactory.getLogger(HarvestersMicroservice.class);

    public static void main(String[] args){

        try {
            HarvestersMicroserviceOptions hmo = new HarvestersMicroserviceOptions();
            CommandLineParser parser = new DefaultParser();

            CommandLine cmd = parser.parse( hmo.createOptions(), args);

            //String filePool = cmd.getOptionValue("filepool");
            //String resourceType = cmd.getOptionValue("resource");

            String resourceType = "dataset";
            String filePool = "C:\\Users\\SOTIRIS SON\\Desktop\\quantum_test_pool\\harvester";
            String harvester = "DATAVERSE";

            HarvestersMicroservice harvestersMicroservice = new HarvestersMicroservice();
            if(harvester.equalsIgnoreCase("IRRI")){
                harvestersMicroservice.addRouteBuilder(new IRRIRouter(filePool,resourceType));
            }else if(harvester.equalsIgnoreCase("IFPRI")){
                harvestersMicroservice.addRouteBuilder(new IFPRIRouter(filePool,resourceType));
            }else if(harvester.equalsIgnoreCase("DATAVERSE")){
                harvestersMicroservice.bind("dataversebean",new DataverseBean());
                harvestersMicroservice.addRouteBuilder(new DataverseRouter(filePool,resourceType,"AfricaRice".toLowerCase()));
            }else if(harvester.equalsIgnoreCase("CKAN")){
                harvestersMicroservice.bind("ckanbean",new CKANBean());
                harvestersMicroservice.addRouteBuilder(new CKANRouter(filePool));
            }

            logger.info("======   Harvester is running   =====");
            logger.info("File Pool: "+filePool);
            logger.info("Resource Type: "+resourceType);
            logger.info("Harvesting: "+harvester);
            harvestersMicroservice.run();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
