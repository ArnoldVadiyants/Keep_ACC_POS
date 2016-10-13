package com.keepaccpos.network.interfaces;

import com.keepaccpos.network.data.DataBanquet;
import com.keepaccpos.network.data.DataDelivery;
import com.keepaccpos.network.data.DataOpen;
import com.keepaccpos.network.data.DataPost;
import com.keepaccpos.network.data.DataBill;
import com.keepaccpos.network.data.DataBlock;
import com.keepaccpos.network.data.DataCategory;
import com.keepaccpos.network.data.DataTable;
import com.keepaccpos.network.model.Packet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Arnold on 05.04.2016.
 */
public interface RetrofitApiInterface {
    String BASE_URL = "http://keepacc.com/";

    // @POST("arnitest.php?message=handle")
    //    @POST("dispatcher.php?message=Handle")
   /* @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<Packet> packetPost(@Field("packet") Map<String,List> packet);
*/
    @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<DataPost> packetPost(@Field("packet") Packet packet);
    @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<DataOpen> open(@Field("packet") Packet packet);

    @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<DataBanquet> getBanquette(@Field("packet") Packet packet);
    @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<DataDelivery> getDelivery(@Field("packet") Packet packet);

    @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<DataBlock> getBlocks(@Field("packet") Packet packet);

    @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<DataTable> getTables(@Field("packet") Packet packet);

    @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<DataCategory> getCategories(@Field("packet") Packet packet);

    @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<DataBill> getBill(@Field("packet") Packet packet);

    @FormUrlEncoded
    @POST("dispatcher.php?message=HandleMobile")
    Call<DataBill> getBillWithMenu(@Field("packet") Packet packet);


}
