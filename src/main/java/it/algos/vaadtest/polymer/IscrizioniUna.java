package it.algos.vaadtest.polymer;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.lang.annotation.Annotation;

import static it.algos.vaadtest.application.TestCost.TAG_ALF;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 10-ago-2019
 * Time: 07:52
 */
@Route(value = "una")
@Tag("iscrizioni-una")
@HtmlImport("src/views/prova/iscrizioni-una.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Viewport("width=device-width")
public class IscrizioniUna extends Iscrizioni {
    @Id("first")
    private EditIscrizione first;

    public void annulla() {
        String alfa=first.getModello().getInizio();
        Notification.show("Annullato a livello di uno: inizio="+alfa, 2000, Notification.Position.MIDDLE);
    }

}// end of class
