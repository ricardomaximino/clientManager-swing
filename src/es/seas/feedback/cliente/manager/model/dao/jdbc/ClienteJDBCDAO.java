package es.seas.feedback.cliente.manager.model.dao.jdbc;

import es.seas.feedback.cliente.manager.model.Cliente;
import es.seas.feedback.cliente.manager.model.Contacto;
import es.seas.feedback.cliente.manager.model.Direccion;
import es.seas.feedback.cliente.manager.model.dao.PersonaDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta classe tiene la misma funcionalidades y implementa la misma interfaz 
 * que el ClienteDAO, la única diferencia es que esta clase utiliza JDBC y la
 * clase ClienteDAO utiliza Hibernate.
 *
 * @author Ricardo Maximino<br><br>
 */
public class ClienteJDBCDAO implements PersonaDAO<Cliente> {
    private static final Logger LOG = LoggerFactory.getLogger(ClienteJDBCDAO.class);

    Connection con;
    PreparedStatement stm;
    ResultSet rs;
    ResultSet rsc;
    ResultSet rscc;

    public ClienteJDBCDAO(){
        LOG.info("This class starts.");
        createTables();
    }
    private Connection getConnection() {
        return ConnectionFactory.getConnection();
    }

    /**
     * Este metodo es para añadir un nuevo registro en la base de datos 
     * representando un es.seas.feedback.cliente.manager.model.Cliente.<br>
     * 
     * @param cliente es del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return retorna un valor del tipo boolean, true si el cliente haya sido 
     * añadido a la base de datos y false si no.
     * 
     * <p>El metodo cria un PrepareStatement de la seguinte manera:</p>
     * <ul>
     * <li>String sql = "INSERT INTO clientes (nif,nombre,primerApellido,"<br>
                + "segundoApellido,fechaNacimiento,fechaPrimeraAlta,"<br>
                + "fechaUltimaBaja,activo,provincia,localidade,direccion,"<br>
                + "numero,cp,nota)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";</li>
     *<li>stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);</li>
     * </ul>
     * luego configura los valores utilizando el cliente introducido como
     * parametro. 
     */
    @Override
    public boolean añadirNuevoRegistro(Cliente cliente) {
        Integer clienteId;
        Integer contactoId;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "INSERT INTO clientes (nif,nombre,primerApellido,"
                + "segundoApellido,fechaNacimiento,fechaPrimeraAlta,"
                + "fechaUltimaBaja,activo,provincia,localidade,direccion,"
                + "numero,cp,nota)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        boolean resultado = false;
        /*
        con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        para abilitar el ResultSet coger las primeryKeys generadas por la 
        base de datos.
        rs = stm.getGeneratedKeys(); para conseguir un ResultSet con las 
        primeryKeys generadas por la base de datos en el mismo ordem de 
        inserción.
        */
        try {
            stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, cliente.getNif());
            stm.setString(2, cliente.getNombre());
            stm.setString(3, cliente.getPrimerApellido());
            stm.setString(4, cliente.getSegundoApellido());
            
            //nuevo Fechas
            if(cliente.getFechaNacimiento()!=null){
                stm.setDate(5, Date.valueOf(cliente.getFechaNacimiento()));
            }else{
                stm.setDate(5, null);
            }
            if(cliente.getFechaPrimeraAlta()!=null){
                stm.setDate(6, Date.valueOf(cliente.getFechaPrimeraAlta()));
            }else{
                stm.setDate(6, Date.valueOf(LocalDate.now()));
            }
            if(cliente.getFechaUltimaBaja()!=null){
                stm.setDate(7, Date.valueOf(cliente.getFechaUltimaBaja()));
            }else{
                stm.setDate(7, null);
            }
            
            stm.setBoolean(8, cliente.isActivo());
            
            //nuevo Direccion
            if(cliente.getDirecion()!= null){
                stm.setString(9, cliente.getDirecion().getProvincia());
                stm.setString(10, cliente.getDirecion().getLocalidade());
                stm.setString(11, cliente.getDirecion().getDireccion());
                stm.setString(12, cliente.getDirecion().getNumero());
                stm.setString(13, cliente.getDirecion().getCp());
                stm.setString(14, cliente.getDirecion().getNota());
            }else{
                stm.setString(9, null);
                stm.setString(10, null);
                stm.setString(11, null);
                stm.setString(12, null);
                stm.setString(13, null);
                stm.setString(14, null);                
            }
            /*
                stm.executeUpdate no devuelve la primeryKey generada por la 
                base de datos, este metodo devuelve un int informando el numero 
                de lineas que hayan sido afectadas en la base de datos.
            */
            clienteId = stm.executeUpdate();
            resultado = clienteId > 0;
            rs = stm.getGeneratedKeys();
            rs.first();
            clienteId = rs.getInt(1);
            if (cliente.getContactos().size() > 0) {
                for (Contacto c : cliente.getContactos()) {
                    stm = con.prepareStatement("INSERT INTO contactos "
                            + "(descripcion,contacto) VALUES(?,?)",
                            Statement.RETURN_GENERATED_KEYS);
                    
                    stm.setString(1, c.getDescripcion());
                    stm.setString(2, c.getContacto());
                    contactoId = stm.executeUpdate();
                    // if(contactoId != 1 significa que hay registro duplicado
                    if (contactoId != 1) {
                        LOG.error("if(contactoId != 1 means that in the database"
                                + " repited line or some constrain that do not "
                                + "allow new insert into contactos. In the method "
                                + "añadirNuevoRegistro.");
                    }
                    rs = stm.getGeneratedKeys();
                    rs.first();
                    contactoId = rs.getInt(1);
                    stm = con.prepareStatement("INSERT INTO clientes_contactos "
                            + "(clientes_id,contactos_id) VALUES(?,?)");
                    stm.setLong(1, clienteId);
                    stm.setLong(2, contactoId);
                    stm.executeUpdate();
                }
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method añadirNuevoRegistro 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method añadirNuevoRegistro 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method añadirNuevoRegistro 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return resultado;
    }

    /**
     * Este metodo es para añadir un nuevo registro en la base de datos 
     * representando un es.seas.feedback.cliente.manager.model.Cliente.<br>
     * 
     * @param cliente es del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return retorna un valor del tipo boolean, true si el cliente haya sido 
     * añadido a la base de datos y false si no.
     * 
     * <p>El metodo cria un PrepareStatement de la seguinte manera:</p>
     * <ul>
     * <li>String sql = "UPDATE clientes SET nombre=?,primerApellido=?,"
                + "segundoApellido=?,fechaNacimiento=?,fechaPrimeraAlta=?,"
                + "fechaUltimaBaja=?,activo=?,provincia=?,localidade=?,"
                + "direccion=?,numero=?,cp=?,nota=? WHERE id=?";</li>
     *<li>stm = con.prepareStatement(sql);</li>
     * </ul>
     * luego configura los valores utilizando el cliente introducido como
     * parametro. 
     */    
    @Override
    public boolean atualizarRegistro(Cliente cliente) {
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "UPDATE clientes SET nombre=?,primerApellido=?,"
                + "segundoApellido=?,fechaNacimiento=?,fechaPrimeraAlta=?,"
                + "fechaUltimaBaja=?,activo=?,provincia=?,localidade=?,"
                + "direccion=?,numero=?,cp=?,nota=? WHERE id=?";
        boolean resultado = false;
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, cliente.getNombre());
            stm.setString(2, cliente.getPrimerApellido());
            stm.setString(3, cliente.getSegundoApellido());
            
            //nuevo Fechas
            if(cliente.getFechaNacimiento()!=null){
                stm.setDate(4, Date.valueOf(cliente.getFechaNacimiento()));
            }else{
                stm.setDate(4, null);
            }
            if(cliente.getFechaPrimeraAlta()!=null){
                stm.setDate(5, Date.valueOf(cliente.getFechaPrimeraAlta()));
            }else{
                stm.setDate(5, null);
            }
            if(cliente.getFechaUltimaBaja()!=null){
                stm.setDate(6, Date.valueOf(cliente.getFechaUltimaBaja()));
            }else{
                stm.setDate(6, null);
            }
            
            stm.setBoolean(7, cliente.isActivo());
            
            //nuevo Direccion
            if(cliente.getDirecion()!= null){
                stm.setString(8, cliente.getDirecion().getProvincia());
                stm.setString(9, cliente.getDirecion().getLocalidade());
                stm.setString(10, cliente.getDirecion().getDireccion());
                stm.setString(11, cliente.getDirecion().getNumero());
                stm.setString(12, cliente.getDirecion().getCp());
                stm.setString(13, cliente.getDirecion().getNota());
            }else{
                stm.setString(8, null);
                stm.setString(9, null);
                stm.setString(10, null);
                stm.setString(11, null);
                stm.setString(12, null);
                stm.setString(13, null);                
            }
            stm.setLong(14, cliente.getId());
            
            resultado = stm.executeUpdate() > 0;
            if (cliente.getContactos().size() > 0) {
                for (Contacto c : cliente.getContactos()) {
                    if (c.getId() == 0) {
                        stm = con.prepareStatement("INSERT INTO contactos "
                                + "(descripcion,contacto) VALUES(?,?)",
                                Statement.RETURN_GENERATED_KEYS);
                        
                        stm.setString(1, c.getDescripcion());
                        stm.setString(2, c.getContacto());
                        stm.executeUpdate();
                        rsc = stm.getGeneratedKeys();
                        rsc.first();
                        int contactoId = rsc.getInt(1);
                        stm = con.prepareStatement("INSERT INTO "
                                + "clientes_contactos (clientes_id,contactos_id)"
                                + " VALUES(?,?)");
                        stm.setLong(1, cliente.getId());
                        stm.setLong(2, contactoId);
                        stm.executeUpdate();
                    } else {
                        stm = con.prepareStatement("UPDATE contactos SET "
                                + "descripcion=?, contacto=? WHERE id=?");
                        stm.setString(1, c.getDescripcion());
                        stm.setString(2, c.getContacto());
                        stm.setLong(3, c.getId());
                        stm.executeUpdate();
                    }
                }
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method atualizarRegistro 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method atualizarRegistro 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method atualizarRegistro 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return resultado;
    }
    /**
     * Este metodo configura la columna activo = false del cliente que tenga el
     * nif introducido por parametro.
     * @param cliente del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return del tipo boolean, true si el nif introducido exitiera en la base
     * de datos y false si el nif no haya sido encontrado.
     * <p>El metodo llamara el metodo darDeBaja(String nif) y pasará
     * cliente.getNif() como parametro.</p>
     */
    @Override
    public boolean darDeBaja(Cliente cliente) {
        return darDeBaja(cliente.getNif());
    }

    /**
     * Este metodo configura la columna activo = false del cliente que tenga el
     * nif introducido por parametro.
     * @param nif del tipo String.
     * @return del tipo boolean, true si el nif introducido exitiera en la base
     * de datos y false si el nif no haya sido encontrado.
     */
    @Override
    public boolean darDeBaja(String nif) {
        con = getConnection();
        stm = null;
        rs = null;
        boolean resultado = false;
        try {
            stm = con.prepareStatement("UPDATE clientes SET activo=?, fechaUltimaBaja=? WHERE nif=?");
            stm.setBoolean(1, false);
            stm.setDate(2, Date.valueOf(LocalDate.now()));
            stm.setString(3, nif);
            resultado = stm.executeUpdate() > 0;
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method darDeBaja 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method darDeBaja 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method darDeBaja 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return resultado;
    }

        /**
     * Este metodo borra el registro el la base de datos que tengo el nif igual
     * al parametro introducido.
     * @param cliente del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return del tipo boolean, retorna true si haya sido encontrado algun
     * registro con el nif igual al parametro, y false si no haya encotrado
     * ningún.
     * <p>Este metodo llamará el metodo borrarRegistro(String nif)
     * y pasará cliente.getNif() como parametro.</p>
     */
    @Override
    public boolean borrarRegistro(Cliente cliente) {
        return borrarRegistro(cliente.getNif());
    }

    /**
     * Este metodo borra el registro el la base de datos que tengo el nif igual
     * al parametro introducido.
     * @param nif del tipo String.
     * @return del tipo boolean, retorna true si haya sido encontrado algun
     * registro con el nif igual al parametro, y false si no haya encotrado
     * ningún.
     */
    @Override
    public boolean borrarRegistro(String nif) {
        con = getConnection();
        stm = null;
        rs = null;
        boolean resultado = false;
        try {
            stm = con.prepareStatement("DELETE FROM clientes WHERE nif=?");
            stm.setString(1, nif);
            resultado = stm.executeUpdate() > 0;
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method borrarRegistro 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method borrarRegistro 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method borrarRegistro 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return resultado;
    }

    /**
     * Este metodo lista todos los clientes registrados en la base de datos y
     * retorna un List&lt;Cliente&gt;.
     * @return del tipo 
     * java.util.List&lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     */
    @Override
    public List<Cliente> listarRegistros() {
        Cliente cliente;
        con = getConnection();
        stm = null;
        rs = null;
        rsc= null;
        rscc = null;
        String sql = "SELECT * FROM clientes ORDER BY nombre";
        List<Cliente> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while ( rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNif(rs.getString("nif"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                
                 //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    cliente.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    cliente.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    cliente.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    cliente.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    cliente.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    cliente.setFechaUltimaBaja(null);
                }            
                
                cliente.setActivo(rs.getBoolean("activo"));
                cliente.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "clientes_contactos WHERE clientes_id=?");
                stm.setLong(1, cliente.getId());
                rscc = stm.executeQuery();
                while (rscc.next()) {
                    stm = con.prepareStatement("SELECT * FROM contactos "
                            + "WHERE id=?");
                    stm.setLong(1, rscc.getLong(1));
                    rsc = stm.executeQuery();
                    rsc.first();
                    Contacto contacto = new Contacto(rsc.getString("descripcion"),
                            rsc.getString("contacto"));
                    contacto.setId(rsc.getLong("id"));
                    cliente.getContactos().add(contacto);
                }
                lista.add(cliente);
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method listarRegistros 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method listarRegistros 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method listarRegistros 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return lista;
    }

    /**
     * Este metodo busca en la base de datos un registro que tengo el nif igual
     * al parametro introducido.
     * @param nif del tipo String.
     * @return del tipo es.seas.feedback.cliente.manager.model.Cliente, 
     * y en caso de que no haya tal registro se retornará null.
     */
    @Override
    public Cliente buscarRegistroPorNIF(String nif) {
        Cliente cliente = null;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM clientes WHERE nif=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, nif);
            rs = stm.executeQuery();
            if (rs != null && rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNif(rs.getString("nif"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    cliente.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    cliente.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    cliente.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    cliente.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    cliente.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    cliente.setFechaUltimaBaja(null);
                }
                
                cliente.setActivo(rs.getBoolean("activo"));
                cliente.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "clientes_contactos WHERE clientes_id=?");
                stm.setLong(1, cliente.getId());
                rs = stm.executeQuery();
                List<Long> contactosIds = new ArrayList<>();
                while (rs.next()) {
                    contactosIds.add(rs.getLong(1));
                }
                for (Long l : contactosIds) {
                    stm = con.prepareStatement("SELECT * FROM contactos WHERE id=?");
                    stm.setLong(1, l);
                    rs = stm.executeQuery();
                    rs.first();
                    Contacto contacto = new Contacto(rs.getString("descripcion"),
                            rs.getString("contacto"));
                    contacto.setId(rs.getLong("id"));
                    cliente.getContactos().add(contacto);
                }
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method buscarRegistroPorNIF 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method buscarRegistroPorNIF 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method buscarRegistroPorNIF 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return cliente;
    }

        /**
     * Este metodo busca en la base de datos un registro que tengo el nif igual
     * al parametro introducido.
     * @param id del tipo long.
     * @return del tipo es.seas.feedback.cliente.manager.model.Cliente, 
     * y en caso de que no haya tal registro se retornará null.
     */
    @Override
    public Cliente buscarRegistroPorID(long id) {
        Cliente cliente = null;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM clientes WHERE id=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setLong(1, id);
            rs = stm.executeQuery();
            if (rs != null && rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNif(rs.getString("nif"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    cliente.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    cliente.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    cliente.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    cliente.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    cliente.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    cliente.setFechaUltimaBaja(null);
                }
                
                cliente.setActivo(rs.getBoolean("activo"));
                cliente.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "clientes_contactos WHERE clientes_id=?");
                stm.setLong(1, cliente.getId());
                rs = stm.executeQuery();
                List<Long> contactosIds = new ArrayList<>();
                while (rs.next()) {
                    contactosIds.add(rs.getLong(1));
                }
                for (Long l : contactosIds) {
                    stm = con.prepareStatement("SELECT * FROM contactos WHERE id=?");
                    stm.setLong(1, l);
                    rs = stm.executeQuery();
                    rs.first();
                    Contacto contacto = new Contacto(rs.getString("descripcion"),
                            rs.getString("contacto"));
                    contacto.setId(rs.getLong("id"));
                    cliente.getContactos().add(contacto);
                }
                rs.close();
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method buscarRegistroPorID 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method buscarRegistroPorID 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method buscarRegistroPorID 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return cliente;
    }

    /**
     * Este metodo retorna una lista de cliente que tengan el primerApellido
     * igual al parametro(caseInsensitive).
     * @param primerApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * 
     * <p>En caso de que no se encontre ningún cliente con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosPorPrimerApellido(String primerApellido) {
        Cliente cliente;
        con = getConnection();
        stm = null;
        rs = null;
        rsc = null;
        rscc = null;
        String sql = "SELECT * FROM clientes WHERE primerApellido=? ORDER BY nombre";
        List<Cliente> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, primerApellido);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNif(rs.getString("nif"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    cliente.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    cliente.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    cliente.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    cliente.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    cliente.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    cliente.setFechaUltimaBaja(null);
                }
                
                cliente.setActivo(rs.getBoolean("activo"));
                cliente.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "clientes_contactos WHERE clientes_id=?");
                stm.setLong(1, cliente.getId());
                rscc = stm.executeQuery();
                while (rscc.next()) {
                    stm = con.prepareStatement("SELECT * FROM contactos WHERE id=?");
                    stm.setLong(1,rscc.getLong(1));
                    rsc = stm.executeQuery();
                    rsc.first();
                    Contacto contacto = new Contacto(rsc.getString("descripcion"),
                            rsc.getString("contacto"));
                    contacto.setId(rsc.getLong("id"));
                    cliente.getContactos().add(contacto);
                }
                lista.add(cliente);
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method buscarRegistrosPorPrimerApellido 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method buscarRegistrosPorPrimerApellido 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method buscarRegistrosPorPrimerApellido 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return lista;
    }

        /**
     * Este metodo retorna una lista de cliente que tengan el segundoApellido
     * igual al parametro(caseInsensitive).
     * @param segundoApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * 
     * <p>En caso de que no se encontre ningún cliente con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosPorSegundoApellido(String segundoApellido) {
        Cliente cliente;
        con = getConnection();
        stm = null;
        rs = null;
        rscc = null;
        rsc = null;
        String sql = "SELECT * FROM clientes WHERE segundoApellido=? ORDER BY nombre";
        List<Cliente> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, segundoApellido);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNif(rs.getString("nif"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                 
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    cliente.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    cliente.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    cliente.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    cliente.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    cliente.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    cliente.setFechaUltimaBaja(null);
                }
                
                cliente.setActivo(rs.getBoolean("activo"));
                cliente.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "clientes_contactos WHERE clientes_id=?");
                stm.setLong(1, cliente.getId());
                rscc = stm.executeQuery();
                while (rscc.next()) {
                    stm = con.prepareStatement("SELECT * FROM contactos WHERE id=?");
                    stm.setLong(1, rscc.getLong(1));
                    rsc = stm.executeQuery();
                    rsc.first();
                    Contacto contacto = new Contacto(rsc.getString("descripcion"),
                            rsc.getString("contacto"));
                    contacto.setId(rsc.getLong("id"));
                    cliente.getContactos().add(contacto);
                }
                lista.add(cliente);
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method buscarRegistrosPorSegundoApellido 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method buscarRegistrosPorSegundoApellido 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method buscarRegistrosPorSegundoApellido 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return lista;
    }

        /**
     * Este metodo retorna una lista de cliente que tengan el nombre
     * igual al parametro(caseInsensitive).
     * @param nombre del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * 
     * <p>En caso de que no se encontre ningún cliente con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosPorNombre(String nombre) {
        Cliente cliente;
        con = getConnection();
        stm = null;
        rs = null;
        rscc = null;
        rsc = null;
        String sql = "SELECT * FROM clientes WHERE nombre=? ORDER BY primerApellido";
        List<Cliente> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, nombre);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNif(rs.getString("nif"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    cliente.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    cliente.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    cliente.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    cliente.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    cliente.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    cliente.setFechaUltimaBaja(null);
                }
                
                cliente.setActivo(rs.getBoolean("activo"));
                cliente.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "clientes_contactos WHERE clientes_id=?");
                stm.setLong(1, cliente.getId());
                rscc = stm.executeQuery();
                while (rscc.next()) {
                    stm = con.prepareStatement("SELECT * FROM contactos WHERE id=?");
                    stm.setLong(1, rscc.getLong(1));
                    rsc = stm.executeQuery();
                    rsc.first();
                    Contacto contacto = new Contacto(rsc.getString("descripcion"),
                            rsc.getString("contacto"));
                    contacto.setId(rsc.getLong("id"));
                    cliente.getContactos().add(contacto);
                }
                lista.add(cliente);
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method buscarRegistrosPorNombre 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method buscarRegistrosPorNombre 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method buscarRegistrosPorNombre 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return lista;
    }

    /**
     * Este metodo retorna una lista de cliente que tengan la columna activo
     * igual a false.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * 
     * <p>En caso de que no se encontre ningún cliente con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosDadoDeBaja() {
        Cliente cliente;
        con = getConnection();
        stm = null;
        rs = null;
        rscc = null;
        rsc = null;
        String sql = "SELECT * FROM clientes WHERE activo=? ORDER BY nombre";
        List<Cliente> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, false);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNif(rs.getString("nif"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    cliente.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    cliente.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    cliente.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    cliente.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    cliente.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    cliente.setFechaUltimaBaja(null);
                }
                
                cliente.setActivo(rs.getBoolean("activo"));
                cliente.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "clientes_contactos WHERE clientes_id=?");
                stm.setLong(1, cliente.getId());
                rscc = stm.executeQuery();
                while (rscc.next()) {
                    stm = con.prepareStatement("SELECT * FROM contactos WHERE id=?");
                    stm.setLong(1, rscc.getLong(1));
                    rsc = stm.executeQuery();
                    rsc.first();
                    Contacto contacto = new Contacto(rsc.getString("descripcion"),
                            rsc.getString("contacto"));
                    contacto.setId(rsc.getLong("id"));
                    cliente.getContactos().add(contacto);
                }
                lista.add(cliente);
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method buscarRegistrosDadoDeBaja 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method buscarRegistrosDadoDeBaja 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method buscarRegistrosDadoDeBaja 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return lista;
    }

        /**
     * Este metodo retorna una lista de cliente que tengan la columna activo
     * igual a true.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * 
     * <p>En caso de que no se encontre ningún cliente con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosDadoDeAlta() {
        Cliente cliente;
        con = getConnection();
        stm = null;
        rs = null;
        rscc = null;
        rsc = null;
        String sql = "SELECT * FROM clientes WHERE activo=? ORDER BY nombre";
        List<Cliente> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, true);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNif(rs.getString("nif"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    cliente.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    cliente.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    cliente.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    cliente.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    cliente.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    cliente.setFechaUltimaBaja(null);
                }
                
                cliente.setActivo(rs.getBoolean("activo"));
                cliente.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "clientes_contactos WHERE clientes_id=?");
                stm.setLong(1, cliente.getId());
                rscc = stm.executeQuery();
                while (rscc.next()) {
                    stm = con.prepareStatement("SELECT * FROM contactos WHERE id=?");
                    stm.setLong(1, rscc.getLong(1));
                    rsc = stm.executeQuery();
                    rsc.first();
                    Contacto contacto = new Contacto(rsc.getString("descripcion"),
                            rsc.getString("contacto"));
                    contacto.setId(rsc.getLong("id"));
                    cliente.getContactos().add(contacto);
                }
                lista.add(cliente);
            }
            stm.close();
        } catch (SQLException ex) {
            LOG.error("SQLException in the method buscarRegistrosDadoDeBaja 1ª of 3 "
                    + "SQLException catcher ", ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                LOG.error("SQLException in the method buscarRegistrosDadoDeBaja 2ª "
                        + "of 3 SQLException catcher ", exx);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                LOG.error("SQLException in the method buscarRegistrosDadoDeBaja 3ª "
                        + "of 3 SQLException catcher ", ex);
            }
        }
        return lista;
    }

    /**
     * Este metodo retorna una lista de clientes que tengan su cumpleaños
     * en el mes pasado por parametro.
     * @param mes del tipo java.time.Month;
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * 
     * <p>En caso de que no se encontre ningún cliente con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Cliente> cumpleañerosDelMes(Month mes) {
        List<Cliente> lista = new ArrayList();
        for (Cliente cliente : listarRegistros()) {
            if (cliente.getFechaNacimiento().getMonth().equals(mes)) {
                lista.add(cliente);
            }
        }
        return lista;
    }
    
        private void createTables() {

        String[] tablas = new String[3];
        tablas[0] = "CREATE TABLE IF NOT EXISTS"
                + " clientes(id BIGINT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY,"
                + " nif VARCHAR(100) NOT NULL UNIQUE, nombre VARCHAR(100),"
                + " primerApellido VARCHAR(100), segundoApellido VARCHAR(100),"
                + " fechaNacimiento DATE, fechaPrimeraAlta DATE,"
                + " fechaUltimaBaja DATE, activo BIT(1), provincia VARCHAR(100),"
                + " localidade VARCHAR(100), direccion VARCHAR(100),"
                + " numero VARCHAR(10), cp VARCHAR(100), nota VARCHAR(100))";
        
        tablas[1] = "CREATE TABLE IF NOT EXISTS contactos(id BIGINT(5) "
                + "NOT NULL AUTO_INCREMENT PRIMARY KEY,descripcion "
                + "VARCHAR(100),contacto VARCHAR(100))";
        
        tablas[2] = "CREATE TABLE IF NOT EXISTS clientes_contactos(clientes_id "
                + "BIGINT(5) NOT NULL,contactos_id BIGINT(9) NOT NULL,CONSTRAINT"
                + " pk_clientes_contactos PRIMARY KEY(clientes_id,contactos_id))";
        try {
            con = getConnection();
            con.setAutoCommit(false);
            for (String sqlCreateTable : tablas) {
                stm = con.prepareStatement(sqlCreateTable);
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
