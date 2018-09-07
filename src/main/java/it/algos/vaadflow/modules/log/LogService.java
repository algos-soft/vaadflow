package it.algos.vaadflow.modules.log;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static it.algos.vaadflow.application.FlowCost.TAG_LOG;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 3-set-2018 20.32.35 <br>
 * <br>
 * Estende la classe astratta AService. Layer di collegamento per la Repository. <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Service (ridondante) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_LOG)
@Slf4j
@AIScript(sovrascrivibile = false)
public class LogService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private LogRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola nella superclasse il modello-dati specifico <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public LogService(@Qualifier(TAG_LOG) MongoRepository repository) {
        super(repository);
        super.entityClass = Log.class;
        this.repository = (LogRepository) repository;
    }// end of Spring constructor

    /**
     * Crea una entity e la registra <br>
     *
     * @param livello     di riferimento (obbligatorio)
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (facoltativa, non unica)
     *
     * @return la entity appena creata
     */
    public Log crea(Livello livello, String code, String descrizione) {
        Log entity = newEntity(livello, code, descrizione);
        save(entity);
        return entity;
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Log newEntity() {
        return newEntity(Livello.info, "", "");
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
     *
     * @param livello     di riferimento (obbligatorio)
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (facoltativa, non unica)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Log newEntity(Livello livello, String code, String descrizione) {
        Log entity = null;

        entity = findByKeyUnica(code);
        if (entity != null) {
            return findByKeyUnica(code);
        }// end of if cycle

        entity = Log.builderLog()
                .livello(livello != null ? livello : Livello.info)
                .code(code.equals("") ? null : code)
                .descrizione(descrizione.equals("") ? null : descrizione)
                .build();

        return (Log)creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Log findByKeyUnica(String code) {
        return repository.findByCode(code);
    }// end of method

    /**
     * Property unica (se esiste) <br>
     */
    @Override
    public String getPropertyUnica(AEntity entityBean) {
        return ((Log) entityBean).getCode();
    }// end of method

    //--registra un avviso
    public void debug(String code, String descrizione) {
        createBase(Livello.debug, code, descrizione);
    }// fine del metodo

    //--registra un avviso
    public void info(String code, String descrizione) {
        createBase(Livello.info, code, descrizione);
    }// fine del metodo

    //--registra un avviso
    public void warning(String code, String descrizione) {
        createBase(Livello.warn, code, descrizione);
    }// fine del metodo

    //--registra un avviso
    public void error(String code, String descrizione) {
        createBase(Livello.error, code, descrizione);
    }// fine del metodo

    //--registra un evento generico
    private void createBase(Livello livello, String code, String descrizione) {
        crea(livello,code,descrizione);
    }// fine del metodo statico

}// end of class