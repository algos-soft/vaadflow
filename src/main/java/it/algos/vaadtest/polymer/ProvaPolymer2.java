package it.algos.vaadtest.polymer;

import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.*;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadtest.modules.beverage.Review;
import it.algos.vaadtest.modules.beverage.ReviewModel;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static it.algos.vaadtest.application.TestCost.TAG_ABC;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 05-ago-2019
 * Time: 08:04
 */
@Route(value = TAG_ABC +"2")
@Tag("prova-polymer2")
@HtmlImport("src/views/prova/prova-polymer.html")//questo non funziona perché il Tag (unico) è 'prova-polymer2'
@HtmlImport("src/views/prova/prova-polymer2.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Viewport("width=device-width")
public class ProvaPolymer2 extends PolymerTemplate<ReviewModel> {

//    @Id("title")
//    private H3 title;

//    @Id("first")
//    private TextField first;//solo campo vuoto
//
//    @Id("second")
//    private TextField second;//valore nel campo. Regolato dal Server e non modificabile
//
//    @Id("terzo")
//    private TextField terzo;//valore in una property separata passata tramite ModelloDati e non modificabile (quadre)
//
//    @Id("quarto")
//    private TextField quarto;//valore in una property separata passata tramite ModelloDati e modificabile (graffe)
//
//    @Id("buttons")
//    private FormButtonsBar buttons;
//
//    @Id("conferma1")
//    private Button conferma1;
//
//    @Id("conferma2")
//    private Button conferma2;
//
//    @Id("conferma3")
//    private Button conferma3;

//    @Id("email")
//    private TextField email;
//
//    @Id("password")
//    private PasswordField password;
//
//    @Id("role")
//    private ComboBox<String> role;


    public ProvaPolymer2() {
//        this.first.setValue("");
//        this.second.setValue("value");
//        this.terzo.setValue("terzo");
//        this.quarto.setValue("quarto");
//
//        conferma1 = new Button("Server");
//        conferma3.addClickListener(event -> conferma3());
        List<Review> listaElementi=new ArrayList<>();
        listaElementi.add(new Review(27,"primo",17, LocalTime.of(3,12).toString()));
        listaElementi.add(new Review(33,"secondo",2, LocalTime.of(14,05).toString()));
        listaElementi.add(new Review(98,"ultimo",124, LocalTime.NOON.toString()));
        getModel().setReviews(listaElementi);


        List<String> listaInizio=new ArrayList<>();
        listaInizio.add("10:30");
        listaInizio.add("10:45");
        listaInizio.add("11:00");
        listaInizio.add("11:15");
        listaInizio.add("11:30");

        getModel().setItems(listaInizio);
        getModel().setSelectedPlatformId("10:45");
    }

    @EventHandler
    public void change33(@EventData("element.value") ReviewModel betta) {
        int a = 87;
    }
    @EventHandler
    public void change(@EventData("element.value") String input) {
        String selectedPlatformId = this.getModel().getSelectedPlatformId();
        int a = 87;
    }

    @EventHandler
    public void change2(@ModelItem ReviewModel modello) {
//        Object alfa = modello.getItems();
//        Object beta = modello.getInput();
        int a = 87;
//        ProvaModel modello = getModel();
//        String alfa = modello.getTerzonome();
//        String beta = modello.getQuartonome();
//        int a = 87;
    }

    @EventHandler
    public void conferma(@ModelItem ProvaModel modello) {
        String alfa = modello.getTerzonome();
        String beta = modello.getQuartonome();
        int a = 87;
    }


    public void conferma3() {
//        ProvaModel modello = getModel();
//        String alfa = modello.getTerzonome();
//        String beta = modello.getQuartonome();
//        String delta = second.getValue();
//        Notification.show("Il valore di 'second' è: " + delta, 3000, Notification.Position.MIDDLE);
    }

    @EventHandler
    public void conferma4() {
//        ProvaModel modello = getModel();
//        String alfa = modello.getTerzonome();
//        String beta = modello.getQuartonome();
//        String delta = second.getValue();
//        Notification.show("Il valore di 'second' è: " + delta, 3000, Notification.Position.MIDDLE);
    }
    @EventHandler
    private void handleClick() {
        System.out.println("Received a handle click event");
    }

    @EventHandler
    private void handleClick2(@EventData("event.altKey") boolean altPressed,
                              @EventData("event.srcElement.tagName") String tag,
                              @EventData("event.offsetX") int offsetX,
                              @EventData("event.offsetY") int offsetY) {
        System.out.println("Event alt pressed: " + altPressed);
        System.out.println("Event tag: " + tag.toLowerCase(Locale.ITALIAN));
        System.out.println("Click position on element: [" + offsetX + ", "+ offsetY +"]");
    }

    @EventHandler
    private void handleClick3(@ModelItem ProvaModel modello) {
        System.out.println("Received a message: " + modello.getTerzonome());
    }
    @EventHandler
    private void handleClick4(@ModelItem Review modello) {
        System.out.println("Nome: " + ((Review)modello).getName());
        System.out.println("Count: " + ((Review)modello).getCount());
        System.out.println("Score: " + ((Review)modello).getScore());
        Object alfa= ((Review)modello).getInizio();
        System.out.println("Inizio: " + ((Review)modello).getInizio());
    }


    @EventHandler
    private void handleChange2(@ModelItem Review modello) {
        System.out.println("Nome: " + ((Review)modello).getName());
        System.out.println("Count: " + ((Review)modello).getCount());
        System.out.println("Score: " + ((Review)modello).getScore());
        Object alfa= ((Review)modello).getInizio();
        System.out.println("Inizio: " + ((Review)modello).getInizio());
        ReviewModel modelloX= (ReviewModel)getModel();
        Object alfetta= modelloX.getReviews();
        Object beretta= modelloX.getItems();
        int a=87;
    }



    @EventHandler
    public void handleChange(@RepeatIndex int itemIndex) {

        int a=87;

    }


    @EventHandler
    private void handleChange7() {
        ReviewModel modelloX= (ReviewModel)getModel();
        Object alfetta= modelloX.getReviews();
        Object beretta= modelloX.getItems();
        int a=87;
    }

    public static class Message {
        private String text;

        public Message() {
        }

        public Message(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public interface MessagesModel extends TemplateModel {
        void setMessages(List<Message> messages);
    }

    @EventHandler
    private void handleClick87(@ModelItem Message message) {
        System.out.println("Received a message: " + message.getText());
    }
}// end of class
