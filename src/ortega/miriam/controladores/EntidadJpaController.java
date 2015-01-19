/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.controladores;

import facturacionmueblesdesktop.exceptions.IllegalOrphanException;
import facturacionmueblesdesktop.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ortega.miriam.entidades.Clientes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import ortega.miriam.entidades.Entidad;
import ortega.miriam.entidades.Proveedores;
import ortega.miriam.entidades.Usuarios;

/**
 *
 * @author macbookpro
 */
public class EntidadJpaController extends EntityManagerlocal  implements Serializable {

    public EntidadJpaController() {
        super();
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entidad entidad) {
        if (entidad.getClientesCollection() == null) {
            entidad.setClientesCollection(new ArrayList<Clientes>());
        }
        if (entidad.getProveedoresCollection() == null) {
            entidad.setProveedoresCollection(new ArrayList<Proveedores>());
        }
        if (entidad.getUsuariosCollection() == null) {
            entidad.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        EntityManager em = super.getEmf().createEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Clientes> attachedClientesCollection = new ArrayList<Clientes>();
            for (Clientes clientesCollectionClientesToAttach : entidad.getClientesCollection()) {
                clientesCollectionClientesToAttach = em.getReference(clientesCollectionClientesToAttach.getClass(), clientesCollectionClientesToAttach.getId());
                attachedClientesCollection.add(clientesCollectionClientesToAttach);
            }
            entidad.setClientesCollection(attachedClientesCollection);
            Collection<Proveedores> attachedProveedoresCollection = new ArrayList<Proveedores>();
            for (Proveedores proveedoresCollectionProveedoresToAttach : entidad.getProveedoresCollection()) {
                proveedoresCollectionProveedoresToAttach = em.getReference(proveedoresCollectionProveedoresToAttach.getClass(), proveedoresCollectionProveedoresToAttach.getId());
                attachedProveedoresCollection.add(proveedoresCollectionProveedoresToAttach);
            }
            entidad.setProveedoresCollection(attachedProveedoresCollection);
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : entidad.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getId());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            entidad.setUsuariosCollection(attachedUsuariosCollection);
            em.persist(entidad);
            for (Clientes clientesCollectionClientes : entidad.getClientesCollection()) {
                Entidad oldEntidadidOfClientesCollectionClientes = clientesCollectionClientes.getEntidadid();
                clientesCollectionClientes.setEntidadid(entidad);
                clientesCollectionClientes = em.merge(clientesCollectionClientes);
                if (oldEntidadidOfClientesCollectionClientes != null) {
                    oldEntidadidOfClientesCollectionClientes.getClientesCollection().remove(clientesCollectionClientes);
                    oldEntidadidOfClientesCollectionClientes = em.merge(oldEntidadidOfClientesCollectionClientes);
                }
            }
            for (Proveedores proveedoresCollectionProveedores : entidad.getProveedoresCollection()) {
                Entidad oldEntidadidOfProveedoresCollectionProveedores = proveedoresCollectionProveedores.getEntidadid();
                proveedoresCollectionProveedores.setEntidadid(entidad);
                proveedoresCollectionProveedores = em.merge(proveedoresCollectionProveedores);
                if (oldEntidadidOfProveedoresCollectionProveedores != null) {
                    oldEntidadidOfProveedoresCollectionProveedores.getProveedoresCollection().remove(proveedoresCollectionProveedores);
                    oldEntidadidOfProveedoresCollectionProveedores = em.merge(oldEntidadidOfProveedoresCollectionProveedores);
                }
            }
            for (Usuarios usuariosCollectionUsuarios : entidad.getUsuariosCollection()) {
                Entidad oldEntidadidOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getEntidadid();
                usuariosCollectionUsuarios.setEntidadid(entidad);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldEntidadidOfUsuariosCollectionUsuarios != null) {
                    oldEntidadidOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldEntidadidOfUsuariosCollectionUsuarios = em.merge(oldEntidadidOfUsuariosCollectionUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entidad entidad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad persistentEntidad = em.find(Entidad.class, entidad.getId());
            Collection<Clientes> clientesCollectionOld = persistentEntidad.getClientesCollection();
            Collection<Clientes> clientesCollectionNew = entidad.getClientesCollection();
            Collection<Proveedores> proveedoresCollectionOld = persistentEntidad.getProveedoresCollection();
            Collection<Proveedores> proveedoresCollectionNew = entidad.getProveedoresCollection();
            Collection<Usuarios> usuariosCollectionOld = persistentEntidad.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = entidad.getUsuariosCollection();
            List<String> illegalOrphanMessages = null;
            for (Clientes clientesCollectionOldClientes : clientesCollectionOld) {
                if (!clientesCollectionNew.contains(clientesCollectionOldClientes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clientes " + clientesCollectionOldClientes + " since its entidadid field is not nullable.");
                }
            }
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosCollectionOldUsuarios + " since its entidadid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Clientes> attachedClientesCollectionNew = new ArrayList<Clientes>();
            for (Clientes clientesCollectionNewClientesToAttach : clientesCollectionNew) {
                clientesCollectionNewClientesToAttach = em.getReference(clientesCollectionNewClientesToAttach.getClass(), clientesCollectionNewClientesToAttach.getId());
                attachedClientesCollectionNew.add(clientesCollectionNewClientesToAttach);
            }
            clientesCollectionNew = attachedClientesCollectionNew;
            entidad.setClientesCollection(clientesCollectionNew);
            Collection<Proveedores> attachedProveedoresCollectionNew = new ArrayList<Proveedores>();
            for (Proveedores proveedoresCollectionNewProveedoresToAttach : proveedoresCollectionNew) {
                proveedoresCollectionNewProveedoresToAttach = em.getReference(proveedoresCollectionNewProveedoresToAttach.getClass(), proveedoresCollectionNewProveedoresToAttach.getId());
                attachedProveedoresCollectionNew.add(proveedoresCollectionNewProveedoresToAttach);
            }
            proveedoresCollectionNew = attachedProveedoresCollectionNew;
            entidad.setProveedoresCollection(proveedoresCollectionNew);
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getId());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            entidad.setUsuariosCollection(usuariosCollectionNew);
            entidad = em.merge(entidad);
            for (Clientes clientesCollectionNewClientes : clientesCollectionNew) {
                if (!clientesCollectionOld.contains(clientesCollectionNewClientes)) {
                    Entidad oldEntidadidOfClientesCollectionNewClientes = clientesCollectionNewClientes.getEntidadid();
                    clientesCollectionNewClientes.setEntidadid(entidad);
                    clientesCollectionNewClientes = em.merge(clientesCollectionNewClientes);
                    if (oldEntidadidOfClientesCollectionNewClientes != null && !oldEntidadidOfClientesCollectionNewClientes.equals(entidad)) {
                        oldEntidadidOfClientesCollectionNewClientes.getClientesCollection().remove(clientesCollectionNewClientes);
                        oldEntidadidOfClientesCollectionNewClientes = em.merge(oldEntidadidOfClientesCollectionNewClientes);
                    }
                }
            }
            for (Proveedores proveedoresCollectionOldProveedores : proveedoresCollectionOld) {
                if (!proveedoresCollectionNew.contains(proveedoresCollectionOldProveedores)) {
                    proveedoresCollectionOldProveedores.setEntidadid(null);
                    proveedoresCollectionOldProveedores = em.merge(proveedoresCollectionOldProveedores);
                }
            }
            for (Proveedores proveedoresCollectionNewProveedores : proveedoresCollectionNew) {
                if (!proveedoresCollectionOld.contains(proveedoresCollectionNewProveedores)) {
                    Entidad oldEntidadidOfProveedoresCollectionNewProveedores = proveedoresCollectionNewProveedores.getEntidadid();
                    proveedoresCollectionNewProveedores.setEntidadid(entidad);
                    proveedoresCollectionNewProveedores = em.merge(proveedoresCollectionNewProveedores);
                    if (oldEntidadidOfProveedoresCollectionNewProveedores != null && !oldEntidadidOfProveedoresCollectionNewProveedores.equals(entidad)) {
                        oldEntidadidOfProveedoresCollectionNewProveedores.getProveedoresCollection().remove(proveedoresCollectionNewProveedores);
                        oldEntidadidOfProveedoresCollectionNewProveedores = em.merge(oldEntidadidOfProveedoresCollectionNewProveedores);
                    }
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    Entidad oldEntidadidOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getEntidadid();
                    usuariosCollectionNewUsuarios.setEntidadid(entidad);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldEntidadidOfUsuariosCollectionNewUsuarios != null && !oldEntidadidOfUsuariosCollectionNewUsuarios.equals(entidad)) {
                        oldEntidadidOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldEntidadidOfUsuariosCollectionNewUsuarios = em.merge(oldEntidadidOfUsuariosCollectionNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = entidad.getId();
                if (findEntidad(id) == null) {
                    throw new NonexistentEntityException("The entidad with id " + id + " no longer exists.");
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
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidad;
            try {
                entidad = em.getReference(Entidad.class, id);
                entidad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entidad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Clientes> clientesCollectionOrphanCheck = entidad.getClientesCollection();
            for (Clientes clientesCollectionOrphanCheckClientes : clientesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entidad (" + entidad + ") cannot be destroyed since the Clientes " + clientesCollectionOrphanCheckClientes + " in its clientesCollection field has a non-nullable entidadid field.");
            }
            Collection<Usuarios> usuariosCollectionOrphanCheck = entidad.getUsuariosCollection();
            for (Usuarios usuariosCollectionOrphanCheckUsuarios : usuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entidad (" + entidad + ") cannot be destroyed since the Usuarios " + usuariosCollectionOrphanCheckUsuarios + " in its usuariosCollection field has a non-nullable entidadid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Proveedores> proveedoresCollection = entidad.getProveedoresCollection();
            for (Proveedores proveedoresCollectionProveedores : proveedoresCollection) {
                proveedoresCollectionProveedores.setEntidadid(null);
                proveedoresCollectionProveedores = em.merge(proveedoresCollectionProveedores);
            }
            em.remove(entidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entidad> findEntidadEntities() {
        return findEntidadEntities(true, -1, -1);
    }

    public List<Entidad> findEntidadEntities(int maxResults, int firstResult) {
        return findEntidadEntities(false, maxResults, firstResult);
    }

    private List<Entidad> findEntidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entidad.class));
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

    public Entidad findEntidad(Long id) {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            return em.find(Entidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntidadCount() {
        EntityManager em = super.getEmf().createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entidad> rt = cq.from(Entidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
