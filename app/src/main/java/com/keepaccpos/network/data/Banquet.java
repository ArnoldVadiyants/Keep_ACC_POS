package com.keepaccpos.network.data;

/**
 * Created by Arnold on 28.09.2016.
 */
public class Banquet {


    private String id;
    private String billNum;
    private String state;
    private String dateTimeStart;
    private String dateTimeFinish;
    private String staff;
    private String mobile;
    private String modifier;
    private String dateTime;
    private String client;
    private String hall;
    private String countGuest;
    private String prepay;
    private String sumPrice;
    private String reduction;
    private String total;
    private String discount;
public Banquet()
{

}
    public Banquet(String id, String billNum, String state, String dateTimeStart, String dateTimeFinish, String staff, String mobile, String modifier, String dateTime, String client, String hall, String countGuest, String discount, String prepay, String sumPrice, String reduction, String total) {
        this.id = id;
        this.billNum = billNum;
        this.state = state;
        this.dateTimeStart = dateTimeStart;
        this.dateTimeFinish = dateTimeFinish;
        this.staff = staff;
        this.mobile = mobile;
        this.modifier = modifier;
        this.dateTime = dateTime;
        this.client = client;
        this.hall = hall;
        this.countGuest = countGuest;
        this.prepay = prepay;
        this.sumPrice = sumPrice;
        this.reduction = reduction;
        this.total = total;
        this.discount = discount;
    }



    public String getPrepay() {
      return  getNotNull(prepay);
    }

    public void setPrepay(String prepay) {
        this.prepay = prepay;
    }

    public String getSumPrice() {
        return  getNotNull(sumPrice);

    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getReduction() {
        return  getNotNull(reduction);

    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }

    public String getTotal() {
        return  getNotNull(total);

    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDiscount() {
        return  getNotNull(discount);

    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCountGuest() {
        return  getNotNull(countGuest);

    }

    public void setCountGuest(String countGuest) {
        this.countGuest = countGuest;
    }

    public String getHall() {
        return  getNotNull(hall);

    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getDateTime() {
        return  getNotNull(dateTime);
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getModifier() {
        return  getNotNull(modifier);

    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getMobile() {
        return  getNotNull(mobile);

    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClient() {
        return  getNotNull(client);
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getId() {
        return  getNotNull(id);

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillNum() {
        return  getNotNull(billNum);
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getState() {
        return  getNotNull(state);

    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateTimeStart() {
        return  getNotNull(dateTimeStart);

    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public String getDateTimeFinish() {
        return  getNotNull(dateTimeFinish);
    }

    public void setDateTimeFinish(String dateTimeFinish) {
        this.dateTimeFinish = dateTimeFinish;
    }

    public String getStaff() {
        return  getNotNull(staff);
    }

    public void setStaff(String staff) {
        this.staff = staff;
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
