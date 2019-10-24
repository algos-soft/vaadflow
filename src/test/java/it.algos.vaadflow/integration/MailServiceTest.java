package it.algos.vaadflow.integration;

import it.algos.vaadflow.service.AMailService;
import it.algos.vaadtest.TestApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Project vaadwiki
 * Created by Algos
 * User: gac
 * Date: Sun, 09-Jun-2019
 * Time: 13:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MailServiceTest {

    private String title;

    private String body;

    private boolean spedita;

    /**
     * La injection viene fatta da SpringBoot in automatico <br>
     */
    @Autowired
    private ApplicationContext appContext;

    /**
     * La injection viene fatta da SpringBoot in automatico <br>
     */
    @Autowired
    private AMailService service;


    @Before
    public void setUp() {
        Assert.assertNotNull(appContext);
        Assert.assertNotNull(service);
    }// end of method


    /**
     * Spedizione standard senza mittente e senza destinatario
     *
     * @param title soggetto
     * @param body  testo della mail
     */
    @Test
    public void send() {
        title = "Prova";
        body = "Primo tentativo";

        spedita = service.send(title, body);
        assertTrue(spedita);
    }// end of single test


    /**
     * Spedizione senza mittente e senza destinatario
     * Aggiunge ID e nome del server <br>
     *
     * @param title soggetto
     */
    @Test
    public void sendIP() {
        title = "Address";

        spedita = service.sendIP(title);
        assertTrue(spedita);
    }// end of method


    /**
     * Spedizione senza mittente e senza destinatario
     * Aggiunge ID e nome del server <br>
     *
     * @param title soggetto
     * @param body  testo originario della mail
     */
    @Test
    public void sendIP2() {
        title = "Address";
        body = "Messaggio aggiunto";

        spedita = service.sendIP(title, body);
        assertTrue(spedita);
    }// end of method


    /**
     * Spedizione senza mittente e senza destinatario
     * Aggiunge ID e nome del server <br>
     *
     * @param title  soggetto
     * @param inizio della operazione
     */
    @Test
    public void sendIP3() {
        title = "Address";
        long inizio = System.currentTimeMillis();

        spedita = service.sendIP(title, inizio);
        assertTrue(spedita);
    }// end of method


    /**
     * Spedizione senza mittente e senza destinatario
     * Aggiunge ID e nome del server <br>
     *
     * @param title  soggetto
     * @param body   testo originario della mail
     * @param inizio della operazione
     */
    @Test
    public void sendIP4() {
        title = "Address";
        body = "Tempo trascorso";
        long inizio = System.currentTimeMillis();

        spedita = service.sendIP(title, body, inizio);
        assertTrue(spedita);
    }// end of method

}// end of class
