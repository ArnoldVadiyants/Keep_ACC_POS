package com.keepaccpos.network.model;

import com.google.gson.annotations.SerializedName;
import com.keepaccpos.network.model.TableData;

/**
 * Created by Arnold on 06.08.2016.
 */
public class ControllerData {
    @SerializedName("tableName")
    String tableName;
    @SerializedName("tableData")
    TableData tableData;

    public String getTableName() {
        return tableName;
    }

    public TableData getTableData() {
        return tableData;
    }

    public ControllerData(String tableName, TableData tableData) {
        this.tableName = tableName;
        this.tableData = tableData;
    }
}
