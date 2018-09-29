package it.algos.vaadflow.ui;

import com.flowingcode.addons.applayout.AppLayout;
import com.flowingcode.addons.applayout.menu.MenuItem;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.shared.ui.LoadMode;
import it.algos.vaadflow.application.FlowCost;
import it.algos.vaadflow.application.StaticContextAccessor;
import it.algos.vaadflow.backend.login.ALogin;
import it.algos.vaadflow.service.AAnnotationService;
import it.algos.vaadflow.service.ATextService;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.PROJECT_NAME;

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
    /**
     * Recupera da StaticContextAccessor una istanza della classe <br>
     * La classe deve avere l'annotation @Scope = 'singleton', and is created at the time of class loading <br>
     */
    public ALogin login = StaticContextAccessor.getBean(ALogin.class);
    private AAnnotationService annotation = AAnnotationService.getInstance();
    private ATextService text = ATextService.getInstance();


    public MainLayout() {
        setMargin(false);
        setSpacing(false);
        setPadding(false);

        creaAllMenu();
    }// end of method


    protected void creaAllMenu() {
        final AppLayout appLayout = new AppLayout(null, createAvatarComponent(), PROJECT_NAME);
        List<Class> listaClassiMenu = FlowCost.MENU_CLAZZ_LIST;
        ArrayList<MenuItem> listaMenu = null;
        MenuItem developerItems;
        MenuItem menu = null;

        if (listaClassiMenu != null && listaClassiMenu.size() > 0) {
            listaMenu = new ArrayList<>();

            //--crea i menu del developer, inserendoli come sub-menus
            developerItems = creaMenuDeveloper(listaClassiMenu);
            if (developerItems != null) {
                listaMenu.add(developerItems);
            }// end of if cycle

            //--crea gli altri menu, esclusi quelli del developer che sono già inseriti
            for (Class viewClazz : listaClassiMenu) {
                menu = null;
                if (!annotation.getViewAccessibilityDev(viewClazz)) {
                    menu = creaMenu(viewClazz);
                }// end of if cycle

                if (menu != null) {
                    listaMenu.add(menu);
                }// end of if cycle
            }// end of for cycle

            //--crea il logout
            listaMenu.add(creaMenuLogout());

            //--aggiunge i menu
            appLayout.setMenuItems(listaMenu.toArray(new MenuItem[listaMenu.size()]));

            //--crea la barra di bottoni, in alto a destra
            appLayout.setToolbarIconButtons(new MenuItem("Delete", "logout", () -> Notification.show("Delete action")),
                    new MenuItem("Search", "ABACUS", () -> Notification.show("Search action")),
                    new MenuItem("Close", "close", () -> Notification.show("Close action"))
            );

            this.add(appLayout);
        }// end of if cycle
    }// end of method


    private Component createAvatarComponent() {
        Div container = new Div();
        H5 userName;
        container.getElement().setAttribute("style", "text-align: center;");
        Image i = new Image("/frontend/images/avatar.png", "avatar");
        i.getElement().setAttribute("style", "width: 60px; margin-top:10px");

        if (login != null && login.getUtente() != null) {
            container.add(i, new H5(login.getUtente().userName));
        } else {
            container.add(i);
        }// end of if/else cycle

        return container;
    }// end of method


    private MenuItem creaMenuDeveloper(List<Class> listaClassiMenu) {
        MenuItem[] matrice;
        ArrayList<MenuItem> listaMenuDev = new ArrayList<>();
        MenuItem menu;

        for (Class viewClazz : listaClassiMenu) {
            if (annotation.getViewAccessibilityDev(viewClazz)) {
                menu = creaMenu(viewClazz);
                listaMenuDev.add(menu);
            }// end of if cycle
        }// end of for cycle

        matrice = listaMenuDev.toArray(new MenuItem[listaMenuDev.size()]);

        return new MenuItem("Developer", "star", matrice);
    }// end of method


    protected MenuItem creaMenu(Class viewClazz) {
        String linkRoute;
        String menuName;
        String icon;

        linkRoute = annotation.getQualifierName(viewClazz);
        menuName = annotation.getViewName(viewClazz);
        menuName = text.primaMaiuscola(menuName);
        icon = VaadinIcon.MAGIC.toString();

        return new MenuItem(menuName, () -> UI.getCurrent().navigate(linkRoute));
    }// end of method


    protected MenuItem creaMenuLogout() {
        MenuItem menuItem = null;
        String linkRoute = "login";
        String menuName = "Logout";

//        menuItem = new MenuItem(menuName, () -> UI.getCurrent().navigate(linkRoute));
        menuItem = new MenuItem(menuName, () -> UI.getCurrent().getPage().executeJavaScript("location.assign('logout')"));
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