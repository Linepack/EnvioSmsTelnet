/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linepack.enviosmstelnet;

import java.io.IOException;
import java.sql.Time;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import org.linepack.enviosmstelnet.controller.SmsController;
import org.linepack.enviosmstelnet.controller.SmsTelnetController;
import org.linepack.enviosmstelnet.model.EntityManagerDAO;
import org.linepack.enviosmstelnet.model.Sms;
import org.linepack.enviosmstelnet.model.SmsTelnet;

/**
 *
 * @author Giovana
 */
public class Main {

    private static Logger log = Logger.getLogger(Main.class);
    public static EntityManager emOracle;
    public static EntityManager emMysql;

    public static void main(String[] args) throws IOException {

        Timer timer = null;
        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa;
            tarefa = new TimerTask() {

                @Override
                public void run() {

                    List<Sms> smsList = new ArrayList<>();

                    emOracle = EntityManagerDAO.getEntityManager("Oracle");
                    emMysql = EntityManagerDAO.getEntityManager("Mysql");

                    smsList.addAll(SmsController.getSmsNotSend());

                    for (Sms sms : smsList) {
                        enviaSms(sms);
                    }
                }
            };
            timer.scheduleAtFixedRate(tarefa, 5000, 5000);
        }
    }

    public static void enviaSms(Sms smsOracle) {

        SmsTelnet smsTelnet = new SmsTelnet();

        try {
            smsTelnet = SmsTelnetController.query(smsOracle.getId());
        } catch (NullPointerException ex) {
        }

        if (smsTelnet != null) {
            SmsController.updateNotSend(smsTelnet);
            return;
        }

        SmsTelnet smsMysql = new SmsTelnet();
        smsMysql.setTelefone(smsOracle.getTelefone());
        smsMysql.setMensagem(removerAcentos(smsOracle.getMensagem()));
        smsMysql.setStatus("EM FILA");
        smsMysql.setId(smsOracle.getId());
        smsMysql.setHoraInicial(stringToTime(smsOracle.getHoraInicio()));
        smsMysql.setHoraFinal(stringToTime(smsOracle.getHoraFim()));
        SmsTelnetController.insert(smsMysql);
        log.info("SMS " + smsOracle.getId() + " cadastrado no sistema TELNET.");
    }

    private static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    private static Time stringToTime(String horaString) {
        String[] horaStringArray = horaString.split(":");
        Time horaTime = new Time(Integer.parseInt(horaStringArray[0]), Integer.parseInt(horaStringArray[1]), Integer.parseInt(horaStringArray[2]));
        return horaTime;
    }

}
