package it.algos.vaadtest.vaadinbindingdemo;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface BindingModel extends TemplateModel {
    void setHostProperty(String propertyValue);
    String getHostProperty();
}
