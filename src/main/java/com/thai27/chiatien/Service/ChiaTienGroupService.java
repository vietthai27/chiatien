package com.thai27.chiatien.Service;

import com.thai27.chiatien.Entity.ChiaTienGroup;
import com.thai27.chiatien.Repository.ChiaTienGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChiaTienGroupService {
    
    @Autowired
    ChiaTienGroupRepository chiaTienGroupRepository;

    public ChiaTienGroup addGroup(ChiaTienGroup ChiaTienGroup) {
        ChiaTienGroup ChiaTienGroupAdd = new ChiaTienGroup();
        ChiaTienGroupAdd.setGroupName(ChiaTienGroup.getGroupName());
        chiaTienGroupRepository.save(ChiaTienGroupAdd);
        return ChiaTienGroupAdd;
    }

    public ChiaTienGroup editGroup(ChiaTienGroup ChiaTienGroup, Long GroupId) {
        Optional<ChiaTienGroup> ChiaTienGroupEdit = chiaTienGroupRepository.findById(GroupId);
        ChiaTienGroupEdit.get().setGroupName(ChiaTienGroup.getGroupName());
        chiaTienGroupRepository.save(ChiaTienGroupEdit.get());
        return ChiaTienGroupEdit.get();
    }

    public void deleteGroup(Long GroupId) {
        Optional<ChiaTienGroup> ChiaTienGroupDelete = chiaTienGroupRepository.findById(GroupId);
        chiaTienGroupRepository.delete(ChiaTienGroupDelete.get());
    }
}
