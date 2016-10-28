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
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Esta clase es el controlador de todas las cosas referentes al usuario.
 *
 * @author Ricardo Maximino<br>
 *
 * <p>
 * Esta Class es donde se deve ejecutar toda la regla de negocio referente al
 * Usuario.</p>
 * <p>
 * Hay una interface(PersonaControl) criada para desvincular la view del
 * control.</p>
 */
public class UsuarioControl implements PersonaControl<Usuario> {

    private ResourceBundle view = ResourceBundle.getBundle("es.seas.feedback.cliente.manager.view.internationalization.view");
    private ServicioPersona<Usuario> servicio;
    private Controlable principal;
    private Map<Usuario, JInternalFrame> frames;
    private Map<String, Provincia> provincias;
    private PersonaUtilidades personaUtilidades;

     /**
     * Este es el contructor sin argumentos lo cual solamente inicia la variable
     * frames con un new HashMap&lt;&gt;().
     */
    public UsuarioControl() {
        frames = new HashMap<>();
    }

    /**
     * Este es el constructor con argumentos<br>
     *
     * @param serv - Se espera la instancia de una clase que implemente
     * ServicioPersona donde están todas las prestaciones con el banco de datos.
     * @param princip - Se espera la instancia de una clase que implemente
     * Controlable y además extenda JFrame.
     * @param prov - Se espera un Map&lt;stString,Provincia&gt;.
     *
     */
    public UsuarioControl(ServicioPersona serv, Controlable princip, Map<String, Provincia> prov) {
        this();
        this.servicio = serv;
        this.principal = princip;
        principal.setUsuarioControl(this);
        this.provincias = prov;
    }

    /**
     * Este metodo es imprecindible para cambiar el idioma pues configura la
     * variable global view que es un ResourceBundle.
     */
    @Override
    public void refresh() {
        view = ResourceBundle.getBundle("es.seas.feedback.cliente.manager.view.internationalization.view");
    }

    /**
     * Este metodo configura la variable global principal con una instancia de
     * alguna classe que implement Controlable.<br>
     *
     * @param princip - Se espera la instancia de una clase que implementa
     * Controlable y extenda un JFrame.
     */
    @Override
    public void setPrincipal(Controlable princip) {
        this.principal = princip;
        principal.setUsuarioControl(this);
    }

    /**
     * Este metodo retorna el contenido de la variable global principal.<br>
     *
     * @return - La instacia de una clase que implementa Controlable, pero se
     * espera que ademas de implementear Controlable, que se extenda JFrame.
     */
    @Override
    public Controlable getPrincipal() {
        return principal;
    }

    /**
     * Este metodo configura la variable global servicio con una instancia de
     * una classe que implemente ServicioPersona.<br>
     *
     * @param serv - Se espera la instancia de una clase que implementa
     * ServicioPersona.
     */
    @Override
    public void setServicio(ServicioPersona serv) {
        this.servicio = serv;
    }

    /**
     * Este metodo retorna el contenido de la variable global servicio.<br>
     *
     * @return - Una instancia de una clase que implemente ServicioPersona.
     */
    @Override
    public ServicioPersona getServicio() {
        return servicio;
    }
    
    /**
     * Este metodo retorna el contenido de la variable global provincias que es
     * un Map&lt;String,Provincia&gt;.<br>
     *
     * @return - Un Map&lt;String,Provincia&gt;.
     */
    @Override
    public Map<String, Provincia> getProvincias() {
        return provincias;
    }

    /**
     * Este metodo configura la variable global provincias que es un
     * Map&lt;String,Provincia&gt;.<br>
     *
     * @param prov - Un Map&lt;String,Provincia&gt;.
     */
    @Override
    public void setProvincias(Map<String, Provincia> prov) {
        this.provincias = prov;
    }

    /**
     * Este metodo configura la variable global personaUtilidades que es una
     * PersonaUtilidades<br>
     *
     * @param personaUtilidades - Una instancia de la clase PersonaUtilidades.
     */
    @Override
    public void setPersonaUtilidades(PersonaUtilidades personaUtilidades) {
        this.personaUtilidades = personaUtilidades;
    }

    /**
     * Este Metodo retorna el contenido de la variable global
     * personaUtilidades.<br>
     *
     * @return Una istancia de la clase PersonaUtilidades
     */
    @Override
    public PersonaUtilidades getPersonaUtilidades() {
        return personaUtilidades;
    }

    /**
     * El Metodo guardar guarda as alteraciones y crea nuevos
     * registros.<br><br>
     *
     * @param usuario - Se espera la instancia de una clase Usuario.<br>
     * @return - Un valor del tipo boolean como confirmación que la acción haya
     * sido ejecutada correctamente.<br><br>
     *
     * <p>
     * Primeramente este metodo chequea si hay algun usuario ya registrado con
     * el nif del usuario pasado como parametro.</p>
     * <p>
     * Si el resultado de esa busqueda sea null se llama el metodo del
     * ServicioUsuario añadirNuevoRegistro.</p>
     * <p>
     * Se el resultado de esa búsqueda sea true se llama el metodo del
     * ServicioUsuario atualizarRegistro.</p>
     */
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

    /**
     * El metodo listarTodos crea un ListaUsuario(esa class extends
     * JInternalFrame)<br>
     * usando el metodo privado crearFrameListaUsuario y pasa como parametro
     * una<br>
     * lista con todos los usuario llamando el metodo listarRegistros de la
     * class ServicioUsuario.
     */
    @Override
    public void listarTodos() {
        crearFrameListaUsuario(servicio.listarRegistros(), view.getString("message_LIST_OF_ALL_USERS"));
    }

    /**
     * El metodo crearFrameAñadir llama el metodo showInternalFrame de la
     * class <br>
     * VentanaPrincipal(esa clase implemtenta Controlable y deve extender
     * JFrame)y pasa <br>
     * como argumento una referencia de la class FrameUsuario(esa clase extende
     * JInternalFrame)<br>
     * creada usando el metodo private crearFrameUsuario que lleva como
     * argumento un TITULO del<br>
     * frame del tipo String, un USUARIO del tipo Usuario y un boolean<br>
     * (indicando si desea que el botón que permite borrar usuario sea visible o
     * no.
     */
    @Override
    public void crearFrameAñadir() {
        principal.showInternalFrame(crearFrameUsuario(view.getString("message_REGISTRING_NEW_USER"), null, false, true));
    }

    /**
     * Este metodo crea y apresenta un FrameUsuario para atualizar los datos del
     * usuario que conrresponda con el nif del parametro.<br>
     *
     * @param nif - String utilizada para hacer busquedas percisar de un solo
     * resultado.<br>
     * <p>
     * No es PrimaryKey pero si ella una constrain para que no se pueda repetir
     * en la tabla de la base de datos.</p>
     * <p>
     * El metodo creaFrameAtualizar crea una variable del tipo Usuario,
     * iguala esa variable con el retorno del metodo buscar de la class
     * ServicoUsuario(esa clase implementa ServicioPersona).</p>
     * <p>
     * Luego utiliza un if para comprobar si la variable usuario es != the null.
     * Si la variable sea != de null, entonces se crea una variable del Tipo
     * JInternalFrame y iguala esa variable al retorno del Map.get que recibe
     * como key la referencia de un usuario.</p>
     * <p>
     * Si esta key exista en el Map signfica que el usuario indicado ya tiene un
     * JInternalFrame abierto con sus datos,entoces no se necesita crear otro,
     * sino, traer el JIternalFrame con eses datos al frente, utilizando el
     * metodo activateFrame del DesktopManager del DesktopPane de la
     * VentanaPrincipal.</p>
     * <p>
     * Si esta key no exista en el Map entoces se creará un FrameUsuario(esa
     * clase extende JInternalFrame) usando el metodo private crearFrameUsuario
     * se añadirá el usuario y el frame en el Map(hasta que el frame lance el
     * evento dispose, lo cual llamara el metodo closeFrame que utilizará el
     * metodo remove de la class Map para remover la referencia del frame usando
     * como key el keyUsuario,o sea, el usuario usado para crear el frame)
     * exibirá el frame creado usando el metodo showInternalFrame de la class
     * VentanaPrincipal(esa class extends JFrame).</p>
     * <p>
     * Si la variable usuario == null, entoces se exibirá un JOptionPane informando
     * que la tabla esta desactualizada.</p>
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
                StringBuilder titulo = new StringBuilder(view.getString("message_UPDATING_USER"));
                titulo.append(usuario.getNombre());
                titulo.append(" ");
                titulo.append(usuario.getPrimerApellido());
                titulo.append(" ");
                titulo.append(usuario.getSegundoApellido());
                frame = crearFrameUsuario(titulo.toString(), usuario, true, false);
                principal.showInternalFrame(frame);
                frames.put(usuario, frame);
            }
        } else {
            JOptionPane.showMessageDialog(null, view.getString("message_USER_TABLE_IS_NOT_UPDATE"));
        }
    }

    /**
     * Este metodo elimina, en caso de que exista una referencia del
     * JInternalFrame pasado como parametro.<br>
     *
     * @param frame - El para metro es del tipo JInternalFrame.<br><br>
     *
     * <p>
     * El metodo closeFrame comprueba si el frame pasado por parametro es una
     * intancia de la class FrameCliente(esa clase extende JInternalFrame) y en
     * el caso que sea true se remove del Map usando como key el keyUsuario, que
     * es el mismo usuario usado para crear el frame.</p>
     */
    @Override
    public void closeFrame(JInternalFrame frame) {
        if (frame instanceof FrameUsuario) {
            frames.remove(((FrameUsuario) frame).getKeyUsuario());
        }
    }

    /**
     * El metodo crearFrameBuscar crea un BuscarUsuario (esa clase
     * extende JDialog).<br><br>
     *
     *
     * <p>
     * El metodo crearFrameBuscar crea un BuscarUsuario (esa clase
     * extende JDialog) pasando como parametro la class VentanaPrincipal y true
     * para que sea del tipo modal.</p>
     * <p>
     * Entonces llama el metodo setControl de la class BuscarUsuario pasando
     * como parametro this.</p>
     */
    @Override
    public void crearFrameBuscar() {
        BuscarUsuario buscarUsuario = new BuscarUsuario(null, true, personaUtilidades);
        buscarUsuario.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../view/icon.png")));
        buscarUsuario.setControl(this);
        buscarUsuario.setLocation(60, 60);
        buscarUsuario.clear();
        buscarUsuario.setVisible(true);
    }

    /**
     * El metodo buscar crea un FrameUsuario cuando se busca por el nif y un
     * ListaPersona para todos los demas tipo de búsqueda.<br><br>
     *
     * @param nombreDelCampo nombreDelCampo(nombreDelCampo no se refiere a lo
     * campos en la base de datos, si no, al JComboBox que<br>
     * contiene una lista de campos. Los cuales se producen en
     * PersonaUtilidades) lo cual será utilizado el un switch.<br>
     * @param valorDelCampo valorDelCampo, que será el valor utilizado para
     * realizar la búsqueda.<br><br>
     *
     * <p>
     * Este metodo funciona el un switch lo cual podria utiliza una String pero
     * para aumentar la compatibilidade con otras versiones del java utiliza un
     * int</p>
     * <p>
     * Se crea una lista con los campos y se utiliza su index en el switch.</p>
     *
     */
    @Override
    public void buscar(String nombreDelCampo, String valorDelCampo) {
        List<String> listaDeCampos = Arrays.asList(personaUtilidades.getCamposPersona());
        switch (listaDeCampos.indexOf(nombreDelCampo.toUpperCase())) {
            case 0:
                Usuario usu = servicio.buscarRegistroPorNIF(valorDelCampo);
                if (usu != null) {
                    principal.showInternalFrame(this.crearFrameUsuario(view.getString("message_UPDATING_USER") + usu.getNombre() + " " + usu.getPrimerApellido(), usu, true, false));
                } else {
                    JOptionPane.showMessageDialog(null, view.getString("message_NO_REGISTRY_FOUND_ID_THIS_ID") + valorDelCampo.toUpperCase());
                }
                break;
            case 1:
                crearFrameListaUsuario(servicio.buscarRegistrosPorNombre(valorDelCampo), view.getString("message_SEARCHING_BY_NAME") + valorDelCampo.toUpperCase());
                break;
            case 2:
                crearFrameListaUsuario(servicio.buscarRegistrosPorPrimerApellido(valorDelCampo), view.getString("message_SEARCHING_BY_FIRST_LASTNAME") + valorDelCampo.toUpperCase());
                break;
            case 3:
                crearFrameListaUsuario(servicio.buscarRegistrosPorSegundoApellido(valorDelCampo), view.getString("message_SEARCHING_BY_SECOND_LASTNAME") + valorDelCampo.toUpperCase());
                break;
            case 4:
                crearFrameListaUsuario(servicio.buscarRegistrosDadoDeBaja(), view.getString("message_SEARCHING_ALL_OFF"));
                break;
            case 5:
                crearFrameListaUsuario(servicio.buscarRegistrosDadoDeAlta(), view.getString("message_SEARCHING_ALL_ON"));
                break;
            case 6:
                Month month = personaUtilidades.validarMes(valorDelCampo);
                if (month != null) {
                    crearFrameListaUsuario(servicio.cumpleañerosDelMes(month), view.getString("message_SEARCHING_BIRTHDAY_OF_THE_MONTH") + valorDelCampo.toUpperCase());
                } else {
                    JOptionPane.showMessageDialog(null, personaUtilidades.getMesesDelAñoAsString());
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, view.getString("message_THIS_KIND_OF_SEARCH_IS_NOT_IMPLEMENTED"));
                break;
        }
    }
   
    /**
     * El metodo crearFrameListarUsuario crea un ListaPersona(esa class extends
     * JIternalFrame).<br><br>
     *
     * @param list - Lista de Usuario del tipo List&lt;Usuario&gt; con los
     * usuarios que deven aparecer en la tabla.<br>
     * @param titulo - Titulo del tipo String que será el titulo del frame
     * creado en conjunto con la fecha y hora local y el número de registros
     * listado.<br><br>
     *
     * Este metodo crea una instancia de la clase ListaPersona usando el
     * contructor con un argumento:<br><br>
     * <ul>
     * <li>
     * TableModelUsuario - a su vez, para instanciar esta clase se utiliza el
     * constructor con dos argumentos la lista de usuario y String[]
     * titulosDeLaTabla.
     * </li>
     * </ul>
     *
     * Luego se configura el ColumnModel de la tabla utilizando el metodo
     * configurarAnchoDeLasColumnas pasando como parametro el proprio
     * columnModel de la tabla.<br>
     * se configura el control de la clase ListaPersona y finalmente se llama el
     * metodo showInternalFrame<br>
     * de la class VentanaPrincipal(esa class implementa Controlable y deve
     * extender JFrame)<br>
     * y pasa la referencia de la clase ListaPersona que ha sido creada.
     */
    private void crearFrameListaUsuario(List<Usuario> list, String titulo) {
        ListaPersona lista = new ListaPersona(new TableModelUsuario(list, personaUtilidades.getTitulosTablasDePersonas()));
        lista.setTitle(titulo + " - " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)+" - "+ list.size());
        lista.getTabla().setColumnModel(personaUtilidades.configurarAnchoDeLasColumnas(lista.getTabla().getColumnModel()));
        lista.setControl(this);
        principal.showInternalFrame(lista);
    }
    
    /**
     * El Metodo crearFrameUsuario es un metodo private y retorna un
     * JInternalFrame.<br><br>
     *
     * @param titulo titulo del frame que es del tipo String.<br>
     * @param usuario usuario del tipo Usuario, al cual se desea atualizar o
     * null en caso de nuevo usuario.<br>
     * @param borrarVisible  un valor del tipo boolean para determinar si el
     * botón borrar estará visible o no.<br>
     * 
     * @param datosDeAcceso un valor del tipo boolean para configurar la 
     * propriedade enabled de los componentes txtRepiteContraseña (JPasswordField)
     * y sprPoderDeAcceso (JSpinner).<br>
     *
     * <p>
     * Este metodo pide como parametro el titulo del frame, del tipo String,el
     * usuario del tipo Usuario(lo cual puede ser null en caso de nuevo cliente),
     * un valor boolean que hará con que el botón para borrar usuario sea
     * visible o no y otro valor boolean para configurar la propriedade enabled
     * de los componentes txtRepitaContraseña(JPasswordField) y sprPoderDeAcceso
     * (JSpinner)</p>
     */
    private JInternalFrame crearFrameUsuario(String titulo, Usuario usuario, boolean borrarVisible, boolean datosDeAcceso) {
        FrameUsuario frame = new FrameUsuario(provincias, personaUtilidades);
        frame.setTitle(titulo);
        frame.setControl(this);
        frame.borrarEsVisible(borrarVisible);
        frame.setUsuario(usuario);
        frame.getTxtRepiteContraseña().setEnabled(datosDeAcceso);
        frame.getSprPoderDeAcceso().setEnabled(datosDeAcceso);
        return frame;
    }
}
