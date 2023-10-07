package com.escuela.maestromicroservice.service;

import com.escuela.maestromicroservice.entitie.Maestro;
import com.escuela.maestromicroservice.repository.MaestroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MaestroService {

    private final MaestroRepository maestroRepository;

    @Autowired
    public MaestroService (MaestroRepository maestro){
        this.maestroRepository = maestro;
    }

    public void createMaestro(Maestro maestro) throws SQLException {
        maestroRepository.createMaestro(maestro);
    }

    public void updateMaestro(Maestro maestro) throws SQLException {
        maestroRepository.updateMaestro(maestro);
    }

    public void deleteMaestro(int id) throws SQLException {
        maestroRepository.deleteMaestro(id);
    }

    public List<Maestro> listarMaestros() {
        try {
            return maestroRepository.getAllMaestros();
        } catch (SQLException e) {
            // Manejar la excepción apropiadamente, por ejemplo, lanzar una excepción personalizada o registrar el error.
            throw new RuntimeException("Error al listar maestros", e);
        }
    }

}
