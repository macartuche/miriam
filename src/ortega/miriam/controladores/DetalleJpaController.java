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
import ortega.miriam.entidades.Detalle;
import ortega.miriam.entidades.Producto;
import ortega.miriam.entidades.Factura;

/**
 *
 * @author macbookpro
 */
public class DetalleJpaController  extends EntityManagerlocal  implements Serializable {

    public DetalleJpaController() {
        super();
    } 

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detalle detalle) {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Producto idproducto = detalle.getIdproducto();
            if (idproducto != null) {
                idproducto = em.getReference(idproducto.getClass(), idproducto.getId());
                detalle.setIdproducto(idproducto);
            }
            Factura idfactura = detalle.getIdfactura();
            if (idfactura != null) {
                idfactura = em.getReference(idfactura.getClass(), idfactura.getId());
                detalle.setIdfactura(idfactura);
            }
            em.persist(detalle);
            if (idproducto != null) {
                idproducto.getDetalleList().add(detalle);
                idproducto = em.merge(idproducto);
            }
            if (idfactura != null) {
                idfactura.getDetalleList().add(detalle);
                idfactura = em.merge(idfactura);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detalle detalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Detalle persistentDetalle = em.find(Detalle.class, detalle.getId());
            Producto idproductoOld = persistentDetalle.getIdproducto();
            Producto idproductoNew = detalle.getIdproducto();
            Factura idfacturaOld = persistentDetalle.getIdfactura();
            Factura idfacturaNew = detalle.getIdfactura();
            if (idproductoNew != null) {
                idproductoNew = em.getReference(idproductoNew.getClass(), idproductoNew.getId());
                detalle.setIdproducto(idproductoNew);
            }
            if (idfacturaNew != null) {
                idfacturaNew = em.getReference(idfacturaNew.getClass(), idfacturaNew.getId());
                detalle.setIdfactura(idfacturaNew);
            }
            detalle = em.merge(detalle);
            if (idproductoOld != null && !idproductoOld.equals(idproductoNew)) {
                idproductoOld.getDetalleList().remove(detalle);
                idproductoOld = em.merge(idproductoOld);
            }
            if (idproductoNew != null && !idproductoNew.equals(idproductoOld)) {
                idproductoNew.getDetalleList().add(detalle);
                idproductoNew = em.merge(idproductoNew);
            }
            if (idfacturaOld != null && !idfacturaOld.equals(idfacturaNew)) {
                idfacturaOld.getDetalleList().remove(detalle);
                idfacturaOld = em.merge(idfacturaOld);
            }
            if (idfacturaNew != null && !idfacturaNew.equals(idfacturaOld)) {
                idfacturaNew.getDetalleList().add(detalle);
                idfacturaNew = em.merge(idfacturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = detalle.getId();
                if (findDetalle(id) == null) {
                    throw new NonexistentEntityException("The detalle with id " + id + " no longer exists.");
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
            Detalle detalle;
            try {
                detalle = em.getReference(Detalle.class, id);
                detalle.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalle with id " + id + " no longer exists.", enfe);
            }
            Producto idproducto = detalle.getIdproducto();
            if (idproducto != null) {
                idproducto.getDetalleList().remove(detalle);
                idproducto = em.merge(idproducto);
            }
            Factura idfactura = detalle.getIdfactura();
            if (idfactura != null) {
                idfactura.getDetalleList().remove(detalle);
                idfactura = em.merge(idfactura);
            }
            em.remove(detalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detalle> findDetalleEntities() {
        return findDetalleEntities(true, -1, -1);
    }

    public List<Detalle> findDetalleEntities(int maxResults, int firstResult) {
        return findDetalleEntities(false, maxResults, firstResult);
    }

    private List<Detalle> findDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detalle.class));
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

    public Detalle findDetalle(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Detalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detalle> rt = cq.from(Detalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
