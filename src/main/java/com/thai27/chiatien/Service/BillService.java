package com.thai27.chiatien.Service;

import com.thai27.chiatien.dto.request.SplitBillRequest;
import com.thai27.chiatien.dto.response.SplitBillResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillService {
    public SplitBillResponse spitBill(SplitBillRequest splitBillRequest) {

        List<String> memberList = splitBillRequest.getMemberList();

        Map<String, Long> paidAmount = splitBillRequest.getPaidAmount();

        long totalAmount = paidAmount.values()
                .stream()
                .mapToLong(Long::longValue)
                .sum();

        long averageAmount = totalAmount / memberList.size();

        Map<String, Long> mustPaid = new HashMap<>();
        Map<String, Long> getPaid = new HashMap<>();
        for (String member : memberList) {
            if (paidAmount.containsKey(member)) {
                System.out.println(member + " exists in paidAmount");
                Long amount = paidAmount.get(member);
                if (amount > averageAmount) {
                    Long memberAmount = amount - averageAmount;
                    getPaid.put(member, memberAmount);
                } else if (amount < averageAmount) {
                    Long memberAmount = averageAmount - amount;
                    mustPaid.put(member, memberAmount);
                }
            } else {
                mustPaid.put(member, averageAmount);
            }
        }

        SplitBillResponse splitBillResponse = new SplitBillResponse();
        splitBillResponse.setGetPaid(getPaid);
        splitBillResponse.setMustPaid(mustPaid);
        return splitBillResponse;
    }
}
