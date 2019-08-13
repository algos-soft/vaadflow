package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow.annotation.*;
import it.algos.vaadflow.backend.entity.ACEntity;
import it.algos.vaadflow.enumeration.EACompanyRequired;
import it.algos.vaadflow.enumeration.EAFieldType;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.address.AddressPresenter;
import it.algos.vaadflow.modules.address.AddressService;
import it.algos.vaadflow.modules.mese.Mese;
import it.algos.vaadflow.modules.mese.MeseService;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.modules.role.RoleService;
import it.algos.vaadflow.modules.secolo.Secolo;
import it.algos.vaadflow.modules.secolo.SecoloService;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
@AIList(fields = {"ordine", "pageid", "code", "colore","descrizione", "inizio", "fine", "durataOre", "durataMinuti", "durataTempo", "sino", "box", "yesno", "yesnobold", "ruoli", "lastModifica"})
@AIForm(fields = {"ordine", "pageid", "code", "descrizione", "inizio", "listaA", "listaB", "listaC", "sino", "box", "yesno", "yesnobold", "ruoli", "lastModifica", "mese", "secolo", "indirizzoStatico", "indirizzoDinamico"})
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
    @AIField(type = EAFieldType.integer)
    @AIColumn(name = "#")
    public int ordine;


    /**
     * ordine di presentazione (obbligatorio, unico) <br>
     * il più importante per primo <br>
     */
    @NotNull
    @Indexed()
    @Field("pageid")
    @AIField(type = EAFieldType.lungo)
    @AIColumn(name = "page")
    public long pageid;

    /**
     * codice di riferimento (obbligatorio, unico) <br>
     */
    @NotNull
    @Indexed(unique = true)
    @Size(min = 3)
    @Field("cod")
    @AIField(type = EAFieldType.text, required = true, focus = true)
    public String code;

    /**
     * descrizione (obbligatorio, unico) <br>
     */
    @NotNull
    @Field("coll")
    @AIField(type = EAFieldType.color, widthEM = 2)
    @AIColumn()
    public String colore;
    /**
     * descrizione (obbligatorio, unico) <br>
     */
    @NotNull
    @Field("desc")
    @AIField(type = EAFieldType.text, widthEM = 2)
    @AIColumn(flexGrow = true)
    public String descrizione;


    /**
     * booleano
     */
    @Field("bl")
    @AIField(type = EAFieldType.booleano)
    @AIColumn(name = "bl")
    public boolean sino;

    /**
     * booleano
     */
    @Field("sn")
    @AIField(type = EAFieldType.checkbox)
    @AIColumn(name = "sn")
    public boolean box;


    /**
     * booleano
     */
    @Field("yn")
    @AIField(type = EAFieldType.yesno)
    @AIColumn(name = "yn")
    public boolean yesno;

    /**
     * booleano
     */
    @Field("ynb")
    @AIField(type = EAFieldType.yesnobold)
    @AIColumn(name = "ynb")
    public boolean yesnobold;

    /**
     * booleano
     */
    @Field("ini")
    @AIField(type = EAFieldType.localtime)
    @AIColumn(headerIcon = VaadinIcon.CLOCK, headerIconColor = "green")
    public LocalTime inizio;

    /**
     * booleano
     */
    @Field("fin")
    @AIField(type = EAFieldType.localtime)
    @AIColumn(headerIcon = VaadinIcon.CLOCK, headerIconColor = "red")
    public LocalTime fine;

    /**
     * Durata prevista (in ore) del servizio (obbligatoria, calcolata in automatico prima del Save)
     * Informazione ridondante ma comoda per le successive elaborazioni (turno, tabellone, iscrizioni, statistiche)
     */
    @Transient
    @Field("dur")
    @AIField(type = EAFieldType.calculated, name = "Ore", serviceClazz = ProvaService.class)
    @AIColumn(headerIcon = VaadinIcon.PROGRESSBAR, methodName = "durataOre")
    public int durataOre;

    /**
     * Durata prevista (in ore) del servizio (obbligatoria, calcolata in automatico prima del Save)
     * Informazione ridondante ma comoda per le successive elaborazioni (turno, tabellone, iscrizioni, statistiche)
     */
    @Transient
    @Field("dur2")
    @AIField(type = EAFieldType.calculated, name = "Minuti", serviceClazz = ProvaService.class)
    @AIColumn(headerIcon = VaadinIcon.PROGRESSBAR,  methodName = "durataMinuti")
    public int durataMinuti;

    /**
     * Durata prevista (in ore) del servizio (obbligatoria, calcolata in automatico prima del Save)
     * Informazione ridondante ma comoda per le successive elaborazioni (turno, tabellone, iscrizioni, statistiche)
     */
    @Transient
    @Field("dur23")
    @AIField(type = EAFieldType.calculated, name = "Tempo", serviceClazz = ProvaService.class)
    @AIColumn(headerIcon = VaadinIcon.PROGRESSBAR, methodName = "durataTempo")
    public int durataTempo;

    /**
     * enumeration
     */
    @Field("lisA")
    @AIField(type = EAFieldType.enumeration, items = "alfa,beta,gamma")
    @AIColumn(name = "lisA")
    public String listaA;

    /**
     * enumeration
     */
    @Field("lisB")
    @AIField(type = EAFieldType.enumeration, enumClazz = EACompanyRequired.class)
    @AIColumn(name = "lisB")
    public String listaB;

    /**
     * enumeration
     */
    @Field("lisC")
    @AIField(type = EAFieldType.enumeration, serviceClazz = RoleService.class)
    @AIColumn(name = "lisC")
    public String listaC;


    /**
     * combo multiplo <br>
     */
    @Field("rol")
    @AIField(type = EAFieldType.multicombo, serviceClazz = RoleService.class, widthEM = 4)
    @AIColumn(widthEM = 6)
    public List<Role> ruoli;


    @Indexed(direction = IndexDirection.DESCENDING)
    @AIField(name = "last", type = EAFieldType.localdatetime, required = true, help = "ultima modifica della voce effettuata sul server wiki")
    public LocalDateTime lastModifica;


    /**
     * indirizzo (facoltativo, non unica)
     * riferimento statico SENZA @DBRef (embedded)
     */
    @Field("ind")
    @AIField(type = EAFieldType.link, linkClazz = AddressPresenter.class, help = "Indirizzo")
    @AIColumn(name = "ind")
    public Address indirizzoStatico;


    /**
     * indirizzo (facoltativo, non unica)
     * riferimento dinamico CON @DBRef
     */
    @DBRef
    @Field("ind2")
    @AIField(type = EAFieldType.combo, serviceClazz = AddressService.class, help = "Indirizzo")
    @AIColumn(name = "ind2")
    public Address indirizzoDinamico;

    /**
     * mese di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @Field("mese")
    @AIField(type = EAFieldType.combo, serviceClazz = MeseService.class)
    @AIColumn(widthEM = 8)
    public Mese mese;


    /**
     * secolo di riferimento (obbligatorio)
     * riferimento dinamico CON @DBRef
     */
    @NotNull
    @DBRef
    @Field("secolo")
    @AIField(type = EAFieldType.combo, serviceClazz = SecoloService.class)
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