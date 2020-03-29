package it.algos.vaadtest.modules.beverage;

import com.vaadin.flow.templatemodel.AllowClientUpdates;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 16-feb-2020
 * Time: 14:31
 */
public interface ReviewModel extends TemplateModel {

    //    Object getItems();
//    Object getInput();
//    @AllowClientUpdates

    @AllowClientUpdates
    List<Review> getReviews();

    void setReviews(List<Review> reviews);

    String getDescription();
    void setDescription(String description);

    List<String> getItems();

    void setItems(List<String> items);

    String getSelectedPlatformId();

    void setSelectedPlatformId(String selectedPlatformId);

}// end of class
