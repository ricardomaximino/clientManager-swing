/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model.service;

import es.seas.feedback.cliente.manager.model.Cliente;
import java.time.Month;
import java.util.List;
import es.seas.feedback.cliente.manager.model.dao.PersonaDAO;

/**
 *
 * @author Ricardo
 */
public class ServicioCliente implements ServicioPersona<Cliente>{

    private PersonaDAO<Cliente> dao;
    
    public ServicioCliente(){}
    public ServicioCliente(PersonaDAO dao){
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
    public boolean añadirNuevoRegistro(Cliente cliente) {
        return dao.añadirNuevoRegistro(cliente);
    }

    @Override
    public boolean atualizarRegistro(Cliente cliente) {
        return dao.atualizarRegistro(cliente);
    }

    @Override
    public boolean darDeBaja(Cliente cliente) {
        return dao.darDeBaja(cliente);
    }

    @Override
    public boolean darDeBaja(String nif) {
        return dao.darDeBaja(nif);
    }

    @Override
    public boolean borrarRegistro(Cliente cliente) {
        return dao.borrarRegistro(cliente);
    }

    @Override
    public boolean borrarRegistro(String nif) {
        return dao.borrarRegistro(nif);
    }

    @Override
    public List<Cliente> listarRegistros() {
        return dao.listarRegistros();
    }

    @Override
    public Cliente buscarRegistroPorNIF(String nif) {
        return dao.buscarRegistroPorNIF(nif);
    }

    @Override
    public List<Cliente> buscarRegistrosPorSegundoApellido(String primerApellido) {
        return dao.buscarRegistrosPorSegundoApellido(primerApellido);
    }

    @Override
    public List<Cliente> buscarRegistrosPorPrimerApellido(String primerApellido) {
        return dao.buscarRegistrosPorPrimerApellido(primerApellido);
    }

    @Override
    public List<Cliente> buscarRegistrosPorNombre(String nombre) {
        return dao.buscarRegistrosPorNombre(nombre);
    }

    @Override
    public List<Cliente> buscarRegistrosDadoDeBaja() {
        return dao.buscarRegistrosDadoDeBaja();
    }

    @Override
    public List<Cliente> buscarRegistrosDadoDeAlta() {
        return dao.buscarRegistrosDadoDeAlta();
    }

    @Override
    public List<Cliente> cumpleañerosDelMes(Month mes) {
        return dao.cumpleañerosDelMes(mes);
    }
    
}
