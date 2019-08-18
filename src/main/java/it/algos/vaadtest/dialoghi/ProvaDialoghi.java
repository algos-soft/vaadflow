package it.algos.vaadtest.dialoghi;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import it.algos.vaadflow.service.ADialogoService;
import it.algos.vaadflow.ui.dialog.AvvisoConferma;
import it.algos.vaadflow.ui.dialog.AvvisoSemplice;
import it.algos.vaadflow.ui.dialog.DialogoConferma;
import it.algos.vaadtest.AViewxx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.net.URLEncoder;
import java.util.Optional;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 15-ago-2019
 * Time: 17:28
 */
@Route(value = "dialoghi")
public class ProvaDialoghi extends VerticalLayout {

    @Autowired
    protected ApplicationContext appContext;
    @Autowired
    protected ADialogoService dialogoService;


    public ProvaDialoghi() {
        this.setMargin(false);
        this.setSpacing(false);
        add(new Label("Prova di alcuni dialoghi"));
        add(new Label(""));
        avvisoSemplice();
        avvisoSempliceBean();
        avvisoUnBottone();
        dialogoConferma();
        nuovoDialogo();
        nuovoDialogoDueBottoni();
    }// end of constructor


    /**
     * Questo NON ha bottoni e si chiude con un click fuori dal dialogo oppure con Escape
     */
    public void avvisoSemplice() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Avviso semplice: ");
        Button button = new Button("Avviso");
        button.addClickListener(e -> usaAvvisoSemplice());
        layout.add(label);
        layout.add(button);
        this.add(layout);
    }// end of method


    public void usaAvvisoSemplice() {
        AvvisoSemplice avviso = new AvvisoSemplice();
        avviso.open();
    }// end of method


    /**
     * Questo NON ha bottoni e si chiude con un click fuori dal dialogo oppure con Escape
     * Costruito con getBean(). Meglio.
     */
    public void avvisoSempliceBean() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Avviso semplice costruito con getBean(): ");
        Button button = new Button("Avviso");
        button.addClickListener(e -> usaAvvisoSempliceBean());
        layout.add(label);
        layout.add(button);
        this.add(layout);
    }// end of method


    /**
     * Costruito con getBean(). Meglio.
     */
    public void usaAvvisoSempliceBean() {
        AvvisoSemplice avviso = appContext.getBean(AvvisoSemplice.class);
        avviso.open();
    }// end of method


    /**
     * Questo ha un bottone per chiuderlo
     */
    public void avvisoUnBottone() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Avviso con bottone di conferma avvenuta lettura: ");
        Button button = new Button("Avviso");
        button.addClickListener(e -> usaAvvisoUnBottone());
        layout.add(label);
        layout.add(button);
        this.add(layout);
    }// end of method


    public void usaAvvisoUnBottone() {
        AvvisoConferma avviso = appContext.getBean(AvvisoConferma.class);
        avviso.open();
    }// end of method


    /**
     * Questo ha DUE bottone per annullare e confermare
     */
    public void dialogoConferma() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Dialogo con due bottoni: ");
        Button button = new Button("Dialogo");
        button.addClickListener(e -> usaDialogoConferma());
        layout.add(label);
        layout.add(button);
        this.add(layout);
    }// end of method


    public void usaDialogoConferma() {
        DialogoConferma avviso = appContext.getBean(DialogoConferma.class);
        avviso.open();
    }// end of method


    /**
     * Nuovo dialogo, completamente riscritto
     */
    public void nuovoDialogo() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Nuovo dialogo: ");
        Button button = new Button("Dialogo");
        button.addClickListener(e -> usaNuovoDialogo());
        layout.add(label);
        layout.add(button);
        this.add(layout);

    }// end of method


    public void usaNuovoDialogo() {
        dialogoService.dialogoUno(getUI(),"Sei sicuro di voler procedere abbastanza a lungo?");
    }// end of method


    /**
     * Nuovo dialogo, completamente riscritto con due bottoni
     */
    public void nuovoDialogoDueBottoni() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Nuovo dialogo con due bottoni: ");
        Button button = new Button("Dialogo doppio");
        button.addClickListener(e -> usaNuovoDialogoDueBottoni());
        layout.add(label);
        layout.add(button);
        this.add(layout);

    }// end of method


    public void usaNuovoDialogoDueBottoni() {
//        AViewxx dia= appContext.getBean(AViewxx.class);
//        dia.open();
        dialogoService.dialogoDue(getUI(),"Unsaved changes","Do you want to save or discard your changes before navigating away?");
    }// end of method


    public void conferma() {
        add(new Label("Dialogo confermato"));
    }// end of method

}// end of class
