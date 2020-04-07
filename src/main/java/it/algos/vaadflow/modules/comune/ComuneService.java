package it.algos.vaadflow.modules.comune;

import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.provincia.Provincia;
import it.algos.vaadflow.modules.regione.Regione;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import static it.algos.vaadflow.application.FlowCost.*;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 7-apr-2020 7.57.17 <br>
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
@Qualifier(TAG_COMUNE)
@Slf4j
@AIScript(sovrascrivibile = false)
public class ComuneService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    public ComuneRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public ComuneService(@Qualifier(TAG_COMUNE) MongoRepository repository) {
        super(repository);
        super.entityClass = Comune.class;
        this.repository = (ComuneRepository) repository;
    }// end of Spring constructor


    public boolean creaIfNotExist(Regione regione, Provincia provincia, String nome) {
        boolean creata = false;

        if (isMancaByKeyUnica(nome)) {
            AEntity entity = save(newEntity(regione, provincia, nome));
            creata = entity != null;
        }// end of if cycle

        return creata;
    }// end of method


    /**
     * Crea una entity e la registra <br>
     *
     * @param regione   (obbligatoria)
     * @param provincia (obbligatoria)
     * @param nome      (obbligatorio, unico)
     *
     * @return la entity appena creata
     */
    public Comune crea(Regione regione, Provincia provincia, String nome) {
        return (Comune) save(newEntity(regione, provincia, nome));
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Comune newEntity() {
        return newEntity((Regione) null, (Provincia) null, VUOTA);
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Utilizza, eventualmente, la newEntity() della superclasse, per le property della superclasse <br>
     *
     * @param regione   (obbligatoria)
     * @param provincia (obbligatoria)
     * @param nome      (obbligatorio, unico)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Comune newEntity(Regione regione, Provincia provincia, String nome) {
        Comune entity = null;

        entity = Comune.builderComune()
                .regione(regione)
                .provincia(provincia)
                .nome(text.isValid(nome) ? nome : null)
                .build();

        return (Comune) creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param nome      (obbligatorio, unico)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Comune findByKeyUnica(String nome) {
        return repository.findByNome(nome);
    }// end of method

    /**
     * Property unica (se esiste) <br>
     */
    @Override
    public String getPropertyUnica(AEntity entityBean) {
        return ((Comune) entityBean).getNome();
    }// end of method

}// end of class