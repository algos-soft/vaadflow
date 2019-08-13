package it.algos.vaadtest.polymer;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 10-ago-2019
 * Time: 10:19
 */
public abstract class Iscrizioni extends PolymerTemplate<TemplateModel> {
    @Id("buttons")
    private Coda buttons;

    public Iscrizioni() {
//        buttons.setConfermaDisabled(true);
        buttons.addAnnullalListener(e -> annulla());
        buttons.addConfermaListener(e -> conferma());
    }// end of constructor

    public void annulla() {
        Notification.show("Annullato nmella superclasse", 2000, Notification.Position.MIDDLE);
    }
    public void conferma() {
        Notification.show("Confermato", 2000, Notification.Position.MIDDLE);
    }

}// end of class
