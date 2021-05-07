package com.yoga.authentication.api;

import com.yoga.authentication.model.response.Response;
import com.yoga.authentication.util.AESUtil;
import com.yoga.authentication.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1.0/crypt")
@Slf4j
public class CryptoController {

    @Autowired
    private Environment environment;

    @PostMapping(value = "/enrcypt")
    public ResponseEntity<Response> encrypt(@RequestBody String data, @RequestHeader("requestId") String requestId) {
        log.info("{} Encrypting data..", requestId);
        String encryptedData = Optional.ofNullable(data)
                                .filter(StringUtils :: isNotEmpty)
                                .map(d -> AESUtil.encrypt(d, requestId,this::getAesSecretKey))
                                .orElse(null);
        return new ResponseEntity<>(ResponseUtil.prepareEncResponse(encryptedData), HttpStatus.OK);
    }

    @PostMapping(value = "/decrypt")
    public ResponseEntity<Response> decrypt(@RequestBody String enryptedData, @RequestHeader("requestId") String requestId) {
        log.info("{} Decrypting data..", requestId);
        String data = Optional.ofNullable(enryptedData)
                        .filter(StringUtils :: isNotEmpty)
                        .map(d -> AESUtil.decrypt(d, requestId, this::getAesSecretKey))
                        .orElse(null);
        return new ResponseEntity<>(ResponseUtil.prepareEncResponse(data), HttpStatus.OK);
    }

    public String getAesSecretKey(String key) {
        return environment.getProperty(key);
    }
}
