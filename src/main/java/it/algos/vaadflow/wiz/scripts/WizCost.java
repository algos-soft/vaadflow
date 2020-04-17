package it.algos.vaadflow.wiz.scripts;

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

    //--flag per stampare info di debug
    public static final boolean FLAG_DEBUG_WIZ = true;

    public static final String VAAD_FLOW_PROJECT = "vaadflow";

    public static final String PATH_VAAD_FLOW_DIR_STANDARD = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow/";

    public static final String PATH_PROJECTS_DIR_STANDARD = "/Users/gac/Documents/IdeaProjects/";

    public static final String DIR_FLOW = "it/algos/vaadflow/";

    public static final String NORMAL_WIDTH = "9em";

    public static final String NORMAL_HEIGHT = "3em";

    public static final H3 TITOLO_NUOVO_PROGETTO = new H3("Nuovo progetto");

    public static final H3 TITOLO_MODIFICA_PROGETTO = new H3("Modifica progetto esistente");

    public static final String DIR_DOC = "documentation/";

    public static final String DIR_LINKS = "links/";

    public static final String DIR_SNIPPETS = "snippets/";

    public static final String FILE_READ = "README";

    public static final String FILE_BANNER = "banner";

    public static final String FILE_GIT = ".gitignore";

    public static final String FILE_PROPERTIES = "properties";

    public static final String FILE_PROPERTIES_DEST = "application.properties";

    public static final String SOURCE_SUFFIX = ".txt";

    //--parte dal livello del progetto
    //--contiene java e resources di ogni progetto
    public static final String DIR_MAIN = "src/main/";

    //--parte dal livello main
    //--contiene i moduli, di solito due (vaadFlow e vaadTest)
    public static final String DIR_JAVA = DIR_MAIN + "java/it/algos/";

    //--parte dal livello modulo base
    //--valida SOLO per progetto vaadFlow
    public static final String DIR_SOURCES = DIR_JAVA + SLASH + VAAD_FLOW_PROJECT + "wizard/sources/";

    //--parte dal livello main
    //--contiene application.properties (di solito)
    public static final String DIR_RESOURCES = DIR_MAIN + "resources/";

    //--parte dal livello main
    //--contiene images, src, styles (di solito)
    public static final String DIR_FRONTEND = DIR_RESOURCES + "META_INF/resources/frontend/";


}// end of class
