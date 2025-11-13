package com.thai27.chiatien.DTO.Request;

import lombok.Data;

@Data
public class UserResetPasswordRequest {
    private String username;
    private String email;
}
