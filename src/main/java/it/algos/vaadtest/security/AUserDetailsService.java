package it.algos.vaadtest.security;

import it.algos.vaadflow.application.StaticContextAccessor;
import it.algos.vaadflow.modules.role.RoleService;
import it.algos.vaadflow.modules.utente.Utente;
import it.algos.vaadflow.modules.utente.UtenteRepository;
import it.algos.vaadflow.modules.utente.UtenteService;
import it.algos.vaadflow.service.ABootService;
import it.algos.vaadflow.service.AMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Implements the {@link UserDetailsService}.
 * <p>
 * This implementation searches for {@link Utente} entities by the e-mail address
 * supplied in the login screen.
 */
@Service
@Primary
public class AUserDetailsService implements UserDetailsService {


    public PasswordEncoder passwordEncoder;


    public AUserDetailsService(  ) {
    }// end of Spring constructor


    /**
     * Recovers the {@link Utente} from the database using the e-mail address supplied
     * in the login screen. If the user is found, returns a
     * {@link org.springframework.security.core.userdetails.User}.
     *
     * @param uniqueUserName User's uniqueUserName
     */
    @Override
    public UserDetails loadUserByUsername(String uniqueUserName) throws UsernameNotFoundException {
        String passwordHash = "";
        Collection<? extends GrantedAuthority> authorities;
        AMongoService mongo = StaticContextAccessor.getBean(AMongoService.class);
        Utente utente = (Utente) mongo.findByProperty(Utente.class, "userName", uniqueUserName);

        if (null == utente) {
            throw new UsernameNotFoundException("Non c'è nessun utente di nome: " + uniqueUserName);
        } else {
            passwordHash = passwordEncoder.encode(utente.getPasswordInChiaro());
            authorities = utente.getAuthorities();
            return new User(utente.getUserName(), passwordHash, authorities);
        }// end of if/else cycle

    }// end of method


}// end of class