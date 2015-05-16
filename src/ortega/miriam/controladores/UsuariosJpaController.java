/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.controladores;
 

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ortega.miriam.controladores.exceptions.NonexistentEntityException;
import ortega.miriam.entidades.Rol;
import ortega.miriam.entidades.Entidad;
import ortega.miriam.entidades.Usuarios;

/**
 *
 * @author macbookpro
 */
public class UsuariosJpaController extends EntityManagerlocal implements Serializable {

    public UsuariosJpaController() {
        super();
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rolid = usuarios.getRolid();
            if (rolid != null) {
                rolid = em.getReference(rolid.getClass(), rolid.getId());
                usuarios.setRolid(rolid);
            }
            Entidad entidadid = usuarios.getEntidadid();
            if (entidadid != null) {
                entidadid = em.getReference(entidadid.getClass(), entidadid.getId());
                usuarios.setEntidadid(entidadid);
            }
            em.persist(usuarios);
            if (rolid != null) {
                rolid.getUsuariosCollection().add(usuarios);
                rolid = em.merge(rolid);
            }
            if (entidadid != null) {
                entidadid.getUsuariosCollection().add(usuarios);
                entidadid = em.merge(entidadid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            Rol rolidOld = persistentUsuarios.getRolid();
            Rol rolidNew = usuarios.getRolid();
            Entidad entidadidOld = persistentUsuarios.getEntidadid();
            Entidad entidadidNew = usuarios.getEntidadid();
            if (rolidNew != null) {
                rolidNew = em.getReference(rolidNew.getClass(), rolidNew.getId());
                usuarios.setRolid(rolidNew);
            }
            if (entidadidNew != null) {
                entidadidNew = em.getReference(entidadidNew.getClass(), entidadidNew.getId());
                usuarios.setEntidadid(entidadidNew);
            }
            usuarios = em.merge(usuarios);
            if (rolidOld != null && !rolidOld.equals(rolidNew)) {
                rolidOld.getUsuariosCollection().remove(usuarios);
                rolidOld = em.merge(rolidOld);
            }
            if (rolidNew != null && !rolidNew.equals(rolidOld)) {
                rolidNew.getUsuariosCollection().add(usuarios);
                rolidNew = em.merge(rolidNew);
            }
            if (entidadidOld != null && !entidadidOld.equals(entidadidNew)) {
                entidadidOld.getUsuariosCollection().remove(usuarios);
                entidadidOld = em.merge(entidadidOld);
            }
            if (entidadidNew != null && !entidadidNew.equals(entidadidOld)) {
                entidadidNew.getUsuariosCollection().add(usuarios);
                entidadidNew = em.merge(entidadidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Rol rolid = usuarios.getRolid();
            if (rolid != null) {
                rolid.getUsuariosCollection().remove(usuarios);
                rolid = em.merge(rolid);
            }
            Entidad entidadid = usuarios.getEntidadid();
            if (entidadid != null) {
                entidadid.getUsuariosCollection().remove(usuarios);
                entidadid = em.merge(entidadid);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    
    public Usuarios find(String username, String password, String rol){
        EntityManager em = super.getEmf().createEntityManager();
        Query q=em.createNamedQuery("Usuarios.findByUsername");
        q.setParameter("username", username.toLowerCase());
        q.setParameter("password", password.toLowerCase());
        q.setParameter("rol", rol.toLowerCase());
        return (Usuarios)q.getSingleResult();
    }
    public Usuarios findUsuarios(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
