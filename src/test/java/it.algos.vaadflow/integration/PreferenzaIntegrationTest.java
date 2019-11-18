package it.algos.vaadflow.integration;

import it.algos.vaadflow.ATest;
import it.algos.vaadflow.enumeration.EAPrefType;
import it.algos.vaadflow.modules.preferenza.Preferenza;
import it.algos.vaadflow.modules.preferenza.PreferenzaService;
import it.algos.vaadflow.modules.role.EARole;
import it.algos.vaadtest.TestApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 18-nov-2019
 * Time: 07:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PreferenzaIntegrationTest extends ATest {

    private Preferenza preferenza;

    @Autowired
    private PreferenzaService pref;


    @Before
    public void setUp() {
        Assert.assertNotNull(pref);
    }// end of method


    @Test
    public void nonFaNulla() {
    }// end of single test


    @Test
    public void stringa() {
        String keyCode = "alfa";
        String desc = "alfasempre";
        previsto = "prova17";
        sorgente = previsto;
        pref.creaIfNotExist(keyCode, desc, EAPrefType.string, EARole.developer, sorgente);

        ottenuto = pref.getStr(keyCode);
        Assert.assertNotNull(ottenuto);
        Assert.assertEquals(ottenuto, previsto);
        ottenutoBooleano = pref.delete(keyCode);
        Assert.assertTrue(ottenutoBooleano);
    }// end of single test

    @Test
    public void intero() {
        String keyCode = "beta";
        String desc = "bettino";
        previstoIntero = 437;
        sorgenteIntero = previstoIntero;
        pref.creaIfNotExist(keyCode, desc, EAPrefType.integer, EARole.developer, sorgenteIntero);

        ottenutoIntero = pref.getInt(keyCode);
        Assert.assertNotNull(ottenutoIntero);
        Assert.assertEquals(ottenutoIntero, previstoIntero);
        ottenutoBooleano = pref.delete(keyCode);
        Assert.assertTrue(ottenutoBooleano);
    }// end of single test

    @Test
    public void lungo() {
        String keyCode = "delta";
        String desc = "descrizione";
        previstoLungo = 123456789;
        sorgenteLungo = previstoLungo;
        pref.creaIfNotExist(keyCode, desc, EAPrefType.lungo, EARole.developer, sorgenteLungo);

        ottenutoLungo = pref.getLong(keyCode);
        Assert.assertNotNull(ottenutoLungo);
        Assert.assertEquals(ottenutoLungo, previstoLungo);
        ottenutoBooleano = pref.delete(keyCode);
        Assert.assertTrue(ottenutoBooleano);
    }// end of single test


    @Test
    public void lungoVuoto() {
        String keyCode = "gamma";
        String desc = "vuoto";
        previstoLungo = 0;
        sorgenteLungo = previstoLungo;
        pref.creaIfNotExist(keyCode, desc, EAPrefType.lungo, EARole.developer, sorgenteLungo);

        ottenutoLungo = pref.getLong(keyCode);
        Assert.assertNotNull(ottenutoLungo);
        Assert.assertEquals(ottenutoLungo, previstoLungo);
        ottenutoBooleano = pref.delete(keyCode);
        Assert.assertTrue(ottenutoBooleano);
    }// end of single test

//    @Test
//    public void booleano() {
//        String keyCode = "prova";
//        byte[] vuoto = new byte[1];
//        vuoto[0] = 0;
//
//        byte[] pieno = new byte[1];
//        pieno[0] = 1;
//
//        preferenza = new Preferenza();
//        preferenza.setCode(keyCode);
//        preferenza.setType(EAPrefType.bool);
//
//        preferenza.setValue(vuoto);
//        previstoBooleano = false;
//        ottenutoBooleano = (boolean) service.getValue(keyCode);
//        assertEquals(previstoBooleano, ottenutoBooleano);
//
//        preferenza.setValue(pieno);
//        previstoBooleano = true;
//        ottenutoBooleano = (boolean) service.getValue(keyCode);
//        assertEquals(previstoBooleano, ottenutoBooleano);
//    }// end of single test

}// end of class
