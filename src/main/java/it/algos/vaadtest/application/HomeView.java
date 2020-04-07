package it.algos.vaadtest.application;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.ui.LoadMode;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.application.StaticContextAccessor;
import it.algos.vaadflow.ui.menu.AButtonMenu;
import it.algos.vaadflow.ui.menu.APopupMenu;
import it.algos.vaadflow.ui.menu.IAMenu;
import lombok.extern.slf4j.Slf4j;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-ago-2018
 * Time: 10:04
 */
@SpringComponent
@UIScope
@Route(value = "test")
@HtmlImport(value = "styles/algos-styles.html", loadMode = LoadMode.INLINE)
@Slf4j
public class HomeView extends VerticalLayout {


    private Image immagine = new Image("frontend/images/ambulanza.jpg", "vaadin");

//    public VaadinWelcome() {aøal
//        add(immagine);
//        add(new Paragraph("Hello Vaadin 10"));
//        add(new Paragraph("Framework di prova con Vaadin 10"));
//    }// end of method


    public HomeView() {
        this.add(StaticContextAccessor.getBean(APopupMenu.class).getComp());
        add(immagine);
    }

}// end of class
