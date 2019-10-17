package it.algos.vaadtest.modules.beta;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.ui.fields.IAIcon;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 17-ott-2019
 * Time: 16:44
 * <p>
 * Implementazione DI PROVA per selezionare un gruppo di icone da presentare nel FormDialog <br>
 * Si può usare una Enumeration oppure costruire qui una lista di VaadinIcon <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TestIconService implements IAIcon {

    /**
     * Returns icons.
     *
     * @return some icons
     */
    @Override
    public List<VaadinIcon> findIcons() {
        return EAIconeBeta.getIcons();
    }// end of method

}// end of class
