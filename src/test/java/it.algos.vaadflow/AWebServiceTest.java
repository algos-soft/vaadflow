package it.algos.vaadflow;

import it.algos.vaadflow.service.AWebService;
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
 * Time: 16:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AWebServiceTest {

    private static String PAGINA = "ISO 3166-2:IT";

    protected String sorgente = "";

    protected String previsto = "";

    protected String ottenuto = "";

    @InjectMocks
    AWebService aWebService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(aWebService);
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

    }// end of class
