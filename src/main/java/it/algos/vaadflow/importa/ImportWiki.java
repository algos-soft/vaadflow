package it.algos.vaadflow.importa;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.service.AWebService;
import it.algos.vaadflow.wrapper.WrapDueStringhe;
import it.algos.vaadflow.wrapper.WrapTreStringhe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    public ATextService text;


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
    public List<WrapDueStringhe> estraeListaDue(String pagina, String[] titoliTable) {
        List<WrapDueStringhe> listaWrap = null;
        LinkedHashMap<String, LinkedHashMap<String, String>> mappaGenerale = null;
        LinkedHashMap<String, String> mappa;
        String tagUno = titoliTable[0];
        String tagDue = titoliTable[1];
        WrapDueStringhe wrapGrezzo;

        mappaGenerale = aWebService.getMappaTableWiki(pagina, titoliTable);
        if (mappaGenerale != null && mappaGenerale.size() > 0) {
            listaWrap = new ArrayList<>();
            for (String elemento : mappaGenerale.keySet()) {
                mappa = mappaGenerale.get(elemento);
                wrapGrezzo = new WrapDueStringhe(mappa.get(tagUno), mappa.get(tagDue));
                listaWrap.add(wrapGrezzo);
            }// end of for cycle
        }// end of if cycle

        return listaWrap;
    }// end of method


    /**
     * Import da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con due stringhe ognuno
     */
    public List<WrapTreStringhe> estraeListaTre(String pagina, String[] titoliTable) {
        List<WrapTreStringhe> listaWrap = null;
        LinkedHashMap<String, LinkedHashMap<String, String>> mappaGenerale = null;
        LinkedHashMap<String, String> mappa;
        String tagUno = titoliTable[0];
        String tagDue = titoliTable[1];
        String tagTre = titoliTable[2];
        WrapTreStringhe wrapGrezzo;

        mappaGenerale = aWebService.getMappaTableWiki(pagina, titoliTable);
        if (mappaGenerale != null && mappaGenerale.size() > 0) {
            listaWrap = new ArrayList<>();
            for (String elemento : mappaGenerale.keySet()) {
                mappa = mappaGenerale.get(elemento);
                wrapGrezzo = new WrapTreStringhe(mappa.get(tagUno), mappa.get(tagDue), mappa.get(tagTre));
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
        String testoValido = testoGrezzo.trim();
        String tagIni = "<code>";
        String tagEnd = "</code>";

        testoValido = text.levaTesta(testoValido, tagIni);
        testoValido = text.levaCoda(testoValido, tagEnd);
        testoValido = testoValido.substring(3);
        return testoValido;
    }// end of method


    /**
     * Import delle regioni da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con tre stringhe ognuno (sigla, nome, regione)
     */
    public String elaboraNome(String testoGrezzo) {
        String testoValido = testoGrezzo.trim();
        int posIni = 0;
        int posEnd = 0;

        posEnd = testoValido.lastIndexOf("</a>");
        if (posEnd > 0) {
            testoValido = testoValido.substring(0, posEnd);
        }// end of if cycle
        posIni = testoValido.lastIndexOf(">") + 1;
        testoValido = testoValido.substring(posIni);
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

        listaWrapGrezzo = estraeListaDue(PAGINA, titoli);
        if (listaWrapGrezzo != null && listaWrapGrezzo.size() > 0) {
            listaWrap = new ArrayList<>();
            for (WrapDueStringhe wrap : listaWrapGrezzo) {
                prima = wrap.getPrima();
                seconda = wrap.getSeconda();
                prima = elaboraCodice(prima);
                seconda = elaboraNome(seconda);
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
    public List<WrapTreStringhe> provinceBase(String pagina, String[] titoliTable) {
        List<WrapTreStringhe> listaWrap = null;
        List<WrapTreStringhe> listaWrapGrezzo = null;
        WrapTreStringhe wrapValido;
        String prima;
        String seconda;
        String terza;

        listaWrapGrezzo = estraeListaTre(PAGINA, titoliTable);
        if (listaWrapGrezzo != null && listaWrapGrezzo.size() > 0) {
            listaWrap = new ArrayList<>();
            for (WrapTreStringhe wrap : listaWrapGrezzo) {
                prima = wrap.getPrima();
                seconda = wrap.getSeconda();
                terza = wrap.getTerza();
                prima = elaboraCodice(prima);
                seconda = elaboraNome(seconda);
                terza = elaboraNome(terza);
                wrapValido = new WrapTreStringhe(prima, seconda, terza);
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
    public List<WrapTreStringhe> province() {
        List<WrapTreStringhe> listaWrap = new ArrayList<>();
        List<WrapTreStringhe> listaWrapCitta = null;
        List<WrapTreStringhe> listaWrapProvince = null;

        WrapTreStringhe wrapValido;
        String tagCodice = "Codice";
        String tagCitta = "Città metropolitane";
        String tagProvince = "Province";
        String tagRegioni = "Nella regione";
        String[] titoliCitta = new String[]{tagCodice, tagCitta, tagRegioni};
        String[] titoliProvince = new String[]{tagCodice, tagProvince, tagRegioni};

        listaWrapCitta = provinceBase(PAGINA, titoliCitta);
        listaWrapProvince = provinceBase(PAGINA, titoliProvince);
        listaWrap.addAll(listaWrapCitta);
        listaWrap.addAll(listaWrapProvince);
        return listaWrap;
    }// end of method


    /**
     * Import delle provincie da una pagina di wikipedia <br>
     *
     * @return lista di wrapper con tre stringhe ognuno (sigla, nome, regione)
     */
    public List<WrapTreStringhe> comune() {
        List<WrapTreStringhe> listaWrap = new ArrayList<>();
        List<WrapTreStringhe> listaWrapCitta = null;
        List<WrapTreStringhe> listaWrapProvince = null;

        WrapTreStringhe wrapValido;
        String tagCodice = "Codice";
        String tagCitta = "Città metropolitane";
        String tagProvince = "Province";
        String tagRegioni = "Nella regione";
        String[] titoliCitta = new String[]{tagCodice, tagCitta, tagRegioni};
        String[] titoliProvince = new String[]{tagCodice, tagProvince, tagRegioni};

        listaWrapCitta = provinceBase(PAGINA, titoliCitta);
        listaWrapProvince = provinceBase(PAGINA, titoliProvince);
        listaWrap.addAll(listaWrapCitta);
        listaWrap.addAll(listaWrapProvince);
        return listaWrap;
    }// end of method

}// end of class
