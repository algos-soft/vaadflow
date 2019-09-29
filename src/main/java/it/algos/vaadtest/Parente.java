package it.algos.vaadtest;

import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

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
public class Parente {

    public Parente(){
        System.out.println("Parent-----constructor");
    }

    @PostConstruct
    public void init2(){
        System.out.println("Parent-----PostConstruct");
    }

}