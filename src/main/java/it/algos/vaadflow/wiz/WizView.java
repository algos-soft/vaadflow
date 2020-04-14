package it.algos.vaadflow.wiz;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import it.algos.vaadflow.wizard.enumeration.Chiave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.LinkedHashMap;

import static it.algos.vaadflow.application.FlowCost.TAG_WIZ_NEW_PROJECT;
import static it.algos.vaadflow.application.FlowCost.TAG_WIZ_VIEW;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 03:02
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = TAG_WIZ_VIEW)
public class WizView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private WizDialogNewProject dialogNewProject;

    @Autowired
    private WizElaboraNewProject elaboraNewProject;


    /**
     * Costruttore base senza parametri <br>
     */
    public WizView() {
    }// end of Vaadin/@Route constructor


    /**
     * Creazione iniziale (business logic) della view <br>
     * Chiamata DOPO costruttore, init(), postConstruct() e setParameter() <br>
     * <p>
     * Chiamato da com.vaadin.flow.router.Router tramite l'interfaccia BeforeEnterObserver <br>
     * Chiamato DOPO @PostConstruct e DOPO setParameter() <br>
     *
     * @param beforeEnterEvent con la location, ui, navigationTarget, source, ecc
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        inizia();
    }// end of method


    /**
     * Costruzione grafica <br>
     */
    public void inizia() {
        this.setMargin(true);
        this.setPadding(true);
        this.setSpacing(false);

        titolo();
        primoParagrafo();
        secondoParagrafo();
        terzoParagrafo();
        quartoParagrafo();
    }// end of method


    public void titolo() {
        H2 titolo = new H2("Gestione Wizard");
        titolo.getElement().getStyle().set("color", "green");
        this.add(titolo);
    }// end of method


    public void primoParagrafo() {
        H3 paragrafo = new H3("Nuovo progetto");
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);
        this.add(new Label("Vuoto e nella directory IdeaProjects"));

        Button bottone = new Button("New project");
        bottone.getElement().setAttribute("theme", "pimary");
        bottone.addClickListener(event -> dialogNewProject.open(this::elaboraNewProject));
        this.add(bottone);
        this.add(new H1());
    }// end of method


    private void elaboraNewProject(LinkedHashMap<Chiave, Object> mappaInput) {
        dialogNewProject.close();

        if (mappaInput != null) {
            elaboraNewProject.gotInput(mappaInput);
        }// end of if cycle
    }// end of method


    public void secondoParagrafo() {
        H3 paragrafo = new H3("Modifica progetto esistente");
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);
        this.add(new Label("Esistente in qualsiasi directory"));
        this.add(new H1());
    }// end of method


    public void terzoParagrafo() {
        H3 paragrafo = new H3("Nuovo package di VaadFlow");
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);
        this.add(new H1());
    }// end of method


    public void quartoParagrafo() {
        H3 paragrafo = new H3("Nuovo package di Test");
        paragrafo.getElement().getStyle().set("color", "blue");
        this.add(paragrafo);
    }// end of method


    public void apriNuovoProgetto() {
        getUI().ifPresent(ui -> ui.navigate(TAG_WIZ_NEW_PROJECT));
    }// end of method

}// end of class