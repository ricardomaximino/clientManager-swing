/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model.service;

import es.seas.feedback.cliente.manager.model.Usuario;
import es.seas.feedback.cliente.manager.model.dao.PersonaDAO;
import es.seas.feedback.cliente.manager.model.dao.UsuarioDAO;
import java.time.Month;
import java.util.List;

/**
 *
 * @author Ricardo
 */
public class ServicioUsuario implements ServicioPersona<Usuario>{

    private PersonaDAO<Usuario> dao;
    
    public ServicioUsuario(){}
    public ServicioUsuario(PersonaDAO dao){
        this.dao = dao;
    }
    
    @Override
    public void setDao(PersonaDAO dao) {
        this.dao = dao;
    }

    @Override
    public PersonaDAO getDao() {
        return dao;
    }

    @Override
    public boolean añadirNuevoRegistro(Usuario usuario) {
        return dao.añadirNuevoRegistro(usuario);
    }

    @Override
    public boolean atualizarRegistro(Usuario usuario) {
        return dao.atualizarRegistro(usuario);
    }

    @Override
    public boolean darDeBaja(Usuario usuario) {
        return dao.darDeBaja(usuario);
    }

    @Override
    public boolean darDeBaja(String nif) {
        return dao.darDeBaja(nif);
    }

    @Override
    public boolean borrarRegistro(Usuario usuario) {
        return dao.borrarRegistro(usuario);
    }

    @Override
    public boolean borrarRegistro(String nif) {
        return dao.borrarRegistro(nif);
    }

    @Override
    public List<Usuario> listarRegistros() {
        return dao.listarRegistros();
    }

    @Override
    public Usuario buscarRegistroPorNIF(String nif) {
        return dao.buscarRegistroPorNIF(nif);
    }

    @Override
    public List<Usuario> buscarRegistrosPorSegundoApellido(String primerApellido) {
        return dao.buscarRegistrosPorSegundoApellido(primerApellido);
    }

    @Override
    public List<Usuario> buscarRegistrosPorPrimerApellido(String primerApellido) {
        return dao.buscarRegistrosPorPrimerApellido(primerApellido);
    }

    @Override
    public List<Usuario> buscarRegistrosPorNombre(String nombre) {
        return dao.buscarRegistrosPorNombre(nombre);
    }

    @Override
    public List<Usuario> buscarRegistrosDadoDeBaja() {
        return dao.buscarRegistrosDadoDeBaja();
    }

    @Override
    public List<Usuario> buscarRegistrosDadoDeAlta() {
        return dao.buscarRegistrosDadoDeAlta();
    }

    @Override
    public List<Usuario> cumpleañerosDelMes(Month mes) {
        return dao.cumpleañerosDelMes(mes);
    }    
}
