package it.algos.vaadflow.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mar, 13-ago-2019
 * Time: 10:50
 * <p>
 * Classe statica (astratta) per le variabili generali dell'applicazione <br>
 * Le variabili (static) sono uniche per tutta l'applicazione <br>
 * Il valore delle variabili è unico per tutta l'applicazione, ma può essere modificato <br>
 */
public class FlowVar {

    /**
     * Controlla se l'applicazione usa il login oppure no <br>
     * Se si usa il login, occorre la classe SecurityConfiguration <br>
     * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
     * Di defaul (per sicurezza) uguale a true <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
     */
    public static boolean usaSecurity = true;

    /**
     * Controlla se l'applicazione è multi-company oppure no <br>
     * Di defaul (per sicurezza) uguale a true <br>
     * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
     */
    public static boolean usaCompany = true;

    /**
     * Nome identificativo dell'applicazione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     */
    public static String projectName;

    /**
     * Versione dell'applicazione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     */
    public static double projectVersion;

    /**
     * Data della versione dell'applicazione <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     */
    public static LocalDate versionDate;

    /**
     * Eventuali informazioni aggiuntive da utilizzare nelle informazioni <br>
     * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
     */
    public static String projectNote;

    /**
     * Eventuali titolo della pagina <br>
     */
    public static String layoutTitle = "";


    public static List<Class> menuClazzList = new ArrayList<>();

}// end of class
