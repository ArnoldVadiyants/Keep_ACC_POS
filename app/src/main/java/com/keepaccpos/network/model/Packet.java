package com.keepaccpos.network.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Arnold on 06.08.2016.
 */

public class Packet extends Object {
    @SerializedName("networkDataId")
    String networkDataId;
    @SerializedName("controller")
    String controller;
    @SerializedName("controllerMethod")
    String controllerMethod;
    @SerializedName("controllerData")
    ControllerData controllerData;
    @SerializedName("dashboardKey")
    String dashboardKey;

    public Packet(String networkDataId, String controller, String controllerMethod, ControllerData controllerData, String dashboardKey) {
        this.networkDataId = networkDataId;
        this.controller = controller;
        this.controllerMethod = controllerMethod;
        this.controllerData = controllerData;
        this.dashboardKey = dashboardKey;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        //Packet Array
        Map<String,Object>packetMap = new HashMap<>();

       List<Map> packet = new ArrayList<>();
        // Packet Array[0]
        Map<String, Object> packetData = new HashMap<>();
        packetData.put("networkDataId",networkDataId);
        packetData.put("controller", controller);
        packetData.put("controllerMethod", controllerMethod);
        // ControllerData Array
        List<Map>controllerData = new ArrayList<>();
        // ControllerData Array[0]
        Map<String, Object> controllerDataMap = new HashMap<>();
        controllerDataMap.put("tableName",this.controllerData.tableName);
        // TableData Array
        List<Map>tableData = new ArrayList<>();
        // TableData Array[0]
      /*  Map<String,Object>tableDataMap = new HashMap<>();
        tableDataMap.put("cId",this.controllerData.tableData.);
        tableDataMap.put("email",this.controllerData.tableData.email);
        tableDataMap.put("password",this.controllerData.tableData.password);*/
        tableData.add(this.controllerData.tableData.tableData);
        controllerDataMap.put("tableData",tableData);
        controllerData.add(controllerDataMap);
        packetData.put("controllerData", controllerData);
        packet.add(packetData);
        packetMap.put("packet",packet);
        if(dashboardKey!=null &&!dashboardKey.isEmpty())
        {
            packetMap.put("dashboardKey", this.dashboardKey);
        }
       // String json = gson.toJson(packet);
        String objectSerializable = gson.toJson(packetMap);
      /*  String  pedidoSerializado = "";
        try {
            pedidoSerializado = URLEncoder.encode(objectSerializable, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return objectSerializable;
        }
        return pedidoSerializado;*/
        return objectSerializable;
    }

    public String getNetworkDataId() {
        return networkDataId;
    }

    public String getController() {
        return controller;
    }

    public String getControllerMethod() {
        return controllerMethod;
    }

    public ControllerData getControllerData() {
        return controllerData;
    }
}

