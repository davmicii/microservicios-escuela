package com.escuela.materiamicroservice.repository;

import com.escuela.materiamicroservice.entitie.Materia;
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
public class MateriaRepository {

    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public MateriaRepository(DataSource dataSource) {
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

    private final Logger logger = LoggerFactory.getLogger(MateriaRepository.class);

    @Transactional
    public void createMateria(Materia materia) {
        Connection connection = null;
        try {
            connection = getConnection();
            beginTransaction();

            // Insertar el maestro en la base de datos
            String sql = "INSERT INTO Materia (descripcion, puntos) VALUES (:descripcion, :puntos)";

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("descripcion", materia.getDescripcion())
                    .addValue("puntos", materia.getPuntos());

            namedParameterJdbcTemplate.update(sql, params);
            commit();

            logger.info("Maestro guardado exitosamente: nombre={}, apellido={}, titulo={}",
                    materia.getDescripcion(), materia.getPuntos());
        } catch (Exception e) {
            logger.error("Error al guardar la materia: {}", e.getMessage());
            rollback();
        } finally {
            closeConnection(connection);
        }
    }

    @Transactional
    public void updateMateria(Materia materia) throws SQLException {
        Connection connection = getConnection();
        try {
            beginTransaction();

            // Realizar la operación de actualización en la base de datos
            String sql = "UPDATE Materia SET descripcion = ?, puntos = ? WHERE idMateria = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, materia.getDescripcion());
            preparedStatement.setString(2, materia.getPuntos());
            preparedStatement.setLong(3, materia.getIdMateria());

            preparedStatement.executeUpdate();
            commit();
        } catch (Exception e) {
            rollback();
        } finally {
            closeConnection(connection);
        }
    }

    @Transactional
    public void deleteMateria(int idMateria) throws SQLException {
        Connection connection = getConnection();
        try {
            beginTransaction();

            // Realizar la operación de eliminación en la base de datos
            String sql = "DELETE FROM Materia WHERE idMateria = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idMateria);

            preparedStatement.executeUpdate();
            commit();
        } catch (Exception e) {
            rollback();
        } finally {
            closeConnection(connection);
        }
    }

    @Transactional
    public List<Materia> getAllMaterias() throws SQLException {
        Connection connection = getConnection();
        List<Materia> materias = new ArrayList<>();

        try {
            // Realizar la operación de consulta en la base de datos
            String sql = "SELECT * FROM Materia";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Materia materia = new Materia();
                materia.setIdMateria(resultSet.getLong("idMateria"));
                materia.setDescripcion(resultSet.getString("descripcion"));
                materia.setPuntos(resultSet.getString("puntos"));

                materias.add(materia);
            }
        } finally {
            closeConnection(connection);
        }
        return materias;
    }
}
