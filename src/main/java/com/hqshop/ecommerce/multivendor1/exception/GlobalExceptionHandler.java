package com.hqshop.ecommerce.multivendor1.exception;

import com.hqshop.ecommerce.multivendor1.dto.request.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
     ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException e) {
        ApiResponse apiReponse = new ApiResponse();
        apiReponse.setCode(999);
        apiReponse.setMessage(e.getMessage());

        return ResponseEntity.badRequest().body(apiReponse);

    }
    @ExceptionHandler(value =  AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException e) {
        Errorcode errorcode = e.getError();
        ApiResponse apiReponse = new ApiResponse();

        apiReponse.setCode(errorcode.getCode());
        apiReponse.setMessage(errorcode.getMessage());
        return ResponseEntity.badRequest().body(apiReponse);

    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidException(MethodArgumentNotValidException e) {
        String enymkey = e.getFieldError().getDefaultMessage();
        Errorcode errorcode = Errorcode.INVALID_KEY;
        try{
            errorcode = Errorcode.valueOf(enymkey);
        } catch (IllegalArgumentException ex){

        }


        ApiResponse apiReponse = new ApiResponse();
        apiReponse.setMessage(enymkey);
        return ResponseEntity.badRequest().body(apiReponse);
    }

}
