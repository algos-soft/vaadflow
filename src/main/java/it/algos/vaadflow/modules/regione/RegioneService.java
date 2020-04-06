package it.algos.vaadflow.modules.regione;

import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.secolo.EASecolo;
import it.algos.vaadflow.modules.secolo.Secolo;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import static it.algos.vaadflow.application.FlowCost.TAG_REG;
import static it.algos.vaadflow.application.FlowCost.VUOTA;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 6-apr-2020 10.15.25 <br>
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
@Qualifier(TAG_REG)
@Slf4j
@AIScript(sovrascrivibile = false)
public class RegioneService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    public RegioneRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public RegioneService(@Qualifier(TAG_REG) MongoRepository repository) {
        super(repository);
        super.entityClass = Regione.class;
        this.repository = (RegioneRepository) repository;
    }// end of Spring constructor


    /**
     * Crea una entity solo se non esisteva <br>
     *
     * @param iso  (obbligatorio, unico)
     * @param nome (obbligatorio, unico)
     *
     * @return true se la entity è stata creata
     */
    public boolean creaIfNotExist(String iso, String nome) {
        boolean creata = false;

        if (isMancaByKeyUnica(iso)) {
            AEntity entity = save(newEntity(iso, nome));
            creata = entity != null;
        }// end of if cycle

        return creata;
    }// end of method


    /**
     * Crea una entity e la registra <br>
     *
     * @param iso  (obbligatorio, unico)
     * @param nome (obbligatorio, unico)
     *
     * @return la entity appena creata
     */
    public Regione crea(String iso, String nome) {
        return (Regione) save(newEntity(iso, nome));
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Regione newEntity() {
        return newEntity(VUOTA, VUOTA);
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Utilizza, eventualmente, la newEntity() della superclasse, per le property della superclasse <br>
     *
     * @param iso  (obbligatorio, unico)
     * @param nome (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Regione newEntity(String iso, String nome) {
        Regione entity = null;

        entity = Regione.builderRegione()
                .iso(text.isValid(iso) ? iso : null)
                .nome(text.isValid(nome) ? nome : null)
                .build();

        return (Regione) creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param iso (obbligatorio, unico)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Regione findByKeyUnica(String iso) {
        return repository.findByIso(iso);
    }// end of method


    /**
     * Property unica (se esiste) <br>
     */
    @Override
    public String getPropertyUnica(AEntity entityBean) {
        return ((Regione) entityBean).getIso();
    }// end of method


    /**
     * Creazione di alcuni dati iniziali <br>
     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo per il developer) <br>
     * I dati possono essere presi da una Enumeration o creati direttamemte <br>
     * Deve essere sovrascritto - Invocare PRIMA il metodo della superclasse che cancella tutta la Collection <br>
     *
     * @return numero di elementi creati
     */
    @Override
    public int reset() {
        int numRec = super.reset();

        creaIfNotExist("IT-65", "Abruzzo");
        creaIfNotExist("IT-77", "Basilicata");
        creaIfNotExist("IT-78", "Calabria");
        creaIfNotExist("IT-72", "Campania");
        creaIfNotExist("IT-45", "Emilia-Romagna");
        creaIfNotExist("IT-36", "Friuli-Venezia Giulia");
        creaIfNotExist("IT-62", "Lazio");
        creaIfNotExist("IT-42", "Liguria");
        creaIfNotExist("IT-25", "Lombardia");
        creaIfNotExist("IT-57", "Marche");
        creaIfNotExist("IT-67", "Molise");
        creaIfNotExist("IT-21", "Piemonte");
        creaIfNotExist("IT-75", "Puglia");
        creaIfNotExist("IT-88", "Sardegna");
        creaIfNotExist("IT-82", "Sicilia");
        creaIfNotExist("IT-52", "Toscana");
        creaIfNotExist("IT-32", "Trentino-Alto Adige");
        creaIfNotExist("IT-55", "Umbria");
        creaIfNotExist("IT-23", "Valle d'Aosta");
        creaIfNotExist("IT-34", "Veneto");

        return numRec;
    }// end of method

}// end of class