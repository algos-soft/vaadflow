package it.algos.vaadflow;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import it.algos.vaadflow.service.AArrayService;
import it.algos.vaadflow.service.ADateService;
import it.algos.vaadflow.service.ATextService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: ven, 08-dic-2017
 * Time: 10:28
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("array")
@DisplayName("Test sul service di utility per gli arry")
public class AArrayServiceTest extends ATest {


    private final static String[] stringArray = {"primo", "secondo", "quarto", "quinto", "1Ad", "terzo", "a10"};

    private final static ArrayList<String> stringList = new ArrayList(Arrays.asList(stringArray));

    private final static Object[] objArray = {new Label("Alfa"), new Button()};

    private final static ArrayList<Object> objList = new ArrayList(Arrays.asList(objArray));

    private final static Long[] longArray = {234L, 85L, 151099L, 123500L, 3L, 456772L};

    private final static ArrayList<Long> longList = new ArrayList(Arrays.asList(longArray));

    private List<String> prevista;

    private List<String> ottenuta;


    @InjectMocks
    private AArrayService service;


    @InjectMocks
    private ATextService textService;

    @InjectMocks
    private ADateService dateService;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        service.text = textService;
        service.date = dateService;
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Controlla la validità dell'array
     * Deve essitere (not null)
     * Deve avere degli elementi (size > 0)
     * Il primo elemento deve essere valido
     *
     * @param array (List) in ingresso da controllare
     *
     * @return vero se l'array soddisfa le condizioni previste
     */
    @Test
    public void isValid() {
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(stringList);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid((ArrayList) null);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid(new ArrayList());
        assertEquals(previstoBooleano, ottenutoBooleano);

        String[] stringArray = {"", "secondo", "quarto", "quinto", "1Ad", "terzo", "a10"};
        ArrayList<String> lista = new ArrayList(Arrays.asList(stringArray));
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(lista);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = true;
        ottenutoBooleano = service.isValid(objList);
        assertEquals(previstoBooleano, ottenutoBooleano);

        Object[] objArray = {null, new Button()};
        ArrayList<Object> objList = new ArrayList(Arrays.asList(objArray));
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(objList);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla la validità dell'array
     * Deve essitere (not null)
     * Deve avere degli elementi (length > 0)
     * Il primo elemento deve essere una stringa valida
     *
     * @param array (String[]) in ingresso da controllare
     *
     * @return vero se l'array soddisfa le condizioni previste
     */
    @Test
    public void isValid2() {
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(stringArray);
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid((String[]) null);
        assertEquals(previstoBooleano, ottenutoBooleano);

        String[] stringArray = {"", "secondo", "quarto", "quinto", "1Ad", "terzo", "a10"};
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(stringArray);
        assertEquals(previstoBooleano, ottenutoBooleano);

        String[] stringArray2 = {};
        previstoBooleano = false;
        ottenutoBooleano = service.isValid(stringArray2);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test


    @Test
    public void contains() {
        previstoBooleano = true;
        ottenutoBooleano = stringList.contains("secondo");
        assertEquals(previstoBooleano, ottenutoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = stringList.contains("pippoz");
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Aggiunge un elemento ad una List (di per se immutabile)
     * Deve esistere (not null)
     *
     * @param arrayIn (List) ingresso da incrementare
     *
     * @return la lista aumentata di un elemento
     */
    @Test
    public void add() {
        List<Object> objListOttenuta;
        Object newObj = new Object();

        int previstoInt = objList.size() + 1;
        objListOttenuta = service.add(objList, newObj);
        assertNotNull(objListOttenuta);
        int ottenutoInt = objListOttenuta.size();
        assertEquals(previstoInt, ottenutoInt);
    }// end of single test


    /**
     * Costruisce una stringa con i singoli valori divisi da un pipe
     * <p>
     *
     * @param array lista di valori
     *
     * @return stringa con i singoli valori divisi da un separatore
     */
    @Test
    public void toStringaPipe() {
        previsto = "primo|secondo|quarto|quinto|1Ad|terzo|a10";
        ottenuto = service.toStringaPipe(stringList);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
    }// end of single test


    /**
     * Costruisce una stringa con i singoli valori divisi da un separatore
     * <p>
     *
     * @param array lista di valori
     * @param sep   carattere separatore
     *
     * @return stringa con i singoli valori divisi da un separatore
     */
    @Test
    public void toStringa() {
        previsto = "primo|secondo|quarto|quinto|1Ad|terzo|a10";
        ottenuto = service.toStringa(stringList, "|");
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        previsto = "primo,secondo,quarto,quinto,1Ad,terzo,a10";
        ottenuto = service.toStringa(stringList, ",");
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
    }// end of single test


    /**
     * Numero di cicli
     *
     * @param totale da dividere
     * @param blocco divisore
     *
     * @return numero di cicli
     */
    @Test
    public void numCicli() {
        previstoIntero = 5;
        ottenutoIntero = service.numCicli(50, 10);
        assertNotNull(ottenuto);
        assertEquals(previstoIntero, ottenutoIntero);

        previstoIntero = 6;
        ottenutoIntero = service.numCicli(51, 10);
        assertNotNull(ottenuto);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    /**
     * Estra un subset dalla lista
     *
     * @param listatTotale  da suddividere
     * @param dimBlocco     di suddivisione
     * @param cicloCorrente attuale
     *
     * @return sublista corrente del ciclo
     */
    @Test
    public void estraeSublista() {
        List listaOttenuta;
        List listaPrevista;

        String[] subStringa = {"primo", "secondo"};
        listaPrevista = Arrays.asList(subStringa);
        listaOttenuta = service.estraeSublista(stringList, 2, 1);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista, listaOttenuta);

        String[] subStringa2 = {"quinto", "1Ad", "terzo"};
        listaPrevista = Arrays.asList(subStringa2);
        listaOttenuta = service.estraeSublista(stringList, 3, 2);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista, listaOttenuta);

        listaOttenuta = service.estraeSublista(stringList, 2, 0);
        assertNull(listaOttenuta);

        listaOttenuta = service.estraeSublista(null, 2, 1);
        assertNull(listaOttenuta);

        listaOttenuta = service.estraeSublista(stringList, 9, 1);
        assertNotNull(listaOttenuta);
        assertEquals(stringList, listaOttenuta);
    }// end of single test


    /**
     * Estra un subset dalla lista
     *
     * @param listatTotale  da suddividere
     * @param dimBlocco     di suddivisione
     * @param cicloCorrente attuale
     *
     * @return sublista corrente del ciclo
     */
    @Test
    public void estraeSublistaLong() {
        previsto = "234L,85L,151099L,123500L,3L,456772L";
        List<Long> listaOttenuta;
        List<Long> listaPrevista;

        Long[] subLong = {234L, 85L};
        listaPrevista = Arrays.asList(subLong);
        listaOttenuta = service.estraeSublistaLong(longList, 2, 1);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista, listaOttenuta);

        Long[] subLong2 = {123500L, 3L, 456772L};
        listaPrevista = Arrays.asList(subLong2);
        listaOttenuta = service.estraeSublistaLong(longList, 3, 2);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista, listaOttenuta);

    }// end of single test


    /**
     * Differenza tra due array
     *
     * @param primo   array
     * @param secondo array
     *
     * @return differenza
     */
    @Test
    public void differenza() {
        ArrayList listaUno = new ArrayList();
        ArrayList listaDue = new ArrayList();
        ArrayList listaUnoCopia;
        ArrayList delta;
        int dim = 10000;
        long inizio;
        long fine;

        for (int k = 0; k < dim; k++) {
            listaUno.add(k * 3);
            listaDue.add(k * 2);
        }// end of for cycle

        //--prima prova
        inizio = System.currentTimeMillis();
        delta = service.differenza(listaUno, listaDue);
        fine = System.currentTimeMillis();
        System.out.println("prima prova");
        System.out.println("Size listaUno: " + listaUno.size());
        System.out.println("Size listaDue: " + listaDue.size());
        System.out.println("Size delta: " + delta.size());
        System.out.println("Differenza in msec: " + textService.format(fine - inizio));


        //--seconda prova
        inizio = System.currentTimeMillis();
        delta = (ArrayList) listaUno.clone();
        delta.removeAll(listaDue);
        fine = System.currentTimeMillis();
        System.out.println("");
        System.out.println("seconda prova - removeAll");
        System.out.println("Size listaUno: " + listaUno.size());
        System.out.println("Size listaDue: " + listaDue.size());
        System.out.println("Size delta: " + delta.size());
        System.out.println("Differenza in msec: " + textService.format(fine - inizio));


        //--terza prova
        inizio = System.currentTimeMillis();
        delta = (ArrayList) listaUno.clone();
        delta = (ArrayList) listaUno.stream()
                .filter(e -> !listaDue.contains(e))
                .collect(Collectors.toList());
        fine = System.currentTimeMillis();
        System.out.println("");
        System.out.println("terza prova - stream");
        System.out.println("Size listaUno: " + listaUno.size());
        System.out.println("Size listaDue: " + listaDue.size());
        System.out.println("Size delta: " + delta.size());
        System.out.println("Differenza in msec: " + textService.format(fine - inizio));

        //--quarta prova
        inizio = System.currentTimeMillis();
        listaUno.removeAll(listaDue);
        fine = System.currentTimeMillis();
        System.out.println("");
        System.out.println("quarta prova");
        System.out.println("Size listaUno: " + listaUno.size());
        System.out.println("Size listaDue: " + listaDue.size());
        System.out.println("Size delta: " + delta.size());
        System.out.println("Differenza in msec: " + textService.format(fine - inizio));
    }// end of single test


    /**
     * Differenza tra due array
     *
     * @param primo   array
     * @param secondo array
     *
     * @return differenza
     */
    @Test
    public void differenza2() {
        ArrayList listaUno = new ArrayList();
        ArrayList listaDue = new ArrayList();
        ArrayList listaUnoCopia;
        ArrayList delta;
        int dim = 10000;
        long inizio;
        long fine;

        for (int k = 0; k < dim; k++) {
            listaUno.add(k);
        }// end of for cycle
        listaDue.add(345);
        listaDue.add(4781);

        //--prima prova
        inizio = System.currentTimeMillis();
        delta = service.differenza(listaUno, listaDue);
        fine = System.currentTimeMillis();
        System.out.println("");
        System.out.println("tanti meno pochi - differenza grande");
        System.out.println("Size listaUno: " + listaUno.size());
        System.out.println("Size listaDue: " + listaDue.size());
        System.out.println("Size delta: " + delta.size());
        System.out.println("Differenza in msec: " + textService.format(fine - inizio));

        //--seconda prova
        listaDue = new ArrayList();
        for (int k = 0; k < dim; k++) {
            listaDue.add(k);
        }// end of for cycle
        listaDue.remove(345);
        listaDue.remove(4781);
        inizio = System.currentTimeMillis();
        delta = service.differenza(listaUno, listaDue);
        fine = System.currentTimeMillis();
        System.out.println("");
        System.out.println("tanti meno tanti - differenza piccola");
        System.out.println("Size listaUno: " + listaUno.size());
        System.out.println("Size listaDue: " + listaDue.size());
        System.out.println("Size delta: " + delta.size());
        System.out.println("Differenza in msec: " + textService.format(fine - inizio));


        //--terza prova
        listaDue = new ArrayList();
        for (int k = 0; k < dim; k++) {
            listaDue.add(k);
        }// end of for cycle
        listaDue.remove(345);
        listaDue.remove(4781);

        listaUnoCopia = (ArrayList) listaUno.clone();

        listaUnoCopia.removeAll(listaDue);
        inizio = System.currentTimeMillis();
//        delta = service.differenza(listaUno, listaDue);
        fine = System.currentTimeMillis();
    }// end of single test


    /**
     * Differenza tra due array
     *
     * @param primo   array
     * @param secondo array
     *
     * @return differenza
     */
//    @Test
    public void differenza3() {
        ArrayList listaUnoOriginale = new ArrayList();
        ArrayList listaDue = new ArrayList();
        ArrayList listaUnoCopia = null;
        int dim = 300000;
        long inizio = System.currentTimeMillis();

        for (int k = 0; k < dim; k++) {
            listaUnoOriginale.add(k * 2);
        }// end of for cycle

        for (int k = 0; k < dim; k++) {
            listaDue.add(k * 3);
        }// end of for cycle

        inizio = System.currentTimeMillis();
        listaUnoCopia = (ArrayList) listaUnoOriginale.clone();
        System.out.println("");
        System.out.println("Tempo per il clone: " + textService.format(System.currentTimeMillis() - inizio));
        inizio = System.currentTimeMillis();
        ottenutoBooleano = listaUnoCopia.retainAll(listaDue);
        System.out.println("Differenza3 in msec: " + textService.format(System.currentTimeMillis() - inizio));

        inizio = System.currentTimeMillis();
        service.differenza(listaUnoOriginale, listaDue);
        System.out.println("Differenza4 in msec: " + textService.format(System.currentTimeMillis() - inizio));
    }// end of single test


    /**
     * Differenza tra due array
     *
     * @param primo   array
     * @param secondo array
     *
     * @return differenza
     */
//    @Test
    public void differenza5() {
        ArrayList<Long> listaUno = new ArrayList();
        ArrayList<Long> listaDue = new ArrayList();
        HashSet<String> hashA = new HashSet<String>();
        HashSet<String> hashB = new HashSet<String>();
        int dim = 300000;
        long inizio = System.currentTimeMillis();

        for (int k = 0; k < dim; k++) {
            listaUno.add(new Long((k * 2)));
        }// end of for cycle

        for (int k = 0; k < dim; k++) {
            listaDue.add(new Long((k * 3)));
        }// end of for cycle

        inizio = System.currentTimeMillis();
        service.delta(listaUno, listaDue);
        System.out.println("");
        System.out.println("Size listaUno: " + listaUno.size());
        System.out.println("Size listaDue: " + listaDue.size());
        System.out.println("Differenza5 -HashSet- in msec: " + textService.format(System.currentTimeMillis() - inizio));
        System.out.println("");

    }// end of single test


    /**
     * Differenza tra due array
     *
     * @param primo   array
     * @param secondo array
     *
     * @return differenza
     */
    @Test
    public void delta() {
        Long[] alfa = {23L, 34L, 87L, 89L, 90L};
        Long[] beta = {23L, 34L};
        Long[] gamma = {87L, 89L, 90L};
        List<Long> listaUno = new ArrayList(Arrays.asList(alfa));
        List<Long> listaDue = new ArrayList(Arrays.asList(beta));
        List<Long> listaPrevista = new ArrayList(Arrays.asList(gamma));
        List<Long> listaOttenuta;

        listaOttenuta = service.delta(listaUno, listaDue);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista, listaOttenuta);

    }// end of single test


    /**
     * Differenza tra due array
     *
     * @param primo   array
     * @param secondo array
     *
     * @return differenza
     */
    @Test
    public void delta2() {
        Long[] alfa = {23L, 34L, 87L, 89L, 90L};
        Long[] beta = {17L, 564L};
        Long[] gamma = {23L, 34L, 87L, 89L, 90L};
        List<Long> listaUno = new ArrayList(Arrays.asList(alfa));
        List<Long> listaDue = new ArrayList(Arrays.asList(beta));
        List<Long> listaPrevista = new ArrayList(Arrays.asList(gamma));
        List<Long> listaOttenuta;

        listaOttenuta = service.delta(listaUno, listaDue);
        assertNotNull(listaOttenuta);
        assertEquals(listaPrevista.size(), listaOttenuta.size());

    }// end of single test


    /**
     * Differenza tra due array
     *
     * @param primo   array
     * @param secondo array
     *
     * @return differenza
     */
    @Test
    public void delta3() {
        ArrayList<Long> listaUno = new ArrayList();
        ArrayList<Long> listaDue = new ArrayList();
        HashSet<String> hashA = new HashSet<String>();
        HashSet<String> hashB = new HashSet<String>();
        int dim = 30000;
        long inizio = System.currentTimeMillis();

        for (int k = 0; k < dim; k++) {
            listaUno.add(new Long((k * 2)));
        }// end of for cycle

        for (int k = 0; k < dim; k++) {
            listaDue.add(new Long((k * 3)));
        }// end of for cycle

        List<Long> listaOttenuta = service.delta(listaUno, listaDue);
        assertNotNull(listaOttenuta);
        System.out.println("");
        System.out.println("Size listaUno: " + listaUno.size());
        System.out.println("Size listaDue: " + listaDue.size());
        System.out.println("Size listaOttenuta: " + listaOttenuta.size());
        System.out.println("Delta in msec: " + textService.format(System.currentTimeMillis() - inizio));
        System.out.println("");

    }// end of single tes


    /**
     * Differenza tra due array
     *
     * @param primo   array
     * @param secondo array
     *
     * @return differenza
     */
    @Test
    public void delta4() {
        ArrayList<Long> listaUno = new ArrayList();
        ArrayList<Long> listaDue = new ArrayList();
        HashSet<String> hashA = new HashSet<String>();
        HashSet<String> hashB = new HashSet<String>();
        int dim = 300000;
        long inizio = System.currentTimeMillis();

        for (int k = 0; k < dim; k++) {
            listaUno.add(new Long((k * 2)));
        }// end of for cycle

        for (int k = 0; k < dim; k++) {
            listaDue.add(new Long((k * 3)));
        }// end of for cycle

        List<Long> listaOttenuta = service.delta(listaUno, listaDue);
        assertNotNull(listaOttenuta);
        System.out.println("");
        System.out.println("Size listaUno: " + textService.format(listaUno.size()));
        System.out.println("Size listaDue: " + textService.format(listaDue.size()));
        System.out.println("Size listaOttenuta: " + textService.format(listaOttenuta.size()));
        System.out.println("Delta in msec: " + textService.format(System.currentTimeMillis() - inizio));
        System.out.println("");

    }// end of single tes


}// end of class
