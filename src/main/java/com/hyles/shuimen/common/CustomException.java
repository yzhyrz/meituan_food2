package com.hyles.shuimen.common;

import org.springframework.stereotype.Component;


public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
