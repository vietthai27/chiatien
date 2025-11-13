package com.thai27.chiatien.Service;

import com.thai27.chiatien.DTO.Data.BankAccountByUserDto;
import com.thai27.chiatien.DTO.Response.UploadImageResponse;
import com.thai27.chiatien.Entity.Bank;
import com.thai27.chiatien.Entity.ChiaTienUser;
import com.thai27.chiatien.Entity.BankAccount;
import com.thai27.chiatien.Exception.ResourceNotFoundException;
import com.thai27.chiatien.ImageKit.ImageKitService;
import com.thai27.chiatien.Repository.BankRepository;
import com.thai27.chiatien.Repository.BankAccountRepository;
import com.thai27.chiatien.Repository.ChiaTienUserRepository;
import com.thai27.chiatien.Ulti.ResponseModel;
import io.imagekit.sdk.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    ImageKitService imageKitService;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    ChiaTienUserRepository chiaTienUserRepository;
    @Autowired
    BankRepository bankRepository;

    public ResponseModel<String> addBankAccount(String username, Long bankId, String accountNumber, MultipartFile qrImage) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException, IOException {
        Optional<ChiaTienUser> user = chiaTienUserRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseModel.warning(null, "Không tồn tại user: " + username);
        }
        Optional<Bank> bank = bankRepository.findById(bankId);
        if (bank.isEmpty()) {
            return ResponseModel.warning(null, "Không tồn tại ngân hàng");
        }
        BankAccount account = new BankAccount();
        account.setUser(user.get());
        account.setBank(bank.get());
        account.setAccountNumber(accountNumber);
        UploadImageResponse uploadImageResponse = imageKitService.uploadImage(qrImage);
        account.setQrCodeUrl(uploadImageResponse.getImageUrl());
        account.setQrCodeFieldId(uploadImageResponse.getImageFieldId());
        bankAccountRepository.save(account);
        return ResponseModel.success(null, "Thêm tài khoản thành công");
    }

    public ResponseModel<String> editBankAccount(Long accountId, String username, Long bankId, String accountNumber, MultipartFile qrImage) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException, IOException {
        Optional<ChiaTienUser> user = chiaTienUserRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseModel.warning(null, "Không tồn tại user: " + username);
        }
        Optional<Bank> bank = bankRepository.findById(bankId);
        if (bank.isEmpty()) {
            return ResponseModel.warning(null, "Không tồn tại ngân hàng");
        }
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountId);
        if (bankAccount.isEmpty()) {
            return ResponseModel.warning(null, "Không tồn tại tài khoản");
        }
        bankAccount.get().setUser(user.get());
        bankAccount.get().setBank(bank.get());
        bankAccount.get().setAccountNumber(accountNumber);
        if (qrImage != null && !qrImage.isEmpty()) {
            if (bankAccount.get().getQrCodeUrl() != null && !bankAccount.get().getQrCodeUrl().isEmpty()) {
                imageKitService.deleteImage(bankAccount.get().getQrCodeFieldId());
            }
            UploadImageResponse uploadImageResponse = imageKitService.uploadImage(qrImage);
            bankAccount.get().setQrCodeUrl(uploadImageResponse.getImageUrl());
            bankAccount.get().setQrCodeFieldId(uploadImageResponse.getImageFieldId());
        }
        bankAccountRepository.save(bankAccount.get());
        return ResponseModel.success(null, "Sửa tài khoản thành công");
    }

    public ResponseModel<String> deleteBankAccount(Long accountId) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountId);
        bankAccount.ifPresent(account -> bankAccountRepository.delete(account));
        return ResponseModel.success(null, "Xóa tài khoản thành công");
    }

    public ResponseModel<List<BankAccountByUserDto>> getAccountByUsername(String username) throws ResourceNotFoundException {
        Optional<ChiaTienUser> user = chiaTienUserRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Không tồn tại user: " + username);
        }
        List<BankAccountByUserDto> listAccountByUsername = bankAccountRepository.findByUser(user.get().getUsername());
        return ResponseModel.success(listAccountByUsername, "Lấy dữ liệu thành công");
    }

    public ResponseModel<Optional<BankAccount>> getAccountById(Long bankId) throws ResourceNotFoundException {
        Optional<BankAccount> account = bankAccountRepository.findById(bankId);
        if (account.isEmpty()) {
            throw new ResourceNotFoundException("Không tồn tại tài khoản");
        }
        return ResponseModel.success(account, "Lấy dữ liệu thành công");
    }
}
