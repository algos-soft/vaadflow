package it.algos.vaadflow.modules.role;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.backend.data.AData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.annotation.PostConstruct;

import static it.algos.vaadflow.application.FlowCost.TAG_ROL;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: dom, 12-nov-2017
 * Time: 14:54
 * <p>
 * Estende la classe astratta AData per la costruzione inziale della Collection <br>
 * <p>
 * I valori iniziali sono presi da una Enumeration codificata e standard <br>
 * Vengono caricati sul DB (mongo) in modo che se ne possano aggiungere altri specifici per l'applicazione<br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio per le injections) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class RoleData extends AData {


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public RoleData(@Qualifier(TAG_ROL) MongoRepository repository) {
        super(repository);
    }// end of Spring constructor


    /**
     * Metodo invocato subito DOPO il costruttore
     * <p>
     * Performing the initialization in a constructor is not suggested
     * as the state of the UI is not properly set up when the constructor is invoked.
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti,
     * ma l'ordine con cui vengono chiamati NON è garantito
     * <p>
     * Creazione di una collezione - Solo se non ci sono records
     */
    @PostConstruct
    public void inizia() {
        super.collectionName = annotation.getCollectionName(Role.class);
        int numRec = super.count();

        if (numRec == 0) {
            this.crea();
            log.warn("Algos - Creazione dati iniziali ADataGenerator(@PostConstruct).loadData() -> roleData.loadData(): " + numRec + " schede");
        } else {
            log.info("Algos - Data. La collezione Role è presente: " + numRec + " schede");
        }// end of if/else cycle
    }// end of method


    /**
     * Creazione della collezione
     */
    public void crea() {
        int pos = 1;

        for (EARole ruolo : EARole.values()) {
            Role entity = Role.builder()
                    .ordine(pos++)
                    .code(ruolo.toString())
                    .build();
            super.crea(entity, entity.getCode());
        }// end of for cycle
    }// end of method


}// end of class
