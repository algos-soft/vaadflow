package it.algos.vaadflow.wiz;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.wizard.enumeration.Chiave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.LinkedHashMap;

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

        copiaCartelleVarie();
    }// end of method


    public void copiaCartelleVarie() {
        super.regolaDocumentation();
        this.regolaLinks();
        this.regolaSnippets();
        this.regolaRead();
        this.regolaGit();
        this.regolaProperties();
//        this.regolaPom();
//        this.copiaMetaInf();
    }// end of method

}// end of class
