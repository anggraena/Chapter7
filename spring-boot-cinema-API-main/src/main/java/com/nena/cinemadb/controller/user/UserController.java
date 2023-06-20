package com.nena.cinemadb.controller.user;

import com.nena.cinemadb.repository.UserRepository;
import com.nena.cinemadb.response.ResponseHandler;
import com.nena.cinemadb.security.services.UserDetailsServiceImpl;
import com.nena.cinemadb.model.User;
import com.nena.cinemadb.security.jwt.JwtUtils;
import com.nena.cinemadb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;



    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getUserDetail(@RequestHeader(value = "Authorization")String token) {
        String jwtToken = token.substring(7, token.length());
        User userFound = null;
        if(jwtToken != null && jwtUtils.validateTokenJwt(jwtToken)){
            String username = jwtUtils.getUserNameFromToken(jwtToken);
            userFound = userRepository.findByUname(username).orElseThrow( () -> new UsernameNotFoundException("Uname not found"));

        }
        return ResponseHandler.generateResponse("OK", HttpStatus.OK, userFound.getUname());
    }

    @PatchMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> updateUser(@RequestHeader(value = "Authorization")String token, @RequestBody User user) {
        String jwtToken = token.substring(7, token.length());
        User userFound = null;
        if(jwtToken != null && jwtUtils.validateTokenJwt(jwtToken)){
            String username = jwtUtils.getUserNameFromToken(jwtToken);
            userFound = userRepository.findByUname(username).orElseThrow( () -> new UsernameNotFoundException("Uname not found"));
        }

        return ResponseHandler.generateResponse("OK", HttpStatus.OK, userService.updateUser(userFound.getUserId(), user));
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> deleteUser(@RequestHeader(value = "Authorization") String token){
        String jwtToken = token.substring(7, token.length());
        User userFound = null;
        if(jwtToken != null && jwtUtils.validateTokenJwt(jwtToken)){
            String username = jwtUtils.getUserNameFromToken(jwtToken);
            userFound = userRepository.findByUname(username).orElseThrow( () -> new UsernameNotFoundException("Uname not found"));

        }
        return ResponseHandler.generateResponse("OK", HttpStatus.OK, userService.deleteUser(userFound.getUserId()));
    }

}
