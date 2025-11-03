package com.thai27.chiatien.Service;

import com.thai27.chiatien.Entity.Bank;
import com.thai27.chiatien.Repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankService {
    
    @Autowired
    BankRepository bankRepository;

    public Bank addBank(Bank Bank) {
        Bank BankAdd = new Bank();
        BankAdd.setBankName(Bank.getBankName());
        bankRepository.save(BankAdd);
        return BankAdd;
    }

    public Bank editBank(Bank Bank, Long BankId) {
        Optional<Bank> BankEdit = bankRepository.findById(BankId);
        BankEdit.get().setBankName(Bank.getBankName());
        bankRepository.save(BankEdit.get());
        return BankEdit.get();
    }

    public void deleteBank(Long BankId) {
        Optional<Bank> BankDelete = bankRepository.findById(BankId);
        bankRepository.delete(BankDelete.get());
    }
}
