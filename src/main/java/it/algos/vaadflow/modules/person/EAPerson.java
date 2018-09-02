package it.algos.vaadflow.modules.person;

import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 02-set-2018
 * Time: 15:51
 */
public enum EAPerson {
    algos("Mario", "Rossi", "06.785556", "assurbanipal@gmail.com.it"),
    demo("demo", "Company demo", "338 678932", "gates@win.com"),
    test("test", "Company di test", "345 786631", "alfa@libero.it");


    private String nome;
    private String cognome;
    private String telefono;
    private String email;


    EAPerson(String nome, String cognome, String telefono, String email) {
        this.setNome(nome);
        this.setCognome(cognome);
        this.setTelefono(telefono);
        this.setEmail(email);
    }// fine del costruttore


    public String getNome() {
        return nome;
    }// end of method

    public void setNome(String nome) {
        this.nome = nome;
    }// end of method

    public String getCognome() {
        return cognome;
    }// end of method

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }// end of method

    public String getTelefono() {
        return telefono;
    }// end of method

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }// end of method

    public String getEmail() {
        return email;
    }// end of method

    public void setEmail(String email) {
        this.email = email;
    }// end of method

}// end of enumeration class
