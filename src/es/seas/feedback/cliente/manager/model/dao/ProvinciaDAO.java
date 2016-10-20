/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model.dao;

import es.seas.feedback.cliente.manager.model.Provincia;
import java.util.List;

/**
 *
 * @author Ricardo
 */
public interface ProvinciaDAO {
    boolean guardarProvincia(Provincia provincia);
    Provincia getProvincia(String nombre);
    List<Provincia> getList();
}
