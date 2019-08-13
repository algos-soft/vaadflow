package it.algos.vaadtest.polymer;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 10-ago-2019
 * Time: 07:52
 */
@Route(value = "tre")
@Tag("prova-polymer3")
@HtmlImport("src/views/prova/prova-polymer3.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Viewport("width=device-width")
public class IscrizioniTre {
    @Id("first")
    private EditIscrizione first;

    @Id("second")
    private EditIscrizione second;

    @Id("third")
    private EditIscrizione third;
}// end of class
