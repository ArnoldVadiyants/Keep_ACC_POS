package com.keepaccpos.network.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by Arnold on 20.09.2016.
 */
@Generated("org.jsonschema2pojo")

    public class DataDeliveryContent {

        @SerializedName("head")
        @Expose
        private List<List<DeliveryHead>> head = new ArrayList<List<DeliveryHead>>();
        @SerializedName("body")
        @Expose
        private List<List<List<DeliveryBody>>> body = new ArrayList<List<List<DeliveryBody>>>();

        /**
         *
         * @return
         * The head
         */
        public List<List<DeliveryHead>> getDeliveryHead() {
            return head;
        }

        /**
         *
         * @param head
         * The head
         */
        public void setDeliveryHead(List<List<DeliveryHead>> head) {
            this.head = head;
        }

        /**
         *
         * @return
         * The body
         */
        public List<List<List<DeliveryBody>>> getDeliveryBody() {
            return body;
        }

        /**
         *
         * @param body
         * The body
         */
        public void setDeliveryBody(List<List<List<DeliveryBody>>> body) {
            this.body = body;
        }

    }