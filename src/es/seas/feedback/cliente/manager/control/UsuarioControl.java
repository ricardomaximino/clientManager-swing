/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.control;

import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.view.usuario.TableModelUsuario;
import es.seas.feedback.cliente.manager.model.Usuario;
import es.seas.feedback.cliente.manager.view.Controlable;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import es.seas.feedback.cliente.manager.model.service.ServicioPersona;
import es.seas.feedback.cliente.manager.view.ListaPersona;
import es.seas.feedback.cliente.manager.view.PersonaUtilidades;
import es.seas.feedback.cliente.manager.view.usuario.BuscarUsuario;
import es.seas.feedback.cliente.manager.view.usuario.FrameUsuario;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 *
 * @author Ricardo Maximino Gonçalves de Moraes
 * Esta Class es donde se deve ejecutar toda la regla de negocio referente al Usuario.
 * He criado una interface(UsuarioControl) simplesmente para desvincular la view
 * del control
 */
public class UsuarioControl implements PersonaControl<Usuario> {

    private final ResourceBundle view = ResourceBundle.getBundle("es.seas.feedback.cliente.manager.view.internationalization.view");
    private final String NUEVO_USUARIO = view.getString("message_REGISTRING_NEW_USER");
    private final String TABLA_DESACTUALIZADA = view.getString("message_USER_TABLE_IS_NOT_UPDATE");
    private final String BUSQUEDA_NO_IMPLEMENTADA = view.getString("message_THIS_KIND_OF_SEARCH_IS_NOT_IMPLEMENTED");
    private final String ATUALIZANDO_USUARIO = view.getString("message_UPDATING_USER");
    private String MESES_DEL_AÑO;
    
    private ServicioPersona<Usuario> servicio;
    private BuscarUsuario buscarUsuario;
    private Controlable principal;
    private Map<Usuario, JInternalFrame> frames;
    private Map<String,Provincia> provincias;
    private PersonaUtilidades personaUtilidades;

    /*Constructore sin argumentos*/
    public UsuarioControl() {
        frames = new HashMap<>();
    }

    /*Constructore con argumento argumento @Param VentanaPrincipal*/
    public UsuarioControl(ServicioPersona serv,Controlable princip ,Map<String,Provincia> prov) {
        this();
        this.servicio = serv;
        this.principal = princip;
        principal.setUsuarioControl(this);
        this.provincias = prov;
    }

    /*set VentanaPrincipal*/
    @Override
    public void setPrincipal(Controlable princip) {
        this.principal = princip;
        principal.setUsuarioControl(this);
    }

    /*get VentanaPrincipal*/
    @Override
    public Controlable getPrincipal() {
        return principal;
    }

    /*set ServicioUsuario*/
    @Override
    public void setServicio(ServicioPersona serv) {
        this.servicio = serv;
    }

    /*get servicioUsuario*/
    @Override
    public ServicioPersona getServicio() {
        return servicio;
    }

    @Override
    public Map<String, Provincia> getProvincias() {
        return provincias;
    }

    @Override
    public void setProvincias(Map<String, Provincia> prov) {
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

    //Metodos para crear, manipular y borrar Usuarios
    /*El Metodo guardar primeramente comprueva si hay algun usuario ya
    registrado con el nif del Usuario pasado como parametro.
    Si el resultado de esa busqueda sea null se llama el metodo del ServicioUsuario
    añadirNuevoRegistro.
    Se el resultado de esa busqueda sea true se llama el metodo del ServicioUsuario
    atualizarRegistro.*/
    @Override
    public boolean guardar(Usuario usuario) {
        if (servicio.buscarRegistroPorNIF(usuario.getNif()) != null) {
            servicio.atualizarRegistro(usuario);
            return true;
        } else {
            servicio.añadirNuevoRegistro(usuario);
            return true;
        }
    }

    /*El metodo crearFrameListaUsuario crea un ListaUsuario(esa class extends JIternalFrame)
    y pasa como argumento un TableModelUsuario (esa class extends AbstractTableModel),
    con la lista de usuarios pasada como argumento.
    Llama el metodo setControl de la class ListaUsuario y pasa una referencia this.
    Configura el ancho de las columnas de la tabla de la class ListaUsuario llamando el metodo
    statico de la class PersonaUtilidades configurarAnchoDeLasColumnas,
    y finalmente llama el metodo showInternalFrame de la class VentanaPrincipal(esa class extends JFrame y implementa Controlable)
    y pasa la referencia de la class  ListaUsuario creada.*/
    private void crearFrameListaUsuario(List<Usuario> list,String titulo) {
        ListaPersona lista = new ListaPersona(new TableModelUsuario(list,personaUtilidades.getTitulosTablasDePersonas()));
        lista.setTitle(titulo + " - " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
        lista.getTabla().setColumnModel(personaUtilidades.configurarAnchoDeLasColumnas(lista.getTabla().getColumnModel()));
        lista.setControl(this);
        principal.showInternalFrame(lista);
    }

    /*El metodo listarTodos crea un ListaUsuario(esa class extends JInternalFrame)
    usando el metodo privado crearFrameListaUsuario y pasa como parametro una
    lista con todos los usuarios llamando el metodo listarRegistros de la class ServicioUsuario*/
    @Override
    public void listarTodos() {
        crearFrameListaUsuario(servicio.listarRegistros(),view.getString("message_LIST_OF_ALL_USERS") );
    }

    /*El metodo crearFrameAñadir llama el metodo showInternalFrame de la class 
    VentanaPrincipal(esa class extends JFrame y implementa la interface Controlable)y pasa como argumento una referencia
    de la class FrameUsuario(esa class extends JInternalFrame) creada usando 
    el metodo private crearFrameUsuario que lleva como argumento un TITULO del
    frame del tipo String, un USUARIO del tipo Usuario
    y un boolean(indicando si desea que el botón que permite borrar usuario sea visible o no*/
    @Override
    public void crearFrameAñadir() {
        principal.showInternalFrame(crearFrameUsuario(NUEVO_USUARIO, null, false,true));
    }

    /*El metodo creaFrameAtualizar crea una variable del tipo Usuario,
    iguala esa variable con el retorno del metodo buscarUsuario de la class
    ServicoUsuario(esa class implements ServicioPersona).
    Luego utiliza un if para comprobar si la variable usuario es !=  the null.
    Si la variable sea != de null, entonces se crea una variable del Tipo JInternalFrame
    y iguala esa variable al retorno del Map.get que recibe como key la referencia
    de un usuario. Si esta key exista en el Map signfica que el usuario indicado
    ya tiene un JInternalFrame abierto con sus datos,entoces no se necesita crear
    otro, sino, traer el JIternalFrame con eses datos al frente utilizando el
    metodo activateFrame del DesktopManager del DesktopPane de la VentanaPrincipal. Si
    esta key no exista en el Map entoces se creará un FrameUsuario(esa class extends JInternalFrame)
    usando el metodo private crearFrameUsuario se añadirá el usuario y el frame
    en el Map(hasta que el frame fire el evento closing, lo cual llamara el metodo
    closeFrame que utilizará el metodo remove de la class Map para remover la 
    referencia del frame usando como key el keyUsuario,o sea, el usuario usado
    para crear el frame) exibirá el frame creado usando el metodo showInternalFrame de la
    class VentanaPrincipal(esa class extends JFrame).
    Si la variable set == null, entoces se exibirá un JOptionPane informando que
    la tabla esta desactualizada.
     */
    @Override
    public void crearFrameAtualizar(String nif) {
        Usuario usuario = servicio.buscarRegistroPorNIF(nif);
        if (usuario != null) {
            JInternalFrame frame = frames.get(usuario);
            if (frame != null) {
                principal.getDesktopPane().getDesktopManager().activateFrame(frame);
                principal.getDesktopPane().setSelectedFrame(frame);
            } else {
                StringBuilder titulo = new StringBuilder(ATUALIZANDO_USUARIO);
                titulo.append(usuario.getNombre());
                titulo.append(" ");
                titulo.append(usuario.getPrimerApellido());
                titulo.append(" ");
                titulo.append(usuario.getSegundoApellido());
                frame = crearFrameUsuario(titulo.toString(), usuario, true,false);
                principal.showInternalFrame(frame);
                frames.put(usuario, frame);
            }
        } else {
            JOptionPane.showMessageDialog(null, TABLA_DESACTUALIZADA);
        }
    }

    /*El metodo closeFrame comprueba si el frame pasado por parametro es una intancia
    de la class FrameUsuario(esa class extends JInternalFrame) y en el caso que
    sea true se remove del Map usando como key el keyUsuario, que es el mismo usuario
    usado para crear el frame.*/
    @Override
    public void closeFrame(JInternalFrame frame) {
        if (frame instanceof FrameUsuario) {
            frames.remove(((FrameUsuario) frame).getKeyUsuario());
        }
    }

    /*El Metodo crearFrameUsuario es un metodo private y retorna un JInternalFrame
    pide como parametro el titulo del frame del tipo String,el usuario del tipo
    Usuario(lo cual puede ser null en caso de nuevo usuario) y un valor boolean
    que hará con que el botón para borrar usuario sea visible o no.*/
    private JInternalFrame crearFrameUsuario(String titulo, Usuario usuario, boolean borrarVisible,boolean datosDeAcceso) {
        FrameUsuario frame = new FrameUsuario(provincias,personaUtilidades);
        frame.setTitle(titulo);
        frame.setControl(this);
        frame.borrarEsVisible(borrarVisible);
        frame.setUsuario(usuario);
        frame.getTxtRepiteContraseña().setEnabled(datosDeAcceso);
        frame.getSprPoderDeAcceso().setEnabled(datosDeAcceso);
        return frame;
    }

    /*El metodo crearFrameBuscarUsuario crea solo una vez un BuscarUsuario
    (esa class extends JDialog) pasando como parametro para parent la class
    VentanaPrincipal y true para que sea del tipo modal. Entonces llama el metodo
    setControl de la class BuscarUsuario pasando como parametro this luego
    guarda la referencia en una variable global.
    Todas las otras veces que ese metodo es llamado simplesmente llama los metodos
    clear() y setVisible(true) de la class BuscarUsuario;
     */
    @Override
    public void crearFrameBuscar() {
        if (buscarUsuario == null) {
            buscarUsuario = new BuscarUsuario(null, true,personaUtilidades);
            buscarUsuario.setControl(this);
        }
        buscarUsuario.setLocation(60, 60);
        buscarUsuario.clear();
        buscarUsuario.setVisible(true);

    }

    /*El metodo buscar crea un FrameUsuario cuando el resultado de la
    busqueda es un único usuario, o un ListaUsuario si el resultado son varios usuarios.
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
                Usuario usu = servicio.buscarRegistroPorNIF(valorDelCampo);
                if (usu != null) {
                    principal.showInternalFrame(this.crearFrameUsuario(ATUALIZANDO_USUARIO + usu.getNombre()+ " "+ usu.getPrimerApellido(), usu, true,false));
                } else {
                    JOptionPane.showMessageDialog(null,view.getString("message_NO_REGISTRY_FOUND_ID_THIS_ID") + valorDelCampo.toUpperCase());
                }
                break;
            case 1:
                crearFrameListaUsuario(servicio.buscarRegistrosPorNombre(valorDelCampo),view.getString("message_SEARCHING_BY_NAME") +valorDelCampo.toUpperCase());
                break;
            case 2:
                crearFrameListaUsuario(servicio.buscarRegistrosPorPrimerApellido(valorDelCampo),view.getString("message_SEARCHING_BY_FIRST_LASTNAME") +valorDelCampo.toUpperCase());
                break;
            case 3:
                crearFrameListaUsuario(servicio.buscarRegistrosPorSegundoApellido(valorDelCampo),view.getString("message_SEARCHING_BY_SECOND_LASTNAME") +valorDelCampo.toUpperCase());
                break;
            case 4:
                crearFrameListaUsuario(servicio.buscarRegistrosDadoDeBaja(),view.getString("message_SEARCHING_ALL_OFF"));
                break;
            case 5:
                crearFrameListaUsuario(servicio.buscarRegistrosDadoDeAlta(),view.getString("message_SEARCHING_ALL_ON"));
                break;
            case 6:
                Month month = personaUtilidades.validarMes(valorDelCampo);
                if (month != null) {
                    crearFrameListaUsuario(servicio.cumpleañerosDelMes(month),view.getString("message_SEARCHING_BIRTHDAY_OF_THE_MONTH") + valorDelCampo.toUpperCase());
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

