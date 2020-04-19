package it.algos.vaadflow.wiz.scripts;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.wiz.enumeration.EAWiz;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-apr-2020
 * Time: 09:52
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WizDialogUpdateProject extends WizDialog {


    /**
     * Apertura del dialogo <br>
     */
    public void open(WizRecipient wizRecipient) {
        super.wizRecipient = wizRecipient;
        super.isNuovoProgetto = false;
        super.titoloCorrente = new H3();

        super.inizia();
        super.creaBottoni();
        this.creaCheckBoxList();
        this.add(layoutBottoni);
        super.open();
    }// end of method


    /**
     * Legenda iniziale <br>
     */
    protected void legenda() {
        super.legenda();

        layoutLegenda.add(new Label("Update di un progetto esistente"));
        layoutLegenda.add(new Label("Il modulo vaadflow viene sovrascritto"));
        layoutLegenda.add(new Label("Eventuali modifiche locali vengono perse"));
        layoutLegenda.add(new Label("Il modulo di questo progetto NON viene toccato"));
    }// end of method


    /**
     * Di default regola a 'true' solo il parametro 'flagFlow' <br>
     */
    protected void creaCheckBoxList() {
        EAWiz.flagFlow.setStatus(true);

        super.creaCheckBoxList();
    }// end of method

}// end of class
