package com.thai27.chiatien.DTO.Response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SplitBillResponse {
    Map<String, Long> mustPaid = new HashMap<>();
    Map<String, Long> getPaid = new HashMap<>();
    Long totalAmount;
    Long averageAmount;
}
