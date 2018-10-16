package it.algos.vaadflow;

import it.algos.vaadflow.modules.preferenza.EAPrefType;
import it.algos.vaadflow.modules.preferenza.Preferenza;
import it.algos.vaadflow.modules.preferenza.PreferenzaService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 27-ago-2018
 * Time: 08:48
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("preferenze")
@DisplayName("Test sulle preferenze")
public class PreferenzaServiceTest extends ATest {

    @InjectMocks
    public Preferenza preferenza;

    @InjectMocks
    public PreferenzaService service;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(preferenza);
        MockitoAnnotations.initMocks(service);
    }// end of method


    @SuppressWarnings("javadoc")
    @Test
    public void booleano() {
        String keyCode = "prova";
        byte[] vuoto = new byte[1];
        vuoto[0] = 0;

        byte[] pieno = new byte[1];
        pieno[0] = 1;

        preferenza = new Preferenza();
        preferenza.setCode(keyCode);
        preferenza.setType(EAPrefType.bool);

        preferenza.setValue(vuoto);
        previstoBooleano = false;
        ottenutoBooleano = (boolean) service.getValue(keyCode);
        assertEquals(previstoBooleano, ottenutoBooleano);

        preferenza.setValue(pieno);
        previstoBooleano = true;
        ottenutoBooleano = (boolean) service.getValue(keyCode);
        assertEquals(previstoBooleano, ottenutoBooleano);
    }// end of single test

}// end of class
