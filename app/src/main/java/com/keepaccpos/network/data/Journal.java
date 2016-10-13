package com.keepaccpos.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Arnold on 28.09.2016.
 */
@Generated("org.jsonschema2pojo")
public class Journal {
    @SerializedName("id")
    @Expose
    private int id;

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

}