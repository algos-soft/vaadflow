package it.algos.vaadtest.application;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadtest.MainLayout;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-ago-2018
 * Time: 10:04
 */
@SpringComponent
@UIScope
@Route(value = "", layout = MainLayout.class)
@Slf4j
public class HomeView extends VerticalLayout {
    public HomeView() {
        add(new Label("Per adesso"));
    }
}// end of class
