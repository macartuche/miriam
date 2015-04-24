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
import ortega.miriam.entidades.Producto;
import ortega.miriam.entidades.Factura;
import ortega.miriam.entidades.Kardex;

/**
 *
 * @author macbookpro
 */
public class KardexJpaController implements Serializable {

    public KardexJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kardex kardex) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idproducto = kardex.getIdproducto();
            if (idproducto != null) {
                idproducto = em.getReference(idproducto.getClass(), idproducto.getId());
                kardex.setIdproducto(idproducto);
            }
            Factura idfactura = kardex.getIdfactura();
            if (idfactura != null) {
                idfactura = em.getReference(idfactura.getClass(), idfactura.getId());
                kardex.setIdfactura(idfactura);
            }
            em.persist(kardex);
            if (idproducto != null) {
                idproducto.getKardexList().add(kardex);
                idproducto = em.merge(idproducto);
            }
            if (idfactura != null) {
                idfactura.getKardexList().add(kardex);
                idfactura = em.merge(idfactura);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kardex kardex) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kardex persistentKardex = em.find(Kardex.class, kardex.getId());
            Producto idproductoOld = persistentKardex.getIdproducto();
            Producto idproductoNew = kardex.getIdproducto();
            Factura idfacturaOld = persistentKardex.getIdfactura();
            Factura idfacturaNew = kardex.getIdfactura();
            if (idproductoNew != null) {
                idproductoNew = em.getReference(idproductoNew.getClass(), idproductoNew.getId());
                kardex.setIdproducto(idproductoNew);
            }
            if (idfacturaNew != null) {
                idfacturaNew = em.getReference(idfacturaNew.getClass(), idfacturaNew.getId());
                kardex.setIdfactura(idfacturaNew);
            }
            kardex = em.merge(kardex);
            if (idproductoOld != null && !idproductoOld.equals(idproductoNew)) {
                idproductoOld.getKardexList().remove(kardex);
                idproductoOld = em.merge(idproductoOld);
            }
            if (idproductoNew != null && !idproductoNew.equals(idproductoOld)) {
                idproductoNew.getKardexList().add(kardex);
                idproductoNew = em.merge(idproductoNew);
            }
            if (idfacturaOld != null && !idfacturaOld.equals(idfacturaNew)) {
                idfacturaOld.getKardexList().remove(kardex);
                idfacturaOld = em.merge(idfacturaOld);
            }
            if (idfacturaNew != null && !idfacturaNew.equals(idfacturaOld)) {
                idfacturaNew.getKardexList().add(kardex);
                idfacturaNew = em.merge(idfacturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = kardex.getId();
                if (findKardex(id) == null) {
                    throw new NonexistentEntityException("The kardex with id " + id + " no longer exists.");
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
            em = getEntityManager();
            em.getTransaction().begin();
            Kardex kardex;
            try {
                kardex = em.getReference(Kardex.class, id);
                kardex.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kardex with id " + id + " no longer exists.", enfe);
            }
            Producto idproducto = kardex.getIdproducto();
            if (idproducto != null) {
                idproducto.getKardexList().remove(kardex);
                idproducto = em.merge(idproducto);
            }
            Factura idfactura = kardex.getIdfactura();
            if (idfactura != null) {
                idfactura.getKardexList().remove(kardex);
                idfactura = em.merge(idfactura);
            }
            em.remove(kardex);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kardex> findKardexEntities() {
        return findKardexEntities(true, -1, -1);
    }

    public List<Kardex> findKardexEntities(int maxResults, int firstResult) {
        return findKardexEntities(false, maxResults, firstResult);
    }

    private List<Kardex> findKardexEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kardex.class));
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

    public Kardex findKardex(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kardex.class, id);
        } finally {
            em.close();
        }
    }

    public int getKardexCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kardex> rt = cq.from(Kardex.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
