package it.algos.vaadflow.security;

import it.algos.vaadflow.modules.utente.Utente;
import it.algos.vaadflow.modules.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Implements the {@link UserDetailsService}.
 * <p>
 * This implementation searches for {@link Utente} entities by the e-mail address
 * supplied in the login screen.
 */
@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtenteService userRepository;

    public PasswordEncoder passwordEncoder;


    @Autowired
    public UserDetailsServiceImpl(UtenteService userRepository) {
        this.userRepository = userRepository;
    }// end of method

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
        Utente  user = userRepository.findByUserName(username);

        if (null == user) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            passwordHash = passwordEncoder.encode(user.getPasswordInChiaro());
            return new org.springframework.security.core.userdetails.User(user.getUserName(), passwordHash,
                    Collections.singletonList(new SimpleGrantedAuthority("user")));
        }// end of if/else cycle

    }// end of method

}// end of class