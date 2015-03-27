/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linepack.enviosmstelnet.controller;

import javax.persistence.EntityManager;
import org.linepack.enviosmstelnet.model.EntityManagerDAO;
import org.linepack.enviosmstelnet.model.SmsTelnet;

/**
 *
 * @author Giovana
 */
public class SmsTelnetController {

    public static SmsTelnet query(Integer id) {

        EntityManager manager = EntityManagerDAO.getEntityManager("Mysql");
        SmsTelnet sms = manager.find(SmsTelnet.class, id);
        manager.close();

        return sms;
    }

    public static Integer insert(SmsTelnet sms) {

        EntityManager manager = EntityManagerDAO.getEntityManager("Mysql");
        manager.persist(sms);
        manager.getTransaction().commit();

        return sms.getId();

    }

}
