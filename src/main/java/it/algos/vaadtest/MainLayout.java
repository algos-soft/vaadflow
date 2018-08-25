package it.algos.vaadtest;

import com.flowingcode.addons.applayout.AppLayout;
import com.flowingcode.addons.applayout.menu.MenuItem;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.shared.ui.LoadMode;
import it.algos.vaadtest.application.VersBootStrap;
import org.springframework.beans.factory.annotation.Autowired;

import static it.algos.vaadflow.application.FlowCost.*;

/**
 * Gestore dei menu. Unico nell'applicazione (almeno finche non riesco a farne girare un altro)
 * Not annotated with @SpringComponent (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @HtmlImport (obbligatorio) per usare il CSS
 * Annotated with @Push (obbligatorio)
 * Annotated with @PageTitle (facoltativo)
 */
@SuppressWarnings("serial")
@HtmlImport(value = "styles/shared-styles.html", loadMode = LoadMode.INLINE)
@Push
@PageTitle(value = MainLayout.SITE_TITLE)
public class MainLayout extends VerticalLayout implements RouterLayout, PageConfigurator {

    public static final String SITE_TITLE = "World Cup 2018 Stats";

    @Autowired
    private VersBootStrap vers;

    public MainLayout() {
        setMargin(false);
        setSpacing(false);
        setPadding(false);
        final AppLayout app = new AppLayout("Vaadinflow");
        app.setMenuItems(
                new MenuItem("Home", () -> UI.getCurrent().navigate("")),
                new MenuItem("Wizard", () -> UI.getCurrent().navigate(TAG_WIZ)),
                new MenuItem("Role", () -> UI.getCurrent().navigate(TAG_ROL)),
                new MenuItem("Company", () -> UI.getCurrent().navigate(TAG_COM)),
                new MenuItem("Preferenza", () -> UI.getCurrent().navigate(TAG_PRE)),
                new MenuItem("Versione", () -> UI.getCurrent().navigate(TAG_VER)),
                new MenuItem("Log", () -> UI.getCurrent().navigate(TAG_LOG)),
                new MenuItem("Address", () -> UI.getCurrent().navigate(TAG_ADD)),
                new MenuItem("Person", () -> UI.getCurrent().navigate(TAG_PER))
//                new MenuItem("About ...", () -> UI.getCurrent().navigate("about")));
        );
        this.add(app);
    }

    @Override
    protected void onAttach(final AttachEvent attachEvent) {
        super.onAttach(attachEvent);
    }// end of method

    @Override
    public void configurePage(final InitialPageSettings settings) {
        settings.addMetaTag("viewport", "width=device-width, initial-scale=1.0");
        settings.addLink("shortcut icon", "/frontend/images/favicons/favicon-96x96.png");
        settings.addLink("manifest", "/manifest.json");
        settings.addFavIcon("icon", "/frontend/images/favicons/favicon-96x96.png", "96x96");
    }// end of method

}// end of class