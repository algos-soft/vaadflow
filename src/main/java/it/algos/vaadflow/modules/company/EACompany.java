package it.algos.vaadflow.modules.company;

import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.person.Person;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 02-set-2018
 * Time: 09:15
 */
public enum EACompany {

    algos("algos", "Comapny ALGOS di prova", null, "02.453677", "gac@algos.it", null),
    demo("demo", "Company demo", null, "338 678932", "gates@win.com", null),
    test("test", "Company di test", null, "345 786631", "alfa@libero.it", null);


    private String code;
    private String descrizione;
    private Person contatto;
    private String telefono;
    private String email;
    private Address indirizzo;


    EACompany(String code, String descrizione, Person contatto, String telefono, String email, Address indirizzo) {
        this.setCode(code);
        this.setDescrizione(descrizione);
        this.setContatto(contatto);
        this.setTelefono(telefono);
        this.setEmail(email);
        this.setIndirizzo(indirizzo);
    }// fine del costruttore


    public String getCode() {
        return code;
    }// end of method

    public void setCode(String code) {
        this.code = code;
    }// end of method

    public String getDescrizione() {
        return descrizione;
    }// end of method

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }// end of method

    public Person getContatto() {
        return contatto;
    }// end of method

    public void setContatto(Person contatto) {
        this.contatto = contatto;
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

    public Address getIndirizzo() {
        return indirizzo;
    }// end of method

    public void setIndirizzo(Address indirizzo) {
        this.indirizzo = indirizzo;
    }// end of method

}// end of enumeration class
