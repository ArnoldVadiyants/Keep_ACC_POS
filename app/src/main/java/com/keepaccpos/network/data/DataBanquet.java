package com.keepaccpos.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Arnold on 21.09.2016.
 */

@Generated("org.jsonschema2pojo")
public class DataBanquet {


    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    private DataBanquetContent data;
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
    public DataBanquetContent getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataBanquetContent data) {
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