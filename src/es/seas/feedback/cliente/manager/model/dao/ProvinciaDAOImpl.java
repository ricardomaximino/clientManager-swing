/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.seas.feedback.cliente.manager.model.dao;

import es.seas.feedback.cliente.manager.model.Localidade;
import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.model.dao.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Ricardo
 */
public class ProvinciaDAOImpl implements ProvinciaDAO{
    
    @Override
    public boolean guardarProvincia(Provincia provincia){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            if(getProvincia(provincia.getNombre())== null){
                session.save(provincia);
                tx.commit();
                return true;
            }else{
                session.update(provincia);
                tx.commit();
                return true;
            }
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }
    
    @Override
    public Provincia getProvincia(String nombre){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Provincia.class);
            criteria.add(Restrictions.eq("nombre", nombre));
            Provincia provincia = (Provincia)criteria.uniqueResult();
            tx.commit();
            return provincia;
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    
    @Override
    public List<Provincia> getLista(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            List<Provincia> list = session.createQuery("From Provincia order by nombre").list();
            tx.commit();
            return list;
        } catch (Exception ex) {
            tx.rollback();
            ex.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
    public List<Provincia> getProvinciasQueContiene(String localidade){
        List<Provincia> lista = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try{
            Criteria criteria = session.createCriteria(Provincia.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            for(Provincia pro : (List<Provincia>)criteria.list()){
                System.out.println("Provincia: " + pro.getNombre());
                for(Localidade loc : pro.getLocalidades()){
                    System.out.println(loc.getNombre());
                    if(loc.getNombre().toUpperCase() == localidade.toUpperCase()){
                        System.out.println("Localidade: " + loc.getNombre()+ " SI.");
                        lista.add(pro);
                    }else{
                        System.out.println("Localidade: " + loc.getNombre() + " NO.");
                    }
                }
            }
          
            tx.commit();
        }catch(Exception ex){
            tx.rollback();
            ex.printStackTrace();
        }finally{
            session.close();
        }
        return lista;
    }
    public static void main(String[] args) {
        ProvinciaDAOImpl p = new ProvinciaDAOImpl();
        for(Provincia pro : p.getProvinciasQueContiene("Santa Pola")){
            System.out.println(pro.getNombre());
        }
        System.exit(0);
    }
}
