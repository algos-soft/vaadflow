package it.algos.vaadflow.ui.dialog;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
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

        super.heightHeader = "2em";
        super.heightBody = "4em";
        super.heightFooter = "2em";

        this.textConfirmButton = "OK";
    }// end of method

    /**
     * Java event handler on the server, run asynchronously <br>
     * <p>
     * Evento ricevuto dal file html collegato e che 'gira' sul Client <br>
     * Il collegamento tra il Client sul browser e queste API del Server viene gestito da Flow <br>
     * Uno scritp con lo stesso nome viene (eventualmente) eseguito in maniera sincrona sul Client <br>
     */
    @EventHandler
    @Override
    public void handleClickConferma() {
        close();
    }// end of method

}// end of class
