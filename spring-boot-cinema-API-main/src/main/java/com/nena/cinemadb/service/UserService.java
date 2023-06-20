package com.nena.cinemadb.service;

import com.nena.cinemadb.repository.UserRepository;
import com.nena.cinemadb.response.ResponseHandler;
import com.nena.cinemadb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repo;


    @Autowired
    PasswordEncoder passwordEncoder;

    ResponseHandler handler = new ResponseHandler();

    //GET ALL USER
    public ResponseEntity<Object> getAllUser(){
        try{
            List<User> allUser = new ArrayList<User>();
            repo.findAll().forEach(allUser::add);

            if(allUser.isEmpty()){
                return handler.generateResponse("no data", HttpStatus.NO_CONTENT, null);
            }
            return handler.generateResponse("data found", HttpStatus.OK, allUser);
        }catch (Exception e){
            return handler.generateResponse("bad request", HttpStatus.BAD_REQUEST, null);
        }
    }

    //ADD USER
    public ResponseEntity<Object> addNewUser(User newUser){
        Optional<User> userExist = repo.findByUname(newUser.getUname());
        if(userExist.isPresent()){
            return handler.generateResponse("failed", HttpStatus.CONFLICT, null);

        }else{
            if(newUser.getUname() != null && newUser.getEmail() != null && newUser.getPassword() != null){
                try{

                    return handler.generateResponse("success", HttpStatus.OK, repo.save(newUser));
                }catch (Exception e){
                    return handler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
                }
            }
        }
        return handler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);

    }

    //DELETE USER
    public ResponseEntity<Object> deleteUser(Long uname){
        try{
            repo.deleteById(uname);
            return handler.generateResponse("success", HttpStatus.OK, null);
        }catch (Exception e){
            return handler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
        }
    }

    //UPDATE USER
    public ResponseEntity<Object> updateUser(Long uname, User user){
        Optional<User> userFound = repo.findById(uname);
        if(userFound.isPresent()){
            User updateUser = userFound.get();
            updateUser.setEmail(user.getEmail());
            updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
            return handler.generateResponse("success", HttpStatus.OK, repo.save(updateUser));

        }else{
            return handler.generateResponse("failed", HttpStatus.BAD_REQUEST, null);
        }
    }
}
