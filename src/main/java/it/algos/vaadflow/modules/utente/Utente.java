package it.algos.vaadflow.modules.utente;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.annotation.*;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EACompanyRequired;
import it.algos.vaadflow.enumeration.EAFieldType;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.modules.role.RoleService;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.TAG_UTE;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 13-set-2018 18.32.18 <br>
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Document (facoltativo) per avere un nome della collection (DB Mongo) diverso dal nome della Entity <br>
 * Annotated with @TypeAlias (facoltativo) to replace the fully qualified class name with a different value. <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Data (Lombok) for automatic use of Getter and Setter <br>
 * Annotated with @NoArgsConstructor (Lombok) for JavaBean specifications <br>
 * Annotated with @AllArgsConstructor (Lombok) per usare il costruttore completo nel Service <br>
 * Annotated with @Builder (Lombok) con un metodo specifico, per usare quello standard nella (eventuale) sottoclasse <br>
 * Annotated with @Builder (Lombok) lets you automatically produce the code required to have your class
 * be instantiable with code such as: Person.builder().name("Adam Savage").city("San Francisco").build(); <br>
 * Annotated with @EqualsAndHashCode (Lombok) per l'uguaglianza di due istanze della classe <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIEntity (facoltativo Algos) per alcuni parametri generali del modulo <br>
 * Annotated with @AIList (facoltativo Algos) per le colonne automatiche della Lista  <br>
 * Annotated with @AIForm (facoltativo Algos) per i fields automatici del Dialog e del Form <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * <p>
 * Inserisce SEMPRE la versione di serializzazione <br>
 * Le singole property sono pubbliche in modo da poterne leggere il valore tramite 'reflection' <br>
 * Le singole property sono annotate con @AIColumn (facoltativo Algos) per il tipo di Column nella Grid <br>
 * Le singole property sono annotate con @AIField (obbligatorio Algos) per il tipo di Field nel Dialog e nel Form <br>
 * Le singole property sono annotate con @Field("xxx") (facoltativo)
 * -which gives a name to the key to be used to store the field inside the document.
 * -The property name (i.e. 'descrizione') would be used as the field key if this annotation was not included.
 * -Remember that field keys are repeated for every document so using a smaller key name will reduce the required space.
 */

/**
 * Vengono usate SOLO le property indispensabili per la gestione della security <br>
 * Altre property, anche generiche, vanno nella sottoclasse anagrafica Person <br>
 */
@SpringComponent
@Document(collection = "utente")
@TypeAlias("utente")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderUtente")
@EqualsAndHashCode(callSuper = false)
@Qualifier(TAG_UTE)
@AIEntity(company = EACompanyRequired.facoltativa)
@AIList(fields = {"userName", "passwordInChiaro", "locked", "mail"})
@AIForm(fields = {"userName", "ruoli", "passwordInChiaro", "locked", "mail"})
@AIScript(sovrascrivibile = false)
public class Utente extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


//    /**
//     * Istanza (@Scope = 'singleton') inietta da Spring <br>
//     */
//    @Autowired
//    public UtenteService service;


    /**
     * userName o nickName (obbligatorio, unico)
     */
    @NotNull(message = "User's first name must not be null")
    @Field("user")
    @AIField(type = EAFieldType.text)
    public String userName;


    /**
     * password in chiaro (obbligatoria, non unica)
     */
    @Field("pass")
    @AIField(type = EAFieldType.text)
    public String passwordInChiaro;


    /**
     * flag locked (obbligatorio, di default false)
     */
    @Field("lock")
    @AIField(type = EAFieldType.checkbox)
    public boolean locked;


    /**
     * Ruoli attribuiti a questo utente (obbligatorio, non unico)
     * Siccome sono 'embedded' in utente, non serve @OneToMany() o @ManyToOne()
     */
    @Field("role")
    @AIField(type = EAFieldType.noone, required = true, clazz = RoleService.class)
    @AIColumn(name = "Ruolo", width = 200)
    public List<Role> ruoli;


    /**
     * posta elettronica (facoltativo)
     */
    @Field("mail")
    @AIField(type = EAFieldType.email, widthEM = 24)
    @AIColumn(width = 350, name = "Mail")
    public String mail;


//    public boolean isUser() {
//        return service.isUser(this);
//    }// end of method
//
//    public boolean isAdmin() {
//        return service.isAdmin(this);
//    }// end of method
//
//    public boolean isDev() {
//        return service.isDev(this);
//    }// end of method

}// end of entity class