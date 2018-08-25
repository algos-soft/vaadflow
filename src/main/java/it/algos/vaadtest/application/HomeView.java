package it.algos.vaadtest.application;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.ui.MainLayout;
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


    private Image immagine = new Image("frontend/images/ambulanza.jpg", "vaadin");

//    public VaadinWelcome() {
//        add(immagine);
//        add(new Paragraph("Hello Vaadin 10"));
//        add(new Paragraph("Framework di prova con Vaadin 10"));
//    }// end of method

    public HomeView() {
        add(immagine);
    }
}// end of class
