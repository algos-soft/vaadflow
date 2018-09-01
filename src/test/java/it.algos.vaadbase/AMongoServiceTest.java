package it.algos.vaadbase;

import com.mongodb.MongoClient;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.modules.role.RoleRepository;
import it.algos.vaadflow.modules.role.RoleService;
import it.algos.vaadflow.service.AMongoService;
import it.algos.vaadtest.modules.prova.Prova;
import it.algos.vaadtest.modules.prova.ProvaRepository;
import it.algos.vaadtest.modules.prova.ProvaService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 31-ago-2018
 * Time: 08:47
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("mongo")
@DisplayName("Test sul service di accesso ai files")
public class AMongoServiceTest extends ATest {


    @InjectMocks
    private AMongoService mongoService;

    @Mock
    private ProvaRepository repository;

    @InjectMocks
    private ProvaService service;

    @InjectMocks
    private Prova prova;

    private MongoOperations mongo;
    private MongoClient client;
    private SimpleMongoDbFactory factory;


    @BeforeAll
    public void setUp() {
        super.setUpTest();
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(mongoService);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(prova);
        MockitoAnnotations.initMocks(repository);
        client = new MongoClient("localhost", 27017);
        factory = new SimpleMongoDbFactory(client, "vaadtest");
        mongo = new MongoTemplate(factory);
        mongoService.mongo = mongo;
        service.repository = repository;
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
        List objLst = mongoService.findAll(Prova.class);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Inserts multiple documents into a collection.
     *
     * @param lista di elementi da inserire
     * @param clazz della collezione
     *
     * @return lista
     */
    @Test
    public void insert() {
        List<Prova> lista = new ArrayList();
        Prova entity;

        lista = repository.findAllByOrderByOrdineAsc();
        lista = repository.findAll();

        entity = service.newEntity(3,"primo");
        entity = service.crea("ottobre");
        entity = service.findOrCrea("terzosecondo");



        entity = new Prova();
        entity.code="mariolino";
        entity.id = "25681";
        lista.add(entity);

        entity = new Prova();
        entity.code="poveretto";
        entity.id = "destra";
        lista.add(entity);

        entity = new Prova();
        entity.code="amicomio";
        entity.id = "sopra";
        lista.add(entity);

        mongoService.insertMany(lista, Prova.class);
        int a = 87;
    }// end of single test

}// end of class
