package it.algos.vaadflow.modules.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.backend.data.AData;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.service.AAnnotationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;

import static it.algos.vaadflow.application.FlowCost.TAG_COM;
import static it.algos.vaadflow.application.FlowCost.TAG_ROL;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 02-set-2018
 * Time: 09:18
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class CompanyData extends AData {



    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param service di collegamento per la Repository
     */
    @Autowired
    public CompanyData(@Qualifier(TAG_COM) CompanyService service) {
        super(Company.class,service);
        this.service = service;
    }// end of Spring constructor


    /**
     * Metodo invocato da ABoot <br>
     * <p>
     * Creazione di una collezione - Solo se non ci sono records
     */
    public void loadData() {
        int numRec = super.count();

        if (numRec == 0) {
            this.crea();
            log.warn("Algos - Creazione dati iniziali CompanyData.inizia(): " + numRec + " schede");
        } else {
            log.info("Algos - Data. La collezione Role è presente: " + numRec + " schede");
        }// end of if/else cycle
    }// end of method


    /**
     * Creazione della collezione
     */
    private void crea() {
        for (EACompany company : EACompany.values()) {
            service.findOrCrea(company.toString());
        }// end of for cycle
    }// end of method


}// end of class
