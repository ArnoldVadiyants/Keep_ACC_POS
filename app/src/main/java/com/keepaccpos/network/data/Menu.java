package com.keepaccpos.network.data;

/**
 * Created by Arnold on 20.09.2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.keepaccpos.interfaces.IMenu;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;


@Generated("org.jsonschema2pojo")
public class Menu implements IMenu {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link_posmenu")
    @Expose
    private String linkPosmenu;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("bool_nomenu")
    @Expose
    private Object boolNomenu;
    @SerializedName("module")
    @Expose
    private String module;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("identical")
    @Expose
    private String identical;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("modifiers")
    @Expose
    private List<Object> modifiers = new ArrayList<Object>();

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
     * The linkPosmenu
     */
    public String getLinkPosmenu() {
        return linkPosmenu;
    }

    /**
     *
     * @param linkPosmenu
     * The link_posmenu
     */
    public void setLinkPosmenu(String linkPosmenu) {
        this.linkPosmenu = linkPosmenu;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    @Override
    public String getImageUrl() {
      return  getUrl();
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
     * The weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     * The weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     * The boolNomenu
     */
    public Object getBoolNomenu() {
        return boolNomenu;
    }

    /**
     *
     * @param boolNomenu
     * The bool_nomenu
     */
    public void setBoolNomenu(Object boolNomenu) {
        this.boolNomenu = boolNomenu;
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

    /**
     *
     * @return
     * The identical
     */
    public String getIdentical() {
        return identical;
    }

    /**
     *
     * @param identical
     * The identical
     */
    public void setIdentical(String identical) {
        this.identical = identical;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The modifiers
     */
    public List<Object> getModifiers() {
        return modifiers;
    }

    /**
     *
     * @param modifiers
     * The modifiers
     */
    public void setModifiers(List<Object> modifiers) {
        this.modifiers = modifiers;
    }

}