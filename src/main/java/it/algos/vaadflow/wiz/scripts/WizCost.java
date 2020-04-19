package it.algos.vaadflow.wiz.scripts;

import com.vaadin.flow.component.html.H3;
import org.slf4j.Logger;

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

    public static final String VAADFLOW_PROJECT = "vaadflow/";

    public static final String VAADFLOW_NAME = "vaadflow";

    public static final String PATH_VAADFLOW_DIR_STANDARD = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow/";

    public static final String PATH_PROJECTS_DIR_STANDARD = "/Users/gac/Documents/IdeaProjects/";

    public static final String DIR_VAADFLOW = "it/algos/vaadflow/";

    public static final String NORMAL_WIDTH = "9em";

    public static final String NORMAL_HEIGHT = "3em";

    public static final H3 TITOLO_NUOVO_PROGETTO = new H3("Nuovo progetto");

    public static final H3 TITOLO_MODIFICA_PROGETTO = new H3("Modifica progetto esistente");

    public static final String DIR_DOC = "documentation/";

    public static final String DIR_LINKS = "links/";

    public static final String DIR_SNIPPETS = "snippets/";

    public static final String DIR_APPLICATION = "application/";

    public static final String DIR_MODULES = "modules/";

    public static final String APP_NAME = "application";

    public static final String FILE_READ = "README";

    public static final String FILE_COST = "Cost";

    public static final String FILE_BOOT = "Boot";

    public static final String FILE_VERS = "Vers";

    public static final String FILE_POM = "pom";

    public static final String FILE_BANNER = "banner";

    public static final String FILE_GIT = ".gitignore";

    public static final String FILE_PROPERTIES = "properties";

    public static final String FILE_PROPERTIES_DEST = "application.properties";

    public static final String TXT_SUFFIX = ".txt";

    public static final String XML_SUFFIX = ".xml";

    public static final String JAVA_SUFFIX = ".java";

    //--parte dal livello del progetto
    //--contiene java e resources di ogni progetto
    public static final String DIR_MAIN = "src/main/";

    //--parte dal livello main
    //--contiene i moduli, di solito due (vaadFlow e vaadTest)
    public static final String DIR_JAVA = DIR_MAIN + "java/it/algos/";

    //--parte dal livello modulo base
    //--valida SOLO per progetto vaadFlow
    public static final String DIR_VAADFLOW_SOURCES = DIR_JAVA + VAADFLOW_PROJECT + "wiz/sources/";

    //--parte dal livello main
    //--contiene application.properties (di solito)
    public static final String DIR_RESOURCES = DIR_MAIN + "resources/";

    //--parte dal livello main
    //--contiene images, src, styles (di solito)
    public static final String DIR_FRONTEND = DIR_RESOURCES + "META_INF/resources/frontend/";


    //--metodo statico invocato da WizDialog.regolazioniIniziali()
    public static void printInfo(Logger log) {
        if (FLAG_DEBUG_WIZ) {
            System.out.println("");
            System.out.println("********************");
            System.out.println("Costanti statiche");
            System.out.println("********************");
            System.out.println("PATH_VAADFLOW_DIR_STANDARD = " + PATH_VAADFLOW_DIR_STANDARD);
            System.out.println("PATH_PROJECTS_DIR_STANDARD = " + PATH_PROJECTS_DIR_STANDARD);
            System.out.println("DIR_MAIN = " + DIR_MAIN);
            System.out.println("DIR_JAVA = " + DIR_JAVA);
            System.out.println("DIR_VAADFLOW_SOURCES = " + DIR_VAADFLOW_SOURCES);
            System.out.println("DIR_FRONTEND = " + DIR_FRONTEND);
            System.out.println("");
        }// end of if cycle
    }// end of static method

}// end of class
