package it.algos.vaadflow;

import it.algos.vaadflow.enumeration.EARegione;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class ImportWikiTest extends ATest {

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


//    @Test
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


//    @Test
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


    /**
     * Import dei comuni dalle pagine di wikipedia <br>
     *
     * @return lista di wrapper con tre stringhe ognuno (regione, provincia, nome)
     */
//    @Test
    public void comune() {
        List<WrapTreStringhe> risultato = null;
        risultato = importWiki.comuni();
        assertNotNull(risultato);

        System.out.println("");
        for (WrapTreStringhe wrap : risultato) {
            System.out.println("");
            System.out.println("Regione: " + wrap.getPrima());
            System.out.println("Provincia: " + wrap.getSeconda());
            System.out.println("Nome: " + wrap.getTerza());
        }// end of for cycle
    }// end of single test


//    @Test
    public void comuniRegioneAbruzzo() {
        singolaRegione(EARegione.abruzzo);
    }// end of single test

//    @Test
    public void comuniRegioneBasilicata() {
        singolaRegione(EARegione.basilicata);
    }// end of single test

//    @Test
    public void comuniRegioneCalabria() {
        singolaRegione(EARegione.calabria);
    }// end of single test

//    @Test
    public void comuniRegioneCampania() {
        singolaRegione(EARegione.campania);
    }// end of single test

//    @Test
    public void comuniRegioneEmilia() {
        singolaRegione(EARegione.emilia);
    }// end of single test

//    @Test
    public void comuniRegioneFriuli() {
        singolaRegione(EARegione.friuli);
    }// end of single test

//    @Test
    public void comuniRegioneLazio() {
        singolaRegione(EARegione.lazio);
    }// end of single test

//    @Test
    public void comuniRegioneLiguria() {
        singolaRegione(EARegione.liguria);
    }// end of single test

//    @Test
    public void comuniRegioneLombardia() {
        singolaRegione(EARegione.lombardia);
    }// end of single test

//    @Test
    public void comuniRegioneMarche() {
        singolaRegione(EARegione.marche);
    }// end of single test

    @Test
    public void comuniRegioneMolise() {
        singolaRegione(EARegione.molise);
    }// end of single test

//    @Test
    public void comuniRegionePiemonte() {
        singolaRegione(EARegione.piemonte);
    }// end of single test


    /**
     * Import dei comuni di una singola regione <br>
     *
     * @return lista di wrapper con tre stringhe ognuno (regione, provincia, nome)
     */
    private List<WrapTreStringhe> singolaRegione(EARegione eaRegione) {
        List<WrapTreStringhe> risultato = null;

        previstoIntero = eaRegione.getComuni();
        risultato = importWiki.singolaRegione(eaRegione);
        assertNotNull(risultato);
        assertEquals(previstoIntero, risultato.size());

        System.out.println(eaRegione.getNome() + " con " + risultato.size() + " comuni");
        for (WrapTreStringhe wrap : risultato) {
            System.out.println("");
            System.out.println("Provincia: " + wrap.getSeconda());
            System.out.println("Comune: " + wrap.getTerza());
        }// end of for cycle

        return risultato;
    }// end of single test

}// end of test class
