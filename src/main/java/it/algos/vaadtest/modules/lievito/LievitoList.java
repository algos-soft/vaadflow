package it.algos.vaadtest.modules.lievito;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.annotation.AIView;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAOperation;
import it.algos.vaadflow.modules.provincia.Provincia;
import it.algos.vaadflow.modules.provincia.ProvinciaService;
import it.algos.vaadflow.modules.role.EARoleType;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadflow.ui.MainLayout14;
import it.algos.vaadflow.ui.list.AGridViewList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.VUOTA;
import static it.algos.vaadtest.application.TestCost.TAG_LIE;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 7-apr-2020 16.09.31 <br>
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
 * La sequenza di costruzione da parte di SpringBott/Vaadin/Route è:
 * 1) Costruttore esterno visibile (Vaadin/@Route constructor) (sempre presente anche implicitamente)
 * 2) init() interno del costruttore Java (sempre presente)
 * 3) @PostConstruct -> postConstruct() (facoltativo, sempre possibile)
 * 4) setParameter() (solo per questa view creata da @Route via browser)
 * 5) beforeEnter() (solo per questa view creata da @Route via browser)
 * Considerato che le sottoclassi concrete NON sono singleton e vengo ri-create ogni volta che dal menu (via @Router)
 * si invocano, è inutile (anche se possibile) usare un metodo @PostConstruct che è sempre un'appendici di init() del
 * costruttore.
 * Meglio spostare tutta la logica iniziale nel metodo beforeEnter() che è l'ultimo della catena di creazione <br>
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
 * 'tipizzata' con la entityClazz (Collection) specifica nel metodo creaGridPaginata() <br>
 */
@UIScope
@Route(value = VUOTA, layout = MainLayout14.class)
@Qualifier(TAG_LIE)
@Slf4j
@Secured("user")
@AIScript(sovraScrivibile = false)
@AIView(vaadflow = false, menuName = TAG_LIE, menuIcon = VaadinIcon.ASTERISK, searchProperty = "descrizione", startListEmpty = true, roleTypeVisibility = EARoleType.developer)
public class LievitoList extends AGridViewList {

    @Autowired
    ProvinciaService provinciaService;

    //--casting del Service per usarlo localmente
    //--casting subito dopo aver invocato il costruttore della superclasse
    private LievitoService service;


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
    public LievitoList(@Qualifier(TAG_LIE) IAService service) {
        super(service, Lievito.class);
        this.service = (LievitoService) service;
    }// end of Vaadin/@Route constructor


    /**
     * La injection viene fatta da Java/SpringBoot SOLO DOPO l'init() interno del costruttore dell'istanza <br>
     * Si usa un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Performing the initialization in a constructor is not suggested
     * as the state of the UI is not properly set up when the constructor is invoked.
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti,
     * ma l'ordine con cui vengono chiamati NON è garantito
     * DEVE essere inserito nella sottoclasse e invocare (eventualmente) un metodo della superclasse.
     */
    @PostConstruct
    protected void postConstruct() {
        //super.inizia();
    }// end of method


    /**
     * Regola i parametri del browser per una view costruita da @Route <br>
     * <p>
     * Chiamato da com.vaadin.flow.router.Router tramite l'interfaccia HasUrlParameter implementata in AViewList <br>
     * Chiamato DOPO @PostConstruct ma PRIMA di beforeEnter() <br>
     * Può essere sovrascritto, per gestire diversamente i parametri in ingresso <br>
     * Invocare PRIMA il metodo della superclasse <br>
     *
     * @param event     con la location, ui, navigationTarget, source, ecc
     * @param parameter opzionali nella chiamata del browser
     */
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        super.setParameter(event, parameter);
    }// end of method


    /**
     * Creazione iniziale (business logic) della view DOPO costruttore, init(), postConstruct() e setParameter() <br>
     * <p>
     * Chiamato da com.vaadin.flow.router.Router tramite l'interfaccia BeforeEnterObserver implementata in AViewList <br>
     * Chiamato DOPO @PostConstruct e DOPO setParameter() <br>
     * Qui va tutta la logica inizale della view <br>
     * Può essere sovrascritto, per costruire diversamente la view <br>
     * Invocare PRIMA il metodo della superclasse <br>
     *
     * @param beforeEnterEvent con la location, ui, navigationTarget, source, ecc
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        super.beforeEnter(beforeEnterEvent);
    }// end of method


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

        super.usaPopupFiltro = true;
    }// end of method


    /**
     * Costruisce gli oggetti base (placeholder) di questa view <br>
     * <p>
     * Li aggiunge alla view stessa <br>
     * Chiamato da AViewList.initView() e sviluppato nella sottoclasse ALayoutViewList <br>
     * Può essere sovrascritto, per modificare il layout standard <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void fixLayout() {
        super.fixLayout();
    }// end of method


    /**
     * Eventuali messaggi di avviso specifici di questa view ed inseriti in 'alertPlacehorder' <br>
     * <p>
     * Chiamato da AViewList.initView() e sviluppato nella sottoclasse ALayoutViewList <br>
     * Normalmente ad uso esclusivo del developer (eventualmente dell'admin) <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaAlertLayout() {
        super.creaAlertLayout();
    }// end of method


    /**
     * Barra dei bottoni SOPRA la Grid inseriti in 'topPlaceholder' <br>
     * <p>
     * In fixPreferenze() si regola quali bottoni mostrare. Nell'ordine: <br>
     * 1) eventuale bottone per cancellare tutta la collezione <br>
     * 2) eventuale bottone di reset per ripristinare (se previsto in automatico) la collezione <br>
     * 3) eventuale bottone New, con testo regolato da preferenza o da parametro <br>
     * 4) eventuale bottone 'Cerca...' per aprire un DialogSearch oppure un campo EditSearch per la ricerca <br>
     * 5) eventuale bottone per annullare la ricerca e riselezionare tutta la collezione <br>
     * 6) eventuale combobox di selezione della company (se applicazione multiCompany) <br>
     * 7) eventuale combobox di selezione specifico <br>
     * 8) eventuali altri bottoni specifici <br>
     * <p>
     * I bottoni vengono creati SENZA listeners che vengono regolati nel metodo addListeners() <br>
     * Chiamato da AViewList.initView() e sviluppato nella sottoclasse ALayoutViewList <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaTopLayout() {
        super.creaTopLayout();
    }// end of method


    /**
     * Crea un (eventuale) Popup di selezione, filtro e ordinamento <br>
     * DEVE essere sovrascritto, per regolare il contenuto (items) <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void creaPopupFiltro() {
        super.creaPopupFiltro();

        //--esempio
        filtroComboBox.setPlaceholder("Provincia ...");
        List<Provincia> items = provinciaService.findAll();
        filtroComboBox.setItems(items);
        filtroComboBox.addValueChangeListener(event -> sincroProvince(event));
    }// end of method


    protected void sincroProvince(HasValue.ValueChangeEvent eventCombo) {
        List items;
        Provincia provincia = (Provincia) eventCombo.getValue();

        if (provincia != null) {
            items = service.findAllByProvincia(provincia);
        } else {
            items = service.findAll();
        }// end of if/else cycle

        grid.setItems(items);
    }// end of method


    /**
     * Crea la lista dei SOLI filtri necessari alla Grid per la prima visualizzazione della view <br>
     * I filtri normali vanno in updateFiltri() <br>
     * <p>
     * Chiamato da AViewList.initView() e sviluppato nella sottoclasse AGridViewList <br>
     * Chiamato SOLO alla creazione della view. Successive modifiche ai filtri sono gestite in updateFiltri() <br>
     * Può essere sovrascritto SOLO se ci sono dei filtri che devono essere attivi già alla partenza della Grid <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaFiltri() {
        super.creaFiltri();
    }// end of method


    /**
     * Aggiorna la lista dei filtri della Grid. Modificati per: popup, newEntity, deleteEntity, ecc... <br>
     * Normalmente tutti i filtri  vanno qui <br>
     * <p>
     * Chiamato da AViewList.initView() e sviluppato nella sottoclasse AGridViewList <br>
     * Alla prima visualizzazione della view usa SOLO creaFiltri() e non questo metodo <br>
     * Può essere sovrascritto, per costruire i filtri specifici dei combobox, popup, ecc. <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void updateFiltri() {
        super.updateFiltri();

        //esempio
        //String nazionalita = "";
        //if (filtroComboBox != null && filtroComboBox.getValue() != null) {
        //    nazionalita = (String) filtroComboBox.getValue();
        //    filtri.add(Criteria.where("nazionalita").is(nazionalita));
        //}// end of if cycle
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
        appContext.getBean(LievitoDialog.class, service, entityClazz).open(entityBean, isEntityModificabile ? EAOperation.edit : EAOperation.showOnly, this::save, this::delete);
    }// end of method

}// end of class