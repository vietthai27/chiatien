package com.thai27.chiatien.DTO.Response;

import com.thai27.chiatien.DTO.Data.UserListDto;
import lombok.Data;

import java.util.List;

@Data
public class UserListResponse {

    private List<UserListDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
