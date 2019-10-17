package it.algos.vaadtest.modules.beta;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadflow.ui.dialog.AViewDialog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadtest.application.TestCost.TAG_BET;

/**
 * Project vaadtest <br>
 * Created by Algos
 * User: Gac
 * Fix date: 14-ott-2019 18.56.46 <br>
 * <p>
 * Estende la classe astratta AViewDialog per visualizzare i fields <br>
 * Necessario per la tipizzazione del binder <br>
 * Costruita (nella List) con appContext.getBean(BetaDialog.class, service, entityClazz);
 * <p>
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * - la documentazione precedente a questo tag viene SEMPRE riscritta <br>
 * - se occorre preservare delle @Annotation con valori specifici, spostarle DOPO @AIScript <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_BET)
@Slf4j
@AIScript(sovrascrivibile = true)
public class BetaDialog extends AViewDialog<Beta> {


    /**
     * Costruttore senza parametri <br>
     * Non usato. Serve solo per 'coprire' un piccolo bug di Idea <br>
     * Se manca, manda in rosso i parametri del costruttore usato <br>
     */
    public BetaDialog() {
    }// end of constructor


    /**
     * Costruttore con parametri <br>
     * Not annotated with @Autowired annotation, per creare l'istanza SOLO come SCOPE_PROTOTYPE <br>
     * L'istanza DEVE essere creata con appContext.getBean(BetaDialog.class, service, entityClazz); <br>
     *
     * @param service     business class e layer di collegamento per la Repository
     * @param binderClass di tipo AEntity usata dal Binder dei Fields
     */
    public BetaDialog(IAService service, Class<? extends AEntity> binderClass) {
        super(service, binderClass);
    }// end of constructor


    /**
     * Eventuali aggiustamenti finali al layout
     * Aggiunge eventuali altri componenti direttamente al layout grafico (senza binder e senza fieldMap)
     * Sovrascritto nella sottoclasse
     */
    @Override
    protected void fixLayout() {
        super.fixLayout();

//        Component comp = creaSelect();
//        getFormLayout().add(comp);
    }// end of method


    public Component creaSelect() {

//        ComboBox<EAIconeBeta> comboBox = new ComboBox();
//        comboBox.setLabel("Icone");
//        comboBox.setItems(EAIconeBeta.getIcons());
//        comboBox.setWidth("4em");


        Select<EAIconeBeta> select = new Select<>();
        select.setLabel("How are you feeling today?");

        EAIconeBeta[] items=  EAIconeBeta.class.getEnumConstants();
//        select.setItems(EAIconeBeta.getIcons());
        select.setItems(items);

        select.setRenderer(new ComponentRenderer<>(enumeration -> {
            Div text = new Div();
            text.setText(enumeration.name().toLowerCase());

            FlexLayout wrapper = new FlexLayout();
            text.getStyle().set("margin-left", "0.5em");
            wrapper.add(((EAIconeBeta)enumeration).getIcon().create(), text);
            return wrapper;
        }));

        return select;
    }// end of method

//    /**
//     * Costruisce un comboBox di colori, costruendo una classe ad hoc con testo e bottone-colore.
//     */
//    private void addComboColor() {
////        Component comp = new AColor("maroon");
////        ComboBox<AColor> combo = new ComboBox<>();
//
//        Select<ProvaDialog.AColor> select = new Select<>();
//        select.setLabel("Seleziona un colore");
//
//        ArrayList<ProvaDialog.AColor> items = new ArrayList<>();
//        for (EAColor color : EAColor.values()) {
//            items.add();
//        }// end of for cycle
//
//        select.setItems(items);
//        select.setRenderer(new ComponentRenderer<>(color -> {
//            Div div = new Div();
//            div.getStyle().set("text-align", "left");
//            div.setText((color.getName()));
//
//            FlexLayout wrapper = new FlexLayout();
//            wrapper.setFlexGrow(1, div);
//            wrapper.add(color.getColore(), new Label("&nbsp;&nbsp;&nbsp;"), div);
//            return wrapper;
//        }));
//
//        select.setWidth("8em");
//        getFormLayout().add(select);
//    }// end of method

}// end of class