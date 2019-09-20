package it.algos.vaadtest.modules.beta;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadflow.ui.dialog.IADialog;
import it.algos.vaadflow.ui.MainLayout;
import it.algos.vaadflow.ui.list.AGridViewList;
import it.algos.vaadflow.enumeration.EAOperation;
import it.algos.vaadtest.application.MainLayout14;
import it.algos.vaadflow.backend.entity.AEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import static it.algos.vaadtest.application.TestCost.TAG_BET;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-set-2019 10.57.42 <br>
 * <br>
 * Estende la classe astratta AViewList per visualizzare la Grid <br>
 * <p>
 * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
 * Le istanze @Autowired usate da questa classe vengono iniettate automaticamente da SpringBoot se: <br>
 * 1) vengono dichiarate nel costruttore @Autowired di questa classe, oppure <br>
 * 2) la property è di una classe con @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON), oppure <br>
 * 3) vengono usate in un un metodo @PostConstruct di questa classe, perché SpringBoot le inietta DOPO init() <br>
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
@Route(value = TAG_BET, layout = MainLayout14.class)
@Qualifier(TAG_BET)
@Slf4j
@AIScript(sovrascrivibile = true)
public class BetaList extends AGridViewList {


    /**
     * Icona visibile nel menu (facoltativa)
     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
     * Se manca il MENU_NAME, di default usa il 'name' della view
     */
    public static final VaadinIcon VIEW_ICON = VaadinIcon.ASTERISK;


   /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param dialog    per visualizzare i fields
     */
    @Autowired
    public BetaList(@Qualifier(TAG_BET) IADialog dialog, @Qualifier(TAG_BET) IAService service) {
        super(null, null, service);
        super.entityClazz = Beta.class;
    }// end of Spring constructor


    /**
     * Apertura del dialogo per una nuova entity <br>
     * Sovrascritto <br>
     */
    protected void openNew() {
        dialog = appContext.getBean(BetaDialog.class, service, entityClazz);
        ((BetaDialog) dialog).fixFunzioni(this::save, this::delete);
        dialog.open(service.newEntity(), EAOperation.addNew, context);
    }// end of method


    /**
     * Apertura del dialogo per una entity esistente <br>
     * Sovrascritto <br>
     */
    protected void openEdit(AEntity entityBean) {
        dialog = appContext.getBean(BetaDialog.class, service, entityClazz);
        ((BetaDialog) dialog).fixFunzioni(this::save, this::delete);
        dialog.open(entityBean, EAOperation.edit, context);
    }// end of method

}// end of class