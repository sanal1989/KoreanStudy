package com.koreanstudy.controller.restcontroller;

import com.koreanstudy.dto.UserDTO;
import com.koreanstudy.service.UserServiceImp;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    UserServiceImp userServiceImp;

    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<UserDTO> addUser(@RequestBody UserDTO user){
        return userServiceImp.saveUser(user);
    }


    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<UserDTO> deleteUser(@PathVariable Long id){
        return userServiceImp.deleteUserById(id);
    }

    @GetMapping(value = "/{id}")
    public UserDTO getUser(@PathVariable Long id){
        return userServiceImp.getUserById(id);
    }
}
