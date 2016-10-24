/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.view;

import es.seas.feedback.cliente.manager.model.Localidade;
import es.seas.feedback.cliente.manager.model.Provincia;
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

/**
 *
 * @author Ricardo
 */
public class PersonaUtilidades {
    private String[] titulosTablaDePersonas;
    private String[] mesesDelAño;
    private String[] camposPersona;
    private ResourceBundle view = ResourceBundle.getBundle("es.seas.feedback.cliente.manager.view.internationalization.view");
    
    public PersonaUtilidades(){
        setArrays();
    }
    public void refresh(){
        view = ResourceBundle.getBundle("es.seas.feedback.cliente.manager.view.internationalization.view",Locale.getDefault());
        setArrays();
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
    public void refreshInternationalization(){
        setArrays();
    }
    public String[] getTitulosTablasDePersonas(){
        return titulosTablaDePersonas;
    }
    public String[] getCamposPersona(){
        return camposPersona;
    }
    public String[] getMesesDelAño(){
        return mesesDelAño;
    }
    /**
     * Retorna una String con los meses del año separado por coma en el idioma seleccionado como default.
     * @return Retorna una String con los meses del año.
     * 
     * Este metodo simplesmente crea un StringBuilder y dentro de un For que
     * utiliza la variable privada mesesDelAño de la class PersonaUtilidades para añadir los meses
     * en el StringBuilder y al final retorna el toString del StringBuilder.
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
    // ControlCliente y ControlUsuario
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
    
     /*El metodo parseStringTOMonth utiliza un switch para verificar cual es el
    mes que introdujo el usuario y return un Month.
    En caso de que el mes pasado por parametro no estea escrito correctamente(IgnoreCase)
    el metodo retornará null*/
    // ControlCliente y ControlUsuario
    public Month validarMes(String mes) {
        
        List<String> listaDeMeses = new ArrayList<>(Arrays.asList(mesesDelAño));
        Month month = null;
        try{
            month = Month.of(listaDeMeses.indexOf(mes.toUpperCase())+1);
        }catch(DateTimeException ex){}
        return month;
    }
    
    //BuscarCliente y BuscarUsuario
    public DefaultComboBoxModel<String> camposPersonaComboModel(){
            DefaultComboBoxModel<String> camposPersonaModel = new DefaultComboBoxModel();
        for(String s: camposPersona){
            camposPersonaModel.addElement(s);
            
        }
        camposPersonaModel.setSelectedItem(camposPersona[0]);
        return camposPersonaModel;
    }
    
    //FrameCliente y FrameUsuario
    public void setComboDia(JComboBox<String> cmbDia) {
        DefaultComboBoxModel<String> dia = new DefaultComboBoxModel<>();
        for (Integer i = 0; i < 32; i++) {
            dia.addElement("" + i);
        }
        dia.setSelectedItem("1");
        cmbDia.setModel(dia);
    }
    //FrameCliente y FrameUsuario
     public void setComboMes(JComboBox<String> cmbMes) {
        DefaultComboBoxModel<String> mes = new DefaultComboBoxModel<>();
        for (String m : mesesDelAño) {
            mes.addElement(m);
        }
        mes.setSelectedItem(mesesDelAño[0]);
        cmbMes.setModel(mes);
    }
    //FrameCliente y FrameUsuario
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
    public void setComboProvincia(Map<String,Provincia> provincias,String selectedItem,JComboBox<String> cmbProvincia) {
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
    public void setComboLocalidade(Map<String,Provincia> provincias,String provincia, String selectedItem,JComboBox<String> cmbLocalidade) {
        if (provincia != null) {
            DefaultComboBoxModel<String> localidade = new DefaultComboBoxModel<>();
            for (Localidade l : provincias.get(provincia).getLocalidades()) {
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
}
