package it.algos.vaadtest.modules.alfa;

import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.company.Company;
import it.algos.vaadflow.modules.company.CompanyService;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.algos.vaadtest.application.TestCost.TAG_ALF;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 23-ott-2019 13.52.45 <br>
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
@Qualifier(TAG_ALF)
@Slf4j
@AIScript(sovraScrivibile = false)
public class AlfaService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio: <br>
     * Iniettata automaticamente dal Framework @Autowired (SpringBoot/Vaadin) <br>
     * Disponibile dopo il metodo beforeEnter() invocato da @Route al termine dell'init() di questa classe <br>
     * Disponibile dopo un metodo @PostConstruct invocato da Spring al termine dell'init() di questa classe <br>
     */
    @Autowired
    private CompanyService companyService;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public AlfaService(@Qualifier(TAG_ALF) MongoRepository repository) {
        super(repository);
        super.entityClass = Alfa.class;
        this.repository = (AlfaRepository) repository;
    }// end of Spring constructor


    /**
     * Crea una entity e la registra <br>
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity appena creata
     */
    public Alfa crea(Company company, String descrizione, boolean ragazzo, String nazionalita, boolean simpatico) {
        return (Alfa) save(newEntity(company, descrizione, ragazzo, nazionalita, simpatico));
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Alfa newEntity() {
        return newEntity(null, "", true, "", true);
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Utilizza, eventualmente, la newEntity() della superclasse, per le property della superclasse <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Alfa newEntity(Company company, String descrizione, boolean ragazzo, String nazionalita, boolean simpatico) {
        Alfa entity = null;

        entity = Alfa.builderAlfa()
                .descrizione(text.isValid(descrizione) ? descrizione : null)
                .ragazzo(ragazzo)
                .nazionalita(nazionalita)
                .simpatico(simpatico)
                .build();
        entity.company = company;
        return entity;
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
     * @return all ordered entities
     */
    @Override
    public List<? extends AEntity> findAll() {
        return super.findAll();
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
        int numRec = super.reset();
        int numRecord = 20;
        String desc = "";
        boolean ragazzo = false;
        String nazionalita  = "";
        boolean simpatico = false;
        Company company=null;

        for (int k = 1; k <= numRecord; k++) {

            if (k == 1) {
                desc = "ragazzo-francese-simpatico";
                ragazzo = true;
                nazionalita = "francese";
                simpatico = true;
                company = companyService.getAlgos();
            }// end of if cycle

            if (k > 1 && k <= 5) {
                desc = "ragazzo-turco-simpatico";
                ragazzo = true;
                nazionalita = "turco";
                simpatico = true;
                company = companyService.getTest();
            }// end of if cycle

            if (k > 5 && k <= 10) {
                desc = "ragazzo-inglese-antipatico";
                ragazzo = true;
                nazionalita = "inglese";
                simpatico = false;
                company = companyService.getAlgos();
            }// end of if cycle

            if (k > 10 && k < numRecord) {
                desc = "ragazza-francese-simpatica";
                ragazzo = false;
                nazionalita = "francese";
                simpatico = true;
                company = companyService.getTest();
            }// end of if cycle

            if (k == numRecord) {
                desc = "ragazza-tedesca-antipatica";
                ragazzo = false;
                nazionalita = "tedesca";
                simpatico = false;
                company = companyService.getAlgos();
            }// end of if cycle

            desc = k + desc;
            crea(company, desc, ragazzo, nazionalita, simpatico);
        }// end of for cycle

        return numRec;
    }// end of method

}// end of class