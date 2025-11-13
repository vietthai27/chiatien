package com.thai27.chiatien.Service;

import com.thai27.chiatien.DTO.Data.UserDetail;
import com.thai27.chiatien.Entity.ChiaTienUser;
import com.thai27.chiatien.Repository.ChiaTienUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    ChiaTienUserRepository chiaTienUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ChiaTienUser userInfo = chiaTienUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Tài khoản " + username + " không tồn tại trong hệ thống"
                        )
                );
        return UserDetail.build(userInfo);
    }

}
