package it.algos.vaadtest.alex;

import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.List;

public interface PolymerModel extends TemplateModel {

    String getTitle();
    void setTitle(String message);

    String getMessage();
    void setMessage(String message);

    List<PolymerItem> getItems();
    void setItems(List<PolymerItem> items);

}
