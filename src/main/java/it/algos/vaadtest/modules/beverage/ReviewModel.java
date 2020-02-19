package it.algos.vaadtest.modules.beverage;

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

    void setReviews(List<Review> reviews);

}// end of class
