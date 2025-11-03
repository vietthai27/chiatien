package com.thai27.chiatien.dto.request;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SplitBillRequest {
    List<String> memberList;
    Map<String, Long> paidAmount;
}
