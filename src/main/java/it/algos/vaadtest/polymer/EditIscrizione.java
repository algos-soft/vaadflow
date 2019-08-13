package it.algos.vaadtest.polymer;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 09-ago-2019
 * Time: 19:26
 */
/**
 * Java wrapper of the polymer element `edit-iscrizione`
 */
@Tag("edit-iscrizione")
@HtmlImport("src/views/prova/edit-iscrizione.html")
public class EditIscrizione extends PolymerTemplate<EditIscrizione.IscrizioneModel>{
    private IscrizioneModel model = getModel();


    public EditIscrizione() {

        model.setColore("lightsalmon");
        model.setNomeDisabilitato(false);
        model.setFunzione("amb-mat");
        model.setIcona("vaadin:ambulance");
        model.setNome("Addaoo Pippo");
        model.setNoteOrariDisabilitati(false);
        model.setInizio("20:45");
//        info.setNoteUno("...");
        model.setFine("23:30");
    }// end of constructor


//    @EventHandler
//    private void onClick(@ModelItem("event.detail.userInfo") Info userInfo) {
//        System.err.println("contact : name = ");
//    }// end of method


    @EventHandler
    private void handleClickMilite() {
        System.err.println("milite uno ");
    }// end of method


    @EventHandler
    private void handleClickInizio() {
        String alfa = getModel().getInizio();
        System.err.println("inizio: " + alfa);
    }// end of method


    @EventHandler
    private void handleClickNote() {
        String alfa = getModel().getNote();
        System.err.println("note: " + alfa);
    }// end of method


    @EventHandler
    private void handleClickFine() {
        String alfa = getModel().getFine();
        System.err.println("fine: " + alfa);
    }// end of method

    public IscrizioneModel getModello() {
        return getModel();
    }// end of method

    public interface IscrizioneModel extends TemplateModel {


        boolean isNomeDisabilitato();


        void setNomeDisabilitato(boolean nomeDisabilitato);


        String getColore();


        void setColore(String colore);


        String getFunzione();


        void setFunzione(String funzione);


        String getIcona();


        void setIcona(String icona);

        String getNome();


        void setNome(String nome);


        boolean isNoteOrariDisabilitati();


        void setNoteOrariDisabilitati(boolean noteOrariDisabilitati);


        String getInizio();

        void setInizio(String inizio);


        String getNote();


        void setNote(String note);


        String getFine();


        void setFine(String fine);

    }// end of interface

}// end of class
