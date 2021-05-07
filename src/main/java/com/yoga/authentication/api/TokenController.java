package com.yoga.authentication.api;

import com.yoga.authentication.model.request.UserRequest;
import com.yoga.authentication.model.response.Response;
import com.yoga.authentication.service.TokenService;
import com.yoga.authentication.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v2.0/token")
@Slf4j
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/generate")
    public ResponseEntity<Response> generateToken(@RequestBody UserRequest userRequest, @RequestHeader("requestId") String requestId) {
        log.info("{} Generating Token for user {}", requestId, userRequest.getUserId());
        String token = tokenService.generateToken(userRequest);
        log.info("{} Token for user {} is : {}", requestId, userRequest.getUserId(), token);
        return new ResponseEntity<>(ResponseUtil.prepareTokenResponse(userRequest,token), HttpStatus.OK);
    }

    @PostMapping(value = "/validate")
    public ResponseEntity<Response> validateToken(@RequestHeader("requestId") String requestId,
                                                  @RequestHeader("Authorization") String authorisation,
                                                  @RequestHeader("userId") String userId) {
        log.info("{} Validating Token for user {}", requestId, userId);
        String jwt = authorisation.substring(7);
        Boolean status;
        try {
            status = tokenService.validateToken(jwt, userId);
        } catch (Exception e) {
            status = Boolean.FALSE;
        }
        log.info("{} Status of token {} is : {}", requestId, jwt, status);
        return new ResponseEntity<>(ResponseUtil.validateTokenResponse(status), HttpStatus.OK);
    }
}
