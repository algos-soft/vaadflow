package it.algos.vaadtest.polymer;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadtest.application.TestCost.TAG_ABC;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 09-ago-2019
 * Time: 07:52
 */
@Route(value = TAG_ABC + "3")
@Tag("prova-list")
@HtmlImport("src/views/prova/prova-list.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Viewport("width=device-width")
public class ProvaPolymer3 extends PolymerTemplate<ProvaPolymer3.ProvaModel> {

    private ProvaModel model = getModel();

//    @Id("first")
//    private EditIscrizione first;
//
//    @Id("second")
//    private EditIscrizione second;

    public ProvaPolymer3() {

        model.setColore("lightsalmon");
        model.setNomeDisabilitato(false);
        model.setFunzione("amb-mat");
        model.setIcona("vaadin:ambulance");
        model.setNome("Addaoo Pippo");
        model.setNoteOrariDisabilitati(false);
        model.setInizio("20:45");
//        info.setNoteUno("...");
        model.setFine("23:30");

        model.setSecondoVisibile(false);
    }// end of constructor


//    @EventHandler
//    private void onClick(@ModelItem("event.detail.userInfo") Info userInfo) {
//        System.err.println("contact : name = ");
//    }// end of method


    @EventHandler
    private void handleClickRegistra() {
//        String alfa = first.getModello().getInizio();
//        String beta = second.getModello().getInizio();
        System.err.println("milite uno ");
    }// end of method


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


    public interface ProvaModel extends TemplateModel {

        boolean isSecondoVisibile();

        void setSecondoVisibile(boolean visibile);

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
