/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model.dao.datos;

import es.seas.feedback.cliente.manager.model.Localidade;
import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.model.dao.ProvinciaDAO;
import es.seas.feedback.cliente.manager.model.dao.ProvinciaDAOImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Ricardo
 */
public class LectorEscritor {
    
     public void leerProvinciasYLocalidades(String provinciasAchivo, String localidadesArchivo) {
         String provinciasFile = "provincias/provincias.sql";
         String localidadesFile = "provincias/localidades.sql";

         if(provinciasAchivo != null) provinciasFile = provinciasAchivo;
         if(localidadesArchivo != null)localidadesFile = localidadesArchivo;
         
        ProvinciaDAO dao = new ProvinciaDAOImpl();      
        
            System.out.println("Leyendo y registrando provincias...");
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(provinciasFile)),  "latin1");     
                BufferedReader br = new BufferedReader(inputStreamReader);             
                String[] line = br.readLine().split(" ");
                Provincia provincia = null;
                while (line!= null){                 
                    //System.out.println(line[0] + " " + line[1]);
                    provincia = new Provincia(line[1].trim(),Long.parseLong(line[0]));
                    dao.guardarProvincia(provincia);                    
                    line = br.readLine().split(" ");
                }
            } catch (FileNotFoundException ex) {
                System.out.println("El archivo no existe.");
            } catch (IOException ex) {
                System.out.println("Error en la lectura");
            }catch(ArrayIndexOutOfBoundsException ex){
                System.out.println("Final del Archivo");
            }
            
        
        System.out.println("Leyendo y registrando localidades");
        //Localidades
        for (Provincia provincia : dao.getLista()) {
            //System.out.println(provincia.getNombre());
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(localidadesFile)),  "latin1"); 
                BufferedReader br = new BufferedReader(inputStreamReader);
                String sline = br.readLine();
                String[] line = null;
                while (sline != null){ 
                    line = sline.split(",");
                   // System.out.println(line[0] + " " +line[1]+ " "+line[2]);
                  
                    if(provincia.getId()== Long.parseLong(line[1].trim())){
                        provincia.getLocalidades().add(new Localidade(line[2].trim(), provincia.getNombre()));
                    }  
                    sline = br.readLine();
                }
            } catch (FileNotFoundException ex) {
                System.out.println("El archivo no existe.");
            } catch (IOException ex) {
                System.out.println("Error en la lectura");
            }
            //System.out.println("Guardando la provincia de " + provincia.getNombre() + " con " + provincia.getLocalidades().size() + " localidades");
            dao.guardarProvincia(provincia);
            
        }
       // System.out.println("Lectura y registro finalizado.");
    }

    
}
