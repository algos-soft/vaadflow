package it.algos.vaadflow.service;

import com.vaadin.flow.component.UI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

import static it.algos.vaadflow.application.FlowCost.KEY_MAPPA_BODY;
import static it.algos.vaadflow.application.FlowCost.KEY_MAPPA_HEADER;

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
     * Istanza unica di una classe (@Scope = 'singleton') di servizio: <br>
     * Iniettata automaticamente dal Framework @Autowired (SpringBoot/Vaadin) <br>
     * Disponibile dopo il metodo beforeEnter() invocato da @Route al termine dell'init() di questa classe <br>
     * Disponibile dopo un metodo @PostConstruct invocato da Spring al termine dell'init() di questa classe <br>
     */
    @Autowired
    public ARouteService routeService = ARouteService.getInstance();


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


    public void dialogoZero(Optional<UI> interfacciaUtente, String bodyText) {
        String routeNameTag = "dialogozero";
        routeService.navigate(interfacciaUtente, routeNameTag, bodyText);
    }// end of method


    public void dialogoUno(Optional<UI> interfacciaUtente, String bodyText) {
        String routeNameTag = "dialogouno";
        routeService.navigate(interfacciaUtente, routeNameTag, bodyText);
    }// end of method


    public void dialogoDue(Optional<UI> interfacciaUtente, String headerText, String bodyText) {
        String routeNameTag = "dialogodue";
        routeNameTag = "xx";
        HashMap<String, String> mappa = new HashMap<>();
        mappa.put(KEY_MAPPA_HEADER, headerText);
        mappa.put(KEY_MAPPA_BODY, bodyText);

        routeService.navigate(interfacciaUtente, routeNameTag, mappa);
    }// end of method


//    public void routeVersoDialogo(Optional<UI> interfacciaUtente, String bodyText, String routeNameTag) {
////        String bodyTextUTF8;
////        UI ui = null;
////
////        if (interfacciaUtente != null) {
////            ui = interfacciaUtente.get();
////        }// end of if cycle
////
////        try { // prova ad eseguire il codice
////            if (ui != null) {
////                bodyTextUTF8 = routeService.codifica(bodyText);
////                ui.navigate(routeNameTag + "/" + bodyTextUTF8);
////            }// end of if cycle
////        } catch (Exception unErrore) { // intercetta l'errore
////        }// fine del blocco try-catch
////
//        routeService.navigate(interfacciaUtente, routeNameTag, bodyText);
//    }// end of method


}// end of class
