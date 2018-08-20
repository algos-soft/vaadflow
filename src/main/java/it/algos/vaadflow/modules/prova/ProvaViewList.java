package it.algos.vaadflow.modules.prova;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.presenter.IAPresenter;
import it.algos.vaadflow.ui.AViewList;
import it.algos.vaadflow.ui.dialog.IADialog;
import it.algos.vaadtest.MainLayout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import static it.algos.vaadflow.application.FlowCost.TAG_PRO;

/**
 * Project vaadflow <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 20-ago-2018 19.09.21 <br>
 * <br>
 * Estende la classe astratta AViewList per visualizzare la Grid <br>
 * <p>
 * Invocata da @Route e NON dalla catena @Autowired di SpringBoot <br>
 * Le istanza A VALLE di questa classe vengono iniettate automaticamente da SpringBoot se: <br>
 * 1) vengono dichiarate nel costruttore @Autowired di questa classe <br>
 * 2) usano @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) <br>
 * <p>
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Not annotated with @SpringComponent (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @UIScope (obbligatorio) <br>
 * Annotated with @Route (obbligatorio) per la selezione della vista. @Route(value = "") per la vista iniziale <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@UIScope
@Route(value = TAG_PRO, layout = MainLayout.class)
@Qualifier(TAG_PRO)
@Slf4j
@AIScript(sovrascrivibile = true)
public class ProvaViewList extends AViewList {


    /**
     * Icona visibile nel menu (facoltativa)
     */
    public static final VaadinIcon VIEW_ICON = VaadinIcon.ASTERISK;


   /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     * @param dialog    per visualizzare i fields
     */
    @Autowired
    public ProvaViewList(@Qualifier(TAG_PRO) IAPresenter presenter, @Qualifier(TAG_PRO) IADialog dialog) {
        super(presenter, dialog);
       // ((ProvaViewDialog) dialog).fixFunzioni(this::save, this::delete);
    }// end of Spring constructor


}// end of class