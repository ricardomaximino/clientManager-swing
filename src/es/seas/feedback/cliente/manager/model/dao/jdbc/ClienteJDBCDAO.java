/**
 * Este package es donde se encuentran todas las class y interfaces relacionada
 * a la API JDBC.
 */
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
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Esta classe tiene la misma funcionalidades que el ClienteDAO
 *
 * @author Ricardo
 */
public class ClienteJDBCDAO implements PersonaDAO<Cliente> {

    private Connection getConnection() {
        return ConnectionFactory.getConnection();
    }

    @Override
    public boolean añadirNuevoRegistro(Cliente cliente) {
        Connection con = getConnection();
        PreparedStatement stm = null;
        String sql = "INSERT INTO clientes (nif,nombre,primerApellido,"
                + "segundoApellido,fechaNacimiento,fechaPrimeraAlta,"
                + "fechaUltimaBaja,activo,provincia,localidade,direccion,"
                + "numero,cp,nota)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        boolean resultado = false;
        try {
            stm = con.prepareStatement(sql);
            setStatementValues(stm, cliente);
            resultado = (stm.executeUpdate() > 0) ? true : false;
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ClienteJDBCDAO.class.getName());
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                exx.printStackTrace();

            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(ClienteJDBCDAO.class.getName());
            }
        }
        return resultado;
    }

    @Override
    public boolean atualizarRegistro(Cliente cliente) {
        Connection con = getConnection();
        PreparedStatement stm = null;
        String sql = "UPDATE clientes SET nombre=?,primerApellido=?,"
                + "segundoApellido=?,fechaNacimiento=?,fechaPrimeraAlta=?,"
                + "fechaUltimaBaja=?,activo=?,provincia=?,localidade=?,direccion=?,"
                + "numero=?,cp=?,nota=? WHERE id=?";
        boolean resultado = false;
        try {
            stm = con.prepareStatement(sql);
            stm.setString(1, cliente.getNombre());
            stm.setString(2, cliente.getPrimerApellido());
            stm.setString(3, cliente.getSegundoApellido());
            stm.setDate(4, Date.valueOf(cliente.getFechaNacimiento()));
            stm.setDate(5, Date.valueOf(cliente.getFechaPrimeraAlta()));
            stm.setDate(6, Date.valueOf(cliente.getFechaUltimaBaja()));
            stm.setBoolean(7, cliente.isActivo());
            stm.setString(8, cliente.getDirecion().getProvincia());
            stm.setString(9, cliente.getDirecion().getLocalidade());
            stm.setString(10, cliente.getDirecion().getDireccion());
            stm.setString(11, cliente.getDirecion().getNumero());
            stm.setString(12, cliente.getDirecion().getCp());
            stm.setString(13, cliente.getDirecion().getNota());
            stm.setLong(14, cliente.getId());
            resultado = (stm.executeUpdate() > 0) ? true : false;
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ClienteJDBCDAO.class.getName());
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                exx.printStackTrace();
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(ClienteJDBCDAO.class.getName());
            }
        }
        return resultado;
    }

    @Override
    public boolean darDeBaja(Cliente persona) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean darDeBaja(String nif) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean borrarRegistro(Cliente persona) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean borrarRegistro(String nif) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> listarRegistros() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cliente buscarRegistroPorNIF(String nif) {
        Cliente cliente = null;
        Connection con = getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM seas.clientes WHERE nif=?";
        boolean resultado = false;
        try {
            cliente = new Cliente();
            stm = con.prepareStatement(sql);
            stm.setString(1, nif);
            rs = stm.executeQuery();
            rs.first();
            cliente.setId(rs.getLong("id"));
            cliente.setNif(rs.getString("nif"));
            cliente.setNombre(rs.getString("nombre"));
            cliente.setPrimerApellido(rs.getString("primerApellido"));
            cliente.setSegundoApellido(rs.getString("segundoApellido"));
            cliente.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
            cliente.setFechaPrimeraAlta(rs.getDate("fechaPrimeraAlta").toLocalDate());
            cliente.setFechaUltimaBaja(rs.getDate("fechaUltimaBaja").toLocalDate());
            cliente.setActivo(rs.getBoolean("activo"));
            cliente.setDirecion(new Direccion(rs.getString("provincia"), rs.getString("localidade"), rs.getString("direccion"), rs.getString("numero"), rs.getString("cp"), rs.getString("nota")));
            stm = con.prepareStatement ("SELECT contactos_id FROM clientes_contactos WHERE clientes_id=?");
            stm.setLong(1, cliente.getId());
            rs = stm.executeQuery();
            List<Long> contactosIds = new ArrayList<>();
            while(rs.next()){
                contactosIds.add(rs.getLong("contactos_id"));
            }
            for(Long l : contactosIds){
                stm = con.prepareStatement("SELECT * FROM contactos WHERE id=?");
                stm.setLong(1, l);
                rs = stm.executeQuery();
                rs.first();
                Contacto contacto = new Contacto(rs.getString("descripcion"), rs.getString("contacto"));
                contacto.setId(rs.getLong("id"));
                cliente.getContactos().add(contacto);
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ClienteJDBCDAO.class.getName());
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException exx) {
                exx.printStackTrace();
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(ClienteJDBCDAO.class.getName());
            }
        }
        return cliente;
    }

    @Override
    public Cliente buscarRegistroPorID(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> buscarRegistrosPorPrimerApellido(String segundoApellido) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> buscarRegistrosPorSegundoApellido(String primerApellido) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> buscarRegistrosPorNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> buscarRegistrosDadoDeBaja() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> buscarRegistrosDadoDeAlta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cliente> cumpleañerosDelMes(Month mes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setStatementValues(PreparedStatement stm, Cliente cliente) throws SQLException {
        stm.setString(1, cliente.getNif());
        stm.setString(2, cliente.getNombre());
        stm.setString(3, cliente.getPrimerApellido());
        stm.setString(4, cliente.getSegundoApellido());
        stm.setDate(5, Date.valueOf(cliente.getFechaNacimiento()));
        stm.setDate(6, Date.valueOf(cliente.getFechaPrimeraAlta()));
        stm.setDate(7, Date.valueOf(cliente.getFechaUltimaBaja()));
        stm.setBoolean(8, cliente.isActivo());
        stm.setString(9, cliente.getDirecion().getProvincia());
        stm.setString(10, cliente.getDirecion().getLocalidade());
        stm.setString(11, cliente.getDirecion().getDireccion());
        stm.setString(12, cliente.getDirecion().getNumero());
        stm.setString(13, cliente.getDirecion().getCp());
        stm.setString(14, cliente.getDirecion().getNota());
    }

    public static void main(String[] args) {
        ClienteJDBCDAO c = new ClienteJDBCDAO();
        Cliente cli = new Cliente();
        cli.setNif("nif" + Math.random() * 999);
        cli.setNombre("Ricardo");
        cli.setPrimerApellido("Gonçalves");
        cli.setSegundoApellido("de Moraes");
        cli.setActivo(true);
        cli.setDirecion(new Direccion("Alicante", "Santa Pola", "Calle Ganadeos", "24", "03130", "Entresuelo"));
        cli.setFechaNacimiento(LocalDate.of(1982, Month.NOVEMBER, 15));
        cli.setFechaPrimeraAlta(LocalDate.now());
        cli.setFechaUltimaBaja(LocalDate.now());
        c.añadirNuevoRegistro(cli);
        cli.setId(11);
        c.atualizarRegistro(cli);
        System.out.println(c.buscarRegistroPorNIF("1"));
    }
}
