package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.function.ValueProvider;
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
import it.algos.vaadflow.ui.fields.AComboBox;
import it.algos.vaadflow.ui.list.AGridViewList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.ArrayList;

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
@AIView(roleTypeVisibility = EARoleType.user)
@AIScript(sovrascrivibile = true)
public class ProvaViewList extends AGridViewList {

    /**
     * Icona visibile nel menu (facoltativa)
     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
     * Se manca il MENU_NAME, di default usa il 'name' della view
     */
    public static final VaadinIcon VIEW_ICON = VaadinIcon.ASTERISK;

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
     * Aggiunge al menu eventuali @routes specifiche
     * Solo sovrascritto
     */
    @Override
    protected void addSpecificRoutes() {
        addRoute(SecoloViewList.class);
    }// end of method


    /**
     * Le preferenze specifiche, eventualmente sovrascritte nella sottoclasse
     * Può essere sovrascritto, per aggiungere informazioni
     * Invocare PRIMA il metodo della superclasse
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneEdit = true;
        super.usaPagination = true;
        ArrayList lista = service.findAllProperty("code", Prova.class);
        ArrayList lista2 = service.findAllProperty("ordine", Prova.class);
        logger.debug("Alfetta");
    }// end of method


    /**
     * Placeholder (eventuale, presente di default) SOPRA la Grid
     * - con o senza campo edit search, regolato da preferenza o da parametro
     * - con o senza bottone New, regolato da preferenza o da parametro
     * - con eventuali altri bottoni specifici
     * Può essere sovrascritto, per aggiungere informazioni
     * Invocare PRIMA il metodo della superclasse
     */
    @Override
    protected void creaTopLayout() {
        super.creaTopLayout();
        topPlaceholder.add(creaPopup());
    }// end of method


    protected Component creaPopup() {
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
    protected void creaGridPaginata() {
        PaginatedGrid<Prova> gridPaginated = new PaginatedGrid<Prova>();
        super.grid = gridPaginated;
        super.creaGridPaginata();
    }// end of method


    /**
     * Aggiunge le colonne alla PaginatedGrid <br>
     * Sovrascritto (obbligatorio) <br>
     */
    protected void addColumnsGridPaginata() {

        fixColumn(Prova::getOrdine,"ordine");
        fixColumn(Prova::getCode,"code");
        fixColumn(Prova::getDescrizione,"descrizione");
        fixColumn(Prova::getLastModifica,"lastModifica");

    }// end of method


    /**
     * Costruisce la colonna in funzione della PaginatedGrid specifica della sottoclasse <br>
     * DEVE essere sviluppato nella sottoclasse, sostituendo AEntity con la classe effettiva  <br>
     */
    protected void fixColumn(ValueProvider<Prova, ?> valueProvider , String propertyName) {
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