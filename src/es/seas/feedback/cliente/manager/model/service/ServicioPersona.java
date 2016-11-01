package es.seas.feedback.cliente.manager.model.service;

import java.time.Month;
import java.util.List;
import es.seas.feedback.cliente.manager.model.dao.PersonaDAO;

/**
 * Esta interfaz es para manejo simples de personas.
 * @author Ricardo Maximino
 * <p>Este interfaz es igual que la interfaz PersonaDAO, tiene los mismos 
 * metodos.</p>
 */
public interface ServicioPersona<T> {
    /**
     * Este metodo fue pensado para configura la variable global dao del tipo 
     * PersonaDAO.
     * @param dao del tipo es.seas.feedback.cliente.manager.model.dao.PersonaDAO.
     */
     void setDao(PersonaDAO dao);
     /**
      * Este fue pensado para retornar el valor de la variable global dao del 
      * tipo PersonaDAO.
      * @return del tipo es.seas.feedback.cliente.manager.model.dao.PersonaDAO.
      */
     PersonaDAO getDao();
     
     /**
      * Este metodo fue pensado para añadir un nuevo registro en la base de 
      * datos.
      * @param persona del tipo especifica utilizando Generics &lt;T&gt;.
      * @return boolean para certificar si la acción fue realizada correctamente
      * o no.
      * <p>La idea general de este metodo es:</p>
      * dao.añadirNuevoRegistro(parametro);
      */
     boolean añadirNuevoRegistro(T persona);
     
     /**
      * Este metodo fue pensado para atualizar un registro en la base de 
      * datos.
      * @param persona del tipo especifica utilizando Generics &lt;T&gt;.
      * @return boolean para certificar si la acción fue realizada correctamente
      * o no.
      * <p>La idea general de este metodo es:</p>
      * dao.atualizarRegistro(parametro);
      */
     boolean atualizarRegistro(T persona);
     
     /**
      * Este metodo fue pensado para dar de baja al registro de una persona 
      * en la base de datos.
      * @param persona del tipo especifica utilizando Generics &lt;T&gt;.
      * @return boolean para certificar si la acción fue realizada correctamente
      * o no.
      * <p>La idea general de este metodo es:</p>
      * dao.darDebaja(parametro);
      */
     boolean darDeBaja(T persona);
     
     /**
      * Este metodo fue pensado para dar de baja al registro de una persona
      * en la base de datos.
      * @param nif del tipo String, identificador único en la tabla.
      * @return boolean para certificar si la acción fue realizada correctamente
      * o no.
      * <p>La idea general de este metodo es:</p>
      * dao.darDeBaja(parametro);
      */
     boolean darDeBaja(String nif);
     
     /**
      * Este metodo fue pensado para borrar un registro en la base de datos.
      * @param persona del tipo especifica utilizando Generics &lt;T&gt;.
      * @return boolean para certificar si la acción fue realizada correctamente
      * o no.
      * <p>La idea general de este metodo es:</p>
      * dao.borrarRegistro(parametro);
      */
     boolean borrarRegistro(T persona);
     
     /**
      * Este metodo fue pensado para borrar un registro en la base de datos.
      * @param nif del tipo String, identificador único el la tabla.
      * @return boolean para certificar si la acción fue realizada correctamente
      * o no.
      * <p>La idea general de este metodo es:</p>
      * dao.borrarRegistro(parametro);
      */
     boolean borrarRegistro(String nif);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;.
      * @return del tipo java.util.List&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.listarRegistro();
      */
     List<T> listarRegistros();
     
     /**
      * Este metodo fue pensado para buscar el registro de una persona en la
      * base de datos utilizando el parametro introducido.
      * @param nif del tipo String, identificador único en la tabla.
      * @return del tipo especificado utilizando Generics &lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistroPorNIF(parametro)
      */
     T buscarRegistroPorNIF(String nif);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro introducido.
      * @param primerApellido del tipo String;
      * @return del tipo java.util.List&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistroPorPrimerApellido(parametro);
      */
     List<T> buscarRegistrosPorPrimerApellido(String primerApellido);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro introducido.
      * @param segundoApellido del tipo String;
      * @return del tipo java.util.List&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistroPorSegundoApellido(parametro);
      */
     List<T> buscarRegistrosPorSegundoApellido(String segundoApellido);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro introducido.
      * @param nombre del tipo String;
      * @return del tipo java.util.List&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistroPorNombre(parametro);
      */
     List<T> buscarRegistrosPorNombre(String nombre);
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro ativo = false.
      * @return del tipo java.util.List&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistrosDadoDeBaja();
      */
     List<T> buscarRegistrosDadoDeBaja();
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro ativo = false.
      * @return del tipo java.util.List&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.buscarRegistrosDadoDeAlta();
      */
     List<T> buscarRegistrosDadoDeAlta();
     
     /**
      * Este metodo fue pensado para listar todos los registros de la tabla
      * referente a la clase especificada utilizando Generics &lt;T&gt;, y 
      * filtrado por el parametro introducido.
      * @param mes del tipo java.time.Month.
      * @return del tipo java.util.List&lt;T&gt;.
      * <p>La idea general de este metodo es:</p>
      * dao.cumpleañerosDelMes((parametro);
      */
     List<T> cumpleañerosDelMes(Month mes);
}
