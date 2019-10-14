package it.algos.vaadflow.modules.versione;

import it.algos.vaadflow.annotation.*;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EACompanyRequired;
import it.algos.vaadflow.enumeration.EAFieldType;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-set-2019 21.19.40 <br>
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * <p>
 * Not annotated with @SpringComponent (inutile).  <br>
 * Not annotated with @Scope (inutile). Le istanze 'prototype' vengono generate da xxxService.newEntity() <br>
 * Not annotated with @Qualifier (inutile) <br>
 * Annotated with @Entity (facoltativo) per specificare che si tratta di una collection (DB Mongo) <br>
 * Annotated with @Document (facoltativo) per avere un nome della collection (DB Mongo) diverso dal nome della Entity <br>
 * Annotated with @TypeAlias (facoltativo) to replace the fully qualified class name with a different value. <br>
 * Annotated with @Data (Lombok) for automatic use of Getter and Setter <br>
 * Annotated with @NoArgsConstructor (Lombok) for JavaBean specifications <br>
 * Annotated with @AllArgsConstructor (Lombok) per usare il costruttore completo nel Service <br>
 * Annotated with @Builder (Lombok) con un metodo specifico, per usare quello standard nella (eventuale) sottoclasse <br>
 * - lets you automatically produce the code required to have your class be instantiable with code such as:
 * - Person.builder().name("Adam Savage").city("San Francisco").build(); <br>
 * Annotated with @EqualsAndHashCode (Lombok) per l'uguaglianza di due istanze della classe <br>
 * Annotated with @AIEntity (facoltativo Algos) per alcuni parametri generali del modulo <br>
 * Annotated with @AIList (facoltativo Algos) per le colonne automatiche della Grid nella lista <br>
 * Annotated with @AIForm (facoltativo Algos) per i fields automatici nel dialogo del Form <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * <p>
 * Inserisce SEMPRE la versione di serializzazione <br>
 * Le singole property sono pubbliche in modo da poterne leggere il valore tramite 'reflection' <br>
 * Le singole property sono annotate con @AIField (obbligatorio Algos) per il tipo di fields nel dialogo del Form <br>
 * Le singole property sono annotate con @AIColumn (facoltativo Algos) per il tipo di Column nella Grid <br>
 * Le singole property sono annotate con @Field("xxx") (facoltativo)
 * -which gives a name to the key to be used to store the field inside the document.
 * -The property name (i.e. 'descrizione') would be used as the field key if this annotation was not included.
 * -Remember that field keys are repeated for every document so using a smaller key name will reduce the required space.
 * Le property non primitive, di default sono EMBEDDED con un riferimento statico
 *      (EAFieldType.link e XxxPresenter.class)
 * Le singole property possono essere annotate con @DBRef per un riferimento DINAMICO (not embedded)
 *      (EAFieldType.combo e XXService.class, con inserimento automatico nel ViewDialog)
 * Una (e una sola) property deve avere @AIColumn(flexGrow = true) per fissare la larghezza della Grid <br>
 */
@Entity
@Document(collection = "versione")
@TypeAlias("versione")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderVersione")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovrascrivibile = false)
@AIEntity(recordName = "versione", company = EACompanyRequired.nonUsata)
@AIList(fields = {"id", "titolo", "descrizione", "timestamp"})
@AIForm(fields = {"id", "titolo", "descrizione", "timestamp"})
public class Versione extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * titolo della versione (obbligatorio, non unico) <br>
     */
    @NotNull
    @Indexed(direction = IndexDirection.DESCENDING)
    @Size(min = 3)
    @Field("tit")
    @AIField(type = EAFieldType.text, required = true, focus = true, widthEM = 12)
    @AIColumn(widthEM = 7)
    public String titolo;

    /**
     * nome descrittivo della versione (obbligatorio, unico) <br>
     */
    @NotNull(message = "La descrizione è obbligatoria")
    @Size(min = 2, max = 50)
    @Field("desc")
    @AIField(type = EAFieldType.textarea, firstCapital = true, widthEM = 24)
    @AIColumn(flexGrow = true)
    public String descrizione;

    /**
     * momento in cui si effettua la modifica della versione (obbligatorio, non unica) <br>
     * inserito automaticamente prima del persist <br>
     */
    @NotNull(message = "Il tempo è obbligatorio")
    @Indexed(direction = IndexDirection.DESCENDING)
    @Field("time")
    @AIField(type = EAFieldType.localdate, widthEM = 20)
    public LocalDate timestamp;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return id;
    }// end of method


}// end of entity class