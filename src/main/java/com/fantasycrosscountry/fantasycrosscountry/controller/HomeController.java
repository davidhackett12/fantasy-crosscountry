package com.fantasycrosscountry.fantasycrosscountry.controller;

import com.fantasycrosscountry.fantasycrosscountry.models.User;
import com.fantasycrosscountry.fantasycrosscountry.models.data.UserDao;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("home")
public class HomeController {

    @Autowired
    public UserDao userDao;

    //routes to homepage//
    @RequestMapping(value = "")
    public String index(Model model, @CookieValue(value = "user", defaultValue = "none") String username){
        if (!username.equals("none")){
            User user = userDao.findByUsername(username);
            model.addAttribute("user", user);
        }
        return "home/index";
    }

    //for displaying the signup page//
    @RequestMapping(value="signup", method = RequestMethod.GET)
    public String displaySignUp(Model model){

        model.addAttribute("user", new User());
        return "home/signup";
    }
    //process the signup and validates the user//
    @RequestMapping(value="signup", method = RequestMethod.POST)
    public String processSignUp(Model model, @ModelAttribute @Valid User user, Errors errors,
                                String verify, HttpServletResponse response){
        User sameName = userDao.findByUsername(user.getUsername());
        //creates a user if everything is valid//
        if(!errors.hasErrors() && user.getPassword().equals(verify) && sameName == null) {
            model.addAttribute("user", user);
            userDao.save(user);
            Cookie cookie = new Cookie("user", user.getUsername());
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:";
        }
        //if things aren't valid//
        else{
            model.addAttribute(user);
            if (!user.getPassword().equals(verify)){
                model.addAttribute("error", "Passwords don't match");
                user.setPassword("");
            }
            if (sameName != null) {
                model.addAttribute("error", "Username is already taken, please select " +
                        "a different one");
            }

            return "home/signup";
        }
    }

    //displays login page//
    @RequestMapping(value="login", method = RequestMethod.GET)
    public String displayLogin(Model model){
        return "home/login";
    }

    //processes login page//
    @RequestMapping(value="login", method = RequestMethod.POST)
    public String processLogin(Model model, String username, String password, HttpServletResponse response){
        User user = userDao.findByUsername(username);
        if (user == null){
            model.addAttribute("error", "Username not found");
            model.addAttribute("username", username);
            return "home/login";
        }
        else if (!user.getPassword().equals(password)){
            model.addAttribute("error", "incorrect password");
            model.addAttribute("username", username);
            return "home/login";
        }

        Cookie cookie = new Cookie("user", user.getUsername());
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:";
    }

    //for logging out, obviously//
    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return "redirect:";
    }

}