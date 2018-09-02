package it.algos.vaadflow.modules.company;

import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.address.AddressService;
import it.algos.vaadflow.modules.person.Person;
import it.algos.vaadflow.modules.person.PersonService;
import it.algos.vaadflow.service.AAnnotationService;
import it.algos.vaadflow.service.ATextService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 02-set-2018
 * Time: 09:15
 */
public enum EACompany {

    algos("algos", "Company ALGOS di prova", "02.453677", "gac@algos.it"),
    demo("demo", "Company demo", "338 678932", "gates@win.com"),
    test("test", "Company di test", "345 786631", "alfa@libero.it");


    private String code;
    private String descrizione;
    private String telefono;
    private String email;


    EACompany(String code, String descrizione, String telefono, String email) {
        this.setCode(code);
        this.setDescrizione(descrizione);
        this.setTelefono(telefono);
        this.setEmail(email);
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
