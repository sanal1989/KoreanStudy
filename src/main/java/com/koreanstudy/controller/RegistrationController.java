package com.koreanstudy.controller;

import com.koreanstudy.dto.UserDTO;
import com.koreanstudy.entity.User;
import com.koreanstudy.service.UserServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class RegistrationController {

    UserServiceImp userServiceImp;

    public RegistrationController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") UserDTO userDto,
                               BindingResult result,
                               Model model){
        Optional<User> existingUser = userServiceImp.findUserByEmail(userDto.getMail());

        if(existingUser.isPresent()){
            result.rejectValue("mail", null,
                    "Пользователь с такой почтой уже существует!");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userServiceImp.saveUser(userDto);
        return "redirect:/register?success";
    }
}
