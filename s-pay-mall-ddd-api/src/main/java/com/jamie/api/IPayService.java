package com.jamie.api;

import com.jamie.api.dto.CreatePayRquestDTO;
import com.jamie.api.response.Response;

public interface IPayService {

    Response<String> createPayOrder(CreatePayRquestDTO createPayRequestDTO);



}
