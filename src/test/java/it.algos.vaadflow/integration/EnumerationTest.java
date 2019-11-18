package it.algos.vaadflow.integration;

import it.algos.vaadflow.ATest;
import it.algos.vaadflow.enumeration.*;
import it.algos.vaadtest.TestApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 16-nov-2019
 * Time: 16:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnumerationTest extends ATest {


    @Before
    public void setUp() {
    }// end of method


    /**
     * Stringa di valori (text) da usare per memorizzare la preferenza <br>
     * La stringa è composta da tutti i valori separati da virgola <br>
     * Poi, separato da punto e virgola viene il valore selezionato di default <br>
     *
     * @return stringa di valori e valore di default
     */
    @Test
    public void send() {
        previsto = "routers,tabs,buttons,popup,flowing,vaadin;tabs";
        ottenuto = EAMenu.tabs.getPref();
        assertNotNull(ottenuto);
        assertEquals(ottenuto, previsto);

        previsto = "routers,tabs,buttons,popup,flowing,vaadin;popup";
        ottenuto = EAMenu.popup.getPref();
        assertNotNull(ottenuto);
        assertEquals(ottenuto, previsto);

        previsto = "cerca...,ricerca...,find...;cerca...";
        ottenuto = EASearchText.cerca.getPref();
        assertNotNull(ottenuto);
        assertEquals(ottenuto, previsto);

        previsto = "new,nuovo;nuovo";
        ottenuto = EANewText.nuovoItaliano.getPref();
        assertNotNull(ottenuto);
        assertEquals(ottenuto, previsto);

        previsto = "show,mostra,vedi;show";
        ottenuto = EAShowText.show.getPref();
        assertNotNull(ottenuto);
        assertEquals(ottenuto, previsto);

        previsto = "open,edit,modifica,apre,apri;edit";
        ottenuto = EAEditText.edit.getPref();
        assertNotNull(ottenuto);
        assertEquals(ottenuto, previsto);

        previsto = "nessuno,terminale,collectionMongo,sendMail;terminale";
        ottenuto = EALogAction.terminale.getPref();
        assertNotNull(ottenuto);
        assertEquals(ottenuto, previsto);

    }// end of single test


}// end of class
