package it.algos.vaadflow.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static it.algos.vaadflow.application.FlowCost.SPAZIO;
import static it.algos.vaadflow.application.FlowCost.UNDERSCORE;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 06-apr-2020
 * Time: 16:43
 */
@Service
@Slf4j
public class AWebService extends AbstractService {

    /**
     * Tag aggiunto prima del titoloWiki (leggibile) della pagina per costruire il 'domain' completo
     */
    public final static String TAG_WIKI = "https://it.wikipedia.org/wiki/";

    private final static String TAG_INIZIALE = "https://";

    //--codifica dei caratteri
    public static String INPUT = "UTF8";


    /**
     * Crea la connessione di tipo GET
     */
    public URLConnection getURLConnection(String domain) throws Exception {
        URLConnection urlConn = null;

        if (domain != null && domain.length() > 0) {
            urlConn = new URL(domain).openConnection();
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; it-it) AppleWebKit/418.9 (KHTML, like Gecko) Safari/419.3");
        }// end of if cycle

        return urlConn;
    } // fine del metodo


    /**
     * Request di tipo GET
     */
    public String getUrlRequest(URLConnection urlConn) throws Exception {
        String risposta;
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader readBuffer;
        StringBuilder textBuffer = new StringBuilder();
        String stringa;

        input = urlConn.getInputStream();
        inputReader = new InputStreamReader(input, INPUT);

        // read the request
        readBuffer = new BufferedReader(inputReader);
        while ((stringa = readBuffer.readLine()) != null) {
            textBuffer.append(stringa);
        }// fine del blocco while

        //--close all
        readBuffer.close();
        inputReader.close();
        input.close();

        risposta = textBuffer.toString();

        return risposta;
    } // fine del metodo


    /**
     * Request di tipo GET
     * Accetta SOLO un domain (indirizzo) completo
     *
     * @param indirizzoWeb completo
     *
     * @return risposta grezza
     */
    public String leggeWeb(String indirizzoWeb) {
        String risposta = "";
        URLConnection urlConn;
        String tag = TAG_INIZIALE;

        try { // prova ad eseguire il codice
            String indirizzoWebCompleto = indirizzoWeb.startsWith(tag) ? indirizzoWeb : tag + indirizzoWeb;
            urlConn = getURLConnection(indirizzoWebCompleto);
            risposta = getUrlRequest(urlConn);
        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
        }// fine del blocco try-catch

        return risposta;
    } // fine del metodo


    /**
     * Request di tipo GET
     * Accetta SOLO un indirizzo di una pagina wiki
     *
     * @param indirizzoWiki
     *
     * @return risposta grezza
     */
    public String leggeSorgenteWiki(String indirizzoWikiGrezzo) {
        String indirizzoWikiElaborato = indirizzoWikiGrezzo.replaceAll(SPAZIO, UNDERSCORE);
        return leggeWeb(TAG_WIKI + indirizzoWikiElaborato);
    } // fine del metodo

}// end of class
