/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linepack.enviosmstelnet.controller;

import org.linepack.enviosmstelnet.Main;
import org.linepack.enviosmstelnet.model.SmsTelnet;

/**
 *
 * @author Giovana
 */
public class SmsTelnetController {

    public static SmsTelnet query(Integer id) {
        SmsTelnet sms = Main.emMysql.find(SmsTelnet.class, id);
        if (sms != null) {
            Main.emMysql.refresh(sms);
        }
        return sms;
    }

    public static Integer insert(SmsTelnet sms) {

        Main.emMysql.persist(sms);
        if (!Main.emMysql.getTransaction().isActive()) {
            Main.emMysql.getTransaction().begin();
        }
        Main.emMysql.getTransaction().commit();

        return sms.getId();

    }

}
