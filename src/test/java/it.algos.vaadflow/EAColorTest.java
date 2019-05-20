package it.algos.vaadflow;

import it.algos.vaadflow.enumeration.EAColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: Thu, 16-May-2019
 * Time: 22:07
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Test per una Enumeration di colori")
public class EAColorTest extends ATest {


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        previstoIntero = 16;
    }// end of method


    @Test
    @DisplayName("Estraggo un arrayList di EAColor")
    public void getColors() {
        Object lista = EAColor.getColors();
        assertNotNull(lista);
        assertEquals(lista.getClass(), ArrayList.class);

        ottenutoIntero = ((ArrayList) lista).size();
        assertEquals(previstoIntero, ottenutoIntero);
    }// end of single test


    @Test
    @DisplayName("Elenco la Enumeration EAColor")
    public void showEnumeration() {
        List<EAColor> lista = EAColor.getColors();
        assertNotNull(lista);

        System.out.println("Nomi");
        for (EAColor color : lista) {
            System.out.println(color.getTag());
        }// end of for cycle
        System.out.println("");

        System.out.println("Esadecimale");
        for (EAColor color : lista) {
            System.out.println(color.getEsadecimale());
        }// end of for cycle
        System.out.println("");

        System.out.println("Nomi + esadecimale");
        for (EAColor color : lista) {
            System.out.println(color.getTag() + ": " + color.getEsadecimale());
        }// end of for cycle
        System.out.println("");

    }// end of single test

}// end of class
