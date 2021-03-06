package it.algos.vaadflow.modules.comune;

import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EARegione;
import it.algos.vaadflow.importa.ImportWiki;
import it.algos.vaadflow.modules.provincia.Provincia;
import it.algos.vaadflow.modules.provincia.ProvinciaService;
import it.algos.vaadflow.modules.regione.Regione;
import it.algos.vaadflow.modules.regione.RegioneService;
import it.algos.vaadflow.service.AService;
import it.algos.vaadflow.wrapper.WrapDueStringhe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.algos.vaadflow.application.FlowCost.TAG_COMUNE;
import static it.algos.vaadflow.application.FlowCost.VUOTA;

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
@AIScript(sovraScrivibile = false)
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
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private RegioneService regioneService;

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private ProvinciaService provinciaService;


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
     * @param nome (obbligatorio, unico)
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


    /**
     * Returns all entities of the type <br>
     *
     * @return all ordered entities
     */
    public List<Comune> findAll() {
        return (List) repository.findAllByOrderByNomeAsc();
    }// end of method


    /**
     * Returns all entities of the type <br>
     *
     * @return all ordered entities
     */
    public List<Comune> findAllByProvincia(Provincia provincia) {
        return (List) repository.findAllByProvinciaOrderByNomeAsc(provincia);
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
        List<WrapDueStringhe> listaWrap = null;
        ImportWiki importService;
        Regione regione;
        Provincia provincia;
        int numRec = super.reset();
        String comuneText;

        //--recupera una lista di tutte le provincie dal server di Wikipedia
        importService = appContext.getBean(ImportWiki.class);
//        listaWrap = importService.comuni();

        for (EARegione eaRegione : EARegione.values()) {
            listaWrap = importService.singolaRegione(eaRegione);
            if (listaWrap != null && listaWrap.size() > 0) {
                for (WrapDueStringhe wrap : listaWrap) {
                    regione = regioneService.findByNome(eaRegione.getNome());
                    provincia = provinciaService.findByNome(wrap.getPrima());
                    comuneText = wrap.getSeconda();
                    creaIfNotExist(regione, provincia, comuneText);
                }// end of for cycle
            }// end of if cycle
        }// end of for cycle

        return numRec;
    }// end of method


    public List<Comune> findItems(AEntity entityBean) {
        return findAllByProvincia((Provincia) entityBean);
    }// end of method

}// end of class