package it.algos.vaadflow.service;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import it.algos.vaadflow.annotation.*;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EACompanyRequired;
import it.algos.vaadflow.enumeration.EAFieldType;
import it.algos.vaadflow.modules.role.EARoleType;
import it.algos.vaadflow.ui.IAView;
import it.algos.vaadflow.ui.list.AViewList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.VUOTA;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: gio, 07-dic-2017
 * Time: 21:58
 * <p>
 * Gestisce le interfacce specifiche di VaadFlow: AIEntity, AIView, AIList, AIForm, AIColumn, AIField<br>
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti Spring non la costruisce <br>
 * L'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AAnnotationService.class); <br>
 * 3) @Autowired private AAnnotationService annotation; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AAnnotationService extends AbstractService {

    public static final String TESTO_NULL = " non può essere vuoto";

    public static final String INT_NULL = " deve contenere solo caratteri numerici";

    public static final String INT_ZERO = " deve essere maggiore di zero";

    public static final String TAG_EM = "em";

    public static final String TAG_PX = "px";

    /**
     * Versione della classe per la serializzazione
     */
    private static final long serialVersionUID = 1L;


    /**
     * Get the annotation Algos AIEntity.
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific annotation
     */
    public AIEntity getAIEntity(final Class<? extends AEntity> entityClazz) {
        return entityClazz != null ? entityClazz.getAnnotation(AIEntity.class) : null;
    }


    /**
     * Get the annotation Algos AIList.
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific annotation
     */
    public AIList getAIList(final Class<? extends AEntity> entityClazz) {
        return entityClazz != null ? entityClazz.getAnnotation(AIList.class) : null;
    }


    /**
     * Get the annotation Algos AIForm.
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific annotation
     */
    public AIForm getAIForm(final Class<? extends AEntity> entityClazz) {
        return entityClazz != null ? entityClazz.getAnnotation(AIForm.class) : null;
    }


    /**
     * Get the annotation Algos AIView.
     *
     * @param viewClazz the class of type IAView
     *
     * @return the specific annotation
     */
    public AIView getAIView(final Class<? extends IAView> viewClazz) {
        return viewClazz != null ? viewClazz.getAnnotation(AIView.class) : null;
    }


    /**
     * Get the annotation Algos AIColumn.
     *
     * @param reflectionJavaField di riferimento per estrarre l'interfaccia
     *
     * @return the specific annotation
     */
    public AIColumn getAIColumn(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(AIColumn.class) : null;
    }


    /**
     * Get the annotation Algos.
     *
     * @param entityClazz the class of type AEntity
     * @param fieldName   the property name
     *
     * @return the specific interface
     */
    public AIColumn getAIColumn(Class<? extends AEntity> entityClazz, String fieldName) {
        AIColumn annotation = null;
        List<Field> listaFields;

        try {
            listaFields = reflection.getAllFields(entityClazz);
            if (array.isValid(listaFields)) {
                for (Field reflectionJavaField : listaFields) {
                    if (reflectionJavaField.getName().equals(fieldName)) {
                        annotation = getAIColumn(reflectionJavaField);
                        break;
                    }// end of if cycle
                }// end of for cycle

            }// end of if cycle
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }

        return annotation;
    }


    /**
     * Get the annotation Algos AIField.
     *
     * @param reflectionJavaField di riferimento per estrarre l'interfaccia
     *
     * @return the specific annotation
     */
    public AIField getAIField(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(AIField.class) : null;
    }


    /**
     * Get the annotation Algos AIField.
     *
     * @param entityClazz the class of type AEntity
     * @param fieldName   the property name
     *
     * @return the specific annotation
     */
    public AIField getAIField(Class<? extends AEntity> entityClazz, String fieldName) {
        AIField annotation = null;
        Field reflectionJavaField;

        try {
            reflectionJavaField = entityClazz.getDeclaredField(fieldName);
            annotation = getAIField(reflectionJavaField);
        } catch (Exception unErrore) {
            logger.error(unErrore);
        }


        return annotation;
    }


    /**
     * Get the annotation Document.
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the specific Annotation
     */
    public Document getDocument(final Class<? extends AEntity> entityClazz) {
        return entityClazz != null ? entityClazz.getAnnotation(Document.class) : null;
    }


    /**
     * Get the annotation Qualifier.
     *
     * @param viewClazz the class of type AViewList
     *
     * @return the specific Annotation
     */
    public Qualifier getQualifier(final Class<? extends IAView> viewClazz) {
        return viewClazz != null ? viewClazz.getAnnotation(Qualifier.class) : null;
    }


    /**
     * Get the annotation Route.
     *
     * @param viewClazz the class of type AViewList
     *
     * @return the specific Annotation
     */
    public Route getRoute(final Class<? extends IAView> viewClazz) {
        return viewClazz != null ? viewClazz.getAnnotation(Route.class) : null;
    }


    /**
     * Get the annotation NotNull.
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public NotNull getNotNull(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(NotNull.class) : null;
    }


    /**
     * Get the annotation Indexed.
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public Indexed getUnique(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(Indexed.class) : null;
    }


    /**
     * Get the annotation Size.
     *
     * @param reflectionJavaField di riferimento per estrarre l'annotation
     *
     * @return the Annotation for the specific field
     */
    public Size getSize(final Field reflectionJavaField) {
        return reflectionJavaField != null ? reflectionJavaField.getAnnotation(Size.class) : null;
    }


    /**
     * Get the name of the mongo collection.
     * Cerca nella classe l'annotation @Document
     * Se non la trova, di default usa il nome (minuscolo) della classe AEntity
     *
     * @param entityClazz the class of type AEntity
     *
     * @return the name of the mongo collection
     */
    public String getCollectionName(final Class<? extends AEntity> entityClazz) {
        String name;
        String entityName = entityClazz != null ? entityClazz.getSimpleName().toLowerCase() : VUOTA;
        Document annotation = entityClazz != null ? this.getDocument(entityClazz) : null;

        name = annotation != null ? annotation.collection() : VUOTA;

        return text.isValid(name) ? name : entityName;
    }


    /**
     * Get the name of the route.
     *
     * @param viewClazz the class of type AViewList
     *
     * @return the name of the vaadin-view @route
     */
    public String getRouteName(final Class<? extends AViewList> viewClazz) {
        Route annotation = viewClazz != null ? this.getRoute(viewClazz) : null;

        return annotation != null ? annotation.value() : VUOTA;
    }


    /**
     * Get the name of the qualifier.
     *
     * @param viewClazz the class of type AViewList
     *
     * @return the qualifier of the spring-view
     */
    public String getQualifierName(final Class<? extends AViewList> viewClazz) {
        Qualifier annotation = viewClazz != null ? this.getQualifier(viewClazz) : null;

        return annotation != null ? annotation.value() : VUOTA;
    }// end of method


    /**
     * Get the key field for mongoDB.
     * Si può usare una chiave più breve del nome della property <br>
     * Da usare solo nelle Collection con molte entity <br>
     *
     * @param entityClazz  the class of type AEntity
     * @param propertyName the property name
     *
     * @return the name for the mongoDb database query
     */
    public String getKeyFieldMongo(final Class<? extends AEntity> entityClazz, String propertyName) {
        String fieldKeyMongo = propertyName;
        org.springframework.data.mongodb.core.mapping.Field fieldAnnotation = null;
        Field reflectionJavaField = reflection.getField(entityClazz, fieldKeyMongo);

        if (reflectionJavaField != null) {
            fieldAnnotation = reflectionJavaField.getAnnotation(org.springframework.data.mongodb.core.mapping.Field.class);
            if (fieldAnnotation != null) {
                fieldKeyMongo = fieldAnnotation.value();
            }
        }

        return fieldKeyMongo;
    }

    /**
     * Get the specific annotation of the field.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the Annotation for the specific field
     */
    public int getSizeMin(final Field reflectionJavaField) {
        int min = 0;
        Size annotation = this.getSize(reflectionJavaField);

        if (annotation != null) {
            min = annotation.min();
        }// end of if cycle

        return min;
    }// end of method


    /**
     * Restituisce il nome del menu
     * 1) Cerca in @interface AIView della classe AViewList la property menuName
     * 2) Se non la trova, cerca nella classe la property statica MENU_NAME
     * 3) Se non la trova, di default usa la property 'value' di @interface Route
     *
     * @param viewClazz the view class
     *
     * @return the name of the spring-view
     */
    public String getMenuName(final Class<? extends IAView> viewClazz) {
        String menuName = "";
        AIView annotationView = null;
        Route annotationRoute = null;

        /**
         * 1) Cerca in @interface AIView della classe la property menuName
         */
        annotationView = this.getAIView(viewClazz);
        if (annotationView != null) {
            menuName = annotationView.menuName();
        }// end of if cycle


        /**
         * 2) Se non la trova, cerca nella classe la property statica MENU_NAME
         */
        if (text.isEmpty(menuName)) {
            menuName = reflection.getMenuName(viewClazz);
        }// end of if cycle

        /**
         * 3) Se non la trova, di default usa la property 'value' di @interface Route
         */
        if (text.isEmpty(menuName)) {
            annotationRoute = this.getRoute(viewClazz);
        }// end of if cycle

        if (annotationRoute != null) {
            menuName = annotationRoute.value();
        }// end of if cycle

        menuName = text.isValid(menuName) ? text.primaMaiuscola(menuName) : "Home";

        return menuName;
    }// end of method


    /**
     * Valore della VaadinIcon di una view
     *
     * @param viewClazz classe view su cui operare la riflessione
     */
    public VaadinIcon getMenuIcon(final Class<? extends IAView> viewClazz) {
        VaadinIcon menuIcon = null;
        AIView annotationView = null;

        /**
         * 1) Cerca in @interface AIView della classe la property menuIcon
         */
        annotationView = this.getAIView(viewClazz);
        if (annotationView != null) {
            menuIcon = annotationView.menuIcon();
        }// end of if cycle

        return menuIcon;
    }// end of method


    /**
     * Restituisce il nome del record (da usare nel Dialog)
     * 1) Cerca in @interface AIEntity della classe AEntity la property recordName
     * 2) Se non lo trova, cerca in @interface Document della classe AEntity la property collection
     *
     * @param entityClazz the entity class
     *
     * @return the name of the recordName
     */
    public String getRecordName(final Class<? extends AEntity> entityClazz) {
        String recordName = "";
        AIEntity annotationEntity;

        /**
         * 1) Cerca in @interface AIEntity della classe AEntity la property recordName
         */
        annotationEntity = this.getAIEntity(entityClazz);
        if (annotationEntity != null) {
            recordName = annotationEntity.recordName();
        }// end of if cycle


        /**
         * 2) Se non la trova, cerca in @interface Document della classe AEntity la property collection
         */
        if (text.isEmpty(recordName)) {
            recordName = getCollectionName(entityClazz);
        }// end of if cycle

        return recordName;
    }// end of method


    /**
     * Restituisce il nome della property per le ricerche con searchField <br>
     *
     * @param viewClazz the view class
     *
     * @return the name of the property
     */
    public String getSearchPropertyName(final Class<? extends IAView> viewClazz) {
        String searchProperty = "";
        AIView annotationView = null;

        annotationView = this.getAIView(viewClazz);
        if (annotationView != null) {
            searchProperty = annotationView.searchProperty();
        }// end of if cycle

        return searchProperty;
    }// end of method


    /**
     * Restituisce il nome della property per navigare verso il Form <br>
     *
     * @param viewClazz the view class
     *
     * @return the name of the property
     */
    public String getFormRouteName(final Class<? extends IAView> viewClazz) {
        String routeFormName = "";
        AIView annotationView = null;

        annotationView = this.getAIView(viewClazz);
        if (annotationView != null) {
            routeFormName = annotationView.routeFormName();
        }// end of if cycle

        return routeFormName;
    }// end of method


    /**
     * Restituisce il nome della property per navigare verso il Form <br>
     *
     * @param viewClazz the view class
     *
     * @return the name of the property
     */
    public boolean isStartListEmpty(final Class<? extends IAView> viewClazz) {
        boolean status = false;
        AIView annotation = this.getAIView(viewClazz);

        if (annotation != null) {
            status = annotation.startListEmpty();
        }// end of if cycle

        return status;
    }// end of method


    /**
     * Nomi delle properties della Grid, estratti dalle @Annotation della Entity
     * Se la classe AEntity->@AIList prevede una lista specifica, usa quella lista (con o senza ID)
     * Se l'annotation @AIList non esiste od è vuota,
     * restituisce tutte le colonne (properties della classe e superclasse) //@todo da implementare
     * Sovrascrivibile
     *
     * @param entityClazz the entity class
     *
     * @return lista di nomi di property, oppure null se non esiste l'Annotation specifica @AIList() nella Entity
     */
    public ArrayList<String> getGridPropertiesName(final Class<? extends AEntity> entityClazz) {
        ArrayList<String> lista = null;
        String[] properties = null;
        AIList annotation = this.getAIList(entityClazz);

        if (annotation != null) {
            properties = annotation.fields();
        }// end of if cycle

        if (array.isValid(properties)) {
            lista = new ArrayList<String>(Arrays.asList(properties));
        }// end of if cycle

        return lista;
    }// end of method


    /**
     * Nomi delle properties del, estratti dalle @Annotation della Entity
     * Se la classe AEntity->@AIForm prevede una lista specifica, usa quella lista (con o senza ID)
     * Se l'annotation @AIForm non esiste od è vuota,
     * restituisce tutti i campi (properties della classe e superclasse)
     * Sovrascrivibile
     *
     * @return lista di nomi di property, oppure null se non esiste l'Annotation specifica @AIForm() nella Entity
     */
    public ArrayList<String> getFormPropertiesName(final Class<? extends AEntity> entityClazz) {
        ArrayList<String> lista = null;
        String[] properties = null;
        AIForm annotation = this.getAIForm(entityClazz);

        if (annotation != null) {
            properties = annotation.fields();
        }// end of if cycle

        if (array.isValid(properties)) {
            lista = new ArrayList<String>(Arrays.asList(properties));
        }// end of if cycle

        if (array.isEmpty(lista)) {
            lista = reflection.getAllFieldsNameNoCrono(entityClazz);
        }// end of if cycle

        return lista;
    }// end of method


    /**
     * Nomi dei fields da considerare per estrarre i Java Reflected Field dalle @Annotation della Entity
     * Se la classe AEntity->@AIForm prevede una lista specifica, usa quella lista (con o senza ID)
     * Sovrascrivibile
     *
     * @return nomi dei fields, oppure null se non esiste l'Annotation specifica @AIForm() nella Entity
     */
    @SuppressWarnings("all")
    public ArrayList<String> getFormFieldsName(final Class<? extends AEntity> entityClazz) {
        ArrayList<String> lista = null;
        String[] fields = null;
        AIForm annotation = this.getAIForm(entityClazz);

        if (annotation != null) {
            fields = annotation.fields();
        }// end of if cycle

        if (array.isValid(fields)) {
            lista = new ArrayList(Arrays.asList(fields));
        }// end of if cycle

        return lista;
    }// end of method


    /**
     * Get the status 'nonUsata, facoltativa, obbligatoria' of the class.
     *
     * @param clazz the entity class
     */
    @SuppressWarnings("all")
    public EACompanyRequired getCompanyRequired(final Class<? extends AEntity> entityClazz) {
        EACompanyRequired companyRequired = EACompanyRequired.nonUsata;
        AIEntity annotation = getAIEntity(entityClazz);

        if (annotation != null) {
            companyRequired = annotation.company();
        }// end of if cycle

        return companyRequired;
    }// end of method


    /**
     * Get the roleTypeVisibility of the View class.
     * La Annotation @AIView ha un suo valore di default per la property @AIView.roleTypeVisibility()
     * Se manca completamente l'annotation, inserisco qui un valore di default (per evitare comunque un nullo)
     *
     * @param clazz the view class
     *
     * @return the roleTypeVisibility of the class
     */
    @SuppressWarnings("all")
    public EARoleType getViewRoleType(final Class<? extends IAView> clazz) {
        EARoleType roleTypeVisibility = null;
        AIView annotation = this.getAIView(clazz);

        if (annotation != null) {
            roleTypeVisibility = annotation.roleTypeVisibility();
        }// end of if cycle

        return roleTypeVisibility != null ? roleTypeVisibility : EARoleType.user;
    }// end of method


    public boolean isMenuProgettoBase(final Class<? extends IAView> clazz) {
        boolean status = false;
        AIView annotation = this.getAIView(clazz);

        if (annotation != null) {
            status = annotation.vaadflow();
        }// end of if cycle

        return status;
    }// end of method


    /**
     * Get the accessibility status of the class for the developer login.
     *
     * @param clazz the view class
     *
     * @return true if the class is visible
     */
    @SuppressWarnings("all")
    public boolean getViewAccessibilityDev(final Class<? extends IAView> clazz) {
        EARoleType roleTypeVisibility = getViewRoleType(clazz);
        return (roleTypeVisibility != null && roleTypeVisibility == EARoleType.developer);
    }// end of method


    /**
     * Get the name (columnService) of the property.
     * Se manca, rimane vuoto
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the name (columnService) of the field
     */
    public String getExplicitColumnName(final Field reflectionJavaField) {
        String name = "";
        AIColumn annotation = this.getAIColumn(reflectionJavaField);

        if (annotation != null) {
            name = annotation.name();
        }// end of if cycle

        return name;
    }// end of method


    /**
     * Get the name (columnService) of the property.
     * Se manca, usa il nome del Field
     * Se manca, usa il nome della property
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the name (columnService) of the field
     */
    public String getColumnNameProperty(final Field reflectionJavaField) {
        String name = "";
        AIColumn annotation = this.getAIColumn(reflectionJavaField);

        if (annotation != null) {
            name = annotation.name();
        }// end of if cycle

        if (text.isEmpty(name)) {
            name = this.getFormFieldName(reflectionJavaField);
        }// end of if cycle

        return name;
    }// end of method


    /**
     * Get the name (columnService) of the property.
     * Se manca, rimane vuoto
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the name (columnService) of the field
     */
    public String getExplicitColumnName(Class<? extends AEntity> entityClazz, String fieldName) {
        String name = "";
        Field field = reflection.getField(entityClazz, fieldName);

        if (field != null) {
            name = getExplicitColumnName(field);
        }// end of if cycle

        return name;
    }// end of method


    /**
     * Get the name (columnService) of the property.
     * Se manca, usa il nome del Field
     * Se manca, usa il nome della property
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the name (columnService) of the field
     */
    public String getColumnNameProperty(Class<? extends AEntity> entityClazz, String fieldName) {
        String name = "";
        Field field = reflection.getField(entityClazz, fieldName);

        if (field != null) {
            name = getColumnNameProperty(field);
        }// end of if cycle

        return name;
    }// end of method


    /**
     * Get the visibility of the columnService.
     * Di default true
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the visibility of the columnService
     */
    @Deprecated
    public boolean isColumnVisibile(final Field reflectionJavaField) {
        boolean visibile = false;
        //        EARoleType roleTypeVisibility = EARoleType.nobody;
        //        AIColumn annotation = this.getAIColumn(reflectionJavaField);
        //
        //        if (annotation != null) {
        //            roleTypeVisibility = annotation.roleTypeVisibility();
        //        }// end of if cycle
        //
        //        switch (roleTypeVisibility) {
        //            case nobody:
        //                visibile = false;
        //                break;
        //            case developer:
        //                //@todo RIMETTERE
        //
        ////                if (LibSession.isDeveloper()) {
        //                visibile = true;
        ////                }// end of if cycle
        //                break;
        //            case admin:
        //                //@todo RIMETTERE
        //
        //                //                if (LibSession.isAdmin()) {
        //                visibile = true;
        ////                }// end of if cycle
        //                break;
        //            case user:
        //                visibile = true;
        //                break;
        //            case guest:
        //                visibile = true;
        //                break;
        //            default:
        //                visibile = true;
        //                break;
        //        } // end of switch statement

        return visibile;
    }// end of method


    /**
     * Get the width of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the name (columnService) of the field
     */
    public String getColumnWithEM(Class<? extends AEntity> entityClazz, String fieldName) {
        String widthTxt = "";
        int widthInt = 0;
        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);

        if (annotation != null) {
            widthInt = annotation.widthEM();
        }// end of if cycle

        if (widthInt > 0) {
            widthTxt = widthInt + TAG_EM;
        }// end of if cycle

        return widthTxt;
    }// end of method


    /**
     * Get the status flexibility of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return status of field
     */
    public boolean isFlexGrow(Class<? extends AEntity> entityClazz, String fieldName) {
        boolean status = false;
        Field field = reflection.getField(entityClazz, fieldName);

        if (field != null) {
            status = isFlexGrow(field);
        }// end of if cycle

        return status;
    }// end of method


    /**
     * Get the status flexibility of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    public boolean isFlexGrow(Field reflectionJavaField) {
        boolean status = false;
        AIColumn annotation = this.getAIColumn(reflectionJavaField);

        if (annotation != null) {
            status = annotation.flexGrow();
        }// end of if cycle

        return status;
    }// end of method


    /**
     * Get the status sortable of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return status of field
     */
    public boolean isSortable(Class<? extends AEntity> entityClazz, String fieldName) {
        boolean status = false;
        Field field = reflection.getField(entityClazz, fieldName);

        if (field != null) {
            status = isSortable(field);
        }// end of if cycle

        return status;
    }// end of method


    /**
     * Get the sort for the Grid Columns.
     *
     * @param viewClazz the view class
     *
     * @return sort
     */
    public Sort getSort(Class<? extends IAView> viewClazz) {
        Sort sort = null;
        String sortProperty = VUOTA;
        AIView annotationView = this.getAIView(viewClazz);

        if (annotationView != null) {
            sortProperty = annotationView.sortProperty();
        }// end of if cycle

        if (text.isValid(sortProperty)) {
            sort = new Sort(Sort.DEFAULT_DIRECTION, sortProperty);
        }// end of if cycle

        return sort;
    }// end of method


    /**
     * Get the status flexibility of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    public boolean isSortable(Field reflectionJavaField) {
        boolean status = false;
        AIColumn annotation = this.getAIColumn(reflectionJavaField);

        if (annotation != null) {
            status = annotation.sortable();
        }// end of if cycle

        return status;
    }// end of method


    /**
     * Get the color of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the color (columnService) of the field
     */
    public String getColumnColor(Class<? extends AEntity> entityClazz, String fieldName) {
        String color = "";
        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);

        if (annotation != null) {
            color = annotation.color();
        }// end of if cycle

        return color;
    }// end of method


    /**
     * Get the color of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the color of the field
     */
    public String getFieldColor(final Field reflectionJavaField) {
        String color = "";
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            color = annotation.color();
        }// end of if cycle

        return color;
    }// end of method


    /**
     * Get the type (field) of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the type for the specific field
     */
    public EAFieldType getFormType(final Field reflectionJavaField) {
        EAFieldType type = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            type = annotation.type();
        }// end of if cycle

        return type;
    }// end of method


    /**
     * Get the type (field) of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the type for the specific field
     */
    public EAFieldType getFormType(Class<? extends AEntity> entityClazz, String fieldName) {
        EAFieldType type = null;

        Field field = reflection.getField(entityClazz, fieldName);
        type = getFormType(field);

        return type;
    }// end of method


    /**
     * Get the type (field) of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the type for the specific column
     */
    public EAFieldType getColumnType(final Field reflectionJavaField) {
        EAFieldType type = null;
        AIColumn annotation = this.getAIColumn(reflectionJavaField);

        if (annotation != null) {
            type = annotation.type();
        }// end of if cycle

        if (type == EAFieldType.ugualeAlForm) {
            type = getFormType(reflectionJavaField);
        }// end of if cycle

        if (type == null) {
            type = getFormType(reflectionJavaField);
        }// end of if cycle

        return type;
    }// end of method


    /**
     * Get the type (field) of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the type for the specific column
     */
    public EAFieldType getColumnType(Class<? extends AEntity> entityClazz, String fieldName) {
        EAFieldType type = null;

        Field field = reflection.getField(entityClazz, fieldName);
        type = getColumnType(field);

        if (type == EAFieldType.ugualeAlForm) {
            type = getFormType(entityClazz, fieldName);
        }// end of if cycle

        return type;
    }// end of method


    /**
     * Get the items (String) of the enum.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the items
     */
    public List<String> getEnumItems(final Field reflectionJavaField) {
        List<String> items = null;
        String value = "";
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            value = annotation.items();
        }// end of if cycle

        if (text.isValid(value)) {
            items = array.getList(value);
        }// end of if cycle

        return items;
    }// end of method


    /**
     * Get the clazz of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the type for the specific columnService
     */
    public Class getClazz(final Field reflectionJavaField) {
        Class clazz = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            clazz = annotation.serviceClazz();
        }// end of if cycle

        return clazz;
    }// end of method


    /**
     * Get the clazz of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the type for the specific columnService
     */
    public Class getClazz(Class<? extends AEntity> entityClazz, String fieldName) {
        Class clazz = null;
        Field field = reflection.getField(entityClazz, fieldName);

        if (field != null) {
            clazz = getClazz(field);
        }// end of if cycle

        return clazz;
    }// end of method


    /**
     * Get the name (field) of the property.
     * Se manca, usa il nome della property
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the name (rows) of the field
     */
    public String getFormFieldName(final Field reflectionJavaField) {
        String name = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            name = annotation.name();
        }// end of if cycle

        if (text.isEmpty(name)) {
            name = reflectionJavaField.getName();
        }// end of if cycle

        //        return text.primaMaiuscola(name);
        return name;
    }// end of method


    /**
     * Get the name (field) of the property.
     * Se manca, usa il nome della property
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the capitalized name (rows) of the field
     */
    public String getFormFieldNameCapital(final Field reflectionJavaField) {
        return text.primaMaiuscola(getFormFieldName(reflectionJavaField));
    }// end of method


    /**
     * Get the status focus of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    public boolean isFocus(Field reflectionJavaField) {
        boolean status = true;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            status = annotation.focus();
        }// end of if cycle

        return status;
    }// end of method


    /**
     * Get the status focus of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return status of field
     */
    public boolean isFocus(Class<? extends AEntity> entityClazz, String fieldName) {
        boolean status = true;
        Field field = reflection.getField(entityClazz, fieldName);

        if (field != null) {
            status = isFocus(field);
        }// end of if cycle

        return status;
    }// end of method


    //    /**
    //     * Get the class of the property.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the class for the specific columnService
    //     */
    //    @SuppressWarnings("all")
    //    public Class getComboClass(Field reflectionJavaField) {
    //        Class linkClazz = null;
    //        AIField annotation = this.getAIField(reflectionJavaField);
    //
    //        if (annotation != null) {
    //            linkClazz = annotation.serviceClazz();
    //        }// end of if cycle
    //
    //        return linkClazz;
    //    }// end of method
    //
    //
    //    /**
    //     * Get the class of the property.
    //     *
    //     * @param reflectionJavaField di riferimento per estrarre la Annotation
    //     *
    //     * @return the class for the specific columnService
    //     */
    //    @SuppressWarnings("all")
    //    public Class getComboClass(Class<? extends AEntity> entityClazz, String fieldName) {
    //        Class linkClazz = null;
    //        Field field = reflection.getField(entityClazz, fieldName);
    //
    //        if (field != null) {
    //            linkClazz = getComboClass(field);
    //        }// end of if cycle
    //
    //        return linkClazz;
    //    }// end of method


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class for the specific columnService
     */
    @SuppressWarnings("all")
    public Class getEnumClass(Field reflectionJavaField) {
        Class enumClazz = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            enumClazz = annotation.enumClazz();
        }// end of if cycle

        return enumClazz == Object.class ? null : enumClazz;
    }// end of method


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class for the specific columnService
     */
    @SuppressWarnings("all")
    public Class getLinkClass(Field reflectionJavaField) {
        Class linkClazz = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            linkClazz = annotation.linkClazz();
        }// end of if cycle

        return linkClazz == Object.class ? null : linkClazz;
    }// end of method


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class for the specific columnService
     */
    @SuppressWarnings("all")
    public Class getServiceClass(Field reflectionJavaField) {
        Class serviceClazz = null;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            serviceClazz = annotation.serviceClazz();
        }// end of if cycle

        return serviceClazz == Object.class ? null : serviceClazz;
    }// end of method


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class for the specific columnService
     */
    @SuppressWarnings("all")
    public Class getEnumClass(Class<? extends AEntity> entityClazz, String fieldName) {
        Class linkClazz = null;
        Field field = reflection.getField(entityClazz, fieldName);

        if (field != null) {
            linkClazz = getEnumClass(field);
        }// end of if cycle

        return linkClazz;
    }// end of method


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class for the specific columnService
     */
    @SuppressWarnings("all")
    public Class getLinkClass(Class<? extends AEntity> entityClazz, String fieldName) {
        Class linkClazz = null;
        Field field = reflection.getField(entityClazz, fieldName);

        if (field != null) {
            linkClazz = getLinkClass(field);
        }// end of if cycle

        return linkClazz;
    }// end of method


    /**
     * Get the class of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the class for the specific columnService
     */
    @SuppressWarnings("all")
    public Class getServiceClass(Class<? extends AEntity> entityClazz, String fieldName) {
        Class linkClazz = null;
        Field field = reflection.getField(entityClazz, fieldName);

        if (field != null) {
            linkClazz = getServiceClass(field);
        }// end of if cycle

        return linkClazz;
    }// end of method


    /**
     * Get the width of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the width of the field expressed in em
     */
    public int getWidth(Field reflectionJavaField) {
        int widthInt = 0;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            widthInt = annotation.widthEM();
        }// end of if cycle

        return widthInt;
    }// end of method


    /**
     * Get the widthEM of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the width of the field expressed in em
     */
    public String getWidthEM(Field reflectionJavaField) {
        String width = "";
        int widthInt = this.getWidth(reflectionJavaField);
        String tag = "em";

        if (widthInt > 0) {
            width = widthInt + tag;
        }// end of if cycle

        return width;
    }// end of method


    /**
     * Get the alert message from @NotNull
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the alert message
     */
    public String getMessageNull(Field reflectionJavaField) {
        String message = "";
        NotNull annotation = this.getNotNull(reflectionJavaField);
        EAFieldType type;

        if (annotation != null) {
            message = annotation.message();
        }// end of if cycle

        if (message.equals("{javax.validation.constraints.NotNull.message}")) {
            message = "";
            type = getFormType(reflectionJavaField);
            if (type == EAFieldType.text) {
                message = text.primaMaiuscola(reflectionJavaField.getName()) + TESTO_NULL;
            }// end of if cycle
            if (type == EAFieldType.integer) {
                message = text.primaMaiuscola(reflectionJavaField.getName()) + INT_NULL;
            }// end of if cycle
        }// end of if cycle

        return message;
    }// end of method


    /**
     * Get the alert message from @Size
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the alert message
     */
    public String getMessageSize(Field reflectionJavaField) {
        String message = "";
        Size annotation = this.getSize(reflectionJavaField);
        EAFieldType type = getFormType(reflectionJavaField);
        int min = 0;

        if (type != EAFieldType.text) {
            return "";
        }// end of if cycle

        if (annotation == null) {
            message = this.getMessage(reflectionJavaField);
        } else {
            message = annotation.message();
            if (message.equals("{javax.validation.constraints.Size.message}")) {
                min = annotation.min();
                if (min > 0) {
                    message = text.primaMaiuscola(reflectionJavaField.getName()) + " deve contenere almeno " + min + " caratteri";
                }// end of if cycle
            }// end of if cycle
        }// end of if/else cycle


        return message;
    }// end of method


    /**
     * Get the alert message from @NotNull or from @Size
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the alert message
     */
    public String getMessage(Field reflectionJavaField) {
        String message = "";

        message = getMessageNull(reflectionJavaField);

        //        if (text.isEmpty(message)) {
        //            message = getMessageSize(reflectionJavaField);
        //        }// end of if cycle

        return message;
    }// end of method


    /**
     * Get the status required of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    public boolean isRequired(Field reflectionJavaField) {
        boolean status = false;
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            status = annotation.required();
        }// end of if cycle

        return status;
    }// end of method


    /**
     * Get the status required of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    public boolean isNotNull(Field reflectionJavaField) {
        return getNotNull(reflectionJavaField) != null;
    }// end of method


    /**
     * Get the status required of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return status of field
     */
    public boolean isUnique(Field reflectionJavaField) {
        boolean status = false;
        Indexed annotation = this.getUnique(reflectionJavaField);

        if (annotation != null) {
            status = annotation.unique();
        }// end of if cycle

        return status;

    }// end of method



    /**
     * Bottoni visibili nella toolbar
     *
     * @param clazz the entity class
     *
     * @return lista di bottoni visibili nella toolbar
     */
    @SuppressWarnings("all")
    public EAFormButton getFormBottonDev(final Class<? extends AEntity> clazz) {
        EAFormButton listaNomiBottoni = EAFormButton.standard;
        AIForm annotation = this.getAIForm(clazz);

        if (annotation != null) {
            listaNomiBottoni = annotation.buttonsDev();
        }// end of if cycle

        return listaNomiBottoni;
    }// end of method


    /**
     * Bottoni visibili nella toolbar
     *
     * @param clazz the entity class
     *
     * @return lista di bottoni visibili nella toolbar
     */
    @SuppressWarnings("all")
    public EAFormButton getFormBottonAdmin(final Class<? extends AEntity> clazz) {
        EAFormButton listaNomiBottoni = EAFormButton.standard;
        AIForm annotation = this.getAIForm(clazz);

        if (annotation != null) {
            listaNomiBottoni = annotation.buttonsAdmin();
        }// end of if cycle

        return listaNomiBottoni;
    }// end of method


    /**
     * Bottoni visibili nella toolbar
     *
     * @param clazz the entity class
     *
     * @return lista di bottoni visibili nella toolbar
     */
    @SuppressWarnings("all")
    public EAFormButton getFormBottonUser(final Class<? extends AEntity> clazz) {
        EAFormButton listaNomiBottoni = EAFormButton.standard;
        AIForm annotation = this.getAIForm(clazz);

        if (annotation != null) {
            listaNomiBottoni = annotation.buttonsUser();
        }// end of if cycle

        return listaNomiBottoni;
    }// end of method



    /**
     * Get the icon of the property.
     * Default a VaadinIcon.YOUTUBE che sicuramente non voglio usare
     * e posso quindi escluderlo
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the icon of the field
     */
    public VaadinIcon getHeaderIcon(Class<? extends AEntity> entityClazz, String fieldName) {
        VaadinIcon icon = null;
        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);

        if (annotation != null) {
            icon = annotation.headerIcon();
        }// end of if cycle

        if (icon == VaadinIcon.YOUTUBE) {
            icon = null;
        }// end of if cycle

        return icon;
    }// end of method


    /**
     * Get the size of the icon of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the size of the icon
     */
    public String getHeaderIconSizePX(Class<? extends AEntity> entityClazz, String fieldName) {
        int widthInt = 0;
        int standard = 20;
        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);

        if (annotation != null) {
            widthInt = annotation.headerIconSizePX();
        }// end of if cycle

        if (widthInt == 0) {
            widthInt = standard;
        }// end of if cycle

        return widthInt + TAG_PX;
    }// end of method


    /**
     * Get the color of the property.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the color of the icon
     */
    public String getHeaderIconColor(Class<? extends AEntity> entityClazz, String fieldName) {
        String color = "";
        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);

        if (annotation != null) {
            color = annotation.headerIconColor();
        }// end of if cycle

        return color;
    }// end of method


    /**
     * Get the method name for reflection.
     *
     * @param entityClazz the entity class
     * @param fieldName   the property name
     *
     * @return the method name
     */
    public String getMethodNameColumn(Class<? extends AEntity> entityClazz, String fieldName) {
        String methodName = "";
        AIColumn annotation = this.getAIColumn(entityClazz, fieldName);

        if (annotation != null) {
            methodName = annotation.methodName();
        }// end of if cycle

        return methodName;
    }// end of method


    /**
     * Get the color of the property.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the color of the field
     */
    public String xxxx(final Field reflectionJavaField) {
        String color = "";
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            color = annotation.color();
        }// end of if cycle

        return color;
    }// end of method


    /**
     * Get the method name for reflection.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the method name
     */
    public String getMethodNameField(final Field reflectionJavaField) {
        String methodName = "";
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            methodName = annotation.methodName();
        }// end of if cycle

        return methodName;
    }// end of method


    /**
     * Get the method name for reflection.
     *
     * @param reflectionJavaField di riferimento per estrarre la Annotation
     *
     * @return the method name
     */
    public String getPropertyLinkata(final Field reflectionJavaField) {
        String methodName = "";
        AIField annotation = this.getAIField(reflectionJavaField);

        if (annotation != null) {
            methodName = annotation.propertyLinkata();
        }// end of if cycle

        return methodName;
    }// end of method

}// end of class
