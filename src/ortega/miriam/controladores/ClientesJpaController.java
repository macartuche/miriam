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
import ortega.miriam.entidades.Clientes;
import ortega.miriam.entidades.Entidad;

/**
 *
 * @author macbookpro
 */
public class ClientesJpaController extends EntityManagerlocal  implements Serializable {

    public ClientesJpaController() { 
        super();
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clientes clientes) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadid = clientes.getEntidadid();
            if (entidadid != null) {
                entidadid = em.getReference(entidadid.getClass(), entidadid.getId());
                clientes.setEntidadid(entidadid);
            }
            em.persist(clientes);
            if (entidadid != null) {
                entidadid.getClientesCollection().add(clientes);
                entidadid = em.merge(entidadid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clientes clientes) throws NonexistentEntityException, Exception {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes persistentClientes = em.find(Clientes.class, clientes.getId());
            Entidad entidadidOld = persistentClientes.getEntidadid();
            Entidad entidadidNew = clientes.getEntidadid();
            if (entidadidNew != null) {
                entidadidNew = em.getReference(entidadidNew.getClass(), entidadidNew.getId());
                clientes.setEntidadid(entidadidNew);
            }
            clientes = em.merge(clientes);
            if (entidadidOld != null && !entidadidOld.equals(entidadidNew)) {
                entidadidOld.getClientesCollection().remove(clientes);
                entidadidOld = em.merge(entidadidOld);
            }
            if (entidadidNew != null && !entidadidNew.equals(entidadidOld)) {
                entidadidNew.getClientesCollection().add(clientes);
                entidadidNew = em.merge(entidadidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clientes.getId();
                if (findClientes(id) == null) {
                    throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
        EntityManager em = super.getEmf().createEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            Entidad entidadid = clientes.getEntidadid();
            if (entidadid != null) {
                entidadid.getClientesCollection().remove(clientes);
                entidadid = em.merge(entidadid);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
