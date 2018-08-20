package it.algos.vaadflow.modules.beta;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadtest.MainLayout;

/**
 * Project vaadbaseflow
 * Created by Algos
 * User: gac
 * Date: sab, 18-ago-2018
 * Time: 11:38
 */
@SpringComponent
@UIScope
@Route(value = "beta", layout = MainLayout.class)
public class BetaView extends VerticalLayout {

    public BetaView() {
        add(new Label("Sono in beta"));
    }// end of constructor

}// end of class
