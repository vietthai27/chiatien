package com.thai27.chiatien.Controller;

import com.thai27.chiatien.DTO.Request.AddBankRequest;
import com.thai27.chiatien.Entity.Bank;
import com.thai27.chiatien.Service.BankService;
import com.thai27.chiatien.Ulti.ResponseModel;
import io.imagekit.sdk.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    BankService bankService;

    @GetMapping("/get-banks")
    public ResponseEntity<ResponseModel<Page<Bank>>> getBanks(@RequestParam int pageNumber, @RequestParam String bankName) {
        return ResponseEntity.ok(bankService.listBank(pageNumber, bankName));
    }

    @GetMapping("/get-bank-by-id/{bankId}")
    public ResponseEntity<ResponseModel<Bank>> getBankById(@PathVariable Long bankId) {
        return ResponseEntity.ok(bankService.getBankById(bankId));
    }

    @GetMapping("/get-all-bank")
    public ResponseEntity<ResponseModel<List<Bank>>> getBanksName() {
        return ResponseEntity.ok(bankService.getAllBank());
    }

    @PostMapping("/add-bank")
    public ResponseEntity<ResponseModel<Bank>> addBank(@RequestParam("bankName") String bankName,
                                                       @RequestParam("bankImage") MultipartFile bankImage) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException, IOException {
        return ResponseEntity.ok(bankService.addBank(bankName, bankImage));
    }

    @PutMapping("/edit-bank")
    public ResponseEntity<ResponseModel<Bank>> editBank(@RequestParam("bankName") String bankName,
                                                        @RequestParam(value = "bankImage", required = false) MultipartFile bankImage, @RequestParam Long bankId) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException, IOException {
        return ResponseEntity.ok(bankService.editBank(bankId, bankName, bankImage));
    }

    @DeleteMapping("/delete-bank")
    public ResponseEntity<ResponseModel<String>> deleteBank(@RequestParam Long bankId) {
        return ResponseEntity.ok(bankService.deleteBank(bankId));
    }
}
