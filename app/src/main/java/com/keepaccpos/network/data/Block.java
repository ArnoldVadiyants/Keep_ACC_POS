package com.keepaccpos.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Arnold on 11.09.2016.
 */
@Generated("org.jsonschema2pojo")
public class Block {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("file_image_category")
    @Expose
    private Object fileImageCategory;
    @SerializedName("dataId")
    @Expose
    private String dataId;

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
     * The fileImageCategory
     */
    public Object getFileImageCategory() {
        return fileImageCategory;
    }

    /**
     *
     * @param fileImageCategory
     * The file_image_category
     */
    public void setFileImageCategory(Object fileImageCategory) {
        this.fileImageCategory = fileImageCategory;
    }

    /**
     *
     * @return
     * The dataId
     */
    public String getDataId() {
        return dataId;
    }

    /**
     *
     * @param dataId
     * The dataId
     */
    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

}