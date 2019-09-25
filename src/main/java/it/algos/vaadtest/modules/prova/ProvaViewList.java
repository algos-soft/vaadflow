package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.annotation.AIView;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.role.EARoleType;
import it.algos.vaadflow.modules.secolo.SecoloList;
import it.algos.vaadflow.presenter.IAPresenter;
import it.algos.vaadflow.ui.dialog.AConfirmDialog;
import it.algos.vaadflow.ui.dialog.IADialog;
import it.algos.vaadflow.ui.fields.*;
import it.algos.vaadflow.ui.list.AGridViewList;
import it.algos.vaadtest.application.MainLayout14;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static it.algos.vaadtest.application.TestCost.*;

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
@Route(value = TAG_PRO, layout = MainLayout14.class)
@Qualifier(TAG_PRO)
@Slf4j
@AIView(menuName = "prove", searchProperty = "code", roleTypeVisibility = EARoleType.user)
@AIScript(sovrascrivibile = true)
public class ProvaViewList extends AGridViewList {

    /**
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
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     * @param dialog    per visualizzare i fields
     */
    @Autowired
    @Deprecated
    public ProvaViewList(@Qualifier(TAG_PRO) IAPresenter presenter, @Qualifier(TAG_PRO) IADialog dialog) {
        super(presenter, dialog);
        ((ProvaViewDialog) dialog).fixFunzioni(this::save, this::delete);
    }// end of Vaadin/@Route constructor

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
     * Placeholder SOPRA la Grid <br>
     * Contenuto eventuale, presente di default <br>
     * - con o senza un bottone per cancellare tutta la collezione
     * - con o senza un bottone di reset per ripristinare (se previsto in automatico) la collezione
     * - con o senza gruppo di ricerca:
     * -    campo EditSearch predisposto su un unica property, oppure (in alternativa)
     * -    bottone per aprire un DialogSearch con diverse property selezionabili
     * -    bottone per annullare la ricerca e riselezionare tutta la collezione
     * - con eventuale Popup di selezione, filtro e ordinamento
     * - con o senza bottone New, con testo regolato da preferenza o da parametro <br>
     * - con eventuali altri bottoni specifici <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaTopLayout() {
        super.creaTopLayout();

        topPlaceholder.add(creaPopup());

        Button testVistaSenza = new Button("Test senza parametri", new Icon(VaadinIcon.ASTERISK));
        testVistaSenza.getElement().setAttribute("theme", "secondary");
        testVistaSenza.addClassName("view-toolbar__button");
        testVistaSenza.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(TAG_VIEW_FORM)));
        testVistaSenza.getElement().setAttribute("title", "Posso scrivere quello che voglio. Senza HTML text, però.");
        topPlaceholder.add(testVistaSenza);


        Button testVistaUno = new Button("Test con 1 parametro", new Icon(VaadinIcon.ASTERISK));
        testVistaUno.getElement().setAttribute("theme", "secondary");
        testVistaUno.addClassName("view-toolbar__button");
        testVistaUno.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(TAG_VIEW_FORM + "/alfa")));
        topPlaceholder.add(testVistaUno);


        Button testVistaDiversi = new Button("Test con diversi parametri", new Icon(VaadinIcon.ASTERISK));
        testVistaDiversi.getElement().setAttribute("theme", "secondary");
        testVistaDiversi.addClassName("view-toolbar__button");

        Map<String, String> mappa = null;
        mappa = new LinkedHashMap<String, String>();
        List<String> lista = new ArrayList<>();
        lista.add("mario");
        mappa.put("nome", "mario");

        lista = new ArrayList<>();
        lista.add("27");
        mappa.put("servizio", "27");
        final QueryParameters query = QueryParameters.simple(mappa);

        testVistaDiversi.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(TAG_VIEW_FORM, query)));
        topPlaceholder.add(testVistaDiversi);


        Button testVistaMultipli = new Button("Test con diversi parametri multipli", new Icon(VaadinIcon.ASTERISK));
        testVistaMultipli.getElement().setAttribute("theme", "secondary");
        testVistaMultipli.addClassName("view-toolbar__button");

        Map<String, List<String>> mappa2 = null;
        mappa2 = new LinkedHashMap<String, List<String>>();
        List<String> lista2 = new ArrayList<>();
        lista2.add("mario");
        lista2.add("giovanni");
        lista2.add("francesca");
        mappa2.put("nomi", lista2);

        lista2 = new ArrayList<>();
        lista2.add("27");
        lista2.add("134");
        lista2.add("8");
        mappa2.put("servizi", lista2);
        final QueryParameters query2 = new QueryParameters(mappa2);

        testVistaMultipli.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(TAG_VIEW_FORM, query2)));
        topPlaceholder.add(testVistaMultipli);

        Button polymer = new Button("Polymer", new Icon(VaadinIcon.ASTERISK));
        polymer.getElement().setAttribute("theme", "secondary");
        polymer.addClassName("view-toolbar__button");
        polymer.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(TAG_ALF)));
        topPlaceholder.add(polymer);

        Button polymer2 = new Button("Polymer2", new Icon(VaadinIcon.ASTERISK));
        polymer2.getElement().setAttribute("theme", "error");
        polymer2.addClassName("view-toolbar__button");
        polymer2.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(TAG_ALF + "2")));
        topPlaceholder.add(polymer2);

        Button polymer3 = new Button("polymer3", new Icon(VaadinIcon.ASTERISK));
        polymer3.getElement().setAttribute("theme", "error");
        polymer3.addClassName("view-toolbar__button");
        polymer3.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(TAG_ALF + "3")));
        topPlaceholder.add(polymer3);

        Button una = new Button("una", new Icon(VaadinIcon.ASTERISK));
        una.getElement().setAttribute("theme", "error");
        una.addClassName("view-toolbar__button");
        una.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("una")));
        topPlaceholder.add(una);
        Button due = new Button("due", new Icon(VaadinIcon.ASTERISK));
        due.getElement().setAttribute("theme", "error");
        due.addClassName("view-toolbar__button");
        due.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("due")));
        topPlaceholder.add(due);
        Button tre = new Button("tre", new Icon(VaadinIcon.ASTERISK));
        tre.getElement().setAttribute("theme", "error");
        tre.addClassName("view-toolbar__button");
        tre.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("tre")));
        topPlaceholder.add(tre);
    }// end of method


    /**
     * Aggiunge al menu eventuali @routes specifiche
     * Solo sovrascritto
     */
    @Override
    protected void addSpecificRoutes() {
        addRoute(SecoloList.class);
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
     * Eventuali colonne specifiche aggiunte PRIMA di quelle automatiche
     * Sovrascritto
     */
    protected void addSpecificColumnsBefore() {
        Grid.Column<AEntity> colonna = null;
        Grid.Column<AEntity> colonna2 = null;

        Icon nameIcon = new Icon(VaadinIcon.CHECK);
        nameIcon.setSize("10px");
        nameIcon.getElement().getStyle().set("float", "center");
        nameIcon.setColor("green");
        Label nameLabel = new Label("");
        nameLabel.add(nameIcon);
        nameLabel.getElement().getStyle().set("float", "center");

        Icon nameIcon2 = new Icon(VaadinIcon.CLOCK);
        nameIcon2.setSize("30px");
        nameIcon2.getStyle().set("float", "center");
        nameIcon2.setColor("red");
        Label nameLabel2 = new Label("");
        nameLabel2.add(nameIcon2);

//        Icon nameIcon = new Icon(VaadinIcon.MONEY);
//        nameIcon.setSize("20px");
//        nameIcon.getStyle().set("float", "left");
//        nameIcon.setColor("blue");
//        Label nameLabel = new Label("Name");
//        nameLabel.add(nameIcon);
//        grid().addColumn(Client::getName).setHeader(nameLabel).setFlexGrow(5).setSortable(true).setKey("name");  }// end of method


        colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
            return new Label("x");
        }));//end of lambda expressions and anonymous inner class
        colonna.setKey("abbaco");
        colonna.setHeader(nameLabel);
        colonna.setWidth("10em");
        colonna2 = grid.addColumn(new ComponentRenderer<>(entity -> {
            return new Label("y");
        }));//end of lambda expressions and anonymous inner class
        colonna2.setKey("rovina");
        colonna2.setHeader(nameLabel2);
        colonna2.setWidth("2em");


    }// end of method


    /**
     * Eventuali colonne specifiche aggiunte DOPO quelle automatiche
     * Sovrascritto
     */
    @Override
    protected void addSpecificColumnsAfter() {
        String colorName2 = "#ef6c00";
        Grid.Column colonna2 = grid.addComponentColumn(servizio -> {
            Label label = new Label();
            String htmlCode = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            label.getElement().setProperty("innerHTML", htmlCode);
            label.getElement().getStyle().set("background-color", colorName2);
            label.getElement().getStyle().set("color", colorName2);

            return label;
        });//end of lambda expressions

        colonna2.setId("idColor2");
        colonna2.setHeader("G");
        colonna2.setWidth("3em");
        colonna2.setFlexGrow(0);
    }


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