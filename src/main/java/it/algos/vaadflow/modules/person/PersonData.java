package it.algos.vaadflow.modules.person;

import it.algos.vaadflow.backend.data.AData;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.company.Company;
import it.algos.vaadflow.modules.company.CompanyService;
import it.algos.vaadflow.modules.company.EACompany;
import it.algos.vaadflow.service.IAService;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import static it.algos.vaadflow.application.FlowCost.TAG_COM;
import static it.algos.vaadflow.application.FlowCost.TAG_PER;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 02-set-2018
 * Time: 15:08
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class PersonData extends AData {

    /**
     * Il service viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private PersonService service;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param service di collegamento per la Repository
     */
    @Autowired
    public PersonData(@Qualifier(TAG_PER) IAService service) {
        super(Person.class, service);
        this.service = (PersonService) service;
    }// end of Spring constructor


    /**
     * Metodo invocato da ABoot <br>
     * <p>
     * Creazione di una collezione - Solo se non ci sono records
     */
    public void loadData() {
        int numRec = super.count();

        if (numRec == 0) {
            this.crea();
            log.warn("Algos - Creazione dati iniziali PersonData.inizia(): " + numRec + " schede");
        } else {
            log.info("Algos - Data. La collezione Person è presente: " + numRec + " schede");
        }// end of if/else cycle
    }// end of method


    /**
     * Creazione della collezione
     */
    private void crea() {
        String code;
        String descrizione;
        Person contatto;
        String telefono;
        String email;
        Address indirizzo;

        for (EACompany company : EACompany.values()) {
            code = company.getCode();
            descrizione = company.getDescrizione();
            telefono = company.getTelefono();
            email = company.getEmail();

//            service.crea(code,  descrizione,null, telefono, email, null);
        }// end of for cycle
    }// end of method

}// end of class
