/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.control;

import es.seas.feedback.cliente.manager.model.Cliente;
import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.view.cliente.TableModelCliente;
import es.seas.feedback.cliente.manager.view.Controlable;
import es.seas.feedback.cliente.manager.view.cliente.BuscarCliente;
import es.seas.feedback.cliente.manager.view.cliente.FrameCliente;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import es.seas.feedback.cliente.manager.model.service.ServicioPersona;
import es.seas.feedback.cliente.manager.view.PersonaUtilidades;
import es.seas.feedback.cliente.manager.view.ListaPersona;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Ricardo Maximino Gonçalves de Moraes
 * Esta Class es donde se deve ejecutar toda la regla de negocio referente al Cliente.
 * He criado una interface(ClienteControl) simplesmente para desvincular la view
 * del control
 */
public class ClienteControl implements PersonaControl<Cliente> {

    //?? --- Internacionalization
    private final ResourceBundle view = ResourceBundle.getBundle("es.seas.feedback.cliente.manager.view.internationalization.view");
    private final String NUEVO_CLIENTE = view.getString("message_REGISTRING_NEW_CLIENT");
    private final String TABLA_DESACTUALIZADA = view.getString("message_CLIENT_TABLE_IS_NOT_UPDATE");
    private final String BUSQUEDA_NO_IMPLEMENTADA = view.getString("message_THIS_KIND_OF_SEARCH_IS_NOT_IMPLEMENTED");
    private final String ATUALIZANDO_CLIENTE = view.getString("message_UPDATING_CLIENT");
    private String MESES_DEL_AÑO;
    
    private ServicioPersona<Cliente> servicio;
    private BuscarCliente buscarCliente;
    private Controlable principal;
    private Map<Cliente, JInternalFrame> frames;
    private Map<String,Provincia> provincias;
    private PersonaUtilidades personaUtilidades;


    /*Constructore con argumento argumento @Param VentanaPrincipal*/
    public ClienteControl(ServicioPersona serv,Controlable princip ,Map<String,Provincia> prov) {
        frames = new HashMap<>();
        this.servicio = serv;
        this.principal = princip;
        principal.setClienteControl(this);
        this.provincias = prov;
    }

    /*set VentanaPrincipal*/
    @Override
    public void setPrincipal(Controlable princip) {
        this.principal = princip;
        principal.setClienteControl(this);
    }

    /*get VentanaPrincipal*/
    @Override
    public Controlable getPrincipal() {
        return principal;
    }

    /*set ServicioCliente*/
    @Override
    public void setServicio(ServicioPersona serv) {
        this.servicio = serv;
    }

    /*get servicioCliente*/
    @Override
    public ServicioPersona getServicio() {
        return servicio;
    }
    
    @Override
    public Map<String, Provincia> getProvincias() {
        return provincias;
    }  
    
    @Override
    public void setProvincias(Map<String,Provincia> prov) {
        this.provincias = prov;
    }
    
    @Override
    public void setPersonaUtilidades(PersonaUtilidades personaUtilidades){
        this.personaUtilidades = personaUtilidades;
        StringBuilder sb = new StringBuilder();
        for(String s : personaUtilidades.getMesesDelAño()){
            sb.append(sb);
            sb.append(", ");
        }
        MESES_DEL_AÑO=view.getString("message_THE_MONTHS_OF_THE_YEAR_ARE") + sb.toString().substring(0,sb.toString().lastIndexOf(","));
    }
    
    @Override
    public PersonaUtilidades getPersonaUtilidades(){
        return personaUtilidades;
    }

    //Metodos para crear, manipular y borrar Clientes
    /*El Metodo guardarCliente primeramente comprueva si hay algun cliente ya
    registrado con el nif del cliente pasado como parametro.
    Si el resultado de esa busqueda sea null se llama el metodo del ServicioCliente
    registrarNuevoCliente.
    Se el resultado de esa busqueda sea true se llama el metodo del ServicioCliente
    atualizarCliente.*/
    @Override
    public boolean guardar(Cliente cliente) {
        if (servicio.buscarRegistroPorNIF(cliente.getNif()) != null) {
            servicio.atualizarRegistro(cliente);
            return true;
        } else {
            servicio.añadirNuevoRegistro(cliente);
            return true;
        }
    }

    /*El metodo crearFrameListarTodos crea un ListaCliente(esa class extends JIternalFrame)
    y pasa como argumento un TableModelCliente (esa class extends AbstractTableModel),
    con la lista de clientes pasada como argumento.
    Llama el metodo setControl de la class ListaCliente y pasa una referencia this.
    Configura el ancho de las columnas de la tabla de la class ListaCliente
    y finalmente llama el metodo showInternalFrame de la class VentanaPrincipal(esa class extends JFrame)
    y pasa la referencia de la class  ListaCliente creada.*/
    private void crearFrameListaCliente(List<Cliente> list,String titulo) {
        ListaPersona lista = new ListaPersona(new TableModelCliente(list,personaUtilidades.getTitulosTablasDePersonas()));
        lista.setTitle(titulo + " - " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        lista.getTabla().setColumnModel(personaUtilidades.configurarAnchoDeLasColumnas(lista.getTabla().getColumnModel()));
        lista.setControl(this);  
        principal.showInternalFrame(lista);
    }

    /*El metodo listarTodos crea un ListaCliente(esa class extends JInternalFrame)
    usando el metodo privado crearFrameListaCliente y pasa como parametro una
    lista con todos los clientes llamando el metodo listarClientes de la class ServicioCliente*/
    @Override
    public void listarTodos() {
        crearFrameListaCliente(servicio.listarRegistros(),view.getString("message_LIST_OF_ALL_CLIENTS") );
    }

    /*El metodo crearFrameNuevoCliente llama el metodo showInternalFrame de la class 
    VentanaPrincipal(esa class extends JFrame)y pasa como argumento una referencia
    de la class FrameCliente(esa class extends JInternalFrame) creada usando 
    el metodo private crearFrameCliente que lleva como argumento un TITULO del
    frame del tipo String, un CLIENTE del tipo Cliente
    y un boolean(indicando si desea que el botón que permite borrar cliente sea visible o no*/
    @Override
    public void crearFrameAñadir() {
        principal.showInternalFrame(crearFrameCliente(NUEVO_CLIENTE, null, false));
        
    }

    /*El metodo creaFrameAtualizarCliente crea una variable del tipo Cliente,
    iguala esa variable con el retorno del metodo buscarCliente de la class
    ServicoClienteImpl(esa class implements ServicioCliente).
    Luego utiliza un if para comprobar si la variable cliente es !=  the null.
    Si la variable sea != de null, entonces se crea una variable del Tipo JInternalFrame
    y iguala esa variable al retorno del Map.get que recibe como key la referencia
    de un cliente. Si esta key exista en el Map signfica que el cliente indicado
    ya tiene un JInternalFrame abierto con sus datos,entoces no se necesita crear
    otro, sino, traer el JIternalFrame con eses datos al frente utilizando el
    metodo activateFrame del DesktopManager del DesktopPane de la VentanaPrincipal. Si
    esta key no exista en el Map entoces se creará un FrameCliente(esa class extends JInternalFrame)
    usando el metodo private crearFrameCliente se añadirá el cliente y el frame
    en el Map(hasta que el frame fire el evento closing, lo cual llamara el metodo
    closeFrame que utilizará el metodo remove de la class Map para remover la 
    referencia del frame usando como key el keyCliente,o sea, el cliente usado
    para crear el frame) exibirá el frame creado usando el metodo showInternalFrame de la
    class VentanaPrincipal(esa class extends JFrame).
    Si la variable set == null, entoces se exibirá un JOptionPane informando que
    la tabla esta desactualizada.
     */
    @Override
    public void crearFrameAtualizar(String nif) {
        Cliente cliente = servicio.buscarRegistroPorNIF(nif);
        if (cliente != null) {
            JInternalFrame frame = frames.get(cliente);
            if (frame != null) {
                principal.getDesktopPane().getDesktopManager().activateFrame(frame);
                principal.getDesktopPane().setSelectedFrame(frame);
            } else {
                StringBuilder titulo = new StringBuilder(ATUALIZANDO_CLIENTE);
                titulo.append(cliente.getNombre());
                titulo.append(" ");
                titulo.append(cliente.getPrimerApellido());
                titulo.append(" ");
                titulo.append(cliente.getSegundoApellido());
                frame = crearFrameCliente(titulo.toString(), cliente, true);
                principal.showInternalFrame(frame);
                frames.put(cliente, frame);
            }
        } else {
            JOptionPane.showMessageDialog(null, TABLA_DESACTUALIZADA);
        }
    }

    /*El metodo closeFrame comprueba si el frame pasado por parametro es una intancia
    de la class FrameCliente(esa class extends JInternalFrame) y en el caso que
    sea true se remove del Map usando como key el keyCliente, que es el mismo cliente
    usado para crear el frame.*/
    @Override
    public void closeFrame(JInternalFrame frame) {
        if (frame instanceof FrameCliente) {
            frames.remove(((FrameCliente) frame).getKeyCliente());
        }
    }

    /*El Metodo crearFrameCliente es un metodo private y retorna un JInternalFrame
    pide como parametro el titulo del frame del tipo String,el cliente del tipo
    Cliente(lo cual puede ser null en caso de nuevo cliente) y un valor boolean
    que hará con que el botón para borrar cliente sea visible o no.*/
    private JInternalFrame crearFrameCliente(String titulo, Cliente cliente, boolean borrarVisible) {
        FrameCliente frame = new FrameCliente(provincias,personaUtilidades);
        frame.setTitle(titulo);
        frame.setControl(this);
        frame.borrarEsVisible(borrarVisible);
        frame.setCliente(cliente);
        return frame;
    }

    /*El metodo crearFrameBuscarCliente crea solo una vez un BuscarCliente
    (esa class extends JDialog) pasando como parametro para parent la class
    VentanaPrincipal y true para que sea del tipo modal. Entonces llama el metodo
    setControl de la class BuscarCliente pasando como parametro this luego
    guarda la referencia en una variable global.
    Todas las otras veces que ese metodo es llamado simplesmente llama los metodos
    clear() y setVisible(true) de la class BuscarCliente;
     */
    @Override
    public void crearFrameBuscar() {
        if (buscarCliente == null) {
            buscarCliente = new BuscarCliente(null, true, personaUtilidades);
            buscarCliente.setControl(this);
        }
        buscarCliente.setLocation(60, 60);
        buscarCliente.clear();
        buscarCliente.setVisible(true);

    }

    /*El metodo buscar crea un FrameCliente cuando el resultado de la
    busqueda es un único cliente, o un ListaCliente si el resultado son varios clientes.
    El metodo recibe dos parametros del tipo String sendo:
    El primer nombreDelCampo(nombreDelCampo no se refiere a lo campos en la base de datos,
    si no, al JComboBox que contiene una lista de campos.) lo cual será utilizado
    el un switch. El segundo valorDelCampo, que será el valor utilizado para realizar la búsqueda.
     */
    @Override
    public void buscar(String nombreDelCampo, String valorDelCampo) {
        List<String> listaDeCampos = Arrays.asList(personaUtilidades.getCamposPersona());
        switch (listaDeCampos.indexOf(nombreDelCampo.toUpperCase())) {
            case 0:
                Cliente cli = servicio.buscarRegistroPorNIF(valorDelCampo);
                if (cli != null) {
                    principal.showInternalFrame(this.crearFrameCliente(ATUALIZANDO_CLIENTE, cli, true));
                } else {
                    JOptionPane.showMessageDialog(null, view.getString("message_NO_REGISTRY_FOUND_ID_THIS_ID") + valorDelCampo.toUpperCase());
                }
                break;
            case 1:
                crearFrameListaCliente(servicio.buscarRegistrosPorNombre(valorDelCampo),view.getString("message_SEARCHING_BY_NAME") +valorDelCampo.toUpperCase());
                break;
            case 2:
                crearFrameListaCliente(servicio.buscarRegistrosPorPrimerApellido(valorDelCampo),view.getString("message_SEARCHING_BY_FIRST_LASTNAME") +valorDelCampo.toUpperCase());
                break;
            case 3:
                crearFrameListaCliente(servicio.buscarRegistrosPorSegundoApellido(valorDelCampo),view.getString("message_SEARCHING_BY_SECOND_LASTNAME") +valorDelCampo.toUpperCase());
                break;
            case 4:
                crearFrameListaCliente(servicio.buscarRegistrosDadoDeBaja(),view.getString("message_SEARCHING_ALL_OFF"));
                break;
            case 5:
                crearFrameListaCliente(servicio.buscarRegistrosDadoDeAlta(),view.getString("message_SEARCHING_ALL_ON"));
                break;
            case 6:
                Month month = personaUtilidades.validarMes(valorDelCampo);
                if (month != null) {
                    crearFrameListaCliente(servicio.cumpleañerosDelMes(month),view.getString("message_SEARCHING_BIRTHDAY_OF_THE_MONTH") + valorDelCampo.toUpperCase());
                } else {
                    JOptionPane.showMessageDialog(null, MESES_DEL_AÑO);
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, BUSQUEDA_NO_IMPLEMENTADA);
                break;
        }
    }
}

