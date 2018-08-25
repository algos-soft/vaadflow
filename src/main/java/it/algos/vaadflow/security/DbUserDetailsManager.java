package it.algos.vaadflow.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;

public class DbUserDetailsManager implements UserDetailsManager {

//    @Autowired
//    private UtenteService utenteService;

//    @Autowired
//    private CompanyService companyService;

//    @Autowired
//    private ALogin login;

    @Override
    public void createUser(UserDetails userDetails) {

    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String username) {
//        Utente utente = utenteService.findByUsername(username);
//        return utente != null;
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        String password = "admin";
//        Utente utente = utenteService.findByUsername(username);

        if (false) {
//            login.setCompany(utente.company);

//            password = utente.getPassword();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            GrantedAuthority auth = new SimpleGrantedAuthority("USER");
            authorities.add(auth);
            userDetails = new User("admin", "{noop}" + password, authorities);
        } else {
            throw new UsernameNotFoundException(username);
        }
        return userDetails;
    }
}
