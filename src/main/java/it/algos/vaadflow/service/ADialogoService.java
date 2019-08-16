package it.algos.vaadflow.service;

import com.vaadin.flow.component.UI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 16-ago-2019
 * Time: 22:36
 */
@Service
@Slf4j
public class ADialogoService extends AbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Private final property
     */
    private static final ADialogoService INSTANCE = new ADialogoService();


    /**
     * Private constructor to avoid client applications to use constructor
     */
    private ADialogoService() {
    }// end of constructor


    /**
     * Gets the unique instance of this Singleton.
     *
     * @return the unique instance of this Singleton
     */
    public static ADialogoService getInstance() {
        return INSTANCE;
    }// end of static method


    public void dialogoUno(Optional<UI> interfacciaUtente, String bodyText) {
        String bodyTextUTF8;
        UI ui = null;

        if (interfacciaUtente != null) {
            ui = interfacciaUtente.get();
        }// end of if cycle

        try { // prova ad eseguire il codice
            if (ui != null) {
                bodyTextUTF8 = URLEncoder.encode(bodyText, "UTF-8");
                ui.navigate("dialogouno" + "/" + bodyTextUTF8);
            }// end of if cycle
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

    }// end of method


    public String decodifica(String bodyTextUTF8) {
        String bodyText = bodyTextUTF8;


        try { // prova ad eseguire il codice
                bodyText = URLDecoder.decode(bodyTextUTF8, "UTF-8");
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        return bodyText;
    }// end of method

}// end of class
