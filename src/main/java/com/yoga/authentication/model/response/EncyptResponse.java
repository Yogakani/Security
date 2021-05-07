package com.yoga.authentication.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class EncyptResponse extends HttpStatus {
    private String data;
}
