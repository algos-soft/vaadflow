package it.algos.vaadflow;

import com.github.fakemongo.Fongo;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import it.algos.vaadtest.modules.prova.Prova;
import it.algos.vaadtest.modules.prova.ProvaRepository;
import it.algos.vaadtest.modules.prova.ProvaRepositoryMongo;
import it.algos.vaadtest.modules.prova.ProvaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 01-set-2018
 * Time: 12:36
 */
//@DataMongoTest
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = TestApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RoleTest {

    @InjectMocks
    SimpleMongoDbFactory mongoDbFactory;
    MongoTemplate mongoTemplate;
    @InjectMocks
    Prova prova;
    @InjectMocks
    ProvaService service;
    @InjectMocks
    ProvaRepositoryMongo repository;
    private Fongo fongo;
    private DB db;
    private DBCollection collection;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(mongoDbFactory);
        mongoTemplate= new MongoTemplate(mongoDbFactory);
//        MockitoAnnotations.initMocks(mongoTemplate);
        MockitoAnnotations.initMocks(prova);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(repository);
        repository.mongoOperations=mongoTemplate;
        service.repository = repository;

        fongo = new Fongo("mongo server 1");
        MockitoAnnotations.initMocks(fongo);

// once you have a DB instance, you can interact with it
// just like you would with a real one.
        db = fongo.getDB("mydb");
        MockitoAnnotations.initMocks(db);

//        db.createCollection("mycollection", new BasicDBObject());
//        collection = db.getCollection("mycollection");
//        collection.insert(new BasicDBObject("name", "jon"));


        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(prova);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(repository);
        service.repository = repository;
    }// end of method

    @Test
    public void readsFirstRole() {

        Prova prova = service.findByKeyUnica("alfa");
        assertNotNull(prova);
    }// end of single test

}// end of test class
