package it.algos.vaadflow.modules.utente;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.backend.data.AData;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.security.SecurityConfiguration;
import it.algos.vaadflow.service.IAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static it.algos.vaadflow.application.FlowCost.TAG_UTE;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 14-set-2018
 * Time: 15:34
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class UtenteData extends AData {


    /**
     * Il service viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private UtenteService service;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param service di collegamento per la Repository
     */
    @Autowired
    public UtenteData(@Qualifier(TAG_UTE) IAService service) {
        super(Utente.class, service);
        this.service = (UtenteService) service;
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
            log.warn("Algos - Creazione dati iniziali UtenteData.loadData(): " + numRec + " schede");
        } else {
            log.info("Algos - Data. La collezione Utente è presente: " + numRec + " schede");
        }// end of if/else cycle
    }// end of method


    /**
     * Creazione della collezione
     */
    private int creaAll() {
        int num = 0;
        String userName;
        String passwordInChiaro;
        List<Role> ruoli;
        String mail;

        for (EAUtente utente : EAUtente.values()) {
            userName = utente.getUserName();
            passwordInChiaro = utente.getPasswordInChiaro();
            ruoli = utente.getRuoli();
            mail = utente.getMail();

            service.crea(userName, passwordInChiaro, ruoli, mail);
            num++;
        }// end of for cycle

        return num;
    }// end of method


}// end of class
