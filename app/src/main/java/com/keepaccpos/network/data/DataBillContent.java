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
public class DataBillContent {

    @SerializedName("menu")
    @Expose
    private List<Menu> menu = new ArrayList<Menu>();
    @SerializedName("bill_head")
    @Expose
    private List<BillHead> billHead = new ArrayList<BillHead>();
    @SerializedName("bill_body")
    @Expose
    private List<BillBody> billBody = new ArrayList<BillBody>();

    /**
     *
     * @return
     * The menu
     */
    public List<Menu> getMenu() {
        return menu;
    }

    /**
     *
     * @param menu
     * The menu
     */
    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    /**
     *
     * @return
     * The billHead
     */
    public List<BillHead> getBillHead() {
        return billHead;
    }

    /**
     *
     * @param billHead
     * The bill_head
     */
    public void setBillHead(List<BillHead> billHead) {
        this.billHead = billHead;
    }

    /**
     *
     * @return
     * The billBody
     */
    public List<BillBody> getBillBody() {
        return billBody;
    }

    /**
     *
     * @param billBody
     * The bill_body
     */
    public void setBillBody(List<BillBody> billBody) {
        this.billBody = billBody;
    }



}
