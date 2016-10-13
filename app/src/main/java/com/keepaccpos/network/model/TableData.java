package com.keepaccpos.network.model;

import java.util.HashMap;

/**
 * Created by Arnold on 07.08.2016.
 */
public class TableData {
     HashMap<String,String> tableData = new HashMap<>();
    public TableData()
    {}

    public TableData(HashMap<String, String> tableData) {
        this.tableData = tableData;
    }


    public HashMap<String,String>getTableData()
{
    return tableData;
}
}
