/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ortega.miriam.entidades.Factura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ortega.miriam.controladores.exceptions.IllegalOrphanException;
import ortega.miriam.controladores.exceptions.NonexistentEntityException;
import ortega.miriam.entidades.TipoFactura;

/**
 *
 * @author macbookpro
 */
public class TipoFacturaJpaController extends EntityManagerlocal implements Serializable {

    public TipoFacturaJpaController() {
        super();
    } 

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoFactura tipoFactura) {
        if (tipoFactura.getFacturaList() == null) {
            tipoFactura.setFacturaList(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            List<Factura> attachedFacturaList = new ArrayList<Factura>();
            for (Factura facturaListFacturaToAttach : tipoFactura.getFacturaList()) {
                facturaListFacturaToAttach = em.getReference(facturaListFacturaToAttach.getClass(), facturaListFacturaToAttach.getId());
                attachedFacturaList.add(facturaListFacturaToAttach);
            }
            tipoFactura.setFacturaList(attachedFacturaList);
            em.persist(tipoFactura);
            for (Factura facturaListFactura : tipoFactura.getFacturaList()) {
                TipoFactura oldIdtipofacOfFacturaListFactura = facturaListFactura.getIdtipofac();
                facturaListFactura.setIdtipofac(tipoFactura);
                facturaListFactura = em.merge(facturaListFactura);
                if (oldIdtipofacOfFacturaListFactura != null) {
                    oldIdtipofacOfFacturaListFactura.getFacturaList().remove(facturaListFactura);
                    oldIdtipofacOfFacturaListFactura = em.merge(oldIdtipofacOfFacturaListFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoFactura tipoFactura) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            TipoFactura persistentTipoFactura = em.find(TipoFactura.class, tipoFactura.getId());
            List<Factura> facturaListOld = persistentTipoFactura.getFacturaList();
            List<Factura> facturaListNew = tipoFactura.getFacturaList();
            List<String> illegalOrphanMessages = null;
            for (Factura facturaListOldFactura : facturaListOld) {
                if (!facturaListNew.contains(facturaListOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaListOldFactura + " since its idtipofac field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Factura> attachedFacturaListNew = new ArrayList<Factura>();
            for (Factura facturaListNewFacturaToAttach : facturaListNew) {
                facturaListNewFacturaToAttach = em.getReference(facturaListNewFacturaToAttach.getClass(), facturaListNewFacturaToAttach.getId());
                attachedFacturaListNew.add(facturaListNewFacturaToAttach);
            }
            facturaListNew = attachedFacturaListNew;
            tipoFactura.setFacturaList(facturaListNew);
            tipoFactura = em.merge(tipoFactura);
            for (Factura facturaListNewFactura : facturaListNew) {
                if (!facturaListOld.contains(facturaListNewFactura)) {
                    TipoFactura oldIdtipofacOfFacturaListNewFactura = facturaListNewFactura.getIdtipofac();
                    facturaListNewFactura.setIdtipofac(tipoFactura);
                    facturaListNewFactura = em.merge(facturaListNewFactura);
                    if (oldIdtipofacOfFacturaListNewFactura != null && !oldIdtipofacOfFacturaListNewFactura.equals(tipoFactura)) {
                        oldIdtipofacOfFacturaListNewFactura.getFacturaList().remove(facturaListNewFactura);
                        oldIdtipofacOfFacturaListNewFactura = em.merge(oldIdtipofacOfFacturaListNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoFactura.getId();
                if (findTipoFactura(id) == null) {
                    throw new NonexistentEntityException("The tipoFactura with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            TipoFactura tipoFactura;
            try {
                tipoFactura = em.getReference(TipoFactura.class, id);
                tipoFactura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoFactura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Factura> facturaListOrphanCheck = tipoFactura.getFacturaList();
            for (Factura facturaListOrphanCheckFactura : facturaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoFactura (" + tipoFactura + ") cannot be destroyed since the Factura " + facturaListOrphanCheckFactura + " in its facturaList field has a non-nullable idtipofac field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoFactura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoFactura> findTipoFacturaEntities() {
        return findTipoFacturaEntities(true, -1, -1);
    }

    public List<TipoFactura> findTipoFacturaEntities(int maxResults, int firstResult) {
        return findTipoFacturaEntities(false, maxResults, firstResult);
    }

    private List<TipoFactura> findTipoFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoFactura.class));
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

    public TipoFactura findTipoFactura(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(TipoFactura.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoFacturaCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoFactura> rt = cq.from(TipoFactura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
