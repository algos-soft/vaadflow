package it.algos.vaadflow;

import it.algos.vaadflow.importa.ImportProvincie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 06-apr-2020
 * Time: 15:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ImportProvincieTest  {

    @InjectMocks
    ImportProvincie importProvincie;

    protected String sorgente = "";

    protected String previsto = "";

    protected String ottenuto = "";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(importProvincie);
    }// end of method


    /**
     * Sorgente completo di una pagina web <br>
     *
     * @param urlCompleto
     *
     * @return sorgente
     */
    @Test
    public void getSorgente() {
        sorgente = "https://it.wikipedia.org/wiki/ISO_3166-2:IT";
        ottenuto = importProvincie.getSorgente(sorgente);
        assertNotNull(ottenuto);
    }// end of single test


    @Test
    public void esegue() {
        Object risultato;
        risultato = importProvincie.esegue();
        assertNotNull(risultato);
    }// end of single test

}// end of test class
