package com.thai27.chiatien.Controller;

import com.thai27.chiatien.DTO.Data.BankAccountByUserDto;
import com.thai27.chiatien.Entity.Bank;
import com.thai27.chiatien.Entity.BankAccount;
import com.thai27.chiatien.Exception.ResourceNotFoundException;
import com.thai27.chiatien.Service.BankAccountService;
import com.thai27.chiatien.Ulti.ResponseModel;
import io.imagekit.sdk.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bank-account")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @PostMapping("/add-bank-account")
    public ResponseEntity<ResponseModel<String>> addBank(@RequestParam("username") String username,
                                                         @RequestParam("bankId") Long bankId,
                                                         @RequestParam("accountNumber") String accountNumber,
                                                         @RequestParam("qrImage") MultipartFile qrImage) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException, IOException {
        return ResponseEntity.ok((bankAccountService.addBankAccount(username, bankId, accountNumber, qrImage)));
    }

    @PutMapping("/edit-bank-account")
    public ResponseEntity<ResponseModel<String>> editBank(@RequestParam("accountId") Long accountId,
                                                          @RequestParam("username") String username,
                                                          @RequestParam("bankId") Long bankId,
                                                          @RequestParam("accountNumber") String accountNumber,
                                                          @RequestParam(value = ("qrImage"), required = false) MultipartFile qrImage) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException, IOException {
        return ResponseEntity.ok((bankAccountService.editBankAccount(accountId, username, bankId, accountNumber, qrImage)));
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<ResponseModel<String>> deleteAccount(@RequestParam("accountId") Long accountId) {
        return ResponseEntity.ok(bankAccountService.deleteBankAccount(accountId));
    }

    @GetMapping("/get-account-by-username")
    public ResponseEntity<ResponseModel<List<BankAccountByUserDto>>> getAccountByUsername(@RequestParam("username") String username) throws ResourceNotFoundException {
        return ResponseEntity.ok(bankAccountService.getAccountByUsername(username));
    }

    @GetMapping("/get-account-by-id")
    public ResponseEntity<ResponseModel<Optional<BankAccount>>> getAccountById(@RequestParam("accountId") Long accountId) throws ResourceNotFoundException {
        return ResponseEntity.ok(bankAccountService.getAccountById(accountId));
    }

}
