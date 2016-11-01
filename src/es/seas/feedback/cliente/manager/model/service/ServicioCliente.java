package es.seas.feedback.cliente.manager.model.service;

import es.seas.feedback.cliente.manager.model.Cliente;
import java.time.Month;
import java.util.List;
import es.seas.feedback.cliente.manager.model.dao.PersonaDAO;

/**
 * Esta clase es la implementación de la interfaz ServicioPersona&lt;Cliente&gt;.
 * @author Ricardo Maximino
 */
public class ServicioCliente implements ServicioPersona<Cliente>{

    private PersonaDAO<Cliente> dao;
    
    public ServicioCliente(){}
    public ServicioCliente(PersonaDAO dao){
        this.dao = dao;
    }
    
    /**
     * Este metodo configura la variable global dao.
     * @param dao del tipo PersonaDAO.
     */
    @Override
    public void setDao(PersonaDAO dao) {
        this.dao = dao;
    }

    /**
     * Este metodo retorna el valor de la variable global dao.
     * @return de tipo es.seas.feedback.cliente.manager.model.dao.PersonaDAO.
     */
    @Override
    public PersonaDAO getDao() {
        return dao;
    }

    /**
     * Este metodo intenta añadir un nuevo registro en la tabla clientes.
     * @param cliente del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return boolean para certificar si la acción ha sido realizado 
     * correctamente o no.
     */
    @Override
    public boolean añadirNuevoRegistro(Cliente cliente) {
        return dao.añadirNuevoRegistro(cliente);
    }

    /**
     * Este metodo intenta atualizar el registro en la tabla clientes.
     * @param cliente es.seas.feedback.cliente.manager.model.Cliente.
     * @return boolean para certificar si la acción ha sido realizada
     * correctamente o no.
     */
    @Override
    public boolean atualizarRegistro(Cliente cliente) {
        return dao.atualizarRegistro(cliente);
    }

    /**
     * Este metodo intenta dar de baja a un registro en la tabla clientes.
     * @param cliente del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return boolean para certificar si la acción ha sido realizado
     * correctamente o no.
     */
    @Override
    public boolean darDeBaja(Cliente cliente) {
        return dao.darDeBaja(cliente);
    }

    /**
     * Este metodo intenta dar de baja a un registro en la tabla clientes.
     * @param nif del tipo String, identificador único en la tabla.
     * @return boolean para certificar si la acción ha sido realizado
     * correctamente o no.
     */
    @Override
    public boolean darDeBaja(String nif) {
        return dao.darDeBaja(nif);
    }

    /**
     * Este metodo intenta borrar un registro de la tabla clientes.
     * @param cliente del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return boolean para certificar si la acción ha sido realizada
     * correctamente o no.
     */
    @Override
    public boolean borrarRegistro(Cliente cliente) {
        return dao.borrarRegistro(cliente);
    }

    /**
     * Este metodo intenta borrar un registro de la tabla clientes.
     * @param nif del tipo String, identificador único en la tabla.
     * @return boolean para certificar si la acción ha sido realizada
     * correctamente o no.
     */
    @Override
    public boolean borrarRegistro(String nif) {
        return dao.borrarRegistro(nif);
    }

    /**
     * Este metodo lista todos los registros de la tabla clientes.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     */
    @Override
    public List<Cliente> listarRegistros() {
        return dao.listarRegistros();
    }

    /**
     * Este metodo busca un registro en la tabla clientes donde el nif sea
     * igual al parametro introducido.
     * @param nif del tipo String, identificador único en la tabla.
     * @return del tipo es.seas.feedback.cliente.manager.model.Cliente.
     */
    @Override
    public Cliente buscarRegistroPorNIF(String nif) {
        return dao.buscarRegistroPorNIF(nif);
    }

    /**
     * Este metodo lista todos los registros de la tabla clientes filtrado por
     * el parametro introducido.
     * @param segundoApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     */
    @Override
    public List<Cliente> buscarRegistrosPorSegundoApellido(String segundoApellido) {
        return dao.buscarRegistrosPorSegundoApellido(segundoApellido);
    }

    /**
     * Este metodo lista todos los registros de la tabla clientes filtrado por
     * el parametro introducido.
     * @param primerApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     */
    @Override
    public List<Cliente> buscarRegistrosPorPrimerApellido(String primerApellido) {
        return dao.buscarRegistrosPorPrimerApellido(primerApellido);
    }

    /**
     * Este metodo lista todos los registros de la tabla clientes filtrado por
     * el parametro introducido.
     * @param nombre del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     */
    @Override
    public List<Cliente> buscarRegistrosPorNombre(String nombre) {
        return dao.buscarRegistrosPorNombre(nombre);
    }

    /**
     * Este metodo lista todos los registros de la tabla clientes filtrado por
     * el parametro activo = false.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     */
    @Override
    public List<Cliente> buscarRegistrosDadoDeBaja() {
        return dao.buscarRegistrosDadoDeBaja();
    }

    /**
     * Este metodo lista todos los registros de la tabla clientes filtrado por
     * el parametro activo = true.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     */
    @Override
    public List<Cliente> buscarRegistrosDadoDeAlta() {
        return dao.buscarRegistrosDadoDeAlta();
    }

    /**
     * Este metodo lista todos los registros de la tabla clientes filtrado por
     * el parametro introducido.
     * @param mes del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     */
    @Override
    public List<Cliente> cumpleañerosDelMes(Month mes) {
        return dao.cumpleañerosDelMes(mes);
    }
    
}
