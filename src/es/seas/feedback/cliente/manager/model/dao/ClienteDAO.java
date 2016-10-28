
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

/*
 *
 * @author Ricardo
 */
public class ClienteDAO implements PersonaDAO<Cliente> {

    private static final Logger log = LoggerFactory.getLogger(ClienteDAO.class);

    public ClienteDAO() {
        log.info("ClienteDAO implements PersonaDAO was instantiated " + LocalDate.now().toString());
    }

    @Override
    public boolean añadirNuevoRegistro(Cliente cliente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(cliente);
            tx.commit();
            return true;
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean atualizarRegistro(Cliente clienteParametro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Cliente cliente = (Cliente)session.createCriteria(Cliente.class).add(Restrictions.eq("nif", clienteParametro.getNif())).uniqueResult();
            setChanges(cliente, clienteParametro);
            session.update(cliente);
            tx.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean darDeBaja(Cliente cliente) {
        return darDeBaja(cliente.getNif());
    }

    @Override
    public boolean darDeBaja(String nif) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Cliente cli = (Cliente) session.get(Cliente.class, nif);
            if (cli != null) {
                cli.setActivo(false);
                cli.setFechaUltimaBaja(LocalDate.now());
                session.update(cli);
                tx.commit();
            } else {
                throw new NullPointerException("No hay ningun cliente registrado con el N.I.F.: " + nif);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean borrarRegistro(Cliente cliente) {
        return borrarRegistro(cliente.getNif());
    }

    @Override
    public boolean borrarRegistro(String nif) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("nif", nif));
            Cliente cli = (Cliente) criteria.uniqueResult();
            if (cli != null) {
                session.delete(cli);
                tx.commit();
            } else {
                log.debug("Cliente no encontrado.");
            }
            return true;
        } catch (HibernateException ex) {
            tx.rollback();
            ex.printStackTrace();
            log.error(ex.getLocalizedMessage());
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Cliente> listarRegistros() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Cliente> list = criteria.list();
            tx.commit();

            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public Cliente buscarRegistroPorNIF(String nif) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("nif", nif));
            Cliente cli = (Cliente) criteria.uniqueResult();
            if (cli != null) {
                tx.commit();

            } else {

                log.debug("Cliente no encontrado.");
            }
            return cli;
        } catch (HibernateException ex) {
            log.error(ex.getLocalizedMessage());
            ex.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            session.close();
        }
    }
    @Override
    public Cliente buscarRegistroPorID(long id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Cliente cliente = null;
        try{
            cliente = (Cliente)session.get(Cliente.class, Long.class);
            tx.commit();
        }catch(HibernateException ex){
            ex.printStackTrace();
            tx.rollback();
        }finally{
            session.close();
        }
        return cliente;
    }

    @Override
    public List<Cliente> buscarRegistrosPorSegundoApellido(String segundoApellido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("segundoApellido", segundoApellido));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Cliente> list = criteria.list();
            tx.commit();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Cliente> buscarRegistrosPorPrimerApellido(String primerApellido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("primerApellido", primerApellido));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Cliente> list = criteria.list();
            tx.commit();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Cliente> buscarRegistrosPorNombre(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("nombre", nombre));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Cliente> list = criteria.list();
            tx.commit();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Cliente> buscarRegistrosDadoDeBaja() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("activo", false));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Cliente> list = criteria.list();
            tx.commit();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Cliente> buscarRegistrosDadoDeAlta() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.add(Restrictions.eq("activo", true));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Cliente> list =  criteria.list();
            tx.commit();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Cliente> cumpleañerosDelMes(Month mes) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Cliente.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Cliente> list = criteria.list();
            List<Cliente> cumpleañeros = new ArrayList<>();
            for (Cliente cli : list) {
                if (cli.getFechaNacimiento().getMonth().equals(mes)) {
                    cumpleañeros.add(cli);
                }
            }
            tx.commit();
            return cumpleañeros;
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
            return null;
        } finally {
            session.close();
        }
    }
     private void setChanges(Cliente cliente1,Cliente cliente2){
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
