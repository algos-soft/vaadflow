package it.algos.vaadflow.modules.versione;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.annotation.*;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EACompanyRequired;
import it.algos.vaadflow.enumeration.EAFieldType;
import lombok.*;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static it.algos.vaadflow.application.FlowCost.TAG_VER;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 23-ago-2018 19.27.16 <br>
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
@Document(collection = "versione")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderVersione")
@EqualsAndHashCode(callSuper = false)
@Qualifier(TAG_VER)
@AIEntity(company = EACompanyRequired.nonUsata)
@AIList(fields = {"id", "titolo", "nome", "timestamp"})
@AIForm(fields = {"id", "titolo","nome", "timestamp"})
@AIScript(sovrascrivibile = false)
public class Versione extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * titolo della versione (obbligatorio, non unico) <br>
     */
    @NotNull
    @Indexed()
    @Size(min = 3)
    @AIField(type = EAFieldType.text, required = true, focus = true, widthEM = 12)
    @AIColumn(widthPX = 1)
    public String titolo;

    /**
     * nome descrittivo della versione (obbligatorio, unico) <br>
     */
    @NotNull(message = "La descrizione è obbligatoria")
    @Size(min = 2, max = 50)
    @AIField(type = EAFieldType.text, firstCapital = true, widthEM = 24)
    @AIColumn(widthPX = 12)
    public String nome;

    /**
     * momento in cui si effettua la modifica della versione (obbligatorio, non unica) <br>
     * inserito automaticamente prima del persist <br>
     */
    @NotNull(message = "Il tempo è obbligatorio")
    @Size(min = 2, max = 50)
    @AIField(type = EAFieldType.localdatetime, firstCapital = true, widthEM = 24)
    @AIColumn(widthPX = 5)
    public LocalDateTime timestamp;

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return id;
    }// end of method


}// end of entity class