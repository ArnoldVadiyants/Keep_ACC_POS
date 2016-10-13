package com.keepaccpos.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Arnold on 28.09.2016.
 */


    @Generated("org.jsonschema2pojo")
    public class DeliveryHead {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("value")
        @Expose
        private String value;

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
         * The text
         */
        public String getText() {
            return text;
        }

        /**
         *
         * @param text
         * The text
         */
        public void setText(String text) {
            this.text = text;
        }

        /**
         *
         * @return
         * The value
         */
        public String getValue() {
            return value;
        }

        /**
         *
         * @param value
         * The value
         */
        public void setValue(String value) {
            this.value = value;
        }

    }