package com.thai27.chiatien.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserInfoAlreadyExistException extends Exception{
    public UserInfoAlreadyExistException(String message){
        super(message);
    }

}
