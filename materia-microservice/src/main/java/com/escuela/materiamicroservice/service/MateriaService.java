package com.escuela.materiamicroservice.service;

import com.escuela.materiamicroservice.entitie.Materia;
import com.escuela.materiamicroservice.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MateriaService {
    private final MateriaRepository maestroRepository;

    @Autowired
    public MateriaService (MateriaRepository maestro){
        this.maestroRepository = maestro;
    }

    public void createMateria(Materia maestro) throws SQLException {
        maestroRepository.createMateria(maestro);
    }

    public void updateMateria(Materia maestro) throws SQLException {
        maestroRepository.updateMateria(maestro);
    }

    public void deleteMateria(int id) throws SQLException {
        maestroRepository.deleteMateria(id);
    }

    public List<Materia> listarMaterias() {
        try {
            return maestroRepository.getAllMaterias();
        } catch (SQLException e) {
            // Manejar la excepción apropiadamente, por ejemplo, lanzar una excepción personalizada o registrar el error.
            throw new RuntimeException("Error al listar materias", e);
        }
    }
}
