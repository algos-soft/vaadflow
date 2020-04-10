package it.algos.vaadflow.integration;

import it.algos.vaadflow.ATest;
import it.algos.vaadflow.ui.form.AViewForm;
import it.algos.vaadtest.TestApplication;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.test.context.junit4.SpringRunner;

import static it.algos.vaadtest.application.TestCost.TAG_ABC;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 10-apr-2020
 * Time: 07:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ViewFormIntegrationTest extends ATest {

//    @Autowired
//    private AViewForm viewForm;



    @Before
    public void setUpIniziale() {
        Assert.assertNotNull(text);
//        Assert.assertNotNull(viewForm);
    }// end of method


    /**
     * Crea una entity Bio partendo da una Page <br>
     * La entity NON viene salvata <br>
     *
     * @param page scaricata dal server wiki
     *
     * @return entity Bio
     */
    @Test
    public void creaBio() {

//        Assert.assertNotNull(page);
//        Assert.assertEquals(previsto, ottenuto);
    }// end of single test

}// end of test class
