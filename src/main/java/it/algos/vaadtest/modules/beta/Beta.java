package it.algos.vaadtest.modules.beta;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow.annotation.*;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EACompanyRequired;
import it.algos.vaadflow.enumeration.EAFieldType;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.ui.fields.AIndirizzo;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 14-ott-2019 18.56.46 <br>
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
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * - la documentazione precedente a questo tag viene SEMPRE riscritta <br>
 * - se occorre preservare delle @Annotation con valori specifici, spostarle DOPO @AIScript <br>
 * Annotated with @AIEntity (facoltativo Algos) per alcuni parametri generali del modulo <br>
 * Annotated with @AIList (facoltativo Algos) per le colonne automatiche della Grid nella lista <br>
 * Annotated with @AIForm (facoltativo Algos) per i fields automatici nel dialogo del Form <br>
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
 * (EAFieldType.link e XxxPresenter.class)
 * Le singole property possono essere annotate con @DBRef per un riferimento DINAMICO (not embedded)
 * (EAFieldType.combo e XXService.class, con inserimento automatico nel ViewDialog)
 * Una (e una sola) property deve avere @AIColumn(flexGrow = true) per fissare la larghezza della Grid <br>
 */
@Entity
@Document(collection = "beta")
@TypeAlias("beta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderBeta")
@EqualsAndHashCode(callSuper = false)
@AIScript(sovraScrivibile = false)
@AIEntity(recordName = "beta", company = EACompanyRequired.nonUsata)
//@AIList(fields = {"ordine", "code", "data", "datetime", "time", "icona", "posta", "password", "intero"})
@AIList(fields = {"ordine", "code"})
@AIForm(fields = {"ordine", "code", "indirizzo", "data", "datetime", "time", "icona", "posta", "password", "intero", "note"})
public class Beta extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * ordine di presentazione (obbligatorio, unico) <br>
     * il più importante per primo <br>
     */
    @NotNull
    @Indexed()
    @Field("ord")
    @AIField(type = EAFieldType.integer, widthEM = 3)
    @AIColumn(sortable = true, name = "#", widthEM = 5)
    public int ordine;

    /**
     * codice di riferimento (obbligatorio, unico) <br>
     */
    @NotNull(message = "Il codice è obbligatorio")
//    @Indexed()
    @Size(min = 2)
    @Field("cod")
    @AIField(type = EAFieldType.text, required = true, focus = true, widthEM = 8)
    @AIColumn(sortable = true, widthEM = 8)
    public String code;

    /**
     * icona (facoltativa)
     */
    @Field("icon")
    @AIField(type = EAFieldType.vaadinIcon, serviceClazz = TestIconService.class, widthEM = 8, color = "verde")
    @AIColumn(headerIcon = VaadinIcon.USERS, headerIconColor = "green")
    public VaadinIcon icona;

    /**
     * indirizzo (facoltativo, non unica)
     * riferimento dinamico CON @DBRef
     */
    @DBRef
    @Field("ind2")
    @AIField(type = EAFieldType.custom, serviceClazz = AIndirizzo.class, help = "Indirizzo")
    @AIColumn(name = "ind2")
    public Address indirizzo;

    @AIField(type = EAFieldType.localdate)
    public LocalDate data;

    @AIField(type = EAFieldType.localdatetime)
    public LocalDateTime datetime;

    @AIField(type = EAFieldType.localtime)
    public LocalTime time;

    @AIField(type = EAFieldType.email)
    public String posta;

    @AIField(type = EAFieldType.password)
    public String password;

    @AIField(type = EAFieldType.integer)
    public int intero;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return code;
    }// end of method


}// end of entity class