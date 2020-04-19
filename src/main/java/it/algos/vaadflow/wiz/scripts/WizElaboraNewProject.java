package it.algos.vaadflow.wiz.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.wiz.enumeration.EAWiz;
import it.algos.vaadflow.wiz.enumeration.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow.application.FlowCost.VUOTA;
import static it.algos.vaadflow.wiz.scripts.WizCost.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:31
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class WizElaboraNewProject extends WizElabora {


    @Override
    public void esegue() {
        super.isNuovoProgetto = true;
        super.esegue();

        super.copiaDirectoryDocumentation();
        super.copiaDirectoryLinks();
        super.copiaDirectorySnippets();

        this.copiaCartellaVaadFlow();
        this.creaModuloNuovoProgetto();

        super.copiaMetaInf();
        super.scriveFileProperties();

        super.scriveFileRead();
        super.copiaFileGit();
        super.scriveFilePom();
//        super.copiaFileBanner();
    }// end of method


    public void copiaCartellaVaadFlow() {
        String srcPath = pathVaadFlowAlgos + VAADFLOW_NAME;
        String destPath = pathProjectAlgos + VAADFLOW_NAME;

        file.copyDirectoryDeletingAll(srcPath, destPath);
    }// end of method


    public void creaModuloNuovoProgetto() {
        //--creaDirectoryProjectModulo
        file.creaDirectory(pathProjectModulo);

        //--classe principale dell'applicazione col metodo 'main'
        creaFileApplicationMainClass();

        //--creaDirectoryApplication (empty)
        file.creaDirectory(pathProjectDirApplication);

        //--creaDirectoryModules (empty)
        file.creaDirectory(pathProjectDirModules);

        //--crea contenuto della directory Application
        scriveFileCost();
        scriveFileBoot();
//        scriveFileVers();

        creaDirectorySecurity();
    }// end of method


    public void creaFileApplicationMainClass() {
        String mainApp = newProjectName + text.primaMaiuscola(APP_NAME);
        mainApp = text.primaMaiuscola(mainApp);
        String destPath = pathProjectModulo + mainApp + JAVA_SUFFIX;
        String testoApp = leggeFile(APP_NAME + TXT_SUFFIX);

        testoApp = Token.replace(Token.moduleNameMinuscolo, testoApp, newProjectName);
        testoApp = Token.replace(Token.moduleNameMaiuscolo, testoApp, text.primaMaiuscola(newProjectName));

        if (EAWiz.flagSecurity.isAbilitato()) {
            testoApp = Token.replace(Token.usaSecurity, testoApp, VUOTA);
        } else {
            testoApp = Token.replace(Token.usaSecurity, testoApp, ", exclude = {SecurityAutoConfiguration.class}");
        }// end of if/else cycle

        file.scriveFile(destPath, testoApp, true);
    }// end of method


    protected void scriveFileCost() {
        String sourceText = leggeFile(FILE_COST);
        String destPath = pathProjectDirApplication + newProjectNameUpper + FILE_COST + JAVA_SUFFIX;

        sourceText = Token.replace(Token.moduleNameMinuscolo, sourceText, newProjectName);
        sourceText = Token.replace(Token.moduleNameMaiuscolo, sourceText, text.primaMaiuscola(newProjectName));

        file.scriveFile(destPath, sourceText, true);
    }// end of method


    public void scriveFileBoot() {
        String sourceText = leggeFile(FILE_BOOT);
        String destPath = pathProjectDirApplication + newProjectNameUpper + FILE_BOOT + JAVA_SUFFIX;

        sourceText = Token.replace(Token.moduleNameMinuscolo, sourceText, newProjectName);
        sourceText = Token.replace(Token.moduleNameMaiuscolo, sourceText, text.primaMaiuscola(newProjectName));

        file.scriveFile(destPath, sourceText, true);
    }// end of method


    public void scriveFileVers() {
        String sourceText = leggeFile(FILE_VERS);
        String destPath = pathProjectDirApplication + newProjectNameUpper + FILE_VERS + JAVA_SUFFIX;

        sourceText = Token.replace(Token.moduleNameMinuscolo, sourceText, newProjectName);
        sourceText = Token.replace(Token.moduleNameMaiuscolo, sourceText, text.primaMaiuscola(newProjectName));

        file.scriveFile(destPath, sourceText, true);
    }// end of method


    public void creaDirectorySecurity() {
    }// end of method

}// end of class
