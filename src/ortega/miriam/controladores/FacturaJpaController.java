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
import ortega.miriam.entidades.Proveedores;
import ortega.miriam.entidades.TipoFactura;
import ortega.miriam.entidades.Clientes;
import ortega.miriam.entidades.Kardex;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager; 
import ortega.miriam.controladores.exceptions.IllegalOrphanException;
import ortega.miriam.controladores.exceptions.NonexistentEntityException;
import ortega.miriam.entidades.Detalle;
import ortega.miriam.entidades.Factura;

/**
 *
 * @author macbookpro
 */
public class FacturaJpaController extends EntityManagerlocal  implements Serializable  {

    public FacturaJpaController() {
         super();
    } 

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        if (factura.getKardexList() == null) {
            factura.setKardexList(new ArrayList<Kardex>());
        }
        if (factura.getDetalleList() == null) {
            factura.setDetalleList(new ArrayList<Detalle>());
        }
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Proveedores idproveedor = factura.getIdproveedor();
            if (idproveedor != null) {
                idproveedor = em.getReference(idproveedor.getClass(), idproveedor.getId());
                factura.setIdproveedor(idproveedor);
            }
            TipoFactura idtipofac = factura.getIdtipofac();
            if (idtipofac != null) {
                idtipofac = em.getReference(idtipofac.getClass(), idtipofac.getId());
                factura.setIdtipofac(idtipofac);
            }
            Clientes idcliente = factura.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getId());
                factura.setIdcliente(idcliente);
            }
            List<Kardex> attachedKardexList = new ArrayList<Kardex>();
            for (Kardex kardexListKardexToAttach : factura.getKardexList()) {
                kardexListKardexToAttach = em.getReference(kardexListKardexToAttach.getClass(), kardexListKardexToAttach.getId());
                attachedKardexList.add(kardexListKardexToAttach);
            }
            factura.setKardexList(attachedKardexList);
//            List<Detalle> attachedDetalleList = new ArrayList<Detalle>();
//            for (Detalle detalleListDetalleToAttach : factura.getDetalleList()) {
//                detalleListDetalleToAttach = em.getReference(detalleListDetalleToAttach.getClass(), detalleListDetalleToAttach.getId());
//                attachedDetalleList.add(detalleListDetalleToAttach);
//            }
//            factura.setDetalleList(attachedDetalleList);
            em.persist(factura);
            if (idproveedor != null) {
                idproveedor.getFacturaList().add(factura);
                idproveedor = em.merge(idproveedor);
            }
            if (idtipofac != null) {
                idtipofac.getFacturaList().add(factura);
                idtipofac = em.merge(idtipofac);
            }
            if (idcliente != null) {
                idcliente.getFacturaList().add(factura);
                idcliente = em.merge(idcliente);
            }
            for (Kardex kardexListKardex : factura.getKardexList()) {
                Factura oldIdfacturaOfKardexListKardex = kardexListKardex.getIdfactura();
                kardexListKardex.setIdfactura(factura);
                kardexListKardex = em.merge(kardexListKardex);
                if (oldIdfacturaOfKardexListKardex != null) {
                    oldIdfacturaOfKardexListKardex.getKardexList().remove(kardexListKardex);
                    oldIdfacturaOfKardexListKardex = em.merge(oldIdfacturaOfKardexListKardex);
                }
            }
            for (Detalle detalleListDetalle : factura.getDetalleList()) {
                 detalleListDetalle.setIdfactura(factura);
                if(detalleListDetalle.getId()==null){
                    em.persist(detalleListDetalle);
                }else{
                    em.merge(detalleListDetalle);
                }
//                detalleListDetalle = em.merge(detalleListDetalle);
//                if (oldIdfacturaOfDetalleListDetalle != null) {
//                    oldIdfacturaOfDetalleListDetalle.getDetalleList().remove(detalleListDetalle);
//                    oldIdfacturaOfDetalleListDetalle = em.merge(oldIdfacturaOfDetalleListDetalle);
//                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getId());
            Proveedores idproveedorOld = persistentFactura.getIdproveedor();
            Proveedores idproveedorNew = factura.getIdproveedor();
            TipoFactura idtipofacOld = persistentFactura.getIdtipofac();
            TipoFactura idtipofacNew = factura.getIdtipofac();
            Clientes idclienteOld = persistentFactura.getIdcliente();
            Clientes idclienteNew = factura.getIdcliente();
            List<Kardex> kardexListOld = persistentFactura.getKardexList();
            List<Kardex> kardexListNew = factura.getKardexList();
            List<Detalle> detalleListOld = persistentFactura.getDetalleList();
            List<Detalle> detalleListNew = factura.getDetalleList();
            List<String> illegalOrphanMessages = null;
            for (Kardex kardexListOldKardex : kardexListOld) {
                if (!kardexListNew.contains(kardexListOldKardex)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Kardex " + kardexListOldKardex + " since its idfactura field is not nullable.");
                }
            }
            for (Detalle detalleListOldDetalle : detalleListOld) {
                if (!detalleListNew.contains(detalleListOldDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detalle " + detalleListOldDetalle + " since its idfactura field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idproveedorNew != null) {
                idproveedorNew = em.getReference(idproveedorNew.getClass(), idproveedorNew.getId());
                factura.setIdproveedor(idproveedorNew);
            }
            if (idtipofacNew != null) {
                idtipofacNew = em.getReference(idtipofacNew.getClass(), idtipofacNew.getId());
                factura.setIdtipofac(idtipofacNew);
            }
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getId());
                factura.setIdcliente(idclienteNew);
            }
            List<Kardex> attachedKardexListNew = new ArrayList<Kardex>();
            for (Kardex kardexListNewKardexToAttach : kardexListNew) {
                kardexListNewKardexToAttach = em.getReference(kardexListNewKardexToAttach.getClass(), kardexListNewKardexToAttach.getId());
                attachedKardexListNew.add(kardexListNewKardexToAttach);
            }
            kardexListNew = attachedKardexListNew;
            factura.setKardexList(kardexListNew);
            List<Detalle> attachedDetalleListNew = new ArrayList<Detalle>();
            for (Detalle detalleListNewDetalleToAttach : detalleListNew) {
                detalleListNewDetalleToAttach = em.getReference(detalleListNewDetalleToAttach.getClass(), detalleListNewDetalleToAttach.getId());
                attachedDetalleListNew.add(detalleListNewDetalleToAttach);
            }
            detalleListNew = attachedDetalleListNew;
            factura.setDetalleList(detalleListNew);
            factura = em.merge(factura);
            if (idproveedorOld != null && !idproveedorOld.equals(idproveedorNew)) {
                idproveedorOld.getFacturaList().remove(factura);
                idproveedorOld = em.merge(idproveedorOld);
            }
            if (idproveedorNew != null && !idproveedorNew.equals(idproveedorOld)) {
                idproveedorNew.getFacturaList().add(factura);
                idproveedorNew = em.merge(idproveedorNew);
            }
            if (idtipofacOld != null && !idtipofacOld.equals(idtipofacNew)) {
                idtipofacOld.getFacturaList().remove(factura);
                idtipofacOld = em.merge(idtipofacOld);
            }
            if (idtipofacNew != null && !idtipofacNew.equals(idtipofacOld)) {
                idtipofacNew.getFacturaList().add(factura);
                idtipofacNew = em.merge(idtipofacNew);
            }
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getFacturaList().remove(factura);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getFacturaList().add(factura);
                idclienteNew = em.merge(idclienteNew);
            }
            for (Kardex kardexListNewKardex : kardexListNew) {
                if (!kardexListOld.contains(kardexListNewKardex)) {
                    Factura oldIdfacturaOfKardexListNewKardex = kardexListNewKardex.getIdfactura();
                    kardexListNewKardex.setIdfactura(factura);
                    kardexListNewKardex = em.merge(kardexListNewKardex);
                    if (oldIdfacturaOfKardexListNewKardex != null && !oldIdfacturaOfKardexListNewKardex.equals(factura)) {
                        oldIdfacturaOfKardexListNewKardex.getKardexList().remove(kardexListNewKardex);
                        oldIdfacturaOfKardexListNewKardex = em.merge(oldIdfacturaOfKardexListNewKardex);
                    }
                }
            }
            for (Detalle detalleListNewDetalle : detalleListNew) {
                if (!detalleListOld.contains(detalleListNewDetalle)) {
                    Factura oldIdfacturaOfDetalleListNewDetalle = detalleListNewDetalle.getIdfactura();
                    detalleListNewDetalle.setIdfactura(factura);
                    detalleListNewDetalle = em.merge(detalleListNewDetalle);
                    if (oldIdfacturaOfDetalleListNewDetalle != null && !oldIdfacturaOfDetalleListNewDetalle.equals(factura)) {
                        oldIdfacturaOfDetalleListNewDetalle.getDetalleList().remove(detalleListNewDetalle);
                        oldIdfacturaOfDetalleListNewDetalle = em.merge(oldIdfacturaOfDetalleListNewDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = factura.getId();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Kardex> kardexListOrphanCheck = factura.getKardexList();
            for (Kardex kardexListOrphanCheckKardex : kardexListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the Kardex " + kardexListOrphanCheckKardex + " in its kardexList field has a non-nullable idfactura field.");
            }
            List<Detalle> detalleListOrphanCheck = factura.getDetalleList();
            for (Detalle detalleListOrphanCheckDetalle : detalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the Detalle " + detalleListOrphanCheckDetalle + " in its detalleList field has a non-nullable idfactura field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Proveedores idproveedor = factura.getIdproveedor();
            if (idproveedor != null) {
                idproveedor.getFacturaList().remove(factura);
                idproveedor = em.merge(idproveedor);
            }
            TipoFactura idtipofac = factura.getIdtipofac();
            if (idtipofac != null) {
                idtipofac.getFacturaList().remove(factura);
                idtipofac = em.merge(idtipofac);
            }
            Clientes idcliente = factura.getIdcliente();
            if (idcliente != null) {
                idcliente.getFacturaList().remove(factura);
                idcliente = em.merge(idcliente);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
