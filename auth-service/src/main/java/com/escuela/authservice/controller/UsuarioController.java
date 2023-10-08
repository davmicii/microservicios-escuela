package com.escuela.authservice.controller;

import com.escuela.authservice.dto.TokenDto;
import com.escuela.authservice.dto.UsuarioDto;
import com.escuela.authservice.entitie.Usuario;
import com.escuela.authservice.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UsuarioDto dto){
        TokenDto tokenDto = usuarioService.login(dto);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token){
        TokenDto tokenDto = usuarioService.validate(token);
        if(tokenDto == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/create")
    public ResponseEntity<Usuario> create(@RequestBody UsuarioDto dto){
        Usuario usuario = usuarioService.create(dto);
        if(usuario == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(usuario);
    }
}
