package it.algos.vaadflow.modules.utente;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.annotation.*;
import it.algos.vaadflow.backend.entity.ACEntity;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EACompanyRequired;
import it.algos.vaadflow.enumeration.EAFieldType;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.modules.role.RoleService;
import lombok.*;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import static it.algos.vaadflow.application.FlowCost.TAG_UTE;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 25-ago-2018 9.53.12 <br>
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Document (facoltativo) per avere un nome della collection (DB mongo) diverso dal nome della Entity <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Data (Lombok) for automatic use of Getter and Setter <br>
 * Annotated with @NoArgsConstructor (Lombok) for JavaBean specifications <br>
 * Annotated with @AllArgsConstructor (Lombok) per usare il costruttore completo nel Service <br>
 * Annotated with @Builder (Lombok) con un metodo specifico, per usare quello standard nella (eventuale) sottoclasse <br>
 * Annotated with @Builder (Lombok) lets you automatically produce the code required to have your class
 * be instantiable with code such as: Person.builder().name("Adam Savage").city("San Francisco").build(); <br>
 * Annotated with @EqualsAndHashCode (Lombok) per l'uguaglianza di due istanze dellaq classe <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIEntity (facoltativo Algos) per alcuni parametri generali del modulo <br>
 * Annotated with @AIList (facoltativo Algos) per le colonne automatiche della Lista  <br>
 * Annotated with @AIForm (facoltativo Algos) per i fields automatici del Dialog e del Form <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * Inserisce SEMPRE la versione di serializzazione <br>
 * Le singole property sono pubbliche in modo da poterne leggere il valore tramite 'reflection' <br>
 * Le singole property sono annotate con @AIField (obbligatorio Algos) per il tipo di Field nel Dialog e nel Form <br>
 * Le singole property sono annotate con @AIColumn (facoltativo Algos) per il tipo di Column nella Grid <br>
 */
@SpringComponent
@Document(collection = "utente")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderUtente")
@EqualsAndHashCode(callSuper = false)
@Qualifier(TAG_UTE)
@AIEntity(company = EACompanyRequired.facoltativa)
@AIList(fields = {"username", "password", "enabled", "role"})
@AIForm(fields = {"username", "password", "enabled", "role"})
@AIScript(sovrascrivibile = false)
public class Utente extends ACEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public UtenteService service;


    @AIField(type = EAFieldType.text)
    public String username;

    @AIField(type = EAFieldType.text)
    public String password;

    @AIField(type = EAFieldType.checkbox)
    public boolean enabled;

    /**
     * ruolo (obbligatorio, non unico)
     * riferimento dinamico con @DBRef (obbligatorio per il ComboBox)
     */
    @DBRef
    @AIField(type = EAFieldType.combo, required = true, clazz = RoleService.class)
    @AIColumn(name = "Ruolo", width = 200)
    public Role role;

    public boolean isUser() {
        return service.isUser(this);
    }// end of method

    public boolean isAdmin() {
        return service.isAdmin(this);
    }// end of method

    public boolean isDev() {
        return service.isDev(this);
    }// end of method

}// end of entity class