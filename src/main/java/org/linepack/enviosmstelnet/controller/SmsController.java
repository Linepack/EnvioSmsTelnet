/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linepack.enviosmstelnet.controller;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.linepack.enviosmstelnet.model.EntityManagerDAO;
import org.linepack.enviosmstelnet.model.Sms;
import org.linepack.enviosmstelnet.model.SmsTelnet;

/**
 *
 * @author Giovana
 */
public class SmsController {

    public static Sms query(Integer id) {

        EntityManager manager = EntityManagerDAO.getEntityManager("Oracle");
        Sms sms = manager.find(Sms.class, id);
        manager.close();

        return sms;
    }

    private static void update(SmsTelnet smsMysql, Sms smsOracle, EntityManager manager) {       
        
        if (smsMysql.getStatus().equals("ENVIADO")) {
            smsOracle.setStatus(1);
        }
        
        if(smsMysql.getErro() != null){
            smsOracle.setErro(smsMysql.getErro());
        }
        
        manager.merge(smsOracle);       
    }
    
    
    public static void updateNotSend(){
        
        EntityManager manager = EntityManagerDAO.getEntityManager("Oracle");        
        Query query = manager.createQuery("select s from GESMS s where s.status = 0");                 
        
        for (Object smsObject : query.getResultList()){
            
            Sms smsOracle = new Sms();
            smsOracle = (Sms) smsObject;            
            SmsTelnet smsMysql = SmsTelnetController.query(smsOracle.getId());
            SmsController.update(smsMysql, smsOracle, manager);
        } 
        
        manager.getTransaction().commit();                
        manager.close();
        
    }

}
