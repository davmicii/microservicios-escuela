package com.escuela.authservice.controller;

import com.escuela.authservice.dto.JwtAuthenticationResponse;
import com.escuela.authservice.dto.SignInRequest;
import com.escuela.authservice.dto.SignUpRequest;
import com.escuela.authservice.entitie.Usuario;
import com.escuela.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UsuarioController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("register")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    /*@PostMapping("/validate")
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
    }*/
}
