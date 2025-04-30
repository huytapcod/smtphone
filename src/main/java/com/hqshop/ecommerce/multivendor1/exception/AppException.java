package com.hqshop.ecommerce.multivendor1.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    public AppException(Errorcode error) {
        super(error.getMessage());
        this.error = error;
    }

    private Errorcode error;

    public AppException(Errorcode error, String customMessage) {
        super(customMessage);
        this.error = error;
    }

}
