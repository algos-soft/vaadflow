package it.algos.vaadtest.application;

import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.application.FlowVar;
import it.algos.vaadflow.backend.login.ALogin;
import it.algos.vaadflow.boot.ABoot;
import it.algos.vaadtest.modules.alfa.AlfaList;
import it.algos.vaadflow.modules.company.CompanyService;
import it.algos.vaadflow.modules.utente.UtenteService;
import it.algos.vaadtest.dialoghi.ProvaDialoghi;
import it.algos.vaadtest.modules.beta.BetaList;
import it.algos.vaadtest.modules.prova.ProvaList;
import it.algos.vaadtest.modules.prova.ProvaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContextEvent;
import java.time.LocalDate;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 24-ago-2018
 * Time: 16:48
 * <p>
 * Estende la classe ABoot per le regolazioni iniziali di questa applicazione <br>
 * <p>
 * Running logic after the Spring context has been initialized <br>
 * Parte perché SpringBoot chiama il metodo contextInitialized() <br>
 * Invoca alcuni metodi della superclasse <br>
 * Di norma dovrebbe esserci una sola classe di questo tipo nel programma <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
@AIScript(sovrascrivibile = false)
public class TestBoot extends ABoot {


    private final static String PROJECT_NAME = "test";

    private final static double PROJECT_VERSION = 1.5;

    private final static LocalDate VERSION_DATE = LocalDate.of(2019, 10, 22);

    @Autowired
    public ProvaService provaService;

    /**
     * Iniettata dal costruttore <br>
     */
    private TestVers testVers;


    /**
     * Costruttore @Autowired <br>
     *
     * @param testVers Log delle versioni, modifiche e patch installat
     */
    @Autowired
    public TestBoot(TestVers testVers) {
        super();
        this.testVers = testVers;
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
     * Inizializzazione delle versioni standard di vaadinflow <br>
     * Inizializzazione delle versioni del programma specifico <br>
     */
    @Override
    protected void iniziaVersioni() {
        testVers.inizia();
    }// end of method


    /**
     * Riferimento alla sottoclasse specifica di ABoot per utilizzare il metodo sovrascritto resetPreferenze() <br>
     * Il metodo DEVE essere sovrascritto nella sottoclasse specifica <br>
     */
    protected void regolaRiferimenti() {
        preferenzaService.applicationBoot = this;
    }// end of method


    /**
     * Crea le preferenze standard <br>
     * Se non esistono, le crea <br>
     * Se esistono, NON modifica i valori esistenti <br>
     * Per un reset ai valori di default, c'è il metodo reset() chiamato da preferenzaService <br>
     * Il metodo può essere sovrascritto per creare le preferenze specifiche dell'applicazione <br>
     * Invocare PRIMA il metodo della superclasse <br>
     */
    @Override
    protected int creaPreferenze() {
        int numPref = super.creaPreferenze();

//        pref.creaIfNotExist("poltrona", "divano", EAPrefType.enumeration, EARole.user, "ordine,code,descrizione;code");

        return numPref;
    }// end of method


    /**
     * Eventuali regolazione delle preferenze standard effettuata nella sottoclasse specifica <br>
     * Serve per modificare solo per l'applicazione specifica il valore standard della preferenza <br>
     * Eventuali modifiche delle preferenze specifiche (che peraltro possono essere modificate all'origine) <br>
     * Metodo che DEVE essere sovrascritto <br>
     */
    @Override
    protected void fixPreferenze() {
    }// end of method


    /**
     * Cancella e ricrea le preferenze standard <br>
     * Metodo invocato dal metodo reset() di preferenzeService per poter usufruire della sovrascrittura
     * nella sottoclasse specifica dell'applicazione <br>
     * Il metodo può essere sovrascitto per ricreare le preferenze specifiche dell'applicazione <br>
     * Le preferenze standard sono create dalla enumeration EAPreferenza <br>
     * Le preferenze specifiche possono essere create da una Enumeration specifica, oppure singolarmente <br>
     * Invocare PRIMA il metodo della superclasse <br>
     *
     * @return numero di preferenze creato
     */
    @Override
    public int resetPreferenze() {
        int numPref = super.resetPreferenze();

//        pref.crea("poltrona", "divano", EAPrefType.enumeration, EARole.user, "ordine,code,descrizione;code");

        return numPref;
    }// end of method


    /**
     * Regola alcune informazioni dell'applicazione
     */
    protected void regolaInfo() {
        /**
         * Nome identificativo dell'applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         */
        FlowVar.projectName = PROJECT_NAME;

        /**
         * Versione dell'applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         */
        FlowVar.projectVersion = PROJECT_VERSION;

        /**
         * Data della versione dell'applicazione <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         */
        FlowVar.versionDate = VERSION_DATE;

        /**
          * Controlla se l'applicazione usa il login oppure no <br>
          * Se si usa il login, occorre la classe SecurityConfiguration <br>
          * Se non si usa il login, occorre disabilitare l'Annotation @EnableWebSecurity di SecurityConfiguration <br>
          * Di defaul (per sicurezza) uguale a true <br>
          */
        FlowVar.usaSecurity = false;

        /**
         * Controlla se l'applicazione è multi-company oppure no <br>
         * Di defaul (per sicurezza) uguale a true <br>
         * Deve essere regolato in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.usaCompany = true;


        /**
         * Derscrizione completa dell'applicazione <br>
         * Usato (eventualmente) nella barra di menu in testa pagina <br>
         * Usato (eventualmente) nella barra di informazioni a piè di pagina <br>
         */
        FlowVar.projectBanner = "Programma di prova per testare vaadflow";


        /**
         * Service da usare per recuperare dal mongoDB l'utenza loggata tramite 'username' che è unico <br>
         * Di default UtenteService oppure eventuale sottoclasse specializzata per applicazioni con accessi particolari <br>
         * Eventuale casting a carico del chiamante <br>
         * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.loginServiceClazz = UtenteService.class;

        /**
         * Classe da usare per gestire le informazioni dell'utenza loggata <br>
         * Di default ALogin oppure eventuale sottoclasse specializzata per applicazioni con accessi particolari <br>
         * Eventuale casting a carico del chiamante <br>
         * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.loginClazz = ALogin.class;

        /**
         * Service da usare per recuperare la lista delle Company (o sottoclassi) <br>
         * Di default CompanyService oppure eventuale sottoclasse specializzata per Company particolari <br>
         * Eventuale casting a carico del chiamante <br>
         * Deve essere regolata in xxxBoot.regolaInfo() sempre presente nella directory 'application' <br>
         */
        FlowVar.companyServiceClazz = CompanyService.class;

    }// end of method


    /**
     * Inizializzazione dei dati di alcune collections specifiche sul DB mongo
     */
    protected void iniziaDataProgettoSpecifico() {
        provaService.reset();
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
        FlowVar.menuClazzList.add(ProvaDialoghi.class);
        FlowVar.menuClazzList.add(ProvaList.class);
        FlowVar.menuClazzList.add(BetaList.class);
		FlowVar.menuClazzList.add(AlfaList.class);
	}// end of method


}// end of boot class