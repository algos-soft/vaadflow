package it.algos.vaadtest.modules.alfa;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.annotation.AIView;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAOperation;
import it.algos.vaadflow.modules.role.EARoleType;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadflow.ui.MainLayout14;
import it.algos.vaadflow.ui.list.AGridViewList;
import it.algos.vaadflow.wrapper.AFiltro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.access.annotation.Secured;

import java.util.ArrayList;
import java.util.Arrays;

import static it.algos.vaadtest.application.TestCost.TAG_ALF;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 23-ott-2019 13.52.45 <br>
 * <p>
 * Estende la classe astratta AViewList per visualizzare la Grid <br>
 * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
 * <p>
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
 * La sottoclasse concreta viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
 * Le property di questa classe/sottoclasse vengono iniettate (@Autowired) automaticamente se: <br>
 * 1) vengono dichiarate nel costruttore @Autowired della sottoclasse concreta, oppure <br>
 * 2) la property è di una classe con @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) e viene richiamate
 * con AxxService.getInstance() <br>
 * 3) sono annotate @Autowired; sono disponibili SOLO DOPO @PostConstruct <br>
 * <p>
 * Considerato che le sottoclassi concrete NON sono singleton e vengo ri-create ogni volta che dal menu (via @Router)
 * si invocano, è inutile (anche se possibile) usare un metodo @PostConstruct che è sempre un'0appendici di init() del
 * costruttore.
 * Meglio spostare tutta la logica iniziale nel metodo beforeEnter() <br>
 * <p>
 * Graficamente abbiamo in tutte (di solito) le XxxViewList:
 * 1) una barra di menu (obbligatorio) di tipo IAMenu
 * 2) un topPlaceholder (eventuale, presente di default) di tipo HorizontalLayout
 * - con o senza campo edit search, regolato da preferenza o da parametro
 * - con o senza bottone New, regolato da preferenza o da parametro
 * - con eventuali bottoni specifici, aggiuntivi o sostitutivi
 * 3) un alertPlaceholder di avviso (eventuale) con label o altro per informazioni; di norma per il developer
 * 4) un headerGridHolder della Grid (obbligatoria) con informazioni sugli elementi della lista
 * 5) una Grid (obbligatoria); alcune regolazioni da preferenza o da parametro (bottone Edit, ad esempio)
 * 6) un bottomPlacehorder della Grid (eventuale) con informazioni sugli elementi della lista; di norma delle somme
 * 7) un bottomPlacehorder (eventuale) con bottoni aggiuntivi
 * 8) un footer (obbligatorio) con informazioni generali
 * <p>
 * Le preferenze vengono (eventualmente) lette da mongo e (eventualmente) sovrascritte nella sottoclasse
 * <p>
 * Annotation @Route(value = "") per la vista iniziale - Ce ne può essere solo una per applicazione
 * ATTENZIONE: se rimangono due (o più) classi con @Route(value = ""), in fase di compilazione appare l'errore:
 * -'org.springframework.context.ApplicationContextException:
 * -Unable to start web server;
 * -nested exception is org.springframework.boot.web.server.WebServerException:
 * -Unable to start embedded Tomcat'
 * <p>
 * Usa l'interfaccia HasUrlParameter col metodo setParameter(BeforeEvent event, ...) per ricevere parametri opzionali
 * anche per chiamate che usano @Route <br>
 * Usa l'interfaccia BeforeEnterObserver col metodo beforeEnter()
 * invocato da @Route al termine dell'init() di questa classe e DOPO il metodo @PostConstruct <br>
 * <p>
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Not annotated with @SpringComponent (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @UIScope (obbligatorio) <br>
 * Annotated with @Route (obbligatorio) per la selezione della vista. @Route(value = "") per la vista iniziale <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @Secured (facoltativo) per l'accesso con security a seconda del ruolo dell'utente loggato <br>
 * - 'developer' o 'admin' o 'user' <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * - la documentazione precedente a questo tag viene SEMPRE riscritta <br>
 * - se occorre preservare delle @Annotation con valori specifici, spostarle DOPO @AIScript <br>
 * Annotated with @AIView (facoltativo Algos) per il menu-name, l'icona-menu, la property-search e la visibilità <br>
 * Se serve una Grid paginata estende APaginatedGridViewList altrimenti AGridViewList <br>
 * Se si usa APaginatedGridViewList è obbligatorio creare la PaginatedGrid
 * 'tipizzata' con la entityClazz (Collection) specifica nel metodo creaGridPaginata <br>
 */
@UIScope
@Route(value = TAG_ALF, layout = MainLayout14.class)
@Qualifier(TAG_ALF)
@Slf4j
@Secured("user")
@AIScript(sovraScrivibile = false)
@AIView(vaadflow = false, menuName = TAG_ALF, menuIcon = VaadinIcon.ASTERISK, searchProperty = "descrizione", roleTypeVisibility = EARoleType.developer)
public class AlfaList extends AGridViewList {

    private Checkbox checkbox1;

    private Checkbox checkbox2;


    /**
     * Costruttore @Autowired <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     * Nella sottoclasse concreta si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Nella sottoclasse concreta si usa una costante statica, per scrivere sempre uguali i riferimenti <br>
     * Passa alla superclasse il service iniettato qui da Vaadin/@Route <br>
     * Passa alla superclasse anche la entityClazz che viene definita qui (specifica di questo modulo) <br>
     *
     * @param service business class e layer di collegamento per la Repository
     */
    @Autowired
    public AlfaList(@Qualifier(TAG_ALF) IAService service) {
        super(service, Alfa.class);
    }// end of Vaadin/@Route constructor


    /**
     * Preferenze specifiche di questa view <br>
     * <p>
     * Chiamato da AViewList.initView() e sviluppato nella sottoclasse APrefViewList <br>
     * Può essere sovrascritto, per modificare le preferenze standard <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

//        super.searchType = EASearch.dialog;
        super.usaPopupFiltro = true;
        super.usaButtonReset = true;
    }// end of method


    /**
     * Crea un (eventuale) Popup di selezione, filtro e ordinamento <br>
     * DEVE essere sovrascritto, per regolare il contenuto (items) <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void creaPopupFiltro() {
        super.creaPopupFiltro();

        filtroComboBox.setPlaceholder("nazionalità ...");
        filtroComboBox.setItems(new ArrayList(Arrays.asList("francese", "inglese", "tedesca", "turco", "russo")));
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

        checkbox1 = new Checkbox();
        checkbox1.setLabel("Ragazzo");
        checkbox1.setIndeterminate(true);
        checkbox1.setValue(true);
        topPlaceholder.add(checkbox1);

        checkbox2 = new Checkbox();
        checkbox2.setLabel("Simpatico");
        checkbox2.setIndeterminate(true);
        checkbox2.setValue(true);
        topPlaceholder.add(checkbox2);
    }// end of method


    /**
     * Aggiunge tutti i listeners ai bottoni di 'topPlaceholder' che sono stati creati SENZA listeners <br>
     * <p>
     * Chiamato da AViewList.initView() e sviluppato nella sottoclasse ALayoutViewList <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void addListeners() {
        super.addListeners();

        if (checkbox1 != null) {
            checkbox1.addValueChangeListener(e -> {
                updateFiltri();
                updateGrid();
            });//end of lambda expression
        }// end of if cycle

        if (checkbox2 != null) {
            checkbox2.addValueChangeListener(e -> {
                updateFiltri();
                updateGrid();
            });//end of lambda expression
        }// end of if cycle
    }// end of method


    /**
     * Aggiorna i filtri specifici della Grid. Modificati per: popup, newEntity, deleteEntity, ecc... <br>
     * <p>
     * Può essere sovrascritto, per costruire i filtri specifici dei combobox, popup, ecc. <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void updateFiltriSpecifici() {
        super.updateFiltriSpecifici();

        String nazionalita = "";
        boolean ragazzo;
        boolean simpatico;

        if (filtroComboBox != null && filtroComboBox.getValue() != null) {
            nazionalita = (String) filtroComboBox.getValue();
            filtri.add(new AFiltro(Criteria.where("nazionalita").is(nazionalita)));
        }// end of if cycle

        if (checkbox1 != null && !checkbox1.isIndeterminate()) {
            ragazzo = checkbox1.getValue();
            filtri.add(new AFiltro(Criteria.where("ragazzo").is(ragazzo)));
        }// end of if cycle

        if (checkbox2 != null && !checkbox2.isIndeterminate()) {
            simpatico = checkbox2.getValue();
            filtri.add(new AFiltro(Criteria.where("simpatico").is(simpatico)));
        }// end of if cycle
    }// end of method


    /**
     * Creazione ed apertura del dialogo per una nuova entity oppure per una esistente <br>
     * Il dialogo è PROTOTYPE e viene creato esclusivamente da appContext.getBean(... <br>
     * Nella creazione vengono regolati il service e la entityClazz di riferimento <br>
     * Contestualmente alla creazione, il dialogo viene aperto con l'item corrente (ricevuto come parametro) <br>
     * Se entityBean è null, nella superclasse AViewDialog viene modificato il flag a EAOperation.addNew <br>
     * Si passano al dialogo anche i metodi locali (di questa classe AViewList) <br>
     * come ritorno dalle azioni save e delete al click dei rispettivi bottoni <br>
     * Il metodo DEVE essere sovrascritto <br>
     *
     * @param entityBean item corrente, null se nuova entity
     */
    @Override
    protected void openDialog(AEntity entityBean) {
        appContext.getBean(AlfaDialog.class, service, entityClazz).open(entityBean, isEntityModificabile ? EAOperation.edit : EAOperation.showOnly, this::save, this::delete);
    }// end of method

}// end of class