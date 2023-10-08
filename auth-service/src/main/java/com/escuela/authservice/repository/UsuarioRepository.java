package com.escuela.authservice.repository;

import com.escuela.authservice.entitie.Usuario;
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
import java.util.Optional;

@Component
public class UsuarioRepository {
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UsuarioRepository(DataSource dataSource) {
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

    private final Logger logger = LoggerFactory.getLogger(UsuarioRepository.class);

    @Transactional
    public Usuario createUsuario(Usuario usuario) {
        Connection connection = null;
        try {
            connection = getConnection();
            beginTransaction();

            // Insertar el usuario en la base de datos
            String sql = "INSERT INTO Usuario (correo, contrasenia) VALUES (:correo, :contrasenia)";

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("correo", usuario.getCorreo())
                    .addValue("contrasenia", usuario.getContrasenia());

            namedParameterJdbcTemplate.update(sql, params);
            commit();

            logger.info("Usuario guardado exitosamente: correo={}, contrasenia={}",
                    usuario.getCorreo(), usuario.getContrasenia());

            // Devuelve el usuario creado
            return usuario;
        } catch (Exception e) {
            logger.error("Error al guardar usuario: {}", e.getMessage());
            rollback();
            return null;
        } finally {
            closeConnection(connection);
        }
    }


    @Transactional
    public List<Usuario> getAllUsuarios() throws SQLException {
        Connection connection = getConnection();
        List<Usuario> usuarios = new ArrayList<>();

        try {
            // Realizar la operación de consulta en la base de datos
            String sql = "SELECT * FROM Usuario";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getLong("idUsuario"));
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setContrasenia(resultSet.getString("contrasenia"));
                usuarios.add(usuario);
            }
        } finally {
            closeConnection(connection);
        }
        return usuarios;
    }

    @Transactional
    public Optional<Usuario> findByCorreo(String correo) {
        try {
            Connection connection = getConnection();
            String sql = "SELECT * FROM Usuario WHERE correo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, correo);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setContrasenia(resultSet.getString("constrasenia"));
                return Optional.of(usuario);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
