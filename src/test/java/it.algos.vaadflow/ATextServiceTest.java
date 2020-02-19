package it.algos.vaadflow;


import com.vaadin.flow.component.html.Label;
import it.algos.vaadflow.service.AArrayService;
import it.algos.vaadflow.service.ATextService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: gio, 07-dic-2017
 * Time: 14:23
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("text")
@DisplayName("Test sul service di elaborazione stringhe")
public class ATextServiceTest extends ATest {


    @InjectMocks
    public AArrayService array;

    //    private String sorgente = "";
//    private String previsto = "";
//    private String ottenuto = "";
//    private boolean previstoBooleano;
//    private boolean ottenutoBooleano;
    private String tag = "";

    private String oldTag = "";

    private String newTag = "";

    private int pos;

    @InjectMocks
    private ATextService service;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(array);
        service.array = array;
        service.array.text = service;
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Null-safe, short-circuit evaluation.
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa è vuota o nulla
     */
    @Test
    @DisplayName("Controllo la stringa nulla o vuota")
    public void isEmpty() {
        System.out.println("");
        System.out.println("Controllo la stringa nulla o vuota");

        sorgente = "Modifica scheda";
        previstoBooleano = false;
        ottenutoBooleano = service.isEmpty(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa vuota", sorgente, ottenutoBooleano);

        sorgente = "";
        previstoBooleano = true;
        ottenutoBooleano = service.isEmpty(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa vuota", sorgente, ottenutoBooleano);

        sorgente = null;
        previstoBooleano = true;
        ottenutoBooleano = service.isEmpty(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa vuota", sorgente, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Null-safe, short-circuit evaluation.
     *
     * @param stringa in ingresso da controllare
     *
     * @return vero se la stringa esiste è non è vuota
     */
    @Test
    @DisplayName("Controllo la stringa valida")
    public void isValid() {
        System.out.println("");
        System.out.println("Controllo la stringa valida");

        sorgente = "Modifica scheda";
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa valida", sorgente, ottenutoBooleano);

        sorgente = "";
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa valida", sorgente, ottenutoBooleano);

        sorgente = null;
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(sorgente);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Stringa valida", sorgente, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla che sia una stringa e che sia valida.
     *
     * @param obj in ingresso da controllare
     *
     * @return vero se la stringa esiste è non è vuota
     */
    @Test
    @DisplayName("Controllo l'oggetto valido")
    public void isValidObj() {
        System.out.println("");
        System.out.println("Controllo l'oggetto valido");

        Label label = new Label();
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(label);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Oggetto valido", label.getText(), ottenutoBooleano);

        Object objString = "";
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(label);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Oggetto valido", objString.toString(), ottenutoBooleano);

        Object objStringFull = "a";
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(objStringFull);
        assertEquals(previstoBooleano, ottenutoBooleano);
        print("Oggetto valido", objStringFull.toString(), ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Forza il primo carattere della stringa al carattere maiuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testo in ingresso
     *
     * @return test formattato in uscita
     */
    @Test
    @DisplayName("Prima maiuscola")
    public void primaMaiuscola() {
        System.out.println("");
        System.out.println("Prima maiuscola");

        sorgente = "TUTTO MAIUSCOLO ";
        previsto = "TUTTO MAIUSCOLO";
        ottenuto = service.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima maiuscola", sorgente, ottenuto);

        sorgente = " tutto minuscolo";
        previsto = "Tutto minuscolo";
        ottenuto = service.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima maiuscola", sorgente, ottenuto);

        sorgente = " afRodiSiacHo ";
        previsto = "AfRodiSiacHo";
        ottenuto = service.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima maiuscola", sorgente, ottenuto);

        previsto = "";
        ottenuto = service.primaMaiuscola(null);
        assertEquals(previsto, ottenuto);
        print("Prima maiuscola", sorgente, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Forza il primo carattere della stringa al carattere minuscolo
     * <p>
     * Se la stringa è nulla, ritorna un nullo
     * Se la stringa è vuota, ritorna una stringa vuota
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testo in ingresso
     *
     * @return test formattato in uscita
     */
    @Test
    @DisplayName("Prima minuscola")
    public void primaMinuscola() {
        System.out.println("");
        System.out.println("Prima minuscola");

        sorgente = "tutto minuscolo ";
        previsto = "tutto minuscolo";
        ottenuto = service.primaMinuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima minuscola", sorgente, ottenuto);

        sorgente = " TUTTO MAIUSCOLO";
        previsto = "tUTTO MAIUSCOLO";
        ottenuto = service.primaMinuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima minuscola", sorgente, ottenuto);

        sorgente = " AfRodiSiacHo ";
        previsto = "afRodiSiacHo";
        ottenuto = service.primaMinuscola(sorgente);
        assertEquals(previsto, ottenuto);
        print("Prima minuscola", sorgente, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Elimina dal testo il tagIniziale, se esiste
     * <p>
     * Esegue solo se il testo è valido
     * Se tagIniziale è vuoto, restituisce il testo
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn     ingresso
     * @param tagIniziale da eliminare
     *
     * @return test ridotto in uscita
     */
    @Test
    @DisplayName("Elimina il tag iniziale")
    public void levaTesta() {
        System.out.println("");
        System.out.println("Elimina il tag iniziale");

        sorgente = "Non Levare questo inizio ";
        tag = "Non";
        previsto = "Levare questo inizio";
        ottenuto = service.levaTesta(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questo inizio ";
        tag = "";
        previsto = "Non Levare questo inizio";
        ottenuto = service.levaTesta(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questo inizio ";
        tag = "NonEsisteQuestoTag";
        previsto = "Non Levare questo inizio";
        ottenuto = service.levaTesta(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Elimina dal testo il tagFinale, se esiste
     * <p>
     * Esegue solo se il testo è valido
     * Se tagIniziale è vuoto, restituisce il testo
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn   ingresso
     * @param tagFinale da eliminare
     *
     * @return test ridotto in uscita
     */
    @Test
    @DisplayName("Elimina il tag finale")
    public void levaCoda() {
        System.out.println("");
        System.out.println("Elimina il tag finale");

        sorgente = " Levare questa fine Non ";
        tag = "Non";
        previsto = "Levare questa fine";
        ottenuto = service.levaCoda(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "";
        previsto = "Non Levare questa fine";
        ottenuto = service.levaCoda(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "NonEsisteQuestoTag";
        previsto = "Non Levare questa fine";
        ottenuto = service.levaCoda(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva tag", sorgente + SEP3 + tag, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Elimina il testo da tagFinale in poi
     * <p>
     * Esegue solo se il testo è valido
     * Se tagInterrompi è vuoto, restituisce il testo
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn   ingresso
     * @param tagInterrompi da dove inizia il testo da eliminare
     *
     * @return test ridotto in uscita
     */
    @Test
    @DisplayName("Elimina dal tag finale in poi")
    public void levaCodaDa() {
        System.out.println("");
        System.out.println("Elimina dal tag finale in poi");

        sorgente = " Levare questa fine Non ";
        tag = "N";
        previsto = "Levare questa fine";
        ottenuto = service.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva da tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "";
        previsto = "Non Levare questa fine";
        ottenuto = service.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva da tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "questa";
        previsto = "Non Levare";
        ottenuto = service.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva da tag", sorgente + SEP3 + tag, ottenuto);

        sorgente = "Non Levare questa fine ";
        tag = "NonEsisteQuestoTag";
        previsto = "Non Levare questa fine";
        ottenuto = service.levaCodaDa(sorgente, tag);
        assertEquals(previsto, ottenuto);
        print("Leva da tag", sorgente + SEP3 + tag, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Sostituisce nel testo tutte le occorrenze di oldTag con newTag.
     * Esegue solo se il testo è valido
     * Esegue solo se il oldTag è valido
     * newTag può essere vuoto (per cancellare le occorrenze di oldTag)
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso da elaborare
     * @param oldTag  da sostituire
     * @param newTag  da inserire
     *
     * @return testo modificato
     */
    @Test
    @DisplayName("Sostituisce tutte le occorrenze")
    public void sostituisce() {
        System.out.println("");
        System.out.println("Sostituisce tutte le occorrenze");

        sorgente = "Devo sostituire tutte le t con p";
        oldTag = "t";
        newTag = "p";
        previsto = "Devo sospipuire puppe le p con p";
        ottenuto = service.sostituisce(sorgente, oldTag, newTag);
        assertEquals(previsto, ottenuto);
        print("Sostituisce", sorgente + SEP3 + oldTag + SEP3 + newTag, ottenuto);

        sorgente = "Devo sostituire oldTagtutte le oldTag con newTag";
        oldTag = "oldTag";
        newTag = "newTag";
        previsto = "Devo sostituire newTagtutte le newTag con newTag";
        ottenuto = service.sostituisce(sorgente, oldTag, newTag);
        assertEquals(previsto, ottenuto);
        print("Sostituisce", sorgente + SEP3 + oldTag + SEP3 + newTag, ottenuto);

        sorgente = "Devo oldTag cancoldTagellare tutte le oldTag";
        oldTag = "oldTag";
        newTag = "";
        previsto = "Devo cancellare tutte le";
        ottenuto = service.sostituisce(sorgente, oldTag, newTag);
        assertEquals(previsto, ottenuto);
        print("Sostituisce", sorgente + SEP3 + oldTag + SEP3 + newTag, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Inserisce nel testo alla posizione indicata
     * Esegue solo se il testo è valido
     * Esegue solo se il newTag è valido
     * Elimina spazi vuoti iniziali e finali
     *
     * @param testoIn ingresso da elaborare
     * @param newTag  da inserire
     * @param pos     di inserimento
     *
     * @return testo modificato
     */
    @Test
    @DisplayName("Inserisce il tag")
    public void inserisce() {
        System.out.println("");
        System.out.println("Inserisce il tag");

        tag = "tutte";
        newTag = "pippoz";
        sorgente = "Devo oldTag cancellare tutte le oldTag";
        previsto = "Devo oldTag cancellare " + newTag + "tutte le oldTag";

        pos = sorgente.indexOf(tag);
        ottenuto = service.inserisce(sorgente, newTag, pos);
        assertEquals(previsto, ottenuto);
        print("Inserisce tag", sorgente + SEP3 + newTag + SEP3 + tag, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla se il testo contiene uno elemento di una lista di tag
     *
     * @param testoIn   ingresso
     * @param listaTags lista di tag da controllare
     *
     * @return vero se ne contiene uno o più di uno
     */
    @Test
    public void isContiene() {
        ArrayList tags = new ArrayList<>();
        tags.add("@AIScript(sovrascrivibile = false)");
        tags.add("@AIScript(sovrascrivibile=false)");
        tags.add("@AIScript(sovrascrivibile= false)");
        tags.add("@AIScript(sovrascrivibile =false)");

        sorgente = "Non penso si possa fare @AIScript( sovrascrivibile = false) tutto quello previsto";
        previstoBooleano = false;
        ottenutoBooleano = service.isContiene(sorgente, tags);
        assertEquals(previstoBooleano, ottenutoBooleano);

        sorgente = "Non penso si possa fare @AIScript(sovrascrivibile = false) tutto quello previsto";
        previstoBooleano = true;
        ottenutoBooleano = service.isContiene(sorgente, tags);
        assertEquals(previstoBooleano, ottenutoBooleano);

        sorgente = "Non penso si possa fare @AIScript(sovrascrivibile= false) tutto quello previsto";
        previstoBooleano = true;
        ottenutoBooleano = service.isContiene(sorgente, tags);
        assertEquals(previstoBooleano, ottenutoBooleano);

        sorgente = "Non penso si possa fare @AIScript(sovrascrivibile =false) tutto quello previsto";
        previstoBooleano = true;
        ottenutoBooleano = service.isContiene(sorgente, tags);
        assertEquals(previstoBooleano, ottenutoBooleano);

        sorgente = "Non penso si possa fare @AIScript(sovrascrivibile=false) tutto quello previsto";
        previstoBooleano = true;
        ottenutoBooleano = service.isContiene(sorgente, tags);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test


    @Test
    public void estrae() {
        String tagIni = "{";
        String tagEnd = "}";
        previsto = "non comprende ancora";
        sorgente = "Questo testo {non comprende ancora} e manca la fine";
        ottenuto = service.estrae(sorgente, tagIni, tagEnd);
        assertEquals(previsto, ottenuto);

        tag = "\"";
        sorgente = "Questo testo \"non comprende ancora\" e manca la fine";
        ottenuto = service.estrae(sorgente, tag);
        assertEquals(previsto, ottenuto);
    }// end of single test


    @Test
    public void format() {
        sorgenteIntero = 5;
        previsto = "5";
        ottenuto = service.format(sorgenteIntero);
        assertEquals(previsto, ottenuto);

        sorgenteIntero = 857;
        previsto = "857";
        ottenuto = service.format(sorgenteIntero);
        assertEquals(previsto, ottenuto);

        sorgenteIntero = 1534;
        previsto = "1.534";
        ottenuto = service.format(sorgenteIntero);
        assertEquals(previsto, ottenuto);

        String[] stringArray = {"ordine", "code", "secondo", "ultimo"};
        previsto = "4";
        ottenuto = service.format(stringArray);
        assertEquals(previsto, ottenuto);

        List sorgenteList = Arrays.asList(stringArray);
        ottenuto = service.format(sorgenteList);
        assertEquals(previsto, ottenuto);


        sorgenteIntero = 4;
        previsto = "04";
        ottenuto = service.format2(sorgenteIntero);
        assertEquals(previsto, ottenuto);

        sorgenteIntero = 4;
        previsto = "004";
        ottenuto = service.format3(sorgenteIntero);
        assertEquals(previsto, ottenuto);

        sorgenteIntero = 35;
        previsto = "035";
        ottenuto = service.format3(sorgenteIntero);
        assertEquals(previsto, ottenuto);

        sorgenteIntero = 135;
        previsto = "135";
        ottenuto = service.format3(sorgenteIntero);
        assertEquals(previsto, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Confronta due numeri.
     *
     * @param primo   numero
     * @param secondo numero
     *
     * @return :
     * 1 if secondo should be before primo
     * -1 if primo should be before secondo
     * 0 otherwise
     */
    @Test
    public void compareInt() {
        int primo;
        int secondo;

        primo = 17;
        secondo = 14;
        previstoIntero = 1; //vanno invertiti
        ottenutoIntero = text.compareInt(primo, secondo);
        assertEquals(previstoIntero, ottenutoIntero);

        primo = 345;
        secondo = 1860;
        previstoIntero = -1; //restano come sono
        ottenutoIntero = text.compareInt(primo, secondo);
        assertEquals(previstoIntero, ottenutoIntero);


        primo = 13;
        secondo = 13;
        previstoIntero = 0; //sono uguali
        ottenutoIntero = text.compareInt(primo, secondo);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Confronta due stringhe.
     *
     * @param prima   stringa
     * @param seconda stringa
     *
     * @return :
     * 1 if seconda should be before prima
     * -1 if prima should be before seconda
     * 0 otherwise
     */
    @Test
    public void compareStr() {
        String prima;
        String seconda;

        prima = "gamma";
        seconda = "alfa";
        previstoIntero = 1; //vanno invertiti
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);

        prima = "Gamma";
        seconda = "alfa";
        previstoIntero = -1; //restano come sono
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);

        prima = "gamma";
        seconda = "Alfa";
        previstoIntero = 1; //vanno invertiti
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);

        prima = "Gamma";
        seconda = "Alfa";
        previstoIntero = 1; //vanno invertiti
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);

        prima = "Pomeriggio";
        seconda = "Martedi";
        previstoIntero = 1; //vanno invertiti
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);

        prima = "domani";
        seconda = "pomeriggio";
        previstoIntero = -1; //restano come sono
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);

        prima = "dopo";
        seconda = "dopo";
        previstoIntero = 0; //sono uguali
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);

        prima = "KakizakiMuku Kakizaki";
        seconda = "HoustonBrant Houston";
        previstoIntero = 1; //vanno invertiti
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);

        prima = "d'AsburgoElena d'Asburgo";
        seconda = "VenezianoAntonio Veneziano (poeta)";
        previstoIntero = 1; //vanno invertiti
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);

        prima = "MitscherlichEilhard Mitscherlich";
        seconda = "SchottHeinrich Wilhelm Schott";
        previstoIntero = -1; //restano come sono
        ottenutoIntero = text.compareStr(prima, seconda);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    /**
     * Formattazione di un intero con un decimale.
     * <p>
     * Il numero è stato moltiplicato per 10 e memorizzato come intero <br>
     * Va diviso per 10 ed inserita la virgola <br>
     *
     * @param value da formattare (intero * 10)
     *
     * @return stringa formattata
     */
    @Test
    public void formatOneDecimal() {
        sorgenteIntero = 234;
        previsto = "23,4";

        ottenuto= service.formatOneDecimal(sorgenteIntero);
        assertEquals(previsto, ottenuto);
    }// end of single test

}// end of class
