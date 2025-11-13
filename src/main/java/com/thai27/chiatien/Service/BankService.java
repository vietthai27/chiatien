package com.thai27.chiatien.Service;

import com.thai27.chiatien.DTO.Response.UploadImageResponse;
import com.thai27.chiatien.Entity.Bank;
import com.thai27.chiatien.ImageKit.ImageKitService;
import com.thai27.chiatien.Repository.BankRepository;
import com.thai27.chiatien.Ulti.ResponseModel;
import io.imagekit.sdk.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);

    @Autowired
    BankRepository bankRepository;
    @Autowired
    ImageKitService imageKitService;

    public ResponseModel<Page<Bank>> listBank(int pageNumber, String bankName) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, 5);
        Page<Bank> listbank;
        if (bankName.isEmpty()) {
            listbank = bankRepository.findAll(pageRequest);
        } else {
            String likeBankName = "%" + bankName + "%";
            listbank = bankRepository.findAllByBankNameLikeIgnoreCase(likeBankName, pageRequest);
        }
        return ResponseModel.success(listbank, "Lấy dữ liệu thành công");
    }

    public ResponseModel<Bank> addBank(String bankName, MultipartFile bankImage) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException, IOException {
        Bank bankAdd = new Bank();
        bankAdd.setBankName(bankName);
        UploadImageResponse uploadImageResponse = imageKitService.uploadImage(bankImage);
        bankAdd.setImageUrl(uploadImageResponse.getImageUrl());
        bankAdd.setImageFieldId(uploadImageResponse.getImageFieldId());
        bankRepository.save(bankAdd);
        return ResponseModel.success(bankAdd, "Thêm dữ liệu thành công");
    }

    public ResponseModel<Bank> editBank(Long BankId, String bankName, MultipartFile bankImage) throws ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException, IOException {
        Optional<Bank> bankEdit = bankRepository.findById(BankId);
        bankEdit.get().setBankName(bankName);
        if (bankImage != null && !bankImage.isEmpty()) {
            imageKitService.deleteImage(bankEdit.get().getImageFieldId());
            UploadImageResponse uploadImageResponse = imageKitService.uploadImage(bankImage);
            bankEdit.get().setImageUrl(uploadImageResponse.getImageUrl());
            bankEdit.get().setImageFieldId(uploadImageResponse.getImageFieldId());
        }
        bankRepository.save(bankEdit.get());
        return ResponseModel.success(bankEdit.get(), "Sửa dữ liệu thành công");
    }

    public ResponseModel<String> deleteBank(Long bankId) {
        try {
            Optional<Bank> bankDelete = bankRepository.findById(bankId);
            imageKitService.deleteImage(bankDelete.get().getImageFieldId());
            bankRepository.delete(bankDelete.get());
            return ResponseModel.success(null, "Xóa thành công");
        } catch (Exception e) {
            LOGGER.error("error delete bank", e);
            return ResponseModel.error(null, "Xóa thất bại");
        }
    }

    public ResponseModel<Bank> getBankById(Long bankId) {
        return ResponseModel.success(bankRepository.findById(bankId).get(), "Lấy dữ liệu thành công");
    }

    public ResponseModel<List<Bank>> getAllBank() {
        return ResponseModel.success(bankRepository.findAll(), "Lấy dữ liệu thành công");
    }
}
