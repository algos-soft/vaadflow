package it.algos.vaadtest.modules.prova;

import com.mongodb.client.MongoDatabase;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.secolo.SecoloViewList;
import it.algos.vaadflow.presenter.IAPresenter;
import it.algos.vaadflow.ui.AViewList;
import it.algos.vaadflow.ui.dialog.ADeleteDialog;
import it.algos.vaadflow.ui.dialog.ASearchDialog;
import it.algos.vaadflow.ui.dialog.IADialog;
import it.algos.vaadflow.ui.fields.ATextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
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
//@Tag("prova-list")
//@HtmlImport("frontend://src/views/prova/prova-list.html")
@Qualifier(TAG_PRO)
@Slf4j
@AIScript(sovrascrivibile = true)
public class ProvaViewList extends AViewList {

    /**
     * Icona visibile nel menu (facoltativa)
     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
     * Se manca il MENU_NAME, di default usa il 'name' della view
     */
    public static final VaadinIcon VIEW_ICON = VaadinIcon.ASTERISK;


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
     * Creazione e posizionamento dei componenti UI <br>
     * Può essere sovrascritto <br>
     */
    protected void creaLayout2() {
        if (creaTopLayout()) {
            this.add(topPlaceholder);
        }// end of if cycle

        if (creaAlertLayout()) {
            this.add(alertPlacehorder);
        }// end of if cycle

        creaGrid();
        creaGridBottomLayout();
        creaPaginationLayout();
        creaFooterLayout();
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
     * Le preferenze specifiche, eventualmente sovrascritte nella sottoclasse
     * Può essere sovrascritto, per aggiungere informazioni
     * Invocare PRIMA il metodo della superclasse
     */
    @Override
    protected void fixPreferenzeSpecifiche() {
        super.fixPreferenzeSpecifiche();
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
    protected boolean creaTopLayout() {
        super.creaTopLayout();
        topPlaceholder.add(creaSearch());
        return true;
    }// end of method



    /**
     * Eventuali colonne calcolate aggiunte DOPO quelle automatiche
     * Sovrascritto
     */
    @Override
    protected void addSpecificColumnsBefore() {
        super.addSpecificColumnsAfter();
        Grid.Column beta = grid.addColumn(new ComponentRenderer<>(entity -> new Label("30")));//end of lambda expressions and anonymous inner class
        beta.setHeader("Beta");
        beta.setKey("beta");
        beta.setWidth("3em");
        beta.setFlexGrow(0);
        Grid.Column alfa = grid.addColumn(new ComponentRenderer<>(entity -> new Label("40")));//end of lambda expressions and anonymous inner class
        alfa.setHeader("Alfa");
        alfa.setKey("alfa");
        alfa.setWidth("4em");
        alfa.setFlexGrow(0);
        Grid.Column gamma = grid.addColumn(new ComponentRenderer<>(entity -> new Label("50")));//end of lambda expressions and anonymous inner class
        gamma.setHeader("gamma");
        gamma.setKey("gamma");
        gamma.setWidth("5em");
        gamma.setFlexGrow(0);
    }


    /**
     * Eventuale modifica dell'ordine di presentazione delle colonne
     * Sovrascritto
     *
     * @param gridPropertyNamesList
     */
    @Override
    protected List<String> reorderingColumns(List<String> gridPropertyNamesList) {
        ArrayList listaNew = new ArrayList(gridPropertyNamesList);
        Object obj = listaNew.get(1);
        listaNew.remove(obj);
        listaNew.add(0, obj);

        return listaNew;
    }// end of method


}// end of class