package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.service.AService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import static it.algos.vaadtest.application.TestCost.TAG_PRO;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 3-set-2018 20.31.07 <br>
 * <br>
 * Estende la classe astratta AService. Layer di collegamento per la Repository. <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Service (ridondante) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRO)
@Slf4j
@AIScript(sovrascrivibile = false)
public class ProvaService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (prevista dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    public ProvaRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public ProvaService(@Qualifier(TAG_PRO) MongoRepository repository) {
        super(repository);
        super.entityClass = Prova.class;
        this.repository = (ProvaRepository) repository;
   }// end of Spring constructor

    /**
     * Ricerca di una entity (la crea se non la trova) <br>
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity trovata o appena creata
     */
    public Prova findOrCrea(String code) {
        Prova entity = findByKeyUnica(code);

        if (entity == null) {
            entity = crea(code);
        }// end of if cycle

        return entity;
    }// end of method

    /**
     * Crea una entity e la registra <br>
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity appena creata
     */
    public Prova crea(String code) {
         return (Prova)save(newEntity(0, code));
    }// end of method

     /**
      * Creazione in memoria di una nuova entity che NON viene salvata
      * Eventuali regolazioni iniziali delle property
      * Senza properties per compatibilità con la superclasse
      *
      * @return la nuova entity appena creata (non salvata)
      */
     @Override
     public Prova newEntity() {
         return newEntity(0, "");
     }// end of method


     /**
      * Creazione in memoria di una nuova entity che NON viene salvata <br>
      * Eventuali regolazioni iniziali delle property <br>
      * All properties <br>
      * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
      *
      * @param ordine      di presentazione (obbligatorio con inserimento automatico se è zero)
	* @param code        codice di riferimento (obbligatorio)
      *
      * @return la nuova entity appena creata (non salvata)
      */
     public Prova newEntity(int ordine, String code) {
         Prova entity = null;

         entity = findByKeyUnica(code);
		if (entity != null) {
			return findByKeyUnica(code);
		}// end of if cycle
		
         entity = Prova.builderProva()
				.ordine(ordine != 0 ? ordine : this.getNewOrdine())
				.code(code.equals("") ? null : code)
				.build();

         return (Prova)creaIdKeySpecifica(entity);
     }// end of method


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Prova findByKeyUnica(String code) {
        return repository.findByCode(code);
    }// end of method

    

    /**
     * Ordine di presentazione (obbligatorio, unico per tutte le eventuali company), <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property <br>
     * Incrementa di uno il risultato <br>
     */
    public int getNewOrdine() {
        int ordine = 0;
        List<Prova> lista = repository.findAllByOrderByOrdineAsc();

        if (lista != null && lista.size() > 0) {
            ordine = lista.get(lista.size() - 1).getOrdine();
        }// end of if cycle

        return ordine + 1;
    }// end of method

}// end of class