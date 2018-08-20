package it.algos.vaadtest.application;

import com.flowingcode.addons.applayout.AppLayout;
import com.flowingcode.addons.applayout.menu.MenuItem;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-ago-2018
 * Time: 17:52
 */
@Slf4j
public class MainLayoutProva extends VerticalLayout {

    public MainLayoutProva() {
        super();
        this.removeAll();
        final AppLayout app = new AppLayout("Layout di prova");
        app.setMenuItems(
                new MenuItem("Aldo", () -> UI.getCurrent().navigate("")),
                new MenuItem("Giovanni", () -> UI.getCurrent().navigate("delta")),
                new MenuItem("Giacomo", () -> UI.getCurrent().navigate("gamma"))
        );
        this.add(app);
    }// end of method

}// end of class
