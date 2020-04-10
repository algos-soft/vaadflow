package it.algos.vaadtest.modules.beta;

import com.vaadin.flow.router.Route;
import it.algos.vaadflow.service.IAService;
import it.algos.vaadflow.ui.form.AViewForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadtest.application.TestCost.TAG_BET;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 10-apr-2020
 * Time: 07:27
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = "form")
public class BetaForm extends AViewForm {

    public BetaForm(@Qualifier(TAG_BET) IAService service) {
        super(service, Beta.class);
    }// end of Vaadin/@Route constructor


    /**
     * Preferenze standard <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     * Le preferenze vengono (eventualmente) lette da mongo e (eventualmente) sovrascritte nella sottoclasse <br>
     */
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaTitoloForm = true;
        super.titoloForm = "Pippoz Belloz";
        super.usaEditButton = true;
        super.usaDeleteButton = true;
    }// end of method


    /**
     * Eventuali messaggi di avviso specifici di questa view ed inseriti in 'alertPlacehorder' <br>
     * <p>
     * Chiamato da AViewForm.initView() <br>
     * Normalmente ad uso esclusivo del developer (eventualmente dell'admin) <br>
     * Può essere sovrascritto, per aggiungere informazioni <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    protected void fixAlertLayout() {
        super.fixAlertLayout();

        alertPlacehorder.add(text.getLabelDev("Developer"));
        alertPlacehorder.add(text.getLabelAdmin("Admin"));
        alertPlacehorder.add(text.getLabelUser("User"));
    }// end of method

}// end of class
