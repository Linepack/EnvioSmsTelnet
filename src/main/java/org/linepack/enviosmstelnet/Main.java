/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linepack.enviosmstelnet;

import java.sql.Time;
import org.linepack.enviosmstelnet.controller.SmsController;
import org.linepack.enviosmstelnet.controller.SmsTelnetController;
import org.linepack.enviosmstelnet.model.Sms;
import org.linepack.enviosmstelnet.model.SmsTelnet;

/**
 *
 * @author Giovana
 */
public class Main {

    public static void main(String[] args) {

        Integer sequenciaSmsOracle = null;
        try {
            sequenciaSmsOracle = Integer.parseInt(args[0]);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }        
          
        Sms smsOracle = SmsController.query(sequenciaSmsOracle);
        
        SmsTelnet smsMysql = new SmsTelnet();
        smsMysql.setTelefone(smsOracle.getTelefone());
        smsMysql.setMensagem(smsOracle.getMensagem());
        smsMysql.setStatus("EM FILA");
        smsMysql.setId(sequenciaSmsOracle);
        
        Time dataInicial = new Time(0, 0, 0);
        smsMysql.setHoraInicial(dataInicial);
        
        Time dataFinal = new Time(23, 59, 0);
        smsMysql.setHoraFinal(dataFinal);
        
        SmsTelnetController.insert(smsMysql);        
    }

}
