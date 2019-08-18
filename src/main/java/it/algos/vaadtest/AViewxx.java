package it.algos.vaadtest;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = "xx")
@HtmlImport("src/views/a.html")
@Tag("a-a")
public class AViewxx extends PolymerTemplate<AViewxx.DialogoModel> implements HasComponents {

    private @Id
    Dialog dialog;


    public AViewxx() {
        getModel().setWidth("Do you want to save or discard your changes before navigating away");
        getModel().setBackgroundColorFooter("#FFFF00");
        dialog.setCloseOnOutsideClick(true);
        dialog.setCloseOnEsc(true);
        Registration registration = dialog.addDialogCloseActionListener(e -> close());
        dialog.open();

//        Button a = new Button("Injected", event -> dialog.open());

//        Button b = new Button("Java dialog", event -> {
//            Dialog javaDialog = new Dialog();
//            Div div = new Div();
//            div.setText("bbbbbbbbbbbb");
//            Button beta=new Button("Beta");
//            javaDialog.add(div);
//            javaDialog.add(beta);
//            javaDialog.open();
//        });
//        add(a, b);
    }



    public void close() {
        dialog.close();
        System.out.println("Uscito");
    }


    /**
     * Modello dati per collegare questa classe java col polymer
     */
    public interface DialogoModel extends TemplateModel {

        void setWidth(String width);

        void setHeightHeader(String heightHeader);

        void setHeightBody(String heightBody);

        void setHeightFooter(String heightFooter);

        void setBackgroundColorHeader(String backgroundColorHeader);

        void setBackgroundColorBody(String backgroundColorBody);

        void setBackgroundColorFooter(String backgroundColorFooter);

    }// end of interface

}