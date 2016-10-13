package com.keepaccpos.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by Arnold on 11.09.2016.
 */
@Generated("org.jsonschema2pojo")
 public class DataTableContent {

    @SerializedName("block")
    @Expose
    private List<Table> table = new ArrayList<Table>();

    /**
     *
     * @return
     * The table
     */
    public List<Table> getTable() {
        return table;
    }

    /**
     *
     * @param table
     * The table
     */
    public void setTable(List<Table> table) {
        this.table = table;
    }

}
