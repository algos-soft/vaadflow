package it.algos.vaadflow.modules.person;

import it.algos.vaadflow.modules.address.EAAddress;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 02-set-2018
 * Time: 15:51
 */
public enum EAPerson {
    uno("Mario", "Rossi", "06.785556", "assurbanipal@gmail.com.it", EAAddress.uno),
    due("Aldo", "Guzzini", "338 678932", "gates@win.com", null),
    tre("Lucia", "Portella", "345 786631", "alfa@libero.it",null);


    private String nome;
    private String cognome;
    private String telefono;
    private String email;
    private EAAddress address;


    EAPerson(String nome, String cognome, String telefono, String email, EAAddress address) {
        this.setNome(nome);
        this.setCognome(cognome);
        this.setTelefono(telefono);
        this.setEmail(email);
        this.setAddress(address);
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

    public EAAddress getAddress() {
        return address;
    }

    public void setAddress(EAAddress address) {
        this.address = address;
    }
}// end of enumeration class
