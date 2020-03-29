package it.algos.vaadtest.vaadinbindingdemo;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;

@Route(value = "vaad")
@HtmlImport("src/views/prova/vaad.html")
@Tag("my-template")
//@JsModule("./com/example/my-template.js")
public class PolymerBindingTemplate extends PolymerTemplate<BindingModel> {
    public PolymerBindingTemplate() {
        getModel().setHostProperty("Bound property");
    }
}
