package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.application.AContext;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAOperation;
import it.algos.vaadflow.presenter.IAPresenter;
import it.algos.vaadflow.service.AColumnService;
import it.algos.vaadflow.service.AMailService;
import it.algos.vaadflow.ui.dialog.AViewDialog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;

import static it.algos.vaadtest.application.TestCost.TAG_PRO;

/**
 * Project vaadtest <br>
 * Created by Algos
 * User: Gac
 * Fix date: 20-ott-2018 18.52.31 <br>
 * <p>
 * Estende la classe astratta AViewDialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_PRO)
@Slf4j
@AIScript(sovrascrivibile = true)
public class ProvaViewDialog extends AViewDialog<Prova> {

    @Autowired
    protected AMailService posta;

   /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     */
    @Autowired
    public ProvaViewDialog(@Qualifier(TAG_PRO) IAPresenter presenter) {
        super(presenter);
    }// end of Spring constructor



    /**
     * Opens the given item for editing in the dialog.
     * Legge la entityBean, ed inserisce i valori nel binder
     * Legge la entityBean ed inserisce nella UI i valori di eventuali fields NON associati al binder
     *
     * @param item      The item to edit; it may be an existing or a newly created instance
     * @param operation The operation being performed on the item
     * @param context   legato alla sessione
     * @param title     of the window dialog
     */
    @Override
    public void open(AEntity item, EAOperation operation, AContext context, String title) {
        posta.send("Seconda","Spedita alle: "+ LocalDateTime.now().toString());
        super.open(item, operation, context, title);
    }// end of method

}// end of class