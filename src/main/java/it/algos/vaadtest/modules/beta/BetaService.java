package it.algos.vaadtest.modules.beta;

import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAOperation;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.address.AddressService;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static it.algos.vaadtest.application.TestCost.TAG_BET;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 14-ott-2019 18.56.46 <br>
 * <br>
 * Business class. Layer di collegamento per la Repository. <br>
 * <br>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * NOT annotated with @VaadinSessionScope (sbagliato, perché SpringBoot va in loop iniziale) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * - la documentazione precedente a questo tag viene SEMPRE riscritta <br>
 * - se occorre preservare delle @Annotation con valori specifici, spostarle DOPO @AIScript <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_BET)
@Slf4j
@AIScript(sovraScrivibile = false)
public class BetaService extends AService {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    public BetaRepository repository;

    @Autowired
    private AddressService addressService;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public BetaService(@Qualifier(TAG_BET) MongoRepository repository) {
        super(repository);
        super.entityClass = Beta.class;
        this.repository = (BetaRepository) repository;
    }// end of Spring constructor


    /**
     * Ricerca di una entity (la crea se non la trova) <br>
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity trovata o appena creata
     */
    public Beta findOrCrea(String code) {
        Beta entity = null;

        if (entity == null) {
            entity = crea(code);
        }// end of if cycle

        return entity;
    }// end of method


    /**
     * Crea una entity e la registra <br>
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity appena creata
     */
    public Beta crea(String code) {
        return (Beta) save(newEntity(0, code));
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Beta newEntity() {
        return newEntity(0, "");
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Utilizza, eventualmente, la newEntity() della superclasse, per le property della superclasse <br>
     *
     * @param ordine di presentazione (obbligatorio con inserimento automatico se è zero)
     * @param code   codice di riferimento (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Beta newEntity(int ordine, String code) {
        Beta entity = null;

//        entity = findByKeyUnica(code);
//        if (entity != null) {
//            return findByKeyUnica(code);
//        }// end of if cycle

        entity = Beta.builderBeta()
                .ordine(ordine != 0 ? ordine : this.getNewOrdine())
                .code(text.isValid(code) ? code : null)
                .build();

        return (Beta) creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Operazioni eseguite PRIMA del save <br>
     * Regolazioni automatiche di property <br>
     * Controllo della validità delle properties obbligatorie <br>
     * Può essere sovrascritto - Invocare PRIMA il metodo della superclasse
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, Edit)
     *
     * @return the modified entity
     */
    @Override
    public AEntity beforeSave(AEntity entityBean, EAOperation operation) {
//        ((Beta)entityBean).icona= VaadinIcon.DEL;
        return super.beforeSave(entityBean, operation);
    }


    public String getKeyUnica(AEntity entityBean) {
        int num = repository.countByCode(((Beta) entityBean).code);
        int num2 = num;
        Beta alfa = repository.findFirstByCode(((Beta) entityBean).code);
        Object beta = alfa;
        int a = 87;
        return "";
    }// end of method


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Beta findByKeyUnica(String code) {
        return repository.findByCode(code);
    }// end of method


    /**
     * Proviene da Lista (quasi sempre)
     * Primo ingresso dopo il click sul bottone <br>
     *
     * @param entityBean
     * @param operation
     */
    @Override
    public AEntity save(AEntity entityBean, EAOperation operation) {
        List lista = addressService.findAll();
        ((Beta) entityBean).indirizzo = (Address) lista.get(1);
        return super.save(entityBean, operation);
    }


    /**
     * Creazione di alcuni dati demo iniziali <br>
     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo per il developer) <br>
     * La collezione viene svuotata <br>
     * I dati possono essere presi da una Enumeration o creati direttamemte <br>
     * Deve essere sovrascritto - Invocare PRIMA il metodo della superclasse
     *
     * @return numero di elementi creato
     */
    @Override
    public int reset() {
        super.reset();

        Beta beta;

        beta = crea("alfa");
        beta.data = LocalDate.now();
        beta.datetime = LocalDateTime.now();
        beta.time = LocalTime.now();
        save(beta);

        beta = crea("beta");
        beta.data = null;
        beta.datetime = null;
        beta.time = null;
        save(beta);

        beta = crea("gamma");
        beta.data = LocalDate.now();
        beta.datetime = LocalDateTime.now();
        beta.time = LocalTime.now();
        save(beta);

        return 0;
    }// end of method

}// end of class