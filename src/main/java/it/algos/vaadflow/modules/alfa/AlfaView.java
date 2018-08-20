package it.algos.vaadflow.modules.alfa;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadtest.MainLayout;

/**
 * Project vaadbaseflow
 * Created by Algos
 * User: gac
 * Date: sab, 18-ago-2018
 * Time: 11:33
 */
@UIScope
@Route(value = "alfa", layout = MainLayout.class)
public class AlfaView extends VerticalLayout {

    public AlfaView() {
        add(new Label("Sono in alfa"));
    }// end of constructor

}// end of class
