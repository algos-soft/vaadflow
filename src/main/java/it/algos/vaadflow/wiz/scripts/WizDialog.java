package it.algos.vaadflow.wiz.scripts;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import it.algos.vaadflow.modules.log.LogService;
import it.algos.vaadflow.service.AArrayService;
import it.algos.vaadflow.service.AFileService;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.wiz.enumeration.Chiave;
import it.algos.vaadflow.wiz.enumeration.EAWiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.LinkedHashMap;

import static it.algos.vaadflow.application.FlowCost.SLASH;
import static it.algos.vaadflow.application.FlowCost.VUOTA;
import static it.algos.vaadflow.wiz.scripts.WizCost.*;

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

    @Autowired
    protected LogService logger;

    protected WizRecipient wizRecipient;

    protected LinkedHashMap<Chiave, Checkbox> mappaCheckbox;

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

    protected ComboBox<File> fieldComboProgetti;


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
        if (FLAG_DEBUG_WIZ) {
            WizCost.printInfo(log);
        }// end of if cycle

        this.pathUserDir = System.getProperty("user.dir") + SLASH;
        this.pathVaadFlow = PATH_VAAD_FLOW_DIR_STANDARD;
        if (!pathVaadFlow.equals(pathUserDir)) {
            logger.error("Attenzione. La directory di VaadFlow è cambiata", WizDialog.class, "regolazioniIniziali");
        }// end of if/else cycle

        //valido SOLO per new project
        if (isNuovoProgetto) {
            this.pathProjectsDir = file.levaDirectoryFinale(pathVaadFlow);
            this.pathProjectsDir = file.levaDirectoryFinale(pathProjectsDir);
            if (!pathProjectsDir.equals(PATH_PROJECTS_DIR_STANDARD)) {
                logger.error("Attenzione. La directory dei Projects è cambiata", WizDialog.class, "regolazioniIniziali");
            }// end of if cycle
        } else {
            this.pathProjectsDir = VUOTA;
        }// end of if/else cycle

        this.pathSources = pathVaadFlow + DIR_SOURCES;

        if (FLAG_DEBUG_WIZ) {
            System.out.println("Ingresso del dialogo");
            log.info("Directory di esecuzione: pathUserDir=" + pathUserDir);
            log.info("Directory VaadFlow: pathVaadFlow=" + pathVaadFlow);
            if (isNuovoProgetto) {
                log.info("Directory dei nuovi progetti: pathProjectsDir=" + pathProjectsDir);
            }// end of if cycle
            log.info("Sorgenti VaadFlow: pathSources=" + pathSources);
            System.out.println("");
        }// end of if cycle
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

        mappaCheckbox = new LinkedHashMap<>();
        Checkbox unCheckbox;
        for (EAWiz flag : EAWiz.values()) {
            if (flag.isNewProject()) {
                unCheckbox = new Checkbox(flag.getLabelBox(), flag.isStatus());
                mappaCheckbox.put(flag.getChiave(), unCheckbox);
                layoutCheckBox.add(unCheckbox);
            }// end of if cycle
        }// end of for cycle

        this.add(layoutCheckBox);
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


    /**
     * Chiamato all'uscita del dialogo <br>
     * Regola in una mappa tutti i valori che saranno usati da WizElaboraNewProject e WizElaboraUpdateProject <br>
     */
    protected void setMappa() {
        if (mappaInput != null) {
            mappaInput.put(Chiave.pathUserDir, pathUserDir);
            mappaInput.put(Chiave.pathVaadFlow, pathVaadFlow);
            mappaInput.put(Chiave.pathProjectsDir, pathProjectsDir);
            mappaInput.put(Chiave.pathSources, pathSources);
            mappaInput.put(Chiave.newProjectName, fieldComboProgetti.getValue().getName());
            mappaInput.put(Chiave.pathTargetProget, fieldComboProgetti.getValue().getAbsolutePath());

            for (Chiave key : mappaCheckbox.keySet()) {
                mappaInput.put(key, mappaCheckbox.get(key).getValue());
            }// end of for cycle

            //--visualizzazione di controllo
            if (FLAG_DEBUG_WIZ) {
                System.out.println("Uscita dal dialogo");
                log.info("Progetto corrente: pathUserDir=" + pathUserDir);
                log.info("Directory VaadFlow: pathVaadFlow=" + pathVaadFlow);
                log.info("Directory dei nuovi progetti: pathProjectsDir=" + pathProjectsDir);
                log.info("Sorgenti VaadFlow: pathSources=" + pathSources);
                if (isNuovoProgetto) {
                    log.info("Nome nuovo progetto: newProjectName=" + fieldComboProgetti.getValue().getName());
                    log.info("Directory nuovo progetto: pathTargetProget=" + fieldComboProgetti.getValue().getAbsolutePath());
                }// end of if cycle
                System.out.println("");
            }// end of if cycle
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
