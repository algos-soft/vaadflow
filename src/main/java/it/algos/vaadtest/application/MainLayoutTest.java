package it.algos.vaadtest.application;

import com.flowingcode.addons.applayout.AppLayout;
import com.flowingcode.addons.applayout.menu.MenuItem;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.shared.ui.LoadMode;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 19-ago-2018
 * Time: 13:55
 */
//@HtmlImport(value = "styles/shared-styles.html", loadMode = LoadMode.INLINE)
//@Push
//@PageTitle(value = MainLayoutTest.SITE_TITLE)
public abstract class MainLayoutTest extends VerticalLayout implements RouterLayout, PageConfigurator {

    public static final String SITE_TITLE = "World Cup 2018 Stats";

    public MainLayoutTest() {
        setMargin(false);
        setSpacing(false);
        setPadding(false);
        final  AppLayout app = new AppLayout("Layout di test - va sostituito");
        app.setMenuItems(
                new MenuItem("Home", () -> UI.getCurrent().navigate("")),
                new MenuItem("Pippoz", () -> UI.getCurrent().navigate("alfa")),
                new MenuItem("Domani", () -> UI.getCurrent().navigate("beta")),
                new MenuItem("Adesso", () -> UI.getCurrent().navigate("delta"))
        );
        this.add(app);
    }// end of method

    @Override
    public void showRouterLayoutContent(HasElement content) {
    }// end of method

    @Override
    protected void onAttach(final AttachEvent attachEvent) {
//        getUI().ifPresent(ui -> ui.addDetachListener(e -> matchUpdater.unregisterAll(e.getUI())));
    }// end of method

    @Override
    public void configurePage(final InitialPageSettings settings) {
        settings.addMetaTag("viewport", "width=device-width, initial-scale=1.0");
        settings.addLink("shortcut icon", "/frontend/images/favicons/favicon-96x96.png");
        settings.addLink("manifest", "/manifest.json");
        settings.addFavIcon("icon", "/frontend/images/favicons/favicon-96x96.png", "96x96");
    }// end of method

}// end of class
