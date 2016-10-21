/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model.dao.jdbc;

import es.seas.feedback.cliente.manager.model.Cliente;
import es.seas.feedback.cliente.manager.model.dao.PersonaDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Month;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class ClienteJDBCDAO implements PersonaDAO<Cliente> {

    @Override
    public boolean añadirNuevoRegistro(Cliente persona) {
        ConnectionFactory factory = new ConnectionFactory();
        Connection con = factory.getConnection();
        boolean resultado = false;
        try {
            String sql="INSERT INTO seas.cliente VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            String s = "INSERT INTO seas.usuarios(cp, direccion, localidade, nota, numero, provincia, fechaNacimiento, nif, nombre, primerApellido, segundoApellido, contraseña, level,fechaPrimeraAlta, fechaUltimaBaja,activo) VALUES (?, ?, ?,?,?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(s);
            //stm.setBoolean(1,);//'2', '1', '03130', 'Calle Ganderos', ' Santa Pola', 'Nena\n', '24', 'Alicante', '1988-01-24', NULL, NULL, '24', 'Africa', 'Chacopino', 'Chacopino', NULL, '0'
            stm.setString(1, "03130");
            stm.setString(2, "03130");
            stm.setString(3, "03130");
            stm.setString(4, "03130");
            stm.setString(5, "03130");
            stm.setString(6, "03130");
            stm.setDate(7,new Date(1982,11,15));
            stm.setString(8, "03130");
            stm.setString(9, "03130");
            stm.setString(10, "03130");
            stm.setString(11, "03130");
            stm.setString(12, "03130");
            stm.setString(13, "03130");
            stm.setDate(14, new Date(1982,11,15));
            stm.setDate(15, new Date(1982,11,15));
            stm.setBoolean(16, true);
            
            System.out.println(stm.execute());
            resultado = true;
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    @Override
    public boolean atualizarRegistro(Cliente persona) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public static void main(String[] args) {
        ClienteJDBCDAO c = new ClienteJDBCDAO();
        c.añadirNuevoRegistro(null);
    }
}
