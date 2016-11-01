package es.seas.feedback.cliente.manager.model.dao.datos;

import es.seas.feedback.cliente.manager.model.Localidade;
import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.model.dao.ClienteDAO;
import es.seas.feedback.cliente.manager.model.dao.ProvinciaDAO;
import es.seas.feedback.cliente.manager.model.dao.jdbc.ProvinciaJDBCDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase introduce provincias y localidades a la base de datos utilizando
 * archos de texto.
 *
 * @author Ricardo Maximino
 */
public class LectorEscritor {

    private static final Logger LOG = LoggerFactory.getLogger(LectorEscritor.class);
    
    public LectorEscritor() {
        LOG.info("LectorEscritor was instantiated " + LocalDate.now().toString());
    }

    /**
     * Este metodo lee las provincias y localidades y las registra en la base
     * de datos.
     * @param provinciasAchivo del tipo String, con la dirección del archivo 
     * en disco.
     * @param localidadesArchivo de tipo String con la direccion del archivo
     * en disco.
     * <p>En el archivo provincias debe estar de la siguiente manera:</p>
     * 1,Nombre_de_la_Provincia<br>
     * 2,Nombre_de_la_Provincia<br>
     * 
     * <p>Siempre el código de la provincia separado por coma del nombre de la
     * provincia. El código de la provincia es la llave primaria y el nombre
     * tiene constraint UNIQUE, asi que no se puede repetir ni el nombre, ni
     * el código de la provincia en el archivo.</p>
     * <br>
     * 
     * <p>En el archivo localidades debe estar de la siguiente manera:</p>
     * 1,1,Nombre_de_la_Localidade<br>
     * 2,1,Nombre_de_la_Localidade<br>
     * 3,1,Nombre_de_la_Localidade<br>
     * 4,2,Nombre_de_la_Localidade<br>
     * 5,2,Nombre_de_la_Localidade<br>
     * <br>
     * El metodo cria una String[] valores = line.split(",");<br>
     * luego utiliza if(provincia.getId().equal(Long.parse(valores[1].trim())){
     * Localidade loc = new Localidade( valores[2].trim(),Nombre_de_Provincia);}
     * <p>El primer dato, todo lo que este antes de la primera coma no se utiliza
     * , poderia incluso hacerse asi:</p>
     * ,1,Nombre_de_la_Localidade<br>
     */
    public void leerProvinciasYLocalidades(String provinciasAchivo, String localidadesArchivo) {
        String provinciasFile = "provincias/provincias.sql";
        String localidadesFile = "provincias/localidades.sql";       
        if (provinciasAchivo != null) {
            provinciasFile = provinciasAchivo;
        }
        if (localidadesArchivo != null) {
            localidadesFile = localidadesArchivo;
        }      
        ProvinciaDAO dao = new ProvinciaJDBCDAO();       
        LOG.info("Leyendo y registrando provincias...");
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(provinciasFile)), "latin1");            
            BufferedReader br = new BufferedReader(inputStreamReader);            
            String[] line = br.readLine().split(",");
            Provincia provincia;
            while (line != null) {
                provincia = new Provincia(line[1].trim(), Long.parseLong(line[0]));
                dao.guardarProvincia(provincia);                
                line = br.readLine().split(",");
            }
            LOG.info("Provincias registradas correctamente.");
        } catch (FileNotFoundException ex) {
            LOG.error("El archivo no existe.",ex);
        } catch (IOException ex) {
            LOG.error("Error en la lectura",ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            LOG.error("Final del Archivo",ex);
        }
        
        LOG.info("Leyendo y registrando localidades");
        for (Provincia provincia : dao.getLista()) {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(localidadesFile)), "latin1");                
                BufferedReader br = new BufferedReader(inputStreamReader);
                String sline = br.readLine();
                String[] line;
                while (sline != null) {                    
                    line = sline.split(",");                    
                    if (provincia.getId() == Long.parseLong(line[1].trim())) {
                        provincia.getLocalidades().add(new Localidade(line[2].trim(), provincia.getNombre()));
                    }                    
                    sline = br.readLine();
                }
                LOG.info("Localidades de la provincia de " +provincia.getNombre()+ " registradas correctamente.");
            } catch (FileNotFoundException ex) {
                LOG.error("El archivo no existe.", ex);
            } catch (IOException ex) {
                LOG.error("Error en la lectura",ex);
            };
            dao.guardarProvincia(provincia);          
        }
    }
    
}
