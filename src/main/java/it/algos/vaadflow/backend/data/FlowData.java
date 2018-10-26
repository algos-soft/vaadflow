package it.algos.vaadflow.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.modules.address.AddressService;
import it.algos.vaadflow.modules.anno.AnnoService;
import it.algos.vaadflow.modules.company.CompanyService;
import it.algos.vaadflow.modules.giorno.GiornoService;
import it.algos.vaadflow.modules.logtype.LogtypeService;
import it.algos.vaadflow.modules.mese.MeseService;
import it.algos.vaadflow.modules.person.PersonService;
import it.algos.vaadflow.modules.role.RoleService;
import it.algos.vaadflow.modules.secolo.SecoloService;
import it.algos.vaadflow.modules.utente.UtenteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 20-ott-2018
 * Time: 08:53
 * <p>
 * Poiché siamo in fase di boot, la sessione non esiste ancora <br>
 * Questo vuol dire che le classi @VaadinSessionScope (come i xxxService dei moduli)
 * NON possono essere iniettate automaticamente da Spring <br>
 * Vengono costruite con la BeanFactory <br>
 * Non riesco, però, a costruire classi con classi annidate come Person e Company che usano Address <br>
 * Per queste mi devo arrangiare <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class FlowData extends AData {


//    @Autowired
//    private ApplicationContext applicationContext;
//
//    private AutowireCapableBeanFactory beanFactory;


//    /**
//     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
//     */
//    private MeseService mese;
//    /**
//     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
//     */
//    @Autowired
//    private AMongoService mongoService;


    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private RoleService roleService;

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private UtenteService utenteService;

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private LogtypeService logtypeService;

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private AddressService addressService;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    @Autowired
    private PersonService personService;

    /**
     * Istanza @VaadinSessionScope inietta da BeanFactory <br>
     */
    @Autowired
    private CompanyService companyService;

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private MeseService meseService;

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private SecoloService secoloService;


    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private AnnoService annoService;


    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    private GiornoService giornoService;


    /**
     * Inizializzazione dei dati di alcune collections standard sul DB mongo <br>
     */
    public void loadAllData() {
        roleService.loadData();
        utenteService.loadData();
        logtypeService.loadData();

        addressService.loadData();
        personService.loadData();
        companyService.loadData();

        secoloService.loadData();
        meseService.loadData();
        annoService.loadData();
        giornoService.loadData();
    }// end of method


//    /**//
//
//    /**
//     * Inizializzazione dei dati della collection indicata <br>
//     */
//    public int loadAnno() {
//        int numRec = mongoService.count(Anno.class);
//        int ordine = 0;
//        Anno entity;
//        String titoloAnno;
//        EASecolo secoloEnum;
//        Secolo secoloBean;
//        String titoloSecolo;
//        String collectionName = annotation.getCollectionName(Anno.class);
//
//        if (numRec == 0) {
//            //costruisce gli anni prima di cristo dal 1000
//            for (int k = ANTE_CRISTO; k > 0; k--) {
//                ordine = ANNO_INIZIALE - k;
//                titoloAnno = k + EASecolo.TAG_AC;
//                secoloEnum = EASecolo.getSecoloAC(k);
//                titoloSecolo = secoloEnum.getTitolo();
//                secoloBean = secoloService.findByKeyUnica(titoloSecolo);
//                if (ordine != ANNO_INIZIALE) {
//                    entity = Anno.builderAnno()
//                            .secolo(secoloBean)
//                            .ordine(ordine)
//                            .titolo(titoloAnno)
//                            .build();
//                    entity.id = entity.titolo;
//                    mongoService.insert(entity, Anno.class);
//                    numRec++;
//                }// end of if cycle
//            }// end of for cycle
//
//            //costruisce gli anni dopo cristo fino al 2030
//            for (int k = 1; k <= DOPO_CRISTO; k++) {
//                ordine = k + ANNO_INIZIALE;
//                titoloAnno = k + VUOTA;
//                secoloEnum = EASecolo.getSecoloDC(k);
//                titoloSecolo = secoloEnum.getTitolo();
//                secoloBean = secoloService.findByKeyUnica(titoloSecolo);
//                if (ordine != ANNO_INIZIALE) {
//                    entity = Anno.builderAnno()
//                            .secolo(secoloBean)
//                            .ordine(ordine)
//                            .titolo(titoloAnno)
//                            .build();
//                    entity.id = entity.titolo;
//                    mongoService.insert(entity, Anno.class);
//                    numRec++;
//                }// end of if cycle
//            }// end of for cycle
//            log.warn("Algos " + collectionName + "- Creazione dati iniziali: " + numRec + " schede");
//        } else {
//            log.info("Algos - Data. La collezione " + collectionName + " è già presente: " + numRec + " schede");
//        }// end of if/else cycle
//
//        return numRec;
//    }// end of method
//
//    /**
//     * Inizializzazione dei dati della collection indicata <br>
//     */
//    public int loadGiorno() {
//        int numRec = mongoService.count(Giorno.class);
//        int ordine = 0;
//        Giorno entity;
//        ArrayList<HashMap> lista;
//        String titolo;
//        int bisestile;
//        Mese meseEntity;
//        String collectionName = annotation.getCollectionName(Giorno.class);
//
//        if (numRec == 0) {
//            //costruisce i 366 records
//            lista = date.getAllGiorni();
//            for (HashMap mappaGiorno : lista) {
//                titolo = (String) mappaGiorno.get(KEY_MAPPA_GIORNI_TITOLO);
//                bisestile = (int) mappaGiorno.get(KEY_MAPPA_GIORNI_BISESTILE);
//                meseEntity = meseService.findByKeyUnica((String) mappaGiorno.get(KEY_MAPPA_GIORNI_MESE_TESTO));
//                entity = Giorno.builderGiorno()
//                        .mese(meseEntity)
//                        .ordine(ordine)
//                        .titolo(titolo)
//                        .build();
//                entity.id = entity.titolo;
//                mongoService.insert(entity, Giorno.class);
//                numRec++;
//            }// end of for cycle
//            log.warn("Algos " + collectionName + "- Creazione dati iniziali: " + numRec + " schede");
//        } else {
//            log.info("Algos - Data. La collezione " + collectionName + " è già presente: " + numRec + " schede");
//        }// end of if/else cycle
//
//        return numRec;
//    }// end of method

}// end of class
