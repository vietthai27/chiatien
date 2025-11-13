package com.thai27.chiatien.Ulti;

import com.thai27.chiatien.Entity.ChiaTienUser;
import com.thai27.chiatien.Entity.Role;
import com.thai27.chiatien.Repository.ChiaTienUserRepository;
import com.thai27.chiatien.Repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitData {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ChiaTienUserRepository chiaTienUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void initData() {
        List<Role> listRole = roleRepository.findAll();
        if (listRole.size() != 3) {
            Role roleAdmin = new Role();
            roleAdmin.setRolename(Constant.ROLE_ADMIN);
            roleRepository.save(roleAdmin);
            Role roleModer = new Role();
            roleModer.setRolename(Constant.ROLE_MODER);
            roleRepository.save(roleModer);
            Role roleUser = new Role();
            roleUser.setRolename(Constant.ROLE_USER);
            roleRepository.save(roleUser);
            if (!chiaTienUserRepository.existsByUsername("ADMIN")) {
                ChiaTienUser userAdmin = new ChiaTienUser();
                userAdmin.setUsername("ADMIN");
                userAdmin.setFullName("SUPER ADMIN");
                userAdmin.setEmail("");
                userAdmin.setRoles(List.of(roleAdmin));
                userAdmin.setPassword(passwordEncoder.encode("ADMIN1234"));
                chiaTienUserRepository.save(userAdmin);
            }
        }
    }
}
