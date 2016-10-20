/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.view.usuario;

import es.seas.feedback.cliente.manager.model.Usuario;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Ricardo
 */
public class TableModelUsuario extends AbstractTableModel{
    private List<Usuario> datos;
    private String[] titulos;
    
    public TableModelUsuario(){}
    public TableModelUsuario(List<Usuario> datos,String[] titulos){
        this.titulos = titulos;
        this.datos = datos;
    }

    public String[] getTitulos() {
        return titulos;
    }

    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
    }
    
    public void setDatos(List<Usuario> datos){
        this.datos = datos;
    }
    public List<Usuario> getDatos(){
        return datos;
    }

    @Override
    public int getRowCount() {
        return datos.size();
    }

    @Override
    public int getColumnCount() {
        return titulos.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object object = null;
       switch(columnIndex){
           case 0: object = datos.get(rowIndex).getId();break;
           case 1: object = datos.get(rowIndex).getNif();break;
           case 2: object = datos.get(rowIndex).getNombre();break;
           case 3: object = datos.get(rowIndex).getPrimerApellido();break;
           case 4: object = datos.get(rowIndex).getSegundoApellido();break;
           case 5: object = datos.get(rowIndex).getFechaNacimiento();break;
           case 6: object = datos.get(rowIndex).isActivo();break;
           case 7: object = datos.get(rowIndex).getFechaPrimeraAlta();break;
           case 8: object = datos.get(rowIndex).getFechaUltimaBaja();break;
       }
       return object;
    }
    
    @Override
    public String getColumnName(int col){
        return titulos[col];
    } 
    
}
