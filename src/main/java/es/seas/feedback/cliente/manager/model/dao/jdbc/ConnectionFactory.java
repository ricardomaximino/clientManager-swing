package es.seas.feedback.cliente.manager.model.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Esta clase es una fabrica de conecciones con la base de datos.
 * @author Ricardo Maximino
 * <p>Esta clase crea conecciones utilizando JDBC API.</p>
 */
public final class ConnectionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);
    private static Connection conn = null;
    private static final String URL ="jdbc:mysql://localhost:3306/client_manager";
    private static final String USER="root";
    private static final String PASSWORD="root";
    
    /**
     * Este metodo retorna una nueva conección con la base de datos.
     * @return del tipo java.sql.Connection.
     */
    public static Connection getConnection(){
        
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            LOG.error("No fue posible conectar a la base de datos",ex);
        }
        return conn;
    }
}
