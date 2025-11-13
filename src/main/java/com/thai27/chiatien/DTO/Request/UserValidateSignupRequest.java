package com.thai27.chiatien.DTO.Request;

import lombok.Data;

@Data
public class UserValidateSignupRequest {
    private String validateCode;
    private String email;
}
