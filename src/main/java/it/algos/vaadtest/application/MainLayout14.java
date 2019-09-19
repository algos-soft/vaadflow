package it.algos.vaadtest.application;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLayout;
import it.algos.vaadflow.application.AContext;
import it.algos.vaadflow.application.StaticContextAccessor;
import it.algos.vaadflow.backend.login.ALogin;
import it.algos.vaadflow.service.AMenuService;
import it.algos.vaadflow.service.AVaadinService;
import it.algos.vaadflow.ui.IAView;
import it.algos.vaadtest.dialoghi.ProvaDialoghi;
import it.algos.vaadtest.modules.prova.ProvaViewList;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Map;

import static it.algos.vaadflow.application.FlowVar.usaSecurity;

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

    /**
     * Recuperato dalla sessione, quando la @route fa partire la UI. <br>
     * Viene regolato nel service specifico (AVaadinService) <br>
     */
    private AContext context;

    /**
     * Mantenuto nel 'context' <br>
     */
    private ALogin login;

    /**
     * Service (@Scope = 'singleton') iniettato da StaticContextAccessor e usato come libreria <br>
     * Unico per tutta l'applicazione. Usato come libreria.
     */
    private AVaadinService vaadinService = StaticContextAccessor.getBean(AVaadinService.class);

    /**
     * Istanza unica di una classe (@Scope = 'singleton') di servizio: <br>
     * Iniettata automaticamente dal Framework @Autowired (SpringBoot/Vaadin) <br>
     * Disponibile SOLO DOPO @PostConstruct o comunque dopo l'init (anche implicito) del costruttore <br>
     */
    @Autowired
    private AMenuService menuService;

    //--property
    private VerticalLayout menuLayout;

    //--property
    private Map<String, ArrayList<Class<? extends IAView>>> mappa;


    public MainLayout14() {
        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Algos");
//        Image img = new Image("images/Visualpharm-Office-Space-User.ico", "Algos");
        img.setHeight("44px");
        addToNavbar(new DrawerToggle(), img);

        menuLayout = new VerticalLayout();
        addToDrawer(menuLayout);
        this.setDrawerOpened(false);
    }// end of constructor


    /**
     * Metodo invocato subito DOPO il costruttore
     * L'istanza DEVE essere creata da SpringBoot con Object algos = appContext.getBean(AlgosClass.class);  <br>
     * <p>
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() del costruttore <br>
     * Si usa quindi un metodo @PostConstruct per avere disponibili tutte le istanze @Autowired <br>
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti, <br>
     * ma l'ordine con cui vengono chiamati (nella stessa classe) NON è garantito <br>
     */
    @PostConstruct
    protected void inizia() {
        context = vaadinService.getSessionContext();
        login = context != null ? context.getLogin() : null;

        mappa = menuService.creaMappa();

//        menuLayout.add(menuService.creaRouter(ProvaDialoghi.class));
//        menuLayout.add(menuService.creaAlgosRouter(ProvaViewList.class));

        menuLayout.add(menuService.creaMenuDeveloper(mappa));

        if (usaSecurity) {
            //--crea menu dello sviluppatore (se loggato)
            if (context.isDev()) {
                menuLayout.add(menuService.creaMenuDeveloper(mappa));
            }// end of if cycle

            //--crea menu dell'admin (se loggato)
            if (context.isDev() || context.isAdmin()) {
//                creaMenuAdmin();
            }// end of if cycle

            //--crea menu utente normale (sempre)
//            creaMenuUser();

            //--crea menu logout (sempre)
//            creaMenuLogout();
        } else {
            //--crea menu indifferenziato
//            creaMenuNoSecurity();
        }// end of if/else cycle
    }// end of method

}// end of class
