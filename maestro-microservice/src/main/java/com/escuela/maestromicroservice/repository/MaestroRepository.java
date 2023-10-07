package com.escuela.maestromicroservice.repository;

import com.escuela.maestromicroservice.entitie.Maestro;
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
public class MaestroRepository {
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public MaestroRepository(DataSource dataSource) {
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

    private final Logger logger = LoggerFactory.getLogger(MaestroRepository.class);

    @Transactional
    public void createMaestro(Maestro maestro) {
        Connection connection = null;
        try {
            connection = getConnection();
            beginTransaction();

            // Insertar el maestro en la base de datos
            String sql = "INSERT INTO Maestro (nombre, apellido, titulo) VALUES (:nombre, :apellido, :titulo)";

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("nombre", maestro.getNombre())
                    .addValue("apellido", maestro.getApellido())
                    .addValue("titulo", maestro.getTitulo());

            namedParameterJdbcTemplate.update(sql, params);
            commit();

            logger.info("Maestro guardado exitosamente: nombre={}, apellido={}, titulo={}",
                    maestro.getNombre(), maestro.getApellido(), maestro.getTitulo());
        } catch (Exception e) {
            logger.error("Error al guardar el maestro: {}", e.getMessage());
            rollback();
        } finally {
            closeConnection(connection);
        }
    }

    @Transactional
    public void updateMaestro(Maestro maestro) throws SQLException {
        Connection connection = getConnection();
        try {
            beginTransaction();

            // Realizar la operación de actualización en la base de datos
            String sql = "UPDATE Maestro SET nombre = ?, apellido = ?, titulo = ? WHERE idMaestro = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maestro.getNombre());
            preparedStatement.setString(2, maestro.getApellido());
            preparedStatement.setString(3, maestro.getTitulo());
            preparedStatement.setLong(4, maestro.getIdMaestro());

            preparedStatement.executeUpdate();
            commit();
        } catch (Exception e) {
            rollback();
        } finally {
            closeConnection(connection);
        }
    }

    @Transactional
    public void deleteMaestro(int idMaestro) throws SQLException {
        Connection connection = getConnection();
        try {
            beginTransaction();

            // Realizar la operación de eliminación en la base de datos
            String sql = "DELETE FROM Maestro WHERE idMaestro = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMaestro);

            preparedStatement.executeUpdate();
            commit();
        } catch (Exception e) {
            rollback();
        } finally {
            closeConnection(connection);
        }
    }

    @Transactional
    public List<Maestro> getAllMaestros() throws SQLException {
        Connection connection = getConnection();
        List<Maestro> maestros = new ArrayList<>();

        try {
            // Realizar la operación de consulta en la base de datos
            String sql = "SELECT * FROM Maestro";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Maestro maestro = new Maestro();
                maestro.setIdMaestro(resultSet.getLong("idMaestro"));
                maestro.setNombre(resultSet.getString("nombre"));
                maestro.setApellido(resultSet.getString("apellido"));
                maestro.setTitulo(resultSet.getString("titulo"));

                maestros.add(maestro);
            }
        } finally {
            closeConnection(connection);
        }
        return maestros;
    }

}
