package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.ui.views.AView;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static it.algos.vaadflow.application.FlowCost.SPAZIO;
import static it.algos.vaadtest.application.TestCost.TAG_VIEW_FORM;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: Sat, 27-Jul-2019
 * Time: 11:17
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = TAG_VIEW_FORM)
//@Viewport("width=device-width")
public class ProvaViewForm extends AView {

    /**
     * Placeholder (eventuale) per informazioni aggiuntive <br>
     * Deve essere sovrascritto <br>
     */
    @Override
    protected void creaAlertLayout() {
        if (text.isValid(parameter)) {
            alertPlaceholder.add(new Label("Ho ricevuto il parametro singolo " + parameter));
        } else {
            alertPlaceholder.add(new Label("Non è arrivato nessun parametro singolo"));
        }// end of if/else cycle

        if (array.isValid(parametersMap)) {
            alertPlaceholder.add(new Label("Ho ricevuto la serie di parametri: "));
            for (String key : parametersMap.keySet()) {
                String testo = key + ": ";
                Object value = parametersMap.get(key);
                if (value instanceof List) {
                    for (Object stringa : (List) value) {
                        testo += stringa + SPAZIO;
                    }// end of for cycle
                }// end of if cycle
                alertPlaceholder.add(new Label(testo.trim()));
            }// end of for cycle
        } else {
            alertPlaceholder.add(new Label("Non è arrivato nessun gruppo di parametri"));
        }// end of if/else cycle
    }// end of method


    /**
     * Corpo centrale della vista <br>
     * Placeholder (obbligatorio) <br>
     */
    protected void creaBodyLayout() {
        HorizontalLayout layout = new HorizontalLayout();

        Button alfa = new Button("Espandi");
        alfa.getElement().setAttribute("theme", "primary");
        alfa.setIcon(new Icon(VaadinIcon.DATABASE));
        layout.add(alfa);

        Button beta = new Button("Aggiungi");
        beta.getElement().setAttribute("theme", "secondaty");
        beta.setIcon(new Icon(VaadinIcon.CHECK));
        layout.add(beta);

        Button gamma = new Button("Interrompi");
        gamma.getElement().setAttribute("theme", "error");
        gamma.setIcon(new Icon(VaadinIcon.DEL));
        layout.add(gamma);
        bodyPlaceholder.add(layout);
    }// end of method

}// end of class