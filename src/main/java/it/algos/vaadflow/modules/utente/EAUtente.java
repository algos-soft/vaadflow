package it.algos.vaadflow.modules.utente;

import it.algos.vaadflow.modules.role.Role;

import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 14-set-2018
 * Time: 15:39
 */
public enum EAUtente {
    uno("gac", "fulvia", null, "gac@algos.it"),
    due("alex", "axel01", null, "alex@algos.it");

    public String userName;
    public String passwordInChiaro;
    public List<Role> ruoli;
    public String mail;

    EAUtente(String userName, String passwordInChiaro, List<Role> ruoli, String mail) {
        this.setUserName(userName);
        this.setPasswordInChiaro(passwordInChiaro);
        this.setRuoli(ruoli);
        this.setMail(mail);
    }// fine del costruttore

    public String getUserName() {
        return userName;
    }// end of method

    public void setUserName(String userName) {
        this.userName = userName;
    }// end of method

    public String getPasswordInChiaro() {
        return passwordInChiaro;
    }// end of method

    public void setPasswordInChiaro(String passwordInChiaro) {
        this.passwordInChiaro = passwordInChiaro;
    }// end of method

    public List<Role> getRuoli() {
        return ruoli;
    }// end of method

    public void setRuoli(List<Role> ruoli) {
        this.ruoli = ruoli;
    }// end of method

    public String getMail() {
        return mail;
    }// end of method

    public void setMail(String mail) {
        this.mail = mail;
    }// end of method

}// end of enumeration class
