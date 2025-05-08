package org.example.auditoria.controller;

import org.example.auditoria.dto.CreateUsuarioDTO;
import org.example.auditoria.dto.SignInDTO;
import org.example.auditoria.mapper.UsuarioMapper;
import org.example.auditoria.model.Usuario;
import org.example.auditoria.repository.UsuarioRepository;
import org.example.auditoria.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;

    @Autowired
    UsuarioMapper usuarioMapper;

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody SignInDTO user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.email(),
                        user.senha()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }
    @PostMapping("/signup")
    public String registerUser(@RequestBody CreateUsuarioDTO user) {
        if (userRepository.existsByEmail(user.email())) {
            return "Error: Email já usado!";
        }
        Usuario newUser = usuarioMapper.toEntity(user);
        newUser.setSenha(encoder.encode(user.senha()));
        userRepository.save(newUser);
        return "User registered successfully!";
    }
}
