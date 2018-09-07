package it.algos.vaadflow.modules.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.person.Person;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.algos.vaadflow.application.FlowCost.TAG_COM;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 3-set-2018 20.32.36 <br>
 * <br>
 * Estende la classe astratta AService. Layer di collegamento per la Repository. <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Service (ridondante) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_COM)
@Slf4j
@AIScript(sovrascrivibile = false)
public class CompanyService extends AService {

    private static final String ALGOS = "algos";
    private static final String DEMO = "algos";

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private CompanyRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola nella superclasse il modello-dati specifico <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public CompanyService(@Qualifier(TAG_COM) MongoRepository repository) {
        super(repository);
        super.entityClass = Company.class;
        this.repository = (CompanyRepository) repository;
    }// end of Spring constructor

//    /**
//     * Ricerca di una entity (la crea se non la trova) <br>
//     *
//     * @param code di riferimento interno (obbligatorio ed unico)
//     *
//     * @return la entity trovata o appena creata
//     */
//    public Company findOrCrea(String code) {
//        return findOrCrea(code, "");
//    }// end of method
//
//    /**
//     * Ricerca di una entity (la crea se non la trova) <br>
//     *
//     * @param code        di riferimento interno (obbligatorio ed unico)
//     * @param descrizione ragione sociale o descrizione della company (visibile - obbligatoria)
//     *
//     * @return la entity trovata o appena creata
//     */
//    public Company findOrCrea(String code, String descrizione) {
//        Company entity = findByKeyUnica(code);
//
//        if (entity == null) {
//            entity = newEntity(code, descrizione);
//            save(entity);
//        }// end of if cycle
//
//        return entity;
//    }// end of method


    /**
     * Crea una entity <br>
     * Se esiste già, la cancella prima di ricrearla <br>
     *
     * @param code        di riferimento interno (obbligatorio ed unico)
     * @param descrizione ragione sociale o descrizione della company (visibile - obbligatoria)
     * @param contatto    persona di riferimento (facoltativo)
     * @param telefono    della company (facoltativo)
     * @param email       della company (facoltativo)
     * @param indirizzo   della company (facoltativo)
     *
     * @return la entity trovata o appena creata
     */
    public Company crea(String code, String descrizione, Person contatto, String telefono, String email, Address indirizzo) {
        Company entity = findByKeyUnica(code);

        if (entity != null) {
            delete(entity);
        }// end of if cycle

        entity = newEntity(code, descrizione, contatto, telefono, email, indirizzo);
        save(entity);

        return entity;
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Company newEntity() {
        return newEntity("", "");
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Properties obbligatorie
     *
     * @param code        di riferimento interno (obbligatorio ed unico)
     * @param descrizione ragione sociale o descrizione della company (visibile - obbligatoria)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Company newEntity(String code, String descrizione) {
        return newEntity(code, descrizione, (Person) null, "", "", (Address) null);
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
     *
     * @param code        di riferimento interno (obbligatorio ed unico)
     * @param descrizione ragione sociale o descrizione della company (visibile - obbligatoria)
     * @param contatto    persona di riferimento (facoltativo)
     * @param telefono    della company (facoltativo)
     * @param email       della company (facoltativo)
     * @param indirizzo   della company (facoltativo)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Company newEntity(String code, String descrizione, Person contatto, String telefono, String email, Address indirizzo) {
        Company entity = null;

        entity = findByKeyUnica(code);
        if (entity != null) {
            return findByKeyUnica(code);
        }// end of if cycle

        entity = Company.builderCompany()
                .code(code != null ? code : "")
                .descrizione(descrizione.equals("") ? null : descrizione)
                .contatto(contatto)
                .telefono(telefono.equals("") ? null : telefono)
                .email(email.equals("") ? null : email)
                .indirizzo(indirizzo)
                .build();

        return (Company) creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Company findByKeyUnica(String code) {
        return repository.findByCode(code);
    }// end of method


    /**
     * Returns all instances of the type <br>
     * La Entity è EACompanyRequired.nonUsata. Non usa Company. <br>
     * Lista ordinata <br>
     *
     * @return lista ordinata di tutte le entities
     */
    @Override
    public List<Company> findAll() {
        List<Company> lista = null;

        try { // prova ad eseguire il codice
            lista = repository.findAllByOrderByCodeAsc();
        } catch (Exception unErrore2) { // intercetta l'errore
            log.error(unErrore2.toString());
            try { // prova ad eseguire il codice
                lista = repository.findAll();
            } catch (Exception unErrore3) { // intercetta l'errore
                log.error(unErrore3.toString());
            }// fine del blocco try-catch
        }// fine del blocco try-catch

        return lista;
    }// end of method

    /**
     * Property unica (se esiste).
     */
    public String getPropertyUnica(AEntity entityBean) {
        return ((Company) entityBean).getCode();
    }// end of method


    /**
     * Recupera dal db mongo la company (se esiste)
     */
    public Company getAlgos() {
        return findByKeyUnica(ALGOS);
    }// end of method

    /**
     * Recupera dal db mongo la company (se esiste)
     */
    public Company getDemo() {
        return findByKeyUnica(DEMO);
    }// end of method

}// end of class