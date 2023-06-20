package com.nena.cinemadb.controller.general;

import com.nena.cinemadb.DTO.JwtResponse;
import com.nena.cinemadb.DTO.LoginRequest;
import com.nena.cinemadb.DTO.MessageResponse;
import com.nena.cinemadb.DTO.SignupRequest;
import com.nena.cinemadb.model.ERole;
import com.nena.cinemadb.model.Role;
import com.nena.cinemadb.model.User;
import com.nena.cinemadb.repository.RoleRepository;
import com.nena.cinemadb.repository.UserRepository;
import com.nena.cinemadb.security.jwt.JwtUtils;
import com.nena.cinemadb.security.services.UserDetailsImpl;
import com.nena.cinemadb.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/public")
public class PublicController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    FilmService service;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        if(userRepository.existsByUname(signupRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username already taken"));
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email already taken"));
        }

        //create user account
        User user = new User(signupRequest.getEmail(), signupRequest.getUsername(),passwordEncoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if(strRoles == null){
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: role is not found"));
            roles.add(userRole);
        } else{
            strRoles.forEach(role -> {
                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    //GET NOW SHOWING FILM
    @GetMapping("/schedules/nowshow")
    public ResponseEntity<Object> getFilmNowShow(){
        return service.getFilmNowShowing();
    }

    //GET COMING SOON FILM
    @GetMapping("/schedules/comingsoon")
    public ResponseEntity<Object> getComingSoon(){
        return service.getComingSoonFilm();
    }



    //GET FILM SCHEDULE
    @GetMapping("/schedules/{filmCode}")
    public ResponseEntity<Object> getAllSchedule(@PathVariable String filmCode){
        return service.getBySchedules(filmCode);
    }

}
