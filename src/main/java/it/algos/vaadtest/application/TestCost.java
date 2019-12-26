package it.algos.vaadtest.application;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

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

    public final static String TAG_GAM = "gamma";

    public final static String TAG_ALF = "alfa";

    public final static String TAG_PIP = "pippo";

    public final static String TAG_DEL = "delta";

    public final static String TAG_NOP = "nopresenter";

    public final static String TAG_CAT = "categoria";

    public final static String TAG_BET = "beta";

    public final static String TAG_ABC = "abc";

    public final static String TAG_BOL = "bolla";

    public final static String TAG_PRO = "prova";

    public final static String TAG_VIEW_FORM = "provaView";

    public final static String PREF_LOCAL_DATA = "prefLocalData";

    public final static String PREF_LOCAL_DATA_TIME = "prefDataTime";

    public final static String PREF_LOCAL_TIME = "prefTime";

}// end of class