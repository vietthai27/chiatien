package com.thai27.chiatien.DTO.Request;

import lombok.Data;

@Data
public class UserChangePasswordRequest {
    private String token;
    private String oldPassword;
    private String newPassword;
}
