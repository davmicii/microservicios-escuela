package com.escuela.alumnomicroservice.repository;

import com.escuela.alumnomicroservice.entitie.Alumno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AlumnoRepository {
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public AlumnoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    // Método para obtener una conexión
    public Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Transactional
    public void beginTransaction() {
        // La anotación @Transactional se encargará de iniciar la transacción
    }

    @Transactional
    public void commit() {
        // La anotación @Transactional se encargará de confirmar la transacción
    }

    @Transactional
    public void rollback() {
        // La anotación @Transactional se encargará de revertir la transacción
    }

    public void closeConnection(Connection connection) {
        DataSourceUtils.releaseConnection(connection, dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    private final Logger logger = LoggerFactory.getLogger(AlumnoRepository.class);

    @Transactional
    public void createAlumno(Alumno alumno) {
        Connection connection = null;
        try {
            connection = getConnection();
            beginTransaction();

            // Insertar el maestro en la base de datos
            String sql = "INSERT INTO Alumnos (nombre, apellido, Maestro_Id) VALUES (:nombre, :apellido, :Maestro_Id)";

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("nombre", alumno.getNombre())
                    .addValue("apellido", alumno.getApellido())
                    .addValue("Maestro_Id", alumno.getMaestro_Id());

            namedParameterJdbcTemplate.update(sql, params);
            commit();

            logger.info("Alumno guardado exitosamente: nombre={}, apellido={}, Maestro_Id={}",
                    alumno.getNombre(), alumno.getApellido(), alumno.getMaestro_Id());
        } catch (Exception e) {
            logger.error("Error al guardar el alumno: {}", e.getMessage());
            rollback();
        } finally {
            closeConnection(connection);
        }
    }

    @Transactional
    public void updateAlumno(Alumno alumno) throws SQLException {
        Connection connection = getConnection();
        try {
            beginTransaction();

            // Realizar la operación de actualización en la base de datos
            String sql = "UPDATE Alumnos SET nombre = ?, apellido = ?, Maestro_Id WHERE idAlumno = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, alumno.getNombre());
            preparedStatement.setString(2, alumno.getApellido());
            preparedStatement.setLong(3, alumno.getMaestro_Id());
            preparedStatement.setLong(4, alumno.getIdAlumno());

            preparedStatement.executeUpdate();
            commit();
        } catch (Exception e) {
            rollback();
        } finally {
            closeConnection(connection);
        }
    }

    @Transactional
    public void deleteAlumno(int idAlumno) throws SQLException {
        Connection connection = getConnection();
        try {
            beginTransaction();

            // Realizar la operación de eliminación en la base de datos
            String sql = "DELETE FROM Alumnos WHERE idAlumno = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idAlumno);

            preparedStatement.executeUpdate();
            commit();
        } catch (Exception e) {
            rollback();
        } finally {
            closeConnection(connection);
        }
    }

    @Transactional
    public List<Alumno> getAllAlumnos() throws SQLException {
        Connection connection = getConnection();
        List<Alumno> alumnos = new ArrayList<>();

        try {
            // Realizar la operación de consulta en la base de datos
            String sql = "SELECT * FROM Alumnos";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Alumno alumno = new Alumno();
                alumno.setNombre(resultSet.getString("idAlumno"));
                alumno.setNombre(resultSet.getString("nombre"));
                alumno.setApellido(resultSet.getString("apellido"));
                alumno.setMaestro_Id(resultSet.getLong("Maestro_Id"));

                alumnos.add(alumno);
            }
        } finally {
            closeConnection(connection);
        }
        return alumnos;
    }

    @Transactional
    public List<Alumno> getAllAlumnosByMaestro(Long maestroID) throws SQLException {
        Connection connection = getConnection();
        List<Alumno> alumnos = new ArrayList<>();

        try {
            // Realizar la operación de consulta en la base de datos
            String sql = "SELECT * FROM Alumnos WHERE Maestro_Id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, maestroID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Alumno alumno = new Alumno();
                alumno.setNombre(resultSet.getString("nombre"));
                alumno.setApellido(resultSet.getString("apellido"));
                alumno.setMaestro_Id(resultSet.getLong("Maestro_Id"));

                alumnos.add(alumno);
            }
        } finally {
            closeConnection(connection);
        }
        return alumnos;
    }
}
