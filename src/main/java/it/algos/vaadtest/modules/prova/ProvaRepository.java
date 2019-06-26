package it.algos.vaadtest.modules.prova;

import java.util.List;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadflow.annotation.AIScript;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import static it.algos.vaadtest.application.TestCost.TAG_PRO;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Fix date: 20-ott-2018 18.52.31 <br>
 * <br>
 * Estende la l'interaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (obbligatorio) <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRO)
@AIScript(sovrascrivibile = false)
public interface ProvaRepository extends MongoRepository<Prova, String> {


	public Prova findByCode(String code);

	public List<Prova> findAllByOrderByOrdineAsc();

//	@Query(value="{'code' : {$regex : ?0, $options: 'i'}}")
//	public List<Prova> findByCodeStartingWith(String code);

}// end of class