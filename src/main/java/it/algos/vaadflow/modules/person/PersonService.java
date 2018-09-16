package it.algos.vaadflow.modules.person;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.address.AddressService;
import it.algos.vaadflow.modules.address.EAAddress;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.modules.utente.Utente;
import it.algos.vaadflow.modules.utente.UtenteService;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static it.algos.vaadflow.application.FlowCost.TAG_PER;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 13-set-2018 18.32.17 <br>
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

/**
 * La newEntity() usa il metodo newEntity() della superclasse per usare 'builderUtente' <br>
 */
@SpringComponent
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PER)
@Slf4j
@AIScript(sovrascrivibile = false)
public class PersonService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    protected UtenteService utenteService;


    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    protected AddressService addressService;

    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private PersonRepository repository;

    /**
     * Default constructor
     */
    public PersonService() {
    }// end of constructor

    /**
     * Costruttore <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola nella superclasse il modello-dati specifico <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public PersonService(@Qualifier(TAG_PER) MongoRepository repository) {
        super();
        super.entityClass = Person.class;

        this.repository = (PersonRepository) repository;
    }// end of Spring constructor

//    /**
//     * Creazione in memoria di una nuova entity che NON viene salvata <br>
//     * Recupera da StaticContextAccessor una istanza di questa stessa classe <br>
//     */
//    public static Person getNewPerson(String nome, String cognome) {
//        Person entity = null;
//        PersonService istanza = StaticContextAccessor.getBean(PersonService.class);
//
//        if (istanza != null) {
//            entity = istanza.newEntity(nome, cognome);
//        }// end of if cycle
//
//        return entity;
//    }// end of method

    /**
     * Crea una entity <br>
     * Se esiste già, la cancella prima di ricrearla <br>
     *
     * @param nome:            (obbligatorio, non unico)
     * @param cognome:         (obbligatorio, non unico)
     * @param telefono:        (facoltativo)
     * @param indirizzo:       via, nome e numero (facoltativo)
     * @param userName         userName o nickName (obbligatorio, unico)
     * @param passwordInChiaro password in chiaro (obbligatoria, non unica)
     *                         con inserimento automatico (prima del 'save') se è nulla
     * @param ruoli            Ruoli attribuiti a questo utente (lista di valori obbligatoria)
     *                         con inserimento del solo ruolo 'user' (prima del 'save') se la lista è nulla
     *                         lista modificabile solo da developer ed admin
     * @param mail             posta elettronica (facoltativo)
     *
     * @return la entity trovata o appena creata
     */
    public Person crea(
            String nome,
            String cognome,
            String telefono,
            Address indirizzo,
            String userName,
            String passwordInChiaro,
            List<Role> ruoli,
            String mail) {
        Person entity;

        entity = newEntity(nome, cognome, telefono, indirizzo, userName, passwordInChiaro, ruoli, mail);
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
    public Person newEntity() {
        return newEntity("", "");
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Properties obbligatorie
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok)
     *
     * @param nome:    obbligatorio
     * @param cognome: obbligatorio
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Person newEntity(String nome, String cognome) {
        return newEntity(nome, cognome, "", (Address) null);
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok)
     *
     * @param nome:      (obbligatorio, non unico)
     * @param cognome:   (obbligatorio, non unico)
     * @param telefono:  (facoltativo)
     * @param indirizzo: via, nome e numero (facoltativo)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Person newEntity(String nome, String cognome, String telefono, Address indirizzo) {
        return newEntity(nome, cognome, telefono, indirizzo, "", "", (List<Role>) null, "");
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
     * Utilizza, eventualmente, la newEntity() della superclasse, per le property della superclasse <br>
     *
     * @param nome:            (obbligatorio, non unico)
     * @param cognome:         (obbligatorio, non unico)
     * @param telefono:        (facoltativo)
     * @param indirizzo:       via, nome e numero (facoltativo)
     * @param userName         userName o nickName (obbligatorio, unico)
     * @param passwordInChiaro password in chiaro (obbligatoria, non unica)
     *                         con inserimento automatico (prima del 'save') se è nulla
     * @param ruoli            Ruoli attribuiti a questo utente (lista di valori obbligatoria)
     *                         con inserimento del solo ruolo 'user' (prima del 'save') se la lista è nulla
     *                         lista modificabile solo da developer ed admin
     * @param mail             posta elettronica (facoltativo)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Person newEntity(
            String nome,
            String cognome,
            String telefono,
            Address indirizzo,
            String userName,
            String passwordInChiaro,
            List<Role> ruoli,
            String mail) {
        Person entity = null;
        Utente entityDellaSuperClasseUtente = utenteService.newEntity(nome, cognome, ruoli, mail);

        //--casting dalla superclasse alla classe attuale
        entity = (Person) entityDellaSuperClasseUtente;

        //--regola le property di questa classe
        entity.setNome(nome.equals("") ? null : nome);
        entity.setCognome(cognome.equals("") ? null : cognome);
        entity.setTelefono(telefono.equals("") ? null : telefono);
        entity.setIndirizzo(indirizzo);

        return (Person) creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Operazioni eseguite PRIMA del save <br>
     * Regolazioni automatiche di property <br>
     *
     * @param entityBean da regolare prima del save
     *
     * @return the modified entity
     */
    @Override
    public AEntity beforeSave(AEntity entityBean) {
        Person entity = (Person) super.beforeSave(entityBean);

        return entity;
    }// end of method


    /**
     * Property unica (se esiste).
     */
    public String getPropertyUnica(AEntity entityBean) {
        return ((Person) entityBean).getNome() + ((Person) entityBean).getCognome();
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     *
     * @param eaPerson: enumeration di dati iniziali di prova
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Deprecated
    public Person newEntity(EAPerson eaPerson) {
        String nome;
        String cognome;
        String telefono;
        String email;
        EAAddress eaAddress;
        Address indirizzo;

        if (eaPerson != null) {
            nome = eaPerson.getNome();
            cognome = eaPerson.getCognome();
            telefono = eaPerson.getTelefono();
            email = eaPerson.getEmail();
            eaAddress = eaPerson.getAddress();
            indirizzo = addressService.newEntity(eaAddress);

            return newEntity(nome, cognome, telefono, indirizzo);
        } else {
            return null;
        }// end of if/else cycle
    }// end of method

}// end of class