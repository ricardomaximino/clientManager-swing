package es.seas.feedback.cliente.manager.model.dao;

import java.time.Month;
import java.util.List;

/**
 * Esta interfaz es de operaciones simples para una persona, fue creada para 
 * quitar la dependencia de un DAO especifico para los servicios.
 * @author Ricardo Maximino
 */
public interface PersonaDAO<T> {
    /**
     * Este metodo esta pensado para intentar registrar una persona al banco de
     * datos.
     * @param persona del tipo especificado utilizando(Generics) &lt;T&gt;.
     * @return del tipo boolean para avisar se ha sido posible realizar la 
     * acción en la base de datos.
     * <p>La idea general del metodo es:</p>
     * boolean resultado = true;
     * try{<br>
     * registrar en la base de datos.<br>
     * }catch(SQLException ex){<br>
     * resultado = false;<br>
     * }<br>
     * return resultado.<br>
     */
     boolean añadirNuevoRegistro(T persona);
     
    /**
     * Este metodo esta pensado para intentar atualizar el registro de una 
     * persona al banco de datos.
     * @param persona del tipo especificado utilizando(Generics) &lt;T&gt;.
     * @return del tipo boolean para avisar se ha sido posible realizar la 
     * acción en la base de datos.
     * <p>La idea general del metodo es:</p>
     * boolean resultado = true;
     * try{<br>
     * utualizar en la base de datos.<br>
     * }catch(SQLException ex){<br>
     * resultado = false;<br>
     * }<br>
     * return resultado.<br>
     */
     boolean atualizarRegistro(T persona);
     
     /**
     * Este metodo esta pensado para intentar dar de baja a una persona al 
     * banco de datos.
     * @param persona del tipo especificado utilizando(Generics) &lt;T&gt;.
     * @return del tipo boolean para avisar se ha sido posible realizar la 
     * acción en la base de datos.
     * <p>La idea general del metodo es:</p>
     * boolean resultado = true;
     * try{<br>
     * dar de baja en la base de datos.<br>
     * }catch(SQLException ex){<br>
     * resultado = false;<br>
     * }<br>
     * return resultado.<br>
     */
     boolean darDeBaja(T persona);
     
    /**
     * Este metodo esta pensado para intentar registrar una persona al banco de datos.
     * @param nif del tipo String, Identificador único en la base de datos.
     * @return del tipo boolean para avisar se ha sido posible realizar la 
     * acción en la base de datos.
     * <p>La idea general del metodo es:</p>
     * boolean resultado = true;
     * try{<br>
     * dar de baja en la base de datos.<br>
     * }catch(SQLException ex){<br>
     * resultado = false;<br>
     * }<br>
     * return resultado.<br>
     */     
     boolean darDeBaja(String nif);
     
    /**
     * Este metodo esta pensado para intentar borrar el registro de una persona
     * del banco de datos.
     * @param persona del tipo especificado utilizando(Generics) &lt;T&gt;.
     * @return del tipo boolean para avisar se ha sido posible realizar la 
     * acción en la base de datos.
     * <p>La idea general del metodo es:</p>
     * boolean resultado = true;
     * try{<br>
     * borrar registro de la base de datos.<br>
     * }catch(SQLException ex){<br>
     * resultado = false;<br>
     * }<br>
     * return resultado.<br>
     */     
     boolean borrarRegistro(T persona);
     
    /**
     * Este metodo esta pensado para intentar registrar una persona al banco de datos.
     * @param nif del tipo String, identificador único en la base de datos.
     * @return del tipo boolean para avisar se ha sido posible realizar la 
     * acción en la base de datos.
     * <p>La idea general del metodo es:</p>
     * boolean resultado = true;
     * try{<br>
     * borrar registro de la base de datos.<br>
     * }catch(SQLException ex){<br>
     * resultado = false;<br>
     * }<br>
     * return resultado.<br>
     */     
     boolean borrarRegistro(String nif);
     
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * referente al tipo especificado utilizando Generics.
     * @return del tipo java.util.List&lt;T&gt; con todos los registros de la
     * tabla.
     * <p>La idea general del metodo es:</p>
     * List&lt;T&gt; resultado = new ArrayList&lt;&gt;();
     * try{<br>
     * resultado = listar todos los registro de la tabla.<br>
     * }catch(SQLException ex){<br>
     * <br>
     * }<br>
     * return resultado.<br>
     */     
     List<T> listarRegistros();

    /**
     * Este metodo esta pensado para buscar el registro de una persona en el 
     * banco de datos.
     * @param nif del tipo String, Identificador único en la base de datos.
     * @return del tipo especificado utilizando(Generics) &lt;T&gt;.
     * <p>La idea general del metodo es:</p>
     * T resultado = null;
     * try{<br>
     * resultado = buscar registro en la base de datos.<br>
     * }catch(SQLException ex){<br>
     * <br>
     * }<br>
     * return resultado.<br>
     */     
     T buscarRegistroPorNIF(String nif);
     
    /**
     * Este metodo esta pensado para buscar el registro de una persona en el
     * banco de datos.
     * @param id del tipo Long, La primery key en la base de datos.
     * @return del tipo especificado utilizando(Generics) &lt;T&gt;.
     * <p>La idea general del metodo es:</p>
     * T resultado = null;
     * try{<br>
     * resultado = buscar registro en la base de datos.<br>
     * }catch(SQLException ex){<br>
     * <br>
     * }<br>
     * return resultado.<br>
     */          
     T buscarRegistroPorID(long id);
     
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * referente al tipo especificado utilizando Generics filtrado por el 
     * parametro introducido.
     * @param primerApellido del tipo String.
     * @return del tipo java.util.List&lt;T&gt; con todos los registros de la
     * tabla.
     * <p>La idea general del metodo es:</p>
     * List&lt;T&gt; resultado = new ArrayList&lt;&gt;();
     * try{<br>
     * resultado = listar todos los registro de la tabla con el filtro.<br>
     * }catch(SQLException ex){<br>
     * <br>
     * }<br>
     * return resultado.<br>
     */          
     List<T> buscarRegistrosPorPrimerApellido(String primerApellido);
     
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * referente al tipo especificado utilizando Generics filtrado por el 
     * parametro introducido.
     * @param segundoApellido del tipo String.
     * @return del tipo java.util.List&lt;T&gt; con todos los registros de la
     * tabla.
     * <p>La idea general del metodo es:</p>
     * List&lt;T&gt; resultado = new ArrayList&lt;&gt;();
     * try{<br>
     * resultado = listar todos los registro de la tabla con el filtro.<br>
     * }catch(SQLException ex){<br>
     * <br>
     * }<br>
     * return resultado.<br>
     */     
     List<T> buscarRegistrosPorSegundoApellido(String segundoApellido);
     
     /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * referente al tipo especificado utilizando Generics filtrado por el 
     * parametro introducido.
     * @param nombre del tipo String.
     * @return del tipo java.util.List&lt;T&gt; con todos los registros de la
     * tabla.
     * <p>La idea general del metodo es:</p>
     * List&lt;T&gt; resultado = new ArrayList&lt;&gt;();
     * try{<br>
     * resultado = listar todos los registro de la tabla con el filtro.<br>
     * }catch(SQLException ex){<br>
     * <br>
     * }<br>
     * return resultado.<br>
     */
     List<T> buscarRegistrosPorNombre(String nombre);
     
     /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * referente al tipo especificado utilizando Generics filtrado por
     * personaActiva = false.
     * @return del tipo java.util.List&lt;T&gt; con todos los registros de la
     * tabla.
     * <p>La idea general del metodo es:</p>
     * List&lt;T&gt; resultado = new ArrayList&lt;&gt;();
     * try{<br>
     * resultado = listar todos los registro de la tabla con el filtro.<br>
     * }catch(SQLException ex){<br>
     * <br>
     * }<br>
     * return resultado.<br>
     */
     List<T> buscarRegistrosDadoDeBaja();
     
     /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * referente al tipo especificado utilizando Generics filtrado por
     * personaActiva = true.
     * @return del tipo java.util.List&lt;T&gt; con todos los registros de la
     * tabla.
     * <p>La idea general del metodo es:</p>
     * List&lt;T&gt; resultado = new ArrayList&lt;&gt;();
     * try{<br>
     * resultado = listar todos los registro de la tabla con el filtro.<br>
     * }catch(SQLException ex){<br>
     * <br>
     * }<br>
     * return resultado.<br>
     */     
     List<T> buscarRegistrosDadoDeAlta();
     
    /**
     * Este metodo esta pensado para listar todos lo registros de la tabla 
     * referente al tipo especificado utilizando Generics filtrado por el 
     * parametro introducido.
     * @param mes del tipo java.time.Month.
     * @return del tipo java.util.List&lt;T&gt; con todos los registros de la
     * tabla.
     * <p>La idea general del metodo es:</p>
     * List&lt;T&gt; resultado = new ArrayList&lt;&gt;();
     * try{<br>
     * resultado = listar todos los registro de la tabla con el filtro.<br>
     * }catch(SQLException ex){<br>
     * <br>
     * }<br>
     * return resultado.<br>
     */     
     List<T> cumpleañerosDelMes(Month mes);
}
