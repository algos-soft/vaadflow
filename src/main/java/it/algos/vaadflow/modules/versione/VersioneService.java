package it.algos.vaadflow.modules.versione;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.application.AContext;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.preferenza.EAPrefType;
import it.algos.vaadflow.modules.preferenza.PreferenzaService;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.TAG_VER;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 26-ott-2018 9.59.58 <br>
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
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_VER)
@Slf4j
@AIScript(sovrascrivibile = false)
public class VersioneService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    private final static String TAG = ".";

    /**
     * La injection viene fatta da SpringBoot in automatico <br>
     */
    @Autowired
    private PreferenzaService pref;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private VersioneRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola nella superclasse il modello-dati specifico <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public VersioneService(@Qualifier(TAG_VER) MongoRepository repository) {
        super(repository);
        super.entityClass = Versione.class;
        this.repository = (VersioneRepository) repository;
    }// end of Spring constructor

    /**
     * Crea una entity e la registra <br>
     *
     * @param sigla  del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param titolo della versione (obbligatorio, non unico) <br>
     * @param nome   descrittivo della versione (obbligatorio, unico) <br>
     *
     * @return la entity appena creata
     */
    public Versione crea(String sigla, String titolo, String nome) {
        return (Versione) save(newEntity(sigla, 0, titolo, nome, (LocalDateTime) null));
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Senza properties per compatibilità con la superclasse <br>
     *
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Versione newEntity( ) {
        return newEntity("", 0, "", "", (LocalDateTime) null);
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param sigla     del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param ordine    di presentazione (obbligatorio con inserimento automatico se è zero)
     * @param titolo    della versione (obbligatorio, non unico) <br>
     * @param nome      descrittivo della versione (obbligatorio, unico) <br>
     * @param timestamp in cui si effettua la modifica della versione (obbligatorio, non unica) <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Versione newEntity(String sigla, int ordine, String titolo, String nome, LocalDateTime timestamp) {
        Versione entity = null;
        int newOrdine = 0;
        String textOrdine;

        entity = Versione.builderVersione()
                .titolo(text.isValid(titolo) ? titolo : null)
                .nome(text.isValid(nome) ? nome : null)
                .timestamp(timestamp != null ? timestamp : LocalDateTime.now())
                .build();

        newOrdine = getNewOrdine(sigla, ordine);
        entity.id = getIdKey(sigla, newOrdine);

        return entity;
    }// end of method


    /**
     * Ordine di presentazione (obbligatorio, unico per ogni project), <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property per lo specifico progetto <br>
     * Incrementa di uno il risultato <br>
     */
    public int getNewOrdine(String sigla, int ordine) {
        int newOrdine = ordine;
        List<Versione> lista = null;
        String idKey = "";

        if (newOrdine == 0) {
            lista = repository.findByIdRegex(sigla);
            if (lista != null && lista.size() > 0) {
                idKey = lista.get(lista.size() - 1).getId();
                idKey = idKey.substring(1);
                idKey = idKey.startsWith(TAG) ? text.levaTesta(idKey, TAG) : idKey;
                idKey = idKey.startsWith(TAG) ? text.levaTesta(idKey, TAG) : idKey;//doppio per numeri sopra i 10 e fino a 100
            }// end of if cycle

            try { // prova ad eseguire il codice
                newOrdine = Integer.decode(idKey);
            } catch (Exception unErrore) { // intercetta l'errore
                log.error(unErrore.toString());
            }// fine del blocco try-catch
        }// end of if cycle

        return newOrdine + 1;
    }// end of method


    public String getIdKey(String codeUnCarattere, int numProgressivo) {
        if (numProgressivo < 100) {
            if (numProgressivo < 10) {
                return codeUnCarattere + TAG + TAG + numProgressivo;
            } else {
                return codeUnCarattere + TAG + numProgressivo;
            }// end of if/else cycle
        } else {
            return codeUnCarattere + numProgressivo;
        }// end of if/else cycle
    }// end of method

    /**
     * Retrieves an entity by its id.
     * Codice formato da un carattere, un separatore ed un numero
     */
    public Versione findByCode(String codeUnCarattere, int numProgressivo) {
        return (Versione) findById(getIdKey(codeUnCarattere, numProgressivo));
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
        return findAll((Sort) null);
    }// end of method


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
        int num = super.reset();

        return num;
    }// end of method

    /**
     * Crea una entity di Versione e la registra <br>
     * Crea una nuova preferenza (solo se non esistente) <br>
     *
     * @param sigla    del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     * @param type     della preferenza
     * @param value    di default della preferenza
     */
    public void creaPref(String sigla, String codePref, String descPref, EAPrefType type, Object value) {
//        pref.creaIfNotExist(codePref, descPref, type, value);//@todo da sistemare
        this.crea(sigla, "Preferenze", codePref + ", di default " + value);
    }// end of method


    /**
     * Crea una entity di Versione e la registra <br>
     * Crea una nuova preferenza di tipo string (solo se non esistente) <br>
     *
     * @param sigla    del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     * @param value    di default della preferenza
     */
    public void creaPrefTxt(String sigla, String codePref, String descPref, String value) {
        creaPref(sigla, codePref, descPref, EAPrefType.string, value);
    }// end of method

    /**
     * Crea una entity di Versione e la registra <br>
     * Crea una nuova preferenza di tipo string (solo se non esistente) <br>
     *
     * @param sigla    del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public void creaPrefTxt(String sigla, String codePref, String descPref) {
        creaPrefTxt(sigla, codePref, descPref, "");
    }// end of method


    /**
     * Crea una entity di Versione e la registra <br>
     * Crea una nuova preferenza di tipo bool (solo se non esistente) <br>
     *
     * @param sigla    del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     * @param value    di default della preferenza
     */
    public void creaPrefBool(String sigla, String codePref, String descPref, boolean value) {
        creaPref(sigla, codePref, descPref, EAPrefType.bool, value);
    }// end of method

    /**
     * Crea una entity di Versione e la registra <br>
     * Crea una nuova preferenza di tipo bool (solo se non esistente) <br>
     *
     * @param sigla    del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public void creaPrefBool(String sigla, String codePref, String descPref) {
        creaPrefBool(sigla, codePref, descPref, false);
    }// end of method


    /**
     * Crea una entity di Versione e la registra <br>
     * Crea una nuova preferenza di tipo int (solo se non esistente) <br>
     *
     * @param sigla    del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     * @param value    di default della preferenza
     */
    public void creaPrefInt(String sigla, String codePref, String descPref, int value) {
        creaPref(sigla, codePref, descPref, EAPrefType.integer, value);
    }// end of method

    /**
     * Crea una entity di Versione e la registra <br>
     * Crea una nuova preferenza di tipo int (solo se non esistente) <br>
     *
     * @param sigla    del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public void creaPrefInt(String sigla, String codePref, String descPref) {
        creaPrefInt(sigla, codePref, descPref, 0);
    }// end of method


    /**
     * Crea una entity di Versione e la registra <br>
     * Crea una nuova preferenza di tipo date (solo se non esistente) <br>
     *
     * @param sigla    del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     * @param value    di default della preferenza
     */
    public void creaPrefDate(String sigla, String codePref, String descPref, LocalDateTime value) {
        creaPref(sigla, codePref, descPref, EAPrefType.date, value);
    }// end of method

    /**
     * Crea una entity di Versione e la registra <br>
     * Crea una nuova preferenza di tipo date (solo se non esistente) <br>
     *
     * @param sigla    del progetto interessato (obbligatorio, un solo carattere) <br>
     * @param codePref key code della preferenza (obbligatoria per Pref)
     * @param descPref dettagliata (obbligatoria per Pref)
     */
    public void creaPrefDate(String sigla, String codePref, String descPref) {
        creaPrefDate(sigla, codePref, descPref, (LocalDateTime) null);
    }// end of method


}// end of class