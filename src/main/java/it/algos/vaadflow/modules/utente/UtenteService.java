package it.algos.vaadflow.modules.utente;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.company.Company;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.modules.role.RoleService;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import static it.algos.vaadflow.application.FlowCost.TAG_UTE;

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
@Qualifier(TAG_UTE)
@Slf4j
@AIScript(sovrascrivibile = false)
public class UtenteService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public RoleService roleService;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private UtenteRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola nella superclasse il modello-dati specifico <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public UtenteService(@Qualifier(TAG_UTE) MongoRepository repository) {
        super(repository);
        super.entityClass = Utente.class;
        this.repository = (UtenteRepository) repository;
   }// end of Spring constructor

    /**
     * Ricerca di una entity (la crea se non la trova) <br>
     *
     * @param username di riferimento (obbligatorio, unico per tutta l'applicazione)
     * @param password (obbligatoria, non unica)
     *
     * @return la entity trovata o appena creata
     */
    public Utente findOrCrea(String username, String password) {
        Utente entity = findByUsername(username);

        if (entity == null) {
            crea(username, password);
        }// end of if cycle

        return entity;
    }// end of method


    /**
     * Crea una entity e la registra <br>
     *
     * @param company (obbligatoria)
     * @param username di riferimento (obbligatorio, unico per tutta l'applicazione)
     * @param password (obbligatoria, non unica)
     *
     * @return la entity appena creata
     */
    public Utente crea(Company company, String username, String password) {
        Utente entity;

        entity = newEntity(username, password);
        entity.company = company;
        save(entity);

        return entity;
    }// end of method


    /**
     * Crea una entity e la registra <br>
     *
     * @param username di riferimento (obbligatorio, unico per tutta l'applicazione)
     * @param password (obbligatoria, non unica)
     *
     * @return la entity appena creata
     */
    public Utente crea(String username, String password) {
        Utente entity;

        entity = newEntity(username, password);
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
     public Utente newEntity() {
         return newEntity("", "", (Role) null);
     }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Properties obbligatorie <br>
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
     *
     * @param username di riferimento (obbligatorio, unico per tutta l'applicazione)
     * @param password (obbligatoria, non unica)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Utente newEntity(String username, String password) {
        return newEntity(username, password, (Role) null);
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
     *
     * @param username di riferimento (obbligatorio, unico per tutta l'applicazione)
     * @param password (obbligatoria, non unica)
     * @param role     ruolo (obbligatorio, non unico)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Utente newEntity(String username, String password, Role role) {
        Utente entity = null;

        entity = findByUsername(username);
        if (entity != null) {
            return findByUsername(username);
        }// end of if cycle

        entity = Utente.builderUtente()
                .username(username.equals("") ? null : username)
                .password(password.equals("") ? null : password)
                .enabled(true)
                .role(role != null ? role : roleService.getUser())
                .build();

        return (Utente)creaIdKeySpecifica(entity);
    }// end of method


    /**
     * Property unica (se esiste).
     */
    public String getPropertyUnica(AEntity entityBean) {
        return ((Utente) entityBean).getUsername();
    }// end of method

    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param username di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Utente findByUsername(String username) {
        return repository.findByUsername(username);
    }// end of method

    public boolean isUser(Utente utente) {
        return utente.role == roleService.getUser() || isAdmin(utente);
    }// end of method

    public boolean isAdmin(Utente utente) {
        return utente.role == roleService.getAdmin() || isDev(utente);
    }// end of method

    public boolean isDev(Utente utente) {
        return utente.role == roleService.getDeveloper();
    }// end of method

}// end of class