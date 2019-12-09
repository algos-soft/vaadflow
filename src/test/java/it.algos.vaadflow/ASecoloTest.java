package it.algos.vaadflow;

import it.algos.vaadflow.modules.secolo.EASecolo;
import it.algos.vaadflow.modules.secolo.SecoloService;
import it.algos.vaadflow.service.AArrayService;
import it.algos.vaadflow.service.ATextService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 07-dic-2019
 * Time: 09:44
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("secoli")
@DisplayName("Test sulla collection 'secolo'")
class ASecoloTest extends ATest {

    @InjectMocks
    SecoloService service;

    @InjectMocks
    AArrayService array;

    @InjectMocks
    ATextService text;


    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(array);
        MockitoAnnotations.initMocks(text);
        array.text = text;
        service.array = array;
    }// end of method


    /**
     * Recupera l'enumeration dal titolo <br>
     *
     * @return enumeration trovata
     */
    @Test
    public void getSecolo() {
        EASecolo eaSecoloPrevisto = EASecolo.XIII;
        EASecolo eaSecoloOttenuto;
        sorgente = "XIII secolo";

        eaSecoloOttenuto = EASecolo.getSecolo(sorgente);
        assertNotNull(eaSecoloOttenuto);
        assertEquals(eaSecoloPrevisto, eaSecoloOttenuto);
    }// end of single test


    /**
     * Recupera l'ordine della enumeration dal titolo <br>
     *
     * @return ordinamento
     */
    @Test
    public void getOrder() {
        previstoIntero = 23;
        sorgente = "XIII secolo";

        ottenutoIntero = EASecolo.getOrder(sorgente);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    /**
     * Riordina una lista di valori <br>
     *
     * @return numero di elementi creato
     */
    @Test
    void riordina() {
        sorgenteList = Arrays.asList("XIII secolo", "VII secolo");
        previstoList = Arrays.asList("VII secolo", "XIII secolo");

        ottenutoList = service.riordina(sorgenteList);
        assertNotNull(ottenutoList);
        assertEquals(previstoList, ottenutoList);
    }// end of single test

    @Test
    void mostraTutti() {
        EASecolo[] matrice= EASecolo.values();
        assertNotNull(matrice);
        assertEquals(31, matrice.length);

        System.out.println();
        for (EASecolo secolo : matrice) {
            System.out.println(secolo.getTitolo());
        }// end of for cycle

    }// end of single test

}// end of class
