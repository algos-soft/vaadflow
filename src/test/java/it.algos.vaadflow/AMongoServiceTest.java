package it.algos.vaadflow;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.IMongodConfig;
//import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
//import de.flapdoodle.embed.mongo.config.Net;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.process.runtime.Network;
import it.algos.vaadflow.service.AMongoService;
import it.algos.vaadtest.TestApplication;
import it.algos.vaadtest.modules.prova.Prova;
import it.algos.vaadtest.modules.prova.ProvaRepository;
import it.algos.vaadtest.modules.prova.ProvaService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 31-ago-2018
 * Time: 08:47
 */
//@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Tag("mongo")
//@DisplayName("Test sul service di accesso ai files")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
public class AMongoServiceTest extends ATest {


//    private MongodExecutable mongodExecutable;
//    private MongoTemplate mongoTemplate;
//
//    @AfterEach
//    void clean() {
//        mongodExecutable.stop();
//    }// end of method
//
//    @BeforeEach
//    void  setup() throws Exception {
//        String ip = "localhost";
//        int port = 27017;
//
//        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
//                .net(new Net(ip, port, Network.localhostIsIPv6()))
//                .build();
//
//        MongodStarter starter = MongodStarter.getDefaultInstance();
//        mongodExecutable = starter.prepare(mongodConfig);
//        mongodExecutable.start();
//        mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "alfa");
//    }// end of method
//
//
//        @Test
//    void test() throws Exception {
//        // given
//        DBObject objectToSave = BasicDBObjectBuilder.start()
//                .add("key", "value")
//                .get();
//
//        Prova prova=new Prova();
//        prova.id="17";
//        prova.code="domani";
//        mongoTemplate.save(prova, "prova");
//
//        Prova prova2=new Prova();
//        prova2.id="15";
//        prova2.code="domani";
//        mongoTemplate.save(prova2, "prova");
//
//        Object listaProva=mongoTemplate.findAll(Prova.class, "prova");
//
//        // when
//        mongoTemplate.save(objectToSave, "collection");
//
//        // then
//        Object lista=mongoTemplate.findAll(DBObject.class, "collection");
//        int a=867;
//    }





//    @InjectMocks
//    private AMongoService mongoService;

//    @InjectMocks
//    MongoEntityInformation metadata;

//    @InjectMocks
//    MongoTemplate mongoOperations;

//    @InjectMocks
//    private ProvaRepositoryImpl repository;

    @InjectMocks
    private ProvaService service;

//    @InjectMocks
//    private Prova prova;

//    private MongoOperations mongo;
//    private MongoClient client;
//    private SimpleMongoDbFactory factory;


    @BeforeAll
    public void setUp() {
//        super.setUpTest();
        MockitoAnnotations.initMocks(this);
//        MockitoAnnotations.initMocks(mongoService);
//        MockitoAnnotations.initMocks(metadata);
//        MockitoAnnotations.initMocks(mongoOperations);
        MockitoAnnotations.initMocks(service);
//        MockitoAnnotations.initMocks(prova);
//        MockitoAnnotations.initMocks(repository);
//        client = new MongoClient("localhost", 27017);
//        factory = new SimpleMongoDbFactory(client, "vaadtest");
//        mongo = new MongoTemplate(factory);
//        mongoService.mongo = mongo;
//        service.repository = (ProvaRepository)repository;
        service.array = array;
        service.text = text;
        service.annotation = annotation;
        service.reflection = reflection;
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Find all
     *
     * @param collection clazz
     *
     * @return lista
     */
    @Test
    public void findAll() {
//        List objLst = mongoService.findAll(Prova.class);
        int a = 87;
    }// end of single test


    public void test(@Autowired MongoTemplate mongoTemplate) {

        Prova objectToSave = new Prova();
        Object beta = service.newEntity(3, "pippo");
        mongoTemplate.save(objectToSave, "prova");

        Object lista = mongoTemplate.findAll(Prova.class, "prova");
        int a = 87;
    }// end of single test

//    @SuppressWarnings("javadoc")
//    /**
//     * Inserts multiple documents into a collection.
//     *
//     * @param lista di elementi da inserire
//     * @param clazz della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void insert() {
//        List<Prova> lista = new ArrayList();
//        Prova entity;
//        lista = repository.findAll();
//        entity = repository.findByCode("destra");
//        service.deleteAll();
//
//        entity = service.newEntity(18, "venerdi");
//        entity.id = "ven";
//        lista.add(entity);
//
//        entity = service.newEntity(18, "martedi");
//        entity.id = "nmar";
//        lista.add(entity);
//
//        entity = service.newEntity(18, "giovedi");
//        entity.id = "gio";
//        lista.add(entity);
//
//        mongoService.insertMany(lista, Prova.class);
//        int a = 87;
//    }// end of single test

}// end of class
