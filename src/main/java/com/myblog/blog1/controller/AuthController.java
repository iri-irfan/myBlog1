package com.myblog.blog1.controller;


import com.myblog.blog1.entity.Role;
import com.myblog.blog1.entity.User;
import com.myblog.blog1.payload.LoginDto;
import com.myblog.blog1.payload.UserDto;
import com.myblog.blog1.repository.RoleRepository;
import com.myblog.blog1.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private  AuthenticationManager  authenticationManager;
    private  RoleRepository roleRepository;
    private  PasswordEncoder passwordEncoder;
    private  ModelMapper modelMapper;
    private  UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          ModelMapper modelMapper,
                          UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){

        if (userRepository.existsByEmail(userDto.getEmail())){
            return new ResponseEntity<>("Email taken", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByUsername(userDto.getUsername())){
            return new ResponseEntity<>("Username taken", HttpStatus.BAD_REQUEST);
        }
        User user = mapToEntity(userDto);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            Role role = roleRepository.findByName(userDto.getRoleType()).get();

        Set<Role> convertRoleToSet = new HashSet<>();
        convertRoleToSet.add(role);
        user.setRoles(convertRoleToSet);

        userRepository.save(user);
        return new ResponseEntity<>("User Registered Sucessfully",HttpStatus.CREATED);
    }


    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Signed in Succesfully!!", HttpStatus.OK);

    }




    User mapToEntity(UserDto userDto){

        User user = modelMapper.map(userDto, User.class);
        return user;
    }




}
