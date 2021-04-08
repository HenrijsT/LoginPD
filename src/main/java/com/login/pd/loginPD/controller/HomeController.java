package com.login.pd.loginPD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    //default url mapping
    @RequestMapping(value = "/")
    public String home(HttpServletRequest request) {
        request.setAttribute("mode", "MODE_HOME");
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "adminpage";
        } else if (request.isUserInRole("ROLE_USER")) {
            return "userpage";
        } else {
            return "homepage";
        }
    }

    //after login redirects users and admins to proper page
    @RequestMapping(value = "/default")
    public String defaultULR(HttpServletRequest request) {
        request.setAttribute("mode", "MODE_HOME");
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "adminpage";
        } else if (request.isUserInRole("ROLE_USER")) {
            return "userpage";
        } else {
            return "homepage";
        }
    }


}
