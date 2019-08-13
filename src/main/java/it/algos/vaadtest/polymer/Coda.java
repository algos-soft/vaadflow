package it.algos.vaadtest.polymer;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 10-ago-2019
 * Time: 08:38
 */


/**
 * Java wrapper of the polymer element `algos-coda`
 */
@Tag("algos-coda")
@HtmlImport("src/views/prova/algos-coda.html")
public class Coda extends PolymerTemplate<TemplateModel> {

    @Id("annulla")
    private Button annulla;

    @Id("conferma")
    private Button conferma;


    public void setAnnullaText(String annullaText) {
        annulla.setText(annullaText == null ? "" : annullaText);
    }// end of method


    public void setConfermaText(String confermaText) {
        conferma.setText(confermaText == null ? "" : confermaText);
    }// end of method


    public void setAnnullaDisabled(boolean annullaDisabled) {
        annulla.setEnabled(!annullaDisabled);
    }// end of method


    public void setConfermaDisabled(boolean confermaDisabled) {
        conferma.setEnabled(!confermaDisabled);
    }// end of method


    public Registration addAnnullalListener(ComponentEventListener<Coda.AnnullaEvent> listener) {
        return annulla.addClickListener(e -> listener.onComponentEvent(new Coda.AnnullaEvent(this, true)));
    }// end of method


    public Registration addConfermaListener(ComponentEventListener<ConfermaEvent> listener) {
        return conferma.addClickListener(e -> listener.onComponentEvent(new Coda.ConfermaEvent(this, true)));
    }// end of method


    public static class AnnullaEvent extends ComponentEvent<Coda> {

        public AnnullaEvent(Coda source, boolean fromClient) {
            super(source, fromClient);
        }// end of constructor

    }// end of method


    public static class ConfermaEvent extends ComponentEvent<Coda> {

        public ConfermaEvent(Coda source, boolean fromClient) {
            super(source, fromClient);
        }// end of constructor

    }// end of method


}// end of class
