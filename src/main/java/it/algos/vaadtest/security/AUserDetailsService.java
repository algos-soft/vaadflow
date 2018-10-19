package it.algos.vaadtest.security;

import it.algos.vaadflow.application.AContext;
import it.algos.vaadflow.application.FlowCost;
import it.algos.vaadflow.application.StaticContextAccessor;
import it.algos.vaadflow.backend.login.ALogin;
import it.algos.vaadflow.modules.company.Company;
import it.algos.vaadflow.modules.preferenza.PreferenzaService;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.modules.role.RoleService;
import it.algos.vaadflow.modules.utente.Utente;
import it.algos.vaadflow.modules.utente.UtenteService;
import it.algos.vaadflow.service.ABootService;
import it.algos.vaadflow.service.AMongoService;
import it.algos.vaadtest.modules.prova.Prova;
import it.algos.vaadtest.modules.prova.ProvaViewDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collection;

import static it.algos.vaadflow.application.FlowCost.KEY_CONTEXT;
import static it.algos.vaadflow.application.FlowCost.PROJECT_NAME;
import static it.algos.vaadflow.application.FlowCost.SHOW_ROLE;

/**
 * Implements the {@link UserDetailsService}.
 * <p>
 * This implementation searches for {@link Utente} entities by the e-mail address
 * supplied in the login screen.
 */
@Service
@Primary
public class AUserDetailsService implements UserDetailsService {

    private final ALogin login;
    private final UtenteService utenteService;
    private final RoleService roleService;

    public PasswordEncoder passwordEncoder;

    /**
     * Istanza (@Scope = 'singleton') inietta da Spring <br>
     */
    @Autowired
    protected ABootService boot;

    @Autowired
    public AUserDetailsService(ALogin login, UtenteService utenteService, RoleService roleService) {
        this.login = login;
        this.utenteService = utenteService;
        this.roleService = roleService;
    }// end of Spring constructor

    /**
     * Recovers the {@link Utente} from the database using the e-mail address supplied
     * in the login screen. If the user is found, returns a
     * {@link org.springframework.security.core.userdetails.User}.
     *
     * @param username User's e-mail address
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String passwordHash = "";
        Collection<? extends GrantedAuthority> authorities;
        Utente utente = utenteService.findByUserName(username);

        login.setUtente(utente);
        FlowCost.LAYOUT_TITLE = login.getCompany() != null ? login.getCompany().descrizione : PROJECT_NAME;

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute(KEY_CONTEXT, new AContext(login));


        if (null == utente) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            passwordHash = passwordEncoder.encode(utente.getPasswordInChiaro());
            authorities = roleService.getAuthorities(utente);
            return new User(utente.getUserName(), passwordHash, authorities);
        }// end of if/else cycle

    }// end of method

}// end of class