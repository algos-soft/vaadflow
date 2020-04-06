package it.algos.vaadflow.importa;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.VUOTA;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 06-apr-2020
 * Time: 15:25
 * Importazione delle provincie da Wikipedia <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class ImportProvincie {

    public static String KEY_SIGLA = "sigla";

    public static String KEY_NOME = "nome";

    public static String KEY_REGIONE = "regione";

    private static String PAGINA = "ISO 3166-2:IT";


    public ImportProvincie() {
    }// end of constructor


    /**
     * Sorgente completo di una pagina web <br>
     *
     * @param urlCompleto
     *
     * @return sorgente
     */
    public String getSorgente(String urlCompleto) {
        String sorgente=null;

        return sorgente;
    }// end of method


    /**
     * Import delle provincie da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con tre stringhe ognuno (sigla, nome, regione)
     */
    public List<HashMap<String, String>> esegue() {
        List<HashMap<String, String>> lista = null;
        HashMap<String, String> mappa;
        String templateProvincie;

        lista = new ArrayList<>();

        mappa = new HashMap<>();
        mappa.put(KEY_SIGLA, "CH");
        mappa.put(KEY_NOME, "Chieti");
        mappa.put(KEY_REGIONE, "Abruzzo");
        lista.add(mappa);

        mappa = new HashMap<>();
        mappa.put(KEY_SIGLA, "AQ");
        mappa.put(KEY_NOME, "L'Aquila");
        mappa.put(KEY_REGIONE, "Abruzzo");
        lista.add(mappa);

        mappa = new HashMap<>();
        mappa.put(KEY_SIGLA, "PE");
        mappa.put(KEY_NOME, "Pescara");
        mappa.put(KEY_REGIONE, "Abruzzo");
        lista.add(mappa);

//        regione = regioneService.findByNome("Abruzzo");
//        creaIfNotExist(regione, "CH", "Chieti");
//        creaIfNotExist(regione, "AQ", "L'Aquila");
//        creaIfNotExist(regione, "PE", "Pescara");
//        creaIfNotExist(regione, "TE", "Teramo");
//
//        regione = regioneService.findByNome("Lombardia");
//        creaIfNotExist(regione, "BG", "Bergamo");
//        creaIfNotExist(regione, "BS", "Brescia");
//        creaIfNotExist(regione, "CO", "Como");
//        creaIfNotExist(regione, "CR", "Cremona");
//        creaIfNotExist(regione, "LC", "Lecco");
//        creaIfNotExist(regione, "LO", "Lodi");
//        creaIfNotExist(regione, "MN", "Mantova");
//        creaIfNotExist(regione, "MI", "Milano");
//        creaIfNotExist(regione, "MB", "Monza e Brianza");
//        creaIfNotExist(regione, "PV", "Pavia");
//        creaIfNotExist(regione, "SO", "Sondrio");
//        creaIfNotExist(regione, "VA", "Varese");

        return lista;
    }// end of method

}// end of class
