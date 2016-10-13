package com.keepaccpos.network.data;

/**
 * Created by Arnold on 11.09.2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DataTable {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private DataTableContent data;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The result
     */
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @return
     * The data
     */
    public DataTableContent getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataTableContent data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
