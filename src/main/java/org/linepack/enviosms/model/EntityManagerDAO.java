/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linepack.enviosms.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Giovana
 */
public class EntityManagerDAO {
    
    
    public static EntityManager getEntityManager(String banco){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(banco);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        
        return em;
    }
    
}
