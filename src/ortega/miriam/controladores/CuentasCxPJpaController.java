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
import ortega.miriam.entidades.Abonos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ortega.miriam.controladores.exceptions.NonexistentEntityException;
import ortega.miriam.entidades.CuentasCxP;

/**
 *
 * @author macbookpro
 */
public class CuentasCxPJpaController extends EntityManagerlocal  
implements Serializable {

    public CuentasCxPJpaController() {
        super();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentasCxP cuentasCxP) {
        if (cuentasCxP.getAbonos() == null) {
            cuentasCxP.setAbonos(new ArrayList<Abonos>());
        }
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Factura facturaId = cuentasCxP.getFacturaId();
            if (facturaId != null) {
                facturaId = em.getReference(facturaId.getClass(), facturaId.getId());
                cuentasCxP.setFacturaId(facturaId);
            }
            List<Abonos> attachedAbonos = new ArrayList<Abonos>();
            for (Abonos abonosAbonosToAttach : cuentasCxP.getAbonos()) {
                abonosAbonosToAttach = em.getReference(abonosAbonosToAttach.getClass(), abonosAbonosToAttach.getId());
                attachedAbonos.add(abonosAbonosToAttach);
            }
            cuentasCxP.setAbonos(attachedAbonos);
            em.persist(cuentasCxP);
            if (facturaId != null) {
                CuentasCxP oldCuentaOfFacturaId = facturaId.getCuenta();
                if (oldCuentaOfFacturaId != null) {
                    oldCuentaOfFacturaId.setFacturaId(null);
                    oldCuentaOfFacturaId = em.merge(oldCuentaOfFacturaId);
                }
                facturaId.setCuenta(cuentasCxP);
                facturaId = em.merge(facturaId);
            }
            for (Abonos abonosAbonos : cuentasCxP.getAbonos()) {
                CuentasCxP oldCuentaOfAbonosAbonos = abonosAbonos.getCuenta();
                abonosAbonos.setCuenta(cuentasCxP);
                abonosAbonos = em.merge(abonosAbonos);
                if (oldCuentaOfAbonosAbonos != null) {
                    oldCuentaOfAbonosAbonos.getAbonos().remove(abonosAbonos);
                    oldCuentaOfAbonosAbonos = em.merge(oldCuentaOfAbonosAbonos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentasCxP cuentasCxP) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            CuentasCxP persistentCuentasCxP = em.find(CuentasCxP.class, cuentasCxP.getId());
            Factura facturaIdOld = persistentCuentasCxP.getFacturaId();
            Factura facturaIdNew = cuentasCxP.getFacturaId();
            List<Abonos> abonosOld = persistentCuentasCxP.getAbonos();
            List<Abonos> abonosNew = cuentasCxP.getAbonos();
            if (facturaIdNew != null) {
                facturaIdNew = em.getReference(facturaIdNew.getClass(), facturaIdNew.getId());
                cuentasCxP.setFacturaId(facturaIdNew);
            }
            List<Abonos> attachedAbonosNew = new ArrayList<Abonos>();
            for (Abonos abonosNewAbonosToAttach : abonosNew) {
                abonosNewAbonosToAttach = em.getReference(abonosNewAbonosToAttach.getClass(), abonosNewAbonosToAttach.getId());
                attachedAbonosNew.add(abonosNewAbonosToAttach);
            }
            abonosNew = attachedAbonosNew;
            cuentasCxP.setAbonos(abonosNew);
            cuentasCxP = em.merge(cuentasCxP);
            if (facturaIdOld != null && !facturaIdOld.equals(facturaIdNew)) {
                facturaIdOld.setCuenta(null);
                facturaIdOld = em.merge(facturaIdOld);
            }
            if (facturaIdNew != null && !facturaIdNew.equals(facturaIdOld)) {
                CuentasCxP oldCuentaOfFacturaId = facturaIdNew.getCuenta();
                if (oldCuentaOfFacturaId != null) {
                    oldCuentaOfFacturaId.setFacturaId(null);
                    oldCuentaOfFacturaId = em.merge(oldCuentaOfFacturaId);
                }
                facturaIdNew.setCuenta(cuentasCxP);
                facturaIdNew = em.merge(facturaIdNew);
            }
            for (Abonos abonosOldAbonos : abonosOld) {
                if (!abonosNew.contains(abonosOldAbonos)) {
                    abonosOldAbonos.setCuenta(null);
                    abonosOldAbonos = em.merge(abonosOldAbonos);
                }
            }
            for (Abonos abonosNewAbonos : abonosNew) {
                if (!abonosOld.contains(abonosNewAbonos)) {
                    CuentasCxP oldCuentaOfAbonosNewAbonos = abonosNewAbonos.getCuenta();
                    abonosNewAbonos.setCuenta(cuentasCxP);
                    abonosNewAbonos = em.merge(abonosNewAbonos);
                    if (oldCuentaOfAbonosNewAbonos != null && !oldCuentaOfAbonosNewAbonos.equals(cuentasCxP)) {
                        oldCuentaOfAbonosNewAbonos.getAbonos().remove(abonosNewAbonos);
                        oldCuentaOfAbonosNewAbonos = em.merge(oldCuentaOfAbonosNewAbonos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuentasCxP.getId();
//                if (findCuentasCxP(id) == null) {
//                    throw new NonexistentEntityException("The cuentasCxP with id " + id + " no longer exists.");
//                }
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
            CuentasCxP cuentasCxP;
            try {
                cuentasCxP = em.getReference(CuentasCxP.class, id);
                cuentasCxP.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentasCxP with id " + id + " no longer exists.", enfe);
            }
            Factura facturaId = cuentasCxP.getFacturaId();
            if (facturaId != null) {
                facturaId.setCuenta(null);
                facturaId = em.merge(facturaId);
            }
            List<Abonos> abonos = cuentasCxP.getAbonos();
            for (Abonos abonosAbonos : abonos) {
                abonosAbonos.setCuenta(null);
                abonosAbonos = em.merge(abonosAbonos);
            }
            em.remove(cuentasCxP);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentasCxP> findCuentasCxPEntities() {
        return findCuentasCxPEntities(true, -1, -1);
    }

    public List<CuentasCxP> findCuentasCxPEntities(int maxResults, int firstResult) {
        return findCuentasCxPEntities(false, maxResults, firstResult);
    }

    private List<CuentasCxP> findCuentasCxPEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentasCxP.class));
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
      
    public List<CuentasCxP> findCtas(String criteria, Date from, Date until,
            String type) {
        EntityManager em = super.getEmf().createEntityManager();
        String query  = "Select c from CuentasCxP c where 1=1 ";
        if(type.equals("COBRAR")){
            query += " and lower(c.facturaId.idcliente.entidadid.nombres) "
                    + "like :criteria ";
        }else{
            query += " and lower(c.facturaId.idproveedor.entidadid.nombres)"
                    + " like :criteria ";
        }
        query += " and c.tipo=:type ";

        if(from!=null && until!=null){
            query += " and c.fecha between :from and :until ";
        }
        Query q = em.createQuery(query);
        q.setParameter("type", type);
        q.setParameter("criteria", "%"+criteria.toLowerCase()+"%");
        
        if(from!=null && until!=null){
            q.setParameter("from", from);
            q.setParameter("until", until);
        }
        
        return q.getResultList();
    }

    public int getCuentasCxPCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentasCxP> rt = cq.from(CuentasCxP.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
