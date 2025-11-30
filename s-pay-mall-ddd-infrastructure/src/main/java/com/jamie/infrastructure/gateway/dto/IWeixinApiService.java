package com.jamie.infrastructure.gateway.dto;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 微信接口服务
 */

public interface IWeixinApiService {

    @GET("cgi-bin/token")
    Call<WeixinTokenResponseDTO> getToken(@Query("grant_type") String grantType,
                                          @Query("appid") String appid,
                                          @Query("secret") String appSecret);

    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrCodeResponseDTO> createQrCode(@Query("access_token") String accessToken, @Body WeixinQrCodeRequestDTO weixinQrCodeRequestDTO);

    @POST("cgi-bin/message/template/send")
    Call<Void> sendMessage(@Query("access_token") String accessToken, @Body WeixinTemplateMessageDTO weixinTemplateMessageDTO);

}

