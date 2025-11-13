package com.thai27.chiatien.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ChildDataStillExist extends Exception{

    public ChildDataStillExist(String message){
        super(message);
    }
}
