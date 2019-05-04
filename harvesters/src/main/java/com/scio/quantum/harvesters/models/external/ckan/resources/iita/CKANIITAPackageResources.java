
package com.scio.quantum.harvesters.models.external.ckan.resources.iita;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CKANIITAPackageResources {

    @SerializedName("help")
    @Expose
    private String help;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("result")
    @Expose
    private Result result;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CKANIITAPackageResources() {
    }

    /**
     * 
     * @param result
     * @param help
     * @param success
     */
    public CKANIITAPackageResources(String help, boolean success, Result result) {
        super();
        this.help = help;
        this.success = success;
        this.result = result;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("help", help).append("success", success).append("result", result).toString();
    }

}
