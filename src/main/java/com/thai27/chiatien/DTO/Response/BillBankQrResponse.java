package com.thai27.chiatien.DTO.Response;

import lombok.Data;

@Data
public class BillBankQrResponse {
    private String bankName;
    private String QrImageUrl;
}
