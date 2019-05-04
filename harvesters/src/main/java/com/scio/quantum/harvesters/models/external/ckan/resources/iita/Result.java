
package com.scio.quantum.harvesters.models.external.ckan.resources.iita;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Result {

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("facets")
    @Expose
    private Facets facets;
    @SerializedName("results")
    @Expose
    private List<Result_> results = null;
    @SerializedName("search_facets")
    @Expose
    private SearchFacets searchFacets;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Result() {
    }

    /**
     * 
     * @param searchFacets
     * @param sort
     * @param results
     * @param count
     * @param facets
     */
    public Result(int count, String sort, Facets facets, List<Result_> results, SearchFacets searchFacets) {
        super();
        this.count = count;
        this.sort = sort;
        this.facets = facets;
        this.results = results;
        this.searchFacets = searchFacets;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Facets getFacets() {
        return facets;
    }

    public void setFacets(Facets facets) {
        this.facets = facets;
    }

    public List<Result_> getResults() {
        return results;
    }

    public void setResults(List<Result_> results) {
        this.results = results;
    }

    public SearchFacets getSearchFacets() {
        return searchFacets;
    }

    public void setSearchFacets(SearchFacets searchFacets) {
        this.searchFacets = searchFacets;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("count", count).append("sort", sort).append("facets", facets).append("results", results).append("searchFacets", searchFacets).toString();
    }

}
