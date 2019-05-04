
package com.scio.quantum.harvesters.models.external.dataverse.result;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Data {

    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("total_count")
    @Expose
    private int totalCount;
    @SerializedName("start")
    @Expose
    private int start;
    @SerializedName("spelling_alternatives")
    @Expose
    private SpellingAlternatives spellingAlternatives;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("count_in_response")
    @Expose
    private int countInResponse;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param items
     * @param start
     * @param totalCount
     * @param countInResponse
     * @param q
     * @param spellingAlternatives
     */
    public Data(String q, int totalCount, int start, SpellingAlternatives spellingAlternatives, List<Item> items, int countInResponse) {
        super();
        this.q = q;
        this.totalCount = totalCount;
        this.start = start;
        this.spellingAlternatives = spellingAlternatives;
        this.items = items;
        this.countInResponse = countInResponse;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public SpellingAlternatives getSpellingAlternatives() {
        return spellingAlternatives;
    }

    public void setSpellingAlternatives(SpellingAlternatives spellingAlternatives) {
        this.spellingAlternatives = spellingAlternatives;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getCountInResponse() {
        return countInResponse;
    }

    public void setCountInResponse(int countInResponse) {
        this.countInResponse = countInResponse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("q", q).append("totalCount", totalCount).append("start", start).append("spellingAlternatives", spellingAlternatives).append("items", items).append("countInResponse", countInResponse).toString();
    }

}
