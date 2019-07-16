package it.algos.vaadflow.service;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import it.algos.vaadflow.application.StaticContextAccessor;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAFieldType;
import it.algos.vaadflow.modules.preferenza.EAPrefType;
import it.algos.vaadflow.modules.preferenza.PreferenzaService;
import it.algos.vaadflow.ui.fields.ACheckBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 23-set-2018
 * Time: 21:18
 * <p>
 * Gestisce la creazione delle colonne della Grid nel tipo adeguato <br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti Spring non la costruisce <br>
 * Implementa il 'pattern' SINGLETON; l'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AColumnService.class); <br>
 * 2) AColumnService.getInstance(); <br>
 * 3) @Autowired private AColumnService columnService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, basta il 'pattern') <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 */
@Service
@Slf4j
public class AColumnService extends AbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * Private final property
     */
    private static final AColumnService INSTANCE = new AColumnService();


    /**
     * Service (pattern SINGLETON) recuperato come istanza dalla classe <br>
     * The class MUST be an instance of Singleton Class and is created at the time of class loading <br>
     */
    public PreferenzaService pref;


    /**
     * Private constructor to avoid client applications to use constructor
     */
    private AColumnService() {
    }// end of constructor


    /**
     * Gets the unique instance of this Singleton.
     *
     * @return the unique instance of this Singleton
     */
    public static AColumnService getInstance() {
        return INSTANCE;
    }// end of static method


    /**
     * Create a single columnService.
     * The columnService type is chosen according to the annotation @AIColumn or, if is not present, a @AIField.
     *
     * @param grid         a cui aggiungere la colonna
     * @param entityClazz  modello-dati specifico
     * @param propertyName della property
     */
    public void create(Grid<AEntity> grid, Class<? extends AEntity> entityClazz, String propertyName) {
        pref = StaticContextAccessor.getBean(PreferenzaService.class);
        Grid.Column<AEntity> colonna = null;
        EAFieldType type = annotation.getFormType(entityClazz, propertyName);
        String header = annotation.getColumnName(entityClazz, propertyName);
        String width = annotation.getColumnWithEM(entityClazz, propertyName);
        boolean isFlexGrow = annotation.isFlexGrow(entityClazz, propertyName);
        Class clazz = annotation.getComboClass(entityClazz, propertyName);
        String color = annotation.getColumnColor(entityClazz, propertyName);
        boolean sortable = annotation.isSortable(entityClazz, propertyName);

        if (type == null) {
            try { // prova ad eseguire il codice
                colonna = grid.addColumn(propertyName);
                colonna.setSortProperty(propertyName);
            } catch (Exception unErrore) { // intercetta l'errore
                log.error(unErrore.toString());
            }// fine del blocco try-catch
            return;
        }// end of if cycle

        switch (type) {
            case text://@todo in futuro vanno differenziati
            case email:
            case textarea:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    String testo = "";

                    try { // prova ad eseguire il codice
                        if (field.get(entity) instanceof String) {
                            testo = (String) field.get(entity);
                        }// end of if cycle
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    return new Label(testo);
                }));//end of lambda expressions and anonymous inner class
                break;
            case integer:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    String testo = "";
                    int value;

                    try { // prova ad eseguire il codice
                        value = field.getInt(entity);
                        testo = text.format(value);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    return new Label(testo);
                }));//end of lambda expressions and anonymous inner class
                break;
            case lungo:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    String testo = "";
                    long value;

                    try { // prova ad eseguire il codice
                        value = field.getLong(entity);
                        testo = text.format(value);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    return new Label(testo);
                }));//end of lambda expressions and anonymous inner class
                break;
            case booleano:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    boolean status = false;
                    Icon icon;

                    try { // prova ad eseguire il codice
                        status = field.getBoolean(entity);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    if (status) {
                        icon = new Icon(VaadinIcon.CHECK);
                        icon.setColor("green");
                    } else {
                        icon = new Icon(VaadinIcon.CLOSE);
                        icon.setColor("red");
                    }// end of if/else cycle
                    icon.setSize("1em");

                    return icon;
                }));//end of lambda expressions and anonymous inner class
                break;
            case checkbox:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    boolean status = false;

                    try { // prova ad eseguire il codice
                        status = field.getBoolean(entity);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    return new ACheckBox(status);
                }));//end of lambda expressions and anonymous inner class
                break;
            case yesno:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    Label label = new Label();
                    String testo = "";
                    boolean status = false;

                    try { // prova ad eseguire il codice
                        status = field.getBoolean(entity);
                        testo = status ? "si" : "no";
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    if (text.isValid(testo)) {
                        label.setText(testo);
                        if (status) {
                            label.getStyle().set("color", "green");
                        } else {
                            label.getStyle().set("color", "red");
                        }// end of if/else cycle
                    }// end of if cycle

                    return label;
                }));//end of lambda expressions and anonymous inner class
                break;
            case yesnobold:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    Label label = new Label();
                    String testo = "";
                    boolean status = false;

                    try { // prova ad eseguire il codice
                        status = field.getBoolean(entity);
                        testo = status ? "si" : "no";
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    if (text.isValid(testo)) {
                        label.setText(testo);
                        label.getStyle().set("font-weight", "bold");
                        if (status) {
                            label.getStyle().set("color", "green");
                        } else {
                            label.getStyle().set("color", "red");
                        }// end of if/else cycle
                    }// end of if cycle

                    return label;
                }));//end of lambda expressions and anonymous inner class
                break;
            case enumeration:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Object obj = reflection.getPropertyValue(entity, propertyName);
                    return new Label(obj.toString());
                }));//end of lambda expressions and anonymous inner class
                break;
            case combo:
//                colonna = grid.addColumn(propertyName);
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    ComboBox combo = new ComboBox();
                    Object entityBean = reflection.getPropertyValue(entity, propertyName);
                    IAService service = (IAService) StaticContextAccessor.getBean(clazz);
                    List items = ((IAService) service).findAll();
                    if (array.isValid(items)) {
                        combo.setItems(items);
                        combo.setValue(entityBean);
                    }// end of if cycle
                    combo.setEnabled(false);
                    return combo;
                }));
                break;
            case weekdate:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    LocalDate data;
                    String testo = "X";
                    try { // prova ad eseguire il codice
                        data = (LocalDate) field.get(entity);
                        testo = date.getDayWeekShort(data);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    return new Label(testo);
                }));//end of lambda expressions and anonymous inner class
                break;
            case localdate:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    LocalDate data;
                    String testo = "Y";

                    try { // prova ad eseguire il codice
                        data = (LocalDate) field.get(entity);
                        testo = date.getDate(data);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    return new Label(testo);
                }));//end of lambda expressions and anonymous inner class
                break;
            case localdatetime:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Field field = reflection.getField(entityClazz, propertyName);
                    Object obj;
                    LocalDateTime timeStamp;
                    String testo = "Y";

                    try { // prova ad eseguire il codice
                        obj = field.get(entity);
                        if (obj instanceof LocalDateTime) {
                            timeStamp = (LocalDateTime) obj;
                            testo = date.getTime(timeStamp); //@todo aggiungere un selettore per modificare il format dalla annotation
                        } else {
                            log.warn("localdatetime non definito");
                        }// end of if/else cycle
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    return new Label(testo);
                }));//end of lambda expressions and anonymous inner class
                break;
            case vaadinIcon:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Icon icon = null;
                    VaadinIcon vaadinIcon;
                    Field field = reflection.getField(entityClazz, propertyName);
                    try { // prova ad eseguire il codice
                        vaadinIcon = (VaadinIcon) field.get(entity);
                        icon = vaadinIcon.create();
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch
                    if (text.isValid(color) && icon != null) {
                        icon.getElement().getClassList().add(color);
                    }// end of if cycle

                    return icon != null ? icon : new Label("");
                }));//end of lambda expressions and anonymous inner class
                break;
            case link:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Object obj = null;
                    Field field = reflection.getField(entityClazz, propertyName);
                    try { // prova ad eseguire il codice
                        obj = field.get(entity);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch
                    return new Label(obj != null ? obj.toString() : "");
                }));//end of lambda expressions and anonymous inner class
                break;
            case pref:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    EAPrefType typePref = (EAPrefType) reflection.getPropertyValue(entity, "type");
                    byte[] bytes = null;
                    Object value = null;
                    Field field = reflection.getField(entityClazz, propertyName);
                    try { // prova ad eseguire il codice
                        bytes = (byte[]) field.get(entity);
                        value = typePref.bytesToObject(bytes);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch

                    switch (typePref) {
                        case string:
                            break;
                        case bool:
                            boolean status=(boolean)value;
                            Label label = new Label(status ? "si" : "no");
                            label.getStyle().set("font-weight", "bold");
                            if (status) {
                                label.getStyle().set("color", "green");
                            } else {
                                label.getStyle().set("color", "red");
                            }// end of if/else cycle
                            return label;
                        case integer:
                            return new Label(text.format(value));
                        case date:
                            break;
                        case email:
                            break;
                        default:
                            log.warn("Switch - caso non definito");
                            break;
                    } // end of switch statement
                    return new Label(value != null ? value.toString() : "");

//                    if (typePref == EAPrefType.bool) {
//                        return new Checkbox((boolean) value ? "si" : "no", (boolean) value);
//                    } else {
//                        return new Label(value != null ? value.toString() : "");
//                    }// end of if/else cycle
                }));//end of lambda expressions and anonymous inner class
                break;
            default:
                log.warn("Switch - caso non definito");
                break;
        } // end of switch statement


        switch (type) {
            case text://@todo in futuro vanno differenziati
            case textarea:
                //--larghezza di default per un testo = 5em
                //--per larghezze minori o maggiori, inserire widthEM = ... nell'annotation @AIColumn della Entity
                width = text.isValid(width) ? width : "5em";
                break;
            case email:
                //--larghezza di default per un mail = 18em
                //--per larghezze diverse, inserire widthEM = ... nell'annotation @AIColumn della Entity
                width = text.isValid(width) ? width : "18em";
                break;
            case integer:
                //--larghezza di default per un intero = 3em
                //--gestisce numeri fino a 999
                //--per numeri più grandi, inserire widthEM = ... nell'annotation @AIColumn della Entity
                //--oppure usare EAFieldType.lungo nell'annotation @AIField della Entity
                width = text.isValid(width) ? width : "3em";
                break;
            case lungo:
                //--larghezza di default per un lungo = 7em
                //--gestisce numeri fino a 9.999.999
                width = text.isValid(width) ? width : "7em";
                break;
            case booleano:
                //--larghezza fissa per un booleano reso come icona = 2em
                //--può essere aumentata con widthEM = ... nell'annotation @AIColumn della Entity
                //  se si vuole usare un header con un testo più lungo di 2em
                width = text.isValid(width) ? width : "2em";
                break;
            case checkbox:
            case yesno:
            case yesnobold:
                //--larghezza fissa per un booleano reso come checkbox oppure come testo si/no = 2.5em
                //--può essere aumentata con widthEM = ... nell'annotation @AIColumn della Entity
                //  se si vuole usare un header con un testo più lungo di 2.5em
                width = text.isValid(width) ? width : "2.5em";
                break;
            case enumeration:
                break;
            case combo:
                break;
            case weekdate:
                break;
            case localdate:
                break;
            case localdatetime:
                //--larghezza di default per un data+tempo = 10em
                //--vale per la formattazione standard della data
                //--per modificare, inserire widthEM = ... nell'annotation @AIColumn della Entity
                width = text.isValid(width) ? width : "10em";
                break;
            case vaadinIcon:
                break;
            case link:
                break;
            case pref:
                break;
            default:
                log.warn("Switch - caso non definito");
                break;
        } // end of switch statement

        if (colonna != null) {
            //--l'header viene sempre minuscolo ed uguale al nome della property
            //--può essere modificata con name = "Xyz" nell'annotation @AIColumn della Entity
            colonna.setHeader(text.isValid(header) ? header : propertyName.toLowerCase());

            //--di default le colonne NON sono sortable
            //--può essere modificata con sortable = true, nell'annotation @AIColumn della Entity
            colonna.setSortable(sortable);
//            colonna.setSortProperty(propertyName);

//            if (property.equals("id")) {
//                colonna.setWidth("1px");
//            }// end of if cycle

            if (isFlexGrow) {
                colonna.setFlexGrow(1);
            } else {
                if (text.isValid(width)) {
                    colonna.setWidth(width);
                    colonna.setFlexGrow(0);
                }// end of if cycle
            }// end of if/else cycle

        }// end of if cycle

    }// end of method


    /**
     * Regola una singola colonna <br>
     * The columnService type is chosen according to the annotation @AIColumn or, if is not present, a @AIField <br>
     *
     * @param colonna      da regolare
     * @param entityClazz  modello-dati specifico
     * @param propertyName della property
     */
    public void fixColumn(Grid.Column colonna, Class<? extends AEntity> entityClazz, String propertyName) {
        String header = annotation.getColumnName(entityClazz, propertyName);
        String width = annotation.getColumnWithEM(entityClazz, propertyName);
        boolean isFlexGrow = annotation.isFlexGrow(entityClazz, propertyName);
        boolean sortable = annotation.isSortable(entityClazz, propertyName);

        colonna.setHeader(text.isValid(header) ? header : propertyName);
        colonna.setSortProperty(propertyName);
        colonna.setSortable(sortable);

        if (isFlexGrow) {
            colonna.setFlexGrow(1);
        } else {
            if (text.isValid(width)) {
                colonna.setWidth(width);
                colonna.setFlexGrow(0);
            }// end of if cycle
        }// end of if/else cycle

    }// end of method

}// end of class
