package es.seas.feedback.cliente.manager.model.service;

import es.seas.feedback.cliente.manager.model.Usuario;
import es.seas.feedback.cliente.manager.model.dao.PersonaDAO;
import java.time.Month;
import java.util.List;

/**
 * Esta clase es la implementación de la interfaz ServicioPersona&lt;Usuario&gt;.
 * @author Ricardo Maximino
 */
public class ServicioUsuario implements ServicioPersona<Usuario>{

    private PersonaDAO<Usuario> dao;
    
    public ServicioUsuario(){}
    public ServicioUsuario(PersonaDAO<Usuario> dao){
        this.dao = dao;
    }
    
    /**
     * Este metodo configura la variable global dao.
     * @param dao del tipo PersonaDAO.
     */
    @Override
    public void setDao(PersonaDAO<Usuario> dao) {
        this.dao = dao;
    }

    /**
     * Este metodo retorna el valor de la variable global dao.
     * @return de tipo es.seas.feedback.cliente.manager.model.dao.PersonaDAO.
     */
    @Override
    public PersonaDAO<Usuario> getDao() {
        return dao;
    }

    /**
     * Este metodo intenta añadir un nuevo registro en la tabla usuarios.
     * @param usuario del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return boolean para certificar si la acción ha sido realizado 
     * correctamente o no.
     */
    @Override
    public boolean anadirNuevoRegistro(Usuario usuario) {
        return dao.anadirNuevoRegistro(usuario);
    }

    /**
     * Este metodo intenta atualizar el registro en la tabla usuarios.
     * @param usuario es.seas.feedback.cliente.manager.model.Usuario.
     * @return boolean para certificar si la acción ha sido realizada
     * correctamente o no.
     */
    @Override
    public boolean atualizarRegistro(Usuario usuario) {
        return dao.atualizarRegistro(usuario);
    }

    /**
     * Este metodo intenta dar de baja a un registro en la tabla usuarios.
     * @param usuario del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return boolean para certificar si la acción ha sido realizado
     * correctamente o no.
     */
    @Override
    public boolean darDeBaja(Usuario usuario) {
        return dao.darDeBaja(usuario);
    }

    /**
     * Este metodo intenta dar de baja a un registro en la tabla usuarios.
     * @param nif del tipo String, identificador único en la tabla.
     * @return boolean para certificar si la acción ha sido realizado
     * correctamente o no.
     */
    @Override
    public boolean darDeBaja(String nif) {
        return dao.darDeBaja(nif);
    }

    /**
     * Este metodo intenta borrar un registro de la tabla usuarios.
     * @param usuario del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return boolean para certificar si la acción ha sido realizada
     * correctamente o no.
     */
    @Override
    public boolean borrarRegistro(Usuario usuario) {
        return dao.borrarRegistro(usuario);
    }

    /**
     * Este metodo intenta borrar un registro de la tabla usuarios.
     * @param nif del tipo String, identificador único en la tabla.
     * @return boolean para certificar si la acción ha sido realizada
     * correctamente o no.
     */
    @Override
    public boolean borrarRegistro(String nif) {
        return dao.borrarRegistro(nif);
    }

    /**
     * Este metodo lista todos los registros de la tabla usuarios.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     */
    @Override
    public List<Usuario> listarRegistros() {
        return dao.listarRegistros();
    }

    /**
     * Este metodo busca un registro en la tabla usuarios donde el nif sea
     * igual al parametro introducido.
     * @param nif del tipo String, identificador único en la tabla.
     * @return del tipo es.seas.feedback.cliente.manager.model.Usuario.
     */
    @Override
    public Usuario buscarRegistroPorNIF(String nif) {
        return dao.buscarRegistroPorNIF(nif);
    }

    /**
     * Este metodo lista todos los registros de la tabla usuarios filtrado por
     * el parametro introducido.
     * @param segundoApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     */
    @Override
    public List<Usuario> buscarRegistrosPorSegundoApellido(String segundoApellido) {
        return dao.buscarRegistrosPorSegundoApellido(segundoApellido);
    }

    /**
     * Este metodo lista todos los registros de la tabla usuarios filtrado por
     * el parametro introducido.
     * @param primerApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     */
    @Override
    public List<Usuario> buscarRegistrosPorPrimerApellido(String primerApellido) {
        return dao.buscarRegistrosPorPrimerApellido(primerApellido);
    }

    /**
     * Este metodo lista todos los registros de la tabla usuarios filtrado por
     * el parametro introducido.
     * @param nombre del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     */
    @Override
    public List<Usuario> buscarRegistrosPorNombre(String nombre) {
        return dao.buscarRegistrosPorNombre(nombre);
    }

    /**
     * Este metodo lista todos los registros de la tabla usuarios filtrado por
     * el parametro activo = false.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     */
    @Override
    public List<Usuario> buscarRegistrosDadoDeBaja() {
        return dao.buscarRegistrosDadoDeBaja();
    }

    /**
     * Este metodo lista todos los registros de la tabla usuarios filtrado por
     * el parametro activo = true.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     */
    @Override
    public List<Usuario> buscarRegistrosDadoDeAlta() {
        return dao.buscarRegistrosDadoDeAlta();
    }

    /**
     * Este metodo lista todos los registros de la tabla usuarios filtrado por
     * el parametro introducido.
     * @param mes del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     */
    @Override
    public List<Usuario> cumpleanerosDelMes(Month mes) {
        return dao.cumpleanerosDelMes(mes);
    }    
}
