package com.escuela.authservice.service;

import com.escuela.authservice.dto.JwtAuthenticationResponse;
import com.escuela.authservice.dto.SignInRequest;
import com.escuela.authservice.dto.SignUpRequest;
import com.escuela.authservice.entitie.Usuario;

public interface AuthenticationService {
    Usuario signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
}
