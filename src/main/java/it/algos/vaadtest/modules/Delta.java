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
 * Time: 10:42
 */
@Route(value = "delta", layout = MainLayout.class)
@Slf4j
public class Delta extends VerticalLayout {

    public Delta() {
        add(new Label("Sono in delta"));
    }// end of constructor

}// end of class
