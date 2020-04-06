package it.algos.vaadflow.importa;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.service.AWebService;
import it.algos.vaadflow.wrapper.WrapDueStringhe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public class ImportWiki {

    public static String KEY_SIGLA = "sigla";

    public static String KEY_NOME = "nome";

    public static String KEY_REGIONE = "regione";

    public static String PAGINA = "ISO 3166-2:IT";

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    public AWebService aWebService;


    public ImportWiki() {
    }// end of constructor


    /**
     * Sorgente completo di una pagina web <br>
     *
     * @param paginaWiki
     *
     * @return sorgente
     */
    public String getSorgente(String paginaWiki) {
        String sorgente = null;

        sorgente = aWebService.leggeSorgenteWiki(paginaWiki);

        return sorgente;
    }// end of method


    /**
     * Import da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con due stringhe ognuno
     */
    public List<WrapDueStringhe> estraeLista(String pagina, String[] titoliTable) {
        List<WrapDueStringhe> listaWrap = null;
        LinkedHashMap<String, LinkedHashMap<String, String>> mappaGenerale = null;
        LinkedHashMap<String, String> mappaSingola;
        String tagUno = titoliTable[0];
        String tagDue = titoliTable[1];
        WrapDueStringhe wrapGrezzo;

        mappaGenerale = aWebService.getMappaTableWiki(pagina, titoliTable);
        if (mappaGenerale != null && mappaGenerale.size() > 0) {
            listaWrap = new ArrayList<>();
            for (String elemento : mappaGenerale.keySet()) {
                mappaSingola = mappaGenerale.get(elemento);
                wrapGrezzo = new WrapDueStringhe(mappaSingola.get(tagUno), mappaSingola.get(tagDue));
                listaWrap.add(wrapGrezzo);
            }// end of for cycle
        }// end of if cycle

        return listaWrap;
    }// end of method


    /**
     * Import delle regioni da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con tre stringhe ognuno (sigla, nome, regione)
     */
    public String elaboraCodice(String testoGrezzo) {
        String testoValido = VUOTA;

        testoValido = testoGrezzo.substring(3, 5);
        return testoValido;
    }// end of method


    /**
     * Import delle regioni da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con tre stringhe ognuno (sigla, nome, regione)
     */
    public List<WrapDueStringhe> regioni() {
        List<WrapDueStringhe> listaWrap = null;
        List<WrapDueStringhe> listaWrapGrezzo = null;
        WrapDueStringhe wrapValido;
        String tagCodice = "Codice";
        String tagRegioni = "Regioni";
        String[] titoli = new String[]{tagCodice, tagRegioni};
        String prima;
        String seconda;

        listaWrapGrezzo = estraeLista(PAGINA, titoli);
        if (listaWrapGrezzo != null && listaWrapGrezzo.size() > 0) {
            listaWrap = new ArrayList<>();
            for (WrapDueStringhe wrap : listaWrapGrezzo) {
                prima = wrap.getPrima();
                seconda = wrap.getSeconda();
                prima = elaboraCodice(prima);
                seconda = elaboraCodice(seconda);
                wrapValido = new WrapDueStringhe(prima, seconda);
                listaWrap.add(wrapValido);
            }// end of for cycle
        }// end of if cycle

        return listaWrap;
    }// end of method


    /**
     * Import delle provincie da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con tre stringhe ognuno (sigla, nome, regione)
     */
    public List<HashMap<String, String>> province() {
        List<HashMap<String, String>> lista = null;
        HashMap<String, String> mappa;
        String templateProvince;

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
