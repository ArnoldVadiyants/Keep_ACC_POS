package com.keepaccpos.network.data;

/**
 * Created by Arnold on 12.04.2016.
 */

import java.util.ArrayList;
import java.util.List;


public class CategoryLab {
    private List<Category> mCategories = new ArrayList<>();
    private static CategoryLab sCategoryLab;

    private CategoryLab() {

    }

    public static CategoryLab getInstance() {
        if (sCategoryLab == null) {
            sCategoryLab = new CategoryLab();
        }
        return sCategoryLab;
    }

    public List<Category> getCategories( ) {

        return mCategories;
    }

    public void setCategories(List<Category> categories) {
        this.mCategories = categories;
    }

    public Category getCategory(String id) {
        for (Category b : mCategories) {
            if (b.getDataId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public  Category getCategoryByName(String title, List<Category> categories) {
        for (Category b : categories) {
            if (b.getName().equals(title)) {
                return b;
            }
        }
        return null;
    }
    public void removeData() {
        mCategories.clear();
    }

}

