package it.algos.vaadtest.application;

import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mar, 21-ago-2018
 * Time: 09:54
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class TestCost {
	public final static String TAG_CAT = "categoria";
	public final static String TAG_BET = "beta";
	public final static String TAG_ALF = "alfa";
	public final static String TAG_BOL = "bolla";
	public final static String TAG_PRO = "prova";
}// end of class