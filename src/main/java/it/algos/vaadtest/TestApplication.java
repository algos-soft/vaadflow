package it.algos.vaadtest;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Project it.algos.vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 05-apr-2018
 * Time: 18:43
 * Spring boot web application initializer.
 * The entry point of the Spring Boot application.
 * <p>
 * Questa classe contiene il metodo 'main' che è il punto di ingresso dell'applicazione Java
 * In fase di sviluppo si possono avere diverse configurazioni, ognuna delle quali punta un ''main' diverso
 * Nel JAR finale (runtime) si può avere una sola classe col metodo 'main'.
 * Nel WAR finale (runtime) occorre (credo) inserire dei servlet di context diversi
 * <p>
 * Questa classe non fa praticamente niente se non avere le Annotation riportate qui
 * Annotated with @SpringBootApplication (obbligatorio)
 * Annotated with @EnableVaadin (obbligatorio)
 * Annotated with @EntityScan (obbligatorio)
 * Annotated with @EnableMongoRepositories (obbligatorio)
 * <p>
 * Tutte le @route/view devono essere comprese nei paths indicati come String[] in @EnableVaadin
 * Una sola view può avere @Route("")
 */
@SpringBootApplication(scanBasePackages = {"it.algos.vaadflow", "it.algos.vaadtest"})
@EnableVaadin({"it.algos.vaadflow.modules", "it.algos.vaadflow.wizard", "it.algos.vaadflow.developer","it.algos.vaadflow.ui", "it.algos.vaadtest.application", "it.algos.vaadtest.modules"})
@EntityScan({"it.algos.vaadflow.modules", "it.algos.vaadtest.modules"})
@EnableMongoRepositories({"it.algos"})
public class TestApplication extends SpringBootServletInitializer {

    /**
     * Constructor
     *
     * @param args eventuali parametri in ingresso
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TestApplication.class, args);
    }// end of constructor


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        return applicationBuilder.sources(TestApplication.class);
    }// end of method

}// end of main class
