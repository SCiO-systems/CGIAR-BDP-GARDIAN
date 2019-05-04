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

package com.scio.quantum.modelvalidator.server;

import org.apache.commons.cli.Options;

public class ModelValidationMicroserviceOptions {

    public ModelValidationMicroserviceOptions() {

    }

    public Options createOptions(){

        Options options = new Options();

        options.addOption("kafka",true,"Kafka Broker");
        options.addOption("mongo",true,"Mongo Location");
        options.addOption("filepool",true,"Metadata Pool");
        options.addOption("resource",true,"Resource Type");

        return options;
    }


}
