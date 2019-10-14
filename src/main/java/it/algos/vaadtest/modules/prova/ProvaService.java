package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.application.AContext;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAOperation;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static it.algos.vaadtest.application.TestCost.TAG_PRO;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 14-ott-2019 18.35.01 <br>
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
@Qualifier(TAG_PRO)
@Slf4j
@AIScript(sovrascrivibile = false)
public class ProvaService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    public ProvaRepository repository;


    public ProvaService() {
    }// end of Spring constructor


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public ProvaService(@Qualifier(TAG_PRO) MongoRepository repository) {
        super(repository);
        super.entityClass = Prova.class;
        this.repository = (ProvaRepository) repository;
    }// end of Spring constructor


    /**
     * Crea una entity e la registra <br>
     *
     * @param code        di riferimento (obbligatorio ed unico)
     * @param descrizione (obbligatorio)
     *
     * @return la entity appena creata
     */
    public Prova crea(String code, String descrizione) {
        return (Prova) save(newEntity(0, code, descrizione), EAOperation.addNew);
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Senza properties per compatibilità con la superclasse <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Prova newEntity() {
        return newEntity(0, "", "");
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     *
     * @param ordine      di presentazione (obbligatorio con inserimento automatico se è zero)
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Prova newEntity(int ordine, String code, String descrizione) {
        Prova entity = null;

        entity = findByKeyUnica(code);
        if (entity != null) {
            return findByKeyUnica(code);
        }// end of if cycle

        entity = Prova.builderProva()
                .ordine(ordine != 0 ? ordine : this.getNewOrdine())
                .code(code.equals("") ? null : code)
                .descrizione(descrizione.equals("") ? null : descrizione)
                .lastModifica(LocalDateTime.now())
                .build();

        return (Prova) creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Property unica (se esiste).
     */
    @Override
    public String getPropertyUnica(AEntity entityBean) {
        return ((Prova) entityBean).getCode();
    }// end of method


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Prova findByKeyUnica(String code) {
        return repository.findByCode(code);
    }// end of method


    /**
     * Ordine di presentazione (obbligatorio, unico per tutte le eventuali company), <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property <br>
     * Incrementa di uno il risultato <br>
     */
    public int getNewOrdine() {
        int ordine = 0;
        List<Prova> lista = repository.findAllByOrderByOrdineAsc();

        if (lista != null && lista.size() > 0) {
            ordine = lista.get(lista.size() - 1).getOrdine();
        }// end of if cycle

        return ordine + 1;
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
    public List<? extends AEntity> findAll2() {

        //    db.collection.find().skip(20).limit(10).sort({"xxx":1})


        Pageable page = PageRequest.of(0, 2);
        Object lista = repository.findAll(page);
        Object listaAll = super.findAll();

        return repository.findAll(page).getContent();
    }


    /**
     * Operazioni eseguite PRIMA del save <br>
     * Regolazioni automatiche di property <br>
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, Edit)
     *
     * @return the modified entity
     */
    public AEntity beforeSave(AEntity entityBean, EAOperation operation) {
        return super.beforeSave(entityBean, operation);
    }// end of method


    /**
     * Costruisce una lista di nomi delle properties del Search nell'ordine:
     * 1) Sovrascrive la lista nella sottoclasse specifica di xxxService
     *
     * @param context legato alla sessione
     *
     * @return lista di nomi di properties
     */
    @Override
    public List<String> getSearchPropertyNamesList(AContext context) {
        return Arrays.asList("code");
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
        int numRec = super.reset();
        int numRecord = 16;
        String code = "";
        String desc = "";
        Prova prova;

        for (int k = 1; k <= numRecord; k++) {

            if (k == 6 || k == 7) {
                code = "cod" + k;
            } else {
                code = k + "cod";
            }// end of if/else cycle

            if (k == 8) {
                desc = "descrizione leggermente diversa (per test)";
            } else {
                desc = "questa è la descrizione n. " + k;
            }// end of if/else cycle

            prova = crea(code, desc);
            if (k == 3 || k == 9) {
                prova.box = true;
            }// end of if cycle
            if (k == 5 || k == 7) {
                prova.sino = true;
            }// end of if cycle
            if (k == 1 || k == 5 || k == 8 || k == 9 || k == 12 || k == 13) {
                prova.yesno = true;
            }// end of if cycle
            if (k == 2 || k == 3 || k == 4 || k == 7 || k == 14 || k == 15) {
                prova.yesnobold = true;
            }// end of if cycle
            prova.ordine = 178 + k;
            prova.pageid = 1000000L + (14 * k) + k + 8;
            prova.inizio = LocalTime.of(k, k + 2);
            prova.fine = LocalTime.of(k + 1, k * 3);
            save(prova);
        }// end of for cycle

        return numRec;
    }// end of method


    /**
     * Durata di un periodo in ore <br>
     */
    public String durataOre(AEntity entityBean) {
        return "" + date.durata(((Prova) entityBean).fine, ((Prova) entityBean).inizio);
    }// end of method

    /**
     * Durata di un periodo in minuti <br>
     */
    public String durataMinuti(AEntity entityBean) {
        return "" + date.differenza(((Prova) entityBean).fine, ((Prova) entityBean).inizio);
    }// end of method

    /**
     * Durata di un periodo in LocalTime <br>
     */
    public String durataTempo(AEntity entityBean) {
        LocalTime tempo= date.periodo(((Prova) entityBean).fine, ((Prova) entityBean).inizio);
        return  date.getOrario(tempo);
    }// end of method

}// end of class