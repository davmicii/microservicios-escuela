package com.escuela.authservice.service;

import com.escuela.authservice.dto.SignUpRequest;
import com.escuela.authservice.entitie.Usuario;

public interface AuthenticationService {
    Usuario signup(SignUpRequest signUpRequest);
}
