package com.koreanstudy.utils;

import com.koreanstudy.dto.UserDTO;
import com.koreanstudy.entity.Role;
import com.koreanstudy.entity.User;
import com.koreanstudy.entity.enums.ERole;
import com.koreanstudy.repository.RoleRepository;
import com.koreanstudy.service.UserServiceImp;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MyRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserServiceImp userServiceImp;

    public MyRunner(RoleRepository roleRepository,
                    UserServiceImp userServiceImp) {
        this.roleRepository = roleRepository;
        this.userServiceImp = userServiceImp;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!roleRepository.existsByRole(ERole.ROLE_ADMIN)){
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
        if(!roleRepository.existsByRole(ERole.ROLE_USER)){
            roleRepository.save(new Role(ERole.ROLE_USER));
        }

//        UserDTO userDTO = new UserDTO();
//        userDTO.setMail("1@1");
//        userDTO.setPassword("1");
//        userDTO.setName("ADMIN");
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add("ADMIN");
//        userDTO.setRoles(arrayList);
//        userServiceImp.saveUser(userDTO);

    }
}
