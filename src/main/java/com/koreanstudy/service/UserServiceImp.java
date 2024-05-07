package com.koreanstudy.service;

import com.koreanstudy.dto.UserDTO;
import com.koreanstudy.dto.UserRegistrationDTO;
import com.koreanstudy.entity.Role;
import com.koreanstudy.entity.User;
import com.koreanstudy.entity.enums.ERole;
import com.koreanstudy.repository.UserRepository;
import com.koreanstudy.service.interfaces.UserService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    UserRepository userRepository;
    RoleServiceImp roleServiceImp;
    BCryptPasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository,
                          RoleServiceImp roleServiceImp,
                          BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleServiceImp = roleServiceImp;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> saveUser(UserDTO userDTO) {
        userRepository.save(mapToUser(userDTO));
        return userRepository.findAll().stream().map((user) -> mapToUserDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map((user) -> mapToUserDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> deleteUserById(Long id) {
        userRepository.deleteById(id);
        return userRepository.findAll().stream().map((user) -> mapToUserDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found for id :" + id));
        return mapToUserDTO(user);
    }

    public ResponseEntity<?> registrationUser(UserRegistrationDTO userRegistrationDTO) {
        ResponseEntity<?> responseEntity;
        if(userRepository.existsByName(userRegistrationDTO.getName())){
            System.out.println("User exist");
            responseEntity = new ResponseEntity<>("user is already exists status code", HttpStatusCode.valueOf(409));
        }else{
            User user = new User();
            user.setName(userRegistrationDTO.getName());
            user.setMail(userRegistrationDTO.getMail());
            user.setPassword(userRegistrationDTO.getPassword());
            Set<Role> roles = new HashSet<>();
            roles.add(roleServiceImp.findByName(ERole.ROLE_USER));
            user.setRoles(roles);
            userRepository.save(user);
            responseEntity = new ResponseEntity<>(user, HttpStatusCode.valueOf(200));
        }

        return responseEntity;
    }

    private UserDTO mapToUserDTO(User user){
        UserDTO userDto = new UserDTO();
        List<String> roles = new ArrayList<>();
        for (Role role:user.getRoles()) {
            if(role.getRole()==ERole.ROLE_ADMIN)roles.add("ADMIN");
            if(role.getRole()==ERole.ROLE_USER)roles.add("USER");
        }
        userDto.setRoles(roles);
        userDto.setName(user.getName());
        userDto.setMail(user.getMail());
        userDto.setPassword(user.getPassword());
        userDto.setId(user.getId());
        return userDto;
    }

    private User mapToUser(UserDTO userDTO){
        Set<Role> roles = new HashSet<>();
        if(userDTO.getRoles() == null){
            roles.add(roleServiceImp.findByName(ERole.ROLE_USER));
        }else{
            if(userDTO.getRoles().contains("USER")){
                roles.add(roleServiceImp.findByName(ERole.ROLE_USER));
            }if(userDTO.getRoles().contains("ADMIN")){
                roles.add(roleServiceImp.findByName(ERole.ROLE_ADMIN));
            }
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setMail(userDTO.getMail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(roles);
        return user;
    }

    public Optional<User> findUserByEmail(String mail) {
        return userRepository.findByMail(mail);
    }
}
