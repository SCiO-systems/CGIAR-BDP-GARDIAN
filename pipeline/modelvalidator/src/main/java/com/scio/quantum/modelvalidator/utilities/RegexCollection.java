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

package com.scio.quantum.modelvalidator.utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCollection {

    public boolean checkDOI(String doi){
        String doiRegex = "10.\\d{4,9}/[-._;()/:A-Z0-9]+" +
                "|10.1002/[^\\s]+" +
                "|10.\\d{4}/\\d+-\\d+X?(\\d+)\\d+<[\\d\\w]+:[\\d\\w]*>\\d+.\\d+.\\w+;\\d" +
                "|10.1021/\\w\\w\\d++" +
                "|10.1207/[\\w\\d]+\\&\\d+_\\d+";

        Pattern pattern = Pattern.compile(doiRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(doi);

        if(matcher.find() == true){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkYearFormat(String year){
        String yearRegex = "19\\d{2}|20\\d{2}";

        Pattern pattern = Pattern.compile(yearRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(year);

        if(matcher.find() == true){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkURLFormat(String url){
        try {
            URL u = new URL(url);
            return true;
        }
        catch (MalformedURLException e) {
            return false;
        }
    }

}
