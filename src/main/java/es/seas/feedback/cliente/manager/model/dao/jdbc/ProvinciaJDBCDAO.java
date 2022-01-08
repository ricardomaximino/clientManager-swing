package es.seas.feedback.cliente.manager.model.dao.jdbc;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import es.seas.feedback.cliente.manager.model.Localidade;
import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.model.dao.hibernate.ProvinciaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta classe tiene la misma funcionalidades y implementa la misma interfaz 
 * que el ProvinciaDAOImpl, la única diferencia es que esta clase utiliza JDBC y la
 * clase ProvinciaDAOImpl utiliza Hibernate.
 *
 * @author Ricardo Maximino<br><br>
 */
public class ProvinciaJDBCDAO implements ProvinciaDAO {

    private static final Logger LOG = LoggerFactory.getLogger(ProvinciaJDBCDAO.class);

    Connection con;
    PreparedStatement stm;
    ResultSet rs;
    ResultSet rsl;

    /**
     * Este es el constructor sin argumento, el único constructor implementado.
     * <br>
     * Este constructor hace dos cosas:
     * <ul>
     * <li>LOG.info("This class starts."</li>
     * <li>createTables() // IF NOT EXISTS</li>
     * </ul>
     */
    public ProvinciaJDBCDAO(){
        LOG.info("This class starts.");
        createTables();
    }
    private Connection getConnection() {
        return ConnectionFactory.getConnection();
    }

    /**
     * Este metodo guarda Provincia en la base de datos y retorna un valor 
     * booleano para informar si la Provincia haya sido guardada correctamente.
     * @param provincia del tipo es.seas.feedback.cliente.manager.model.Provincia.
     * @return del tipo boolean.
     * <p>
     * Este metodo guarda un nuevo registro y también atualiza uno ya 
     * existente.
     * </p>
     * <p>
     * Se guarda y atualiza no sólo la provincia como también las localidades.
     * </p>
     */
    @Override
    public boolean guardarProvincia(Provincia provincia) {
        Integer localidadeId;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "INSERT INTO provincias (id,nombre) VALUES(?,?)";
        boolean resultado = false;
        try {
            con.setAutoCommit(false);
            stm = con.prepareStatement("SELECT * FROM provincias WHERE id=?");
            stm.setLong(1, provincia.getId());
            rs = stm.executeQuery();
            if (!rs.next()) {
                stm = con.prepareStatement(sql);
                stm.setLong(1, provincia.getId());
                stm.setString(2, provincia.getNombre());
                resultado = stm.executeUpdate() > 0;
                if (provincia.getLocalidades().size() > 0) {
                    for (Localidade l : provincia.getLocalidades()) {
                        stm = con.prepareStatement("INSERT INTO localidades "
                                + "(nombre,provincia) VALUES(?,?)",
                                Statement.RETURN_GENERATED_KEYS);

                        stm.setString(1, l.getNombre());
                        stm.setString(2, provincia.getNombre());
                        localidadeId = stm.executeUpdate();
                        // if(contactoId != 1 significa que hay registro duplicado
                        if (localidadeId != 1) {
                            LOG.error("if(contactoId != 1 means that in the database"
                                    + " repited line or some constrain that do not "
                                    + "allow new insert into contactos. In the method "
                                    + "añadirNuevoRegistro.");
                        }
                        rs = stm.getGeneratedKeys();
                        rs.first();
                        localidadeId = rs.getInt(1);
                        stm = con.prepareStatement("INSERT INTO provincias_localidades "
                                + "(provincias_id,localidades_id) VALUES(?,?)");
                        stm.setLong(1, provincia.getId());
                        stm.setLong(2, localidadeId);
                        stm.executeUpdate();
                    }
                }
            } else if (provincia.getLocalidades().size() > 0) {
                for (Localidade l : provincia.getLocalidades()) {
                    if (l.getId() < 1) {
                        stm = con.prepareStatement("INSERT INTO localidades "
                                + "(nombre,provincia) VALUES(?,?)",
                                Statement.RETURN_GENERATED_KEYS);

                        stm.setString(1, l.getNombre());
                        stm.setString(2, provincia.getNombre());
                        localidadeId = stm.executeUpdate();
                        // if(contactoId != 1 significa que hay registro duplicado
                        if (localidadeId != 1) {
                            LOG.error("if(contactoId != 1 means that in the database"
                                    + " repited line or some constrain that do not "
                                    + "allow new insert into contactos. In the method "
                                    + "añadirNuevoRegistro.");
                        }
                        rs = stm.getGeneratedKeys();
                        rs.first();
                        localidadeId = rs.getInt(1);
                        stm = con.prepareStatement("INSERT INTO provincias_localidades "
                                + "(provincias_id,localidades_id) VALUES(?,?)");
                        stm.setLong(1, provincia.getId());
                        stm.setLong(2, localidadeId);
                        stm.executeUpdate();
                    } else {
                        stm = con.prepareStatement("UPDATE localidades SET nombre=?,provincia=? WHERE id=?");
                        stm.setString(1, l.getNombre());
                        stm.setString(2, l.getProvincia());
                        stm.setLong(3, l.getId());
                    }
                }
                resultado = true;
            }
            stm.close();
            con.commit();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method guardarProvincia 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method guardarProvincia 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method guardarProvincia 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return resultado;
    }

    /**
     * Este metodo busca una provincia por el nombre.
     * @param nombre del tipo String.
     * @return del tipo es.seas.feedback.cliente.manager.model.Provincia.
     * <p>
     * En caso de no se encuentre la provincia buscada el metodo retornará null.
     * </p>
     */
    @Override
    public Provincia getProvincia(String nombre) {
        Provincia provincia = null;
        con = getConnection();
        stm = null;
        rs = null;
        rsl = null;
        String sql = "SELECT * FROM provincias WHERE nombre=?";
        try {
            con.setAutoCommit(false);
            stm = con.prepareStatement(sql);
            stm.setString(1, nombre);
            rs = stm.executeQuery();
            if (rs.next()) {
                provincia = new Provincia(rs.getString("nombre"), rs.getLong("id"));
                stm = con.prepareStatement("SELECT localidades_id FROM provincias_localidades WHERE provincias_id=?");
                stm.setLong(1, provincia.getId());
                rs = stm.executeQuery();
                while (rs.next()) {
                    stm = con.prepareStatement("SELECT * FROM localidades WHERE id=? ORDER BY nombre");
                    stm.setLong(1, rs.getLong(1));
                    rsl = stm.executeQuery();
                    Localidade localidade;
                    //este if solamente es necesario si haya datos corrompidos.
                    if (rsl.next()) {
                        localidade = new Localidade(rsl.getString("nombre"), rsl.getString("provincia"));
                        localidade.setId(rsl.getLong("id"));
                        provincia.getLocalidades().add(localidade);
                    }
                }
            }
            stm.close();
            con.commit();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method getProvincia 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method getProvincia 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method getProvincia 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return provincia;
    }

    /**
     * Este metodo lista todas las provincias registradas en la base de datos.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Provincia&gt;
     * <p>
     * En caso de que no hay ninguna provincia registrada el la base de datos,
     * el metodo retornará una lista vacia.
     * </p>
     */
    @Override
    public List<Provincia> getLista() {
        List<Provincia> lista = new ArrayList<>();
        Provincia provincia = null;
        con = getConnection();
        stm = null;
        rs = null;
        rsl = null;
        ResultSet rspl = null;
        String sql = "SELECT * FROM provincias ORDER BY nombre";
        try {
            con.setAutoCommit(false);
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()){
                provincia = new Provincia(rs.getString("nombre"), rs.getLong("id"));
                stm = con.prepareStatement("SELECT localidades_id FROM provincias_localidades WHERE provincias_id=?");
                stm.setLong(1, provincia.getId());
                rspl = stm.executeQuery();
                while (rspl.next()) {
                    stm = con.prepareStatement("SELECT * FROM localidades WHERE id=? ORDER BY nombre");
                    stm.setLong(1, rspl.getLong(1));
                    rsl = stm.executeQuery();
                    Localidade localidade;
                    //este if solamente es necesario si haya datos corrompidos.
                    if (rsl.next()) {
                        localidade = new Localidade(rsl.getString("nombre"), rsl.getString("provincia"));
                        localidade.setId(rsl.getLong("id"));
                        provincia.getLocalidades().add(localidade);
                    }
                }
                
                lista.add(provincia);
            }
            stm.close();
            con.commit();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method getLista 1ª of 3 "
                    + "SQLException catcher ", ex);
            if (ex instanceof MySQLSyntaxErrorException) {

            }
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method getLista 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method getLista 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return lista;
    }


    /*Crea las tres tablas relacionadas con la clase Provincia.*/
    private void createTables() {
        String[] tablas = new String[3];
        tablas[0] = "CREATE TABLE IF NOT EXISTS "
                + "provincias(id BIGINT(5) NOT NULL PRIMARY KEY,"
                + "nombre VARCHAR(100) NOT NULL UNIQUE)";
        
        tablas[1] = "CREATE TABLE IF NOT EXISTS "
                + "localidades(id BIGINT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(100),provincia VARCHAR(100))";
        
        tablas[2] = "CREATE TABLE IF NOT EXISTS "
                + "provincias_localidades(provincias_id BIGINT(5) NOT NULL,"
                + "localidades_id BIGINT(9) NOT NULL,"
                + "CONSTRAINT pk_provincias_localidades "
                + "PRIMARY KEY (provincias_id,localidades_id))";
        
        try {
            con = getConnection();
            con.setAutoCommit(false);
           
            for (String tabla : tablas) {
                stm = con.prepareStatement(tabla);
                stm.execute();
            }
            
            stm.close();
            con.commit();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method createTables 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method createTables 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method createTables 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
    }

}
