package es.seas.feedback.cliente.manager.view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.TableColumnModel;

import es.seas.feedback.cliente.manager.model.Localidade;
import es.seas.feedback.cliente.manager.model.Provincia;

/**
 * Esta clase provee funciones utiles para varias otras clases de la aplicación
 * en la camada de visualización.
 * @author Ricardo Maximino
 */
public class PersonaUtilidades {
    private String[] titulosTablaDePersonas;
    private String[] mesesDelAño;
    private String[] camposPersona;
    private ResourceBundle view = ResourceBundle.getBundle("view.messages.view");
    
    /**
     * Este constructor sin argumento simplemente configura las variables 
     * utilizando el default.
     */
    public PersonaUtilidades(){
        setArrays();
    }
    /**
     * Este metodo configura novamente las variables utilizando el default.
     * Impresindible para el cambio de idiomas.
     */
    public void refresh(){
        view = ResourceBundle.getBundle("view",Locale.getDefault());
        setArrays();
    }

    /**
     * Este metodo llama el metodo refresh().
     */
    public void refreshInternationalization(){
        setArrays();
    }
    /**
     * Este metodo retorna los titulos de la tabla para personas en el idioma
     * configurado como default.
     * @return del tipo String[].
     */
    public String[] getTitulosTablasDePersonas(){
        return titulosTablaDePersonas;
    }
    /**
     * Este metodo retorna los campos para filtros de personas en el idioma
     * seleccionado como default.
     * @return del tipo String[].
     */
    public String[] getCamposPersona(){
        return camposPersona;
    }
    /**
     * Este metodo retorna los meses del año en el idioma seleccionado como
     * default.
     * @return del tipo String[].
     */
    public String[] getMesesDelAño(){
        return mesesDelAño;
    }
    /**
     * Retorna una String con los meses del año separado por coma en el idioma seleccionado como default.
     * @return Retorna una String con los meses del año.
     * 
     * <p>Este metodo simplesmente crea un StringBuilder y dentro de un For que
     * utiliza la variable privada mesesDelAño de la class PersonaUtilidades para añadir los meses
     * en el StringBuilder y al final retorna el toString del StringBuilder.</p>
     */
    public String getMesesDelAñoAsString(){
        StringBuilder sb = new StringBuilder();
        for(String s : mesesDelAño){
            System.out.println(s);
            sb.append(s);
            sb.append(", ");
        }
        return sb.toString().substring(0,sb.toString().lastIndexOf(","));
    }
    /**
     * Este metodo configura el ancho de las columnas del TableColumnModel
     * pasado como parametro.
     * @param columnModel del tipo javax.swing.table.TableColumnModel.
     * @return del tipo javax.swing.table.TableColumnModel, el mismo pasado
     * como parametro pero ya con las columnas configuradas.
     */
    public  TableColumnModel configurarAnchoDeLasColumnas(TableColumnModel columnModel){
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(70);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(70);
        columnModel.getColumn(6).setPreferredWidth(20);
        columnModel.getColumn(7).setPreferredWidth(70);
        columnModel.getColumn(8).setPreferredWidth(70);
        return columnModel;
    }
    
    /**
     * Este metodo converte los meses pasados como String el en idioma 
     * seleccionado como default en java.time.Month.
     * @param mes del tipo String.
     * @return del tipo java.time.Month.
     * <p>Si la String pasada como argumento no sea valida en el idioma 
     * selecionado como default, el metodo retornará null.</p>
     */
    public Month validarMes(String mes) {
        
        List<String> listaDeMeses = new ArrayList<>(Arrays.asList(mesesDelAño));
        Month month = null;
        try{
            month = Month.of(listaDeMeses.indexOf(mes.toUpperCase())+1);
        }catch(DateTimeException ex){}
        return month;
    }
    
    //FrameCliente y FrameUsuario
    /**
     * Este metodo crea y configura un DefaultComboBoxModel&lt;String&gt;.
     * @return del tipo javax.swing.DefaultComboBoxModel.
     */
    public DefaultComboBoxModel<String> camposPersonaComboModel(){
            DefaultComboBoxModel<String> camposPersonaModel = new DefaultComboBoxModel<String>();
        for(String s: camposPersona){
            camposPersonaModel.addElement(s);
            
        }
        camposPersonaModel.setSelectedItem(camposPersona[0]);
        return camposPersonaModel;
    }
    
    
    //FrameCliente y FrameUsuario
    /**
     * Este metodo configura el JComboBox&lt;String&gt; pasado como parametro.
     * @param cmbDia del tipo javax.swing.JComboBox&lt;String&gt;.
     * <p>El JComboBox será configurado con los días del mes del 1 al 31.</p>
     */
    public void setComboDia(JComboBox<String> cmbDia) {
        DefaultComboBoxModel<String> dia = new DefaultComboBoxModel<>();
        for (Integer i = 0; i < 32; i++) {
            dia.addElement("" + i);
        }
        dia.setSelectedItem("1");
        cmbDia.setModel(dia);
    }
    //FrameCliente y FrameUsuario
    /**
     * Este metodo configura el JComboBox&lt;String&gt; pasado como parametro.
     * @param cmbMes del tipo javax.swing.JComboBox&lt;String&gt;.
     * <p>El JComboBox será configurado con los meses del año en el
     * idioma seleccionado como default.</p>
     */
     public void setComboMes(JComboBox<String> cmbMes) {
        DefaultComboBoxModel<String> mes = new DefaultComboBoxModel<>();
        for (String m : mesesDelAño) {
            mes.addElement(m);
        }
        mes.setSelectedItem(mesesDelAño[0]);
        cmbMes.setModel(mes);
    }
    //FrameCliente y FrameUsuario
     /**
     * Este metodo configura el JComboBox&lt;String&gt; pasado como parametro.
     * @param yearsFromNow del tipo Integer.
     * @param selectedItemFromNow del tipo Integer.
     * @param cmbAño del tipo javax.swing.JComboBox&lt;String&gt;.
     * <p>El JComboBox será configurado de la seguiente manera:</p><br>
     * DefaultComboBoxModel&lt;String&gt; año = new DefaultComboBoxModel&lt;&gt;();<br>
     * if (yearsFromNow == null) {<br>
     *      yearsFromNow = 100;<br>
     *  }<br>
     *  if (selectedItemFromNow == null) {<br>
     *      selectedItemFromNow = 50;<br>
     *  }<br>
     * <br>
     *  int year = LocalDate.now().getYear();<br>
     *  for (int i = year - yearsFromNow; i &lt; year + 1; i++) {<br>
     *      año.addElement("" + i);<br>
     *  }<br>
     *  año.setSelectedItem("" + (year - selectedItemFromNow));<br>
     *  cmbAño.setModel(año);<br>
     */
    public void setComboAño(Integer yearsFromNow, Integer selectedItemFromNow,JComboBox<String> cmbAño) {
        DefaultComboBoxModel<String> año = new DefaultComboBoxModel<>();
        if (yearsFromNow == null) {
            yearsFromNow = 100;
        }
        if (selectedItemFromNow == null) {
            selectedItemFromNow = 50;
        }

        int year = LocalDate.now().getYear();
        for (int i = year - yearsFromNow; i < year + 1; i++) {
            año.addElement("" + i);
        }
        año.setSelectedItem("" + (year - selectedItemFromNow));
        cmbAño.setModel(año);
    }
    //FrameCliente y FrameUsuario
    /**
     * Este metodo configura las provicias en el JComboBox utilizando las
     * provincias contidas el el mapa y configura como preselecionada
     * la provincia igual al parametro pasado como selectedItem.
     * @param provincias del tipo java.util.Map&lt;String,Provincia&gt;.
     * @param selectedItem del tipo String, provincia a ser preseleccionada.
     * @param cmbProvincia del tipo javax.swing.JComboBox&lt;String&gt;
     */
    public void setComboProvincia(Map<String, Provincia> provincias,String selectedItem,JComboBox<String> cmbProvincia) {
        DefaultComboBoxModel<String> provincia = new DefaultComboBoxModel<>();
        for (Provincia p : provincias.values()) {
            provincia.addElement(p.getNombre());
        }
        if (selectedItem != null) {
            provincia.setSelectedItem(selectedItem);
        } else {
            provincia.setSelectedItem(provincia.getElementAt(0));
        }
        cmbProvincia.setModel(provincia);
    }
    //FrameCliente y FrameUsuario
    /**
     * Este metodo configura las localidades en el JComboBox utilizando las
     * provincias del contidas el el mapa y configura como preselecionada
     * la provincia igual al parametro pasado como selectedItem.
     * @param provincias del tipo java.util.Map&lt;String,Provincia&gt;.
     * @param provincia del tipo String, provincia selecionada.
     * @param selectedItem del tipo String, localidade a ser preseleccionada.
     * @param cmbLocalidade del tipo javax.swing.JComboBox&lt;String&gt;
     */
    public void setComboLocalidade(Map<String, Provincia> provincias, String provincia, String selectedItem, JComboBox<String> cmbLocalidade) {
        if (provincia != null) {
            DefaultComboBoxModel<String> localidade = new DefaultComboBoxModel<>();
            List<Localidade> localidades = provincias.get(provincia).getLocalidades();
            for (Localidade l : localidades) {
                localidade.addElement(l.getNombre());
            }
            if (selectedItem != null) {
                localidade.setSelectedItem(selectedItem);
            } else {
                localidade.setSelectedItem(localidade.getElementAt(0));
            }
            cmbLocalidade.setModel(localidade);
        }
    }

    private void setArrays(){
        titulosTablaDePersonas = new String[9];
        titulosTablaDePersonas[0]= view.getString("tableColumn_ID2");
        titulosTablaDePersonas[1]= view.getString("tableColumn_ID");
        titulosTablaDePersonas[2]= view.getString("tableColumn_NAME");
        titulosTablaDePersonas[3]= view.getString("tableColumn_FIRSTLASTNAME");
        titulosTablaDePersonas[4]= view.getString("tableColumn_SECONDLASTNAME");
        titulosTablaDePersonas[5]= view.getString("tableColumn_BIRTHDAY");
        titulosTablaDePersonas[6]= view.getString("tableColumn_ACTIVE");
        titulosTablaDePersonas[7]= view.getString("tableColumn_FIRSTREGISTRY");
        titulosTablaDePersonas[8]= view.getString("tableColumn_LASTCUTOFFSERVICE");
        
        mesesDelAño = new String[12];
        mesesDelAño[0]= view.getString("month_JANUARY");
        mesesDelAño[1]= view.getString("month_FEBRUARY");
        mesesDelAño[2]= view.getString("month_MARCH");
        mesesDelAño[3]= view.getString("month_APRIL");
        mesesDelAño[4]= view.getString("month_MAY");
        mesesDelAño[5]= view.getString("month_JUNE");
        mesesDelAño[6]= view.getString("month_JULY");
        mesesDelAño[7]= view.getString("month_AUGUST");
        mesesDelAño[8]= view.getString("month_SEPTEMBER");
        mesesDelAño[9]= view.getString("month_OCTOBER");
        mesesDelAño[10]= view.getString("month_NOVEMBER");
        mesesDelAño[11]= view.getString("month_DECEMBER");
        
        camposPersona = new String[7];
        camposPersona[0]= view.getString("ID");
        camposPersona[1]= view.getString("NAME");
        camposPersona[2]= view.getString("FIRST_LASTNAME");
        camposPersona[3]= view.getString("SECOND_LASTNAME");
        camposPersona[4]= view.getString("CUTED_OFF");
        camposPersona[5]= view.getString("JOINED");
        camposPersona[6]= view.getString("BIRTHDAY_THIS_MONTH");
    }    
}
