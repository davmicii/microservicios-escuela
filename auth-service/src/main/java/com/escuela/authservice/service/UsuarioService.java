package com.escuela.authservice.service;

import com.escuela.authservice.dto.TokenDto;
import com.escuela.authservice.dto.UsuarioDto;
import com.escuela.authservice.entitie.Usuario;
import com.escuela.authservice.repository.UsuarioRepository;
import com.escuela.authservice.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    public Usuario create(UsuarioDto dto){
        Optional<Usuario> user = usuarioRepository.findByCorreo(dto.getCorreo());
        if(!user.isPresent())
            return null;
        String password = passwordEncoder.encode(dto.getContrasenia());
        Usuario usuario = Usuario.builder()
                .idUsuario(dto.getIdUsuario())
                .correo(dto.getCorreo())
                .contrasenia(password)
                .build();
        return usuarioRepository.createUsuario(usuario);
    }

    public TokenDto login(UsuarioDto dto){
        Optional<Usuario> user = usuarioRepository.findByCorreo(dto.getCorreo());
        if(!user.isPresent())
            return null;
        if(passwordEncoder.matches(dto.getContrasenia(), user.get().getContrasenia()))
            return new TokenDto(jwtProvider.createToken(user.get()));
        return null;
    }

    public TokenDto validate(String token){
        if(!jwtProvider.validate(token))
            return null;
        String correo = jwtProvider.getCorreoFromToken(token);
        if(!usuarioRepository.findByCorreo(correo).isPresent())
            return null;
        return new TokenDto(token);
    }
}
