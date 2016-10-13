package com.keepaccpos.network;

import android.content.Context;

import com.keepaccpos.helpers.SessionManager;
import com.keepaccpos.network.model.ControllerData;
import com.keepaccpos.network.model.Packet;
import com.keepaccpos.network.model.TableData;

/**
 * Created by Arnold on 10.09.2016.
 */

public class PacketPost {
    public static final String TYPE_TABLE = "Table";
    public static final String TYPE_BANQUETTE = "Banquette";
    public static final String TYPE_DELIVERY = "Delivery";

    private static final String NETWORK_DATA_ID_NONE = "0";
    private static final String CONTROLLER_AUTHORIZATION = "AuthorizationController";
    private static final String CONTROLLER_USER = "UserController";
    private static final String CONTROLLER_TABLEVIEW = "TableViewController";
    private static final String CONTROLLER_BILL = "BillController";
    private static final String CONTROLLER_CATEGORY = "CategoryController";
    private static final String CONTROLLER_METHOD_LOGIN = "Login";
    private static final String CONTROLLER_METHOD_LOGOUT = "Logout";
    private static final String CONTROLLER_METHOD_GET_BANQUET = "GetBanquette";
    private static final String CONTROLLER_METHOD_GET_DELIVERY = "GetDelivery";

    private static final String CONTROLLER_METHOD_CANCEL_REFINE = "CancelPartial";
    private static final String CONTROLLER_METHOD_CANCEL_FULL = "CancelFull";
    private static final String CONTROLLER_METHOD_GET = "Get";
    private static final String CONTROLLER_METHOD_OPEN = "Open";
    private static final String CONTROLLER_METHOD_ADD = "Add";
    private static final String CONTROLLER_METHOD_LOGIN_POS = "LoginPos";
    private static final String CONTROLLER_METHOD_REFINE = "Refine";
    private static final String CONTROLLER_METHOD_CLOSE = "Close";
    private static final String CONTROLLER_METHOD_UPDATE_BILL = "UpdateBill";
    private static final String TABLE_NAME_REALIZATION_TABLEVIEW_MODEL = "realization_tableviewmodel";
    private static final String TABLE_NAME_REALIZATION_BANQUET_BILL_JOURNAL_MODEL = "realization_banquettebilljournalmodel";
    private static final String TABLE_NAME_REALIZATION_DELIVERY_BILL_JOURNAL_MODEL = "realization_deliverybilljournalmodel";


    public enum PacketType {Login,Logout,SessionFinish, LoginPos, GetBlocks, GetTables, GetCategories, GetBill, AddItemToBill, UpdateItemBill,Refine,Close,CancelRefine,CancelFull, OpenTable, GetBanquette,GetDelivery, OpenDelivery}

    //private static Packet mPacket;
    public static Packet getPacket(Context context, PacketType packetType, TableData tableData) {
        String dashboardKey = new SessionManager(context).getDashboardKey();
        Packet packet = null;
        ControllerData controllerData = null;
        switch (packetType) {
            case Login:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_AUTHORIZATION, CONTROLLER_METHOD_LOGIN, controllerData, null);
                break;
            case Logout:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_USER, CONTROLLER_METHOD_LOGOUT, controllerData, dashboardKey);
                break;

            case LoginPos:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_AUTHORIZATION, CONTROLLER_METHOD_LOGIN_POS, controllerData, dashboardKey);
                break;
            case GetBlocks:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_CATEGORY, CONTROLLER_METHOD_GET, controllerData, null);
                break;
            case GetTables:
                controllerData = new ControllerData(TABLE_NAME_REALIZATION_TABLEVIEW_MODEL, tableData);
                packet = new Packet("0", CONTROLLER_TABLEVIEW, CONTROLLER_METHOD_GET, controllerData, dashboardKey);
                break;
            case GetCategories:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_CATEGORY, CONTROLLER_METHOD_GET, controllerData, dashboardKey);
                break;
            case GetBill:
                controllerData = new ControllerData(TABLE_NAME_REALIZATION_TABLEVIEW_MODEL, tableData);
                packet = new Packet("0", CONTROLLER_BILL, CONTROLLER_METHOD_GET, controllerData, dashboardKey);
                break;
            case AddItemToBill:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_BILL, CONTROLLER_METHOD_ADD, controllerData, dashboardKey);
                break;
            case UpdateItemBill:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_BILL, CONTROLLER_METHOD_UPDATE_BILL, controllerData, dashboardKey);
                break;
            case Refine:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_BILL, CONTROLLER_METHOD_REFINE, controllerData, dashboardKey);
                break;
            case Close:
            controllerData = new ControllerData("", tableData);
            packet = new Packet("0", CONTROLLER_BILL, CONTROLLER_METHOD_CLOSE, controllerData, dashboardKey);
            break;
            case CancelRefine:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_BILL, CONTROLLER_METHOD_CANCEL_REFINE, controllerData, dashboardKey);
                break;
            case CancelFull:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_BILL, CONTROLLER_METHOD_CANCEL_FULL, controllerData, dashboardKey);
                break;
            case OpenTable:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_BILL, CONTROLLER_METHOD_OPEN, controllerData, dashboardKey);
                break;
            case GetBanquette:
                controllerData = new ControllerData(TABLE_NAME_REALIZATION_BANQUET_BILL_JOURNAL_MODEL, tableData);
                packet = new Packet("0", CONTROLLER_TABLEVIEW, CONTROLLER_METHOD_GET_BANQUET, controllerData, dashboardKey);
                break;
            case GetDelivery:
                controllerData = new ControllerData(TABLE_NAME_REALIZATION_DELIVERY_BILL_JOURNAL_MODEL, tableData);
                packet = new Packet("0", CONTROLLER_TABLEVIEW, CONTROLLER_METHOD_GET_DELIVERY, controllerData, dashboardKey);
                break;
            case OpenDelivery:
                controllerData = new ControllerData("", tableData);
                packet = new Packet("0", CONTROLLER_BILL, CONTROLLER_METHOD_OPEN, controllerData, dashboardKey);
                break;
        }
        return packet;
    }

}

