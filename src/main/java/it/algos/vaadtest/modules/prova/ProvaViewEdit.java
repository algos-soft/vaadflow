package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.application.AContext;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.service.AService;
import it.algos.vaadflow.service.AVaadinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static it.algos.vaadflow.application.FlowCost.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 14-mar-2019
 * Time: 16:52
 */
@SpringComponent
@Tag("provaView")
@Route(value = "provaViewRoute")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class ProvaViewEdit extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver, BeforeLeaveObserver {

    protected final Button backButton = new Button(ANNULLA);

    protected final Button saveButton = new Button(REGISTRA);

    protected final Button deleteButton = new Button(DELETE);

    /**
     * Barra dei bottoni di comando <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected final HorizontalLayout bottomLayout = new HorizontalLayout();

    /**
     * Titolo del dialogo <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    protected final Div titleLayout = new Div();

    /**
     * Corpo centrale del Form <br>
     * Placeholder (presente di default) <br>
     */
    protected final FormLayout formLayout = new FormLayout();

    protected String locationBack;

    /**
     * Recuperato dalla sessione, quando la @route fa partire la UI. <br>
     * Viene regolato nel service specifico (AVaadinService) <br>
     */
    @Autowired
    protected ApplicationContext appContext;

    protected AContext context;

    /**
     * Flag di preferenza per usare il bottone back. Normalmente true.
     */
    protected boolean usaBackButton;

    /**
     * Flag di preferenza per usare il bottone Save. Normalmente true.
     */
    protected boolean usaSaveButton;

    /**
     * Flag di preferenza per usare il bottone delete. Normalmente true.
     */
    protected boolean usaDeleteButton;

    /**
     * Flag di preferenza per le due colonne nel form. Normalmente true.
     */
    protected boolean usaFormDueColonne;

    @Autowired
    protected ProvaService service;

    //    protected T currentItem;
    protected AEntity currentItem;

    protected AEntity entityBean;

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     * Unica per tutta l'applicazione. Usata come libreria. <br>
     */
    @Autowired
    private AVaadinService vaadinService;

    private String idKey;


    public ProvaViewEdit() {
    }


//    public ProvaView(String locationBack, String idKey) {
//        this.locationBack = locationBack;
//        this.idKey = idKey;
//    }


    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * Le preferenze vengono (eventualmente) lette da mongo e (eventualmente) sovrascritte nella sottoclasse
     */
    @PostConstruct
    protected void initView() {

        //--Login and context della sessione
        context = vaadinService.getSessionContext();

        //--ApplicationContext
//        appContext = StaticContextAccessor.getBean(ApplicationContext.class);

        //--Service della classe
//        service = (ProvaService) appContext.getBean("ProvaService");

        //--Le preferenze standard
        fixPreferenze();

        //--Le preferenze specifiche, eventualmente sovrascritte nella sottoclasse
        fixPreferenzeSpecifiche();

        //--Titolo placeholder del dialogo, regolato dopo open()
        this.add(creaTitleLayout());

        //--Form placeholder standard per i campi, creati dopo open()
        this.add(creaFormLayout());

        //--spazio per distanziare i bottoni dai campi
        this.add(new H3());

        //--Barra placeholder dei bottoni, creati adesso ma regolabili dopo open()
        this.add(creaBottomLayout());


//        setCloseOnEsc(true);
//        setCloseOnOutsideClick(false);
//        addOpenedChangeListener(event -> {
//            if (!isOpened()) {
//                getElement().removeFromParent();
//            }// end of if cycle
//        });//end of lambda expressions and anonymous inner class
    }// end of method


    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();
        Map<String, List<String>> parametersMap = queryParameters.getParameters();

        if (parametersMap!=null&&parametersMap.size()>0) {
            setParameters(parametersMap);
        }// end of if cycle

        open();
    }// end of method


    private void setParameters(Map<String, List<String>> parametersMap) {
        idKey=parametersMap.get("id").get(0);
    }// end of method


    /**
     * Le preferenze vengono (eventualmente) lette da mongo e (eventualmente) sovrascritte nella sottoclasse
     */
    private void fixPreferenze() {
        //--Flag di preferenza per usare il bottone Cancel. Normalmente true.
        usaBackButton = true;

        //--Flag di preferenza per usare il bottone Save. Normalmente true.
        usaSaveButton = true;

        //--Flag di preferenza per usare il bottone Delete. Normalmente true.
        usaDeleteButton = true;

        //Flag di preferenza per le due colonne nel form. Normalmente true.
        usaFormDueColonne = true;
    }// end of method


    /**
     * Le preferenze specifiche, eventualmente sovrascritte nella sottoclasse
     * Può essere sovrascritto, per aggiungere informazioni
     * Invocare PRIMA il metodo della superclasse
     */
    protected void fixPreferenzeSpecifiche() {
    }// end of method


    /**
     * Titolo del dialogo <br>
     * Placeholder (eventuale, presente di default) <br>
     */
    private Component creaTitleLayout() {
        return titleLayout;
    }// end of method


    /**
     * Body placeholder per i campi, creati dopo open()
     */
    protected Div creaFormLayout() {
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

        return div;
    }// end of method


    /**
     * Barra dei bottoni
     */
    protected Component creaBottomLayout() {
        bottomLayout.setClassName("buttons");
        bottomLayout.setPadding(false);
        bottomLayout.setSpacing(true);
        bottomLayout.setMargin(false);

        Label spazioVuotoEspandibile = new Label("");
        bottomLayout.add(spazioVuotoEspandibile);

        backButton.addClickListener(e -> back());
        backButton.setIcon(new Icon(VaadinIcon.ARROW_LEFT));
        bottomLayout.add(backButton);

        saveButton.getElement().setAttribute("theme", "primary");
        saveButton.setIcon(new Icon(VaadinIcon.DATABASE));
        bottomLayout.add(saveButton);

//        deleteButton.addClickListener(e -> deleteClicked());
        deleteButton.setIcon(new Icon(VaadinIcon.CLOSE_CIRCLE));
        deleteButton.getElement().setAttribute("theme", "error");
        bottomLayout.add(deleteButton);

        bottomLayout.setFlexGrow(1, spazioVuotoEspandibile);
        return bottomLayout;
    }// end of method


    public void open() {
        //--controllo iniziale di sicurezza
        if (service == null) {
            Notification.show("Manca il service xxx.open()", 3000, Notification.Position.BOTTOM_START);
            return;
        }// end of if cycle

        if (((AService) service).mancaCompanyNecessaria()) {
            Notification.show("Non è stata selezionata nessuna company in AViewDialog.open()", 3000, Notification.Position.BOTTOM_START);
            return;
        }// end of if cycle

        entityBean = service.findById(idKey);
        if (entityBean == null) {
            Notification.show("Qualcosa non ha funzionato in AViewDialog.open()", 3000, Notification.Position.BOTTOM_START);
            return;
        }// end of if cycle

        int a = 87;
//        this.currentItem = (T) entityBean;
//        this.operation = operation;
//        this.context = context;
//        Object view = presenter.getView();
//        if (view != null) {
//            this.itemType = presenter.getView().getMenuName();
//        }// end of if cycle
//        this.fixTitleLayout(title);
//
//        if (registrationForSave != null) {
//            registrationForSave.remove();
//        }
//        registrationForSave = saveButton.addClickListener(e -> saveClicked(operation));
//
//        //--Controlla la visibilità dei bottoni
//        saveButton.setVisible(operation.isSaveEnabled());
//        deleteButton.setVisible(operation.isDeleteEnabled());
//
        //--Crea i fields
//        creaFields();
//
//        super.open();
    }// end of method


    /**
     * Azione proveniente dal click sul bottone Back
     */
    public void back() {
        if (locationBack != null) {
            routeVerso(locationBack);
        }// end of if cycle
    }// end of method


    /**
     * Navigazione verso un altra pagina
     */
    protected void routeVerso(String location) {
        UI ui = null;
        Optional<UI> optional = getUI();

        if (optional.isPresent()) {
            ui = optional.get();
        }// end of if cycle

        if (ui != null) {
            ui.navigate(location);
        }// end of if cycle
    }// end of method


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    }// end of method


    @Override
    public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
    }// end of method

}// end of class
