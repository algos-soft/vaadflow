package it.algos.vaadtest.polymer;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import static it.algos.vaadtest.application.TestCost.TAG_ALF;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 10-ago-2019
 * Time: 07:52
 */
@Tag("algos-testa")
@HtmlImport("src/views/prova/algos-testa.html")
public class Testa extends PolymerTemplate<TemplateModel>{
}// end of class

