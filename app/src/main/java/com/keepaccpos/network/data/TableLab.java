package com.keepaccpos.network.data;

/**
 * Created by Arnold on 12.04.2016.
 */

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.List;


public class TableLab {
    private HashMap<String, List<Table>> mTables = new HashMap<>();

    private static TableLab sTableLab;

    private TableLab() {

    }

    public static TableLab getInstance() {
        if (sTableLab == null) {
            sTableLab = new TableLab();
        }
        return sTableLab;
    }
    public void removeTables(String name) {

         mTables.remove(name);
    }
    @Nullable
    public List<Table> getTables(String name) {

        return mTables.get(name);
    }

    public void setTables(String blockName, List<Table> tables) {
        this.mTables.put(blockName, tables);
    }

    @Nullable
    public Table getTable(String id, List<Table> tables) {
        for (Table t : tables) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }
    @Nullable
    public Table getTable(String id,String blockName) {
        List<Table>tables = getTables(blockName);
        if(tables == null || tables.isEmpty())
        {
            return null;
        }
        for (Table t : tables) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public void removeData() {
        mTables.clear();
    }



    @Nullable
    public Table getTableByName(String title, List<Table> tables) {
        for (Table t : tables) {
            if (t.getName().equals(title)) {
                return t;
            }
        }
        return null;
    }


}

