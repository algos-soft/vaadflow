package it.algos.vaadflow;

import it.algos.vaadflow.enumeration.EATempo;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 23-nov-2019
 * Time: 13:47
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("pref")
@DisplayName("Test sulla enumeration di suddivisione dei tempi")
public class EATempoTest extends ATest {

    private static String ESEGUITO = "Eseguito";

    private EATempo tempoNessuno = EATempo.nessuno;

    private EATempo tempoMillisecondi = EATempo.millisecondi;

    private EATempo tempoSecondi = EATempo.secondi;

    private EATempo tempoMinuti = EATempo.minuti;

    private EATempo tempoOre = EATempo.ore;

    private EATempo tempoGiorni = EATempo.giorni;

    private long inizio;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }// end of method


    @Before
    public void setUpInizio() {
        inizio = System.currentTimeMillis();
    }// end of method


    @Test
    public void tempoNessuno() {
        setUpInizio();
        previstoIntero = 0;
        ottenutoIntero = tempoNessuno.get(inizio);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    @Test
    public void tempoMillisecondi() {
        setUpInizio();
        long previstoMillisecondiMassimi = 5;
        ottenutoLungo = tempoMillisecondi.getLong(inizio);
        assertTrue(ottenutoLungo < previstoMillisecondiMassimi);
        System.out.println(ESEGUITO + tempoMillisecondi.getTxt((int)ottenutoLungo));
    }// end of single test


    @Test
    public void tempoSecondi() {
        setUpInizio();
        inizio -= 7 * 1000;
        previstoIntero = 7;
        ottenutoIntero = tempoSecondi.get(inizio);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(ESEGUITO + tempoSecondi.getTxt(ottenutoIntero));
    }// end of single test


    @Test
    public void tempoMinuti() {
        setUpInizio();
        inizio -= 4 * 1000 * 60;
        previstoIntero = 4;
        ottenutoIntero = tempoMinuti.get(inizio);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(ESEGUITO + tempoMinuti.getTxt(ottenutoIntero));
    }// end of single test


    @Test
    public void tempoOre() {
        setUpInizio();
        inizio -= 3 * 1000 * 60 * 60;
        previstoIntero = 3;
        ottenutoIntero = tempoOre.get(inizio);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(ESEGUITO + tempoOre.getTxt(ottenutoIntero));
    }// end of single test


    @Test
    public void tempoGiorni() {
        setUpInizio();
        inizio -= 5 * 1000 * 60 * 60 * 24;
        previstoIntero = 5;
        ottenutoIntero = tempoGiorni.get(inizio);
        assertEquals(previstoIntero, ottenutoIntero);
        System.out.println(ESEGUITO + tempoGiorni.getTxt(ottenutoIntero));
    }// end of single test


}// end of class
