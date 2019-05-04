package com.scio.quantum.extractor.models.vocabulary;

public class GeonamesTerm extends Term{

    private String capital;
    private String population;
    private String continent;
    private String geonameid;

    public GeonamesTerm(String capital, String population, String continent, String geonameid, String term, String frequency) {
        super(term, frequency);
        this.capital = capital;
        this.population = population;
        this.continent = continent;
        this.geonameid = geonameid;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getGeonameid() {
        return geonameid;
    }

    public void setGeonameid(String geonameid) {
        this.geonameid = geonameid;
    }

}
