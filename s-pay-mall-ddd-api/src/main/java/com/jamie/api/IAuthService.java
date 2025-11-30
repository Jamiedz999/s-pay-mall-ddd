package com.jamie.api;

import com.jamie.api.response.Response;

public interface IAuthService {
    Response<String> weixinQrCodeTicket();

    Response<String> checkLogin(String ticket);

}
