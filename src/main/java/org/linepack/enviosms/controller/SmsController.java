/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linepack.enviosms.controller;

import javax.persistence.EntityManager;
import org.linepack.enviosms.model.EntityManagerDAO;
import org.linepack.enviosms.model.Sms;
import org.linepack.enviosms.model.SmsTelnet;

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

    public static void update(SmsTelnet smsMysql, Sms smsOracle) {

        EntityManager manager = EntityManagerDAO.getEntityManager("Oracle");
        
        if (smsMysql.getStatus() == "ENVIADO") {
            smsOracle.setStatus(1);
        }
        
        if(smsMysql.getErro() == null){
            smsOracle.setErro(smsMysql.getErro());
        }
        
        manager.merge(smsOracle);
        manager.getTransaction().commit();
        
        manager.close();

    }

}
