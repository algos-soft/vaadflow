package it.algos.vaadflow.ui.list;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import it.algos.vaadflow.application.FlowCost;
import it.algos.vaadflow.application.FlowVar;
import it.algos.vaadflow.application.StaticContextAccessor;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAColor;
import it.algos.vaadflow.enumeration.EAMenu;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadflow.ui.fields.AComboBox;
import it.algos.vaadflow.ui.menu.AButtonMenu;
import it.algos.vaadflow.ui.menu.APopupMenu;
import it.algos.vaadflow.ui.menu.IAMenu;
import lombok.extern.slf4j.Slf4j;

import static it.algos.vaadflow.application.FlowCost.*;
import static it.algos.vaadflow.application.FlowVar.usaCompany;
import static it.algos.vaadflow.application.FlowVar.usaSecurity;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: Mon, 20-May-2019
 * Time: 07:48
 * <p>
 * Classe astratta per visualizzare la Grid <br>
 * La classe viene divisa verticalmente in alcune classi astratte, per 'leggerla' meglio (era troppo grossa) <br>
 * Nell'ordine (dall'alto):
 * - 1 APropertyViewList (che estende la classe Vaadin VerticalLayout) per elencare tutte le property usate <br>
 * - 2 AViewList con la business logic principale <br>
 * - 3 APrefViewList per regolare le preferenze ed i flags <br>
 * - 4 ALayoutViewList per regolare il layout <br>
 * - 5 AGridViewList per gestire la Grid <br>
 * - 6 APaginatedGridViewList (opzionale) per gestire una Grid specializzata (add-on) che usa le Pagine <br>
 * L'utilizzo pratico per il programmatore è come se fosse una classe sola <br>
 * <p>
 * Sottoclasse di servizio per regolare il layout di AViewList in una classe 'dedicata' <br>
 * Alleggerisce la 'lettura' della classe principale <br>
 * Le property sono regolarmente disponibili in AViewList ed in tutte le sue sottoclassi <br>
 * Regola menu, topLayout e alertLayout <br>
 * Qui vengono regolati i layout 'standard'. <br>
 * Nelle sottoclassi concrete i layout possono essere modificati. <br>
 */
@Slf4j
public abstract class ALayoutViewList extends APrefViewList {


    /**
     * Costruttore @Autowired <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     * Nella sottoclasse concreta si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Nella sottoclasse concreta si usa una costante statica, per scrivere sempre uguali i riferimenti <br>
     * Passa nella superclasse anche la entityClazz che viene definita qui (specifica di questo mopdulo) <br>
     *
     * @param service     business class e layer di collegamento per la Repository
     * @param entityClazz modello-dati specifico di questo modulo
     */
    public ALayoutViewList(IAService service, Class<? extends AEntity> entityClazz) {
        super(service, entityClazz);
    }// end of Vaadin/@Route constructor


    /**
     * Costruisce gli oggetti base (placeholder) di questa view <br>
     * Li aggiunge alla view stessa <br>
     * Può essere sovrascritto, per modificare il layout standard <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixLayout() {
        super.fixLayout();
        this.setMargin(false);
        this.setSpacing(false);
        this.setPadding(false);

        this.topPlaceholder = new HorizontalLayout();
        this.alertPlacehorder = new VerticalLayout();
        this.gridPlaceholder = new VerticalLayout();

        //this.gridPlaceholder.setSizeFull();

        this.bottomPlacehorder = new HorizontalLayout();

        if (pref.isBool(USA_DEBUG)) {
            this.getElement().getStyle().set("background-color", EAColor.yellow.getEsadecimale());
            alertPlacehorder.getElement().getStyle().set("background-color", EAColor.blue.getEsadecimale());
            topPlaceholder.getElement().getStyle().set("background-color", EAColor.lime.getEsadecimale());
            gridPlaceholder.getElement().getStyle().set("background-color", EAColor.red.getEsadecimale());
            bottomPlacehorder.getElement().getStyle().set("background-color", EAColor.silver.getEsadecimale());
        }// end of if cycle

        gridPlaceholder.setMargin(false);
        gridPlaceholder.setSpacing(false);
        gridPlaceholder.setPadding(false);
    }// end of method


    /**
     * Costruisce la barra di menu e l'aggiunge alla UI <br>
     * Lo standard è 'Flowingcode'
     * Può essere sovrascritto
     * Invocare PRIMA il metodo della superclasse
     */
    protected void creaMenuLayout() {
        IAMenu menu;
        EAMenu typeMenu = EAMenu.getMenu(pref.getEnumStr(USA_MENU));

        if (typeMenu != null) {
            switch (typeMenu) {
                case buttons:
                    menu = StaticContextAccessor.getBean(AButtonMenu.class);
                    this.add(menu.getComp());
                    break;
                case popup:
                    menu = StaticContextAccessor.getBean(APopupMenu.class);
                    this.add(menu.getComp());
                    break;
                case flowing:
//                    menu = StaticContextAccessor.getBean(AFlowingcodeAppLayoutMenu.class);
//                    this.add(menu.getComp());
                    break;
                case vaadin:
//                    menu = StaticContextAccessor.getBean(AAppLayoutMenu.class);
//                    this.add(new Label("."));
//                    this.add(((AFlowingcodeAppLayoutMenu) menu).getAppLayoutFlowing());
                    break;
                default:
                    log.warn("Switch - caso non definito");
                    break;
            } // end of switch statement
        }// end of if cycle

    }// end of method


    /**
     * Costruisce un (eventuale) layout per informazioni aggiuntive alla grid ed alla lista di elementi
     * Normalmente ad uso esclusivo del developer
     * Può essere sovrascritto, per aggiungere informazioni
     * Invocare PRIMA il metodo della superclasse
     */
    protected void creaAlertLayout() {
        alertPlacehorder.removeAll();
//        alertPlacehorder.addClassName("view-toolbar");
        alertPlacehorder.setMargin(false);
        alertPlacehorder.setSpacing(false);
        alertPlacehorder.setPadding(false);

        if (isEntityDeveloper || isEntityAdmin || isEntityEmbedded || isEntityUsaDatiDemo) {
            usaTopAlert = true;
        }// end of if cycle

        if (usaTopAlert) {
            if (usaSecurity) {
                if (isEntityDeveloper) {
                    alertPlacehorder.add(new Label("Lista visibile solo perché sei collegato come developer. Gli admin e gli utenti normali non la vedono."));
                }// end of if cycle

                if (isEntityAdmin) {
                    alertPlacehorder.add(new Label("Lista visibile solo perché sei collegato come admin. Gli utenti normali non la vedono."));
                }// end of if cycle
            }// end of if cycle

            if (isEntityEmbedded) {
                alertPlacehorder.add(new Label("Questa lista non dovrebbe mai essere usata direttamente (serve come test o per le sottoclassi specifiche)"));
                alertPlacehorder.add(new Label("L'entity è 'embedded' nelle collezioni che la usano (no @Annotation property DbRef)"));
            }// end of if cycle

            if (isEntityEmbedded || isEntityUsaDatiDemo) {
                alertPlacehorder.add(new Label("Allo startup del programma, sono stati creati alcuni elementi di prova"));
            }// end of if cycle
        }// end of if cycle

    }// end of method


    /**
     * Placeholder SOPRA la Grid (non obbligatoria, ma quasi sempre presente) <br>
     * <p>
     * - con o senza un bottone per cancellare tutta la collezione
     * - con o senza un bottone di reset per ripristinare (se previsto in automatico) la collezione
     * - con o senza bottone New, con testo regolato da preferenza o da parametro <br>
     * - con o senza gruppo di ricerca:
     * -    campo EditSearch predisposto su un unica property, oppure (in alternativa)
     * -    bottone per aprire un DialogSearch con diverse property selezionabili
     * -    bottone per annullare la ricerca e riselezionare tutta la collezione
     * - con eventuale Popup di selezione della company (se applicazione multiCompany)
     * - con eventuale Popup di selezione specifico, filtro e ordinamento
     * - con eventuali altri bottoni specifici <br>
     * <p>
     * I bottoni vengono creati SENZA listeners che vengono regolati nel metodo addListeners() <br>
     * Chiamato da AViewList.initView() e sviluppato nella sottoclasse ALayoutViewList <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaTopLayout() {
        topPlaceholder.removeAll();
        topPlaceholder.addClassName("view-toolbar");
        String buttonTitle;
        boolean isDeveloper = login.isDeveloper();
        boolean isAdmin = login.isAdmin();

        //--il bottone associa un evento standard -> AViewList.openConfirmDelete(), che può essere sovrascritto
        if ((!FlowVar.usaSecurity && usaBottoneDeleteAll) || (isDeveloper && usaBottoneDeleteAll)) {
            deleteAllButton = new Button("Delete", new Icon(VaadinIcon.CLOSE_CIRCLE));
            deleteAllButton.getElement().setAttribute("theme", "error");
            deleteAllButton.addClassName("view-toolbar__button");
            topPlaceholder.add(deleteAllButton);
        }// end of if cycle


        //--il bottone associa un evento standard -> AViewList.openConfirmReset(), che rinvia al service specifico
        if ((!FlowVar.usaSecurity && usaBottoneReset) || (isDeveloper && usaBottoneReset)) {
            resetButton = new Button("Reset", new Icon(VaadinIcon.CLOSE_CIRCLE));
            resetButton.getElement().setAttribute("theme", "error");
            resetButton.addClassName("view-toolbar__button");
            topPlaceholder.add(resetButton);
        }// end of if cycle


        //--il bottone associa un evento standard -> AViewList.openNew()
        //--il bottone associa, se previsto da pref, un tasto shortcut
        if (usaBottoneNew) {
            buttonTitle = text.primaMaiuscola(pref.getStr(FlowCost.FLAG_TEXT_NEW));
            newButton = new Button(buttonTitle, new Icon("lumo", "plus"));
            newButton.getElement().setAttribute("theme", "primary");
            newButton.addClassName("view-toolbar__button");
            if (pref.isBool(USA_BUTTON_SHORTCUT)) {
                newButton.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
            }// end of if cycle
            topPlaceholder.add(newButton);
        }// end of if cycle

        //--con o senza gruppo di ricerca
        if (usaSearch) {
            //--bottone per aprire un DialogSearch con diverse property selezionabili
            if (usaSearchDialog) {
                //--il bottone associa un evento standard -> openConfirmDialogDelete(), che deve essere sovrascritto
                buttonTitle = text.primaMaiuscola(pref.getStr(FlowCost.FLAG_TEXT_SEARCH));
                searchButton = new Button(buttonTitle, new Icon("lumo", "search"));
                searchButton.getElement().setAttribute("theme", "secondary");
                searchButton.addClassName("view-toolbar__button");

                //--bottone piccolo per eliminare i filtri di una ricerca e restituire tutte le entities
                clearFilterButton = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
                topPlaceholder.add(searchButton, clearFilterButton);
            } else {
                //--campo EditSearch predisposto su un unica property
                searchField = new TextField("", "Search");
                searchField.setPrefixComponent(new Icon("lumo", "search"));
                searchField.addClassName("view-toolbar__search-field");
                searchField.setValueChangeMode(ValueChangeMode.EAGER);

                //--bottone piccolo per pulire il campo testo di ricerca
                clearFilterButton = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
                topPlaceholder.add(searchField, clearFilterButton);
            }// end of if/else cycle
        }// end of if cycle

        //--eventuale filtro sulla company
        if (usaCompany && login.isDeveloper()) {
            creaCompanyFiltro();
            if (filtroCompany != null) {
                topPlaceholder.add(filtroCompany);
            }// end of if cycle
        }// end of if cycle

        //--eventuale filtro specifico
        if (usaPopupFiltro) {
            creaPopupFiltro();
            if (filtroComboBox != null) {
                topPlaceholder.add(filtroComboBox);
            }// end of if cycle
        }// end of if cycle
    }// end of method


    /**
     * Aggiunge tutti i listeners ai bottoni di 'topPlaceholder' che sono stati creati SENZA listeners <br>
     * Chiamato da AViewList.initView() e sviluppato nella sottoclasse ALayoutViewList <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addListeners() {
        super.addListeners();

        if (deleteAllButton != null) {
            deleteAllButton.addClickListener(event -> openConfirmDelete());
        }// end of if cycle

        if (resetButton != null) {
            resetButton.addClickListener(e -> openConfirmReset());
        }// end of if cycle

        if (newButton != null) {
            newButton.addClickListener(event -> openNew());
        }// end of if cycle

        if (searchButton != null) {
            searchButton.addClickListener(e -> openSearch());
        }// end of if cycle

        if (clearFilterButton != null) {
            clearFilterButton.addClickListener(e -> {
                updateItems();
                updateGrid();
            });//end of lambda expressions
        }// end of if cycle

        if (searchField != null) {
            searchField.addValueChangeListener(e -> {
                updateItems();
                updateGrid();
            });//end of lambda expression
        }// end of if cycle

        if (searchButton != null) {
            searchButton.addClickListener(e -> openSearch());
            if (clearFilterButton != null) {
                clearFilterButton.addClickListener(e -> searchField.clear());
            }// end of if cycle
        }// end of if cycle

        if (filtroCompany != null) {
            filtroCompany.addValueChangeListener(e -> {
                updateItems();
                updateGrid();
            });// end of lambda expressions
        }// end of if cycle

    }// end of method


    /**
     * Crea un (eventuale) Popup di selezione, filtro e ordinamento <br>
     * DEVE essere sovrascritto, per regolare il contenuto (items) <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void creaPopupFiltro() {
        filtroComboBox = new AComboBox();
        filtroComboBox.setWidth("8em");
    }// end of method


    /**
     * Crea un Popup di selezione della company <br>
     * Creato solo se devleper=true e usaCompany=true <br>
     */
    protected void creaCompanyFiltro() {
        if (false) {
            filtroCompany = new AComboBox();
            filtroCompany.setPlaceholder("company ...");
            filtroCompany.setWidth("9em");
            filtroCompany.setItems(companyService.findAll());
        }// end of if cycle
    }// end of method

}// end of class
