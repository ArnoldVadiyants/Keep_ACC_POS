package com.keepaccpos.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by Arnold on 20.09.2016.
 */
@Generated("org.jsonschema2pojo")

    public class DataBanquetContent {

    @SerializedName("head")
    @Expose
    private List<List<BanquetHead>> head = new ArrayList<List<BanquetHead>>();
    @SerializedName("body")
    @Expose
    private List<List<List<BanquetBody>>> body = new ArrayList<List<List<BanquetBody>>>();

    /**
     *
     * @return
     * The head
     */
    public List<List<BanquetHead>> getHead() {
        return head;
    }

    /**
     *
     * @param head
     * The head
     */
    public void setHead(List<List<BanquetHead>> head) {
        this.head = head;
    }

    /**
     *
     * @return
     * The body
     */
    public List<List<List<BanquetBody>>> getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBanquetBody(List<List<List<BanquetBody>>> body) {
        this.body = body;
    }

}