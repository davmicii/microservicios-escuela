package com.escuela.alumnomicroservice.controller;

import com.escuela.alumnomicroservice.entitie.Alumno;
import com.escuela.alumnomicroservice.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {
    private final AlumnoService alumnoService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @PostMapping("/insertar")
    public ResponseEntity<String> insertarAlumno(@RequestBody Alumno alumno) {
        try {
            alumnoService.createAlumno(alumno);
            return ResponseEntity.ok("Alumno insertado correctamente");
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Error al insertar alumno: " + e.getMessage());
        }
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> modificarAlumno(@PathVariable Long id, @RequestBody Alumno alumno) {
        alumno.setIdAlumno(id);
        try {
            alumnoService.updateAlumno(alumno);
            return ResponseEntity.ok("Alumno actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar alumno: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{idAlumno}")
    public ResponseEntity<String> eliminarAlumno(@PathVariable int idAlumno) {
        try {
            alumnoService.deleteAlumno(idAlumno);
            return ResponseEntity.ok("Alumno eliminado correctamente");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar alumno");
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Alumno>> listarAlumo() {
        List<Alumno> alumnos = alumnoService.listarAlumnos();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/listar/{Maestro_Id}")
    public ResponseEntity<List<Alumno>> listarAlummoByMaestro(@PathVariable long Maestro_Id) {
        List<Alumno> alumnos = alumnoService.listarAlumnosByIdMaestro(Maestro_Id);
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }
}
