package it.algos.vaadflow.wiz;

import it.algos.vaadflow.wizard.enumeration.Chiave;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;

import static it.algos.vaadflow.application.FlowCost.VUOTA;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:29
 */
@Slf4j
public abstract class WizElabora implements WizRecipient {


    //--flag regolato nel dialogo di input
    public boolean flagSecurity;

    //--flag regolato nel dialogo di input
    public boolean flagDocumentation;

    //--flag regolato nel dialogo di input
    public boolean flagLinks;

    //--flag regolato nel dialogo di input
    public boolean flagSnippets;

    //--flag regolato nel dialogo di input
    public boolean flagDirectoryFlow;

    //--flag regolato nel dialogo di input
    public boolean flagDirectoryNewProject;

    //--flag regolato nel dialogo di input
    public boolean flagResources;

    //--flag regolato nel dialogo di input
    public boolean flagProperties;

    //--flag regolato nel dialogo di input
    public boolean flagRead;

    //--flag regolato nel dialogo di input
    public boolean flagGit;

    //--flag regolato nel dialogo di input
    public boolean flagPom;

    //--flag regolato nel dialogo di input
    public boolean flagSovrascriveFile;

    //--flag regolato nel dialogo di input
    public boolean flagSovrascriveDirectory;

    //--wrapper di trasmissione dati tra i dialoghi e e questa classe di elaborazione
    protected LinkedHashMap<Chiave, Object> mappaInput;

    //--flag di controllo regolato nella sottoclasse concreta
    protected boolean isNuovoProgetto;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory dove gira questo programma; recuperata dal System
    protected String pathUserDir;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory che contiene il programma VaadFlow
    //--userDir meno NAME_PROJECT_BASE
    protected String pathVaadFlowDir;

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

    //--regolata in base ai risultati del dialogo
    //--ha senso solo se isNuovoProgetto=true
    protected String newProjectName;

    //--regolata in base ai risultati del dialogo
    //--ha senso solo se isNuovoProgetto=false
    protected String targetProjectName;


    /**
     * Evento lanciato alla chiusura del dialogo
     */
    @Override
    public void gotInput(LinkedHashMap<Chiave, Object> mappaInput) {
        this.mappaInput = mappaInput;
        this.regolazioniIndipendentiDalDialogo();
        this.regolazioniMappaInputDialogo();
    }// end of method


    /**
     * Regolazioni iniziali indipendenti dal dialogo di input
     */
    protected void regolazioniIndipendentiDalDialogo() {
        this.pathUserDir = (String) mappaInput.get(Chiave.pathUserDir);
        this.pathVaadFlowDir = (String) mappaInput.get(Chiave.pathVaadFlowDir);
        if (isNuovoProgetto) {
            this.pathProjectsDir = (String) mappaInput.get(Chiave.pathProjectsDir);
        } else {
            this.pathProjectsDir = VUOTA;
        }// end of if/else cycle
        this.pathSources = (String) mappaInput.get(Chiave.pathSources);
        if (isNuovoProgetto) {
            this.newProjectName = (String) mappaInput.get(Chiave.newProjectName);
        } else {
            this.targetProjectName = (String) mappaInput.get(Chiave.targetProjectName);
        }// end of if/else cycle

        log.info("Progetto corrente: " + pathUserDir);
        log.info("Directory VaadFlow: " + pathVaadFlowDir);
        if (isNuovoProgetto) {
            log.info("Directory dei nuovi progetti: " + pathProjectsDir);
        }// end of if cycle
        log.info("Sorgenti VaadFlow: " + pathSources);
        if (isNuovoProgetto) {
            log.info("Nome nuovo progetto: " + newProjectName);
        }// end of if cycle
    }// end of method


    /**
     * Regolazioni della mappa proveniente dal dialogo di input
     */
    protected void regolazioniMappaInputDialogo() {
        if (mappaInput.containsKey(Chiave.flagSecurity)) {
            this.flagSecurity = (boolean) mappaInput.get(Chiave.flagSecurity);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagDocumentation)) {
            this.flagDocumentation = (boolean) mappaInput.get(Chiave.flagDocumentation);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagLinks)) {
            this.flagLinks = (boolean) mappaInput.get(Chiave.flagLinks);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagSnippets)) {
            this.flagSnippets = (boolean) mappaInput.get(Chiave.flagSnippets);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagDirectoryFlow)) {
            this.flagDirectoryFlow = (boolean) mappaInput.get(Chiave.flagDirectoryFlow);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagDirectoryNewProject)) {
            this.flagDirectoryNewProject = (boolean) mappaInput.get(Chiave.flagDirectoryNewProject);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagResources)) {
            this.flagResources = (boolean) mappaInput.get(Chiave.flagResources);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagProperties)) {
            this.flagProperties = (boolean) mappaInput.get(Chiave.flagProperties);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagRead)) {
            this.flagRead = (boolean) mappaInput.get(Chiave.flagRead);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagGit)) {
            this.flagGit = (boolean) mappaInput.get(Chiave.flagGit);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagPom)) {
            this.flagPom = (boolean) mappaInput.get(Chiave.flagPom);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagSovrascriveFile)) {
            this.flagSovrascriveFile = (boolean) mappaInput.get(Chiave.flagSovrascriveFile);
        }// end of if cycle
        if (mappaInput.containsKey(Chiave.flagSovrascriveDirectory)) {
            this.flagSovrascriveDirectory = (boolean) mappaInput.get(Chiave.flagSovrascriveDirectory);
        }// end of if cycle
    }// end of method

}// end of class
