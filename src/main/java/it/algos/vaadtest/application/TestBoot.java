package it.algos.vaadtest.application;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.application.FlowCost;
import it.algos.vaadflow.boot.ABoot;
import it.algos.vaadflow.modules.preferenza.EAPreferenza;
import it.algos.vaadtest.modules.categoria.CategoriaViewList;
import it.algos.vaadtest.modules.prova.ProvaViewList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.servlet.ServletContextEvent;
import java.time.LocalDate;

import static it.algos.vaadflow.application.FlowCost.*;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 24-ago-2018
 * Time: 16:48
 * <p>
 * Running logic after the Spring context has been initialized
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
@AIScript(sovrascrivibile = true)
public class TestBoot extends ABoot {


    /**
     * Iniettata dal costruttore <br>
     */
    private VersBootStrap versBootStrap;


    /**
     * Costruttore @Autowired <br>
     *
     * @param versBootStrap Log delle versioni, modifiche e patch installat
     */
    @Autowired
    public TestBoot(VersBootStrap versBootStrap) {
        super();
        this.versBootStrap = versBootStrap;
    }// end of Spring constructor

    /**
     * Executed on container startup <br>
     * Setup non-UI logic here <br>
     * Viene sovrascritto in questa sottoclasse concreta che invoca il metodo super.inizia() <br>
     * Nella superclasse vengono effettuate delle regolazioni standard; <br>
     * questa sottoclasse concreta può singolarmente modificarle <br>
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        super.inizia();
    }// end of method


    /**
     * Inizializzazione dei dati di alcune collections specifiche sul DB mongo
     */
    protected void iniziaDataProgettoSpecifico() {
    }// end of method


    /**
     * Inizializzazione delle versioni del programma specifico
     */
    protected void iniziaVersioni() {
        versBootStrap.inizia();
    }// end of method


    /**
     * Regola alcune informazioni dell'applicazione
     */
    protected void regolaInfo() {
        PROJECT_NAME = "test";
        PROJECT_VERSION = "0.1";
        PROJECT_DATE = LocalDate.of(2018, 10, 14);
    }// end of method


    /**
     * Regola alcune preferenze iniziali
     * Se non esistono, le crea
     * Se esistono, sostituisce i valori esistenti con quelli indicati qui
     */
    protected void regolaPreferenze() {
        pref.saveValue(EAPreferenza.usaCompany.getCode(), true);
    }// end of method


    /**
     * Questa classe viene invocata PRIMA della chiamata del browser
     * Se NON usa la security, le @Route vengono create solo qui
     * Se USA la security, le @Route vengono sovrascritte all'apertura del brose nella classe AUserDetailsService
     * <p>
     * Aggiunge le @Route (view) specifiche di questa applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseCost
     * Vengono aggiunte dopo quelle standard
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     */
    protected void addRouteSpecifiche() {
        FlowCost.MENU_CLAZZ_LIST.add(ProvaViewList.class);
        FlowCost.MENU_CLAZZ_LIST.add(CategoriaViewList.class);
    }// end of method


}// end of boot class