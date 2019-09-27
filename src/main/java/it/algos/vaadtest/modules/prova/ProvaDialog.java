package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.application.StaticContextAccessor;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAColor;
import it.algos.vaadflow.enumeration.EAOperation;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.address.AddressService;
import it.algos.vaadflow.modules.address.AddressDialog;
import it.algos.vaadflow.presenter.IAPresenter;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadflow.ui.dialog.ADialog;
import it.algos.vaadflow.ui.dialog.AViewDialog;
import it.algos.vaadflow.ui.fields.ATextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.vaadin.gatanaso.MultiselectComboBox;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.FLASH;
import static it.algos.vaadtest.application.TestCost.TAG_PRO;

/**
 * Project vaadtest <br>
 * Created by Algos
 * User: Gac
 * Fix date: 20-ott-2018 18.52.31 <br>
 * <p>
 * Estende la classe astratta AViewDialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_PRO)
@Slf4j
@AIScript(sovrascrivibile = false)
public class ProvaDialog extends AViewDialog<Prova> {

    private final static String INDIRIZZO = "indirizzoStatico";

    @Autowired
    ApplicationContext appContext;

    private AddressService addressService;

    private AddressDialog addressDialog;

    private Address indirizzoTemporaneo;

    private ATextField indirizzoField;


    /**
     * Costruttore base senza parametri <br>
     * Non usato. Serve solo per 'coprire' un piccolo bug di Idea <br>
     * Se manca, manda in rosso i parametri del costruttore usato <br>
     */
    public ProvaDialog() {
    }// end of constructor


    /**
     * Costruttore base con parametri <br>
     * L'istanza DEVE essere creata con appContext.getBean(GiornoDialog.class, service, entityClazz); <br>
     *
     * @param service     business class e layer di collegamento per la Repository
     * @param binderClass di tipo AEntity usata dal Binder dei Fields
     */
    public ProvaDialog(IAService service, Class<? extends AEntity> binderClass) {
        super(service, binderClass);
    }// end of constructor


    /**
     * Le preferenze specifiche, eventualmente sovrascritte nella sottoclasse
     * Può essere sovrascritto, per aggiungere informazioni
     * Invocare PRIMA il metodo della superclasse
     */
    @Override
    protected void fixPreferenzeSpecifiche() {
        super.fixPreferenzeSpecifiche();
        super.usaFormDueColonne = false;
    }// end of method


    /**
     * Costruisce eventuali fields specifici (costruiti non come standard type)
     * Aggiunge i fields specifici al binder
     * Aggiunge i fields specifici alla fieldMap
     * Sovrascritto nella sottoclasse
     */
    @Override
    protected void addSpecificAlgosFields() {
        super.addSpecificAlgosFields();

        addressService = StaticContextAccessor.getBean(AddressService.class);

        addressDialog = StaticContextAccessor.getBean(AddressDialog.class);
        addressDialog.fixFunzioni(this::saveUpdate, this::deleteUpdate, this::annullaUpdate);
        addressDialog.fixConfermaAndNotRegistrazione();

        indirizzoField = (ATextField) getField(INDIRIZZO);
        if (indirizzoField != null) {
            indirizzoField.addFocusListener(e -> addressDialog.open(getIndirizzo(), EAOperation.edit, context));
        }// end of if cycle

        AbstractField propertyField = null;
        propertyField = new ATextField("Alfa");
        ((ATextField) propertyField).setWidth("2em");

        if (propertyField != null) {
            AbstractField comboField = fieldMap.get("ruoli");
            List lista = new ArrayList<>();
            lista.add("alfetta");
            lista.add("domani");
            lista.add("mattina");
            ((MultiselectComboBox) comboField).setItems(lista);
        }// end of if cycle

    }// end of method


    /**
     * Regola in lettura eventuali valori NON associati al binder
     * Dal DB alla UI
     * Sovrascritto
     */
    protected void readSpecificFields() {
        indirizzoTemporaneo = getIndirizzoCorrente();
        indirizzoField.setValue(indirizzoTemporaneo != null ? indirizzoTemporaneo.toString() : "");
    }// end of method


    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dallla  UI al DB
     * Sovrascritto
     */
    protected void writeSpecificFields() {
        Prova prova = super.getCurrentItem();
        prova.setIndirizzoStatico(indirizzoTemporaneo);
    }// end of method


    private void saveUpdate(Address entityBean, EAOperation operation) {
        indirizzoTemporaneo = entityBean;
        indirizzoField.setValue(entityBean.toString());
        focusOnPost(INDIRIZZO);
        Notification.show("La modifica di indirizzo è stata confermata ma devi registrare questa persona per renderla definitiva", FLASH, Notification.Position.BOTTOM_START);
    }// end of method


    private void deleteUpdate(Address entityBean) {
        indirizzoTemporaneo = null;
        indirizzoField.setValue("");
        focusOnPost(INDIRIZZO);
    }// end of method


    protected void annullaUpdate(Address entityBean) {
        cancelButton.focus();
    }// end of method


    /**
     * Eventuali aggiustamenti finali al layout
     * Aggiunge eventuali altri componenti direttamente al layout grafico (senza binder e senza fieldMap)
     * Sovrascritto nella sottoclasse
     */
    @Override
    protected void fixLayout() {
//        Object beta = StaticContextAccessor.getBean(ADialog.class);
        getFormLayout().add(new Label("Prova"));
        getFormLayout().add(new Button("Click", event -> paperino()));
        getFormLayout().add(new Button("ClickSecondo", event -> topolino()));

        Component comp = creaCombo();
        getFormLayout().add(comp);

        addComboColor();
    }// end of method


    public Component creaCombo() {

        MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();
        multiselectComboBox.setLabel("Select items");
        multiselectComboBox.setPlaceholder("Choose...");
        multiselectComboBox.setItems("Item 1", "Item 2", "Item 3", "Item 4");

        return multiselectComboBox;
    }// end of method


    /**
     * Costruisce un comboBox di colori, costruendo una classe ad hoc con testo e bottone-colore.
     */
    private void addComboColor() {
//        Component comp = new AColor("maroon");
//        ComboBox<AColor> combo = new ComboBox<>();

        Select<AColor> select = new Select<>();
        select.setLabel("Seleziona un colore");

        ArrayList<AColor> items = new ArrayList<>();
        for (EAColor color : EAColor.values()) {
            items.add(new AColor(color.getTag()));
        }// end of for cycle

        select.setItems(items);
        select.setRenderer(new ComponentRenderer<>(color -> {
            Div div = new Div();
            div.getStyle().set("text-align", "left");
            div.setText((color.getName()));

            FlexLayout wrapper = new FlexLayout();
            wrapper.setFlexGrow(1, div);
            wrapper.add(color.getColore(), new Label("&nbsp;&nbsp;&nbsp;"), div);
            return wrapper;
        }));

        select.setWidth("8em");
        getFormLayout().add(select);
    }// end of method


    private Address getIndirizzoCorrente() {
        Address indirizzo = null;
        Prova prova = getCurrentItem();

        if (prova != null) {
            indirizzo = prova.getIndirizzoStatico();
        }// end of if cycle

        return indirizzo;
    }// end of method


    private Address getIndirizzo() {
        Address indirizzo = getIndirizzoCorrente();

        if (indirizzo == null) {
            indirizzo = addressService.newEntity();
        }// end of if cycle

        return indirizzo;
    }// end of method


    /**
     * Recupera il field dal nome
     *
     * @param publicFieldName
     */
    @Override
    protected AbstractField getField(String publicFieldName) {
        return super.getField(publicFieldName);
    }// end of method


    public void pippo() {
        int a = 87;
    }// end of method


    public void pluto() {
        int a = 87;
    }// end of method


    public void paperino() {
        ADialog dialog = appContext.getBean(ADialog.class, "Alfetta");
        dialog.open("Pippoz", this::pippo, this::pluto);
    }// end of method


    public void topolino() {
        ADialog dialog = appContext.getBean(ADialog.class, "Burletta");
        VerticalLayout vert = new VerticalLayout(new Label("PrimaRiga"), new Button("Ok"), new Label("Terza riga"));
        dialog.open(vert, this::pippo, this::pluto);
    }// end of method


    public class AColor extends Div {

        private Button colore;

        private String name;


        /**
         * Costruttore completo con parametri.
         *
         * @param name descrizione del parametro
         */
        public AColor(String name) {
            this.name = name;
            this.colore = new Button();
            colore.getElement().getStyle().set("background-color", name);

            this.add(colore);
            this.add(name);
        }// fine del metodo costruttore completo


        public Button getColore() {
            return colore;
        }


        public String getName() {
            return name;
        }

    }// end of class

}// end of class