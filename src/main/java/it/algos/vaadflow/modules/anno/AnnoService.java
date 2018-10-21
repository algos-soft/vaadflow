package it.algos.vaadflow.modules.anno;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.secolo.EASecolo;
import it.algos.vaadflow.modules.secolo.Secolo;
import it.algos.vaadflow.modules.secolo.SecoloService;
import it.algos.vaadflow.service.AService;
import it.algos.vaadflow.ui.dialog.AViewDialog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.algos.vaadflow.application.FlowCost.TAG_ANN;
import static it.algos.vaadflow.application.FlowCost.VUOTA;

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
@Qualifier(TAG_ANN)
@Slf4j
@AIScript(sovrascrivibile = false)
public class AnnoService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    //--usato nell'ordinamento delle categorie
    public   static final int ANNO_INIZIALE = 2000;

    public   static final int ANTE_CRISTO = 1000;
    public    static final int DOPO_CRISTO = 2030;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    public AnnoRepository repository;



    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private SecoloService secoloService;

    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public AnnoService(@Qualifier(TAG_ANN) MongoRepository repository) {
        super(repository);
        super.entityClass = Anno.class;
        this.repository = (AnnoRepository) repository;
    }// end of Spring constructor


    /**
     * Crea una entity e la registra <br>
     *
     * @param secolo      di riferimento (obbligatorio)
     * @param ordinamento (obbligatorio, unico)
     * @param titolo      (obbligatorio, unico)
     *
     * @return la entity appena creata
     */
    public Anno crea(Secolo secolo, int ordinamento, String titolo) {
        return (Anno) save(newEntity(secolo, ordinamento, titolo));
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Anno newEntity() {
        return newEntity((Secolo) null, 0, "");
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Utilizza, eventualmente, la newEntity() della superclasse, per le property della superclasse <br>
     *
     * @param secolo di riferimento (obbligatorio)
     * @param ordine (obbligatorio, unico)
     * @param titolo (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Anno newEntity(Secolo secolo, int ordine, String titolo) {
        Anno entity = null;

        entity = findByKeyUnica(titolo);
        if (entity != null) {
            return findByKeyUnica(titolo);
        }// end of if cycle

        entity = Anno.builderAnno()
                .secolo(secolo)
                .ordine(ordine)
                .titolo(titolo)
                .build();

        return (Anno) creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Property unica (se esiste).
     */
    public String getPropertyUnica(AEntity entityBean) {
        return text.isValid(((Anno) entityBean).getTitolo()) ? ((Anno) entityBean).getTitolo() : "";
    }// end of method


    /**
     * Operazioni eseguite PRIMA del save <br>
     * Regolazioni automatiche di property <br>
     *
     * @param entityBean da regolare prima del save
     * @param operation  del dialogo (NEW, EDIT)
     *
     * @return the modified entity
     */
    @Override
    public AEntity beforeSave(AEntity entityBean, AViewDialog.Operation operation) {
        Anno entity = (Anno) super.beforeSave(entityBean, operation);

        if (entity.getSecolo() == null || entity.ordine == 0 || text.isEmpty(entity.titolo)) {
            entity = null;
            log.error("entity incompleta in AnnoService.beforeSave()");
        }// end of if cycle

        return entity;
    }// end of method

    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param titolo (obbligatorio, unico)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Anno findByKeyUnica(String titolo) {
        return repository.findByTitolo(titolo);
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
        return repository.findTop100ByOrderByOrdine();
    }// end of method

    /**
     * Creazione di alcuni dati demo iniziali <br>
     * Viene invocato alla creazione del programma e dal bottone Reset della lista (solo per il developer) <br>
     * La collezione viene svuotata <br>
     * I dati possono essere presi da una Enumeration o creati direttamemte <br>
     * Deve essere sovrascritto - Invocare PRIMA il metodo della superclasse
     * <p>
     * Ante cristo dal 1000
     * Dopo cristo fino al 2030
     *
     * @return numero di elementi creato
     */
    @Override
    public int reset() {
        return flow.loadAnno();
    }// end of method

    /**
     * Controlla l'esistenza di una Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param titolo (obbligatorio, unico)
     *
     * @return true se trovata
     */
    public boolean isEsiste(String titolo) {
        return findByKeyUnica(titolo) != null;
    }// end of method


}// end of class