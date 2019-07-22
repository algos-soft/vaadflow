package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.annotation.AIView;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.role.EARoleType;
import it.algos.vaadflow.modules.secolo.SecoloViewList;
import it.algos.vaadflow.presenter.IAPresenter;
import it.algos.vaadflow.ui.dialog.AConfirmDialog;
import it.algos.vaadflow.ui.dialog.IADialog;
import it.algos.vaadflow.ui.fields.*;
import it.algos.vaadflow.ui.list.AGridViewList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.vaadin.klaudeta.PaginatedGrid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadtest.application.TestCost.TAG_PRO;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-ott-2018 18.52.31 <br>
 * <br>
 * Estende la classe astratta AViewList per visualizzare la Grid <br>
 * <p>
 * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
 * Le istanze @Autowired usate da questa classe vengono iniettate automaticamente da SpringBoot se: <br>
 * 1) vengono dichiarate nel costruttore @Autowired di questa classe, oppure <br>
 * 2) la property è di una classe con @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON), oppure <br>
 * 3) vengono usate in un un metodo @PostConstruct di questa classe, perché SpringBoot le inietta DOPO init() <br>
 * <p>
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Not annotated with @SpringComponent (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @UIScope (obbligatorio) <br>
 * Annotated with @Route (obbligatorio) per la selezione della vista. @Route(value = "") per la vista iniziale <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@UIScope
@Route(value = TAG_PRO)
@Qualifier(TAG_PRO)
@Slf4j
@AIView(menuName = "prove", searchProperty = "code", roleTypeVisibility = EARoleType.user)
@AIScript(sovrascrivibile = true)
public class ProvaViewList extends AGridViewList {

    /**
     * Icona visibile nel menu (facoltativa)
     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
     * Se manca il MENU_NAME, di default usa il 'name' della view
     */
    public static final VaadinIcon VIEW_ICON = VaadinIcon.ASTERISK;

//    /**
//     * Label del menu (facoltativa)
//     * Vaadin usa il 'name' della Annotation @Route per identificare (internamente) e recuperare la view
//     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
//     * Se manca il MENU_NAME, di default usa il 'name' della view
//     */
//    public static final String MENU_NAME = "pluto";

    private AComboBox<String> comboUpload;

    private PaginatedGrid<Prova> gridPaginated;

//    @Id("search")
//    private TextField search;
//    @Id("newReview")
//    private Button addReview;
//    @Id("header")
//    private H1 header=new H1();


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     * @param dialog    per visualizzare i fields
     */
    @Autowired
    public ProvaViewList(@Qualifier(TAG_PRO) IAPresenter presenter, @Qualifier(TAG_PRO) IADialog dialog) {
        super(presenter, dialog);
        ((ProvaViewDialog) dialog).fixFunzioni(this::save, this::delete);
    }// end of Spring constructor


    /**
     * Placeholder (eventuale) per informazioni aggiuntive alla grid ed alla lista di elementi <br>
     * Normalmente ad uso esclusivo del developer <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaAlertLayout() {
        super.creaAlertLayout();
        alertPlacehorder.add(new Label("Serve per inserire eventuali commenti"));
        alertPlacehorder.add(new Label("La collezione di 15 elementi viene cancellata e ricostruita ad ogni avvio del programma di test"));
    }// end of method


    /**
     * Aggiunge al menu eventuali @routes specifiche
     * Solo sovrascritto
     */
    @Override
    protected void addSpecificRoutes() {
        addRoute(SecoloViewList.class);
    }// end of method


    /**
     * Le preferenze standard <br>
     * Le preferenze specifiche della sottoclasse <br>
     * Può essere sovrascritto, per modificare le preferenze standard <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneReset = true;
        super.usaSearch = true;
        super.usaSearchDialog = false;
        super.usaPopupFiltro = false;
        super.usaBottoneEdit = true;
        super.usaPagination = true;
        ArrayList lista = service.findAllProperty("code", Prova.class);
        ArrayList lista2 = service.findAllProperty("ordine", Prova.class);
        logger.debug("Alfetta");
//        super.gridWith = 100;

//        super.searchProperty="descrizione";
        super.grid = new PaginatedGrid<Prova>();
    }// end of method


    private Component creaPopup() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Popup");
        items.add("Primo");
        items.add("Sercondo");
        comboUpload = new AComboBox();
        comboUpload.setWidth("8em");
        comboUpload.setItems(items);
        comboUpload.setValue("Popup");
        comboUpload.addValueChangeListener(event -> openProvaDialog(event));

        return comboUpload;
    }// end of method


    /**
     * Crea la GridPaginata <br>
     * DEVE essere sovrascritto nella sottoclasse con la PaginatedGrid specifica della Collection <br>
     * DEVE poi invocare il metodo della superclasse per le regolazioni base della PaginatedGrid <br>
     * Oppure queste possono essere fatte nella sottoclasse , se non sono standard <br>
     */
    protected void creaGridPaginata2() {
//        PaginatedGrid<Prova> gridPaginated = new PaginatedGrid<Prova>();
//        super.grid = new PaginatedGrid<Prova>();

//        super.creaGridPaginata();
    }// end of method


//    /**
//     * Aggiunge le colonne alla PaginatedGrid <br>
//     * Sovrascritto (obbligatorio) <br>
//     */
//    protected void addColumnsGridPaginata2() {
//        fixColumn(Prova::getOrdine, "ordine");
//        fixColumn(Prova::getCode, "code");
//
////        fixColumn(Prova::getDescrizione, "descrizione");
//        Grid.Column colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
//            Label label = new Label();
//            Object value;
//            Field field = reflection.getField(entityClazz, "descrizione");
//
//            try { // prova ad eseguire il codice
//                value = field.get(entity);
//                if (value instanceof String) {
//                    label.setText((String) value);
//                }// end of if cycle
//
//            } catch (Exception unErrore) { // intercetta l'errore
//                log.error(unErrore.toString());
//            }// fine del blocco try-catch
//            return label;
//        }));//end of lambda expressions and anonymous inner class
//
////        fixColumn(Prova::isSino, "sino");
//        fixColumn(Prova::getLastModifica, "lastModifica");
//
//
//        Grid.Column colonna2 = grid.addColumn(new ComponentRenderer<>(entity -> {
//            Checkbox checkbox;
//            Boolean status = false;
//            Field field = reflection.getField(entityClazz, "sino");
//            try { // prova ad eseguire il codice
//                status = field.getBoolean(entity);
//            } catch (Exception unErrore) { // intercetta l'errore
//                log.error(unErrore.toString());
//            }// fine del blocco try-catch
//            checkbox = new Checkbox(status);
//            return checkbox;
//        }));//end of lambda expressions and anonymous inner class
//
//    }// end of method


    /**
     * Costruisce la colonna in funzione della PaginatedGrid specifica della sottoclasse <br>
     * DEVE essere sviluppato nella sottoclasse, sostituendo AEntity con la classe effettiva  <br>
     */
    private void fixColumn(ValueProvider<Prova, ?> valueProvider, String propertyName) {
        Grid.Column singleColumn;
        singleColumn = ((PaginatedGrid<Prova>) grid).addColumn(valueProvider);
        columnService.fixColumn(singleColumn, Prova.class, propertyName);
    }// end of method


    protected void openProvaDialog(HasValue.ValueChangeEvent event) {
        String value = (String) event.getValue();

        String message = "Vuoi continuare ?";
        String additionalMessage = "L'operazione non si può interrompere";
        AConfirmDialog dialog = appContext.getBean(AConfirmDialog.class);
//        dialog.open(message, additionalMessage, this::esegueProvaDialogo, null);
        dialog.open(message, additionalMessage, new Pippo(value), null);
//            comboUpload.setValue("Popup");

    }// end of method


    protected void esegueProvaDialogo() {
        Notification.show("Primo - Dura 2 secondi", 2000, Notification.Position.MIDDLE);
    }// end of method


    protected void esegueProvaDialogo(String testo) {
        Notification.show(testo + "- Dura 2 secondi", 2000, Notification.Position.MIDDLE);
    }// end of method


//    /**
//     * Eventuali colonne calcolate aggiunte DOPO quelle automatiche
//     * Sovrascritto
//     */
//    @Override
//    protected void addSpecificColumnsBefore() {
//        super.addSpecificColumnsAfter();
//        Grid.Column beta = grid.addColumn(new ComponentRenderer<>(entity -> new Label("30")));//end of lambda expressions and anonymous inner class
//        beta.setHeader("Beta");
//        beta.setKey("beta");
//        beta.setWidth("3em");
//        beta.setFlexGrow(0);
//        Grid.Column alfa = grid.addColumn(new ComponentRenderer<>(entity -> new Label("40")));//end of lambda expressions and anonymous inner class
//        alfa.setHeader("Alfa");
//        alfa.setKey("alfa");
//        alfa.setWidth("12em");
//        alfa.setFlexGrow(0);
//        Grid.Column gamma = grid.addColumn(new ComponentRenderer<>(entity -> new Label("50")));//end of lambda expressions and anonymous inner class
//        gamma.setHeader("gamma");
//        gamma.setKey("gamma");
//        gamma.setWidth("16em");
//        gamma.setFlexGrow(0);
//    }


//    /**
//     * Eventuale modifica dell'ordine di presentazione delle colonne
//     * Sovrascritto
//     *
//     * @param gridPropertyNamesList
//     */
//    @Override
//    protected List<String> reorderingColumns(List<String> gridPropertyNamesList) {
//        ArrayList listaNew = new ArrayList(gridPropertyNamesList);
//        Object obj = listaNew.get(1);
//        listaNew.remove(obj);
//        listaNew.add(0, obj);
//
//        return listaNew;
//    }// end of method


//    /**
//     * Apre il dialog di detail
//     */
//    protected void addDetailDialog() {
//        //--Flag di preferenza per aprire il dialog di detail con un bottone Edit. Normalmente true.
//        if (usaBottoneEdit) {
//            ComponentRenderer renderer = new ComponentRenderer<>(this::createEditButton);
//            Grid.Column colonna = gridPaginated.addColumn(renderer);
//            colonna.setWidth("6em");
//            colonna.setFlexGrow(0);
//        } else {
//            EAOperation operation = isEntityModificabile ? EAOperation.edit : EAOperation.showOnly;
//            grid.addSelectionListener(evento -> apreDialogo((SingleSelectionEvent) evento, operation));
//        }// end of if/else cycle
//    }// end of method


//    /**
//     * Eventuale header text
//     */
//    protected void fixGridHeader(String messaggio) {
//        try { // prova ad eseguire il codice
//            HeaderRow topRow = gridPaginated.prependHeaderRow();
//            Grid.Column[] matrix = array.getColumnArray(gridPaginated);
//            HeaderRow.HeaderCell informationCell = topRow.join(matrix);
//            Label testo = new Label(messaggio);
//            informationCell.setComponent(testo);
//        } catch (Exception unErrore) { // intercetta l'errore
//            log.error(unErrore.toString());
//        }// fine del blocco try-catch
//    }// end of method


    public void updateViewDopoSearch() {
        LinkedHashMap<String, AbstractField> fieldMap = searchDialog.fieldMap;
        List<AEntity> lista;
        IAField field;
        Object fieldValue = null;
        ArrayList<CriteriaDefinition> listaCriteriaDefinition = new ArrayList();
        ArrayList<CriteriaDefinition> listaCriteriaDefinitionRegex = new ArrayList();

        for (String fieldName : searchDialog.fieldMap.keySet()) {
            field = (IAField) searchDialog.fieldMap.get(fieldName);
            fieldValue = field.getValore();
            if (field instanceof ATextField || field instanceof ATextArea) {
                if (text.isValid(fieldValue)) {
//                    listaCriteriaDefinition.add(Criteria.where(fieldName).is(fieldValue));
                    listaCriteriaDefinitionRegex.add(Criteria.where(fieldName).regex((String) fieldValue));
                }// end of if cycle
            }// end of if cycle
            if (field instanceof AIntegerField) {
                if ((Integer) fieldValue > 0) {
                    listaCriteriaDefinitionRegex.add(Criteria.where(fieldName).regex((String) fieldValue));
                }// end of if cycle
            }// end of if cycle
        }// end of for cycle

        lista = mongo.findAllByProperty(entityClazz, listaCriteriaDefinitionRegex.stream().toArray(CriteriaDefinition[]::new));

        if (array.isValid(lista)) {
            items = lista;
        }// end of if cycle

        this.updateView();

        creaAlertLayout();
    }// end of method


    private class Pippo implements Runnable {

        private String testo;


        public Pippo(String testo) {
            this.testo = testo;
        }


        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            esegueProvaDialogo(testo);
        }// end of method

    }// end of class

}// end of class