package it.algos.vaadtest.modules.prova;

import it.algos.vaadflow.annotation.*;
import it.algos.vaadflow.backend.entity.ACEntity;
import it.algos.vaadflow.enumeration.EACompanyRequired;
import it.algos.vaadflow.enumeration.EAFieldType;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.address.AddressPresenter;
import it.algos.vaadflow.modules.address.AddressService;
import it.algos.vaadflow.modules.mese.Mese;
import it.algos.vaadflow.modules.mese.MeseService;
import it.algos.vaadflow.modules.secolo.Secolo;
import it.algos.vaadflow.modules.secolo.SecoloService;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-ott-2018 18.52.31 <br>
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * <p>
 * Not annotated with @SpringComponent (inutile).  <br>
 * Not annotated with @Scope (inutile). Le istanze 'prototype' vengono generate da xxxService.newEntity() <br>
 * Not annotated with @Qualifier (inutile) <br>
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
 * Le singole property sono annotate con @AIColumn (facoltativo Algos) per il tipo di Column nella Grid <br>
 * Le singole property sono annotate con @AIField (obbligatorio Algos) per il tipo di fields nel dialogo del Form <br>
 * Le singole property sono annotate con @Field("xxx") (facoltativo)
 * -which gives a name to the key to be used to store the field inside the document.
 * -The property name (i.e. 'descrizione') would be used as the field key if this annotation was not included.
 * -Remember that field keys are repeated for every document so using a smaller key name will reduce the required space.
 */
//@Entity
@Document(collection = "prova")
@TypeAlias("prova")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderProva")
@EqualsAndHashCode(callSuper = false)
@AIEntity(company = EACompanyRequired.facoltativa)
@AIList(fields = {"ordine", "code", "descrizione", "sino", "lastModifica", "meseStatico", "meseDinamico"})
@AIForm(fields = {"ordine", "code", "descrizione", "sino", "lastModifica", "mese", "secolo", "indirizzoStatico", "indirizzoDinamico"})
@AIScript(sovrascrivibile = false)
public class Prova extends ACEntity {


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
    @AIColumn(name = "#")
    public int ordine;

    /**
     * codice di riferimento (obbligatorio, unico) <br>
     */
    @NotNull
    @Indexed(unique = true)
    @Size(min = 3)
    @Field("cod")
    @AIField(type = EAFieldType.text, required = true, focus = true, widthEM = 8)
    @AIColumn(widthEM = 6)
    public String code;

    /**
     * descrizione (obbligatorio, unico) <br>
     */
    @NotNull
    @Field("desc")
    @AIField(type = EAFieldType.text, widthEM = 2)
    @AIColumn(name = "Desc", flexGrow = true, sortable = false)
    public String descrizione;


    /**
     * booleano
     */
    @Field("sn")
    @AIField(type = EAFieldType.checkbox)
    @AIColumn(name = "sn")
    public boolean sino;


    @Indexed(direction = IndexDirection.DESCENDING)
    @AIField(name = "last", type = EAFieldType.localdatetime, required = true, help = "ultima modifica della voce effettuata sul server wiki")
    @AIColumn(widthEM = 16)
    public LocalDateTime lastModifica;


    /**
     * indirizzo (facoltativo, non unica)
     * riferimento statico SENZA @DBRef (embedded)
     */
    @Field("ind")
    @AIField(type = EAFieldType.link, clazz = AddressPresenter.class, help = "Indirizzo")
    @AIColumn(name = "ind")
    public Address indirizzoStatico;


    /**
     * indirizzo (facoltativo, non unica)
     * riferimento dinamico CON @DBRef
     */
    @DBRef
    @Field("ind2")
    @AIField(type = EAFieldType.combo, clazz = AddressService.class, help = "Indirizzo")
    @AIColumn(name = "ind2")
    public Address indirizzoDinamico;

    /**
     * mese di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @Field("mese")
    @AIField(type = EAFieldType.combo, clazz = MeseService.class)
    @AIColumn(widthEM = 8)
    public Mese mese;


    /**
     * secolo di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @Field("secolo")
    @AIField(type = EAFieldType.combo, clazz = SecoloService.class)
    @AIColumn(widthEM = 8)
    public Secolo secolo;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return code;
    }// end of method


}// end of entity class