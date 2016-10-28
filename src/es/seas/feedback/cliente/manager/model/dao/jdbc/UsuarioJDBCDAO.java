package es.seas.feedback.cliente.manager.model.dao.jdbc;

import es.seas.feedback.cliente.manager.model.Usuario;
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
 * Esta classe tiene la misma funcionalidades que el UsuarioDAO
 *
 * @author Ricardo
 */
public class UsuarioJDBCDAO implements PersonaDAO<Usuario> {
    private static final Logger LOG = LoggerFactory.getLogger(UsuarioJDBCDAO.class);

    Connection con;
    PreparedStatement stm;
    ResultSet rs;
    ResultSet rsc;

    public UsuarioJDBCDAO(){
        LOG.info("This class starts");
    }
    private Connection getConnection() {
        return ConnectionFactory.getConnection();
    }

    /**
     * Este metodo es para añadir un nuevo registro en la base de datos 
     * representando un es.seas.feedback.cliente.manager.model.Usuario.<br>
     * 
     * @param usuario es del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return retorna un valor del tipo boolean, true si el usuario haya sido 
     * añadido a la base de datos y false si no.
     * 
     * <p>El metodo cria un PrepareStatement de la seguinte manera:</p>
     * <ul>
     * <li>String sql = "INSERT INTO usuarios (nif,nombre,primerApellido,"<br>
                + "segundoApellido,fechaNacimiento,fechaPrimeraAlta,"<br>
                + "fechaUltimaBaja,activo,provincia,localidade,direccion,"<br>
                + "numero,cp,nota,contraseña,controle)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";</li>
     *<li>stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);</li>
     * </ul>
     * luego configura los valores utilizando el usuario introducido como
     * parametro. 
     */
    @Override
    public boolean añadirNuevoRegistro(Usuario usuario) {
        Integer usuarioId;
        Integer contactoId;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "INSERT INTO usuarios (nif,nombre,primerApellido,"
                + "segundoApellido,fechaNacimiento,fechaPrimeraAlta,"
                + "fechaUltimaBaja,activo,provincia,localidade,direccion,"
                + "numero,cp,nota,contraseña,controle)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
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
            stm.setString(1, usuario.getNif());
            stm.setString(2, usuario.getNombre());
            stm.setString(3, usuario.getPrimerApellido());
            stm.setString(4, usuario.getSegundoApellido());
            
            //nuevo Fechas
            if(usuario.getFechaNacimiento()!=null){
                stm.setDate(5, Date.valueOf(usuario.getFechaNacimiento()));
            }else{
                stm.setDate(5, null);
            }
            if(usuario.getFechaPrimeraAlta()!=null){
                stm.setDate(6, Date.valueOf(usuario.getFechaPrimeraAlta()));
            }else{
                stm.setDate(6, Date.valueOf(LocalDate.now()));
            }
            if(usuario.getFechaUltimaBaja()!=null){
                stm.setDate(7, Date.valueOf(usuario.getFechaUltimaBaja()));
            }else{
                stm.setDate(7, null);
            }
            
            stm.setBoolean(8, usuario.isActivo());
            
            //nuevo Direccion
            if(usuario.getDirecion()!= null){
                stm.setString(9, usuario.getDirecion().getProvincia());
                stm.setString(10, usuario.getDirecion().getLocalidade());
                stm.setString(11, usuario.getDirecion().getDireccion());
                stm.setString(12, usuario.getDirecion().getNumero());
                stm.setString(13, usuario.getDirecion().getCp());
                stm.setString(14, usuario.getDirecion().getNota());
            }else{
                stm.setString(9, null);
                stm.setString(10, null);
                stm.setString(11, null);
                stm.setString(12, null);
                stm.setString(13, null);
                stm.setString(14, null);                
            }
            stm.setString(15, usuario.getContraseña());
            stm.setInt(16, usuario.getLevel());
            /*
                stm.executeUpdate no devuelve la primeryKey generada por la 
                base de datos, este metodo devuelve un int informando el numero 
                de lineas que hayan sido afectadas en la base de datos.
            */
            usuarioId = stm.executeUpdate();
            resultado = usuarioId > 0;
            rs = stm.getGeneratedKeys();
            rs.first();
            usuarioId = rs.getInt(1);
            if (usuario.getContactos().size() > 0) {
                for (Contacto c : usuario.getContactos()) {
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
                    stm = con.prepareStatement("INSERT INTO usuarios_contactos "
                            + "(usuarios_id,contactos_id) VALUES(?,?)");
                    stm.setLong(1, usuarioId);
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
     * representando un es.seas.feedback.cliente.manager.model.Usuario.<br>
     * 
     * @param usuario es del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return retorna un valor del tipo boolean, true si el usuario haya sido 
     * añadido a la base de datos y false si no.
     * 
     * <p>El metodo cria un PrepareStatement de la seguinte manera:</p>
     * <ul>
     * <li>String sql = "UPDATE usuarios SET nombre=?,primerApellido=?,"
                + "segundoApellido=?,fechaNacimiento=?,fechaPrimeraAlta=?,"
                + "fechaUltimaBaja=?,activo=?,provincia=?,localidade=?,"
                + "direccion=?,numero=?,cp=?,nota=? WHERE id=?";</li>
     *<li>stm = con.prepareStatement(sql);</li>
     * </ul>
     * luego configura los valores utilizando el usuario introducido como
     * parametro. 
     */    
    @Override
    public boolean atualizarRegistro(Usuario usuario) {
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "UPDATE usuarios SET nombre=?,primerApellido=?,"
                + "segundoApellido=?,fechaNacimiento=?,fechaPrimeraAlta=?,"
                + "fechaUltimaBaja=?,activo=?,provincia=?,localidade=?,"
                + "direccion=?,numero=?,cp=?,nota=?,contraseña=?,controle=? WHERE id=?";
        boolean resultado = false;
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, usuario.getNombre());
            stm.setString(2, usuario.getPrimerApellido());
            stm.setString(3, usuario.getSegundoApellido());
            
            //nuevo Fechas
            if(usuario.getFechaNacimiento()!=null){
                stm.setDate(4, Date.valueOf(usuario.getFechaNacimiento()));
            }else{
                stm.setDate(4, null);
            }
            if(usuario.getFechaPrimeraAlta()!=null){
                stm.setDate(5, Date.valueOf(usuario.getFechaPrimeraAlta()));
            }else{
                stm.setDate(5, null);
            }
            if(usuario.getFechaUltimaBaja()!=null){
                stm.setDate(6, Date.valueOf(usuario.getFechaUltimaBaja()));
            }else{
                stm.setDate(6, null);
            }
            
            stm.setBoolean(7, usuario.isActivo());
            
            //nuevo Direccion
            if(usuario.getDirecion()!= null){
                stm.setString(8, usuario.getDirecion().getProvincia());
                stm.setString(9, usuario.getDirecion().getLocalidade());
                stm.setString(10, usuario.getDirecion().getDireccion());
                stm.setString(11, usuario.getDirecion().getNumero());
                stm.setString(12, usuario.getDirecion().getCp());
                stm.setString(13, usuario.getDirecion().getNota());
            }else{
                stm.setString(8, null);
                stm.setString(9, null);
                stm.setString(10, null);
                stm.setString(11, null);
                stm.setString(12, null);
                stm.setString(13, null);                
            }
            stm.setString(14, usuario.getContraseña());
            stm.setInt(15, usuario.getLevel());
            
            stm.setLong(16, usuario.getId());
            
            resultado = stm.executeUpdate() > 0;
            if (usuario.getContactos().size() > 0) {
                for (Contacto c : usuario.getContactos()) {
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
                                + "usuarios_contactos (usuarios_id,contactos_id)"
                                + " VALUES(?,?)");
                        stm.setLong(1, usuario.getId());
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
     * Este metodo configura la columna activo = false del usuario que tenga el
     * nif introducido por parametro.
     * @param usuario del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return del tipo boolean, true si el nif introducido exitiera en la base
     * de datos y false si el nif no haya sido encontrado.
     * <p>El metodo llamara el metodo darDeBaja(String nif) y pasará
     * usuario.getNif() como parametro.</p>
     */
    @Override
    public boolean darDeBaja(Usuario usuario) {
        return darDeBaja(usuario.getNif());
    }

    /**
     * Este metodo configura la columna activo = false del usuario que tenga el
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
            stm = con.prepareStatement("UPDATE usuarios SET activo=?, fechaUltimaBaja=? WHERE nif=?");
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
     * @param usuario del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return del tipo boolean, retorna true si haya sido encontrado algun
     * registro con el nif igual al parametro, y false si no haya encotrado
     * ningún.
     * <p>Este metodo llamará el metodo borrarRegistro(String nif)
     * y pasará usuario.getNif() como parametro.</p>
     */
    @Override
    public boolean borrarRegistro(Usuario usuario) {
        return borrarRegistro(usuario.getNif());
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
            stm = con.prepareStatement("DELETE FROM usuarios WHERE nif=?");
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
     * Este metodo lista todos los usuarios registrados en la base de datos y
     * retorna un List&lt;Usuario&gt;.
     * @return del tipo 
     * java.util.List&lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     */
    @Override
    public List<Usuario> listarRegistros() {
        Usuario usuario;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM usuarios ORDER BY nombre";
        List<Usuario> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while ( rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNif(rs.getString("nif"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                
                 //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    usuario.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    usuario.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    usuario.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    usuario.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    usuario.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    usuario.setFechaUltimaBaja(null);
                }            
                
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setLevel(rs.getInt("controle"));
                
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "usuarios_contactos WHERE usuarios_id=?");
                stm.setLong(1, usuario.getId());
                rsc = stm.executeQuery();
                List<Long> contactosIds = new ArrayList<>();
                while (rsc.next()) {
                    contactosIds.add(rsc.getLong(1));
                }
                for (Long l : contactosIds) {
                    stm = con.prepareStatement("SELECT * FROM contactos "
                            + "WHERE id=?");
                    stm.setLong(1, l);
                    rsc = stm.executeQuery();
                    rsc.first();
                    Contacto contacto = new Contacto(rsc.getString("descripcion"),
                            rsc.getString("contacto"));
                    contacto.setId(rsc.getLong("id"));
                    System.out.println("contacto id na lista " + contacto.getId());
                    usuario.getContactos().add(contacto);
                }
                lista.add(usuario);
            }
            if(rsc != null) rsc.close();
            if(rs != null) rs.close();
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
     * @return del tipo es.seas.feedback.cliente.manager.model.Usuario, 
     * y en caso de que no haya tal registro se retornará null.
     */
    @Override
    public Usuario buscarRegistroPorNIF(String nif) {
        Usuario usuario = null;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM usuarios WHERE nif=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, nif);
            rs = stm.executeQuery();
            if (rs != null && rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNif(rs.getString("nif"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    usuario.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    usuario.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    usuario.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    usuario.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    usuario.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    usuario.setFechaUltimaBaja(null);
                }
                
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setLevel(rs.getInt("controle"));
                
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "usuarios_contactos WHERE usuarios_id=?");
                stm.setLong(1, usuario.getId());
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
                    System.out.println("Contacto id in PORNIF: " + contacto.getId());
                    usuario.getContactos().add(contacto);
                }
                rs.close();
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
        return usuario;
    }

        /**
     * Este metodo busca en la base de datos un registro que tengo el nif igual
     * al parametro introducido.
     * @param id del tipo long.
     * @return del tipo es.seas.feedback.cliente.manager.model.Usuario, 
     * y en caso de que no haya tal registro se retornará null.
     */
    @Override
    public Usuario buscarRegistroPorID(long id) {
        Usuario usuario = null;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM usuarios WHERE id=?";
        try {
            stm = con.prepareStatement(sql);
            stm.setLong(1, id);
            rs = stm.executeQuery();
            if (rs != null && rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNif(rs.getString("nif"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    usuario.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    usuario.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    usuario.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    usuario.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    usuario.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    usuario.setFechaUltimaBaja(null);
                }
                
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setLevel(rs.getInt("controle"));
                
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "usuarios_contactos WHERE usuarios_id=?");
                stm.setLong(1, usuario.getId());
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
                    usuario.getContactos().add(contacto);
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
        return usuario;
    }

    /**
     * Este metodo retorna una lista de usuario que tengan el primerApellido
     * igual al parametro(caseInsensitive).
     * @param primerApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * 
     * <p>En caso de que no se encontre ningún usuario con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosPorPrimerApellido(String primerApellido) {
        Usuario usuario;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM usuarios WHERE primerApellido=? ORDER BY nombre";
        List<Usuario> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, primerApellido);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNif(rs.getString("nif"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    usuario.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    usuario.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    usuario.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    usuario.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    usuario.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    usuario.setFechaUltimaBaja(null);
                }
                
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setLevel(rs.getInt("controle"));
                
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "usuarios_contactos WHERE usuarios_id=?");
                stm.setLong(1, usuario.getId());
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
                    usuario.getContactos().add(contacto);
                }
                lista.add(usuario);
            }
            rs.close();
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
     * Este metodo retorna una lista de usuario que tengan el segundoApellido
     * igual al parametro(caseInsensitive).
     * @param segundoApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * 
     * <p>En caso de que no se encontre ningún usuario con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosPorSegundoApellido(String segundoApellido) {
        Usuario usuario;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM usuarios WHERE segundoApellido=? ORDER BY nombre";
        List<Usuario> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, segundoApellido);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNif(rs.getString("nif"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                 
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    usuario.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    usuario.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    usuario.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    usuario.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    usuario.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    usuario.setFechaUltimaBaja(null);
                }
                
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setLevel(rs.getInt("controle"));
                
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "usuarios_contactos WHERE usuarios_id=?");
                stm.setLong(1, usuario.getId());
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
                    usuario.getContactos().add(contacto);
                }
                lista.add(usuario);
            }
            rs.close();
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
     * Este metodo retorna una lista de usuario que tengan el nombre
     * igual al parametro(caseInsensitive).
     * @param nombre del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * 
     * <p>En caso de que no se encontre ningún usuario con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosPorNombre(String nombre) {
        Usuario usuario;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM usuarios WHERE nombre=? ORDER BY primerApellido";
        List<Usuario> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, nombre);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNif(rs.getString("nif"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    usuario.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    usuario.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    usuario.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    usuario.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    usuario.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    usuario.setFechaUltimaBaja(null);
                }
                
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setLevel(rs.getInt("controle"));
                
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "usuarios_contactos WHERE usuarios_id=?");
                stm.setLong(1, usuario.getId());
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
                    usuario.getContactos().add(contacto);
                }
                lista.add(usuario);
            }
            rs.close();
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
     * Este metodo retorna una lista de usuario que tengan la columna activo
     * igual a false.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * 
     * <p>En caso de que no se encontre ningún usuario con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosDadoDeBaja() {
        Usuario usuario;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM usuarios WHERE activo=? ORDER BY nombre";
        List<Usuario> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, false);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNif(rs.getString("nif"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    usuario.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    usuario.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    usuario.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    usuario.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    usuario.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    usuario.setFechaUltimaBaja(null);
                }
                
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setLevel(rs.getInt("controle"));
                
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "usuarios_contactos WHERE usuarios_id=?");
                stm.setLong(1, usuario.getId());
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
                    usuario.getContactos().add(contacto);
                }
                lista.add(usuario);
            }
            rs.close();
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
     * Este metodo retorna una lista de usuario que tengan la columna activo
     * igual a true.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * 
     * <p>En caso de que no se encontre ningún usuario con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosDadoDeAlta() {
        Usuario usuario;
        con = getConnection();
        stm = null;
        rs = null;
        String sql = "SELECT * FROM usuarios WHERE activo=? ORDER BY nombre";
        List<Usuario> lista = new ArrayList<>();
        try {
            stm = con.prepareStatement(sql);
            stm.setBoolean(1, true);
            rs = stm.executeQuery();
            while (rs != null && rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNif(rs.getString("nif"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                
                //nuevo
                Date sqlFechaNacimiento = rs.getDate("fechaNacimiento");
                if(sqlFechaNacimiento != null){
                    usuario.setFechaNacimiento(sqlFechaNacimiento.toLocalDate());
                }else{
                    usuario.setFechaNacimiento(null);
                }               
                Date sqlFechaPrimeraAlta = rs.getDate("fechaPrimeraAlta");
                if(sqlFechaPrimeraAlta != null){
                    usuario.setFechaPrimeraAlta(sqlFechaPrimeraAlta.toLocalDate());
                }else{
                    usuario.setFechaPrimeraAlta(null);
                }                
                Date sqlFechaUltimaBaja = rs.getDate("fechaUltimaBaja");
                if(sqlFechaUltimaBaja != null){
                    usuario.setFechaUltimaBaja(sqlFechaUltimaBaja.toLocalDate());
                }else{
                    usuario.setFechaUltimaBaja(null);
                }
                
                usuario.setActivo(rs.getBoolean("activo"));
                usuario.setDirecion(new Direccion(rs.getString("provincia"),
                        rs.getString("localidade"), rs.getString("direccion"),
                        rs.getString("numero"), rs.getString("cp"),
                        rs.getString("nota")));
                
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setLevel(rs.getInt("controle"));
                
                stm = con.prepareStatement("SELECT contactos_id FROM "
                        + "usuarios_contactos WHERE usuarios_id=?");
                stm.setLong(1, usuario.getId());
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
                    usuario.getContactos().add(contacto);
                }
                lista.add(usuario);
            }
            rs.close();
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
     * Este metodo retorna una lista de usuarios que tengan su cumpleaños
     * en el mes pasado por parametro.
     * @param mes del tipo java.time.Month;
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * 
     * <p>En caso de que no se encontre ningún usuario con esta búsqueda,
     * se retornará una lista vacia.</p>
     */
    @Override
    public List<Usuario> cumpleañerosDelMes(Month mes) {
        List<Usuario> lista = new ArrayList();
        for (Usuario usuario : listarRegistros()) {
            if (usuario.getFechaNacimiento().getMonth().equals(mes)) {
                lista.add(usuario);
            }
        }
        return lista;
    }

}
