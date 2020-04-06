package it.algos.vaadflow;

import it.algos.vaadflow.importa.ImportWiki;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.service.AWebService;
import it.algos.vaadflow.wrapper.WrapDueStringhe;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 06-apr-2020
 * Time: 15:59
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImportWikiTest {

    @InjectMocks
    protected AWebService aWebService;

    protected String sorgente = "";

    protected String previsto = "";

    protected String ottenuto = "";

    @InjectMocks
    ImportWiki importWiki;

    @InjectMocks
    ATextService text;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(importWiki);
        MockitoAnnotations.initMocks(aWebService);
        MockitoAnnotations.initMocks(text);
        importWiki.aWebService = aWebService;
        aWebService.text = text;
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
        sorgente = "ISO 3166-2:IT";
        ottenuto = importWiki.getSorgente(sorgente);
        assertNotNull(ottenuto);
    }// end of single test


    @Test
    public void regioni() {
        List<WrapDueStringhe>   risultato = null;
        risultato = importWiki.regioni();
        assertNotNull(risultato);
    }// end of single test

}// end of test class
