package it.algos.vaadtest;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: Mon, 10-Jun-2019
 * Time: 18:48
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public  class Clente extends Parente {


    public Clente(){
        System.out.println("Child-----constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("Child-----PostConstruct");
    }

}// end of class
