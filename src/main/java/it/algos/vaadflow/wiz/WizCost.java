package it.algos.vaadflow.wiz;

import com.vaadin.flow.component.html.H3;

import static it.algos.vaadflow.application.FlowCost.SLASH;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:09
 */
public class WizCost {

    public static final String VAAD_FLOW_PROJECT = "vaadflow";

    public static final String NAME_PROJECT_BASE_LONG = "it/algos/vaadflow";

    public static final String NORMAL_WIDTH = "9em";

    public static final String NORMAL_HEIGHT = "3em";

    public static final H3 TITOLO_NUOVO_PROGETTO = new H3("Nuovo progetto");

    public static final H3 TITOLO_MODIFICA_PROGETTO = new H3("Modifica progetto esistente");

    //--parte dal livello del progetto
    //--contiene java e resources di ogni progetto
    protected static final String DIR_MAIN = "/src/main";

    //--parte dal livello main
    //--contiene i moduli, di solito due (vaadFlow e vaadTest)
    protected static final String DIR_JAVA = DIR_MAIN + "/java/it/algos";

    //--parte dal livello modulo base
    //--valida SOLO per progetto vaadFlow
    public static final String DIR_SOURCES = DIR_JAVA + SLASH + VAAD_FLOW_PROJECT + "/wizard/sources";

}// end of class
