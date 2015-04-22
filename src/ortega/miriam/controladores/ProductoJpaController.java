/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.controladores;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ortega.miriam.controladores.exceptions.NonexistentEntityException;
import ortega.miriam.entidades.Grupo;
import ortega.miriam.entidades.Producto;

/**
 *
 * @author macbookpro
 */
public class ProductoJpaController  extends EntityManagerlocal  implements Serializable {

    public ProductoJpaController() {
        super();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Grupo grupoid = producto.getGrupoid();
            if (grupoid != null) {
                grupoid = em.getReference(grupoid.getClass(), grupoid.getId());
                producto.setGrupoid(grupoid);
            }
            em.persist(producto);
            if (grupoid != null) {
                grupoid.getProductoList().add(producto);
                grupoid = em.merge(grupoid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = super.getEmf().createEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Grupo grupoidOld = persistentProducto.getGrupoid();
            Grupo grupoidNew = producto.getGrupoid();
            if (grupoidNew != null) {
                grupoidNew = em.getReference(grupoidNew.getClass(), grupoidNew.getId());
                producto.setGrupoid(grupoidNew);
            }
            producto = em.merge(producto);
            if (grupoidOld != null && !grupoidOld.equals(grupoidNew)) {
                grupoidOld.getProductoList().remove(producto);
                grupoidOld = em.merge(grupoidOld);
            }
            if (grupoidNew != null && !grupoidNew.equals(grupoidOld)) {
                grupoidNew.getProductoList().add(producto);
                grupoidNew = em.merge(grupoidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Grupo grupoid = producto.getGrupoid();
            if (grupoid != null) {
                grupoid.getProductoList().remove(producto);
                grupoid = em.merge(grupoid);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    
    public List<Producto> find(Producto producto){
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> parametros = new HashMap<>();
        sql.append("SELECT p FROM Producto p where 1=1");
        
        if(producto.getId()!=null){
            sql.append(" and p.id =: id");
            parametros.put("id", producto.getId());
        }
        
        if(producto.getNombre()!=null && !producto.getNombre().isEmpty()){
            sql.append(" and p.nombre like :nombre");
            parametros.put("nombre", producto.getNombre());  
        }
        
        if(producto.getStock()>0){
            sql.append(" and p.stock = :stock");
            parametros.put("stock", producto.getStock());  
        }
        
        if(producto.getStockMinimo() > 0){
            sql.append(" and p.stockMinimo = :stockMinimo");
            parametros.put("stockMinimo", producto.getStockMinimo());  
        }
        
        if(producto.getGrupoid()!=null){
            sql.append(" and p.grupoid = :grupoid");
            parametros.put("grupoid", producto.getGrupoid());  
        }
        
        if(producto.getPrecioCompra()!=null){
            sql.append(" and p.precioCompra = :precioCompra");
            parametros.put("precioCompra", producto.getPrecioCompra());  
        }
        
        if(producto.getPrecioVenta()!=null){
            sql.append(" and p.precioVenta = :precioVenta");
            parametros.put("precioVenta", producto.getPrecioVenta());  
        }
         
        Query q = em.createQuery(sql.toString());
        
        for (String key : parametros.keySet()) {
            q.setParameter(key, parametros.get(key));
        }
        return q.getResultList(); 
    }
    
    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
