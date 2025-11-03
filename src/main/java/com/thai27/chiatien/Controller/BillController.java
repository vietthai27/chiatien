package com.thai27.chiatien.Controller;

import com.thai27.chiatien.Service.BillService;
import com.thai27.chiatien.dto.request.SplitBillRequest;
import com.thai27.chiatien.dto.response.SplitBillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillService billService;

    @PostMapping("/split-bill")
    public ResponseEntity<SplitBillResponse> splitBill(@RequestBody SplitBillRequest splitBillRequest) {
        SplitBillResponse splitBillResponse = billService.spitBill(splitBillRequest);
        return ResponseEntity.ok(splitBillResponse);
    }
}
