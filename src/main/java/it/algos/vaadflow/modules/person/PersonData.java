package it.algos.vaadflow.modules.person;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.backend.data.AData;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.address.AddressService;
import it.algos.vaadflow.modules.address.EAAddress;
import it.algos.vaadflow.service.IAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

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
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    protected AddressService address;


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
            numRec = creaAll();
            log.warn("Algos - Creazione dati iniziali PersonData.inizia(): " + numRec + " schede");
        } else {
            log.info("Algos - Data. La collezione Person è presente: " + numRec + " schede");
        }// end of if/else cycle
    }// end of method


    /**
     * Creazione della collezione
     */
    private int creaAll() {
        int num = 0;
        String nome;
        String cognome;
        String telefono;
        String email;
        EAAddress eaAddress;
        Address indirizzo = null;

        for (EAPerson persona : EAPerson.values()) {
            nome = persona.getNome();
            cognome = persona.getCognome();
            telefono = persona.getTelefono();
            email = persona.getEmail();
            eaAddress = persona.getAddress();
            if (eaAddress != null) {
                indirizzo = creaAddress(eaAddress);
            } else {
                indirizzo = null;
            }// end of if/else cycle

            service.crea(nome, cognome, telefono, email, indirizzo);
            num++;
        }// end of for cycle

        return num;
    }// end of method

    /**
     * Creazione del singolo indirizzo
     */
    private Address creaAddress(EAAddress eaAddress) {
        String indirizzo = "";
        String localita = "";
        String cap = "";

        indirizzo = eaAddress.getIndirizzo();
        localita = eaAddress.getLocalita();
        cap = eaAddress.getCap();

        return address.newEntity(indirizzo, localita, cap);
    }// end of method

}// end of class
