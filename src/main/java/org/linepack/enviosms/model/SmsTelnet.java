/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.linepack.enviosms.model;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Giovana
 */
@Entity(name = "sms_outbox")
@Table(name = "sms_outbox")
public class SmsTelnet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String telefone;

    private String mensagem;

    @Column(name = "hora_ini")
    private Time horaInicial;

    @Column(name = "hora_fim")
    private Time horaFinal;

    private String status;

    @Column(name = "ultima_falha")
    private String erro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Time getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Time horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Time getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SmsTelnet)) {
            return false;
        }
        SmsTelnet other = (SmsTelnet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.linepack.enviosms.model.SmsTelnet[ id=" + id + " ]";
    }

}
