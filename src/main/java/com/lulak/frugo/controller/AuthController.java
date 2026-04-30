package com.lulak.frugo.controller;

import com.lulak.frugo.dto.LoginRequest;
import com.lulak.frugo.model.User;
import com.lulak.frugo.repository.UserRepository;
import com.lulak.frugo.security.JwtSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtSecurity jwtSecurity;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtSecurity jwtSecurity){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtSecurity = jwtSecurity;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request){

        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if(user == null){
            return Map.of(
                    "success", false,
                    "message", "User not found"
            );
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return Map.of(
                    "success", false,
                    "message", "Wrong password"
            );
        }

        String token = jwtSecurity.generateToken(user);

        return Map.of(
                "success", true,
                "token", token,
                "role", user.getRole(),
                "username", user.getUsername()
        );
    }
}
