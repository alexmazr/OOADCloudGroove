package com.cloudgroove.userservice.controller;

import com.cloudgroove.userservice.entity.User;
import com.cloudgroove.userservice.repository.UserRepository;
import com.cloudgroove.userservice.util.SessionSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UserController
{
    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/api/signup", method = RequestMethod.POST)
    public String userSignup (@RequestParam("email") String email, @RequestParam("password") String password) {
        if (userRepository.findUserByEmail(email) != null) return "failure-ae";


        // Create new user via build pattern and save to DB
        User newUser = User.builder()
                .email(email)
                .password(password)
                .build();

        // Add user to database
        userRepository.save(newUser);

        SessionSingleton.loginNewUser(newUser);

        return newUser.getUserId();
    }

    @RequestMapping(path = "/api/login", method = RequestMethod.POST)
    public String userLogin (@RequestParam("email") String email, @RequestParam("password") String password) {

        User newUser = userRepository.findUserByEmail(email);

        if (newUser == null) return "failure-dne";
        if (!newUser.getPassword().equals(password)) return "failure-password";

        SessionSingleton.loginNewUser(newUser);

        return newUser.getUserId();
    }

    @RequestMapping(path = "/api/isLoggedIn", method = RequestMethod.GET)
    public boolean getSession (@RequestParam("userId") String userId) {
        if (SessionSingleton.getInstance().getUserId().equals(userId)) return true;
        else return false;
    }
}
