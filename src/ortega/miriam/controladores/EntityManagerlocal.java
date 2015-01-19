/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortega.miriam.controladores;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author macbookpro
 */
public class EntityManagerlocal {
    protected EntityManagerFactory emf; 
    protected EntityManager em;
    
    public EntityManagerlocal() {
        emf = Persistence.createEntityManagerFactory("facturacionMueblesDesktopPU");
        em = emf.createEntityManager();
    }
    
    

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    
    
}
