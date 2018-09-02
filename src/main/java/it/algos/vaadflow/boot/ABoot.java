package it.algos.vaadflow.boot;

import it.algos.vaadflow.application.FlowCost;
import it.algos.vaadflow.developer.DeveloperView;
import it.algos.vaadflow.modules.address.AddressViewList;
import it.algos.vaadflow.modules.company.CompanyData;
import it.algos.vaadflow.modules.company.CompanyViewList;
import it.algos.vaadflow.modules.person.PersonViewList;
import it.algos.vaadflow.modules.preferenza.EAPreferenza;
import it.algos.vaadflow.modules.preferenza.PreferenzaService;
import it.algos.vaadflow.modules.preferenza.PreferenzaViewList;
import it.algos.vaadflow.modules.role.RoleData;
import it.algos.vaadflow.modules.role.RoleViewList;
import it.algos.vaadflow.modules.utente.UtenteViewList;
import it.algos.vaadflow.modules.versione.VersioneViewList;
import it.algos.vaadflow.wizard.WizardView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Project it.algos.vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 06-mag-2018
 * Time: 18:43
 * <p>
 * Running logic after the Spring context has been initialized
 * The method onApplicationEvent() will be executed nella sottoclasse before the application is up and <br>
 * <p>
 * Aggiunge tutte le @Route (views) standard e specifiche di questa applicazione <br>
 * <p>
 * Not annotated with @SpringComponent (SpringBoot crea la sottoclasse concreta) <br>
 * Not annotated with @Scope (inutile) <br>
 */
public abstract class ABoot implements ServletContextListener {

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private RoleData role;


    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private CompanyData company;


//    @Autowired
//    protected UtenteService utenteService;

//    @Autowired
//    protected CompanyService companyService;

    /**
     * Inietta da Spring come 'singleton'
     */
    @Autowired
    protected PreferenzaService pref;

    /**
     * Layout iniettato da Spring
     */
//    @Autowired
//    protected AFooter footer;


    /**
     * Executed on container startup
     * Setup non-UI logic here
     * Utilizzato per:
     * - registrare nella xxxApp, il servlet context non appena è disponibile
     * - regolare alcuni flag dell'applicazione, uguali e validi per tutte le sessioni e tutte le request <br>
     * - lanciare gli schedulers in background <br>
     * - costruire e regolare una versione demo <br>
     * - controllare l'esistenza di utenti abilitati all'accesso <br>
     * Running logic after the Spring context has been initialized
     * Any class that use this @EventListener annotation,
     * will be executed before the application is up and its onApplicationEvent method will be called
     * <p>
     * Viene normalmente creata una sottoclasse per l'applicazione specifica:
     * - per regolare eventualmente alcuni flag in maniera non standard
     * - lanciare gli schedulers in background <br>
     * - costruire e regolare una versione demo <br>
     * - controllare l'esistenza di utenti abilitati all'accesso <br>
     * <p>
     * Stampa a video (productionMode) i valori per controllo
     * Deve essere sovrascritto dalla sottoclasse concreta che invocherà questo metodo()
     */
    protected void inizia() {
        this.iniziaDataStandard();
        this.iniziaDataProgettoSpecifico();
        this.iniziaVersioni();
        this.regolaInfo();
        this.regolaPreferenze();
        this.addRouteStandard();
        this.addRouteSpecifiche();
    }// end of method

    /**
     * Inizializzazione dei dati di alcune collections standard sul DB mongo
     */
    private void iniziaDataStandard() {
        this.role.loadData();
        this.company.loadData();
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
     * Aggiunge le @Route (view) standard
     * Nella sottoclasse concreta che invoca questo metodo, aggiunge le @Route (view) specifiche dell'applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseCost
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     */
    private void addRouteStandard() {
        if (pref.isBool(EAPreferenza.showCompany.getCode())) {
            FlowCost.MENU_CLAZZ_LIST.add(CompanyViewList.class);
        }// end of if cycle
        if (pref.isBool(EAPreferenza.showPreferenza.getCode())) {
            FlowCost.MENU_CLAZZ_LIST.add(PreferenzaViewList.class);
        }// end of if cycle
        if (pref.isBool(EAPreferenza.showRole.getCode())) {
            FlowCost.MENU_CLAZZ_LIST.add(RoleViewList.class);
        }// end of if cycle
        if (pref.isBool(EAPreferenza.showAddress.getCode())) {
            FlowCost.MENU_CLAZZ_LIST.add(AddressViewList.class);
        }// end of if cycle
        if (pref.isBool(EAPreferenza.showPerson.getCode())) {
            FlowCost.MENU_CLAZZ_LIST.add(PersonViewList.class);
        }// end of if cycle
        if (pref.isBool(EAPreferenza.showDeveloper.getCode())) {
            FlowCost.MENU_CLAZZ_LIST.add(DeveloperView.class);
        }// end of if cycle
        if (pref.isBool(EAPreferenza.showWizard.getCode())) {
            FlowCost.MENU_CLAZZ_LIST.add(WizardView.class);
        }// end of if cycle
        if (pref.isBool(EAPreferenza.showVersione.getCode())) {
            FlowCost.MENU_CLAZZ_LIST.add(VersioneViewList.class);
        }// end of if cycle
        if (pref.isBool(EAPreferenza.showUser.getCode())) {
            FlowCost.MENU_CLAZZ_LIST.add(UtenteViewList.class);
        }// end of if cycle
    }// end of method


    /**
     * Aggiunge le @Route (view) specifiche di questa applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseCost
     * Vengono aggiunte dopo quelle standard
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     */
    protected void addRouteSpecifiche() {
    }// end of method


    /**
     * Executed on container shutdown
     * <p>
     * Clean stuff here <br>
     * Può essere sovrascritta dalla sottoclasse <br>
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo) <br>
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }// end of method


}// end of class