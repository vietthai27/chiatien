package com.thai27.chiatien.DTO.Request;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SplitBillRequest {
    Map<Long, Long> paidAmount;
}
