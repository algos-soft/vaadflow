package it.algos.vaadflow.ui.dialog;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.enumeration.EAColor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Map;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 16-ago-2019
 * Time: 15:09
 */
@Route(value = "dialogouno")
@Tag("dialogo-uno")
@HtmlImport("src/views/dialoghi/dialogo-uno.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DialogoUnoPolymer extends DialogoPolymer {

//    /**
//     * Component iniettato nel polymer html con lo stesso ID <br>
//     */
//    @Id("header")
//    private Span header;


    /**
     * Preferenze standard.
     * Le preferenze vengono eventualmente sovrascritte nella sottoclasse
     * Invocare PRIMA il metodo della superclasse
     */
    @Override
    protected void fixPreferenze() {
        super.fixPreferenze();

        super.usaHeader = false;
        super.backgroundColorHeader = EAColor.red;
        super.backgroundColorBody = EAColor.green;
        super.backgroundColorFooter = EAColor.pink;

        super.width = "18em";
        super.heightBody = "8em";
        super.heightFooter = "4em";

        this.textConfirmlButton = "OK";
    }// end of method


    //    private void fixHeader() {
////        this.title = new Label();
//        Button close = new Button();
//        close.setIcon(VaadinIcon.CLOSE.create());
////        close.addClickListener(buttonClickEvent -> close());//@todo LEVATO
//
//        HorizontalLayout headerLayout = new HorizontalLayout();
////        headerLayout.add(this.title, close);
////        headerLayout.setFlexGrow(1, this.title);
//        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
//        headerLayout.getStyle().set("background-color", EAColor.lightgreen.getTag());
////        add(header);//@todo LEVATO
//
////        header.setText("alfa");
//    }// end of method
//
//
    @Override
    protected void fixBody() {
        body.setText(bodyText);
        body.getStyle().set("background-color", super.backgroundColorBody.getTag());
    }// end of method


    @Override
    protected void fixFooter() {
        conferma.setText(textConfirmlButton);
    }// end of method


}// end of class
