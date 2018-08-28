package it.algos.vaadflow.ui;

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
import it.algos.vaadflow.application.FlowCost;
import it.algos.vaadflow.service.AAnnotationService;
import it.algos.vaadflow.service.ATextService;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestore dei menu. Unico nell'applicazione (almeno finche non riesco a farne girare un altro)
 * <p>
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

    private AAnnotationService annotation = AAnnotationService.getInstance();
    private ATextService text = ATextService.getInstance();

    public MainLayout() {
        setMargin(false);
        setSpacing(false);
        setPadding(false);

        creaAllMenu();
    }// end of method


    protected void creaAllMenu() {
        final AppLayout app = new AppLayout("vaadinflow");
        List<Class> listaClassiMenu = FlowCost.MENU_CLAZZ_LIST;
        ArrayList<MenuItem> listaMenu = null;

        if (listaClassiMenu != null && listaClassiMenu.size() > 0) {
            listaMenu = new ArrayList<>();
            for (Class clazz : listaClassiMenu) {
                listaMenu.add(creaMenu(clazz));
            }// end of for cycle
        }// end of if cycle

        app.setMenuItems(listaMenu.toArray(new MenuItem[listaMenu.size()]));
        this.add(app);
    }// end of method


    protected MenuItem creaMenu(Class viewClazz) {
        MenuItem menuItem = null;
        String linkRoute = annotation.getQualifierName(viewClazz);
        String menuName = annotation.getViewName(viewClazz);
        menuName = text.primaMaiuscola(menuName);

        menuItem = new MenuItem(menuName, () -> UI.getCurrent().navigate(linkRoute));
        return menuItem;
    }// end of method


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