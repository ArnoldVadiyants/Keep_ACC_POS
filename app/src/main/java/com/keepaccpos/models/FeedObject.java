package com.keepaccpos.models;

/**
 * Created by Arnold on 23.07.2016.
 */
public class FeedObject {
    private int image;



    private String title;
    private String price;


    public FeedObject(String title, String price, int image ) {
        this.image = image;
        this.title = title;
        this.price = price;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
