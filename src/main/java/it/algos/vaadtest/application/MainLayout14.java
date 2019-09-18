package it.algos.vaadtest.application;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import it.algos.vaadtest.dialoghi.ProvaDialoghi;
import it.algos.vaadtest.modules.prova.ProvaViewList;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 23-ago-2019
 * Time: 23:03
 */
public class MainLayout14 extends AppLayout {

    public MainLayout14() {
        final DrawerToggle drawerToggle = new DrawerToggle();
        final RouterLink prove = new RouterLink("Prove", ProvaViewList.class);
        final RouterLink dialoghi = new RouterLink("Dialoghi", ProvaDialoghi.class);
        final VerticalLayout menuLayout = new VerticalLayout(prove, dialoghi);
        addToDrawer(menuLayout);
        addToNavbar(drawerToggle);
    }

}// end of class
