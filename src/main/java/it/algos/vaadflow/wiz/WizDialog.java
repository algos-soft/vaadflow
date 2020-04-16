package it.algos.vaadflow.wiz;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import it.algos.vaadflow.service.AArrayService;
import it.algos.vaadflow.service.AFileService;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.wiz.enumeration.Chiave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;

import static it.algos.vaadflow.application.FlowCost.SLASH;
import static it.algos.vaadflow.application.FlowCost.VUOTA;
import static it.algos.vaadflow.wiz.WizCost.*;
import static it.algos.vaadflow.wiz.enumeration.Chiave.newProjectName;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:17
 */
@Slf4j
public class WizDialog extends Dialog {

    @Autowired
    protected ATextService text;

    @Autowired
    protected AArrayService array;

    @Autowired
    protected AFileService file;

    protected WizRecipient wizRecipient;

    protected Checkbox fieldCheckBoxSecurity;

    protected Checkbox fieldCheckBoxDocumentation;

    protected Checkbox fieldCheckBoxLinks;

    protected Checkbox fieldCheckBoxSnippets;

    protected Checkbox fieldCheckBoxBaseFlow;

    protected Checkbox fieldCheckBoxNewProject;

    protected Checkbox fieldCheckBoxResources;

    protected Checkbox fieldCheckBoxProperties;

    protected Checkbox fieldCheckBoxRead;

    protected Checkbox fieldCheckBoxGit;

    protected Checkbox fieldCheckBoxPom;

    protected Checkbox fieldCheckBoxSovrascriveFile;

    protected Checkbox fieldCheckBoxSovrascriveDirectory;

    protected boolean isNuovoProgetto;

    protected Button confirmButton;

    protected Button cancelButton;

    protected Button buttonForzaDirectory;

    protected LinkedHashMap<Chiave, Object> mappaInput = new LinkedHashMap<>();

    protected VerticalLayout layoutLegenda;

    protected VerticalLayout layoutTitolo;

    protected VerticalLayout layoutCheckBox;

    protected VerticalLayout layoutBottoni;

    protected H3 titoloCorrente;

    protected ComboBox<String> fieldComboNomeProgetti;


    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory dove gira questo programma; recuperata dal System
    protected String pathUserDir;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory che contiene il programma VaadFlow
    //--PATH_VAAD_FLOW_DIR_STANDARD oppure userDir meno NAME_PROJECT_BASE
    protected String pathVaadFlow;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory che contiene i nuovi programmi appena creati da Idea
    protected String pathProjectsDir;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory dei sorgenti testuali di VaadFlow (da elaborare)
    //--pathVaadFlowDir più DIR_SOURCES
    protected String pathSources;


    /**
     * Regolazioni grafiche
     */
    protected void inizia() {
        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
        this.removeAll();
        this.regolazioniIniziali();
        this.legenda();
    }// end of method


    /**
     * Regolazioni iniziali indipendenti dal dialogo di input
     */
    protected void regolazioniIniziali() {
        this.pathUserDir = System.getProperty("user.dir");
        if (pathUserDir.equals(PATH_VAAD_FLOW_DIR_STANDARD)) {
            this.pathVaadFlow = pathUserDir;
        } else {
            this.pathVaadFlow = text.levaCodaDa(pathUserDir, SLASH);
            log.warn("Attenzione. La directory di VaadFlow è cambiata");
        }// end of if/else cycle

        //valido SOLO per new project
        if (isNuovoProgetto) {
            this.pathProjectsDir = text.levaCodaDa(pathVaadFlow, SLASH);
            this.pathProjectsDir = text.levaCodaDa(pathProjectsDir, SLASH);
            if (!pathProjectsDir.equals(PATH_PROJECTS_DIR_STANDARD)) {
                log.warn("Attenzione. La directory dei Projects è cambiata");
            }// end of if cycle
        } else {
            this.pathProjectsDir = VUOTA;
        }// end of if/else cycle

        this.pathSources = pathVaadFlow + DIR_SOURCES;
    }// end of method


    /**
     * Legenda iniziale <br>
     */
    protected void legenda() {
        layoutTitolo = new VerticalLayout();
        layoutTitolo.setMargin(false);
        layoutTitolo.setSpacing(false);
        layoutTitolo.setPadding(true);
        titoloCorrente.getElement().getStyle().set("color", "blue");
        layoutTitolo.add(titoloCorrente);

        layoutLegenda = new VerticalLayout();
        layoutLegenda.setMargin(false);
        layoutLegenda.setSpacing(false);
        layoutLegenda.setPadding(true);
        layoutLegenda.getElement().getStyle().set("color", "green");
        this.setWidth("25em");

        this.add(layoutTitolo);
        this.add(layoutLegenda);
    }// end of method


    protected void creaCheckBoxList() {
        layoutCheckBox = new VerticalLayout();
        layoutCheckBox.setMargin(false);
        layoutCheckBox.setSpacing(false);
        layoutCheckBox.setPadding(true);

        if (isNuovoProgetto) {
            layoutCheckBox.add(creaSecurity());
        }// end of if cycle
        layoutCheckBox.add(creaDocumentation());
        layoutCheckBox.add(creaLinks());
        layoutCheckBox.add(creaSnippets());
        layoutCheckBox.add(creaFlow());
        if (isNuovoProgetto) {
            layoutCheckBox.add(creaDirectoryNewProject());
        }// end of if cycle
        layoutCheckBox.add(creaResources());
        layoutCheckBox.add(creaProperties());
        layoutCheckBox.add(creaRead());
        layoutCheckBox.add(creaGit());
        layoutCheckBox.add(creaPom());
        if (!isNuovoProgetto) {
            layoutCheckBox.add(creaSovrascriveFile());
            layoutCheckBox.add(creaSovrascriveDirectory());
        }// end of if cycle

        this.add(layoutCheckBox);
    }// end of method


    private Component creaSecurity() {
        fieldCheckBoxSecurity = new Checkbox();
        fieldCheckBoxSecurity.setLabel("Utilizza Spring Security");
        fieldCheckBoxSecurity.setValue(false);
        return fieldCheckBoxSecurity;
    }// end of method


    private Component creaDocumentation() {
        fieldCheckBoxDocumentation = new Checkbox();
        fieldCheckBoxDocumentation.setLabel("Directory documentazione");
        fieldCheckBoxDocumentation.setValue(true);
        return fieldCheckBoxDocumentation;
    }// end of method


    private Component creaLinks() {
        fieldCheckBoxLinks = new Checkbox();
        fieldCheckBoxLinks.setLabel("Directory links a web");
        fieldCheckBoxLinks.setValue(true);
        return fieldCheckBoxLinks;
    }// end of method


    private Component creaSnippets() {
        fieldCheckBoxSnippets = new Checkbox();
        fieldCheckBoxSnippets.setLabel("Directory snippets di aiuto");
        fieldCheckBoxSnippets.setValue(true);
        return fieldCheckBoxSnippets;
    }// end of method


    private Component creaFlow() {
        fieldCheckBoxBaseFlow = new Checkbox();
        fieldCheckBoxBaseFlow.setLabel("Copia la cartella VaadFlow");
        fieldCheckBoxBaseFlow.setValue(true);
        return fieldCheckBoxBaseFlow;
    }// end of method


    private Component creaDirectoryNewProject() {
        fieldCheckBoxNewProject = new Checkbox();
        fieldCheckBoxNewProject.setLabel("Crea la cartella del nuovo progetto");
        fieldCheckBoxNewProject.setValue(true);
        return fieldCheckBoxNewProject;
    }// end of method


    private Component creaResources() {
        fieldCheckBoxResources = new Checkbox();
        fieldCheckBoxResources.setLabel("Directory resources - ATTENZIONE");
        fieldCheckBoxResources.setValue(false);
        return fieldCheckBoxResources;
    }// end of method


    private Component creaProperties() {
        fieldCheckBoxProperties = new Checkbox();
        fieldCheckBoxProperties.setLabel("File application.properties");
        fieldCheckBoxProperties.setValue(true);
        return fieldCheckBoxProperties;
    }// end of method


    private Component creaRead() {
        fieldCheckBoxRead = new Checkbox();
        fieldCheckBoxRead.setLabel("File READ con note di testo");
        fieldCheckBoxRead.setValue(true);
        return fieldCheckBoxRead;
    }// end of method


    private Component creaGit() {
        fieldCheckBoxGit = new Checkbox();
        fieldCheckBoxGit.setLabel("File GIT di esclusione");
        fieldCheckBoxGit.setValue(true);
        return fieldCheckBoxGit;
    }// end of method


    private Component creaPom() {
        fieldCheckBoxPom = new Checkbox();
        fieldCheckBoxPom.setLabel("File Maven di POM.xml - ATTENZIONE");
        fieldCheckBoxPom.setValue(isNuovoProgetto);
        return fieldCheckBoxPom;
    }// end of method


    private Component creaSovrascriveFile() {
        fieldCheckBoxSovrascriveFile = new Checkbox();
        fieldCheckBoxSovrascriveFile.setLabel("Sovrascrive il singolo FILE");
        fieldCheckBoxSovrascriveFile.setValue(false);
        return fieldCheckBoxSovrascriveFile;
    }// end of method


    private Component creaSovrascriveDirectory() {
        fieldCheckBoxSovrascriveDirectory = new Checkbox();
        fieldCheckBoxSovrascriveDirectory.setLabel("Sovrascrive la DIRECTORY");
        fieldCheckBoxSovrascriveDirectory.setValue(false);
        return fieldCheckBoxSovrascriveDirectory;
    }// end of method


    protected void creaBottoni() {
        layoutBottoni = new VerticalLayout();
        HorizontalLayout layoutFooter = new HorizontalLayout();
        layoutFooter.setSpacing(true);
        layoutFooter.setMargin(true);

        cancelButton = new Button("Annulla", event -> {
            esceDalDialogo(false);
        });//end of lambda expressions
        cancelButton.setIcon(VaadinIcon.ARROW_LEFT.create());
        cancelButton.getElement().setAttribute("theme", "primary");
        cancelButton.addClickShortcut(Key.ARROW_LEFT);
        cancelButton.setWidth(NORMAL_WIDTH);
        cancelButton.setHeight(NORMAL_HEIGHT);
        cancelButton.setVisible(true);

        confirmButton = new Button("Conferma", event -> {
            esceDalDialogo(true);
        });//end of lambda expressions
        confirmButton.setIcon(VaadinIcon.EDIT.create());
        confirmButton.getElement().setAttribute("theme", "error");
        confirmButton.setWidth(NORMAL_WIDTH);
        confirmButton.setHeight(NORMAL_HEIGHT);
        confirmButton.setVisible(true);
        confirmButton.setEnabled(false);

        layoutFooter.add(cancelButton, confirmButton);
        layoutBottoni.add(layoutFooter);
    }// end of method


    protected void setMappa() {
        if (mappaInput != null) {
            mappaInput.put(Chiave.pathUserDir, pathUserDir);
            mappaInput.put(Chiave.pathVaadFlow, pathVaadFlow);
            mappaInput.put(Chiave.pathProjectsDir, pathProjectsDir);
            mappaInput.put(Chiave.pathSources, pathSources);
            if (isNuovoProgetto) {
                mappaInput.put(newProjectName, fieldComboNomeProgetti.getValue());
            } else {
                mappaInput.put(Chiave.targetProjectName, fieldComboNomeProgetti.getValue());
            }// end of if/else cycle
            mappaInput.put(Chiave.flagSecurity, fieldCheckBoxSecurity.getValue());
            mappaInput.put(Chiave.flagDocumentation, fieldCheckBoxDocumentation.getValue());
            mappaInput.put(Chiave.flagLinks, fieldCheckBoxLinks.getValue());
            mappaInput.put(Chiave.flagSnippets, fieldCheckBoxSnippets.getValue());
            mappaInput.put(Chiave.flagDirectoryFlow, fieldCheckBoxBaseFlow.getValue());
            if (!isNuovoProgetto) {
                mappaInput.put(Chiave.flagDirectoryNewProject, fieldCheckBoxNewProject.getValue());
            }// end of if cycle
            mappaInput.put(Chiave.flagResources, fieldCheckBoxResources.getValue());
            mappaInput.put(Chiave.flagProperties, fieldCheckBoxProperties.getValue());
            mappaInput.put(Chiave.flagRead, fieldCheckBoxRead.getValue());
            mappaInput.put(Chiave.flagGit, fieldCheckBoxGit.getValue());
            mappaInput.put(Chiave.flagPom, fieldCheckBoxPom.getValue());

            if (!isNuovoProgetto) {
                mappaInput.put(Chiave.flagSovrascriveFile, fieldCheckBoxSovrascriveFile.getValue());
                mappaInput.put(Chiave.flagSovrascriveDirectory, fieldCheckBoxSovrascriveDirectory.getValue());
            }// end of if cycle

            //--visualizzazione di controllo
            log.info("Progetto corrente: pathUserDir=" + pathUserDir);
            log.info("Directory VaadFlow: pathVaadFlow=" + pathVaadFlow);
            if (isNuovoProgetto) {
                log.info("Directory dei nuovi progetti: pathProjectsDir=" + pathProjectsDir);
            }// end of if cycle
            log.info("Sorgenti VaadFlow: pathSources=" + pathSources);
            if (isNuovoProgetto) {
                log.info("Nome nuovo progetto: fieldComboNomeProgetti=" + fieldComboNomeProgetti.getValue());
            }// end of if cycle
            log.info("");
        }// end of if cycle
    }// end of method


    /**
     * Esce dal dialogo con due possibilità (a seconda del flag)
     * 1) annulla
     * 2) esegue
     */
    private void esceDalDialogo(boolean esegue) {
        if (esegue) {
            setMappa();
            wizRecipient.gotInput(mappaInput);
        } else {
            wizRecipient.gotInput(null);
        }// end of if/else cycle
        this.close();
    }// end of method

}// end of class
