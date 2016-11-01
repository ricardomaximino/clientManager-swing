package es.seas.feedback.cliente.manager.model.dao;

import es.seas.feedback.cliente.manager.model.Localidade;
import es.seas.feedback.cliente.manager.model.Provincia;
import es.seas.feedback.cliente.manager.model.dao.hibernate.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase es la implementación del ProvinciaDAO.
 * @author Ricardo Maximino.
 * <p>Esta clase como todas que implemente ProvinciaDAO deve:
 * <ul>
 * <li>guardar provincias en la base de datos con sus repectivas localidades
 *          <br>nueva provincia
 *          <br>atualizar provincia
 *      </li>
 * <li>buscar un provincia con sus respectivas localidades</li>
 * <li>listar todas las provincias con sus respectivas localidades</li>
 * </ul>
 * 
 */
public class ProvinciaDAOImpl implements ProvinciaDAO{
    private static final Logger LOG = LoggerFactory.getLogger(ProvinciaDAOImpl.class);
    
    /**
     * Este metodo intenta guardar una provincia en la base de datos.
     * @param provincia del tipo es.seas.feedback.cliente.manager.model.Provincia.
     * @return boolean para certificar se la acción ha sido realizada con
     * éxito o no.
     */
    @Override
    public boolean guardarProvincia(Provincia provincia){
        boolean resultado = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            if(getProvincia(provincia.getNombre())== null){
                session.save(provincia);
                tx.commit();
                resultado = true;
            }else{
                session.update(provincia);
                tx.commit();
                resultado = true;
            }
        } catch (Exception ex) {
            LOG.error("HibernateException in the method guardarProvincia", ex);
            tx.rollback();
            resultado = false;
        } finally {
            session.close();
        }
        return resultado;
    }
    
    /**
     * Este metodo busca una provincia el la base de datos utilizando el
     * parametro introducido.
     * @param nombre del tipo String, identificador único el la tabla Provincias.
     * @return del tipo es.seas.feedback.cliente.manager.model.Provincia.
     */
    @Override
    public Provincia getProvincia(String nombre){
        Provincia provincia = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            Criteria criteria = session.createCriteria(Provincia.class);
            criteria.add(Restrictions.eq("nombre", nombre));
            provincia = (Provincia)criteria.uniqueResult();
            tx.commit();
        } catch (Exception ex) {
            LOG.error("HibernateException in the method getProvincia", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return provincia;
    }
    
    /**
     * Este metodo lista todas la provincias con sus respectivas localidades
     * registradas en la base de datos.
     * @return java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Provincia&gt;.
     * <p>En caso de errores el metodo retornará una lista vacia y no null.</p>
     */
    @Override
    public List<Provincia> getLista(){
        List<Provincia> list = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            list = session.createQuery("From Provincia order by nombre").list();
            tx.commit();
        } catch (Exception ex) {
            LOG.error("HibernateException in the method getLista", ex);
            tx.rollback();
        } finally {
            session.close();
        }
        return list;
    }
    /**
     * Este metodo no hace parte de la interfaz ProvinciaDAO, y fue creado
     * para buscar provincias que tengan localidades con nombres iguales.
     * @param localidade del tipo String.
     * @return del tipo java.util.List
     * &lt;es.seas.feedback.cliente.manager.model.Provincia&gt;.
     */
    public List<Provincia> getProvinciasQueContiene(String localidade){
        List<Provincia> lista = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try{
            Criteria criteria = session.createCriteria(Provincia.class);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            for(Provincia pro : (List<Provincia>)criteria.list()){
                for(Localidade loc : pro.getLocalidades()){
                    if(loc.getNombre().equals(localidade)){
                        lista.add(pro);
                        break;
                    }
                }
            }
            tx.commit();
        }catch(Exception ex){
            LOG.error("HibernateException in the method getProvinciasQueContiene", ex);
            tx.rollback();
        }finally{
            session.close();
        }
        return lista;
    }
}