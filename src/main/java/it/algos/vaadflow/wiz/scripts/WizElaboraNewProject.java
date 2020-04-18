package it.algos.vaadflow.wiz.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.wiz.enumeration.Chiave;
import it.algos.vaadflow.wiz.enumeration.Token;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import static it.algos.vaadflow.application.FlowCost.SLASH;
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
    public void gotInput(LinkedHashMap<Chiave, Object> mappaInput) {
        super.isNuovoProgetto = true;

        super.gotInput(mappaInput);

        this.copiaCartellaVaadFlow();
        this.copiaCartelleVarie();
        this.creaModuloNuovoProgetto();
    }// end of method


    public void copiaCartellaVaadFlow() {
        String source = pathVaadFlow + DIR_JAVA + SLASH + VAAD_FLOW_PROJECT;
        File srcDir = new File(source);

        String destination = pathProject + DIR_JAVA + SLASH + VAAD_FLOW_PROJECT;
        File destDir = new File(destination);

        try {
            FileUtils.copyDirectory(srcDir, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// end of method


    public void copiaCartelleVarie() {
        super.copiaDocumentation();
        super.copiaLinks();
        super.copiaSnippets();
        super.copiaRead();
        super.copiaBanner();
        super.copiaGit();
        super.copiaProperties();
        super.copiaPom();
        super.copiaMetaInf();
    }// end of method


    public void creaModuloNuovoProgetto() {
        String srcPath = pathVaadFlow;
        String destPath = pathProject;

        creaApplicationMainClass();
        creaDirectoryApplication();
        creaDirectoryModules();
        creaDirectorySecurity();
    }// end of method


    public void creaApplicationMainClass() {
        String mainApp = newProjectName + text.primaMaiuscola(APP_NAME);
        mainApp = text.primaMaiuscola(mainApp);
        String destPath = pathProjectModulo + mainApp + JAVA_SUFFIX;
        String testoApp = leggeFile("Application.txt");

        testoApp = Token.replace(Token.moduleNameMinuscolo, testoApp, newProjectName);
        testoApp = Token.replace(Token.moduleNameMaiuscolo, testoApp, text.primaMaiuscola(newProjectName));

        if (flagSecurity) {
            testoApp = Token.replace(Token.usaSecurity, testoApp, VUOTA);
        } else {
            testoApp = Token.replace(Token.usaSecurity, testoApp, ", exclude = {SecurityAutoConfiguration.class}");
        }// end of if/else cycle

        file.scriveFile(destPath, testoApp, true);
    }// end of method


    public void creaDirectoryApplication() {
        String destPath = pathProjectModulo + DIR_APPLICATION;
        file.creaDirectory(destPath);
    }// end of method


    public void creaDirectoryModules() {
    }// end of method


    public void creaDirectorySecurity() {
    }// end of method

}// end of class
