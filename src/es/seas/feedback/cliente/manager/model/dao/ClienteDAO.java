package es.seas.feedback.cliente.manager.model.dao;

import es.seas.feedback.cliente.manager.model.Cliente;
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
 * PersonaDAO&lt;es.seas.feedback.cliente.manager.model.Cliente&gt; utilizando
 * Hibernate 4.x.
 *
 * @author Ricardo Maximino
 * <br><br>
 * <p>La idea general que implementa los metodos en esta clase es lo siguiente:</p>
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
public class ClienteDAO implements PersonaDAO<Cliente> {

    private static final Logger LOG = LoggerFactory.getLogger(ClienteDAO.class);

    public ClienteDAO() {
        LOG.info("ClienteDAO implements PersonaDAO was instantiated " + LocalDate.now().toString());
    }

    /**
     * Este metodo intenta añadir un nuevo registro en la tabla clientes.
     * @param cliente del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return del tipo boolean para certificar se la acción ha sido realizada 
     * con éxito o no.
     */
    @Override
    public boolean añadirNuevoRegistro(Cliente cliente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(cliente);
            tx.commit();
            return true;
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method añadirNuevoRegistro", ex);
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * Este metodo intenta atualizar un registro en la tabla clientes.
     * @param clienteParametro del tipo 
     * es.seas.feedback.cliente.manager.model.Cliente. 
     * @return del tipo boolean para certificar se la acción ha sido realizada
     * con éxito o no.
     */
    @Override
    public boolean atualizarRegistro(Cliente clienteParametro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Cliente cliente = (Cliente) session.createCriteria(Cliente.class).add(Restrictions.eq("nif", clienteParametro.getNif())).uniqueResult();
            setChanges(cliente, clienteParametro);
            session.update(cliente);
            tx.commit();
            return true;
        } catch (Exception ex) {
            LOG.error("HibernateException in the method atualizarRegistro", ex);
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    /**
     * Este metodo intenta dar de baja a un registro en la tabla clientes.
     * @param cliente del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return boolean para certificar se la acción ha sido realizada con
     * éxito o no.
     */
    @Override
    public boolean darDeBaja(Cliente cliente) {
        return darDeBaja(cliente.getNif());
    }

    /**
     * Este metodo intenta dar de baja a un registro en la tabla clientes.
     * @param nif del tipo String, identificador único en la tabla.
     * @return boolean para certificar se la acción ha sido realizada con 
     * éxito o no.
     */
    @Override
    public boolean darDeBaja(String nif) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        boolean resultado = false;
        try {
            Cliente cli = (Cliente) session.get(Cliente.class, nif);
            if (cli != null) {
                cli.setActivo(false);
                cli.setFechaUltimaBaja(LocalDate.now());
                session.update(cli);
                tx.commit();
                resultado = true;
            }
        } catch (Exception ex) {
            LOG.error("HibernateException in the method darDeBaja", ex);
            tx.rollback();
            resultado = false;
        } finally {
            session.close();
        }
        return resultado;
    }

    /**
     * Este metodo intenta borrar un registro el la tabla clientes.
     * @param cliente del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * @return boolean para certificar se la acción ha sido realizada con
     * éxito o no.
     */
    @Override
    public boolean borrarRegistro(Cliente cliente) {
        return borrarRegistro(cliente.getNif());
    }

    /**
     * Este metodo intenta borrar un registro el la tabla clientes.
     * @param nif del tipo String, identificador único en la tabla.
     * @return boolean para certificar se la acción ha sido realizada con
     * éxito o no.
     */
    @Override
    public boolean borrarRegistro(String nif) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        boolean resultado = false;
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("nif", nif));
            Cliente cli = (Cliente) criteria.uniqueResult();
            if (cli != null) {
                session.delete(cli);
                tx.commit();
                resultado = true;
            }
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method borrarRegistro", ex);
            tx.rollback();
            resultado = false;
        } finally {
            session.close();
        }
        return resultado;
    }

    /**
     * Este metodo lista todo los registro de la tabla clientes.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Cliente> listarRegistros() {
        List<Cliente> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (Exception ex) {
            LOG.error("HibernateException in the method listarRegistros", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo busca un cliente en la tabla clientes utilizando como filtro
     * el identificador ùnico pasado como parametro.
     * @param nif del tipo String, identificador único en la tabla clientes.
     * @return del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * <p>En caso de errores, o de que no se encuentre nigún registro el metodo
     * retornará null.</p>
     */
    @Override
    public Cliente buscarRegistroPorNIF(String nif) {
        Cliente cli = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("nif", nif));
            cli = (Cliente) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method buscarRegistroPorNIF", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return cli;
    }

    /**
     * Este metodo busca un cliente en la tabla clientes utilizando como filtro
     * la llave primaria pasada como parametro.
     * @param id del tipo Long, llave primaria en la tabla clientes.
     * @return del tipo es.seas.feedback.cliente.manager.model.Cliente.
     * <p>En caso de errores, o de que no se encuentre nigún registro el metodo
     * retornará null.</p>
     */
    @Override
    public Cliente buscarRegistroPorID(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Cliente cliente = null;
        try {
            cliente = (Cliente) session.get(Cliente.class, Long.class);
            tx.commit();
        } catch (HibernateException ex) {
            LOG.error("HibernateException in the method buscarRegistroPorID", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return cliente;
    }

    /**
     * Este metodo lista todo los registro de la tabla clientes filtrados por
     * el parametro introducido.
     * @param segundoApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosPorSegundoApellido(String segundoApellido) {
        List<Cliente> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("segundoApellido", segundoApellido));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (Exception ex) {
            LOG.error("HibernateException in the method buscarRegistrosPorSegundoApellido", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo lista todo los registro de la tabla clientes filtrados por
     * el parametro introducido.
     * @param primerApellido del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosPorPrimerApellido(String primerApellido) {
        List<Cliente> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("primerApellido", primerApellido));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (Exception ex) {
            LOG.error("HibernateException in the method buscarRegistrosPorPrimerApellido", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo lista todo los registro de la tabla clientes filtrados por
     * el parametro introducido.
     * @param nombre del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosPorNombre(String nombre) {
        List<Cliente> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("nombre", nombre));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (Exception ex) {
            LOG.error("HibernateException in the method buscarRegistrosPorNombre", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo lista todo los registro de la tabla clientes filtrados por
     * el parametro activo = false.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosDadoDeBaja() {
        List<Cliente> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("activo", false));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (Exception ex) {
            LOG.error("HibernateException in the method buscarRegistrosDadoDeBaja", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo lista todo los registro de la tabla clientes filtrados por
     * el parametro activo = true.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Cliente> buscarRegistrosDadoDeAlta() {
        List<Cliente> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("activo", true));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            list = criteria.list();
            tx.commit();
        } catch (Exception ex) {
            LOG.error("HibernateException in the method buscarRegistrosDadoDeAlta", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    /**
     * Este metodo llama el metodo listarRegistros y crea una lista apartir
     * de la lista generada por el metodo con todos los clientes que el mes de la
     * fecha de nacimiento sea igual al mes introducido como parametro.
     * @param mes del tipo java.time.Month.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Cliente&gt;.
     * <p>En caso de error el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Cliente> cumpleañerosDelMes(Month mes) {
        List<Cliente> list = new ArrayList<>();
        for(Cliente cli : listarRegistros()){
            if(cli.getFechaNacimiento().getMonth().equals(mes)){
                list.add(cli);
            }
        }
       return list;
    }

    private void setChanges(Cliente cliente1, Cliente cliente2) {
        cliente1.setActivo(cliente2.isActivo());
        cliente1.setContactos(cliente2.getContactos());
        cliente1.setDirecion(cliente2.getDirecion());
        cliente1.setFechaNacimiento(cliente2.getFechaNacimiento());
        cliente1.setFechaPrimeraAlta(cliente2.getFechaPrimeraAlta());
        cliente1.setFechaUltimaBaja(cliente2.getFechaUltimaBaja());
        cliente1.setNif(cliente2.getNif());
        cliente1.setNombre(cliente2.getNombre());
        cliente1.setPrimerApellido(cliente2.getPrimerApellido());
        cliente1.setSegundoApellido(cliente2.getSegundoApellido());
    }
}
