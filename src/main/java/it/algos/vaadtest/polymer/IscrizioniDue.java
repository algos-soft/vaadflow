package it.algos.vaadtest.polymer;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 10-ago-2019
 * Time: 07:52
 */
@Route(value = "due")
@Tag("iscrizioni-due")
@HtmlImport("src/views/prova/iscrizioni-due.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Viewport("width=device-width")
public class IscrizioniDue extends Iscrizioni {

    @Id("first")
    private EditIscrizione first;

    @Id("second")
    private EditIscrizione second;

}// end of class
