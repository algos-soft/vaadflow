package it.algos.vaadtest.dialoghi;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import it.algos.vaadflow.service.AAvvisoService;
import it.algos.vaadflow.service.ADialogoService;
import it.algos.vaadflow.ui.MainLayout14;
import it.algos.vaadflow.ui.dialog.AvvisoConferma;
import it.algos.vaadflow.ui.dialog.AvvisoSemplice;
import it.algos.vaadflow.ui.dialog.DialogoConferma;
import it.algos.vaadflow.ui.dialog.polymer.bean.DialogoDueBeanPolymer;
import it.algos.vaadflow.ui.dialog.polymer.bean.DialogoUnoBeanPolymer;
import it.algos.vaadflow.ui.dialog.polymer.bean.DialogoZeroBeanPolymer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 15-ago-2019
 * Time: 17:28
 */
@Route(value = "dialoghi", layout = MainLayout14.class)
public class ProvaDialoghi extends VerticalLayout {

    @Autowired
    protected ApplicationContext appContext;

    @Autowired
    protected ADialogoService dialogoService;

    @Autowired
    protected AAvvisoService avvisoService;

    Annulla azioneAnnulla = new Annulla();

    Conferma azioneConferma = new Conferma();


    public ProvaDialoghi() {
        this.setMargin(false);
        this.setSpacing(false);
        add(new Label("Prova di alcuni dialoghi"));
        add(new Label(""));
        avvisoSempliceOld();
        avvisoSempliceOldBean();
        avvisoUnBottone();
        dialogoConferma();
        add(new Label("Nuovi via @route"));
        nuovoDialogoRoute();
        nuovoDialogoRouteUnBottone();
        nuovoDialogoRouteDueBottoni();
        add(new Label("Nuovi via getBean()"));
        nuovoDialogoBean();
        nuovoDialogoBeanUnBottone();
        nuovoDialogoBeanDueBottoni();
        nuovissimoAvviso();
    }// end of constructor


    /**
     * Questo NON ha bottoni e si chiude con un click fuori dal dialogo oppure con Escape
     */
    public void avvisoSempliceOld() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Avviso semplice old: ");
        Button button = new Button("Avviso");
        button.addClickListener(e -> {
            new AvvisoSemplice().open();
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);
    }// end of method


    /**
     * Questo NON ha bottoni e si chiude con un click fuori dal dialogo oppure con Escape
     * Costruito con getBean(). Meglio.
     */
    public void avvisoSempliceOldBean() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Avviso semplice old costruito con getBean(): ");
        Button button = new Button("Avviso");
        button.addClickListener(e -> {
            appContext.getBean(AvvisoSemplice.class).open();
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);
    }// end of method


    /**
     * Questo ha un bottone per chiuderlo
     */
    public void avvisoUnBottone() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Avviso old con bottone di conferma avvenuta lettura: ");
        Button button = new Button("Avviso");
        button.addClickListener(e -> {
            appContext.getBean(AvvisoConferma.class).open();
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);
    }// end of method


    /**
     * Questo ha DUE bottone per annullare e confermare
     */
    public void dialogoConferma() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Dialogo old con due bottoni: ");
        Button button = new Button("Dialogo");
        button.addClickListener(e -> {
            appContext.getBean(DialogoConferma.class).open();
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);
    }// end of method


    /**
     * Nuovo dialogo, completamente riscritto
     */
    public void nuovoDialogoRoute() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Avviso route senza bottoni: ");
        Button button = new Button("Avviso");
        button.addClickListener(e -> {
            dialogoService.dialogoZero(getUI(), "La tua iscrizione al turno è stata effettuata correttamente.");
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);
    }// end of method


    /**
     * Nuovo dialogo, completamente riscritto con un bottone
     */
    public void nuovoDialogoRouteUnBottone() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Nuovo dialogo route con un bottone: ");
        Button button = new Button("Dialogo bottone");
        button.addClickListener(e -> {
            dialogoService.dialogoUno(getUI(), "Delete", "Registrazione effettuata correttamente.");
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);
    }// end of method


    /**
     * Nuovo dialogo, completamente riscritto con due bottoni
     */
    public void nuovoDialogoRouteDueBottoni() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Nuovo dialogo route con due bottoni: ");
        Button button = new Button("Dialogo doppio");
        button.addClickListener(e -> {
            dialogoService.dialogoDue(getUI(), "Unsaved changes", "Do you want to save or discard your changes before navigating away?");
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);

    }// end of method


    /**
     * Nuovo dialogo, completamente riscritto
     */
    public void nuovoDialogoBean() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Avviso bean() senza bottoni: ");
        Button button = new Button("Avviso");
        button.addClickListener(e -> {
            DialogoZeroBeanPolymer dialogo = appContext.getBean(DialogoZeroBeanPolymer.class, "La tua iscrizione al turno è stata effettuata correttamente");
            this.add(dialogo);
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);
    }// end of method


    /**
     * Nuovo dialogo, completamente riscritto con un bottone
     */
    public void nuovoDialogoBeanUnBottone() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Nuovo dialogo bean() con un bottone: ");
        Button button = new Button("Dialogo bottone");
        button.addClickListener(e -> {
            DialogoUnoBeanPolymer dialogo = appContext.getBean(DialogoUnoBeanPolymer.class,
                    "Delete",
                    "Registrazione effettuata correttamente",
                    azioneConferma);
            this.add(dialogo);
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);
    }// end of method


    /**
     * Nuovo dialogo, completamente riscritto con due bottoni
     */
    public void nuovoDialogoBeanDueBottoni() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);

        Label label = new Label("Nuovo dialogo bean() con due bottoni: ");
        Button button = new Button("Dialogo bottone");
        button.addClickListener(e -> {
            DialogoDueBeanPolymer dialogo =
                    appContext.getBean(
                            DialogoDueBeanPolymer.class,
                            "Delete",
                            "Registrazione effettuata correttamente",
                            azioneConferma, azioneAnnulla);
            this.add(dialogo);
        });//end of lambda expressions and anonymous inner class
        layout.add(label, button);
        this.add(layout);
    }// end of method


    /**
     * Nuovissimo avviso/dialogo, completamente riscritto con un bottone
     */
    public void nuovissimoAvviso() {
        HorizontalLayout layout = new HorizontalLayout();
        this.setSpacing(true);
        String testo = "Sempre lo stesso testo";

        Label label = new Label("Nuovissimo avviso bean() con un bottone: ");
        Button button = new Button("Bottone info");
        button.addClickListener(e -> {
            avvisoService.info(this, testo);
        });//end of lambda expressions and anonymous inner class
        Button button2 = new Button("Bottone warn");
        button2.addClickListener(e -> {
            avvisoService.warn(this, testo);
        });//end of lambda expressions and anonymous inner class
        Button button3 = new Button("Bottone error");
        button3.addClickListener(e -> {
            avvisoService.error(this, testo);
        });//end of lambda expressions and anonymous inner class

        layout.add(label, button, button2, button3);
        this.add(layout);
    }// end of method


    public void annulla() {
        add(new Label("Dialogo anullato"));
    }// end of method


    public void conferma() {
        add(new Label("Dialogo confermato"));
    }// end of method


    private class Annulla implements Runnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            annulla();
        }// end of method

    }// end of class


    private class Conferma implements Runnable {

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            conferma();
        }// end of method

    }// end of class

}// end of class
