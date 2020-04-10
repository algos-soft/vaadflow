package it.algos.vaadflow.ui.form;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.History;
import com.vaadin.flow.router.*;
import com.vaadin.flow.shared.ui.LoadMode;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAColor;
import it.algos.vaadflow.enumeration.EAOperation;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadtest.modules.beta.Beta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static it.algos.vaadflow.application.FlowCost.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 10-apr-2020
 * Time: 05:51
 * Classe astratta per visualizzare il Form <br>
 * La classe viene divisa verticalmente in alcune classi astratte, per 'leggerla' meglio (era troppo grossa) <br>
 * Nell'ordine (dall'alto):
 * - 1 APropertyViewForm (che estende la classe Vaadin VerticalLayout) per elencare tutte le property usate <br>
 * - 2 AViewForm con la business logic principale <br>
 * L'utilizzo pratico per il programmatore è come se fosse una classe sola <br>
 * <p>
 * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
 * La injection viene fatta da @Route nel costruttore <br>
 * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() <br>
 * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
 * 1) viene chiamato il costruttore (da @Route) <br>
 * 2) viene eseguito l'init(); del costruttore <br>
 * 3) viene chiamato @PostConstruct da SpringBoot (con qualsiasi firma) <br>
 * 4) viene chiamato setParameter(); <br>
 * 5) viene chiamato beforeEnter(); <br>
 * <p>
 * La sottoclasse concreta viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
 * Le property di questa classe/sottoclasse vengono iniettate (@Autowired) automaticamente se: <br>
 * 1) vengono dichiarate nel costruttore @Autowired della sottoclasse concreta <br>
 * 2) sono istanze di una classe SINGLETON, richiamate con AxxService.getInstance() <br>
 * 3) sono annotate @Autowired; ricorda che sono disponibili SOLO DOPO @PostConstruct <br>
 * <p>
 * Considerato che le sottoclassi concrete NON sono singleton e vengo ri-create ogni volta che dal menu (via @Router)
 * si invocano, è inutile (anche se possibile) usare un metodo @PostConstruct che è sempre un'appendice di init() del
 * costruttore.
 * Meglio spostare tutta la logica iniziale nel metodo beforeEnter() <br>
 * <p>
 * Le sottoclassi concrete NON hanno le annotation @SpringComponent, @SpringView e @Scope
 * NON annotated with @SpringComponent - Sbagliato perché va in conflitto con la @Route
 * NON annotated with @SpringView - Sbagliato perché usa la Route di VaadinFlow
 * NON annotated with @Scope - Sbagliato perché usa @UIScope
 * Annotated with @Route (obbligatorio) per la selezione della vista.
 * <p>
 * Graficamente abbiamo in tutte (di solito) le XxxViewForm:
 * 1) un titolo (eventuale, presente di default) di tipo Label o HorizontalLayout
 * 2) un alertPlaceholder di avviso (eventuale) con label o altro per informazioni; di norma per il developer
 * 3) un Form (obbligatorio);
 * 4) un bottomPlacehorder (obbligatorio) con i bottoni di navigazione, conferma, cancella
 * 5) un footer (obbligatorio) con informazioni generali
 * <p>
 * Le injections vengono fatta da SpringBoot nel metodo @PostConstruct DOPO init() automatico
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
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 */
@HtmlImport(value = "styles/algos-styles.html", loadMode = LoadMode.INLINE)
@Slf4j
public abstract class AViewForm extends APropertyViewForm implements BeforeEnterObserver, HasUrlParameter<String> {

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
    public AViewForm(IAService service, Class<? extends AEntity> entityClazz) {
        this.service = service;
        this.entityClazz = entityClazz;
    }// end of Vaadin/@Route constructor


    /**
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     * La injection viene fatta da @Route nel costruttore <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * 1) viene chiamato il costruttore (da @Route) <br>
     * 2) viene eseguito l'init(); del costruttore <br>
     * 3) viene chiamato @PostConstruct da SpringBoot (con qualsiasi firma) <br>
     * 4) viene chiamato setParameter(); <br>
     * 5) viene chiamato beforeEnter(); <br>
     * <p>
     * Considerato che le sottoclassi concrete NON sono singleton e vengo ri-create ogni volta che dal menu (via @Router)
     * si invocano, è inutile (anche se possibile) usare un metodo @PostConstruct che è sempre un'appendice di init() del
     * costruttore.
     * Meglio spostare tutta la logica iniziale nel metodo beforeEnter() <br>
     */
    @PostConstruct
    protected void postConstruct() {
    }// end of method


    /**
     * Regola i parametri provenienti dal browser per una view costruita da @Route <br>
     * <p>
     * Chiamato da com.vaadin.flow.router.Router tramite l'interfaccia HasUrlParameter implementata in AViewForm <br>
     * Chiamato DOPO @PostConstruct ma PRIMA di beforeEnter() <br>
     * <p>
     * Dal browser arrivano come parametri:
     * 1) typo di form da utilizzare: New, Edit, Show (obbligatorio) <br>
     * 2) entityBean specifico (obbligatorio se Edit o Show) sotto forma di ID univoco della entityClazz specifica <br>
     * 3) link di ritorno (facoltativi) <br>
     * Può essere sovrascritto, per gestire diversamente i parametri in ingresso <br>
     * Invocare PRIMA il metodo della superclasse <br>
     *
     * @param event     con la location, ui, navigationTarget, source, ecc
     * @param parameter opzionali nella chiamata del browser
     */
    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();
        Map<String, List<String>> multiParametersMap = queryParameters.getParameters();

        if (text.isValid(parameter)) {
            this.singleParameter = parameter;
        }// end of if cycle

        if (array.isValid(multiParametersMap)) {
            if (array.isMappaSemplificabile(multiParametersMap)) {
                this.parametersMap = array.semplificaMappa(multiParametersMap);
            } else {
                this.multiParametersMap = multiParametersMap;
            }// end of if/else cycle
        }// end of if cycle
    }// end of method


    /**
     * Creazione iniziale (business logic) della view DOPO costruttore, init(), postConstruct() e setParameter() <br>
     * <p>
     * Chiamato da com.vaadin.flow.router.Router tramite l'interfaccia HasUrlParameter implementata in AViewForm <br>
     * Chiamato DOPO @PostConstruct e DOPO setParameter() <br>
     * Può essere sovrascritto, per costruire diversamente la view <br>
     * Invocare PRIMA il metodo della superclasse <br>
     *
     * @param beforeEnterEvent con la location, ui, navigationTarget, source, ecc
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        fixParameters();
        fixPreferenze();
        initView();
    }// end of method


    /**
     * Preferenze standard <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     * Le preferenze vengono (eventualmente) lette da mongo e (eventualmente) sovrascritte nella sottoclasse <br>
     */
    protected void fixPreferenze() {
        super.usaTitoloForm = true;
        super.titoloForm = VUOTA;
        super.usaFormDueColonne= true;

        super.usaBackButton = true;
        super.usaCancelButton = false;
        super.usaAnnullaButton = false;
        super.usaSaveButton = true;
        super.usaDeleteButton = false;

        super.backButtonText = BOT_BACK;
        super.cancelButtonText = "Pippoz";
        super.annullaButtonText = "Pippoz";
        super.saveButtonText = "Pippoz";
        super.deleteButtonText = "Pippoz";

        super.backButtonIcon = "Pippoz";
        super.cancelButtonIcon = "Pippoz";
        super.annullaButtonIcon = "Pippoz";
        super.saveButtonIcon = "Pippoz";
        super.deleteButtonIcon = "Pippoz";
    }// end of method


    /**
     * Elabora i parametri ricevuti <br>
     * <p>
     * Dal browser arrivano come parametri:
     * 1) typo di form da utilizzare: New, Edit, Show (obbligatorio) <br>
     * 2) entityBean specifico (obbligatorio se Edit o Show) sotto forma di ID univoco della entityClazz specifica <br>
     * 3) link di ritorno (facoltativi) <br>
     */
    protected void fixParameters() {
        String operationFormTxt = VUOTA;
        String entityBeanKey = VUOTA;

        //--formType
        if (parametersMap != null) {
            if (parametersMap.containsKey(KEY_MAPPA_FORM_TYPE)) {
                operationFormTxt = parametersMap.get(KEY_MAPPA_FORM_TYPE);
                if (text.isValid(operationFormTxt)) {
                    if (EAOperation.contiene(operationFormTxt)) {
                        operationForm = EAOperation.valueOf(operationFormTxt);
                        logger.info("Per ora tutto bene. Il formOperation è arrivato del tipo: " + operationFormTxt, getClass(), "fixPreferenze");
                    } else {
                        logger.error("Il valore " + operationFormTxt + " del parametro formOperation non è tra quelli previsti", getClass(), "fixPreferenze");
                        ritorno();
                        return;
                    }// end of if/else cycle
                } else {
                    logger.error("Il parametro formOperation è arrivato ma è vuoto", getClass(), "fixPreferenze");
                    ritorno();
                    return;
                }// end of if/else cycle
            } else {
                logger.error("La mappa parametersMap non contiene formOperation", getClass(), "fixPreferenze");
                ritorno();
                return;
            }// end of if/else cycle
        } else {
            logger.error("Manca parametersMap", getClass(), "fixPreferenze");
            ritorno();
            return;
        }// end of if/else cycle

        //--entityBean
        //--parametersMap è sicuramente arrivata, se non sarebbe uscito prima
        if (parametersMap.containsKey(KEY_MAPPA_ENTITY_BEAN)) {
            entityBeanKey = parametersMap.get(KEY_MAPPA_ENTITY_BEAN);
            if (text.isValid(entityBeanKey)) {
                if (isValidEntityBeanKey(entityBeanKey)) {
                    logger.info("Per ora tutto bene. Il parametro entityBeanKey è arrivato col valore " + entityBeanKey + " ed ha recuperato la entityBean corrispondente", getClass(), "fixPreferenze");
                } else {
                    logger.error("Il valore " + entityBeanKey + " del parametro entityBeanKey non corrisponde a nessuna entityBean del mongoDB " + entityClazz.getSimpleName(), getClass(), "fixPreferenze");
                    ritorno();
                    return;
                }// end of if/else cycle
            } else {
                logger.error("Il parametro entityBeanKey è arrivato ma è vuoto", getClass(), "fixPreferenze");
                ritorno();
                return;
            }// end of if/else cycle
        } else {
            if (operationFormTxt.equals(EAOperation.addNew.name())) {
                logger.info("Per ora tutto bene. Manca l'entityBeanKey ma non serviva.", getClass(), "fixPreferenze");
            } else {
                logger.error("La mappa parametersMap non contiene entityBeanKey", getClass(), "fixPreferenze");
                ritorno();
                return;
            }// end of if/else cycle
        }// end of if/else cycle
    }// end of method


    /**
     * Controlla la validità del parametro entityBeanKey in ingresso <br>
     * Regola la property interna entityBean <br>
     */
    protected boolean isValidEntityBeanKey(String entityBeanKey) {
        if (text.isValid(entityBeanKey)) {
            entityBean = service.findById(entityBeanKey);
        }// end of if cycle

        return entityBean != null;
    }// end of method


    /**
     * Qui va tutta la logica inizale della view <br>
     * <p>
     * Graficamente abbiamo in tutte (di solito) le XxxViewForm: <br>
     * 1) un titolo (eventuale, presente di default) di tipo Label o HorizontalLayout <br>
     * 2) un alertPlaceholder di avviso (eventuale) con label o altro per informazioni; di norma per il developerv <br>
     * 3) un Form (obbligatorio); <br>
     * 4) un bottomPlacehorder (obbligatorio) con i bottoni di navigazione, conferma, cancella <br>
     * 5) un footer (obbligatorio) con informazioni generali <br>
     */
    protected void initView() {
        //--Costruisce tutti i placeholder di questa view
        this.fixLayout();

        //--Regola il titolo della view <br>
        this.fixTitleLayout();

        //--Eventuali messaggi di avviso specifici di questa view ed inseriti in 'alertPlacehorder' <br>
        this.fixAlertLayout();

        //--Form placeholder standard per i campi
        this.fixFormBody();

        //--Separatore
        this.add(new H3());

        //--Form placeholder accessorio eventuale per altri campi, resi graficamente diversi
        this.fixFormSubBody();

        //--Regola la barra dei bottoni di comando <br>
        this.fixBottomBar();
    }// end of method


    /**
     * Costruisce tutti i placeholder di questa view e li aggiunge alla view stessa <br>
     * Chiamato da AViewForm.initView() <br>
     * Può essere sovrascritto, per modificare il layout standard <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixLayout() {
        this.setMargin(false);
        this.setSpacing(false);
        this.setPadding(true);

        super.titlePlaceholder = new Div();
        super.alertPlacehorder = new VerticalLayout();
        super.bodyPlaceHolder = new FormLayout();
        super.bodySubPlaceHolder = new FormLayout();
        super.bottomPlacehorder = new HorizontalLayout();

        if (pref.isBool(USA_DEBUG)) {
            this.getElement().getStyle().set("background-color", EAColor.yellow.getEsadecimale());
            titlePlaceholder.getElement().getStyle().set("background-color", EAColor.lime.getEsadecimale());
            alertPlacehorder.getElement().getStyle().set("background-color", EAColor.lightgreen.getEsadecimale());
            bodyPlaceHolder.getElement().getStyle().set("background-color", EAColor.bisque.getEsadecimale());
            bodySubPlaceHolder.getElement().getStyle().set("background-color", EAColor.red.getEsadecimale());
            bottomPlacehorder.getElement().getStyle().set("background-color", EAColor.silver.getEsadecimale());
        }// end of if cycle

        this.add(titlePlaceholder, alertPlacehorder, bodyPlaceHolder, bodySubPlaceHolder, bottomPlacehorder);
    }// end of method


    /**
     * Regola il titolo della view <br>
     * <p>
     * Chiamato da AViewForm.initView() <br>
     * Recupera recordName dalle @Annotation della classe Entity. Non dovrebbe mai essere vuoto. <br>
     * Costruisce il titolo con la descrizione dell'operazione (New, Edit,...) ed il recordName <br>
     * Sostituisce interamente il titlePlaceholder <br>
     */
    protected void fixTitleLayout() {
        String recordName = annotation.getRecordName(Beta.class);
        String title = text.isValid(recordName) ? recordName : "Error";
        title=operationForm.getNameInTitle() + " " + title.toLowerCase();
        String titoloValido = text.isValid(titoloForm) ? titoloForm : title;

        if (usaTitoloForm) {
            if (operationForm != null) {
                titlePlaceholder.add(new H2(titoloValido));
            }// end of if cycle
        }// end of if cycle
    }// end of method


    /**
     * Eventuali messaggi di avviso specifici di questa view ed inseriti in 'alertPlacehorder' <br>
     * <p>
     * Chiamato da AViewForm.initView() <br>
     * Normalmente ad uso esclusivo del developer (eventualmente dell'admin) <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixAlertLayout() {
        alertPlacehorder.removeAll();
        alertPlacehorder.setMargin(false);
        alertPlacehorder.setSpacing(false);
        alertPlacehorder.setPadding(false);
    }// end of method

    /**
     * Form placeholder standard per i campi <br>
     * Chiamato da AViewForm.initView() <br>
     */
    protected void fixFormBody() {
        FormLayout formLayout=new FormLayout();
        Div div;
        if (usaFormDueColonne) {
            formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                    new FormLayout.ResponsiveStep("50em", 2));
        } else {
            formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("50em", 1));
        }// end of if/else cycle

        formLayout.addClassName("no-padding");
        div = new Div(formLayout);
        div.addClassName("has-padding");

        this.add(div);
    }// end of method

    /**
     * Form placeholder accessorio eventuale per altri campi, resi graficamente diversi <br>
     * Chiamato da AViewForm.initView() <br>
     */
    protected void fixFormSubBody() {
    }// end of method

    /**
     * Regola la barra dei bottoni di comando <br>
     * Chiamato da AViewForm.initView() <br>
     */
    protected void fixBottomBar() {
        bottomPlacehorder.removeAll();
        bottomPlacehorder.setMargin(false);
        bottomPlacehorder.setSpacing(false);
        bottomPlacehorder.setPadding(false);
        Label spazioVuotoEspandibile = new Label("");

        fixBackButton();
        fixEditButton();
        fixSaveButton();
        fixDeleteButton();

//        cancelButton = new Button(ANNULLA);
//        annullaButton = new Button(ANNULLA);

        bottomPlacehorder.setFlexGrow(1, spazioVuotoEspandibile);
    }// end of method


    /**
     * Regola il bottone di 'ritorno' <br>
     * Chiamato da AViewForm.fixBottomBar() <br>
     * Può essere sovrascritto, per modificare titolo, icona, colore e dimensioni del bottone <br>
     */
    protected void fixBackButton() {
        if (usaBackButton) {
            backButton = new Button(backButtonText);
            backButton.addClickListener(e -> ritorno());
            backButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
            if (pref.isBool(USA_BUTTON_SHORTCUT)) {
                backButton.addClickShortcut(Key.ARROW_LEFT);
            }// end of if cycle
            bottomPlacehorder.add(backButton);
        }// end of if cycle
    }// end of method


    /**
     * Regola il bottone di 'modifica' <br>
     * Chiamato da AViewForm.fixBottomBar() <br>
     * Può essere sovrascritto, per modificare titolo, icona, colore e dimensioni del bottone <br>
     */
    protected void fixEditButton() {
        if (usaEditButton) {
            editButton = new Button("Edit");
            editButton.addClickListener(e -> ritorno()); //@todo provvisorio
            editButton.setIcon(new Icon(VaadinIcon.EDIT));
            if (pref.isBool(USA_BUTTON_SHORTCUT)) {
                editButton.addClickShortcut(Key.ENTER);
            }// end of if cycle
            bottomPlacehorder.add(editButton);
        }// end of if cycle
    }// end of method

    /**
     * Regola il bottone di 'registra' <br>
     * Chiamato da AViewForm.fixBottomBar() <br>
     * Può essere sovrascritto, per modificare titolo, icona, colore e dimensioni del bottone <br>
     */
    protected void fixSaveButton() {
        if (usaSaveButton) {
            saveButton = new Button("Save");
            saveButton.addClickListener(e -> ritorno()); //@todo provvisorio
            saveButton.setIcon(new Icon(VaadinIcon.DATABASE));
            if (pref.isBool(USA_BUTTON_SHORTCUT)) {
                saveButton.addClickShortcut(Key.ENTER);
            }// end of if cycle
            bottomPlacehorder.add(saveButton);
        }// end of if cycle
    }// end of method

    /**
     * Regola il bottone di 'registra' <br>
     * Chiamato da AViewForm.fixBottomBar() <br>
     * Può essere sovrascritto, per modificare titolo, icona, colore e dimensioni del bottone <br>
     */
    protected void fixDeleteButton() {
        if (usaDeleteButton) {
            deleteButton = new Button("Delete");
            deleteButton.getElement().setAttribute("theme", "error");
            deleteButton.addClickListener(e -> ritorno()); //@todo provvisorio
            deleteButton.setIcon(new Icon(VaadinIcon.CLOSE_CIRCLE));
            if (pref.isBool(USA_BUTTON_SHORTCUT)) {
                deleteButton.addClickShortcut(Key.ENTER);
            }// end of if cycle
            bottomPlacehorder.add(deleteButton);
        }// end of if cycle
    }// end of method


    /**
     * Torna alla view precedente <br>
     */
    protected void ritorno() {
        History history = UI.getCurrent().getPage().getHistory();
        history.back();
    }// end of method

}// end of class
