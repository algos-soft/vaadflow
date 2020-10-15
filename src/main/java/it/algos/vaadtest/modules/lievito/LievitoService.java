package it.algos.vaadtest.modules.lievito;

import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.modules.provincia.Provincia;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.algos.vaadtest.application.TestCost.TAG_LIE;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 7-apr-2020 16.09.31 <br>
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
@Qualifier(TAG_LIE)
@Slf4j
@AIScript(sovraScrivibile = false)
public class LievitoService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    public LievitoRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public LievitoService(@Qualifier(TAG_LIE) MongoRepository repository) {
        super(repository);
        super.entityClass = Lievito.class;
        this.repository = (LievitoRepository) repository;
    }// end of Spring constructor


    /**
     * Ricerca di una entity (la crea se non la trova) <br>
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity trovata o appena creata
     */
    public Lievito findOrCrea(String code) {


        return null;
    }// end of method


    /**
     * Crea una entity e la registra <br>
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity appena creata
     */
    public Lievito crea(String code) {
        return (Lievito) save(newEntity(0, code, ""));
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Lievito newEntity() {
        return newEntity(0, "", "");
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Utilizza, eventualmente, la newEntity() della superclasse, per le property della superclasse <br>
     *
     * @param ordine      di presentazione (obbligatorio con inserimento automatico se è zero)
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (facoltativa, non unica)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Lievito newEntity(int ordine, String code, String descrizione) {
        Lievito entity = null;


        entity = Lievito.builderLievito()
                .ordine(ordine != 0 ? ordine : this.getNewOrdine())
                .descrizione(text.isValid(descrizione) ? descrizione : null)
                .build();

        return (Lievito) creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Returns all entities of the type <br>
     *
     * @return all ordered entities
     */
    public List<Lievito> findAllByProvincia(Provincia provincia) {
        return (List) repository.findAllByProvinciaOrderByComuneAsc(provincia);
    }// end of method


}// end of class