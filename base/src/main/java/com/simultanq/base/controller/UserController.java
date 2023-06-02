package com.simultanq.base.controller;

import com.simultanq.base.response.LoginMessage;
import com.simultanq.base.entity.User;
import com.simultanq.base.entity.dto.LoginDTO;
import com.simultanq.base.entity.dto.UserDTO;
import com.simultanq.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

//    @PostMapping(path = "/save")
//    public String saveUser(@RequestBody UserDTO userDTO){
//        String id= userService.addUser(userDTO);
//        return id;
//    }

    @PostMapping(path = "/save")
    public User saveUser(@RequestBody UserDTO userDTO){
        return userService.addUser(userDTO);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO){
        LoginMessage loginMessage = userService.loginUser(loginDTO);
        return ResponseEntity.ok(loginMessage);
    }

}
