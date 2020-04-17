package it.algos.vaadflow.wiz.scripts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.wiz.enumeration.Chiave;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import static it.algos.vaadflow.application.FlowCost.SLASH;
import static it.algos.vaadflow.wiz.scripts.WizCost.DIR_JAVA;
import static it.algos.vaadflow.wiz.scripts.WizCost.VAAD_FLOW_PROJECT;

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

        this.copiaCartelleVarie();
//        this.copiaCartellaVaadFlow();
    }// end of method


    public void copiaCartelleVarie() {
        super.regolaDocumentation();
        super.regolaLinks();
        super.regolaSnippets();
        super.regolaRead();
        super.regolaBanner();
        super.regolaGit();
        super.regolaProperties();
        super.regolaPom();
        super.copiaMetaInf();
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


//        super.fixCartellaExtra(true, DIR_JAVA + SLASH + VAAD_FLOW_PROJECT);
    }// end of method

}// end of class
