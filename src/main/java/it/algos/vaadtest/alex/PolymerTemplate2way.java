package it.algos.vaadtest.alex;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.ModelItem;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "demo2way")
@HtmlImport("src/views/prova/demo2way.html")
@Tag("tag-alex")
public class PolymerTemplate2way extends PolymerTemplate<PolymerModel> {

    public PolymerTemplate2way() {
        List<PolymerItem> items=new ArrayList<>();
        items.add(new PolymerItem("Alberto","lumo:edit","Roma","12:30"));
        items.add(new PolymerItem("Michele","lumo:error","Napoli","8:45"));
        items.add(new PolymerItem("Giuseppe","lumo:play","Bologna","17:00"));
        getModel().setItems(items);

        getModel().setTitle("Demo 2 way binding");

    }


    /**
     * Click sul bottone
     */
    @EventHandler
    private void submitItem(@ModelItem PolymerItem item) {
        getModel().setMessage("Submitted item: "+item.getName()+", "+item.getCity()+", "+item.getInizio());
    }


    /**
     * Modifica del valore del timePicker
     */
    @EventHandler
    private void handleChange(@ModelItem PolymerItem item) {
        getModel().setMessage("Submitted item: "+item.getName()+", "+item.getCity()+", "+item.getInizio());
    }


    @EventHandler
    public void submitForm() {
        PolymerModel model = getModel();
        List<PolymerItem> items = model.getItems();
        String msg="";
        for (PolymerItem item : items){
            String name=item.getName();
            String city = item.getCity();
            String inizio=item.getInizio();
            msg+="["+name+", "+city+", "+inizio+"] ";
        }

        String title= model.getTitle();
        getModel().setMessage("Submitted form: "+title+": "+msg);

    }



}
