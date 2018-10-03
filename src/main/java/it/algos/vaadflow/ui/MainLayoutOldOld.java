package it.algos.vaadflow.ui;

import com.flowingcode.addons.applayout.AppLayout;
import com.flowingcode.addons.applayout.menu.MenuItem;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.shared.ui.LoadMode;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.algos.vaadflow.application.FlowCost;
import it.algos.vaadflow.application.StaticContextAccessor;
import it.algos.vaadflow.backend.login.ALogin;
import it.algos.vaadflow.enumeration.EARoleType;
import it.algos.vaadflow.modules.role.EARole;
import it.algos.vaadflow.security.SecurityUtils;
import it.algos.vaadflow.service.AAnnotationService;
import it.algos.vaadflow.service.AReflectionService;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.ui.components.AppNavigation;
import it.algos.vaadflow.ui.components.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.algos.vaadflow.application.FlowCost.PROJECT_NAME;
import static it.algos.vaadflow.application.FlowCost.VIEWPORT;

/**
 * Gestore dei menu. Unico nell'applicazione (almeno finche non riesco a farne girare un altro)
 * <p>
 * Not annotated with @SpringComponent (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @HtmlImport (obbligatorio) per usare il CSS
 * Annotated with @Push (obbligatorio)
 * Annotated with @PageTitle (facoltativo)
 */
@SuppressWarnings("serial")
@Tag("main-view")
@HtmlImport("src/main-view.html")
//@HtmlImport(value = "styles/shared-styles.html", loadMode = LoadMode.INLINE)
//@Push
//@PageTitle(value = MainLayout.SITE_TITLE)
@Slf4j
@Viewport(VIEWPORT)
public class MainLayoutOldOld extends PolymerTemplate<TemplateModel> implements RouterLayout, BeforeEnterObserver {


    public static final String SITE_TITLE = "World Cup 2018 Stats";
    /**
     * Recupera da StaticContextAccessor una istanza della classe <br>
     * La classe deve avere l'annotation @Scope = 'singleton', and is created at the time of class loading <br>
     */
    public ALogin login = StaticContextAccessor.getBean(ALogin.class);
    private AAnnotationService annotation = AAnnotationService.getInstance();
    private AReflectionService reflection = AReflectionService.getInstance();
    private ATextService text = ATextService.getInstance();

    @Id("appNavigation")
    private AppNavigation appNavigation;


    public MainLayoutOldOld() {
        creaAllMenu();
    }// end of method


    protected void creaAllMenu() {
        List<PageInfo> pages = new ArrayList<>();

        pages.add(new PageInfo("role", null, "role"));
        pages.add(new PageInfo("prova", null, "prova"));
        pages.add(new PageInfo("alfa", null, "alfa"));
        pages.add(new PageInfo("beta", null, "beta"));
        appNavigation.init(pages, "role", "prova");
    }// end of method

    private Map<EARole, List<Class>> creaMappa(List<Class> listaClassiMenu) {
        Map<EARole, List<Class>> mappa = new HashMap<>();
        ArrayList<Class> devClazzList = new ArrayList<>();
        ArrayList<Class> adminClazzList = new ArrayList<>();
        ArrayList<Class> utenteClazzList = new ArrayList<>();
        EARoleType type = null;

        for (Class viewClazz : listaClassiMenu) {
            type = annotation.getViewRoleType(viewClazz);

            switch (type) {
                case developer:
                    devClazzList.add(viewClazz);
                    break;
                case admin:
                    adminClazzList.add(viewClazz);
                    break;
                case user:
                    utenteClazzList.add(viewClazz);
                    break;
                default:
                    utenteClazzList.add(viewClazz);
                    log.warn("Switch - caso non definito");
                    break;
            } // end of switch statement
        }// end of for cycle

        mappa.put(EARole.developer, devClazzList);
        mappa.put(EARole.admin, adminClazzList);
        mappa.put(EARole.user, utenteClazzList);

        return mappa;
    }// end of method


    private void addDev(ArrayList<MenuItem> listaMenu, List<Class> devClazzList) {
        MenuItem developerItem;
        MenuItem[] matrice;
        ArrayList<MenuItem> listaSubMenuDev = new ArrayList<>();
        MenuItem menu;

        for (Class viewClazz : devClazzList) {
            menu = creaMenu(viewClazz);
            listaSubMenuDev.add(menu);
        }// end of for cycle

        matrice = listaSubMenuDev.toArray(new MenuItem[listaSubMenuDev.size()]);
        developerItem = new MenuItem("Developer", "build", matrice);
        listaMenu.add(developerItem);
    }// end of method


    private void addAdmin(ArrayList<MenuItem> listaMenu, List<Class> adminClazzList) {
        MenuItem adminItem;
        MenuItem[] matrice;
        ArrayList<MenuItem> listaSubMenuDev = new ArrayList<>();
        MenuItem menu;

        for (Class viewClazz : adminClazzList) {
            menu = creaMenu(viewClazz);
            listaSubMenuDev.add(menu);
        }// end of for cycle

        matrice = listaSubMenuDev.toArray(new MenuItem[listaSubMenuDev.size()]);
        adminItem = new MenuItem("Admin", "apps", matrice);
        listaMenu.add(adminItem);
    }// end of method


    private void addUser(ArrayList<MenuItem> listaMenu, List<Class> userClazzList) {
        MenuItem menu;

        for (Class viewClazz : userClazzList) {
            menu = creaMenu(viewClazz);
            listaMenu.add(menu);
        }// end of for cycle

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


    protected MenuItem creaMenu(Class<? extends AViewList> viewClazz) {
        String linkRoute;
        String menuName;
        String icon;

        linkRoute = annotation.getQualifierName(viewClazz);
        menuName = annotation.getViewName(viewClazz);
        menuName = text.primaMaiuscola(menuName);
        icon = reflection.getIronIcon(viewClazz);

        return new MenuItem(menuName, icon, () -> UI.getCurrent().navigate(linkRoute));
    }// end of method


    protected MenuItem creaMenuLogout() {
        MenuItem menuItem = null;
        String linkRoute = "login";
        String menuName = "Logout";

        menuItem = new MenuItem(menuName, "exit-to-app",() -> UI.getCurrent().getPage().executeJavaScript("location.assign('logout')"));
        return menuItem;
    }// end of method


    @Override
    protected void onAttach(final AttachEvent attachEvent) {
        super.onAttach(attachEvent);
    }// end of method


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
            event.rerouteToError(AccessDeniedException.class);
        }
    }

}// end of class