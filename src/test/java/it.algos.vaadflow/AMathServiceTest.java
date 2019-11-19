package it.algos.vaadflow;

import it.algos.vaadflow.service.AMathService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mar, 19-nov-2019
 * Time: 20:46
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test sugli operatori matematici")
public class AMathServiceTest extends ATest {


    @InjectMocks
    private AMathService service;

    private int dividendo;

    private int divisore;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Divisione tra due numeri double <br>
     *
     * @param dividendo quantità da dividere
     * @param divisore  quantità che divide
     *
     * @return valore risultante di tipo double
     */
    @Test
    public void divisione() {
        previstoDouble = 3.5;
        double dividendo = 7;
        double divisore = 2;

        ottenutoDouble = service.divisione(dividendo, divisore);
        assertEquals(previstoDouble, ottenutoDouble);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Divisione tra due numeri interi <br>
     *
     * @param dividendo quantità da dividere
     * @param divisore  quantità che divide
     *
     * @return valore risultante di tipo double
     */
    @Test
    public void divisione2() {
        previstoDouble = 3.5;
        dividendo = 7;
        divisore = 2;
        ottenutoDouble = service.divisione(dividendo, divisore);
        assertEquals(previstoDouble, ottenutoDouble);

        previstoDouble = 18;
        dividendo = 450;
        divisore = 25;
        ottenutoDouble = service.divisione(dividendo, divisore);
        assertEquals(previstoDouble, ottenutoDouble);

        previstoIntero = 18;
        dividendo = 450;
        divisore = 25;
        ottenutoIntero = (int) service.divisione(dividendo, divisore);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    /**
     * Percentuale tra due numeri interi <br>
     *
     * @param dividendo quantità da dividere
     * @param divisore  quantità che divide
     *
     * @return valore risultante di tipo double
     */
    @Test
    public void percentuale() {
        dividendo = 8;
        divisore = 200;
        previstoDouble = 4;
        ottenutoDouble = service.percentuale(dividendo, divisore);
        assertNotNull(ottenutoDouble);
        assertEquals(previstoDouble, ottenutoDouble);
    }// end of single test


    /**
     * Percentuale tra due numeri interi <br>
     *
     * @param dividendo quantità da dividere
     * @param divisore  quantità che divide
     *
     * @return valore risultante di tipo String
     */
    @Test
    public void percentualeDueDecimali() {
        dividendo = 8;
        divisore = 200;
        previsto = "4,00%";
        ottenuto = service.percentualeDueDecimali(dividendo, divisore);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        dividendo = 8;
        divisore = 19;
        previsto = "42,11%";
        ottenuto = service.percentualeDueDecimali(dividendo, divisore);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        dividendo = 8;
        divisore = 190;
        previsto = "4,21%";
        ottenuto = service.percentualeDueDecimali(dividendo, divisore);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        dividendo = 8;
        divisore = 1900;
        previsto = "0,42%";
        ottenuto = service.percentualeDueDecimali(dividendo, divisore);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        dividendo = 8;
        divisore = 19000;
        previsto = "0,04%";
        ottenuto = service.percentualeDueDecimali(dividendo, divisore);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);
    }// end of single test

    }// end of class
