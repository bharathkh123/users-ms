package com.bharathworks.usersms.controller;

import com.bharathworks.usersms.service.UserService;
import com.bharathworks.usersms.vo.ResponseVo;
import com.bharathworks.usersms.vo.UserDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

   @PostMapping("/")
    public ResponseEntity<UserDetailsDTO> saveUsers(@RequestBody UserDetailsDTO userDetailsDTO){
         return ResponseEntity.ok().body(userService.saveUsers(userDetailsDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseVo> getUserDetails(@PathVariable("userId") Long userId){

       return ResponseEntity.ok().body(userService.getUserDetails(userId));
    }
}
