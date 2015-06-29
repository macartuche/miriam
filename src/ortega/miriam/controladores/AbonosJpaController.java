/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.controladores;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ortega.miriam.controladores.exceptions.NonexistentEntityException;
import ortega.miriam.entidades.Abonos;
import ortega.miriam.entidades.CuentasCxP;

/**
 *
 * @author macbookpro
 */
public class AbonosJpaController  extends EntityManagerlocal 
implements Serializable {

    public AbonosJpaController() {
         super();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Abonos abonos) {
        System.out.println("==========>"+abonos.getCuenta().getId());
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
//            CuentasCxP cuenta = abonos.getCuenta();
//            if (cuenta != null) {
//                cuenta = em.getReference(cuenta.getClass(), cuenta.getId());
//                abonos.setCuenta(cuenta);
//            }
            em.persist(abonos);
//            if (cuenta != null) {
//                cuenta.getAbonos().add(abonos);
//                cuenta = em.merge(cuenta);
//            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Abonos abonos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Abonos persistentAbonos = em.find(Abonos.class, abonos.getId());
            CuentasCxP cuentaOld = persistentAbonos.getCuenta();
            CuentasCxP cuentaNew = abonos.getCuenta();
            if (cuentaNew != null) {
                cuentaNew = em.getReference(cuentaNew.getClass(), cuentaNew.getId());
                abonos.setCuenta(cuentaNew);
            }
            abonos = em.merge(abonos);
            if (cuentaOld != null && !cuentaOld.equals(cuentaNew)) {
                cuentaOld.getAbonos().remove(abonos);
                cuentaOld = em.merge(cuentaOld);
            }
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                cuentaNew.getAbonos().add(abonos);
                cuentaNew = em.merge(cuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = abonos.getId();
                if (findAbonos(id) == null) {
                    throw new NonexistentEntityException("The abonos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Abonos abonos;
            try {
                abonos = em.getReference(Abonos.class, id);
                abonos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The abonos with id " + id + " no longer exists.", enfe);
            }
            CuentasCxP cuenta = abonos.getCuenta();
            if (cuenta != null) {
                cuenta.getAbonos().remove(abonos);
                cuenta = em.merge(cuenta);
            }
            em.remove(abonos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Abonos> findAbonosEntities() {
        return findAbonosEntities(true, -1, -1);
    }

    public List<Abonos> findAbonosEntities(int maxResults, int firstResult) {
        return findAbonosEntities(false, maxResults, firstResult);
    }

    private List<Abonos> findAbonosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Abonos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Abonos findAbonos(Integer id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Abonos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAbonosCount() {
        EntityManager em = super.getEmf().createEntityManager();    
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Abonos> rt = cq.from(Abonos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
