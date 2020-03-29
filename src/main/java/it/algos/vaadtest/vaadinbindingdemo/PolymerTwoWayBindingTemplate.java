package it.algos.vaadtest.vaadinbindingdemo;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;

@Route(value = "vaad2")
@HtmlImport("src/views/prova/polymer2way.html")
@Tag("two-way-template")
public class PolymerTwoWayBindingTemplate extends PolymerTemplate<TwoWayBindingModel> {

    public PolymerTwoWayBindingTemplate() {
        reset();
        getElement().addPropertyChangeListener("name", event -> System.out.println("Name is set to: " + getModel().getName()));
        getElement().addPropertyChangeListener("accepted", event -> System.out.println("isAccepted is set to: "+ getModel().getAccepted()));
        getElement().addPropertyChangeListener("size", event -> System.out.println("Size is set to: " + getModel().getSize()));
    }

    @EventHandler
    private void reset() {
        getModel().setName("John");
        getModel().setAccepted(false);
        getModel().setSize("medium");
    }

    @EventHandler
    private void submit() {
        TwoWayBindingModel model= getModel();
        String name=model.getName();
        int a = 87;
        int b=a;
    }


}
