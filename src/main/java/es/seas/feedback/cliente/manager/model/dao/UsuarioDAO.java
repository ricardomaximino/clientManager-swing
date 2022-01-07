package es.seas.feedback.cliente.manager.model.dao;

import es.seas.feedback.cliente.manager.model.Usuario;
import es.seas.feedback.cliente.manager.model.dao.hibernate.HibernateUtil;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta class implementa la interfaz
 * PersonaDAO&lt;es.seas.feedback.cliente.manager.model.Usuario&gt; utilizando
 * Hibernate 4.x.
 *
 * @author Ricardo Maximino
 * <br><br>
 * <p>
 * La idea general que implementa los metodos en esta clase es lo siguiente:</p>
 * Session session = HibernateUtil.getSessionFactory().openSession();<br>
 * Transaction tx = session.beginTransaction();<br>
 * try {<br>
 * session.acción_deseada();<br>
 * tx.commit();<br>
 * } catch (Exception ex) {<br>
 * tx.rollback();<br>
 * } finally {<br>
 * session.close();<br>
 * }<br>
 */
public class UsuarioDAO implements PersonaDAO<Usuario> {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioDAO.class);

    public UsuarioDAO() {
        LOG.info("UsuarioDAO implements PersonaDAO was instantiated " + LocalDate.now().toString());

    }

     /**
     * Este metodo intenta añadir un nuevo registro en la tabla usuarios.
     * @param usuario del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return del tipo boolean para certificar se la acción ha sido realizada 
     * con éxito o no.
     */
    @Override
    public boolean anadirNuevoRegistro(Usuario usuario) {
        boolean resultado = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(usuario);
            tx.commit();
            resultado = true;
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method añadirNuevoRegistro", ex);
            tx.rollback();
            resultado = false;
        } finally {
            session.close();
        }
        return resultado;
    }

    /**
     * Este metodo intenta atualizar un registro en la tabla usuarios.
     * @param usuarioParametro del tipo 
     * es.seas.feedback.cliente.manager.model.Usuario. 
     * @return del tipo boolean para certificar se la acción ha sido realizada
     * con éxito o no.
     */
    @Override
    public boolean atualizarRegistro(Usuario usuarioParametro) {
        boolean resultado = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Usuario usuario = (Usuario) session.createCriteria(Usuario.class).add(Restrictions.eq("nif", usuarioParametro.getNif())).uniqueResult();
            setChanges(usuario, usuarioParametro);
            session.update(usuario);
            tx.commit();
            resultado = true;
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method atualizarRegistro", ex);
            tx.rollback();
            resultado = false;
        } finally {
            session.close();
        }
        return resultado;
    }

    /**
     * Este metodo intenta dar de baja a un registro en la tabla usuarios.
     * @param usu del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return boolean para certificar se la acción ha sido realizada con
     * éxito o no.
     */
    @Override
    public boolean darDeBaja(Usuario usu) {
        return darDeBaja(usu.getNif());
    }

    /**
     * Este metodo intenta dar de baja a un registro en la tabla usuarios.
     * @param nif del tipo String, identificador único en la tabla.
     * @return boolean para certificar se la acción ha sido realizada con 
     * éxito o no.
     */
    @Override
    public boolean darDeBaja(String nif) {
        boolean resultado = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Usuario usu = (Usuario) session.get(Usuario.class, nif);
            if (usu != null) {
                usu.setActivo(false);
                usu.setFechaUltimaBaja(LocalDate.now());
                session.update(usu);
                tx.commit();
                resultado = true;
            }
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method darDeBaja", ex);
            tx.rollback();
            resultado = false;
        } finally {
            session.close();
        }
        return resultado;
    }

    /**
     * Este metodo intenta borrar un registro el la tabla usuarios.
     * @param usuario del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * @return boolean para certificar se la acción ha sido realizada con
     * éxito o no.
     */
    @Override
    public boolean borrarRegistro(Usuario usuario) {
        return borrarRegistro(usuario.getNif());
    }

    /**
     * Este metodo intenta borrar un registro el la tabla usuarios.
     * @param nif del tipo String, identificador único en la tabla.
     * @return boolean para certificar se la acción ha sido realizada con
     * éxito o no.
     */
    @Override
    public boolean borrarRegistro(String nif) {
        boolean resultado = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nif", nif));
            Usuario usu = (Usuario) criteria.uniqueResult();
            if (usu != null) {
                session.delete(usu);
                tx.commit();
                resultado = true;
            }
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method borrarRegistro", ex);
            tx.rollback();
            LOG.error(ex.getLocalizedMessage());
            resultado = false;
        } finally {
            session.close();
        }
        return resultado;
    }

    /**
     * Este metodo lista todo los registro de la tabla usuarios.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Usuario> listarRegistros() {
        List<Usuario> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method listarRegistros", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo busca un usuario en la tabla usuarios utilizando como filtro
     * el identificador ùnico pasado como parametro.
     * @param nif del tipo String, identificador único en la tabla usuarios.
     * @return del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * <p>En caso de errores, o de que no se encuentre nigún registro el metodo
     * retornará null.</p>
     */
    @Override
    public Usuario buscarRegistroPorNIF(String nif) {
        Usuario usu = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nif", nif));
            usu = (Usuario) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method buscarRegistroPorNIF", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return usu;
    }

    /**
     * Este metodo busca un usuario en la tabla usuarios utilizando como filtro
     * la llave primaria pasada como parametro.
     * @param id del tipo Long, llave primaria en la tabla usuarios.
     * @return del tipo es.seas.feedback.cliente.manager.model.Usuario.
     * <p>En caso de errores, o de que no se encuentre nigún registro el metodo
     * retornará null.</p>
     */
    @Override
    public Usuario buscarRegistroPorID(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Usuario usuario = null;
        try {
            usuario = (Usuario) session.get(Usuario.class, Long.class);
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method buscarRegistroPorID", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return usuario;
    }

    /**
     * Este metodo lista todo los registro de la tabla usuarios filtrados por
     * el parametro introducido.
     * @param segundoApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosPorSegundoApellido(String segundoApellido) {
        List<Usuario> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("segundoApellido", segundoApellido));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method buscarRegistrosPorSegundoApellido", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo lista todo los registro de la tabla usuarios filtrados por
     * el parametro introducido.
     * @param primerApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosPorPrimerApellido(String primerApellido) {
        List<Usuario> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("primerApellido", primerApellido));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method buscarRegistrosPorPrimerApellido", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo lista todo los registro de la tabla usuarios filtrados por
     * el parametro introducido.
     * @param nombre del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosPorNombre(String nombre) {
        List<Usuario> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nombre", nombre));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method buscarRegistrosPorNombre", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo lista todo los registro de la tabla usuarios filtrados por
     * el parametro activo = false.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosDadoDeBaja() {
        List<Usuario> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("activo", false));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method buscarRegistrosDadoDeBaja", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo lista todo los registro de la tabla usuarios filtrados por
     * el parametro activo = true.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Usuario> buscarRegistrosDadoDeAlta() {
        List<Usuario> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("activo", true));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method buscarRegistrosDadoDeAlta", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo llama el metodo listarRegistros y crea una lista apartir
     * de la lista generada por el metodo con todos los usuarios que el mes de la
     * fecha de nacimiento sea igual al mes introducido como parametro.
     * @param mes del tipo java.time.Month.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Usuario&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Usuario> cumpleanerosDelMes(Month mes) {
        List<Usuario> list = new ArrayList<>();
        for(Usuario usu : listarRegistros()){
            if(usu.getFechaNacimiento().getMonth().equals(mes)){
                list.add(usu);
            }
        }
        return list;
    }

    private void setChanges(Usuario usuario1, Usuario usuario2) {
        usuario1.setActivo(usuario2.isActivo());
        usuario1.setContactos(usuario2.getContactos());
        usuario1.setContraseña(usuario2.getContraseña());
        usuario1.setDirecion(usuario2.getDirecion());
        usuario1.setFechaNacimiento(usuario2.getFechaNacimiento());
        usuario1.setFechaPrimeraAlta(usuario2.getFechaPrimeraAlta());
        usuario1.setFechaUltimaBaja(usuario2.getFechaUltimaBaja());
        usuario1.setLevel(usuario2.getLevel());
        usuario1.setNif(usuario2.getNif());
        usuario1.setNombre(usuario2.getNombre());
        usuario1.setPrimerApellido(usuario2.getPrimerApellido());
        usuario1.setSegundoApellido(usuario2.getSegundoApellido());
    }

}
