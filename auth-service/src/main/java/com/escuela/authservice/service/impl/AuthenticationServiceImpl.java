package com.escuela.authservice.service.impl;

import com.escuela.authservice.dto.JwtAuthenticationResponse;
import com.escuela.authservice.dto.SignInRequest;
import com.escuela.authservice.dto.SignUpRequest;
import com.escuela.authservice.entitie.Usuario;
import com.escuela.authservice.repository.UsuarioRepository;
import com.escuela.authservice.service.AuthenticationService;
import com.escuela.authservice.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public Usuario signup(SignUpRequest signUpRequest){
        Usuario user = new Usuario();
        user.setCorreo(signUpRequest.getCorreo());
        user.setContrasenia(signUpRequest.getContrasenia());
        user.setContrasenia(passwordEncoder.encode(signUpRequest.getContrasenia()));

        return usuarioRepository.createUsuario(user);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getCorreo(),
                signInRequest.getContrasenia()));
        var user = usuarioRepository.findByCorreo(signInRequest.getCorreo()).orElseThrow(() -> new IllegalArgumentException(("Correo o contraseña incorrectos")));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }
}
