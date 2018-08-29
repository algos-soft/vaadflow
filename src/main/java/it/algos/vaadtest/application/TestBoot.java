package it.algos.vaadtest.application;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.application.FlowCost;
import it.algos.vaadflow.boot.ABoot;
import it.algos.vaadtest.modules.prova.ProvaViewList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.servlet.ServletContextEvent;

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
     * Inizializzazione dei dati di alcune collections specifiche sul DB Mongo
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
    }// end of method


    /**
     * Regola alcune preferenze iniziali
     * Se non esistono, le crea
     * Se esistono, sostituisce i valori esistenti con quelli indicati qui
     */
    protected void regolaPreferenze() {
    }// end of method


    /**
     * Aggiunge le @Route (view) specifiche di questa applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in FlowCost
     * Vengono aggiunte dopo quelle standard
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     */
    protected void addRouteSpecifiche() {
		FlowCost.MENU_CLAZZ_LIST.add(ProvaViewList.class);
    }// end of method


}// end of boot class