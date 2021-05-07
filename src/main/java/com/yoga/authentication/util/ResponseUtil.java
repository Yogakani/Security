package com.yoga.authentication.util;

import com.yoga.authentication.model.request.UserRequest;
import com.yoga.authentication.model.response.EncyptResponse;
import com.yoga.authentication.model.response.Response;
import com.yoga.authentication.model.response.TokenResponse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

@UtilityClass
public class ResponseUtil {

    public Response prepareTokenResponse(UserRequest userRequest, String token) {
        TokenResponse tokenResponse = new TokenResponse()
                                        .setUserId(userRequest.getUserId())
                                        .setBatchId(userRequest.getBatchId())
                                        .setJwt(token);
        tokenResponse.setStatus(getHttpStatus(Boolean.TRUE));
        return new Response().setResponse(tokenResponse);
    }

    public Response validateTokenResponse(Boolean status) {
        com.yoga.authentication.model.response.HttpStatus httpStatus = new com.yoga.authentication.model.response.HttpStatus();
        httpStatus.setStatus(getHttpStatus(status));
        return new Response().setResponse(httpStatus);
    }

    public String getHttpStatus(boolean status) {
        return status ? String.valueOf(HttpStatus.OK.value()) : String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public Response prepareEncResponse(String data) {
        boolean status = StringUtils.isNotEmpty(data) ? Boolean.TRUE : Boolean.FALSE;
        EncyptResponse encyptResponse = new EncyptResponse()
                                        .setData(data);
        encyptResponse.setStatus(getHttpStatus(status));
        return new Response().setResponse(encyptResponse);
    }
}
