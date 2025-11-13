package com.thai27.chiatien.DTO.Request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddBankRequest {
    private String bankName;
    private MultipartFile bankImage;
}
