package com.keepaccpos.network.data;

/**
 * Created by Arnold on 11.09.2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Table {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("link_catalog_hall")
    @Expose
    private String linkCatalogHall;
    @SerializedName("xpos")
    @Expose
    private String xpos;
    @SerializedName("ypos")
    @Expose
    private String ypos;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("radius")
    @Expose
    private String radius;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("guests")
    @Expose
    private Object guests;
    @SerializedName("journal_id")
    @Expose
    private String journalId;
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
     * The linkCatalogHall
     */
    public String getLinkCatalogHall() {
        return linkCatalogHall;
    }

    /**
     *
     * @param linkCatalogHall
     * The link_catalog_hall
     */
    public void setLinkCatalogHall(String linkCatalogHall) {
        this.linkCatalogHall = linkCatalogHall;
    }

    /**
     *
     * @return
     * The xpos
     */
    public String getXpos() {
        return xpos;
    }

    /**
     *
     * @param xpos
     * The xpos
     */
    public void setXpos(String xpos) {
        this.xpos = xpos;
    }

    /**
     *
     * @return
     * The ypos
     */
    public String getYpos() {
        return ypos;
    }

    /**
     *
     * @param ypos
     * The ypos
     */
    public void setYpos(String ypos) {
        this.ypos = ypos;
    }

    /**
     *
     * @return
     * The width
     */
    public String getWidth() {
        return width;
    }

    /**
     *
     * @param width
     * The width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     *
     * @return
     * The height
     */
    public String getHeight() {
        return height;
    }

    /**
     *
     * @param height
     * The height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     *
     * @return
     * The radius
     */
    public String getRadius() {
        return radius;
    }

    /**
     *
     * @param radius
     * The radius
     */
    public void setRadius(String radius) {
        this.radius = radius;
    }

    /**
     *
     * @return
     * The type
     */
    public Object getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(Object type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The state
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    public void setCloseState(String state) {
        this.state = "closed";
    }
    public void setOpenState() {
        this.state = "open";
    }

    /**
     *
     * @return
     * The guests
     */
    public Object getGuests() {
        return guests;
    }

    /**
     *
     * @param guests
     * The guests
     */
    public void setGuests(Object guests) {
        this.guests = guests;
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