package com.keepaccpos.network.data;

/**
 * Created by Arnold on 28.09.2016.
 */
public class Delivery {


    private String id;
    private  String billNum;
    private  String state;
    private   String date;
    private  String client;
    private  String discount;
    private   String address;
    private   String mobile;
    private   String datePreOrder;
    private   String timePreOrder;
    private   String sumPrice;
    private   String reduction;
    private  String total;

    public Delivery(String id, String billNum, String state, String date, String client, String discount, String address, String mobile, String datePreOrder, String timePreOrder, String sumPrice, String reduction, String total) {
        this.id = id;
        this.billNum = billNum;
        this.state = state;
        this.date = date;
        this.client = client;
        this.discount = discount;
        this.address = address;
        this.mobile = mobile;
        this.datePreOrder = datePreOrder;
        this.timePreOrder = timePreOrder;
        this.sumPrice = sumPrice;
        this.reduction = reduction;
        this.total = total;
    }

    public String getTotal() {
        return getNotNull(total);
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBillNum() {

        return getNotNull(billNum);

    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getState() {
        return getNotNull(state);

    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return getNotNull(date);

    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return getNotNull(client);

    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDiscount() {
        return getNotNull(discount);
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAddress() {
        return getNotNull(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return getNotNull(mobile);
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDatePreOrder() {
        return getNotNull(datePreOrder);
    }

    public void setDatePreOrder(String datePreOrder) {
        this.datePreOrder = datePreOrder;
    }

    public String getTimePreOrder() {
        return getNotNull(timePreOrder);

    }

    public void setTimePreOrder(String timePreOrder) {
        this.timePreOrder = timePreOrder;
    }

    public String getSumPrice() {
        return getNotNull(sumPrice);

    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getReduction() {
        return getNotNull(reduction);
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String getNotNull(String arg)
    {
        if(arg== null)
        {
            return "";
        }
        return arg;
    }
}
