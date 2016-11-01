package es.seas.feedback.cliente.manager.view;

import es.seas.feedback.cliente.manager.model.Persona;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Esta clase es el modelo para montar una tabla del tipo JTable con los datos
 * de una person(es.seas.feedback.cliente.manager.model.Persona).
 * @author Ricardo Maximino
 */
public class TableModelPersona extends AbstractTableModel{
    private List<Persona> datos;
    private String[] titulos;
    
    /**
     * Este constructor sin argumentos no hace nada más que instanciar un 
     * objeto de esta clase.
     */
    public TableModelPersona(){}
    /**
     * Este constructor ademas de instanciar un objeto de esta clase también
     * configura las dos variable globales.
     * @param datos del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Persona&gt;.
     * @param titulos del tipo String[].
     */
    public TableModelPersona(List<Persona> datos,String[] titulos){
        this.titulos = titulos;
        this.datos = datos;
    }

    /**
     * Este metodo retorna el valor de la variable global titulos.
     * @return del tipo String[].
     */
    public String[] getTitulos() {
        return titulos;
    }

    /**
     * Este metodo configura la variable global titulos.
     * @param titulos del tipo String[].
     */
    public void setTitulos(String[] titulos) {
        this.titulos = titulos;
    }
    
    /**
     * Este metodo configura la variable global datos.
     * @param datos del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Persona&gt;.
     */
    public void setDatos(List<Persona> datos){
        this.datos = datos;
    }
    
    /**
     * Este metodo retorna el valor de la variable global datos.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Persona&gt;.
     */
    public List<Persona> getDatos(){
        return datos;
    }

    /**
     * Este metodo retorna el size() de la variable global datos.
     * @return del tipo int.
     */
    @Override
    public int getRowCount() {
        return datos.size();
    }

    /**
     * Este metodo retorna el length de la variable global titulos.
     * @return del tipo int.
     */
    @Override
    public int getColumnCount() {
        return titulos.length;
    }

    /**
     * Este metodo retorna el objeto de las coordenadas pasadas por parametro.
     * @param rowIndex del tipo int.
     * @param columnIndex del tipo int.
     * @return del tipo java.lang.Object.
     * <p>Este metodo utiliza un switch para identificar las columnas.</p>
     * <br>
     * Object object = null;<br>
     * switch(columnIndex){<br>
     *     &emsp;case 0: object = datos.get(rowIndex).getId();break;<br>
     *     &emsp;case 1: object = datos.get(rowIndex).getNif();break;<br>
     *     &emsp;case 2: object = datos.get(rowIndex).getNombre();break;<br>
     *     &emsp;case 3: object = datos.get(rowIndex).getPrimerApellido();break;<br>
     *     &emsp;case 4: object = datos.get(rowIndex).getSegundoApellido();break;<br>
     *     &emsp;case 5: object = datos.get(rowIndex).getFechaNacimiento();break;<br>
     *     &emsp;case 6: object = datos.get(rowIndex).isActivo();break;<br>
     *     &emsp;case 7: object = datos.get(rowIndex).getFechaPrimeraAlta();break;<br>
     *     &emsp;case 8: object = datos.get(rowIndex).getFechaUltimaBaja();break;<br>
     * }<br>
     * return object;<br>
     */
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
    
    /**
     * Este metodo retorna el valor del Array pasado por parametro.
     * @param col del tipo int.
     * @return del tipo String.
     * <p>return titulos[col];</p>
     */
    @Override
    public String getColumnName(int col){
        return titulos[col];
    } 
    
}
