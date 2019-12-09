package it.algos.vaadflow;

import it.algos.vaadflow.modules.mese.EAMese;
import it.algos.vaadflow.modules.mese.MeseService;
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
 * Date: lun, 09-dic-2019
 * Time: 07:46
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("mesi")
@DisplayName("Test sulla collection 'mese'")
class AMeseTest extends ATest {

    @InjectMocks
    MeseService service;

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
     * Mese
     *
     * @param nomeBreveLungo Nome breve o lungo del mese
     *
     * @return Mese
     */
    @Test
    public void getMese() {
        EAMese eaMesePrevisto;
        EAMese eaMeseOttenuto;

        eaMesePrevisto = EAMese.maggio;
        sorgente = "maggio";
        eaMeseOttenuto = EAMese.getMese(sorgente);
        assertNotNull(eaMeseOttenuto);
        assertEquals(eaMesePrevisto, eaMeseOttenuto);

        eaMesePrevisto = EAMese.ottobre;
        sorgente = "ott";
        eaMeseOttenuto = EAMese.getMese(sorgente);
        assertNotNull(eaMeseOttenuto);
        assertEquals(eaMesePrevisto, eaMeseOttenuto);
    }// end of single test


    /**
     * Numero del mese nell'anno
     *
     * @param nomeBreveLungo L'anno parte da gennaio che è il mese numero 1
     *
     * @return Numero del mese
     */
    @Test
    public void getOrder() {
        previstoIntero = 4;
        sorgente = "aprile";
        ottenutoIntero = EAMese.getOrder(sorgente);
        assertEquals(previstoIntero, ottenutoIntero);

        previstoIntero = 11;
        sorgente = "nov";
        ottenutoIntero = EAMese.getOrder(sorgente);
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    /**
     * Riordina una lista di valori <br>
     *
     * @return numero di elementi creato
     */
    @Test
    void riordina() {
        sorgenteList = Arrays.asList("luglio", "marzo", "gennaio");
        previstoList = Arrays.asList("gennaio", "marzo", "luglio");

        ottenutoList = service.riordina(sorgenteList);
        assertNotNull(ottenutoList);
        assertEquals(previstoList, ottenutoList);
    }// end of single test


    @Test
    void mostraTutti() {
        EAMese[] matrice = EAMese.values();
        assertNotNull(matrice);
        assertEquals(12, matrice.length);

        System.out.println();
        String sep = " - ";
        int k=0;
        for (EAMese mese : matrice) {
            System.out.println(++k+sep+mese.getLungo() + sep + mese.getBreve() + sep + mese.getGiorni());
        }// end of for cycle

    }// end of single test

}// end of class
