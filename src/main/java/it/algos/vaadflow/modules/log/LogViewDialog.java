package it.algos.vaadflow.modules.log;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.presenter.IAPresenter;
import it.algos.vaadflow.ui.dialog.AViewDialog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import static it.algos.vaadflow.application.FlowCost.TAG_LOG;

/**
 * Project vaadflow <br>
 * Created by Algos
 * User: Gac
 * Date: 26-ago-2018 9.52.58
 * <p>
 * Estende la classe astratta AViewDialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @UIScope (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@UIScope
@Qualifier(TAG_LOG)
@Slf4j
@AIScript(sovrascrivibile = true)
public class LogViewDialog extends AViewDialog<Log> {


   /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     */
    @Autowired
    public LogViewDialog(@Qualifier(TAG_LOG) IAPresenter presenter) {
        super(presenter);
    }// end of constructor

    
}// end of class