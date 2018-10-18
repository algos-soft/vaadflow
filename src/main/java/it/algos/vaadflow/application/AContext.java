package it.algos.vaadflow.application;

import it.algos.vaadflow.backend.login.ALogin;
import it.algos.vaadflow.modules.company.Company;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 18-ott-2018
 * Time: 10:51
 */
public class AContext {
    private ALogin login;
    private Company company;

    public AContext() {
    } // end of constructor

    public AContext(ALogin login, Company company) {
        this.login = login;
        this.company = company;
    } // end of constructor

    public ALogin getLogin() {
        return login;
    }// end of method

    public void setLogin(ALogin login) {
        this.login = login;
    }// end of method

    public Company getCompany() {
        return company;
    }// end of method

    public void setCompany(Company company) {
        this.company = company;
    }// end of method
}// end of class
