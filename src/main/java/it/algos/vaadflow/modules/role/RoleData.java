package it.algos.vaadflow.modules.role;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.backend.data.AData;
import it.algos.vaadflow.service.AAnnotationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
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
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class RoleData extends AData {


    /**
     * Service iniettato da Spring nel costruttore.
     */
    public RoleService service;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param mongo      service per le operazioni su mongoDB
     * @param repository per la persistenza dei dati
     * @param annotation service per le @Annotation
     * @param service    di collegamento per la Repository
     */
    @Autowired
    public RoleData(MongoOperations mongo,
                    @Qualifier(TAG_ROL) MongoRepository repository,
                    AAnnotationService annotation,
                    RoleService service) {
        super(mongo, repository, annotation);
        this.service = service;
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
//    @PostConstruct
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
        for (EARole ruolo : EARole.values()) {
            service.findOrCrea(ruolo.toString());
        }// end of for cycle
    }// end of method


}// end of class
