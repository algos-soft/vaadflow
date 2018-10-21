package it.algos.vaadflow.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.modules.address.AddressService;
import it.algos.vaadflow.modules.anno.AnnoService;
import it.algos.vaadflow.modules.company.CompanyService;
import it.algos.vaadflow.modules.giorno.GiornoService;
import it.algos.vaadflow.modules.logtype.LogtypeService;
import it.algos.vaadflow.modules.mese.MeseService;
import it.algos.vaadflow.modules.person.PersonService;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.modules.role.RoleService;
import it.algos.vaadflow.modules.secolo.SecoloService;
import it.algos.vaadflow.modules.utente.UtenteService;
import it.algos.vaadtest.TestApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 20-ott-2018
 * Time: 08:53
 * <p>
 * Poiché siamo in fase di boot, la sessione non esiste ancora <br>
 * Questo vuol dire che le classe @VaadinSessionScope (come i xxxService dei moduli)
 * NON possono essere iniettate automaticamente da Spring <br>
 * Vengono costruite con la BeanFactory <br>
 * Non riesco, però, a costruire classi con classi annidate come Person e Company che usano Address <br>
 * Per queste mi devo arrangiare <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class FlowData {


    @Autowired
    private ApplicationContext applicationContext;

    private AutowireCapableBeanFactory beanFactory;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private AddressService address;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private AnnoService anno;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private CompanyService company;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private GiornoService giorno;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private LogtypeService logtype;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private MeseService mese;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private PersonService person;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private RoleService role;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private SecoloService secolo;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    private UtenteService utente;


    @PostConstruct
    public void postCostruct() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        beanFactory = applicationContext.getAutowireCapableBeanFactory();

        this.role = beanFactory.createBean(RoleService.class);
//        this.utente = beanFactory.createBean(UtenteService.class);
//        this.logtype = beanFactory.createBean(LogtypeService.class);
//
//        this.address = beanFactory.createBean(AddressService.class);
//
//        this.mese = beanFactory.createBean(MeseService.class);
//        this.secolo = beanFactory.createBean(SecoloService.class);
//        this.anno = beanFactory.createBean(AnnoService.class);
//        this.giorno = beanFactory.createBean(GiornoService.class);

//        this.role = new RoleService(null);
//        this.utente = new UtenteService(null);
//        this.logtype = new LogtypeService(null);
//
//        this.address = new AddressService(null);
//
//        this.mese = new MeseService(null);
//        this.secolo = new SecoloService(null);
//        this.anno = new AnnoService(null);
//        this.giorno = new GiornoService(null);
    }// end of method


    /**
     * Inizializzazione dei dati di alcune collections standard sul DB mongo <br>
     */
    public void loadData() {
        this.role.loadData();
//        this.utente.loadData();
//        this.logtype.loadData();
//
//        this.address.loadData();
//
//        this.mese.loadData();
//        this.secolo.loadData();
//        this.anno.loadData();
//        this.giorno.loadData();
    }// end of method


}// end of class
