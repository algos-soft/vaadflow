package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadflow.ui.dialog.ASearchDialog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadflow.application.FlowCost.TAG_SEARCH;
import static it.algos.vaadtest.application.TestCost.TAG_PRO;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 03-gen-2019
 * Time: 11:08
 */
//@SpringComponent("plutoz")
//@Tag(TAG_SEARCH + TAG_PRO)
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@Slf4j
public class ProvaSearchDialog  {

    /**
     * Costruttore <br>
     */
    public ProvaSearchDialog() {
        super();
    }// end of constructor


    /**
     * Costruttore <br>
     */
    public ProvaSearchDialog(IAService service) {
        super();
    }// end of

}// end of class
