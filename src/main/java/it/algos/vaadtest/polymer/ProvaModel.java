package it.algos.vaadtest.polymer;

import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 05-ago-2019
 * Time: 08:14
 */
public interface ProvaModel extends TemplateModel {


    String getTerzonome();

    void setTerzonome(String terzonome);

    String getQuartonome();

    void setQuartonome(String quartonome);

    String getConferma();

    void setConferma(String conferma);

}
