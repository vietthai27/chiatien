package com.thai27.chiatien.DTO.Data;

import lombok.Data;

import java.util.List;

@Data
public class UserListDto {
    private Long id;
    private String username;
    private String email;
    private List<RoleListDto> listRoles;
}
