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
import ortega.miriam.entidades.Entidad;
import ortega.miriam.entidades.Proveedores;

/**
 *
 * @author macbookpro
 */
public class ProveedoresJpaController extends EntityManagerlocal  implements Serializable{

    public ProveedoresJpaController( ){
         super();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedores proveedores) {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Entidad entidadid = proveedores.getEntidadid();
            if (entidadid != null) {
                entidadid = em.getReference(entidadid.getClass(), entidadid.getId());
                proveedores.setEntidadid(entidadid);
            }
            em.persist(proveedores);
            if (entidadid != null) {
                entidadid.getProveedoresCollection().add(proveedores);
                entidadid = em.merge(entidadid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedores proveedores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Proveedores persistentProveedores = em.find(Proveedores.class, proveedores.getId());
            Entidad entidadidOld = persistentProveedores.getEntidadid();
            Entidad entidadidNew = proveedores.getEntidadid();
            if (entidadidNew != null) {
                entidadidNew = em.getReference(entidadidNew.getClass(), entidadidNew.getId());
                proveedores.setEntidadid(entidadidNew);
            }
            proveedores = em.merge(proveedores);
            if (entidadidOld != null && !entidadidOld.equals(entidadidNew)) {
                entidadidOld.getProveedoresCollection().remove(proveedores);
                entidadidOld = em.merge(entidadidOld);
            }
            if (entidadidNew != null && !entidadidNew.equals(entidadidOld)) {
                entidadidNew.getProveedoresCollection().add(proveedores);
                entidadidNew = em.merge(entidadidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = proveedores.getId();
                if (findProveedores(id) == null) {
                    throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Proveedores proveedores;
            try {
                proveedores = em.getReference(Proveedores.class, id);
                proveedores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedores with id " + id + " no longer exists.", enfe);
            }
            Entidad entidadid = proveedores.getEntidadid();
            if (entidadid != null) {
                entidadid.getProveedoresCollection().remove(proveedores);
                entidadid = em.merge(entidadid);
            }
            em.remove(proveedores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedores> findProveedoresEntities() {
        return findProveedoresEntities(true, -1, -1);
    }

    public List<Proveedores> findProveedoresEntities(int maxResults, int firstResult) {
        return findProveedoresEntities(false, maxResults, firstResult);
    }

    private List<Proveedores> findProveedoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedores.class));
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

    public Proveedores findProveedores(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Proveedores.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedoresCount() {
        EntityManager em = super.getEmf().createEntityManager();;
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedores> rt = cq.from(Proveedores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
