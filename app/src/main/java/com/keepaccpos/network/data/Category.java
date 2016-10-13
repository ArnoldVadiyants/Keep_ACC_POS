package com.keepaccpos.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.keepaccpos.interfaces.IMenu;

import javax.annotation.Generated;

/**
 * Created by Arnold on 18.09.2016.
 */

@Generated("org.jsonschema2pojo")
public class Category implements IMenu {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("file_image_category")
    @Expose
    private String fileImageCategory;
    @SerializedName("dataId")
    @Expose
    private String dataId;

    /**
     *
     * @return
     * The name
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImageUrl() {
       return getFileImageCategory();
    }

    @Override
    public String getPrice() {
        return null;
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
    public String getFileImageCategory() {
        return fileImageCategory;
    }

    /**
     *
     * @param fileImageCategory
     * The file_image_category
     */
    public void setFileImageCategory(String fileImageCategory) {
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
