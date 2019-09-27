package it.algos.vaadflow.ui.list;

import it.algos.vaadflow.service.IAService;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mer, 25-set-2019
 * Time: 18:39
 */
@Slf4j
public abstract class APaginatedGridViewList extends ALayoutViewList {

    /**
     * Costruttore @Autowired (nella sottoclasse concreta) <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     * Nella sottoclasse concreta si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Nella sottoclasse concreta si usa una costante statica, per scrivere sempre uguali i riferimenti <br>
     *
     * @param service business class e layer di collegamento per la Repository
     */
    public APaginatedGridViewList(IAService service) {
        super(service);
    }// end of Vaadin/@Route constructor


    /**
     * Costruttore @Autowired (nella sottoclasse concreta) <br>
     * Questa classe viene costruita partendo da @Route e NON dalla catena @Autowired di SpringBoot <br>
     * Nella sottoclasse concreta si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Nella sottoclasse concreta si usa una costante statica, per scrivere sempre uguali i riferimenti <br>
     *
     * @param service business class e layer di collegamento per la Repository
     */
    public APaginatedGridViewList(IAService service, Class clazz) {
        super(service);
        super.entityClazz = clazz;
    }// end of Vaadin/@Route constructor

}// end of class
