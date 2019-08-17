package it.algos.vaadflow.ui.dialog;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.enumeration.EAColor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 16-ago-2019
 * Time: 15:09
 */
@Route(value = "dialogouno")
@Tag("dialogo-uno")
@HtmlImport("src/views/dialoghi/dialogo-uno.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DialogoUnoPolymer extends DialogoPolymer {


    /**
     * Preferenze standard.
     * Le preferenze vengono eventualmente sovrascritte nella sottoclasse
     * Invocare PRIMA il metodo della superclasse
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaHeader = false;
        super.backgroundColorHeader = EAColor.red;
        super.backgroundColorBody = EAColor.green;
        super.backgroundColorFooter = EAColor.pink;

        this.textConfirmButton = "OK";
    }// end of method


}// end of class
