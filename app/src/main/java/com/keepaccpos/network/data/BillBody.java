package com.keepaccpos.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Arnold on 20.09.2016.
 */
@Generated("org.jsonschema2pojo")
public class BillBody {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("sold")
    @Expose
    private String sold;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("sum_price")
    @Expose
    private String sumPrice;
    @SerializedName("modifier_ids")
    @Expose
    private Object modifierIds;
    @SerializedName("module_from")
    @Expose
    private String moduleFrom;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("journal_id")
    @Expose
    private String journalId;
    @SerializedName("module")
    @Expose
    private String module;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("owner")
    @Expose
    private String owner;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The category
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     * The sold
     */
    public String getSold() {
        return sold;
    }

    /**
     *
     * @param sold
     * The sold
     */
    public void setSold(String sold) {
        this.sold = sold;
    }

    /**
     *
     * @return
     * The count
     */
    public String getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(String count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The cost
     */
    public String getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     * The cost
     */
    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The sumPrice
     */
    public String getSumPrice() {
        return sumPrice;
    }

    /**
     *
     * @param sumPrice
     * The sum_price
     */
    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    /**
     *
     * @return
     * The modifierIds
     */
    public Object getModifierIds() {
        return modifierIds;
    }

    /**
     *
     * @param modifierIds
     * The modifier_ids
     */
    public void setModifierIds(Object modifierIds) {
        this.modifierIds = modifierIds;
    }

    /**
     *
     * @return
     * The moduleFrom
     */
    public String getModuleFrom() {
        return moduleFrom;
    }

    /**
     *
     * @param moduleFrom
     * The module_from
     */
    public void setModuleFrom(String moduleFrom) {
        this.moduleFrom = moduleFrom;
    }

    /**
     *
     * @return
     * The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     *
     * @param unit
     * The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     *
     * @return
     * The journalId
     */
    public String getJournalId() {
        return journalId;
    }

    /**
     *
     * @param journalId
     * The journal_id
     */
    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    /**
     *
     * @return
     * The module
     */
    public String getModule() {
        return module;
    }

    /**
     *
     * @param module
     * The module
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     *
     * @return
     * The base
     */
    public String getBase() {
        return base;
    }

    /**
     *
     * @param base
     * The base
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     *
     * @return
     * The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     * The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

}