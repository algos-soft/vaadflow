package it.algos.vaadtest.modules;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import it.algos.vaadflow.ui.MainLayout;

@Route(value="hello", layout = MainLayout.class)

public class TabellonePage extends VerticalLayout {

    public TabellonePage() {
//        HelloWorld hello = new HelloWorld();
//        add(hello);
        this.setMargin(false);
        this.setSpacing(false);
        add(new Label("ciao2"));
        add(new Label("seconda riga"));

    }



//    @Id("content")
//    private Div content;
//
//    public void setContent(Component content) {
//        this.content.removeAll();
//        this.content.add(content);
//    }

}
