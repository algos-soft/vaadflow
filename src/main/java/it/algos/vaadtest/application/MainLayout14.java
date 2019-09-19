package it.algos.vaadtest.application;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import it.algos.vaadtest.dialoghi.ProvaDialoghi;
import it.algos.vaadtest.modules.prova.ProvaViewList;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 23-ago-2019
 * Time: 23:03
 * <p>
 * You can set a logo, navigation menus, page content, and more
 * <p>
 * The component is fully responsive and the elements intuitively adjust and modify,
 * depending on the user’s screen size and device type.
 * For example, on small mobile screens, side menus collapse and open with animation,
 * and top menus are repositioned below the main content.
 */
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, viewport-fit=cover")
public class MainLayout14 extends AppLayout implements RouterLayout {

    public MainLayout14() {
        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
        img.setHeight("44px");
        addToNavbar(new DrawerToggle(), img);

        final RouterLink prove = new RouterLink("Prove", ProvaViewList.class);
        final RouterLink dialoghi = new RouterLink("Dialoghi", ProvaDialoghi.class);
        final VerticalLayout menuLayout = new VerticalLayout(prove, dialoghi);
        addToDrawer(menuLayout);
        this.setDrawerOpened(false);
    }// end of constructor


}// end of class
