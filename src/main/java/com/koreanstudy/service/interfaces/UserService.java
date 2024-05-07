package com.koreanstudy.service.interfaces;

import com.koreanstudy.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> saveUser(UserDTO user);

    List<UserDTO> getAllUsers();

    List<UserDTO> deleteUserById(Long id);

    UserDTO getUserById(Long id);
}
