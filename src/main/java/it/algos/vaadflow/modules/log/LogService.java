package it.algos.vaadflow.modules.log;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.application.AContext;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.logtype.Logtype;
import it.algos.vaadflow.modules.logtype.LogtypeService;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.TAG_LOG;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-ott-2018 18.52.54 <br>
 * <br>
 * Business class. Layer di collegamento per la Repository. <br>
 * <br>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @VaadinSessionScope (obbligatorio) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (sbagliato) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@Service
@VaadinSessionScope
@Qualifier(TAG_LOG)
@Slf4j
@AIScript(sovrascrivibile = false)
public class LogService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    protected LogtypeService logtype;


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
     * @param descrizione (obbligatoria, non unica) <br>
     *
     * @return la entity appena creata
     */
    public Log crea(String descrizione) {
        Log entity = newEntity(descrizione);
        save(entity);
        return entity;
    }// end of method

    /**
     * Crea una entity e la registra <br>
     *
     * @param livello     rilevanza del log (obbligatorio)
     * @param type        raggruppamento logico dei log per type di eventi (obbligatorio)
     * @param descrizione (obbligatoria, non unica) <br>
     *
     * @return la entity appena creata
     */
    public Log crea(Livello livello, Logtype type, String descrizione) {
        Log entity = newEntity(livello, type, descrizione);
        save(entity);
        return entity;
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Senza properties per compatibilità con la superclasse <br>
     *
     * @param context della sessione
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Log newEntity(AContext context){
        return newEntity((Livello) null, (Logtype) null, "");
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Properties obbligatorie <br>
     *
     * @param descrizione (obbligatoria, non unica) <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Log newEntity(String descrizione) {
        return newEntity((Livello) null, (Logtype) null, descrizione);
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param livello     rilevanza del log (obbligatorio)
     * @param type        raggruppamento logico dei log per type di eventi (obbligatorio)
     * @param descrizione (obbligatoria, non unica) <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Log newEntity(Livello livello, Logtype type, String descrizione) {
        Log entity;

        entity = Log.builderLog()
                .livello(livello != null ? livello : Livello.info)
                .type(type != null ? type : logtype.getEdit())
                .descrizione(text.isValid(descrizione) ? descrizione : null)
                .evento(LocalDateTime.now())
                .build();

        return (Log) creaIdKeySpecifica(entity);
    }// end of method

    /**
     * Returns all entities of the type <br>
     * <p>
     * Se esiste la property 'ordine', ordinate secondo questa property <br>
     * Altrimenti, se esiste la property 'code', ordinate secondo questa property <br>
     * Altrimenti, se esiste la property 'descrizione', ordinate secondo questa property <br>
     * Altrimenti, ordinate secondo il metodo sovrascritto nella sottoclasse concreta <br>
     * Altrimenti, ordinate in ordine di inserimento nel DB mongo <br>
     *
     * @param context della sessione
     * @return all ordered entities
     */
    @Override
    public List<? extends AEntity> findAll(AContext context) {
        return repository.findAll();
    }// end of method

    /**
     * Property unica (se esiste) <br>
     */
    @Override
    public String getPropertyUnica(AEntity entityBean) {
        String code = "";
        Log log = ((Log) entityBean);

        code += log.getType().code;
        code += log.getEvento().toString();

        return code;
    }// end of method


    //--registra un avviso
    public void debug(Logtype type, String descrizione) {
        crea(Livello.debug, type, descrizione);
    }// fine del metodo

    //--registra un avviso
    public void info(Logtype type, String descrizione) {
        crea(Livello.info, type, descrizione);
    }// fine del metodo

    //--registra un avviso
    public void warning(Logtype type, String descrizione) {
        crea(Livello.warn, type, descrizione);
    }// fine del metodo

    //--registra un avviso
    public void error(Logtype type, String descrizione) {
        crea(Livello.error, type, descrizione);
    }// fine del metodo

    //--registra un avviso
    public void importo(String descrizione) {
        crea(Livello.debug, logtype.getImport(),descrizione);
    }// fine del metodo



}// end of class