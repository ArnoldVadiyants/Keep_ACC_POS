package com.keepaccpos.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.support.v4.print.PrintHelper;
import android.util.Printer;

import com.keepaccpos.R;
import com.keepaccpos.utils.CommonUtils;
import com.keepaccpos.utils.Global;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.impl.SimpleLog;
import org.apache.http.conn.routing.HttpRouteDirector;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import at.smartlab.tshop.C0250R;
import at.smartlab.tshop.Utils;
import io.fabric.sdk.android.services.common.CommonUtils;

public class Settings {
    public static final String BASIC_CHARTS = "basic.charts";
    public static final String BASIC_CSV = "basic.csv";
    public static final String BASIC_DRIVE = "basic.drive";
    public static final String BASIC_GASTRO = "basic.gastro";
    public static final String BASIC_PAYPAL = "basic.paypal";
    public static final String BASIC_PRO = "basic.pro";
    public static final String BASIC_PWD = "basic.pwd";
    public static final String BASIC_SUBSCRIPTION = "basic.subscription";
    public static PrintHelper print;
    private String authorizeNetLogin;
    private String authorizeNetPwd;
    private boolean autoBackup;
    private int backupInterval;
    private String bitcoinAddress;
    private String bluetoothPrinterAddress;
    private String bluetoothPrinterName;
    private int callNumber;
    private String characterEncoding;
    private boolean chartsFeature;
    private boolean checkPassword;
    private boolean checkoutImagePrint;
    private boolean checkoutPdfPrint;
    private boolean checkoutPrint;
    private Context context;
    private boolean csvFeature;
    private String currencySymbol;
    private String cutterCmds;
    private String drawerCmds;
    private boolean driveFeature;
    private boolean escPos;
    private int escPosCharSize;
    private int escPosCharTable;
    private String fileslocation;
    private boolean firstStart;
    public String gAccount;
    private boolean gastroFeature;
    private String invoiceFooter;
    public int invoicePrintMultiply;
    private String invoiceTitle;
    private String invoicingInfos;
    private String kitchenDisplayHost;
    private int kitchenDisplayPort;
    private int lastUsedPaymentIndex;
    private Locale locale;
    private Bitmap logo;
    private int maxPrintColumnNr;
    private BigDecimal minStockValue;
    private int nrOfTables;
    private int operator_id;
    private String password;
    private String paypalAccount;
    private boolean paypalFeature;
    private boolean paypalInitialized;
    private int pos_id;
    private boolean printCallNumberOnInvoice;
    private boolean printTableOnInvoice;
    private String printer;
    private int printerPort;
    private Printer printerType;
    private boolean proVersion;
    private boolean pwdFeature;
    private String shopName;
    private String shopText;
    private String stripeKey;
    private List<String> tableNames;
    private boolean usTaxMode;

    public String getCurrencySymbol() {
        if (this.currencySymbol != null) {
            return this.currencySymbol;
        }
        NumberFormat nf = getNumberFormatter();
        if (nf == null || nf.getCurrency() == null) {
            return StringUtils.EMPTY;
        }
        return nf.getCurrency().getCurrencyCode();
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Settings() {
        this.proVersion = false;
        this.csvFeature = false;
        this.paypalFeature = false;
        this.gastroFeature = false;
        this.chartsFeature = false;
        this.driveFeature = false;
        this.pwdFeature = false;
        this.paypalInitialized = false;
        this.gAccount = StringUtils.EMPTY;
        this.pos_id = 1;
        this.operator_id = 1;
        this.callNumber = 1;
        this.characterEncoding = "IBM437";
        this.printer = "10.0.0.4";
        this.printerPort = 9100;
        this.minStockValue = BigDecimal.ZERO;
        this.invoicingInfos = StringUtils.EMPTY;
        this.tableNames = new ArrayList();
        this.kitchenDisplayPort = 8090;
        this.authorizeNetLogin = StringUtils.EMPTY;
        this.authorizeNetPwd = StringUtils.EMPTY;
        this.printerType = Printer.createPrinter(1);
    }

    public int getBackupInterval() {
        return this.backupInterval;
    }

    public void setBackupInterval(int backupInterval) {
        this.backupInterval = backupInterval;
    }

    public String getFileslocation() {
        return this.fileslocation;
    }

    public void setFileslocation(String fileslocation) {
        this.fileslocation = fileslocation;
    }

    public boolean isEscPos() {
        return this.escPos;
    }

    public void setEscPos(boolean escPos) {
        this.escPos = escPos;
    }

    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public boolean isCheckoutPrint() {
        return this.checkoutPrint;
    }

    public void setCheckoutPrint(boolean checkoutPrint) {
        this.checkoutPrint = checkoutPrint;
    }

    public boolean isCheckoutImagePrint() {
        return this.checkoutImagePrint;
    }

    public int getInvoicePrintMultiply() {
        return this.invoicePrintMultiply;
    }

    public void setInvoicePrintMultiply(int invoicePrintMultiply) {
        this.invoicePrintMultiply = invoicePrintMultiply;
    }

    public void setCheckoutImagePrint(boolean checkoutImagePrint) {
        this.checkoutImagePrint = checkoutImagePrint;
    }

    public boolean isCheckoutPdfPrint() {
        return this.checkoutPdfPrint;
    }

    public void setCheckoutPdfPrint(boolean checkoutPdfPrint) {
        this.checkoutPdfPrint = checkoutPdfPrint;
    }

    public int getEscPosCharTable() {
        return this.escPosCharTable;
    }

    public void setEscPosCharTable(int escPosCharTable) {
        this.escPosCharTable = escPosCharTable;
    }

    public int getEscPosCharSize() {
        return this.escPosCharSize;
    }

    public void setEscPosCharSize(int escPosCharSize) {
        this.escPosCharSize = escPosCharSize;
    }

    public String getCutterCmds() {
        return this.cutterCmds;
    }

    public byte[] getUnescapedCutterCmds() {
        return Utils.toBytes(getCutterCmds());
    }

    public void setCutterCmds(String cutterCmds) {
        this.cutterCmds = cutterCmds;
    }

    public String getDrawerCmds() {
        return this.drawerCmds;
    }

    public byte[] getUnescapedDrawerCmds() {
        return Utils.toBytes(getDrawerCmds());
    }

    public void setDrawerCmds(String drawerCmds) {
        this.drawerCmds = drawerCmds;
    }

    public String getAuthorizeNetLogin() {
        return this.authorizeNetLogin;
    }

    public void setAuthorizeNetLogin(String authorizeNetLogin) {
        this.authorizeNetLogin = authorizeNetLogin;
    }

    public String getAuthorizeNetPwd() {
        return this.authorizeNetPwd;
    }

    public void setAuthorizeNetPwd(String authorizeNetPwd) {
        this.authorizeNetPwd = authorizeNetPwd;
    }

    public String getAuthorizeNetDeviceID() {
        return String.format("TabShop_%s_%d", new Object[]{getShopName(), Integer.valueOf(getPos_id())});
    }

    public String getPaymentName(int checkoutType) {
        switch (checkoutType) {
            case HttpRouteDirector.COMPLETE /*0*/:
                return this.context.getResources().getString(C0250R.string.cash);
            case HttpRouteDirector.CONNECT_TARGET /*1*/:
                return this.context.getResources().getString(C0250R.string.creditcard);
            case HttpRouteDirector.CONNECT_PROXY /*2*/:
                return this.context.getResources().getString(C0250R.string.card);
            case HttpRouteDirector.TUNNEL_TARGET /*3*/:
                return this.context.getResources().getString(C0250R.string.paypal);
            case HttpRouteDirector.TUNNEL_PROXY /*4*/:
                return this.context.getResources().getString(C0250R.string.check);
            case HttpRouteDirector.LAYER_PROTOCOL /*5*/:
                return this.context.getResources().getString(C0250R.string.paypalhere);
            case SimpleLog.LOG_LEVEL_FATAL /*6*/:
                return this.context.getResources().getString(C0250R.string.invoice);
            case SimpleLog.LOG_LEVEL_OFF /*7*/:
                return this.context.getResources().getString(C0250R.string.bitcoin);
            case PayPalActivity.VIEW_NUM_VIEWS /*9*/:
                return this.context.getResources().getString(C0250R.string.payleven);
            case PayPal.PAYMENT_SUBTYPE_INSURANCE /*10*/:
                return this.context.getResources().getString(C0250R.string.yodo);
            case PayPal.PAYMENT_SUBTYPE_REMITTANCES /*11*/:
                return this.context.getResources().getString(C0250R.string.stripe);
            default:
                for (PaymentMethod pm : Model.getInstance().getAllUserPaymentMethods()) {
                    if (pm.getId() == ((long) checkoutType)) {
                        return pm.getName();
                    }
                }
                return StringUtils.EMPTY;
        }
    }

    public int getLastUsedPaymentIndex() {
        return this.lastUsedPaymentIndex;
    }

    public void setLastUsedPaymentIndex(int lastIndex) {
        this.lastUsedPaymentIndex = lastIndex;
    }

    public String getBluetoothPrinterName() {
        return this.bluetoothPrinterName;
    }

    public void setBluetoothPrinterName(String bluetoothPrinterName) {
        this.bluetoothPrinterName = bluetoothPrinterName;
    }

    public String getBluetoothPrinterAddress() {
        return this.bluetoothPrinterAddress;
    }

    public void setBluetoothPrinterAddress(String bluetoothPrinterAddress) {
        this.bluetoothPrinterAddress = bluetoothPrinterAddress;
    }

    public Locale getLocale() {
        if (this.locale == null) {
            return Locale.getDefault();
        }
        return this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getInvoicingInfos() {
        return this.invoicingInfos;
    }

    public void setInvoicingInfos(String invoicingInfos) {
        this.invoicingInfos = invoicingInfos;
    }

    public BigDecimal getMinStockValue() {
        return this.minStockValue;
    }

    public void setMinStockValue(BigDecimal minStockValue) {
        this.minStockValue = minStockValue;
    }

    public boolean isUSTaxMode() {
        return this.usTaxMode;
    }

    public void setUSTaxMode(boolean usTaxMode) {
        this.usTaxMode = usTaxMode;
    }

    public Printer getPrinterType() {
        return this.printerType;
    }

    public void setPrinterType(Printer printerType) {
        this.printerType = printerType;
    }

    public int getMaxPrintColumnNr() {
        return this.maxPrintColumnNr;
    }

    public void setMaxPrintColumnNr(int maxPrintColumnNr) {
        this.maxPrintColumnNr = maxPrintColumnNr;
    }

    public Context getContext() {
        return this.context;
    }

    public NumberFormat getNumberFormatter() {
        NumberFormat formatter = NumberFormat.getInstance(getLocale());
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        return formatter;
    }

    public DateFormat getDateTimeFormatter() {
        return SimpleDateFormat.getDateTimeInstance(3, 3, getLocale());
    }

    public DateFormat getDateFormatter() {
        return SimpleDateFormat.getDateInstance(3, getLocale());
    }

    public int getPos_id() {
        return this.pos_id;
    }

    public void setPos_id(int pos_id) {
        this.pos_id = pos_id;
    }

    public int getOperator_id() {
        return this.operator_id;
    }

    public void setOperator_id(int operator_id) {
        this.operator_id = operator_id;
    }

    public boolean hasCSVFeature() {
        if (isProVersion()) {
            return true;
        }
        return this.csvFeature;
    }

    public void setCSVFeature(boolean enabled) {
        this.csvFeature = enabled;
    }

    public boolean hasChartsFeature() {
        if (isProVersion()) {
            return true;
        }
        return this.chartsFeature;
    }

    public void setChartsFeature(boolean chartsFeature) {
        this.chartsFeature = chartsFeature;
    }

    public boolean hasPwdFeature() {
        if (isProVersion()) {
            return true;
        }
        return this.pwdFeature;
    }

    public void setPwdFeature(boolean pwdFeature) {
        this.pwdFeature = pwdFeature;
    }

    public boolean hasPaypalFeature() {
        if (isProVersion()) {
            return true;
        }
        return this.paypalFeature;
    }

    public void setDriveFeature(boolean driveFeature) {
        this.driveFeature = driveFeature;
    }

    public boolean hasDriveFeature() {
        return true;
    }

    public void setPaypalFeature(boolean paypalFeature) {
        this.paypalFeature = paypalFeature;
    }

    public boolean hasGastroFeature() {
        if (isProVersion()) {
            return true;
        }
        return this.gastroFeature;
    }

    public void setGastroFeature(boolean gastroFeature) {
        this.gastroFeature = gastroFeature;
    }

    public boolean isProVersion() {
        return this.proVersion;
    }

    public void setProVersion(boolean proVersion) {
        this.proVersion = proVersion;
    }

    public String getShopText() {
        return this.shopText;
    }

    public void setShopText(String shopText) {
        this.shopText = shopText;
    }

    public String getInvoiceTitle() {
        return this.invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public List<String> getTableNames() {
        return this.tableNames;
    }

    public void print(String jobName, Bitmap b) {
        print.printBitmap(jobName, b);
    }

    public String getInvoiceFooter() {
        return this.invoiceFooter;
    }

    public void setInvoiceFooter(String invoiceFooter) {
        this.invoiceFooter = invoiceFooter;
    }

    public boolean isAutoBackup() {
        return this.autoBackup;
    }

    public void setAutoBackup(boolean autoBackup) {
        this.autoBackup = autoBackup;
    }

    public Bitmap getLogo() {
        if (this.context == null) {
            return this.logo;
        }
        try {
            this.logo = BitmapFactory.decodeStream(this.context.openFileInput("logo.png"));
            return this.logo;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public Bitmap getLogo(int width, int height) {
        if (this.context != null) {
            try {
                this.logo = BitmapFactory.decodeStream(this.context.openFileInput("logo.png"));
                if (this.logo != null) {
                    Matrix m = new Matrix();
                    m.setRectToRect(new RectF(0.0f, 0.0f, (float) this.logo.getWidth(), (float) this.logo.getHeight()), new RectF(0.0f, 0.0f, (float) width, (float) height), ScaleToFit.CENTER);
                    return Bitmap.createBitmap(this.logo, 0, 0, this.logo.getWidth(), this.logo.getHeight(), m, true);
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
                return null;
            }
        }
        return this.logo;
    }

    public void setLogo(Bitmap bmp, Context c) {
        this.logo = bmp;
        if (bmp != null) {
            try {
                bmp.compress(CompressFormat.PNG, 100, c.openFileOutput("logo.png", 0));
                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        c.deleteFile("logo.png");
    }

    public int increaseCallNumber() {
        this.callNumber++;
        if (this.callNumber > 100) {
            this.callNumber = 1;
        }
        return this.callNumber;
    }

    public int getActualCallNumber() {
        return this.callNumber;
    }

    public void loadSettings(SharedPreferences sp, Context c) {
        int i;
        this.context = c;
        print = new PrintHelper(Model.getInstance().getSettings().getContext());
        print.setScaleMode(1);
        print.setColorMode(1);
        setPrintTableOnInvoice(sp.getBoolean("printTableOnInvoice", true));
        setPrintCallNumberOnInvoice(sp.getBoolean("printCallNumberOnInvoice", false));
        setCheckoutPrint(sp.getBoolean("checkoutPrint", false));
        setCheckoutImagePrint(sp.getBoolean("checkoutImagePrint", false));
        setCheckoutPdfPrint(sp.getBoolean("checkoutPdfPrint", false));
        setInvoicePrintMultiply(sp.getInt("invoicePrintMultiply", 1));
        setLastUsedPaymentIndex(sp.getInt("lastUsedPaymentIndex", 0));
        this.firstStart = sp.getBoolean("firstStart", true);
        this.shopText = sp.getString("shopText", "Burgercastle\n45604\nNew Haven, NSL\nVATNR: 1234");
        String footer = sp.getString("invoiceFooter", StringUtils.EMPTY);
        if (footer.trim().equalsIgnoreCase("printed by TabShop")) {
            setInvoiceFooter(StringUtils.EMPTY);
        } else {
            setInvoiceFooter(footer);
        }
        this.printer = sp.getString("printerIP", StringUtils.EMPTY);
        this.pos_id = sp.getInt("posId", 1);
        this.paypalAccount = sp.getString("paypalAccount", StringUtils.EMPTY);
        this.bitcoinAddress = sp.getString("bitcoin", StringUtils.EMPTY);
        setAutoBackup(sp.getBoolean("autoBackup", false));
        setBackupInterval(sp.getInt("backupInterval", 60));
        setEscPos(sp.getBoolean("escPos", false));
        setCharacterEncoding(sp.getString("characterEncoding", "IBM437"));
        setEscPosCharSize(sp.getInt("escPosCharSize", 0));
        setEscPosCharTable(sp.getInt("escPosCharTable", 0));
        setCutterCmds(sp.getString("cutterCmds", "1B,64,05,1D,56,01"));
        setDrawerCmds(sp.getString("drawerCmds", "1B,70,00,19,FA"));
        setAuthorizeNetLogin(sp.getString("authorizelogin", StringUtils.EMPTY));
        setAuthorizeNetPwd(sp.getString("authorizepwd", StringUtils.EMPTY));
        this.shopName = sp.getString("shopName", StringUtils.EMPTY);
        this.invoiceTitle = sp.getString("invoiceTitle", c.getResources().getString(R.string.print_invoice_title));
        setNrOfTables(sp.getInt("nrOfTables", 2));
        this.gAccount = sp.getString("gAccount", StringUtils.EMPTY);
        this.stripeKey = sp.getString("stripekey", StringUtils.EMPTY);
        this.password = sp.getString("password", StringUtils.EMPTY);
        this.checkPassword = sp.getBoolean("checkPassword", false);
        this.usTaxMode = !sp.getBoolean("exclusiveTaxMode", false);
        this.maxPrintColumnNr = sp.getInt("maxPrintColumnNr", 32);
        this.invoicingInfos = sp.getString("wireTransferInfos", StringUtils.EMPTY);
        List<String> defaultNames = new ArrayList();
        for (i = 0; i < this.nrOfTables; i++) {
            defaultNames.add("Table " + (i + 1));
        }
        String namesStr = sp.getString("tableNames", StringUtils.EMPTY);
        this.tableNames.clear();
        if (namesStr.equals(StringUtils.EMPTY)) {
            this.tableNames.addAll(defaultNames);
        } else {
            Collections.addAll(this.tableNames, namesStr.split(Global.COMMA));
        }
        setBluetoothPrinterAddress(sp.getString("btAdr", StringUtils.EMPTY));
        setBluetoothPrinterName(sp.getString("btName", StringUtils.EMPTY));
        setKitchenDisplayHost(sp.getString("kitchenDisplayAdr", StringUtils.EMPTY));
        this.proVersion = sp.getBoolean("proVersion", false);
        this.csvFeature = sp.getBoolean("csvFeature", false);
        this.paypalFeature = sp.getBoolean("paypalFeature", false);
        this.gastroFeature = sp.getBoolean("gastroFeature", false);
        this.chartsFeature = sp.getBoolean("chartsFeature", false);
        this.driveFeature = sp.getBoolean("driveFeature", false);
        this.pwdFeature = sp.getBoolean("pwdFeature", false);
        try {
            this.minStockValue = new BigDecimal(sp.getString("minStockValue", "5"));
        } catch (Exception e) {
            this.minStockValue = new BigDecimal("5");
        }
        String name = sp.getString("locale", StringUtils.EMPTY);
        if (name.equals(StringUtils.EMPTY)) {
            this.locale = Locale.getDefault();
        } else {
            Locale[] locales = Locale.getAvailableLocales();
            i = 0;
            while (i < locales.length && !locales[i].getDisplayName().equals(name)) {
                i++;
            }
            if (i < locales.length) {
                this.locale = locales[i];
            } else {
                this.locale = Locale.getDefault();
            }
        }
        this.currencySymbol = sp.getString("manualCurrencySymbol", null);
        String appDataDir = StringUtils.EMPTY;
        if (this.context.getExternalFilesDir(null) != null) {
            appDataDir = this.context.getExternalFilesDir(null).getPath();
        } else if (this.context.getFilesDir() != null) {
            appDataDir = this.context.getFilesDir().getPath();
        }
        setFileslocation(sp.getString("fileslocation", appDataDir));
    }

    private boolean checkNull() {
        if (this.locale == null || this.shopText == null || this.printer == null || this.paypalAccount == null || this.shopName == null || this.invoiceTitle == null || this.password == null || this.invoicingInfos == null) {
            return true;
        }
        return false;
    }

    public void storeSettings(SharedPreferences sp) {
        if (!checkNull()) {
            Editor editor = sp.edit();
            editor.putBoolean("firstStart", this.firstStart);
            if (this.locale != null) {
                editor.putString("locale", this.locale.getDisplayName());
            }
            if (this.currencySymbol != null) {
                editor.putString("manualCurrencySymbol", this.currencySymbol);
            }
            editor.putInt("lastUsedPaymentIndex", getLastUsedPaymentIndex());
            editor.putString("shopText", getShopText());
            editor.putBoolean("autoBackup", isAutoBackup());
            editor.putInt("backupInterval", getBackupInterval());
            editor.putBoolean("escPos", isEscPos());
            editor.putString("characterEncoding", getCharacterEncoding());
            editor.putInt("escPosCharSize", getEscPosCharSize());
            editor.putInt("escPosCharTable", getEscPosCharTable());
            editor.putString("drawerCmds", getDrawerCmds());
            editor.putString("cutterCmds", getCutterCmds());
            editor.putString("invoiceFooter", getInvoiceFooter());
            editor.putString("printerIP", getPrinter());
            editor.putInt("posId", this.pos_id);
            editor.putString("paypalAccount", this.paypalAccount);
            editor.putString("bitcoin", this.bitcoinAddress);
            editor.putString("authorizelogin", getAuthorizeNetLogin());
            editor.putString("authorizepwd", getAuthorizeNetPwd());
            editor.putString("shopName", this.shopName);
            editor.putInt("nrOfTables", getNrOfTables());
            editor.putString("invoiceTitle", getInvoiceTitle());
            editor.putBoolean("printTableOnInvoice", isPrintTableOnInvoice());
            editor.putBoolean("printCallNumberOnInvoice", isPrintCallNumberOnInvoice());
            editor.putBoolean("checkoutPrint", isCheckoutPrint());
            editor.putBoolean("checkoutImagePrint", isCheckoutImagePrint());
            editor.putBoolean("checkoutPdfPrint", isCheckoutPdfPrint());
            editor.putInt("invoicePrintMultiply", getInvoicePrintMultiply());
            editor.putString("stripekey", this.stripeKey);
            editor.putString("password", this.password);
            editor.putBoolean("checkPassword", isCheckPassword());
            editor.putBoolean("exclusiveTaxMode", !isUSTaxMode());
            editor.putInt("maxPrintColumnNr", getMaxPrintColumnNr());
            editor.putString("minStockValue", getMinStockValue().toString());
            editor.putString("wireTransferInfos", getInvoicingInfos());
            editor.putString("btName", getBluetoothPrinterName());
            editor.putString("btAdr", getBluetoothPrinterAddress());
            editor.putString("kitchenDisplayAdr", getKitchenDisplayHost());
            StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
            for (int i = 0; i < this.tableNames.size(); i++) {
                sb.append((String) this.tableNames.get(i));
                if (i < this.tableNames.size() - 1) {
                    sb.append(Global.COMMA);
                }
            }
            editor.putString("tableNames", sb.toString());
            editor.putBoolean("proVersion", this.proVersion);
            editor.putBoolean("paypalFeature", this.paypalFeature);
            editor.putBoolean("csvFeature", this.csvFeature);
            editor.putBoolean("gastroFeature", this.gastroFeature);
            editor.putBoolean("chartsFeature", this.chartsFeature);
            editor.putBoolean("driveFeature", this.driveFeature);
            editor.putBoolean("pwdFeature", this.pwdFeature);
            editor.putString("fileslocation", getFileslocation());
            editor.commit();
        }
    }

    public String getPrinter() {
        return this.printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public int getPrinterPort() {
        return this.printerPort;
    }

    public void setPrinterPort(int printerPort) {
        this.printerPort = printerPort;
    }

    public boolean isFirstStart() {
        return this.firstStart;
    }

    public void setFirstStart(boolean firstStart) {
        this.firstStart = firstStart;
    }

    public String getPaypalAccount() {
        return this.paypalAccount;
    }

    public void setPaypalAccount(String paypalAccount) {
        this.paypalAccount = paypalAccount;
    }

    public String getBitcoinAddress() {
        return this.bitcoinAddress;
    }

    public void setBitcoinAddress(String bitcoinAddress) {
        this.bitcoinAddress = bitcoinAddress;
    }

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public boolean isPaypalInitialized() {
        return this.paypalInitialized;
    }

    public void setPaypalInitialized(boolean paypalInitialized) {
        this.paypalInitialized = paypalInitialized;
    }

    public int getNrOfTables() {
        return this.nrOfTables;
    }

    public void setNrOfTables(int nrOfTables) {
        this.nrOfTables = nrOfTables;
        int act = getTableNames().size();
        if (nrOfTables > act) {
            int more = nrOfTables - act;
            for (int i = 0; i < more; i++) {
                this.tableNames.add("Table " + ((act + i) + 1));
            }
        }
        Model.getInstance().createPositionLists(nrOfTables);
    }

    public boolean isCheckPassword() {
        return this.checkPassword;
    }

    public void setCheckPassword(boolean checkPassword) {
        this.checkPassword = checkPassword;
    }

    public void setPassword(String password) {
        try {
            password = password.trim();
            MessageDigest md = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            md.update(password.getBytes(), 0, password.length());
            this.password = new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
        }
    }

    public boolean checkPassword(String pwd) {
        try {
            pwd = pwd.trim();
            MessageDigest md = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            md.update(pwd.getBytes(), 0, pwd.length());
            if (new BigInteger(1, md.digest()).toString(16).equals(this.password)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String getKitchenDisplayHost() {
        return this.kitchenDisplayHost;
    }

    public void setKitchenDisplayHost(String kitchenDisplayHost) {
        this.kitchenDisplayHost = kitchenDisplayHost;
    }

    public int getKitchenDisplayPort() {
        return this.kitchenDisplayPort;
    }

    public void setKitchenDisplayPort(int kitchenDisplayPort) {
        this.kitchenDisplayPort = kitchenDisplayPort;
    }

    public String getStripeKey() {
        return this.stripeKey;
    }

    public void setStripeKey(String key) {
        this.stripeKey = key;
    }

    public boolean isPrintTableOnInvoice() {
        return this.printTableOnInvoice;
    }

    public void setPrintTableOnInvoice(boolean printTableOnInvoice) {
        this.printTableOnInvoice = printTableOnInvoice;
    }

    public boolean isPrintCallNumberOnInvoice() {
        return this.printCallNumberOnInvoice;
    }

    public void setPrintCallNumberOnInvoice(boolean printCallNumberOnInvoice) {
        this.printCallNumberOnInvoice = printCallNumberOnInvoice;
    }
}
