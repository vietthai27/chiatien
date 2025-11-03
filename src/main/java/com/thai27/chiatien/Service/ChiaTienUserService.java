package com.thai27.chiatien.Service;

import com.thai27.chiatien.Entity.ChiaTienUser;
import com.thai27.chiatien.Repository.ChiaTienUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChiaTienUserService {
    @Autowired
    ChiaTienUserRepository chiaTienUserRepository;

    public ChiaTienUser addUser(ChiaTienUser chiaTienUser) {
        ChiaTienUser chiaTienUserAdd = new ChiaTienUser();
        chiaTienUserAdd.setUsername(chiaTienUser.getUsername());
        chiaTienUserAdd.setFullName(chiaTienUser.getFullName());
        chiaTienUserRepository.save(chiaTienUserAdd);
        return chiaTienUserAdd;
    }

    public ChiaTienUser editUser(ChiaTienUser chiaTienUser, Long userId) {
        Optional<ChiaTienUser> chiaTienUserEdit = chiaTienUserRepository.findById(userId);
        chiaTienUserEdit.get().setUsername(chiaTienUser.getUsername());
        chiaTienUserEdit.get().setFullName(chiaTienUser.getFullName());
        chiaTienUserRepository.save(chiaTienUserEdit.get());
        return chiaTienUserEdit.get();
    }

    public void deleteUser(Long userId) {
        Optional<ChiaTienUser> chiaTienUserDelete = chiaTienUserRepository.findById(userId);
        chiaTienUserRepository.delete(chiaTienUserDelete.get());
    }
}
