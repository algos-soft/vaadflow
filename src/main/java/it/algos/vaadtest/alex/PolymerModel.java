package it.algos.vaadtest.alex;

import com.vaadin.flow.templatemodel.AllowClientUpdates;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.List;

public interface PolymerModel extends TemplateModel {

    @AllowClientUpdates
    List<PolymerItem> getItems();

    void setItems(List<PolymerItem> items);

//    String getSelectedPlatformId();
//
//    void setSelectedPlatformId(String selectedPlatformId);

}// end of class
