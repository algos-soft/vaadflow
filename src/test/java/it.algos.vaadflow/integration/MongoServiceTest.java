package it.algos.vaadflow.integration;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.service.AMongoService;
import it.algos.vaadflow.wrapper.AFiltro;
import it.algos.vaadtest.TestApplication;
import it.algos.vaadtest.modules.alfa.Alfa;
import it.algos.vaadtest.modules.prova.Prova;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: dom, 20-ott-2019
 * Time: 21:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MongoServiceTest {


    private static Class<? extends AEntity> PROVA_ENTITY_CLASS = Prova.class;

    private static Class<? extends AEntity> ALFA_ENTITY_CLASS = Alfa.class;

    private static String DATABASE_NAME = "vaadflow";

    private static String PROVA_COLLECTION_NAME = "prova";

    private static String ALFA_COLLECTION_NAME = "prova";

    protected int previstoIntero;

    protected int ottenutoIntero;

    private List<Prova> listaProva;

    private List<String> listaNames;

    private MongoDatabase flowDB;

    private MongoCollection<Document> provaCollection;

    private MongoCollection<Document> alfaCollection;

    /**
     * La injection viene fatta da SpringBoot in automatico <br>
     */
    @Autowired
    private ApplicationContext appContext;

    /**
     * La injection viene fatta da SpringBoot in automatico <br>
     */
    @Autowired
    private AMongoService service;


    @Before
    public void setUp() {
        Assert.assertNotNull(appContext);
        Assert.assertNotNull(service);
        flowDB = service.getDB(DATABASE_NAME);
        Assert.assertNotNull(flowDB);
        provaCollection = service.getCollection(flowDB, PROVA_COLLECTION_NAME);
        Assert.assertNotNull(provaCollection);
        alfaCollection = service.getCollection(flowDB, ALFA_COLLECTION_NAME);
        Assert.assertNotNull(alfaCollection);
    }// end of method


    @Test
    public void count() {
        previstoIntero = 16;
        ottenutoIntero = service.count(PROVA_ENTITY_CLASS);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @Test
    public void list() {
        previstoIntero = 16;
        ottenutoIntero = service.count(PROVA_ENTITY_CLASS);
        assertEquals(ottenutoIntero, previstoIntero);

        listaProva = service.findAll(PROVA_ENTITY_CLASS);

        for (Prova prova : listaProva) {
            System.out.println(prova.code);
        }// end of for cycle

        for (Prova prova : listaProva) {
            System.out.println(prova.descrizione);
        }// end of for cycle

        for (Prova prova : listaProva) {
            System.out.println(prova.ruoli);
        }// end of for cycle

    }// end of single test


    /**
     * Restituisce l'elenco delle collections di un database
     *
     * @param mongoDatabase da scandagliare
     */
    @Test
    public void listCollectionNames() {
        listaNames = service.listCollectionNames(flowDB);
        for (String nome : listaNames) {
            System.out.println("Collection are: " + nome);
        }// end of for cycle

    }// end of single test


    @Test
    public void aggregate() {
        System.out.println("Collection is: " + provaCollection.toString());
        System.out.println("Collection user selected successfully: " + provaCollection.countDocuments() + " elements");
        int a = 87;

        Prova prova = (Prova) service.findByProperty(PROVA_ENTITY_CLASS, "code", "4cod");
        Role role = prova.ruoli.get(2);

//        provaCollection.find(and(gt("dim_cm", 15), lt("dim_cm", 20)));

//        provaCollection.insertOne(
//                new Document()
//                        .append("durataMinuti", 4)
//                        .append("listaA", "Domani"));
//
////                        .append("ruoli", asList(
////                                new Document()
////                                        .append("gamename", "PPA")
////                                        .append("banned", false)
////                                        .append("date", format.parse("2014-10-01T00:00:00Z"))
////                                        .append("score", 11),
////                                new Document()
////                                        .append("gamename", "Test2game")
////                                        .append("banned", false)
////                                        .append("date", format.parse("2014-01-16T00:00:00Z"))
////                                        .append("score", 17))));
//
//        System.out.println("Collection user selected successfully: " + provaCollection.countDocuments() + " elements");
    }// end of single test


    @Test
    public void filtri1() {
        Query query;
        List listaProvaLocale;
        previstoIntero = 20;
        ottenutoIntero = service.count(ALFA_ENTITY_CLASS);
        assertEquals(ottenutoIntero, previstoIntero);

        //--senza filtro, li trova tutti
        listaProva = service.findAll(ALFA_ENTITY_CLASS);
        Assert.assertNotNull(listaProva);
        assertEquals(listaProva.size(), previstoIntero);

        //--condizione ragazzo=true
        previstoIntero = 10;
        query = new Query();
        query.addCriteria(Criteria.where("ragazzo").is(true));
        listaProvaLocale = (List<AEntity>) service.mongoOp.find(query, ALFA_ENTITY_CLASS);
        Assert.assertNotNull(listaProvaLocale);
        assertEquals(listaProvaLocale.size(), previstoIntero);
        System.out.println("");
        for (Alfa prova : (List<Alfa>) listaProvaLocale) {
            System.out.println(prova.descrizione);
        }// end of for cycle

        //--condizione simpatico=true
        previstoIntero = 14;
        query = new Query();
        query.addCriteria(Criteria.where("simpatico").is(true));
        listaProvaLocale = (List<AEntity>) service.mongoOp.find(query, ALFA_ENTITY_CLASS);
        Assert.assertNotNull(listaProvaLocale);
        assertEquals(listaProvaLocale.size(), previstoIntero);
        System.out.println("");
        for (Alfa prova : (List<Alfa>) listaProvaLocale) {
            System.out.println(prova.descrizione);
        }// end of for cycle

        //--condizione ragazzo=true AND simpatico=true
        previstoIntero = 5;
        List<AFiltro> listaCriteriaDefinition=new ArrayList<>();
        CriteriaDefinition criteria1 = Criteria.where("ragazzo").is(true);
        CriteriaDefinition criteria2 = Criteria.where("simpatico").is(true);
        listaCriteriaDefinition.add(new AFiltro(criteria1));
        listaCriteriaDefinition.add(new AFiltro(criteria2));
//        listaProvaLocale = (List<AEntity>) service.mongoOp.find(query, ALFA_ENTITY_CLASS);
        listaProvaLocale = service.findAllByProperty(ALFA_ENTITY_CLASS, listaCriteriaDefinition);
        Assert.assertNotNull(listaProvaLocale);
        assertEquals(previstoIntero, listaProvaLocale.size());
        System.out.println("");
        for (Alfa prova : (List<Alfa>) listaProvaLocale) {
            System.out.println(prova.descrizione);
        }// end of for cycle

    }// end of single test

}// end of class
