package it.algos.vaadflow;

import it.algos.vaadflow.importa.ImportWiki;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.service.AWebService;
import it.algos.vaadflow.wrapper.WrapDueStringhe;
import it.algos.vaadflow.wrapper.WrapTreStringhe;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

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
        importWiki.text = text;
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
        List<WrapDueStringhe> risultato = null;
        risultato = importWiki.regioni();
        assertNotNull(risultato);

        System.out.println("");
        for (WrapDueStringhe wrap : risultato) {
            System.out.println("");
            System.out.println("Codice: " + wrap.getPrima());
            System.out.println("Nome: " + wrap.getSeconda());
        }// end of for cycle
    }// end of single test

    @Test
    public void province() {
        List<WrapTreStringhe> risultato = null;
        risultato = importWiki.province();
        assertNotNull(risultato);

        System.out.println("");
        for (WrapTreStringhe wrap : risultato) {
            System.out.println("");
            System.out.println("Codice: " + wrap.getPrima());
            System.out.println("Province: " + wrap.getSeconda());
            System.out.println("Nella regione: " + wrap.getTerza());
        }// end of for cycle
    }// end of single test

}// end of test class
