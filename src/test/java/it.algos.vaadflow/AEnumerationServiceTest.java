package it.algos.vaadflow;

import it.algos.vaadflow.service.AArrayService;
import it.algos.vaadflow.service.AEnumerationService;
import it.algos.vaadflow.service.ATextService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: Sun, 21-Jul-2019
 * Time: 21:46
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("enumeration")
@DisplayName("Test sulle enumeration Algos")
public class AEnumerationServiceTest extends ATest {

    @InjectMocks
    private AEnumerationService service;

    @InjectMocks
    private ATextService textService;

    @InjectMocks
    private AArrayService arrayService;


    @BeforeAll
    public void setUp() {
        super.setUpTest();
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(textService);
        MockitoAnnotations.initMocks(arrayService);
        service.text = textService;
        service.array = arrayService;
        arrayService.text = text;

        sorgente = "ordine,code,descrizione;code";
        String[] stringArray = {"ordine", "code", "descrizione"};
        previstoList = new ArrayList(Arrays.asList(stringArray));
    }// end of method


    /**
     * Restituisce le due parti del valore grezzo <br>
     *
     * @param rawValue dei valori ammessi seguita dal valore selezionato
     *
     * @return matrice delle due parti
     */
    @Test
    public void getParti() {
        String[] parti = service.getParti(sorgente);
        assertNotNull(parti);
        assertTrue(parti.length == 2);
    }// end of single test


    /**
     * Restituisce una stringa dei valori ammissibili <br>
     *
     * @param rawValue dei valori ammessi seguita dal valore selezionato
     *
     * @return stringa dei valori ammissibili
     */
    @Test
    public void getStringaValori() {
        previsto = "ordine,code,descrizione";
        ottenuto = service.getStringaValori(sorgente);
        assertEquals(ottenuto, previsto);
    }// end of single test


    /**
     * Restituisce la lista dei valori ammissibili <br>
     *
     * @param valueList dei valori ammessi seguita dal valore selezionato
     *
     * @return lista dei valori ammissibili
     */
    @Test
    public void getList() {
        ottenutoList = service.getList(sorgente);
        assertEquals(previstoList, ottenutoList);
    }// end of single test


    /**
     * Trasforma il contenuto del mongoDB nel valore selezionato, eliminando la lista dei valori possibili
     *
     * @param rawValue dei valori ammessi seguita dal valore selezionato
     *
     * @return valore selezionato
     */
    @Test
    public void convertToPresentation() {
        previsto = "code";
        ottenuto = service.convertToPresentation(sorgente);
        assertEquals(ottenuto, previsto);

        previsto = "descrizione";
        ottenuto = service.convertToPresentation("descrizione");
        assertEquals(ottenuto, previsto);

        previsto = "ordine,code";
        ottenuto = service.convertToPresentation("ordine,code");
        assertEquals(ottenuto, previsto);
    }// end of single test


}// end of class
