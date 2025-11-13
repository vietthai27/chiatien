package com.thai27.chiatien.Controller;

import com.thai27.chiatien.DTO.Data.UserListDto;
import com.thai27.chiatien.DTO.Response.UserListResponse;
import com.thai27.chiatien.Service.BillService;
import com.thai27.chiatien.DTO.Request.SplitBillRequest;
import com.thai27.chiatien.DTO.Response.SplitBillResponse;
import com.thai27.chiatien.Service.ChiaTienUserService;
import com.thai27.chiatien.Ulti.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillService billService;
    @Autowired
    ChiaTienUserService chiaTienUserService;

    @PostMapping("/split-bill")
    public ResponseEntity<ResponseModel<SplitBillResponse>> splitBill(@RequestBody SplitBillRequest splitBillRequest) {
        return ResponseEntity.ok(billService.splitBill(splitBillRequest));
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<ResponseModel<UserListResponse>> getAllUser() {
        return ResponseEntity.ok(chiaTienUserService.findAllUser());
    }
}
