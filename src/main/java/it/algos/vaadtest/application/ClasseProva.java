package it.algos.vaadtest.application;

import it.algos.vaadflow.backend.login.ALogin;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mar, 19-feb-2019
 * Time: 08:42
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier("xxxyyyzzz")
@Slf4j
public class ClasseProva extends ALogin {

}// end of class
