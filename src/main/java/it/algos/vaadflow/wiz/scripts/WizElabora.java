package it.algos.vaadflow.wiz.scripts;

import it.algos.vaadflow.service.AFileService;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.wiz.enumeration.Chiave;
import it.algos.vaadflow.wiz.enumeration.Task;
import it.algos.vaadflow.wiz.enumeration.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.SLASH;
import static it.algos.vaadflow.application.FlowCost.VUOTA;
import static it.algos.vaadflow.wiz.scripts.WizCost.*;

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

    /**
     * Service recuperato come istanza dalla classe singleton
     */
    @Autowired
    protected AFileService file;

    /**
     * Service recuperato come istanza dalla classe singleton
     */
    @Autowired
    protected ATextService text;

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
    //--normalmente uguale a pathUserDir
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
    //--pathVaadFlow più DIR_MAIN
    protected String pathVaadFlowMain;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory dei sorgenti testuali di VaadFlow (da elaborare)
    //--pathVaadFlowJava più DIR_SOURCES
    protected String pathSources;

    //--regolata in base ai risultati del dialogo
    //--ha senso solo se isNuovoProgetto=true
    protected String newProjectName;

    //--regolata in base ai risultati del dialogo
    //--ha senso solo se isNuovoProgetto=false
    protected String targetProjectName;

    //--regolata in base ai risultati del dialogo
    //--path completo del progetto da creare/modificare
    protected String pathProject;

    //--regolata in base ai risultati del dialogo
    //--pathProject più DIR_MAIN
    protected String pathProjectMain;


    /**
     * Evento lanciato alla chiusura del dialogo
     */
    @Override
    public void gotInput(LinkedHashMap<Chiave, Object> mappaInput) {
        this.mappaInput = mappaInput;
        this.regolazioniIniziali();
        this.regolazioniMappaInputDialogo();
    }// end of method


    /**
     * Regolazioni iniziali indipendenti (inj parte) dal dialogo di input
     */
    protected void regolazioniIniziali() {
        this.pathUserDir = (String) mappaInput.get(Chiave.pathUserDir);
        this.pathVaadFlow = (String) mappaInput.get(Chiave.pathVaadFlow);
        if (isNuovoProgetto) {
            this.pathProjectsDir = (String) mappaInput.get(Chiave.pathProjectsDir);
        } else {
            this.pathProjectsDir = VUOTA;
        }// end of if/else cycle
        this.pathVaadFlowMain = pathVaadFlow + DIR_MAIN;
        this.pathSources = (String) mappaInput.get(Chiave.pathSources);
        if (isNuovoProgetto) {
            this.newProjectName = (String) mappaInput.get(Chiave.newProjectName);
        } else {
            this.targetProjectName = (String) mappaInput.get(Chiave.targetProjectName);
        }// end of if/else cycle
        if (isNuovoProgetto) {
            this.pathProject = pathProjectsDir + SLASH + newProjectName;
        } else {
            this.pathProject = VUOTA;
        }// end of if/else cycle
        pathProjectMain = pathProject + DIR_MAIN;

        //--visualizzazione di controllo
        log.info("Progetto corrente: pathUserDir=" + pathUserDir);
        log.info("Directory VaadFlow: pathVaadFlowDir=" + pathVaadFlow);
        if (isNuovoProgetto) {
            log.info("Directory dei nuovi progetti: pathProjectsDir=" + pathProjectsDir);
        }// end of if cycle
        log.info("Sorgenti VaadFlow: pathSources=" + pathSources);
        if (isNuovoProgetto) {
            log.info("Nome nuovo progetto: newProjectName=" + newProjectName);
            log.info("Directory nuovo progetto: pathProject=" + pathProject);
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


    /**
     * Cartella di documentazione (in formati vari)
     */
    protected void regolaDocumentation() {
        fixCartellaExtra(flagDocumentation, DIR_DOC);
    }// end of method


    /**
     * Cartella di LINKS utili in text
     */
    protected void regolaLinks() {
        fixCartellaExtra(flagLinks, DIR_LINKS);
    }// end of method


    /**
     * Cartella di snippets utili in text
     */
    protected void regolaSnippets() {
        fixCartellaExtra(flagSnippets, DIR_SNIPPETS);
    }// end of method


    /**
     * File README di text
     */
    protected void regolaRead() {
        String pathName = SLASH + FILE_READ + SOURCE_SUFFIX;
        String srcPath = pathVaadFlow + pathName;
        String destPath = pathProject + pathName;

        if (flagRead) {
            if (flagSovrascriveFile || !file.isEsisteFile(destPath)) {
                file.copyFile(srcPath, destPath);
            }// end of if cycle
        }// end of if cycle
    }// end of method


    /**
     * File banner di text
     */
    protected void regolaBanner() {
        String pathName = DIR_RESOURCES + SLASH + FILE_BANNER + SOURCE_SUFFIX;
        String srcPath = pathVaadFlow + pathName;
        String destPath = pathProject + pathName;

        if (flagRead) {
            if (flagSovrascriveFile || !file.isEsisteFile(destPath)) {
                file.copyFile(srcPath, destPath);
            }// end of if cycle
        }// end of if cycle
    }// end of method


    /**
     * File di esclusioni GIT di text
     */
    protected void regolaGit() {
        String pathName = SLASH + FILE_GIT;
        String srcPath = pathVaadFlow + pathName;
        String destPath = pathProject + pathName;

        if (flagGit) {
            if (flagSovrascriveFile || !file.isEsisteFile(destPath)) {
                file.copyFile(srcPath, destPath);
            }// end of if cycle
        }// end of if cycle
    }// end of method


    /**
     * File application.properties
     * Controlla se esiste la directory e nel caso la crea
     * Letto dai sorgenti
     * Sovrascrive dopo aver controllato se non c'è lo stop nel testo
     */
    protected void regolaProperties() {
        String sourceText = leggeFile(FILE_PROPERTIES);
        String destPath = pathProject + DIR_RESOURCES + SLASH + FILE_PROPERTIES_DEST;
        sourceText = Token.replace(Token.moduleNameMinuscolo, sourceText, newProjectName);

        if (flagProperties) {
            file.scriveNewFile(destPath, sourceText);
            checkAndWriteFile(destPath, sourceText);
        }// end of if cycle
    }// end of method


    /**
     * Cartella di resources META-INF
     */
    protected void copiaMetaInf() {
        String pathName = SLASH + "resources";
        String srcPath = pathVaadFlowMain + pathName;
        String destPath = pathProjectMain + pathName;

        if (flagResources) {
            file.copyDirectoryAddingOnly(srcPath, destPath);
        }// end of if cycle
    }// end of method


    /**
     * File MAVEN di script
     */
    protected void regolaPom() {
//        String sourceText = leggeFile(POM);
//        String destPath = ideaProjectRootPath + "/" + newProjectName + "/" + POM + ".xml";
//        sourceText = Token.replace(Token.moduleNameMinuscolo, sourceText, newProjectName);
//
//        if (flagPom) {
//            checkAndWriteFile(destPath, sourceText);
//        }// end of if cycle
    }// end of method


    /**
     * Sovrascrive o aggiunge a seconda del flag 'flagSovrascriveDirectory'
     */
    protected void fixCartellaExtra(boolean esegue, String dirNameGrezzo) {
        boolean dirCancellata = false;
        String dirName = dirNameGrezzo.startsWith("/") ? dirNameGrezzo.substring(1) : dirNameGrezzo;
        String srcPath = pathVaadFlow + SLASH + dirName;
        String destPath = pathProject + SLASH + dirName;

        if (!esegue) {
            return;
        }// end of if cycle

        if (flagSovrascriveDirectory) {
            dirCancellata = file.deleteDirectory(destPath);
            if (dirCancellata || !file.isEsisteDirectory(destPath)) {
                file.copyDirectoryAddingOnly(srcPath, destPath);
            }// end of if cycle
        } else {
            List<String> lista = file.getFiles(srcPath);
            for (String nomeFile : lista) {
                if (!file.isEsisteFile(destPath + SLASH + nomeFile) || flagSovrascriveFile) {
                    file.copyFile(srcPath + SLASH + nomeFile, destPath + SLASH + nomeFile);
                }// end of if cycle
            }// end of for cycle
        }// end of if/else cycle

    }// end of method


    private String leggeFile(String nomeFileTextSorgente) {
        String nomeFileTxt = nomeFileTextSorgente;

        if (!nomeFileTxt.endsWith(SOURCE_SUFFIX)) {
            nomeFileTxt += SOURCE_SUFFIX;
        }// end of if cycle

        return file.leggeFile(pathSources + SLASH + nomeFileTxt);
    }// end of method


    protected void checkAndWriteFile(String pathNewFile, String sourceText) {
        String fileNameJava = VUOTA;
        String oldText = VUOTA;

        if (flagSovrascriveFile) {
            file.scriveNewFile(pathNewFile, sourceText);
            System.out.println(fileNameJava + " esisteva già ed è stato modificato");
        } else {
            oldText = file.leggeFile(pathNewFile);
            if (text.isValid(oldText)) {
                if (checkFile(oldText)) {
                    file.scriveNewFile(pathNewFile, sourceText);
                    System.out.println(fileNameJava + " esisteva già ed è stato modificato");
                } else {
                    System.out.println(fileNameJava + " esisteva già e NON è stato modificato");
                }// end of if/else cycle
            } else {
                file.scriveNewFile(pathNewFile, sourceText);
                System.out.println(fileNameJava + " non esisteva ed è stato creato");
            }// end of if/else cycle
        }// end of if/else cycle
    }// end of method


    private void checkAndWriteFileTask(Task task, String newTaskText) {
        String fileNameJava = "";
        String pathFileJava;
        String oldFileText = "";

//        fileNameJava = newEntityName + task.getJavaClassName();
//        pathFileJava = packagePath + SEP + fileNameJava;
//
//        if (flagSovrascriveFile) {
//            file.sovraScriveFile(pathFileJava, newTaskText);
//            System.out.println(fileNameJava + " esisteva già ed è stato modificato");
//        } else {
//            oldFileText = file.leggeFile(pathFileJava);
//            if (text.isValid(oldFileText)) {
//                if (checkFile(oldFileText)) {
//                    file.sovraScriveFile(pathFileJava, newTaskText);
//                    System.out.println(fileNameJava + " esisteva già ed è stato modificato");
//                } else {
//                    writeDocOnly(pathFileJava, oldFileText, newTaskText);
//                    System.out.println(fileNameJava + " esisteva già ed è stato modificato SOLO nella documentazione");
//                }// end of if/else cycle
//            } else {
//                file.scriveFile(pathFileJava, newTaskText, true);
//                System.out.println(fileNameJava + " non esisteva ed è stato creato");
//            }// end of if/else cycle
//        }// end of if/else cycle
    }// end of method


    private boolean checkFile(String oldFileText) {
        ArrayList<String> tags = new ArrayList<>();
        tags.add("@AIScript(sovrascrivibile = false)");
        tags.add("@AIScript(sovrascrivibile=false)");
        tags.add("@AIScript(sovrascrivibile= false)");
        tags.add("@AIScript(sovrascrivibile =false)");

        return text.nonContiene(oldFileText, tags);
    }// end of method

}// end of class
