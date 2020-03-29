package it.algos.vaadtest.alex;

import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.ModelItem;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.polymertemplate.RepeatIndex;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.algos.vaadtest.modules.beverage.Review;
import it.algos.vaadtest.modules.beverage.ReviewModel;
import it.algos.vaadtest.polymer.ProvaModel;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Route(value = "alex")

//@HtmlImport("src/views/prova/prova-polymer2.html")
//@Tag("prova-polymer2")

@HtmlImport("src/views/prova/alex.html")
@Tag("tag-alex")


//@HtmlImport("src/views/prova/alex.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Viewport("width=device-width")
public class PolymerTemplateAlex extends PolymerTemplate<PolymerModel> {

    public PolymerTemplateAlex() {
        List<PolymerItem> items=new ArrayList<>();
        items.add(new PolymerItem("primo","lumo:edit",1));
        items.add(new PolymerItem("secondo","lumo:error",2));
        items.add(new PolymerItem("terzo","lumo:play",3));
        getModel().setItems(items);
    }

    @EventHandler
    public void submit(@ModelItem PolymerModel item) {
        int a = 87;
    }

    @EventHandler
    private void handleClick(@EventData("event.altKey") boolean altPressed) {
        System.out.println("Received a handle click event");
    }


}
