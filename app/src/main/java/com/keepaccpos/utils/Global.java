package com.keepaccpos.utils;

public class Global {
    public static final String AMPERSAND = "&";
    public static final String AT_STRING = "@string";
    public static final String BLANK = " ";
    public static final String CHAR_SET_NAME = "UTF-8";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String DA_AGENT_ENABLED = "DTXDAAgentEnabled";
    public static final String DA_AGENT_RESP = "jsonpCallback({mcd:9541})";
    public static final String DA_AGENT_URL = "http://localhost:31592/da/mcd";
    public static final String DB_ERROR = "Database error.";
    public static boolean DEBUG = false;
    public static final String DOT = ".";
    public static final String DTX_NO_BG_SEND_MODE = "DTXDisableBackgroundModeSend";
    public static final String EMPTY_STRING = "";
    public static final String EQUAL = "=";
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String HYPHEN = "-";
    public static String LOG_PREFIX = null;
    public static final String NEWLINE = "\n";
    public static final String PROP_APP_NAME = "DTXApplicationName";
    public static final String PROP_LOG_LEVEL = "DTXLogLevel";
    public static final String QUESTION = "?";
    public static final String[] REQUIRED_PERMISSIONS;
    public static final String SEMICOLON = ";";
    public static final String SHARED_PREFERENCES = "com.dynatrace.android.dtxPref";
    public static final String SLASH = "/";
    public static final String UNDERSCORE = "_";
    public static final String UNKNOWN = "unknown";

    static {
        LOG_PREFIX = "dtxAgent";
        DEBUG = false;
        REQUIRED_PERMISSIONS = new String[]{"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"};
    }
}
