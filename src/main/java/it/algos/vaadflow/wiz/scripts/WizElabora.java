package it.algos.vaadflow.wiz.scripts;

import it.algos.vaadflow.service.AFileService;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.wiz.enumeration.EAWiz;
import it.algos.vaadflow.wiz.enumeration.Task;
import it.algos.vaadflow.wiz.enumeration.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

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

    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con @Autowired <br>
     * Disponibile al termine del costruttore di questa classe <br>
     */
    @Autowired
    protected AFileService file;

    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio <br>
     * Iniettata automaticamente dal framework SpringBoot/Vaadin con @Autowired <br>
     * Disponibile al termine del costruttore di questa classe <br>
     */
    @Autowired
    protected ATextService text;

//    --wrapper di trasmissione dati tra i dialoghi e e questa classe di elaborazione
//    protected LinkedHashMap<Chiave, Object> mappaInput;

    //--flag di controllo regolato nella sottoclasse concreta
    protected boolean isNuovoProgetto;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory dove gira questo programma; recuperata dal System
    protected String pathUserDir;

//    //--regolata indipendentemente dai risultati del dialogo
//    //--dipende solo da dove si trava attualmente il progetto VaadFlow
//    //--posso spostarlo (è successo) senza che cambi nulla
//    //--directory che contiene i nuovi programmi appena creati da Idea
//    protected String pathProjectsDir;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory che contiene il programma VaadFlow
    //--normalmente uguale a pathUserDir
    protected String pathVaadFlow;

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
    protected String pathVaadFlowSources;

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

    //--regolata in base ai risultati del dialogo
    //--pathProjectMain più DIR_JAVA
    protected String pathProjectAlgos;

    //--regolata in base ai risultati del dialogo
    //--pathProjectAlgos più newProjectName
    protected String pathProjectModulo;


    /**
     * Evento lanciato alla chiusura del dialogo
     */
    @Override
    public void esegue() {
//        this.mappaInput = mappaInput;
        this.regolazioniIniziali();
    }// end of method


    /**
     * Regolazioni iniziali indipendenti (in parte) dal dialogo di input <br>
     */
    protected void regolazioniIniziali() {
        this.pathUserDir = EAWiz.pathUserDir.getValue();
        this.pathVaadFlow = EAWiz.pathVaadFlow.getValue();
//        if (isNuovoProgetto) {
//            this.pathProjectsDir = EAWiz.pathProjectsDir.getValue();
//        } else {
//            this.pathProjectsDir = VUOTA;
//        }// end of if/else cycle
        this.pathVaadFlowMain = pathVaadFlow + DIR_MAIN;
        this.pathVaadFlowSources = EAWiz.pathVaadFlowSources.getValue();

        if (isNuovoProgetto) {
            this.newProjectName = EAWiz.newProjectName.getValue();
        } else {

        }// end of if/else cycle
        this.pathProject = EAWiz.pathProjet.getValue() + SLASH;

//        //--Path proveniente da un File (directory) che finisce SENZA '/' (slash)
//        //--Aggiungo lo slash per omogeneità con tutte le altre directory
//        if (isNuovoProgetto) {
//            this.pathProject = (String) mappaInput.get(Chiave.pathTargetProget) + SLASH;
//        } else {
//            this.pathProject = VUOTA;
//        }// end of if/else cycle
        pathProjectMain = pathProject + DIR_MAIN;
        pathProjectAlgos = pathProject + DIR_JAVA;
        pathProjectModulo = pathProjectAlgos + newProjectName + SLASH;

        //--visualizzazione di controllo
        if (FLAG_DEBUG_WIZ) {
            System.out.println("");
            System.out.println("********************");
            if (isNuovoProgetto) {
                System.out.println("Ingresso in WizElaboraNewProject");
            } else {
                System.out.println("Ingresso in WizElaboraUpdateProject");
            }// end of if/else cycle
            System.out.println("********************");
            System.out.println("Progetto corrente: pathUserDir=" + pathUserDir);
            System.out.println("Directory VaadFlow: pathVaadFlow=" + pathVaadFlow);
//            if (isNuovoProgetto) {
//                System.out.println("Directory dei nuovi progetti: pathProjectsDir=" + pathProjectsDir);
//            }// end of if cycle
            System.out.println("Sorgenti VaadFlow: pathVaadFlowSources=" + pathVaadFlowSources);
            if (isNuovoProgetto) {
                System.out.println("Nome nuovo progetto: newProjectName=" + newProjectName);
                System.out.println("Path nuovo progetto: pathProject=" + pathProject);
            }// end of if cycle

            System.out.println("Cartella 'main' di VaadFlow': pathVaadFlowMain=" + pathVaadFlowMain);
            System.out.println("Cartella dei sorgenti di testo di VaadFlow=" + pathVaadFlowSources);
            System.out.println("Directory principale target: pathProjectMain=" + pathProjectMain);
            System.out.println("Cartella Algos: pathProjectAlgos=" + pathProjectAlgos);
            System.out.println("Modulo progetto: pathProjectModulo=" + pathProjectModulo);

            System.out.println("");
        }// end of if cycle

    }// end of method


    /**
     * Cartella di documentazione (in formati vari)
     */
    protected void copiaDocumentation() {
        if (EAWiz.flagDocumentation.isAbilitato()) {
            copyCartellaRootProject(DIR_DOC);
        }// end of if cycle
    }// end of method


    /**
     * Cartella di LINKS utili in text
     */
    protected void copiaLinks() {
        if (EAWiz.flagLinks.isAbilitato()) {
            copyCartellaRootProject(DIR_LINKS);
        }// end of if cycle
    }// end of method


    /**
     * Cartella di snippets utili in text
     */
    protected void copiaSnippets() {
        if (EAWiz.flagSnippets.isAbilitato()) {
            copyCartellaRootProject(DIR_SNIPPETS);
        }// end of if cycle
    }// end of method


    /**
     * File README di text
     */
    protected void copiaRead() {
        if (EAWiz.flagRead.isAbilitato()) {
            copyFileRootProject(FILE_READ + TXT_SUFFIX);
        }// end of if cycle
    }// end of method


    /**
     * File banner di text
     */
    protected void copiaBanner() {
//        String pathName = DIR_RESOURCES + SLASH + FILE_BANNER + TXT_SUFFIX;
//        String srcPath = pathVaadFlow + pathName;
//        String destPath = pathTargetProget + pathName;
//
//        if (flagRead) {
//            if (flagSovrascriveFile || !file.isEsisteFile(destPath)) {
//                file.copyFile(srcPath, destPath);
//            }// end of if cycle
//        }// end of if cycle
    }// end of method


    /**
     * File di esclusioni GIT di text
     */
    protected void copiaGit() {
        if (EAWiz.flagGit.isAbilitato()) {
            copyFileRootProject(FILE_GIT);
        }// end of if cycle
    }// end of method


    /**
     * File application.properties
     * Controlla se esiste la directory e nel caso la crea
     * Letto dai sorgenti
     * Sovrascrive dopo aver controllato se non c'è lo stop nel testo
     */
    protected void copiaProperties() {
        String sourceText = leggeFile(FILE_PROPERTIES);
        String destPath = pathProject + DIR_RESOURCES + FILE_PROPERTIES_DEST;
        sourceText = Token.replace(Token.moduleNameMinuscolo, sourceText, newProjectName);

        if (EAWiz.flagProperty.isAbilitato()) {
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

        if (EAWiz.flagResources.isAbilitato()) {
            file.copyDirectoryAddingOnly(srcPath, destPath);
        }// end of if cycle
    }// end of method


    /**
     * File MAVEN di script
     */
    protected void copiaPom() {
        String sourceText = leggeFile(FILE_POM);
        String destPath = pathProject + FILE_POM + XML_SUFFIX;
        sourceText = Token.replace(Token.moduleNameMinuscolo, sourceText, newProjectName);

        if (EAWiz.flagPom.isAbilitato()) {
            checkAndWriteFile(destPath, sourceText);
        }// end of if cycle
    }// end of method


    /**
     * Sovrascrive o aggiunge a seconda del flag 'flagSovrascriveDirectory' <br>
     */
    protected void copyCartellaRootProject(String dirName) {
        String srcPath = pathVaadFlow + dirName;
        String destPath = pathProject + dirName;

        if (EAWiz.flagDirectory.isAbilitato()) {
            file.copyDirectoryDeletingAll(srcPath, destPath);
        } else {
            file.copyDirectoryAddingOnly(srcPath, destPath);
        }// end of if/else cycle
    }// end of method


    /**
     * Crea o modifica a seconda del flag 'flagSovrascriveFile' <br>
     */
    protected void copyFileRootProject(String fileName) {
        String srcPath = pathVaadFlow + fileName;
        String destPath = pathProject + fileName;

        if (EAWiz.flagFile.isAbilitato()) {
            file.sovraScriveFile(srcPath, destPath);
        } else {
            file.copyFile(srcPath, destPath);
        }// end of if/else cycle
    }// end of method


    protected String leggeFile(String nomeFileTextSorgente) {
        String nomeFileTxt = nomeFileTextSorgente;

        if (!nomeFileTxt.endsWith(TXT_SUFFIX)) {
            nomeFileTxt += TXT_SUFFIX;
        }// end of if cycle

        return file.leggeFile(pathVaadFlowSources + nomeFileTxt);
    }// end of method


    protected void checkAndWriteFile(String pathNewFile, String sourceText) {
        String fileNameJava = VUOTA;
        String oldText = VUOTA;

        if (EAWiz.flagFile.isAbilitato()) {
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
