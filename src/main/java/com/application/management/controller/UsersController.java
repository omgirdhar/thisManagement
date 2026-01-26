package com.application.management.controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.application.management.model.User;
import com.application.management.service.UserService;


@Controller
public class UsersController {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    public UsersController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // SHOW USERS PAGE
    @GetMapping("/users")
    public ModelAndView usersPage() {
        ModelAndView view = new ModelAndView("users");
        view.addObject("newUser", new User());
        view.addObject("userList", userService.getAllUsers());
        return view;
    }

    // SAVE USER
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("newUser") User user) {
    	if (user.getId() == 0) { // new user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.saveUser(user);
        return "redirect:/users";
    }
    
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("existingUser") User user) {
    	if(userService.getUserById(user.getId()) != null) {
    		userService.saveUser(user);
    	}
    	return "redirect:/users";
    }

    // DELETE USER
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
    
    @GetMapping("/updateUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id) {
    	return userService.getUserById(id);
    }

}
