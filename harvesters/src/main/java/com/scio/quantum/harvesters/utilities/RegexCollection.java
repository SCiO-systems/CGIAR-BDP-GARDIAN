package com.scio.quantum.harvesters.utilities;

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

    public String numOfJournal(String source){

        String regex = "\\([^\\d]*(\\d+)[^\\d]*\\)";
        String num = "";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);

        if(matcher.find() == true){
            num = matcher.group(1);
        }
        return num;
    }

    public String volumeOfJournal(String source){

        String regex = "(\\d+)\\([^\\d]*(\\d+)[^\\d]*\\)";
        String volume = "";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);

        if(matcher.find() == true){
            volume = matcher.group(1);
        }
        return volume;
    }

    public String simpleDOI(String doi){
        String doiRegex = "10.\\d{4,9}/[-._;()/:A-Z0-9]+" +
                "|10.1002/[^\\s]+" +
                "|10.\\d{4}/\\d+-\\d+X?(\\d+)\\d+<[\\d\\w]+:[\\d\\w]*>\\d+.\\d+.\\w+;\\d" +
                "|10.1021/\\w\\w\\d++" +
                "|10.1207/[\\w\\d]+\\&\\d+_\\d+";

        Pattern pattern = Pattern.compile(doiRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(doi);

        if(matcher.find() == true){
            return matcher.group();
        }else{
            return "";
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
