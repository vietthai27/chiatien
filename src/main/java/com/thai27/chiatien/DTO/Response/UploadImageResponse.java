package com.thai27.chiatien.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadImageResponse {
    private String imageUrl;
    private String imageFieldId;
}
