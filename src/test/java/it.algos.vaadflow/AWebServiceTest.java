package it.algos.vaadflow;

import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.service.AWebService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.LinkedHashMap;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.VUOTA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 06-apr-2020
 * Time: 16:49
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AWebServiceTest {

    private static String PAGINA = "ISO 3166-2:IT";

    protected String sorgente = "";

    protected String previsto = "";

    protected String ottenuto = "";

    @InjectMocks
    AWebService aWebService;

    @InjectMocks
    ATextService text;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(aWebService);
        MockitoAnnotations.initMocks(text);
        aWebService.text = text;
    }// end of method


    /**
     * Request di tipo GET
     * Accetta SOLO un domain (indirizzo) completo
     *
     * @param indirizzoWeb completo
     *
     * @return risposta grezza
     */
    @Test
    public void legge() {
        sorgente = "https://it.wikipedia.org/wiki/ISO_3166-2:IT";
        ottenuto = aWebService.leggeWeb(sorgente);
        assertNotNull(ottenuto);
    }// end of single test


    /**
     * Request di tipo GET
     * Accetta SOLO un indirizzo di una pagina wiki
     *
     * @param indirizzoWiki
     *
     * @return risposta grezza
     */
    @Test
    public void leggeWiki() {
        sorgente = PAGINA;
        ottenuto = aWebService.leggeSorgenteWiki(sorgente);
        assertNotNull(ottenuto);
    }// end of single test


    /**
     * Costruisce una stringa di testo coi titoli della Table per individuarla nel sorgente della pagina
     *
     * @param titoliTable per individuare una table
     *
     * @return stringa di tag per regex
     */
    @Test
    public void costruisceTagTitoliTable() {
        String[] titoli;

        previsto = VUOTA;
        ottenuto = aWebService.costruisceTagTitoliTable(null);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        titoli = new String[]{"Codice"};
        previsto = VUOTA;
        previsto += "<table class=\"wikitable sortable\">";
        previsto += "<tbody><tr>";
        previsto += "<th>";
        previsto += "Codice";

        ottenuto = aWebService.costruisceTagTitoliTable(titoli);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        System.out.println("");
        System.out.println(ottenuto);


        titoli = new String[]{"Codice", "Province"};
        previsto = VUOTA;
        previsto += "<table class=\"wikitable sortable\">";
        previsto += "<tbody><tr>";
        previsto += "<th>";
        previsto += "Codice";
        previsto += "</th>";
        previsto += "<th>";
        previsto += "Province";

        ottenuto = aWebService.costruisceTagTitoliTable(titoli);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        System.out.println("");
        System.out.println(ottenuto);


        titoli = new String[]{"Codice", "Province", "Nella regione"};
        previsto = VUOTA;
        previsto += "<table class=\"wikitable sortable\">";
        previsto += "<tbody><tr>";
        previsto += "<th>";
        previsto += "Codice";
        previsto += "</th>";
        previsto += "<th>";
        previsto += "Province";
        previsto += "</th>";
        previsto += "<th>";
        previsto += "Nella regione";

        ottenuto = aWebService.costruisceTagTitoliTable(titoli);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
        System.out.println("");
        System.out.println(ottenuto);
    }// end of single test


    /**
     * Request di tipo GET
     * Accetta un array di titoli della table
     *
     * @param sorgentePagina
     * @param titoliTable    per individuarla
     *
     * @return testo della table
     */
    @Test
    public void estraeTableWiki() {
        sorgente = aWebService.leggeSorgenteWiki(PAGINA);

        String[] titoli = new String[]{"Codice", "Città metropolitane", "Nella regione"};

        ottenuto = aWebService.estraeTableWiki(sorgente, titoli);
        assertNotNull(ottenuto);
        System.out.println("");
        System.out.println(ottenuto);
    }// end of single test


    /**
     * Request di tipo GET
     * Accetta un array di titoli della table
     *
     * @param sorgentePagina
     * @param titoliTable    per individuarla
     *
     * @return testo della table
     */
    @Test
    public void estraeTableWiki2() {
        sorgente = aWebService.leggeSorgenteWiki(PAGINA);
        String[] titoli = new String[]{"Codice", "Regioni"};

        ottenuto = aWebService.estraeTableWiki(sorgente, titoli);
        assertNotNull(ottenuto);
        System.out.println("");
        System.out.println(ottenuto);
    }// end of single test


    /**
     * Request di tipo GET
     * Accetta un array di titoli della table
     *
     * @param indirizzoWikiGrezzo della pagina
     * @param titoliTable         per individuarla
     *
     * @return lista grezza di righe
     */
    @Test
    public void getRigheTableWiki() {
        List<String> lista = null;
        String[] titoli = new String[]{"Codice", "Regioni"};
        int previstoIntero = 20;

        lista = aWebService.getRigheTableWiki(PAGINA, titoli);
        assertNotNull(lista);
        assertEquals(previstoIntero, lista.size());
        System.out.println("");
        for (String riga : lista) {
            System.out.println("");
            System.out.println(riga);
        }// end of for cycle
    }// end of single test


    /**
     * Request di tipo GET
     * Accetta un array di titoli della table
     *
     * @param indirizzoWikiGrezzo della pagina
     * @param titoliTable         per individuarla
     *
     * @return lista grezza di righe
     */
    @Test
    public void getMappaTableWiki() {
        LinkedHashMap<String, LinkedHashMap<String, String>> mappaTable = null;
        String[] titoli = new String[]{"Codice", "Regioni"};
        int previstoIntero = 20;

        mappaTable = aWebService.getMappaTableWiki(PAGINA, titoli);
        assertNotNull(mappaTable);
        assertEquals(previstoIntero, mappaTable.size());
        System.out.println("");
        for (String key : mappaTable.keySet()) {
            System.out.println("");
            for (String key2 : mappaTable.get(key).keySet()) {
                System.out.println(mappaTable.get(key).get(key2));
            }// end of for cycle
        }// end of for cycle
    }// end of single test

}// end of class
