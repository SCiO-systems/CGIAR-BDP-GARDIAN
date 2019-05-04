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

import org.apache.commons.codec.binary.Base64;

public class Base64DecoderUnitTest {

    public static void main(String[] args){


        String stringEncoded = "ew0KICAgICJQdWJNZXRhZG" +
                "F0YSI6IHsNCiAgICAgICAgIkNvbnRlbnRQcm92aWRlciI6IFsNCiAgICAgICAgI" +
                "CAgIHsNCiAgICAgICAgICAgICAgICAiQ29udGVudFByb3ZpZGVySUQiOiAiQ0dJQVItQ0lNTVlULVBVQlMiLA0KI" +
                "CAgICAgICAgICAgICAgICJDb250ZW50UHJvdmlkZXJOYW1lIjogIkludGVybmF0aW9uYWwgTWFpemUgYW5kIFdoZWF" +
                "0IEltcHJvdmVtZW50IENlbnRlciAoQ0lNTVlUKSIsDQogICAgICAgICAgICAgICAgIlByb3ZpZGVyTGluayI6ICJod" +
                "HRwczovL3JlcG9zaXRvcnkuY2ltbXl0Lm9yZy94bWx1aS9oYW5kbGUvMTA4ODMvMTc4MTMiLA0KICAgICAgICAgICA" +
                "gICAgICJIREwiOiAiaHR0cDovL2hkbC5oYW5kbGUubmV0LzEwODgzLzE3ODEzIg0KICAgICAgICAgICAgfQ0KICAgICA" +
                "gICBdLA0KICAgICAgICAiRE9JIjogIiIsDQogICAgICAgICJJc09wZW5BY2Nlc3MiOiB0cnVlLA0KICAgICAgICAiRG9j" +
                "TGluayI6ICJodHRwczovL3JlcG9zaXRvcnkuY2ltbXl0Lm9yZy94bWx1aS9iaXRzdHJlYW0vaGFuZGxlLzEwODgzLzE3ODEz" +
                "LzU2OTg0c2VwdGVtYmVyMjAxNi5wZGYiLA0KICAgICAgICAiQ2l0YXRpb24iOiAiJ1NJTUxFU0EgQnVsbGV0aW4nLCBDSU1NW" +
                "VQsIDIwMTYiLA0KICAgICAgICAiRmVhdHVyZWRJbWdVUkwiOiAiaHR0cHM6Ly9yZXBvc2l0b3J5LmNpbW15dC5vcmcveG1sdW" +
                "kvYml0c3RyZWFtL2hhbmRsZS8xMDg4My8xNzgxMy81Njk4NHNlcHRlbWJlcjIwMTYucGRmLmpwZyIsDQogICAgICAgICJUaXRsZ" +
                "SI6ICJTSU1MRVNBIEJ1bGxldGluIiwNCiAgICAgICAgIlN1bW1hcnkiOiAiIiwNCiAgICAgICAgIlNlcmllcyI6ICIiLA0KICAgIC" +
                "AgICAiVm9sdW1lIjogIiIsDQogICAgICAgICJOdW0iOiAiIiwNCiAgICAgICAgIlBhZ2VzIjogIiIsDQogICAgICAgICJJc3N1ZWRZZWFyIjogIj" +
                "IwMTYiLA0KICAgICAgICAiUHViWWVhciI6ICIyMDE2IiwNCiAgICAgICAgIkxhbmd1YWdlIjogIkVuZ2xpc2giLA0KICAgICAgICAi" +
                "Q2F0ZWdvcnkiOiAiT3RoZXIiLA0KICAgICAgICAiUHVibGlzaGVyIjogIkNJTU1ZVCIsDQogICAgICAgICJQdWJsaXNoZXJMaW5rIjo" +
                "gIiIsDQogICAgICAgICJBdXRob3JzIjogIiIsDQogICAgICAgICJUb3BpY3MiOiBbXSwNCiAgICAgICAgIkdlb2dyYXBoaWMiOiBbICJt" +
                "b3phbWJpcXVlIl0NCiAgICB9DQp9";

        byte[] decodedBytes = Base64.decodeBase64(stringEncoded);
        String stringDecoded = new String(decodedBytes);
        System.out.println(stringDecoded);



    }

}
