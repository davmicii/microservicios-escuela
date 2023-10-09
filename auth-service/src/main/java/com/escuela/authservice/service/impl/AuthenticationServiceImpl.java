package com.escuela.authservice.service.impl;

import com.escuela.authservice.dto.SignUpRequest;
import com.escuela.authservice.entitie.Role;
import com.escuela.authservice.entitie.Usuario;
import com.escuela.authservice.repository.UsuarioRepository;
import com.escuela.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario signup(SignUpRequest signUpRequest){
        Usuario user = new Usuario();
        user.setCorreo(signUpRequest.getCorreo());
        user.setContrasenia(signUpRequest.getContrasenia());
        user.setRole(Role.USER);
        user.setContrasenia(passwordEncoder.encode(signUpRequest.getContrasenia()));

        return usuarioRepository.createUsuario(user);
    }
}
