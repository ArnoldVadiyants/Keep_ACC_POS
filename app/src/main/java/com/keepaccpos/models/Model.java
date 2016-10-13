package com.keepaccpos.models;

import android.content.Context;
import android.util.Log;

import com.dynatrace.android.agent.Global;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.Checksum;

import at.smartlab.tshop.C0250R;
import at.smartlab.tshop.StartActivity;
import at.smartlab.tshop.bitcoin.BitcoinExchangeUtils;
import at.smartlab.tshop.log.CrashLog;
import at.smartlab.tshop.persist.AccountDataSource;
import at.smartlab.tshop.persist.DiscountDataSource;
import at.smartlab.tshop.persist.InvoiceDataSource;
import at.smartlab.tshop.persist.InvoicePositionDataSource;
import at.smartlab.tshop.persist.PaymentMethodDataSource;
import at.smartlab.tshop.persist.ProductDataSource;
import at.smartlab.tshop.persist.StockDataSource;
import at.smartlab.tshop.persist.TaxDataSource;
import at.smartlab.tshop.persist.ZReportCountDataSource;
import at.smartlab.tshop.persist.ZReportDataSource;
import at.smartlab.tshop.print.PrintAsyncTask;
import at.smartlab.tshop.ui.ImageLoader;

public class Model {
    private static AccountDataSource accountDB;
    private static Context context;
    private static DiscountDataSource discountDB;
    private static ImageLoader imgLoader;
    private static final Model instance;
    private static InvoiceDataSource invoiceDB;
    private static PaymentMethodDataSource pMethodDB;
    private static InvoicePositionDataSource positionDB;
    private static ProductDataSource productDB;
    private static StockDataSource stockDB;
    private static TaxDataSource taxDB;
    private static ZReportDataSource zrDS;
    private final List<CartListChangedListener> cartListChangedListeners;
    private final ArrayList<Discount> discounts;
    private int index;
    private final ArrayList<Invoice> invoices;
    private boolean isInSplitMode;
    private boolean modelReady;
    private final ArrayList<ArrayList<InvoicePositionActive>> openInvoicesLists;
    private ProductSelectedListener productSelectedListener;
    private final HashMap<String, Product> products;
    private final Settings settings;
    private final Dictionary<String, StockCategory> stock;
    private StockLowListener stockLowListener;
    private final List<TableSelectionListener> tableSelectionListeners;
    private final ArrayList<Tax> taxes;
    private final StringBuffer workingCmd;
    private final ZReport zReport;

    public interface TableSelectionListener {
        void tableSelected(int i);
    }

    public interface ProductSelectedListener {
        void productSelected(Product product);
    }

    public interface CartListChangedListener {
        void cartChanged();

        void positionDeleted(int i);
    }

    static {
        instance = new Model();
    }

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        this.zReport = new ZReport();
        this.invoices = new ArrayList();
        this.openInvoicesLists = new ArrayList();
        this.index = 0;
        this.isInSplitMode = false;
        this.taxes = new ArrayList();
        this.discounts = new ArrayList();
        this.stock = new Hashtable();
        this.products = new HashMap();
        this.settings = new Settings();
        this.workingCmd = new StringBuffer(StringUtils.EMPTY);
        this.modelReady = false;
        this.tableSelectionListeners = new ArrayList();
        this.cartListChangedListeners = new ArrayList();
        initStock();
    }

    public void addTableSelectionListener(TableSelectionListener l) {
        this.tableSelectionListeners.add(l);
    }

    public void removeTableSelectionListener(TableSelectionListener l) {
        this.tableSelectionListeners.remove(l);
    }

    private void notifyTableSelectionChanged(int table) {
        try {
            for (TableSelectionListener l : this.tableSelectionListeners) {
                if (l != null) {
                    l.tableSelected(table);
                }
            }
        } catch (Exception e) {
        }
    }

    public void addCartListChangedListener(CartListChangedListener l) {
        this.cartListChangedListeners.add(l);
    }

    public void removeCartListChangedListener(CartListChangedListener l) {
        this.cartListChangedListeners.remove(l);
    }

    private void notifyCartListPositionDeleted(int position) {
        try {
            for (CartListChangedListener l : this.cartListChangedListeners) {
                if (l != null) {
                    l.positionDeleted(position);
                    l.cartChanged();
                }
            }
        } catch (Exception e) {
        }
    }

    private void notifyCartPositionChanged() {
        try {
            for (CartListChangedListener l : this.cartListChangedListeners) {
                if (l != null) {
                    l.cartChanged();
                }
            }
        } catch (Exception e) {
        }
    }

    public void setProductSelectedListener(ProductSelectedListener l) {
        this.productSelectedListener = l;
    }

    public void removeProductSelectedListener(ProductSelectedListener l) {
        this.productSelectedListener = null;
    }

    public void notifyProductSelected(Product p) {
        try {
            if (this.productSelectedListener != null) {
                this.productSelectedListener.productSelected(p);
            }
        } catch (Exception e) {
        }
    }

    public static void openDatabases(Context ctx) {
        context = ctx.getApplicationContext();
        imgLoader = new ImageLoader(context);
        taxDB = new TaxDataSource(context);
        taxDB.open();
        discountDB = new DiscountDataSource(context);
        discountDB.open();
        accountDB = new AccountDataSource(context);
        accountDB.open();
        productDB = new ProductDataSource(context);
        productDB.open();
        stockDB = new StockDataSource(context);
        stockDB.open();
        pMethodDB = new PaymentMethodDataSource(context);
        pMethodDB.open();
        positionDB = new InvoicePositionDataSource(context);
        positionDB.open();
        invoiceDB = new InvoiceDataSource(context);
        invoiceDB.open();
        zrDS = new ZReportDataSource(context);
        zrDS.open();
    }

    public ImageLoader getImageLoader() {
        return imgLoader;
    }

    public TaxDataSource getTaxDatabase() {
        return taxDB;
    }

    public DiscountDataSource getDiscountDatabase() {
        return discountDB;
    }

    public AccountDataSource getAccountDatabase() {
        return accountDB;
    }

    public ProductDataSource getProductDatabase() {
        return productDB;
    }

    public InvoiceDataSource getInvoiceDatabase() {
        return invoiceDB;
    }

    public InvoicePositionDataSource getInvoicePositionDatabase() {
        return positionDB;
    }

    public List<Stock> getStockEntries() {
        return stockDB.getAllStocks();
    }

    public Dictionary<Long, Tax> getAllTaxesAsDictionary() {
        Dictionary<Long, Tax> taxDict = new Hashtable();
        for (int i = 0; i < this.taxes.size(); i++) {
            taxDict.put(Long.valueOf(((Tax) this.taxes.get(i)).getId()), this.taxes.get(i));
        }
        return taxDict;
    }

    public Dictionary<Long, Discount> getAllDiscountsAsDictionary() {
        Dictionary<Long, Discount> disDict = new Hashtable();
        for (int i = 0; i < this.discounts.size(); i++) {
            disDict.put(Long.valueOf(((Discount) this.discounts.get(i)).getId()), this.discounts.get(i));
        }
        return disDict;
    }

    public HashMap<String, Product> getAllProductsAsDictionary() {
        return this.products;
    }

    public String[] getAllCategories() {
        String[] cs = new String[this.stock.size()];
        Enumeration<String> e = this.stock.keys();
        for (int i = 0; i < cs.length; i++) {
            cs[i] = (String) e.nextElement();
        }
        return cs;
    }

    public List<PaymentMethod> getAllUserPaymentMethods() {
        if (pMethodDB != null) {
            return pMethodDB.getAllPaymentMethods(true);
        }
        return new ArrayList();
    }

    public List<PaymentMethod> getEnabledPaymentMethods() {
        List<PaymentMethod> pms = new ArrayList();
        if (context != null) {
            pms.add(new PaymentMethod(0, context.getString(C0250R.string.cash), StringUtils.EMPTY, true, true));
            pms.add(new PaymentMethod(1, context.getString(C0250R.string.creditcard), StringUtils.EMPTY, true, true));
            pms.add(new PaymentMethod(2, context.getString(C0250R.string.card), StringUtils.EMPTY, true, true));
            if (getInstance().getSettings().hasPaypalFeature() && getInstance().getSettings().isPaypalInitialized()) {
                pms.add(new PaymentMethod(3, context.getString(C0250R.string.paypal), StringUtils.EMPTY, true, true));
            }
            pms.add(new PaymentMethod(4, context.getString(C0250R.string.check), StringUtils.EMPTY, true, true));
            pms.add(new PaymentMethod(5, context.getString(C0250R.string.paypalhere), StringUtils.EMPTY, true, true));
            pms.add(new PaymentMethod(11, context.getString(C0250R.string.stripe), StringUtils.EMPTY, true, true));
            pms.add(new PaymentMethod(6, context.getString(C0250R.string.invoice), StringUtils.EMPTY, true, true));
            NumberFormat formatter = getInstance().getSettings().getNumberFormatter();
            if (BitcoinExchangeUtils.contains(getInstance().getSettings().getCurrencySymbol())) {
                pms.add(new PaymentMethod(7, context.getString(C0250R.string.bitcoin), StringUtils.EMPTY, true, true));
            }
            pms.add(new PaymentMethod(8, context.getString(C0250R.string.authorizenet), StringUtils.EMPTY, true, true));
            pms.add(new PaymentMethod(9, "payleven", StringUtils.EMPTY, true, true));
            pms.add(new PaymentMethod(10, "Yodo", StringUtils.EMPTY, true, true));
        }
        if (pMethodDB != null) {
            for (PaymentMethod p : pMethodDB.getAllPaymentMethods(true)) {
                if (p != null && p.isEnabled()) {
                    pms.add(p);
                }
            }
        }
        return pms;
    }

    public void addUserPaymentMethod(String methodName, String intentStr) {
        if (pMethodDB != null) {
            pMethodDB.createPaymentMethod(methodName, intentStr, true);
        }
    }

    public void deleteUserPaymentMethod(PaymentMethod pm) {
        pMethodDB.deleteMethod(pm);
    }

    public static void closeDatabases() {
        taxDB.close();
        discountDB.close();
        invoiceDB.close();
        accountDB.close();
        positionDB.close();
        productDB.close();
        stockDB.close();
        pMethodDB.close();
        zrDS.close();
    }

    public static boolean isDBReady() {
        if (taxDB == null || discountDB == null || invoiceDB == null || accountDB == null || positionDB == null || productDB == null || stockDB == null || pMethodDB == null || !taxDB.isOpen() || !discountDB.isOpen() || !invoiceDB.isOpen() || !accountDB.isOpen() || !positionDB.isOpen() || !productDB.isOpen() || !stockDB.isOpen() || !pMethodDB.isOpen()) {
            return false;
        }
        return true;
    }

    public static InvoiceDataSource getInvoiceDB() {
        return invoiceDB;
    }

    protected void initStock() {
        ((Hashtable) this.stock).clear();
        this.stock.put(Global.SLASH, new StockCategory("..", null));
    }

    public long getNextInvoiceNumber() {
        long seq_nr = 0;
        int offs = 0;
        List<Invoice> i = invoiceDB.getNextInvoices(1, 0);
        boolean found = false;
        while (i.size() > 0 && !found) {
            String n = Long.toString(((Invoice) i.get(0)).getInvoiceNr());
            if (getInstance().getSettings().getPos_id() == Integer.parseInt(n.substring(n.length() - 7, n.length() - 5))) {
                found = true;
            } else {
                offs++;
                i = invoiceDB.getNextInvoices(1, offs);
            }
        }
        if (i.size() > 0) {
            n = Long.toString(((Invoice) i.get(0)).getInvoiceNr());
            seq_nr = Long.parseLong(n.substring(n.length() - 5));
            if (seq_nr == 99999) {
                seq_nr = 0;
            }
        }
        seq_nr++;
        StringBuilder sb = new StringBuilder();
        Calendar now = Calendar.getInstance();
        sb.append(String.format(Locale.US, "%04d", new Object[]{Integer.valueOf(now.get(1))}));
        sb.append(String.format(Locale.US, "%02d", new Object[]{Integer.valueOf(now.get(2) + 1)}));
        sb.append(String.format(Locale.US, "%02d", new Object[]{Integer.valueOf(now.get(5))}));
        sb.append(String.format(Locale.US, "%02d", new Object[]{Integer.valueOf(getSettings().getPos_id())}));
        sb.append(String.format(Locale.US, "%05d", new Object[]{Long.valueOf(seq_nr)}));
        return Long.parseLong(sb.toString());
    }

    public void deleteAllProducts() {
        try {
            this.products.clear();
            initStock();
            productDB.beginTransaction();
            productDB.deleteAll();
            productDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            CrashLog.getInstance().logException(e);
        } finally {
            productDB.endTransaction();
        }
    }

    public void deleteProduct(String id) {
        Product p = (Product) this.products.get(id);
        if (p != null) {
            String cat = p.getCategory();
            this.products.remove(id);
            productDB.deleteProduct(p);
            if (cat != null) {
                StockCategory category;
                if (cat.equals(StringUtils.EMPTY)) {
                    category = getInstance().getRootCategory();
                } else {
                    category = (StockCategory) this.stock.get(cat);
                }
                if (category != null) {
                    category.getStock().remove(p);
                    if (category.getStock().size() <= 1) {
                        StockCategory parent = category.getParent();
                        if (parent != null) {
                            parent.getStock().remove(category);
                        }
                    }
                }
            }
        }
    }

    public void updateProduct(Product p) {
        if (isDBReady()) {
            productDB.updateProduct(p);
        }
    }

    public void registerStock(Product p, String supplier, BigDecimal qty, BigDecimal costPrice) {
        try {
            productDB.beginTransaction();
            stockDB.createStock(p, supplier, qty, costPrice);
            if (p.getStockQty().compareTo(BigDecimal.ZERO) > 0) {
                p.setCost_price(qty.multiply(costPrice).add(p.getStockQty().multiply(p.getCost_price())).divide(qty.add(p.getStockQty()), 10, RoundingMode.HALF_DOWN));
            } else {
                p.setCost_price(costPrice);
            }
            p.incStockQty(qty);
            productDB.updateProduct(p);
            productDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            CrashLog.getInstance().logException(e);
        } finally {
            productDB.endTransaction();
        }
    }

    public void deleteAllInvoices() {
        List<Invoice> list = invoiceDB.getAllInvoices();
        for (int i = 0; i < list.size(); i++) {
            deleteInvoice((Invoice) list.get(i));
        }
        getInstance().getInvoices().clear();
    }

    public void deleteInvoice(Invoice i) {
        if (i != null) {
            try {
                getAccountDatabase().beginTransaction();
                getInvoiceDatabase().beginTransaction();
                getInvoicePositionDatabase().beginTransaction();
                if (!(i.getCheckoutStatus() == 12 || i.getCheckoutStatus() == 11)) {
                    getAccountDatabase().updateAccount(GregorianCalendar.getInstance(), i.getTotal().negate(), i.getSubtotal().negate(), i.getTax().negate(), i.getDiscount().negate(), i.getCostPrice().negate());
                    getInstance().refillStock(i);
                }
                getInvoices().remove(i);
                long iNr = i.getInvoiceNr();
                getInvoiceDatabase().deleteInvoice(i);
                getInvoicePositionDatabase().deleteInvoicePositions(iNr);
                getAccountDatabase().setTransactionSuccessful();
                getInvoiceDatabase().setTransactionSuccessful();
                getInvoicePositionDatabase().setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
                CrashLog.getInstance().logException(e);
            } finally {
                getAccountDatabase().endTransaction();
                getInvoiceDatabase().endTransaction();
                getInvoicePositionDatabase().endTransaction();
            }
        }
    }

    public void createPositionLists(int nrOfOpenInvoices) {
        if (nrOfOpenInvoices > 0) {
            this.openInvoicesLists.clear();
            for (int i = 0; i < nrOfOpenInvoices; i++) {
                this.openInvoicesLists.add(new ArrayList());
            }
            this.index = 0;
        }
    }

    public void addNewInvoicePosition(InvoicePositionActive pos) {
        if (pos.getQty().compareTo(BigDecimal.ZERO) != 0) {
            if (pos.getQty().compareTo(pos.getProduct().getStockQty()) <= 0 || pos.getProduct().hasAttribute(4)) {
                synchronized (getClass()) {
                    getActualPositionList().add(0, pos);
                    pos.getProduct().decStockQty(pos.getQty());
                    notifyCartPositionChanged();
                }
            }
        }
    }

    public void addNewVariableInvoicePosition(InvoicePositionActive pos) {
        if (pos.getQty().compareTo(BigDecimal.ZERO) != 0) {
            synchronized (getClass()) {
                getActualPositionList().add(pos);
                notifyCartPositionChanged();
            }
        }
    }

    public void deleteInvoicePosition(int index) {
        synchronized (getClass()) {
            if (index < getActualPositionList().size()) {
                InvoicePositionActive pos = (InvoicePositionActive) getActualPositionList().get(index);
                if (pos.getProduct() != null) {
                    Product p = getProduct(pos.getProduct().getId());
                    if (p != null) {
                        p.incStockQty(pos.getQty());
                    }
                }
                getActualPositionList().remove(index);
                notifyCartListPositionDeleted(index);
            }
        }
    }

    public void quickCashCheckout() {
        Invoice invoice;
        Customer c = new Customer(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
        if (isInSplitMode()) {
            invoice = new Invoice(getNextInvoiceNumber(), new Date(), getSplitedPositions(), c);
        } else {
            invoice = new Invoice(getNextInvoiceNumber(), new Date(), getPositions(), c);
        }
        invoice.setCheckoutType(0);
        invoice.setCheckoutStatus(10);
        invoice.setGivenCash(invoice.getTotal());
        invoice.setReturnedCash(BigDecimal.ZERO);
        storeInvoice(invoice);
        new PrintAsyncTask().execute(new Invoice[]{invoice});
        try {
            Services.getInstance().storeInvoiceAsPDF(invoice, Long.toString(invoice.getInvoiceNr()), new File(getInstance().getSettings().getFileslocation()));
        } catch (Exception e) {
        }
        if (isInSplitMode()) {
            Iterator it = getInstance().getSplitedPositions().iterator();
            while (it.hasNext()) {
                getInstance().getPositions().remove((InvoicePositionActive) it.next());
            }
        } else {
            getInstance().getPositions().clear();
        }
        setIsInSplitMode(false);
        notifyCartPositionChanged();
    }

    public ArrayList<InvoicePositionActive> getSplitedPositions() {
        ArrayList<InvoicePositionActive> resL = new ArrayList();
        Iterator it = getActualPositionList().iterator();
        while (it.hasNext()) {
            InvoicePositionActive p = (InvoicePositionActive) it.next();
            if (p.isSelected()) {
                resL.add(p);
            }
        }
        return resL;
    }

    public ArrayList<InvoicePositionActive> getPositions() {
        return getActualPositionList();
    }

    public StockCategory getRootCategory() {
        return (StockCategory) this.stock.get(Global.SLASH);
    }

    public StockCategory getCloseSaleCategory(String catTitle, int attribute) {
        StockCategory closeSaleCat = new StockCategory(catTitle, (StockCategory) this.stock.get(Global.SLASH));
        for (Product p : this.products.values()) {
            if (p.hasAttribute(attribute) && p.getStockQty().compareTo(BigDecimal.ZERO) > 0) {
                closeSaleCat.getStock().add(p);
            }
        }
        return closeSaleCat;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public ArrayList<Invoice> getInvoices() {
        return this.invoices;
    }

    public List<Invoice> getInvoices(int limit, int offs) {
        return invoiceDB.getNextInvoices(limit, offs);
    }

    public List<Invoice> getInvoicesOutOfSync() {
        return invoiceDB.getSyncInvoices();
    }

    public boolean isModelReady() {
        return this.modelReady;
    }

    public void setModelReady(boolean modelReady) {
        this.modelReady = modelReady;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void modifyTax(long r8, java.lang.String r10, java.math.BigDecimal r11) {
        /*
        r7 = this;
        r3 = r7.getClass();
        monitor-enter(r3);
        r0 = 0;
        r1 = 0;
    L_0x0007:
        r2 = r7.taxes;	 Catch:{ all -> 0x003b }
        r2 = r2.size();	 Catch:{ all -> 0x003b }
        if (r1 >= r2) goto L_0x002a;
    L_0x000f:
        r2 = r7.taxes;	 Catch:{ all -> 0x003b }
        r2 = r2.get(r1);	 Catch:{ all -> 0x003b }
        r2 = (at.smartlab.tshop.model.Tax) r2;	 Catch:{ all -> 0x003b }
        r4 = r2.getId();	 Catch:{ all -> 0x003b }
        r2 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r2 != 0) goto L_0x0027;
    L_0x001f:
        r2 = r7.taxes;	 Catch:{ all -> 0x003b }
        r0 = r2.get(r1);	 Catch:{ all -> 0x003b }
        r0 = (at.smartlab.tshop.model.Tax) r0;	 Catch:{ all -> 0x003b }
    L_0x0027:
        r1 = r1 + 1;
        goto L_0x0007;
    L_0x002a:
        if (r0 == 0) goto L_0x0039;
    L_0x002c:
        r0.setPercent(r11);	 Catch:{ all -> 0x003b }
        r0.setName(r10);	 Catch:{ all -> 0x003b }
        r2 = r7.getTaxDatabase();	 Catch:{ all -> 0x003b }
        r2.updateTax(r0);	 Catch:{ all -> 0x003b }
    L_0x0039:
        monitor-exit(r3);	 Catch:{ all -> 0x003b }
        return;
    L_0x003b:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x003b }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: at.smartlab.tshop.model.Model.modifyTax(long, java.lang.String, java.math.BigDecimal):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void modifyDiscount(long r8, java.lang.String r10, java.math.BigDecimal r11) {
        /*
        r7 = this;
        r3 = r7.getClass();
        monitor-enter(r3);
        r0 = 0;
        r1 = 0;
    L_0x0007:
        r2 = r7.discounts;	 Catch:{ all -> 0x003b }
        r2 = r2.size();	 Catch:{ all -> 0x003b }
        if (r1 >= r2) goto L_0x002a;
    L_0x000f:
        r2 = r7.discounts;	 Catch:{ all -> 0x003b }
        r2 = r2.get(r1);	 Catch:{ all -> 0x003b }
        r2 = (at.smartlab.tshop.model.Discount) r2;	 Catch:{ all -> 0x003b }
        r4 = r2.getId();	 Catch:{ all -> 0x003b }
        r2 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r2 != 0) goto L_0x0027;
    L_0x001f:
        r2 = r7.discounts;	 Catch:{ all -> 0x003b }
        r0 = r2.get(r1);	 Catch:{ all -> 0x003b }
        r0 = (at.smartlab.tshop.model.Discount) r0;	 Catch:{ all -> 0x003b }
    L_0x0027:
        r1 = r1 + 1;
        goto L_0x0007;
    L_0x002a:
        if (r0 == 0) goto L_0x0039;
    L_0x002c:
        r0.setPercent(r11);	 Catch:{ all -> 0x003b }
        r0.setName(r10);	 Catch:{ all -> 0x003b }
        r2 = r7.getDiscountDatabase();	 Catch:{ all -> 0x003b }
        r2.updateDiscount(r0);	 Catch:{ all -> 0x003b }
    L_0x0039:
        monitor-exit(r3);	 Catch:{ all -> 0x003b }
        return;
    L_0x003b:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x003b }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: at.smartlab.tshop.model.Model.modifyDiscount(long, java.lang.String, java.math.BigDecimal):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addTax(java.lang.String r6, java.math.BigDecimal r7) {
        /*
        r5 = this;
        r4 = r5.getClass();
        monitor-enter(r4);
        r0 = 0;
        r1 = 0;
    L_0x0007:
        r3 = r5.taxes;	 Catch:{ all -> 0x0048 }
        r3 = r3.size();	 Catch:{ all -> 0x0048 }
        if (r1 >= r3) goto L_0x002c;
    L_0x000f:
        r3 = r5.taxes;	 Catch:{ all -> 0x0048 }
        r3 = r3.get(r1);	 Catch:{ all -> 0x0048 }
        r3 = (at.smartlab.tshop.model.Tax) r3;	 Catch:{ all -> 0x0048 }
        r3 = r3.getName();	 Catch:{ all -> 0x0048 }
        r3 = r3.equals(r6);	 Catch:{ all -> 0x0048 }
        if (r3 == 0) goto L_0x0029;
    L_0x0021:
        r3 = r5.taxes;	 Catch:{ all -> 0x0048 }
        r0 = r3.get(r1);	 Catch:{ all -> 0x0048 }
        r0 = (at.smartlab.tshop.model.Tax) r0;	 Catch:{ all -> 0x0048 }
    L_0x0029:
        r1 = r1 + 1;
        goto L_0x0007;
    L_0x002c:
        if (r0 == 0) goto L_0x003a;
    L_0x002e:
        r0.setPercent(r7);	 Catch:{ all -> 0x0048 }
        r3 = r5.getTaxDatabase();	 Catch:{ all -> 0x0048 }
        r3.updateTax(r0);	 Catch:{ all -> 0x0048 }
    L_0x0038:
        monitor-exit(r4);	 Catch:{ all -> 0x0048 }
        return;
    L_0x003a:
        r3 = r5.getTaxDatabase();	 Catch:{ all -> 0x0048 }
        r2 = r3.createTax(r6, r7);	 Catch:{ all -> 0x0048 }
        r3 = r5.taxes;	 Catch:{ all -> 0x0048 }
        r3.add(r2);	 Catch:{ all -> 0x0048 }
        goto L_0x0038;
    L_0x0048:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0048 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: at.smartlab.tshop.model.Model.addTax(java.lang.String, java.math.BigDecimal):void");
    }

    public void addTax(long id, String name, BigDecimal percent) {
        synchronized (getClass()) {
            this.taxes.add(getTaxDatabase().createTax(id, name, percent));
        }
    }

    public void deleteTax(Tax tax) {
        getTaxDatabase().deleteTax(tax);
        getTaxes().remove(tax);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addDiscount(java.lang.String r6, java.math.BigDecimal r7) {
        /*
        r5 = this;
        r4 = r5.getClass();
        monitor-enter(r4);
        r1 = 0;
        r2 = 0;
    L_0x0007:
        r3 = r5.discounts;	 Catch:{ all -> 0x0048 }
        r3 = r3.size();	 Catch:{ all -> 0x0048 }
        if (r2 >= r3) goto L_0x002c;
    L_0x000f:
        r3 = r5.discounts;	 Catch:{ all -> 0x0048 }
        r3 = r3.get(r2);	 Catch:{ all -> 0x0048 }
        r3 = (at.smartlab.tshop.model.Discount) r3;	 Catch:{ all -> 0x0048 }
        r3 = r3.getName();	 Catch:{ all -> 0x0048 }
        r3 = r3.equals(r6);	 Catch:{ all -> 0x0048 }
        if (r3 == 0) goto L_0x0029;
    L_0x0021:
        r3 = r5.discounts;	 Catch:{ all -> 0x0048 }
        r1 = r3.get(r2);	 Catch:{ all -> 0x0048 }
        r1 = (at.smartlab.tshop.model.Discount) r1;	 Catch:{ all -> 0x0048 }
    L_0x0029:
        r2 = r2 + 1;
        goto L_0x0007;
    L_0x002c:
        if (r1 == 0) goto L_0x003a;
    L_0x002e:
        r1.setPercent(r7);	 Catch:{ all -> 0x0048 }
        r3 = r5.getDiscountDatabase();	 Catch:{ all -> 0x0048 }
        r3.updateDiscount(r1);	 Catch:{ all -> 0x0048 }
    L_0x0038:
        monitor-exit(r4);	 Catch:{ all -> 0x0048 }
        return;
    L_0x003a:
        r3 = r5.getDiscountDatabase();	 Catch:{ all -> 0x0048 }
        r0 = r3.createDiscount(r6, r7);	 Catch:{ all -> 0x0048 }
        r3 = r5.discounts;	 Catch:{ all -> 0x0048 }
        r3.add(r0);	 Catch:{ all -> 0x0048 }
        goto L_0x0038;
    L_0x0048:
        r3 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0048 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: at.smartlab.tshop.model.Model.addDiscount(java.lang.String, java.math.BigDecimal):void");
    }

    public void addDiscount(long id, String name, BigDecimal percent) {
        synchronized (getClass()) {
            this.discounts.add(getDiscountDatabase().createDiscount(id, name, percent));
        }
    }

    public void deleteDiscount(Discount d) {
        getDiscountDatabase().deleteDiscount(d);
        getDiscounts().remove(d);
    }

    public Product addProduct(String id, String title, String descr, double price, double cost_price, double qty, Tax tax, Discount discount, String path, int attr, String image, ProductDataSource ds) {
        if (path == null || path.trim().equals(StringUtils.EMPTY)) {
            path = Global.SLASH;
        }
        long taxId = -1;
        if (tax != null) {
            taxId = tax.getId();
        }
        long discountId = -1;
        if (discount != null) {
            discountId = discount.getId();
        }
        Product p = productDB.createProduct(id, title, descr, price, cost_price, taxId, discountId, qty, path, attr, image);
        createCategoryPath(path).getStock().add(p);
        this.products.put(p.getId(), p);
        return p;
    }

    public Product createProduct(String id, String title, String descr, BigDecimal price, BigDecimal cost_price, BigDecimal qty, Tax tax, Discount discount, String path, int attr, String image) {
        if (path == null || path.trim().equals(StringUtils.EMPTY)) {
            path = Global.SLASH;
        }
        long taxId = -1;
        if (tax != null) {
            taxId = tax.getId();
        }
        long discountId = -1;
        if (discount != null) {
            discountId = discount.getId();
        }
        Product p = productDB.createProduct(id, title, descr, price, cost_price, taxId, discountId, qty, path, attr, image);
        createCategoryPath(path).getStock().add(p);
        this.products.put(p.getId(), p);
        return p;
    }

    public ArrayList<Tax> getTaxes() {
        return this.taxes;
    }

    public ArrayList<Discount> getDiscounts() {
        return this.discounts;
    }

    public void loadTaxes(Context c) {
        this.taxes.clear();
        List<Tax> list = taxDB.getAllTaxes();
        this.taxes.add(new Tax(Global.HYPHEN, new BigDecimal(0)));
        for (int i = 0; i < list.size(); i++) {
            this.taxes.add(list.get(i));
        }
    }

    public void loadDiscounts(Context c) {
        this.discounts.clear();
        List<Discount> list = discountDB.getAllDiscounts();
        this.discounts.add(new Discount(Global.HYPHEN, new BigDecimal(0)));
        for (int i = 0; i < list.size(); i++) {
            this.discounts.add(list.get(i));
        }
    }

    public void loadProducts(Context c) {
        initStock();
        if (productDB != null) {
            Set<String> categories = new HashSet();
            productDB.getAllProducts(categories, this.products);
            categories.add(StringUtils.EMPTY);
            categories.add(Global.SLASH);
            for (String category : categories) {
                StockCategory cat = createCategoryPath(category.trim());
                for (Product p : this.products.values()) {
                    if (p.getCategory().trim().equalsIgnoreCase(category)) {
                        cat.add(p);
                    }
                }
            }
            Log.d("TabShop", "root category size: " + ((StockCategory) this.stock.get(Global.SLASH)).getStock().size());
        }
    }

    protected StockCategory createCategoryPath(String path) {
        if (path == null || path.equals(StringUtils.EMPTY) || path.equals(Global.SLASH)) {
            return (StockCategory) this.stock.get(Global.SLASH);
        }
        if (path.endsWith(Global.SLASH)) {
            path = path.substring(0, path.length() - 1);
        }
        StockCategory cat = (StockCategory) this.stock.get(path.trim());
        if (cat != null) {
            return cat;
        }
        int l = path.lastIndexOf(47);
        StockCategory parent = createCategoryPath(path.substring(0, l + 1));
        cat = new StockCategory(path.substring(l + 1, path.length()), parent);
        parent.addSubcategory(cat);
        this.stock.put(path, cat);
        return cat;
    }

    public void undoInvoice(Invoice i, int newCheckoutState) {
        if (i.getCheckoutStatus() != 12 && i.getCheckoutStatus() != 11) {
            accountDB.updateAccount(GregorianCalendar.getInstance(), i.getTotal().negate(), i.getSubtotal().negate(), i.getTax().negate(), i.getDiscount().negate(), i.getCostPrice().negate());
            getInstance().refillStock(i);
            i.setCheckoutStatus(newCheckoutState);
            i.setSync(false);
            invoiceDB.updateInvoice(i);
            this.zReport.refundInvoice(i);
            try {
                this.zReport.save(zrDS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Invoice loadInvoice(long id) {
        return invoiceDB.loadInvoice(id);
    }

    public void storeInvoice(Invoice invoice) {
        try {
            invoiceDB.beginTransaction();
            positionDB.beginTransaction();
            accountDB.beginTransaction();
            productDB.beginTransaction();
            invoiceDB.createInvoice(invoice.getInvoiceNr(), invoice.getDate(), invoice.getCustomer().getName(), invoice.getCustomer().getAddress(), invoice.getCustomer().getEmail(), invoice.getCheckoutType(), invoice.getCheckoutStatus(), invoice.getGivenCash().toString(), invoice.getReturnedCash().toString(), Checksum.calcChecksum(invoice));
            for (int i = 0; i < invoice.getPositions().size(); i++) {
                InvoicePosition pos = (InvoicePosition) invoice.getPositions().get(i);
                Product p = pos.getProduct();
                if (!p.hasAttribute(16)) {
                    productDB.updateStock(p);
                    positionDB.createInvoicePosition(invoice.getInvoiceNr(), pos);
                } else if (this.products.get(p.getId()) == null) {
                    positionDB.createInvoicePosition(invoice.getInvoiceNr(), pos);
                    getInstance().createProduct(p.getId(), p.getTitle(), StringUtils.EMPTY, p.getPrice().negate(), BigDecimal.ZERO, BigDecimal.ONE, p.getTax(), p.getDiscount(), p.getCategory(), p.getAttribute(), null);
                } else {
                    positionDB.createInvoicePosition(invoice.getInvoiceNr(), pos);
                    getInstance().deleteProduct(p.getId());
                }
            }
            accountDB.updateAccount(GregorianCalendar.getInstance(), invoice.getTotal(), invoice.getSubtotal(), invoice.getTax(), invoice.getDiscount(), invoice.getCostPrice());
            this.zReport.registerInvoice(invoice);
            this.zReport.save(zrDS);
            invoiceDB.setTransactionSuccessful();
            positionDB.setTransactionSuccessful();
            accountDB.setTransactionSuccessful();
            productDB.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            invoiceDB.endTransaction();
            positionDB.endTransaction();
            accountDB.endTransaction();
            productDB.endTransaction();
        }
        checkStock(invoice);
    }

    private void checkStock(Invoice invoice) {
        try {
            Iterator it = invoice.getPositions().iterator();
            while (it.hasNext()) {
                InvoicePosition pos = (InvoicePosition) it.next();
                if (!(pos.getProduct() == null || pos.getProduct().getId().equals(Product.CUSTOM_PRODUCT_ID) || pos.getProduct().hasAttribute(16) || pos.getProduct().getStockQty().compareTo(getInstance().getSettings().getMinStockValue()) > 0 || this.stockLowListener == null)) {
                    this.stockLowListener.notifyItemStockLow(pos.getProduct());
                }
            }
        } catch (Exception e) {
        }
    }

    public void addInvoice(Invoice i) {
        synchronized (this.invoices) {
            this.invoices.add(i);
        }
    }

    public Product getProduct(String id) {
        return (Product) this.products.get(id);
    }

    public Collection<Product> getProducts() {
        return this.products.values();
    }

    public StringBuffer getWorkingCmd() {
        return this.workingCmd;
    }

    public int getNrOfOpenInvoiceLists() {
        return this.openInvoicesLists.size();
    }

    public ArrayList<InvoicePositionActive> getActualPositionList() {
        if (this.index < 0 || this.index >= this.openInvoicesLists.size()) {
            getInstance().getSettings().setNrOfTables(2);
            createPositionLists(2);
            this.index = 0;
        }
        return (ArrayList) this.openInvoicesLists.get(this.index);
    }

    public int getNrOfPositions(int i) {
        if (i < 0 || i >= this.openInvoicesLists.size()) {
            return 0;
        }
        return ((ArrayList) this.openInvoicesLists.get(i)).size();
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
        notifyTableSelectionChanged(index);
    }

    public void refillStock(Invoice invoice) {
        if (invoice != null) {
            try {
                List<InvoicePosition> positions = invoice.getPositions();
                for (int i = 0; i < positions.size(); i++) {
                    InvoicePosition pos = (InvoicePosition) positions.get(i);
                    if (pos.getProduct() != null) {
                        Product p = getInstance().getProduct(pos.getProduct().getId());
                        if (p != null) {
                            p.setStockQty(p.getStockQty().add(pos.getQty()));
                            getInstance().updateProduct(p);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                CrashLog.getInstance().logException(e);
            }
        }
    }

    public void setStockLowListener(StockLowListener l) {
        this.stockLowListener = l;
    }

    public void updateInvoice(Invoice i) {
        invoiceDB.updateInvoice(i);
    }

    public int nextZReportCount(Date day) {
        ZReportCountDataSource zReportDB = new ZReportCountDataSource(context);
        zReportDB.open();
        return zReportDB.getNextZReportNumber(day);
    }

    public ZReport getzReport() {
        return this.zReport;
    }

    public void loadZReport(StartActivity startActivity) {
        try {
            this.zReport.load(zrDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetZReport() {
        this.zReport.reset();
        try {
            this.zReport.save(zrDS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void resetZCounter() {
        this.zReport.resetZCounter();
        try {
            this.zReport.save(zrDS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean isInSplitMode() {
        return this.isInSplitMode;
    }

    public void setIsInSplitMode(boolean isInSplitMode) {
        this.isInSplitMode = isInSplitMode;
    }
}
