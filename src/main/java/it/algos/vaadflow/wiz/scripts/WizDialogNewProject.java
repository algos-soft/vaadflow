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
        layoutLegenda.add(new Label("Devi prima creare un project idea"));
        layoutLegenda.add(new Label("Di tipo 'MAVEN' senza selezionare archetype"));
        layoutLegenda.add(new Label("Rimane il POM vuoto, ma verrà sovrascritto"));
        layoutLegenda.add(new Label("Poi seleziona il progetto dalla lista sottostante"));
    }// end of method


    /**
     * Spazzola la directory 'ideaProjects' <br>
     * Recupera i possibili progetti 'vuoti' <br>
     */
    protected void spazzolaDirectory() {
        List<String> progetti = getNomiProgettiVuoti();

        fieldComboNomeProgetti = new ComboBox<>();
        fieldComboNomeProgetti.setWidth("22em");
        fieldComboNomeProgetti.setAllowCustomValue(false);
        fieldComboNomeProgetti.setLabel(LABEL_COMBO_UNO);

        fieldComboNomeProgetti.setItems(progetti);
        if (progetti.size() == 1) {
            fieldComboNomeProgetti.setValue(progetti.get(0));
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
        this.add(new VerticalLayout(fieldComboNomeProgetti, buttonForzaDirectory));
    }// end of method


    protected void forzaProgetti() {
        List<String> progetti = file.getSubDirectoriesName(pathProjectsDir);
        fieldComboNomeProgetti.setItems(progetti);
        fieldComboNomeProgetti.setLabel(LABEL_COMBO_DUE);
        if (progetti.size() == 1) {
            fieldComboNomeProgetti.setValue(progetti.get(0));
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
    protected List<String> getNomiProgettiVuoti() {
        List<String> nomiProgettiVuoti = new ArrayList<>();
        List<File> cartelleProgetti = null;
        List<File> subMain;
        List<File> subJava;
        String tagVuoto = DIR_MAIN;
        String tagPieno = tagVuoto + "/java";
        String tagCompleto = tagPieno + "/it";
        String pahtDirectoryChiave;

        if (text.isValid(pathProjectsDir)) {
            cartelleProgetti = file.getSubDirectories(pathProjectsDir);
        }// end of if cycle

        //--deve essere valido subMain e vuoto subJava
        if (array.isValid(cartelleProgetti)) {
            for (File cartellaProgetto : cartelleProgetti) {

                subMain = file.getSubDirectories(cartellaProgetto);

                //se manca la sottodirectory src/main non se ne parla
                if (array.isValid(subMain)) {

                    //se esiste NON deve esserci il percorso src/main/java/it
                    subJava = file.getSubDirectories(pathProjectsDir + "/" + cartellaProgetto + tagPieno);
                    pahtDirectoryChiave = pathProjectsDir + "/" + cartellaProgetto + tagCompleto;
                    if (array.isEmpty(subJava) && !file.isEsisteDirectory(pahtDirectoryChiave)) {
                        nomiProgettiVuoti.add(cartellaProgetto.getName());
                    }// end of if cycle
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        if (array.isEmpty(nomiProgettiVuoti)) {
//            nomiProgettiVuoti = progettiEsistenti;
        }// end of if cycle

        return nomiProgettiVuoti;
    }// end of method


    private void addListener() {
        fieldComboNomeProgetti.addValueChangeListener(event -> sincroProject(event.getValue()));
    }// end of method


    private void sincroProject(String valueFromProject) {
        confirmButton.setEnabled(text.isValid(valueFromProject));
    }// end of method

}// end of class
