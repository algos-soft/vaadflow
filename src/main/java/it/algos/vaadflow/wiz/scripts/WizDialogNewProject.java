package it.algos.vaadflow.wiz.scripts;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.SLASH;
import static it.algos.vaadflow.wiz.scripts.WizCost.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 03:36
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogNewProject extends WizDialog {

    private static String LABEL_COMBO_UNO = "Progetti vuoti esistenti (nella directory IdeaProjects)";

    private static String LABEL_COMBO_DUE = "Tutti i progetti esistenti (nella directory IdeaProjects)";


    /**
     * Apertura del dialogo <br>
     */
    public void open(WizRecipient wizRecipient) {
        super.wizRecipient = wizRecipient;
        super.isNuovoProgetto = true;
        super.titoloCorrente = TITOLO_NUOVO_PROGETTO;

        super.inizia();
        super.creaBottoni();
        this.spazzolaDirectory();
        super.creaCheckBoxList();
        this.add(layoutBottoni);
        super.open();
    }// end of method


    /**
     * Legenda iniziale <br>
     */
    protected void legenda() {
        super.legenda();

        layoutLegenda.add(new Label("Creazione di un nuovo project"));
        layoutLegenda.add(new Label("Devi prima creare un new project IntelliJIdea"));
        layoutLegenda.add(new Label("Di tipo 'MAVEN' senza selezionare archetype"));
        layoutLegenda.add(new Label("Rimane il POM vuoto, ma verrà sovrascritto"));
        layoutLegenda.add(new Label("Poi seleziona il progetto dalla lista sottostante"));
    }// end of method


    /**
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera i possibili progetti 'vuoti' <br>
     */
    protected void spazzolaDirectory() {
        List<File> progetti = getProgettiVuoti();

        fieldComboProgetti = new ComboBox<>();
        // Choose which property from Department is the presentation value
        fieldComboProgetti.setItemLabelGenerator(File::getName);
        fieldComboProgetti.setWidth("22em");
        fieldComboProgetti.setAllowCustomValue(false);
        fieldComboProgetti.setLabel(LABEL_COMBO_UNO);

        fieldComboProgetti.setItems(progetti);
        if (progetti.size() == 1) {
            fieldComboProgetti.setValue(progetti.get(0));
            confirmButton.setEnabled(true);
        }// end of if cycle

        buttonForzaDirectory = new Button("Forza directory");
        buttonForzaDirectory.setIcon(VaadinIcon.REFRESH.create());
        buttonForzaDirectory.getElement().setAttribute("theme", "error");
        buttonForzaDirectory.addClickListener(e -> forzaProgetti());
        buttonForzaDirectory.setWidth("12em");
        buttonForzaDirectory.setHeight(NORMAL_HEIGHT);
        buttonForzaDirectory.setVisible(true);
        buttonForzaDirectory.setEnabled(progetti.size() < 1);

        addListener();
        this.add(new VerticalLayout(fieldComboProgetti, buttonForzaDirectory));
    }// end of method


    protected void forzaProgetti() {
        List<File> progetti = file.getSubDirectories(pathProjectsDir);
        fieldComboProgetti.setItems(progetti);
        fieldComboProgetti.setLabel(LABEL_COMBO_DUE);
        if (progetti.size() == 1) {
            fieldComboProgetti.setValue(progetti.get(0));
            confirmButton.setVisible(true);
        }// end of if cycle
        buttonForzaDirectory.setEnabled(false);
    }// end of method


    /**
     * Mostra i progetti 'vuoti'
     * Per essere 'vuoti' non devono avere la directory:
     * src.main.java.it.algos.vaadflow
     * <p>
     * Per essere 'vuoti' deve esserci la directory: src/main/java vuota
     */
    protected List<File> getProgettiVuoti() {
        List<File> progettiVuoti = new ArrayList<>();
        List<File> cartelleProgetti = null;
        String tagPienoDeveEsserci = SLASH + DIR_MAIN;
        String tagVuotoNonDeveEsserci = tagPienoDeveEsserci + "/java";

        if (text.isValid(pathProjectsDir)) {
            cartelleProgetti = file.getSubDirectories(pathProjectsDir);
        }// end of if cycle

        //--deve essere valido subMain e vuoto subJava
        if (array.isValid(cartelleProgetti)) {
            for (File cartellaProgetto : cartelleProgetti) {

                //se manca la sottodirectory src/main non se ne parla
                if (file.isEsisteSubDirectory(cartellaProgetto, tagPienoDeveEsserci)) {

                    //se esiste NON deve esserci il percorso src/main/java/it
                    if (file.isVuotaSubDirectory(cartellaProgetto, tagVuotoNonDeveEsserci)) {
                        progettiVuoti.add(cartellaProgetto);
                    }// end of if cycle
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        return progettiVuoti;
    }// end of method


    private void addListener() {
        fieldComboProgetti.addValueChangeListener(event -> sincroProject(event.getValue()));
    }// end of method


    private void sincroProject(File valueFromProject) {
        confirmButton.setEnabled(valueFromProject != null);
    }// end of method

}// end of class
