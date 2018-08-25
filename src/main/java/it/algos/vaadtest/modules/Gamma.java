package it.algos.vaadtest.modules;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import it.algos.vaadflow.ui.MainLayout;
import lombok.extern.slf4j.Slf4j;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-ago-2018
 * Time: 17:55
 */
@Route(value = "gamma", layout = MainLayout.class)
@Slf4j
public class Gamma extends VerticalLayout {

    public Gamma() {
        add(new Label("Sono in gamma"));
    }// end of constructor

}// end of class
