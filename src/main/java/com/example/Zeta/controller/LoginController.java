package com.example.Zeta.controller;

import com.example.Zeta.dto.AdminDto;
import com.example.Zeta.model.Admin;
import com.example.Zeta.service.impl.AdminServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLOutput;

@Controller
public class LoginController {
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("title","Вход");
        return "login";
    };

    @RequestMapping("/index")
    public String home(Model model){
        model.addAttribute("title","Добро пожаловать!");
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("title","Регистрация");
        model.addAttribute("adminDto",new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        model.addAttribute("title","Забыли пароль?");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid  @ModelAttribute("adminDto")AdminDto adminDto,
                              BindingResult result,
                              Model model){

        try{

            if(result.hasErrors()){
                model.addAttribute("adminDto",adminDto);
                result.toString();
                return "register";
            }
            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);
            if(admin != null){
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("emailError","Емайл уже Зарегистрирован!!!");
                System.out.println("Admin not null");
                return "register";
            }
            if(adminDto.getPassword().equals(adminDto.getRepeatPassword())){
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("success");
                model.addAttribute("success","Вы удачно зарегистрировались!");
                model.addAttribute("adminDto",adminDto);
            }else {
                model.addAttribute("adminDto",adminDto);
                model.addAttribute("passwordError","Пороли не совподают!!!!");
                System.out.println("PASSWORD NOT SAME");
                return "register";
            }

        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("errors","Ошибка на сервере ! Повторите позже!");
        }

        return "register";
    }

}
