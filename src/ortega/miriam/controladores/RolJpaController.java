/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.controladores;

import ortega.miriam.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ortega.miriam.entidades.Usuarios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager; 
import ortega.miriam.controladores.exceptions.IllegalOrphanException;
import ortega.miriam.entidades.Rol;

/**
 *
 * @author macbookpro
 */
public class RolJpaController  extends EntityManagerlocal implements Serializable {

    public RolJpaController() {
        super();
    } 

    

    public void create(Rol rol) {
        if (rol.getUsuariosCollection() == null) {
            rol.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        EntityManager em = super.getEmf().createEntityManager();
        try {
            
            em.getTransaction().begin();
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : rol.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            rol.setUsuariosCollection(attachedUsuariosCollection);
            em.persist(rol);
            for (Usuarios usuariosCollectionUsuarios : rol.getUsuariosCollection()) {
                Rol oldRolidOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getRolid();
                usuariosCollectionUsuarios.setRolid(rol);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldRolidOfUsuariosCollectionUsuarios != null) {
                    oldRolidOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldRolidOfUsuariosCollectionUsuarios = em.merge(oldRolidOfUsuariosCollectionUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = super.getEmf().createEntityManager();
        try { 
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getId());
            Collection<Usuarios> usuariosCollectionOld = persistentRol.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = rol.getUsuariosCollection();
            List<String> illegalOrphanMessages = null;
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosCollectionOldUsuarios + " since its rolid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            rol.setUsuariosCollection(usuariosCollectionNew);
            rol = em.merge(rol);
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    Rol oldRolidOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getRolid();
                    usuariosCollectionNewUsuarios.setRolid(rol);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldRolidOfUsuariosCollectionNewUsuarios != null && !oldRolidOfUsuariosCollectionNewUsuarios.equals(rol)) {
                        oldRolidOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldRolidOfUsuariosCollectionNewUsuarios = em.merge(oldRolidOfUsuariosCollectionNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = rol.getId();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
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
        EntityManager em = super.getEmf().createEntityManager();
        
        try {  
            em.getTransaction().begin();
            Rol rol;
            try { 
                rol = em.getReference(Rol.class, id);
                rol.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Usuarios> usuariosCollectionOrphanCheck = rol.getUsuariosCollection();
            for (Usuarios usuariosCollectionOrphanCheckUsuarios : usuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rol (" + rol + ") cannot be destroyed since the Usuarios " + usuariosCollectionOrphanCheckUsuarios + " in its usuariosCollection field has a non-nullable rolid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) { 
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Rol> find(Rol rol){
        
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> parametros = new HashMap<>();
        sql.append("SELECT r FROM Rol r where 1=1");
        
        if(rol.getId()!=null){
            sql.append(" and r.id =: id");
            parametros.put("id", rol.getId());
        }
        
        if(rol.getNombre()!=null && !rol.getNombre().isEmpty()){
            sql.append(" and r.nombre like :nombre");
            parametros.put("nombre", rol.getNombre()); 
            System.out.println(rol.getNombre());
        }
        
        System.out.println(sql.toString());
        Query q = em.createQuery(sql.toString());
        
        for (String key : parametros.keySet()) {
            q.setParameter(key, parametros.get(key));
        }
        return q.getResultList(); 
    }
    
    public  List<Rol> actualizar(List<Rol> lista){
        EntityManager em = super.getEmf().createEntityManager();
        for (Rol list : lista) {
            em.getTransaction().begin();
            em.refresh(list);
            em.flush(); 
            em.getTransaction().commit();
        }
        return lista;
    }
}
