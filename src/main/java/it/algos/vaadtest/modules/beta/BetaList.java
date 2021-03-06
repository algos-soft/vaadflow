package it.algos.vaadtest.modules.beta;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.*;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.annotation.AIView;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAOperation;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadflow.ui.MainLayout14;
import it.algos.vaadflow.ui.list.AGridViewList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.annotation.Secured;

import java.util.HashMap;

import static it.algos.vaadflow.application.FlowCost.KEY_MAPPA_ENTITY_BEAN;
import static it.algos.vaadflow.application.FlowCost.KEY_MAPPA_FORM_TYPE;
import static it.algos.vaadtest.application.TestCost.TAG_BET;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 14-ott-2019 18.56.46 <br>
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
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = TAG_BET, layout = MainLayout14.class)
@Qualifier(TAG_BET)
@Slf4j
@Secured("developer")
@AIScript(sovraScrivibile = false)
@AIView(vaadflow = false, routeFormName = "betaForm", menuName = TAG_BET, menuIcon = VaadinIcon.MAILBOX, searchProperty = "code")
public class BetaList extends AGridViewList {


    //--casting del Service per usarlo localmente
    private BetaService service;


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
    public BetaList(@Qualifier(TAG_BET) IAService service) {
        super(service, Beta.class);
        this.service = (BetaService) service;
    }// end of Vaadin/@Route constructor


    /**
     * Metodo chiamato da com.vaadin.flow.router.Router verso questa view tramite l'interfaccia BeforeEnterObserver <br>
     * Chiamato DOPO @PostConstruct ma PRIMA di beforeEnter <br>
     *
     * @param event     con la location, ui, navigationTarget, source, ecc
     * @param parameter opzionali nella chiamata del browser
     */
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
    }// end of method


    /**
     * Metodo chiamato da com.vaadin.flow.router.Router verso questa view tramite l'interfaccia BeforeEnterObserver <br>
     * Chiamato DOPO @PostConstruct e DOPO beforeEnter <br>
     *
     * @param beforeEnterEvent con la location, ui, navigationTarget, source, ecc
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        super.beforeEnter(beforeEnterEvent);
    }// end of method


    /**
     * Preferenze standard <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     * Le preferenze vengono (eventualmente) lette da mongo e (eventualmente) sovrascritte nella sottoclasse <br>
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaBottoneEdit = true;
        super.isEntityModificabile = true;
        super.usaBottomLayout = true;

        super.usaRouteFormView = true;
//        logger.debug("Prova mail", getClass(), "fixPreferenze");
//        logger.info("Prova mail", getClass(), "fixPreferenze");
//        logger.warn("Prova mail", getClass(), "fixPreferenze");
//        logger.error("Prova mail", getClass(), "fixPreferenze");
    }// end of method


    protected void creaTopLayout() {
        super.creaTopLayout();

        Button nulla = new Button("Nulla", new Icon(VaadinIcon.ASTERISK));
        nulla.getElement().setAttribute("theme", "error");
        nulla.addClassName("view-toolbar__button");
        nulla.addClickListener(e -> navigaNulla());

        Button singolo = new Button("Singolo", new Icon(VaadinIcon.ASTERISK));
        singolo.getElement().setAttribute("theme", "error");
        singolo.addClassName("view-toolbar__button");
        singolo.addClickListener(e -> navigaSingolo());

        Button mappaSenzaPar = new Button("Mappa par errato", new Icon(VaadinIcon.ASTERISK));
        mappaSenzaPar.getElement().setAttribute("theme", "error");
        mappaSenzaPar.addClassName("view-toolbar__button");
        mappaSenzaPar.addClickListener(e -> navigaMappaParErrata());

        Button mappaSenzaVal = new Button("Mappa val errato", new Icon(VaadinIcon.ASTERISK));
        mappaSenzaVal.getElement().setAttribute("theme", "error");
        mappaSenzaVal.addClassName("view-toolbar__button");
        mappaSenzaVal.addClickListener(e -> navigaMappaValErrato());

        Button mappaEdit = new Button("Edit", new Icon(VaadinIcon.ASTERISK));
        mappaEdit.getElement().setAttribute("theme", "error");
        mappaEdit.addClassName("view-toolbar__button");
        mappaEdit.addClickListener(e -> navigaMappaCorrettaEdit());

        Button mappaNew = new Button("New", new Icon(VaadinIcon.ASTERISK));
        mappaNew.getElement().setAttribute("theme", "error");
        mappaNew.addClassName("view-toolbar__button");
        mappaNew.addClickListener(e -> navigaMappaCorrettaNew());

        Button mappaEditValErrato = new Button("Edit val errato", new Icon(VaadinIcon.ASTERISK));
        mappaEditValErrato.getElement().setAttribute("theme", "error");
        mappaEditValErrato.addClassName("view-toolbar__button");
        mappaEditValErrato.addClickListener(e -> navigaEditValErrato());

        Button mappaEditValCorretto = new Button("Edit val corretto", new Icon(VaadinIcon.ASTERISK));
        mappaEditValCorretto.getElement().setAttribute("theme", "error");
        mappaEditValCorretto.addClassName("view-toolbar__button");
        mappaEditValCorretto.addClickListener(e -> navigaEditValCorretto());

        topPlaceholder.add(nulla, singolo, mappaSenzaPar, mappaSenzaVal, mappaEdit, mappaNew, mappaEditValErrato, mappaEditValCorretto);
    }// end of method


    protected void navigaNulla() {
        getUI().ifPresent(ui -> ui.navigate("form"));
    }// end of method


    protected void navigaSingolo() {
        getUI().ifPresent(ui -> ui.navigate("form" + "/27"));
    }// end of method


    protected void navigaMappaParErrata() {
        HashMap mappa = new HashMap();
        mappa.put("mariorr", "domani");
        final QueryParameters query = routeService.getQuery(mappa);
        getUI().ifPresent(ui -> ui.navigate("form", query));
    }// end of method


    protected void navigaMappaValErrato() {
        HashMap mappa = new HashMap();
        mappa.put(KEY_MAPPA_FORM_TYPE, "domani");
        final QueryParameters query = routeService.getQuery(mappa);
        getUI().ifPresent(ui -> ui.navigate("form", query));
    }// end of method


    protected void navigaMappaCorrettaEdit() {
        HashMap mappa = new HashMap();
        mappa.put(KEY_MAPPA_FORM_TYPE, EAOperation.edit.getNameInText());
        final QueryParameters query = routeService.getQuery(mappa);
        getUI().ifPresent(ui -> ui.navigate("form", query));
    }// end of method


    protected void navigaMappaCorrettaNew() {
        HashMap mappa = new HashMap();
        mappa.put(KEY_MAPPA_FORM_TYPE, EAOperation.addNew.name());
        final QueryParameters query = routeService.getQuery(mappa);
        getUI().ifPresent(ui -> ui.navigate("form", query));
    }// end of method


    protected void navigaEditValErrato() {
        HashMap mappa = new HashMap();
        mappa.put(KEY_MAPPA_FORM_TYPE, EAOperation.edit.name());
        mappa.put(KEY_MAPPA_ENTITY_BEAN, "alfa");
        final QueryParameters query = routeService.getQuery(mappa);
        getUI().ifPresent(ui -> ui.navigate("form", query));
    }// end of method


    protected void navigaEditValCorretto() {
        HashMap mappa = new HashMap();
        mappa.put(KEY_MAPPA_FORM_TYPE, EAOperation.edit.name());
        mappa.put(KEY_MAPPA_ENTITY_BEAN, service.findByKeyUnica("alfa").id);
        final QueryParameters query = routeService.getQuery(mappa);
        getUI().ifPresent(ui -> ui.navigate("form", query));
    }// end of method


    /**
     * Costruisce un (eventuale) layout con bottoni aggiuntivi <br>
     * Facoltativo (assente di default) <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected void creaGridBottomLayout() {
        super.creaGridBottomLayout();
//        bottomPlacehorder.add(new Label("Pippoz"));
    }// end of method


//    protected Button createEditButton(AEntity entityBean) {
//        String label = VUOTA;
//        String iconaTxt = pref.getStr(ICONA_EDIT_BUTTON);
//        if (pref.isBool(FlowCost.USA_TEXT_EDIT_BUTTON)) {
//            label = isEntityModificabile ? pref.getStr(FLAG_TEXT_EDIT) : pref.getStr(FLAG_TEXT_SHOW);
//        }// end of if cycle
//
//        buttonEdit = new Button(label, event -> openForm(entityBean));
////        buttonEdit.setIcon(new Icon("lumo", isEntityModificabile ? "edit" : "search"));
//        buttonEdit.setIcon(new Icon("lumo", iconaTxt));
//        buttonEdit.addClassName("review__edit");
//        buttonEdit.getElement().setAttribute("theme", "tertiary");
//        buttonEdit.setHeight("1em");
//
//        return buttonEdit;
//    }// end of method


    /**
     * Creazione ed apertura del dialogo per una nuova entity oppure per una esistente <br>
     * Il metodo DEVE essere sovrascritto e chiamare super.openForm(AEntity entityBean, String formRouteName) <br>
     *
     * @param entityBean item corrente, null se nuova entity
     */
    @Override
    protected void openForm(AEntity entityBean) {
        String formRouteName = annotation.getFormRouteName(BetaList.class);
        super.openForm(entityBean, formRouteName);
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
        appContext.getBean(BetaDialog.class, service, entityClazz).open(entityBean, isEntityModificabile ? EAOperation.edit : EAOperation.showOnly, this::save, this::delete);
    }// end of method

}// end of class