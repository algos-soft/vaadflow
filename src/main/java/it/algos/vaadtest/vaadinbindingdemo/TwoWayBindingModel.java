package it.algos.vaadtest.vaadinbindingdemo;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface TwoWayBindingModel extends TemplateModel {

    void setName(String name);
    String getName();

    void setAccepted(Boolean accepted);
    Boolean getAccepted();

    void setSize(String size);
    String getSize();

}
