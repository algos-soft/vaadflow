package it.algos.vaadflow.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.service.AAnnotationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Project vbase
 * Created by Algos
 * User: gac
 * Date: lun, 19-mar-2018
 * Time: 21:10
 * <p>
 * Superclasse astratta per la costruzione inziale delle Collections <br>
 * Viene invocata PRIMA della chiamata del browser, tramite un metodo @PostConstruct della sottoclasse <br>
 * Non si possono quindi usare i service specifici dei package che sono @UIScope <br>
 * Viceversa le repository specifiche dei package sono delle interfacce e pertanto vengono 'create' al volo <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio per le injections) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public abstract class AData {


    /**
     * L'istanza viene dichiarata nel costruttore @Autowired della sottoclasse concreta <br>
     */
    protected MongoOperations mongo;


    /**
     * L'istanza viene dichiarata nel costruttore @Autowired della sottoclasse concreta <br>
     */
    protected MongoRepository repository;


    /**
     * Istanza di classe con @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON), iniettata da Spring <br>
     * L'istanza viene dichiarata nel costruttore @Autowired della sottoclasse concreta <br>
     * Service usato come libreria <br>
     */
    protected AAnnotationService annotation;

    /**
     * Nome della collezione su mongoDB <br>
     * Viene regolato dalla sottoclasse nel costruttore <br>
     */
    protected String collectionName;


    public AData() {
    }// end of Spring constructor

    /**
     * Costruttore @Autowired (nella sottoclasse concreta) <br>
     * La sottoclasse usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * La sottoclasse usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param mongo      service per le operazioni su mongoDB
     * @param repository per la persistenza dei dati
     * @param annotation service per le @Annotation
     */
    public AData(MongoOperations mongo, MongoRepository repository, AAnnotationService annotation) {
        this.mongo = mongo;
        this.repository = repository;
        this.annotation = annotation;
    }// end of Spring constructor


    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    public int count() {
        return (int) repository.count();
    }// end of method


    /**
     * Controlla se la collezione esiste già
     *
     * @return true se la collection è inesistente
     */
    protected boolean nessunRecordEsistente() {
        return this.count() == 0;
    }// end of method


    /**
     * Creazione di una entity
     */
    public void crea(AEntity entity) {
        crea(entity, "");
    }// end of method


    /**
     * Creazione di una entity
     */
    public void crea(AEntity entity, String keyID) {
        entity.id = keyID;
        mongo.insert(entity, collectionName);
    }// end of method


    /**
     * Creazione di una collezione
     * Solo se non ci sono records
     * Controlla se la collezione esiste già
     * Creazione della collezione
     */
    protected void loadData() {
    }// end of method


}// end of class
