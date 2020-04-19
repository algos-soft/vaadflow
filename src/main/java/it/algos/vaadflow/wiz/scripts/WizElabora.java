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
    //--directory che contine java e resources (da elaborare)
    //--pathVaadFlow più DIR_MAIN
    protected String pathVaadFlowMain;

    //--regolata indipendentemente dai risultati del dialogo
    //--dipende solo da dove si trava attualmente il progetto VaadFlow
    //--posso spostarlo (è successo) senza che cambi nulla
    //--directory che contine il modulo vaadflow ed il modulo del programmma corrente (da elaborare)
    //--pathVaadFlow più DIR_JAVA
    protected String pathVaadFlowAlgos;

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
    //--ha senso solo se isNuovoProgetto=true
    //--newProjectName con la prima lettera maiuscola da usare per costruire i nomi delle classi
    protected String newProjectNameUpper;

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

    //--regolata in base ai risultati del dialogo
    //--pathProjectModulo più DIR_APPLICATION
    protected String pathProjectDirApplication;

    //--regolata in base ai risultati del dialogo
    //--pathProjectModulo più DIR_MODULES
    protected String pathProjectDirModules;

    /**
     * Evento lanciato alla chiusura del dialogo
     */
    @Override
    public void esegue() {
        this.regolazioniIniziali();
    }// end of method


    /**
     * Regolazioni iniziali indipendenti (in parte) dal dialogo di input <br>
     */
    protected void regolazioniIniziali() {
        this.pathUserDir = EAWiz.pathUserDir.getValue();
        this.pathVaadFlow = EAWiz.pathVaadFlow.getValue();
        this.pathVaadFlowMain = pathVaadFlow + DIR_MAIN;
        this.pathVaadFlowAlgos = pathVaadFlow + DIR_JAVA;
        this.pathVaadFlowSources = EAWiz.pathVaadFlowSources.getValue();

        this.newProjectName = EAWiz.nameTargetProject.getValue();
        this.newProjectNameUpper = text.primaMaiuscola(newProjectName);
        this.pathProject = EAWiz.pathTargetProjet.getValue();

        this.pathProjectMain = pathProject + DIR_MAIN;
        this.pathProjectAlgos = pathProject + DIR_JAVA;
        this.pathProjectModulo = pathProjectAlgos + newProjectName + SLASH;
        this.pathProjectDirApplication = pathProjectModulo + DIR_APPLICATION;
        this.pathProjectDirModules = pathProjectModulo + DIR_MODULES;

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
            System.out.println("Sorgenti VaadFlow: pathVaadFlowSources=" + pathVaadFlowSources);

            System.out.println("Nome target progetto: newProjectName=" + newProjectName);
            System.out.println("Nome target progetto maiuscolo: newProjectNameUpper=" + newProjectNameUpper);
            System.out.println("Path target progetto: pathProject=" + pathProject);

            System.out.println("Cartella 'main' di VaadFlow: pathVaadFlowMain=" + pathVaadFlowMain);
            System.out.println("Cartella 'algos' di VaadFlow: pathVaadFlowAlgos=" + pathVaadFlowAlgos);
            System.out.println("Cartella dei sorgenti di testo di VaadFlow=" + pathVaadFlowSources);
            System.out.println("Directory principale target: pathProjectMain=" + pathProjectMain);
            System.out.println("Cartella Algos: pathProjectAlgos=" + pathProjectAlgos);
            System.out.println("Modulo progetto: pathProjectModulo=" + pathProjectModulo);
            System.out.println("Directory application: pathProjectDirApplication=" + pathProjectDirApplication);
            System.out.println("Directory modules: pathProjectDirModules=" + pathProjectDirModules);

            System.out.println("");
        }// end of if cycle

    }// end of method


    /**
     * Cartella di documentazione (in formati vari)
     * Copia la directory da root di VaadFlow
     */
    protected void copiaDirectoryDocumentation() {
        if (EAWiz.flagDocumentation.isAbilitato()) {
            copyCartellaRootProject(DIR_DOC);
        }// end of if cycle
    }// end of method


    /**
     * Cartella di LINKS utili in text
     * Copia la directory da root di VaadFlow
     */
    protected void copiaDirectoryLinks() {
        if (EAWiz.flagLinks.isAbilitato()) {
            copyCartellaRootProject(DIR_LINKS);
        }// end of if cycle
    }// end of method


    /**
     * Cartella di snippets utili in text
     * Copia la directory da root di VaadFlow
     */
    protected void copiaDirectorySnippets() {
        if (EAWiz.flagSnippets.isAbilitato()) {
            copyCartellaRootProject(DIR_SNIPPETS);
        }// end of if cycle
    }// end of method


    public void copiaCartellaVaadFlow() {
        String srcPath = pathVaadFlowAlgos + NAME_VAADFLOW;
        String destPath = pathProjectAlgos + NAME_VAADFLOW;

        if (EAWiz.flagFlow.isAbilitato()) {
            file.copyDirectoryDeletingAll(srcPath, destPath);
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
     * File application.properties
     * Controlla se esiste la directory e nel caso la crea
     * Letto dai sorgenti
     * Sovrascrive dopo aver controllato se non c'è lo stop nel testo
     */
    protected void scriveFileProperties() {
        String sourceText = leggeFile(FILE_PROPERTIES);
        String destPath = pathProject + DIR_RESOURCES + FILE_PROPERTIES_DEST;
        sourceText = Token.replace(Token.moduleNameMinuscolo, sourceText, newProjectName);

        if (EAWiz.flagProperty.isAbilitato()) {
            checkAndWriteFile(destPath, sourceText);
        }// end of if cycle
    }// end of method


    /**
     * File README di text
     * Elabora il file dai sorgenti di VaadFlow
     */
    protected void scriveFileRead() {
        String sourceText = leggeFile(FILE_READ);
        String destPath = pathProject + FILE_READ + TXT_SUFFIX;

        if (EAWiz.flagRead.isAbilitato()) {
            file.scriveFile(destPath, sourceText, true);
        }// end of if cycle

    }// end of method


    /**
     * File di esclusioni GIT di text
     * Copia la directory da root di VaadFlow
     */
    protected void copiaFileGit() {
        if (EAWiz.flagGit.isAbilitato()) {
            copyFileRootProject(FILE_GIT);
        }// end of if cycle
    }// end of method


    /**
     * File MAVEN di script
     * Copia il file da root di VaadFlow
     */
    protected void scriveFilePom() {
        String sourceText = leggeFile(FILE_POM);
        String destPath = pathProject + FILE_POM + XML_SUFFIX;
        sourceText = Token.replace(Token.moduleNameMinuscolo, sourceText, newProjectName);

        if (EAWiz.flagPom.isAbilitato()) {
            checkAndWriteFile(destPath, sourceText);
        }// end of if cycle
    }// end of method


    /**
     * File banner di text
     * Copia il file da ?
     */
    protected void copiaFileBanner() {
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

        file.copyFileDeletingAll(srcPath, destPath);
    }// end of method


    protected String leggeFile(String nomeFileTextSorgente) {
        String nomeFileTxt = nomeFileTextSorgente;

        if (!nomeFileTxt.endsWith(TXT_SUFFIX)) {
            nomeFileTxt += TXT_SUFFIX;
        }// end of if cycle

        return file.leggeFile(pathVaadFlowSources + nomeFileTxt);
    }// end of method


    protected void checkAndWriteFile(String pathNewFile, String sourceText) {
        String oldText = VUOTA;

        if (file.isEsisteFile(pathNewFile)) {
            oldText = file.leggeFile(pathNewFile);
            if (text.isValid(oldText)) {
                if (checkFile(oldText)) {
                    file.scriveFile(pathNewFile, sourceText, true);
                }// end of if cycle
            } else {
                file.scriveFile(pathNewFile, sourceText, true);
            }// end of if/else cycle
        } else {
            file.scriveFile(pathNewFile, sourceText, true);
        }// end of if/else cycle
    }// end of method


    private void checkAndWriteFileTask(Task task, String newTaskText) {
        String fileNameJava = "";
        String pathFileJava;
        String oldFileText = "";
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
