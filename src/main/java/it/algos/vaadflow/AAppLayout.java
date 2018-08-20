package it.algos.vaadflow;

import com.flowingcode.addons.applayout.AppLayout;
import com.flowingcode.addons.applayout.menu.MenuItem;
import com.vaadin.flow.component.UI;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.annotation.PostConstruct;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-ago-2018
 * Time: 14:12
 */
@Slf4j
public class AAppLayout extends AppLayout {



    private static final AAppLayout instance = new AAppLayout();

    /**
     * Private constructor to avoid client applications to use constructor
     */
    private AAppLayout(){
        super("");
    }

    public static AAppLayout getInstance(){
        return instance;
    }




    @PostConstruct
    private  void pippoz(){
        this.setMenuItems(
                new MenuItem("Home", () -> UI.getCurrent().navigate("")),
                new MenuItem("Alfa", () -> UI.getCurrent().navigate("alfa")),
                new MenuItem("Beta", () -> UI.getCurrent().navigate("beta")),
                new MenuItem("Delta", () -> UI.getCurrent().navigate("delta"))
        );
    }


}// end of class
