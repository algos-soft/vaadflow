package it.algos.vaadflow.modules.secolo;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.application.AContext;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.service.AService;
import it.algos.vaadflow.ui.dialog.AViewDialog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import static it.algos.vaadflow.application.FlowCost.TAG_SEC;


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
/**
 * In deroga a quanto scritto sopra (valido per gli altri xxxService, questa classe è 'singleton' <br>
 * Viene iniettata da SprinBoot in AUserDetailsService, prima che esista la VaadinSession <br>
 * NOT annotated with @VaadinSessionScope (sbagliato) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_SEC)
@Slf4j
@AIScript(sovrascrivibile = false)
public class SecoloService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    public SecoloRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public SecoloService(@Qualifier(TAG_SEC) MongoRepository repository) {
        super(repository);
        super.entityClass = Secolo.class;
        this.repository = (SecoloRepository) repository;
    }// end of Spring constructor


    /**
     * Crea una entity e la registra <br>
     *
     * @param titolo     (obbligatorio, unico)
     * @param inizio     (obbligatorio, unico)
     * @param fine       (obbligatorio, unico)
     * @param anteCristo flag per i secoli prima di cristo (obbligatorio)
     *
     * @return la entity appena creata
     */
    public Secolo crea(String titolo, int inizio, int fine, boolean anteCristo) {
        return (Secolo) save(newEntity(titolo, inizio, fine, anteCristo));
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
    public Secolo newEntity(AContext context){
        return newEntity("", 0, 0, false);
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Utilizza, eventualmente, la newEntity() della superclasse, per le property della superclasse <br>
     *
     * @param titolo     (obbligatorio, unico)
     * @param inizio     (obbligatorio, unico)
     * @param fine       (obbligatorio, unico)
     * @param anteCristo flag per i secoli prima di cristo (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Secolo newEntity(String titolo, int inizio, int fine, boolean anteCristo) {
        Secolo entity = null;

        entity = findByKeyUnica(titolo);
        if (entity != null) {
            return findByKeyUnica(titolo);
        }// end of if cycle

        entity = Secolo.builderSecolo()
                .titolo(titolo)
                .inizio(inizio)
                .fine(fine)
                .anteCristo(anteCristo)
                .build();

        return (Secolo) creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Property unica (se esiste).
     */
    public String getPropertyUnica(AEntity entityBean) {
        return text.isValid(((Secolo) entityBean).getTitolo()) ? ((Secolo) entityBean).getTitolo() : "";
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
        Secolo entity = (Secolo) super.beforeSave(entityBean, operation);

        if (text.isEmpty(entity.titolo) || entity.inizio == 0 || entity.fine == 0) {
            entity = null;
            log.error("entity incompleta in SecoloService.beforeSave()");
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
    public Secolo findByKeyUnica(String titolo) {
        return repository.findByTitolo(titolo);
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

        for (EASecolo eaSecolo : EASecolo.values()) {
            this.crea(eaSecolo.getTitolo(), eaSecolo.getInizio(), eaSecolo.getFine(), eaSecolo.isAnteCristo());
            num++;
        }// end of for cycle

        return num;
    }// end of method

}// end of class