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
import ortega.miriam.entidades.Producto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ortega.miriam.controladores.exceptions.NonexistentEntityException;
import ortega.miriam.entidades.Grupo;
import ortega.miriam.entidades.Rol;

/**
 *
 * @author macbookpro
 */
public class GrupoJpaController  extends EntityManagerlocal  implements Serializable {

    public GrupoJpaController() {
        super();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) {
        if (grupo.getProductoList() == null) {
            grupo.setProductoList(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em =  super.getEmf().createEntityManager();
            em.getTransaction().begin();
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : grupo.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getId());
                attachedProductoList.add(productoListProductoToAttach);
            }
            grupo.setProductoList(attachedProductoList);
            em.persist(grupo);
            for (Producto productoListProducto : grupo.getProductoList()) {
                Grupo oldGrupoidOfProductoListProducto = productoListProducto.getGrupoid();
                productoListProducto.setGrupoid(grupo);
                productoListProducto = em.merge(productoListProducto);
                if (oldGrupoidOfProductoListProducto != null) {
                    oldGrupoidOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldGrupoidOfProductoListProducto = em.merge(oldGrupoidOfProductoListProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getId());
            List<Producto> productoListOld = persistentGrupo.getProductoList();
            List<Producto> productoListNew = grupo.getProductoList();
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getId());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            grupo.setProductoList(productoListNew);
            grupo = em.merge(grupo);
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setGrupoid(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Grupo oldGrupoidOfProductoListNewProducto = productoListNewProducto.getGrupoid();
                    productoListNewProducto.setGrupoid(grupo);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldGrupoidOfProductoListNewProducto != null && !oldGrupoidOfProductoListNewProducto.equals(grupo)) {
                        oldGrupoidOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldGrupoidOfProductoListNewProducto = em.merge(oldGrupoidOfProductoListNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = grupo.getId();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<Producto> productoList = grupo.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setGrupoid(null);
                productoListProducto = em.merge(productoListProducto);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     
    public List<Rol> find(Grupo grupo){
        
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> parametros = new HashMap<>();
        sql.append("SELECT g FROM Grupo g where 1=1");
        
        if(grupo.getId()!=null){
            sql.append(" and g.id =: id");
            parametros.put("id", grupo.getId());
        }
        
        if(grupo.getNombre()!=null && !grupo.getNombre().isEmpty()){
            sql.append(" and g.nombre like :nombre");
            parametros.put("nombre", grupo.getNombre());  
        }
        
     
         
        Query q = em.createQuery(sql.toString());
        
        for (String key : parametros.keySet()) {
            q.setParameter(key, parametros.get(key));
        }
        return q.getResultList(); 
    }
    
}
