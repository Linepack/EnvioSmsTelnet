/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linepack.enviosmstelnet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.linepack.enviosmstelnet.Main;
import org.linepack.enviosmstelnet.model.Sms;
import org.linepack.enviosmstelnet.model.SmsTelnet;

/**
 *
 * @author Giovana
 */
public class SmsController {

    private static Logger log = Logger.getLogger(SmsController.class);

    public static List<Sms> getSmsNotSend() {

        List<Sms> smsList = new ArrayList<>();

        Query query = Main.emOracle.createQuery("select s"
                + " from GESMS s"
                + " where s.status = 0"
                + " and s.numeroDeTentativas < 6"
                + " and s.erro is null"
                + " order by s.id asc");
        for (Object smsObject : query.getResultList()) {
            smsList.add((Sms) smsObject);
        }

        return smsList;
    }

    private static void update(SmsTelnet smsMysql, Sms smsOracle) {

        if (smsMysql.getStatus().equals("ENVIADO")) {
            smsOracle.setStatus(1);
            log.info("SMS " + smsOracle.getId() + " enviado com sucesso.");
        }

        if (smsMysql.getErro() != null) {
            smsOracle.setErro(smsMysql.getErro());
            log.info("SMS " + smsOracle.getId() + " com falha: " + smsOracle.getErro());
        }

        smsOracle.setNumeroDeTentativas(smsMysql.getNumeroDeFalhas() != null ? smsMysql.getNumeroDeFalhas() : 1);
        if (smsOracle.getNumeroDeTentativas() >= 6) {
            log.info("SMS " + smsOracle.getId() + " com número de tentativas esgotado: " + smsOracle.getNumeroDeTentativas());
        }

        smsOracle.setUsuarioAlteracao("JAVA");
        smsOracle.setDataAlteracao(new Date());
        smsOracle.setMensagem(smsMysql.getMensagem());

        Main.emOracle.merge(smsOracle);
    }

    public static void updateNotSend(SmsTelnet smsTelnet) {

        Query query = Main.emOracle.createQuery("select s from GESMS s where s.id = " + smsTelnet.getId());
        for (Object smsObject : query.getResultList()) {
            Sms smsOracle = (Sms) smsObject;
            SmsController.update(smsTelnet, smsOracle);
            
            if (!Main.emOracle.getTransaction().isActive()) {
                Main.emOracle.getTransaction().begin();
            }
            Main.emOracle.getTransaction().commit();
        }

    }

}
