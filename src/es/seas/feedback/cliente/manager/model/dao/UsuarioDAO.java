/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Ricardo
 */
public class UsuarioDAO implements PersonaDAO<Usuario>{

    private static final Logger log = LoggerFactory.getLogger(UsuarioDAO.class);

    public UsuarioDAO() {
        log.info("UsuarioDAO implements PersonaDAO was instantiated " + LocalDate.now().toString());
    }

    @Override
    public boolean añadirNuevoRegistro(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(usuario);
            tx.commit();
            System.out.println("Anadindo no DAO");
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
    public boolean atualizarRegistro(Usuario usuarioParametro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Usuario usuario = (Usuario)session.createCriteria(Usuario.class).add(Restrictions.eq("nif", usuarioParametro.getNif())).uniqueResult();
            setChanges(usuario, usuarioParametro);
            session.update(usuario);
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
    public boolean darDeBaja(Usuario usu) {
        return darDeBaja(usu.getNif());
    }

    @Override
    public boolean darDeBaja(String nif) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Usuario usu = (Usuario) session.get(Usuario.class, nif);
            if (usu != null) {
                usu.setActivo(false);
                usu.setFechaUltimaBaja(LocalDate.now());
                session.update(usu);
                tx.commit();
            } else {
                throw new NullPointerException("No hay ningun usuario registrado con el N.I.F.: " + nif);
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
    public boolean borrarRegistro(Usuario usuario) {
        return borrarRegistro(usuario.getNif());
    }

    @Override
    public boolean borrarRegistro(String nif) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nif", nif));
            Usuario usu = (Usuario) criteria.uniqueResult();
            if (usu != null) {
                session.delete(usu);
                tx.commit();
            } else {
                log.debug("Usuario no encontrado.");
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            tx.rollback();
            log.error(ex.getLocalizedMessage());
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Usuario> listarRegistros() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Usuario> list = criteria.list();
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
    public Usuario buscarRegistroPorNIF(String nif) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nif", nif));
            Usuario cli = (Usuario) criteria.uniqueResult();
            if (cli != null) {
                tx.commit();

            } else {

                log.debug("Cliente no encontrado.");
            }
            return cli;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            log.error(ex.getLocalizedMessage());
            tx.rollback();
            return null;
        } finally {
            session.close();
        }
    }
    @Override
    public Usuario buscarRegistroPorID(long id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Usuario usuario = null;
        try{
            usuario = (Usuario)session.get(Usuario.class, Long.class);
            tx.commit();
        }catch(HibernateException ex){
            ex.printStackTrace();
            tx.rollback();
        }finally{
            session.close();
        }
        return usuario;
    }

    @Override
    public List<Usuario> buscarRegistrosPorSegundoApellido(String segundoApellido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("segundoApellido", segundoApellido));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Usuario> list = criteria.list();
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
    public List<Usuario> buscarRegistrosPorPrimerApellido(String primerApellido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("primerApellido", primerApellido));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Usuario> list = criteria.list();
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
    public List<Usuario> buscarRegistrosPorNombre(String nombre) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nombre", nombre));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Usuario> list = criteria.list();
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
    public List<Usuario> buscarRegistrosDadoDeBaja() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("activo", false));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Usuario> list = criteria.list();
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
    public List<Usuario> buscarRegistrosDadoDeAlta() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("activo", true));
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Usuario> list =  criteria.list();
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
    public List<Usuario> cumpleañerosDelMes(Month mes) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            List<Usuario> list = criteria.list();
            List<Usuario> cumpleañeros = new ArrayList<>();
            for (Usuario usu : list) {
                if (usu.getFechaNacimiento().getMonth().equals(mes)) {
                    cumpleañeros.add(usu);
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
    private void setChanges(Usuario usuario1,Usuario usuario2){
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
