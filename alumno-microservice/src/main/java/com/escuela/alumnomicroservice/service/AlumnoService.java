package com.escuela.alumnomicroservice.service;

import com.escuela.alumnomicroservice.entitie.Alumno;
import com.escuela.alumnomicroservice.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;

    @Autowired
    public AlumnoService (AlumnoRepository alumno){
        this.alumnoRepository = alumno;
    }

    public void createAlumno(Alumno alumno) throws SQLException {
        alumnoRepository.createAlumno(alumno);
    }

    public void updateAlumno(Alumno alumno) throws SQLException {
        alumnoRepository.updateAlumno(alumno);
    }

    public void deleteAlumno(int id) throws SQLException {
        alumnoRepository.deleteAlumno(id);
    }

    public List<Alumno> listarAlumnos() {
        try {
            return alumnoRepository.getAllAlumnos();
        } catch (SQLException e) {
            // Manejar la excepción apropiadamente, por ejemplo, lanzar una excepción personalizada o registrar el error.
            throw new RuntimeException("Error al listar alumnos", e);
        }
    }
    public List<Alumno> listarAlumnosByIdMaestro(Long id) {
        try {
            return alumnoRepository.getAllAlumnosByMaestro(id);
        } catch (SQLException e) {
            // Manejar la excepción apropiadamente, por ejemplo, lanzar una excepción personalizada o registrar el error.
            throw new RuntimeException("Error al listar alumnos", e);
        }
    }
}
