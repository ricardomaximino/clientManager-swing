/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model.dao;

import java.time.Month;
import java.util.List;

/**
 *
 * @author Ricardo
 */
public interface PersonaDAO<T> {
     boolean añadirNuevoRegistro(T persona);
     boolean atualizarRegistro(T persona);
     boolean darDeBaja(T persona);
     boolean darDeBaja(String nif);
     boolean borrarRegistro(T persona);
     boolean borrarRegistro(String nif);
     List<T> listarRegistros();
     T buscarRegistroPorNIF(String nif);
     T buscarRegistroPorID(long id);
     List<T> buscarRegistrosPorPrimerApellido(String segundoApellido);
     List<T> buscarRegistrosPorSegundoApellido(String primerApellido);
     List<T> buscarRegistrosPorNombre(String nombre);
     List<T> buscarRegistrosDadoDeBaja();
     List<T> buscarRegistrosDadoDeAlta();
     List<T> cumpleañerosDelMes(Month mes);
}
