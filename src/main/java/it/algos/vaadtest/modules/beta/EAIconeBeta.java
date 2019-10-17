package it.algos.vaadtest.modules.beta;

import com.vaadin.flow.component.icon.VaadinIcon;
import it.algos.vaadflow.enumeration.EAColor;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 17-ott-2019
 * Time: 11:46
 *
 * Prova per selezionare un gruppo di icone da presentare nel FormDialog <br>
 */
public enum EAIconeBeta {
    alfa(VaadinIcon.RETWEET),
    beta(VaadinIcon.MAILBOX),
    delta(VaadinIcon.DASHBOARD),
    ;

    private VaadinIcon icon;


    EAIconeBeta(VaadinIcon icon) {
        this.icon = icon;
    }// end of constructor


    public static List<VaadinIcon> getIcons() {
        List<VaadinIcon> lista = new ArrayList<>();

        for (EAIconeBeta icona : EAIconeBeta.values()) {
            lista.add(icona.icon);
        }// end of for cycle

        return lista;
    }// end of static method


    public VaadinIcon getIcon() {
        return icon;
    }
}// end of enumeration
