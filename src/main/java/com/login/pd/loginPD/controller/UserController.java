package com.login.pd.loginPD.controller;

import com.login.pd.loginPD.entity.User;
import com.login.pd.loginPD.repository.UserRepository;
import com.login.pd.loginPD.service.LoggingService;
import com.login.pd.loginPD.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    MyUserDetailsService userService;

    @Autowired
    LoggingService loggingservice;

    @Autowired
    UserRepository userRepository;

    //redirects to register GUI
    @RequestMapping("/register")
    public String registration(HttpServletRequest request) {
        request.setAttribute("mode", "MODE_REGISTER");
        return "homepage";
    }

    //saves registered user and logs action
    @PostMapping("/save-user")
    public String registerUser(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        if(!userService.existsByUsername(user.getUsername())) {
            user.setRegDate(new Date().toString());
            user.setRoles("ROLE_USER");
            user.setPassword(userService.encryptPassword(user.getPassword()));
            userService.saveMyUser(user);
            loggingservice.logUserCreated(user.getUsername());
            request.setAttribute("mode", "MODE_HOME");
        } else {
            request.setAttribute("error", "This username already exists");
            request.setAttribute("mode", "MODE_REGISTER");
        }
        return "homepage";
    }

    //redirects to lists(admin POV and user POV) of users
    @RequestMapping("/show-users")
    public String showAllUsers(HttpServletRequest request) {
        request.setAttribute("users", userService.showAllUsers());
        if(request.isUserInRole("ADMIN")) {
            request.setAttribute("mode", "ALL_USERS");
            return "adminpage";
        } else if (request.isUserInRole("USER")) {
            request.setAttribute("mode", "ALL_USERS");
            return "userpage";
        } else {
            request.setAttribute("mode", "MODE_HOME");
            return "homepage";
        }
    }

    //deletes user and logs actions
    @RequestMapping("/delete-user")
    public String deleteUser(@RequestParam int id, HttpServletRequest request) throws IOException {
        loggingservice.logUserDeleted(userRepository.findUserById(id).getUsername());
        userService.deleteMyUser(id);
        request.setAttribute("users", userService.showAllUsers());
        request.setAttribute("mode", "ALL_USERS");
        return "adminpage";
    }

    //redirects to edit user GUI
    @RequestMapping("/edit-user")
    public String editUser(@RequestParam int id, HttpServletRequest request) {
        request.setAttribute("user", userService.editUser(id));
        request.setAttribute("mode", "MODE_UPDATE");
        return "adminpage";
    }

    //saves edited user
    @PostMapping("/edit-save-user")
    public String editSaveUser(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request) throws NoSuchAlgorithmException {
        user.setPassword(userService.encryptPassword(user.getPassword()));
        userService.saveMyUser(user);
        request.setAttribute("mode", "MODE_HOME");
        return "adminpage";
    }

    //redirects to login page
    @RequestMapping(value = "/login")
    public String login (@ModelAttribute User user, HttpServletRequest request) throws IOException {
        return "loginpage";
    }

}
