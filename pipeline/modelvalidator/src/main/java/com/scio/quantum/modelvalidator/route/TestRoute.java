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

package com.scio.quantum.modelvalidator.route;

import com.scio.quantum.modelvalidator.process.GoogleSheetListProcessor;
import com.scio.quantum.modelvalidator.process.IRRICollectionProcessor;
import org.apache.camel.builder.RouteBuilder;

public class TestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        System.out.println("!!!!");

        String clientId = "629954483252-o7mn1nc0hamld8p3l3c8qn1kcu60dqtv.apps.googleusercontent.com";
        String clientSecret = "A1HnRo6kWVR7KQthzXNYJnEq";
        String accessToken = "ya29.GlziBgWk0-C2k9EgJtq5meD_nLGRNkMGHoHW3rGGEeK3UI_2X6KXOIRJSoZjVyURryghpgPOAupYZo9LJ2Tjf-1g_tEFQb6Dzo0xOE-NJvMAl-Axz_xAp4m2UlkC7A";
        String refreshToken = "1/b4lRBIOk00QLGh0Eub7L0omwcCCd2KttoVQ94r0Cs6M";
        String spreadSheetID = "1ZxNbTo4F4nEMFENsRR5732ONkdzoULobHhLIUG7iRKE";
        String applicationName = "camel-google-sheets/1.0";


        from("google-sheets-stream://spreadsheets?accessToken="+
                accessToken+"&refreshToken="+refreshToken+
                "&clientId="+clientId+"&clientSecret="+clientSecret+
                "&spreadsheetId="+spreadSheetID+"&applicationName="+applicationName+
                "&includeGridData=true&maxResults=2&range=Sheet1!A1:Q4")
                .process(new GoogleSheetListProcessor())
                .split(body()).log("Splitted Message: ${in.body}")
                .process(new IRRICollectionProcessor())
                .to("mock:result");

        //from("google-sheets://spreadsheets?accessToken="+accessToken+"&refreshToken="+refreshToken)
          //      .log("BODY: ${in.body}");

    }
}
