package it.algos.vaadflow;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.modules.mese.Mese;
import it.algos.vaadflow.service.AAnnotationService;
import it.algos.vaadflow.service.AMongoService;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadtest.TestApplication;
import it.algos.vaadtest.modules.prova.Prova;
import it.algos.vaadtest.modules.prova.ProvaService;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

//import de.flapdoodle.embed.mongo.MongodExecutable;
//import de.flapdoodle.embed.mongo.MongodStarter;
//import de.flapdoodle.embed.mongo.config.IMongodConfig;
//import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
//import de.flapdoodle.embed.mongo.config.Net;
//import de.flapdoodle.embed.mongo.distribution.Version;
//import de.flapdoodle.embed.process.runtime.Network;


/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: ven, 31-ago-2018
 * Time: 08:47
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplication.class)
public class AMongoServiceTest extends ATest {

//    private final static int CICLI = 1000;
//
//    private static Class PROVA_ENTITY_CLASS = Prova.class;
//
//    @Autowired
//    public MongoOperations mongoOperations;
//
//    @InjectMocks
//    private AMongoService mongoService;
//
//    @InjectMocks
//    private ProvaService provaService;
//
//    @InjectMocks
//    private AAnnotationService annotation;
//
//    @InjectMocks
//    private ATextService text;
//
//    private List listaProve = new ArrayList();
//
//    private String collectionName = "prova";
//
//    private Prova prova;
//
//
//    @BeforeAll
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        MockitoAnnotations.initMocks(provaService);
//        MockitoAnnotations.initMocks(mongoOperations);
//        MockitoAnnotations.initMocks(mongoService);
//        MockitoAnnotations.initMocks(annotation);
//        MockitoAnnotations.initMocks(text);
//        provaService.array = array;
//        provaService.text = text;
//        provaService.annotation = annotation;
//        provaService.reflection = reflection;
//        provaService.mongo = mongoService;
//        mongoService.mongoOp = mongoOperations;
//        mongoService.annotation = annotation;
//        annotation.text = text;
//        mongoService.text = text;
//        mongoService.reflection = reflection;
//    }// end of method
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Delete a collection.
//     *
//     * @param clazz della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void drop() {
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        if (ottenutoIntero == 0) {
//            previstoIntero = 1;
//            prova = new Prova(91, "alfa1", "Alfetta bella", LocalDateTime.now(),new Mese(),new Mese());
//            mongoService.insert(prova, collectionName);
//            ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//            assertEquals(previstoIntero, ottenutoIntero);
//        }// end of if cycle
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        assertTrue(ottenutoIntero > 0);
//
//        mongoService.drop(PROVA_ENTITY_CLASS);
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        assertEquals(0, ottenutoIntero);
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Delete a collection.
//     *
//     * @param collectionName della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void drop2() {
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        if (ottenutoIntero == 0) {
//            previstoIntero = 1;
//            prova = new Prova(91, "alfa1", "Alfetta bella", LocalDateTime.now(),new Mese(),new Mese());
//            mongoService.insert(prova, collectionName);
//            ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//            assertEquals(previstoIntero, ottenutoIntero);
//        }// end of if cycle
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        assertTrue(ottenutoIntero > 0);
//
//        mongoService.drop(collectionName);
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        assertEquals(0, ottenutoIntero);
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Count all
//     *
//     * @param clazz della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void count() {
//        previstoIntero = 4;
//        ottenutoIntero = mongoService.count(ROLE_ENTITY_CLASS);
//        assertEquals(previstoIntero, ottenutoIntero);
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Find single entity
//     *
//     * @param clazz della collezione
//     * @param keyId chiave id
//     *
//     * @return entity
//     */
//    @Test
//    public void find() {
//        String keyId = "pippoz";
//        int intUno = 935;
//        int intDue = 114;
//        int intTre = 767;
//        String codeUno = "alfa211";
//        String codeDue = "altravolta";
//        String codeTre = "nessuno";
//        String descUno = "sopra la panca";
//        String descDue = "sotto la panca";
//        String descTre = "la capra crepa";
//        reset();
//
//        prova = null;
//        prova = new Prova(intUno, codeUno, descUno, LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//
//        prova = null;
//        prova = new Prova(intDue, codeDue, descDue, LocalDateTime.now(),new Mese(),new Mese());
//        prova.id = keyId;
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//
//        prova = null;
//        prova = new Prova(intTre, codeTre, descTre, LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//
//        prova = null;
//        prova = (Prova) mongoService.findById(PROVA_ENTITY_CLASS, keyId);
//        assertNotNull(prova);
//        assertEquals(codeDue, prova.code);
//
//        reset();
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Find single entity
//     *
//     * @param clazz della collezione
//     * @param entityBean da cercare
//     *
//     * @return entity
//     */
//    @Test
//    public void find2() {
//        String keyId = "paperinox";
//        Prova provaFind;
//        int intUno = 449;
//        int intDue = 121;
//        int intTre = 538;
//        String codeUno = "mancoadesso";
//        String codeDue = "forse";
//        String codeTre = "provaci";
//        String descUno = "ancora mattina";
//        String descDue = "dopo il pomeriggio";
//        String descTre = "viene la sera";
//        reset();
//
//        prova = null;
//        prova = new Prova(intUno, codeUno, descUno, LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//
//        prova = null;
//        prova = new Prova(intDue, codeDue, descDue, LocalDateTime.now(),new Mese(),new Mese());
//        prova.id = keyId;
//        provaFind = prova;
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//
//        prova = null;
//        prova = new Prova(intTre, codeTre, descTre, LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//
//        prova = null;
//        prova = (Prova) mongoService.findByEntity(PROVA_ENTITY_CLASS, provaFind);
//        assertNotNull(prova);
//        assertEquals(codeDue, prova.code);
//
//        reset();
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Find single entity
//     *
//     * @param clazz    della collezione
//     * @param property da controllare
//     * @param value    da considerare
//     *
//     * @return lista
//     */
//    @Test
//    public void find3() {
//        int intUno = 935;
//        int intDue = 114;
//        int intTre = 767;
//        String codeUno = "alfa211";
//        String codeDue = "altravolta";
//        String codeTre = "nessuno";
//        String descUno = "finestra rotta";
//        String descDue = "tetto riparato";
//        String descTre = "televisore guasto";
//        reset();
//
//        prova = new Prova(intUno, codeUno, descUno, LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//        prova = new Prova(intDue, codeDue, descDue, LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//        prova = new Prova(intTre, codeTre, descTre, LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//
//        prova = null;
//        prova = (Prova) mongoService.findByProperty(PROVA_ENTITY_CLASS, NAME_ORDINE, intDue);
//        assertNotNull(prova);
//        assertEquals(codeDue, prova.code);
//
//        reset();
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Find all
//     *
//     * @param collection clazz
//     *
//     * @return lista
//     */
//    @Test
//    public void findAll() {
//        previstoIntero = 4;
//        ottenutoList = mongoService.findAll(ROLE_ENTITY_CLASS);
//        assertNotNull(ottenutoList);
//        ottenutoIntero = ottenutoList.size();
//        assertEquals(previstoIntero, ottenutoIntero);
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Insert single document into a collection.
//     *
//     * @param item  da inserire
//     * @param clazz della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void insert() {
//        sorgenteIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        prova = new Prova(935, "alfa211", "nessuno in vista", LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        assertEquals(sorgenteIntero + 1, ottenutoIntero);
//
//        reset();
//    }// end of method
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Insert single document into a collection.
//     *
//     * @param item  da inserire
//     * @param collectionName della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void insert2() {
//        sorgenteIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        prova = new Prova(874, "alfa874", "domani è un altro giorno", LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, collectionName);
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        assertEquals(sorgenteIntero + 1, ottenutoIntero);
//
//        reset();
//    }// end of method
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Insert single document into a collection.
//     *
//     * @param item  da inserire
//     * @param collectionName della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void insert3() {
//        boolean status;
//        String keyId = "xx";
//        String prima = "provainsert";
//        String dopo = "risultato";
//        String descPrima = "descrizione prima";
//        String descDopo = "descrizione dopo";
//
//        prova = new Prova(999, prima, descPrima, LocalDateTime.now(),new Mese(),new Mese());
//        prova.id = keyId;
//        reset();
//        status = mongoService.insert(prova, PROVA_ENTITY_CLASS);
//        assertTrue(status);
//
//        prova = new Prova(777, dopo, descDopo, LocalDateTime.now(),new Mese(),new Mese());
//        prova.id = keyId;
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//
//        previsto = dopo;
//        ottenuto = prova.code;
//        assertEquals(previsto, ottenuto);
//
//        mongoService.drop(collectionName);
//    }// end of method
//
//
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
//    public void insertMany() {
//        long durata;
//        List<Prova> listaEntities = this.getLista();
//        reset();
//
//        long inizio = System.currentTimeMillis();
//        mongoService.insert(listaEntities, PROVA_ENTITY_CLASS);
//        long fine = System.currentTimeMillis();
//
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        assertEquals(listaEntities.size(), ottenutoIntero);
//        durata = fine - inizio;
//        System.out.println("Tempo per inserire: " + listaEntities.size() + " elementi di prova = " + durata + " millisec.");
//
//        reset();
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Update single document into a collection.
//     *
//     * @param item  da modificare
//     * @param clazz della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void update() {
//        String keyId = "pippoz";
//        int intUno = 935;
//        int intDue = 114;
//        String codeUno = "alfa211";
//        String codeDue = "altravolta";
//        String descUno = "finestra rotta";
//        String descDue = "tetto riparato";
//        reset();
//
//        prova = null;
//        prova = new Prova(intUno, codeUno, descUno, LocalDateTime.now(),new Mese(),new Mese());
//        prova.id = keyId;
//        mongoService.insert(prova, PROVA_ENTITY_CLASS);
//
//        prova = null;
//        prova = new Prova(intDue, codeDue, descDue, LocalDateTime.now(),new Mese(),new Mese());
//        prova.id = keyId;
//        mongoService.update(prova, PROVA_ENTITY_CLASS);
//
//        prova = null;
//        prova = (Prova) mongoService.findById(PROVA_ENTITY_CLASS, keyId);
//        assertNotNull(prova);
//        assertEquals(codeDue, prova.code);
//
//        reset();
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Update multiple documents of a collection.
//     *
//     * @param lista di elementi da modificare
//     * @param clazz della collezione
//     */
//    @Test
//    public void updateList() {
//        long durata;
//        List<Prova> listaOriginale = this.getLista("alfa");
//        List<Prova> listaUpdateBulk = this.getLista("beta");
//        reset();
//
//        mongoService.insert(listaOriginale, PROVA_ENTITY_CLASS);
//
//        long inizio = System.currentTimeMillis();
//        mongoService.updateBulk(listaUpdateBulk, PROVA_ENTITY_CLASS);
//        long fine = System.currentTimeMillis();
//
//        prova = null;
//        prova = (Prova) mongoService.findByProperty(PROVA_ENTITY_CLASS, NAME_ORDINE, 3);
//        assertNotNull(prova);
//        assertEquals("beta3", prova.code);
//
//        durata = fine - inizio;
//        System.out.println("");
//        System.out.println("");
//        System.out.println("Aggiornati " + listaUpdateBulk.size() + " elementi di prova");
//        System.out.println("updateBulk  in: " + durata + " millisec.");
//        System.out.println("");
//        System.out.println("");
//
//        reset();
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Delete a single entity.
//     *
//     * @param entity da cancellare
//     *
//     * @return lista
//     */
//    @Test
//    public void delete() {
//        boolean status;
//        reset();
//
//        prova = new Prova(427, "alfa932", "locomotiva", LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, collectionName);
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        assertEquals(1, ottenutoIntero);
//        mongoService.delete(prova);
//        vuoto();
//
//        prova = new Prova(219, "beta151", "treno merci", LocalDateTime.now(),new Mese(),new Mese());
//        mongoService.insert(prova, collectionName);
//        status = provaService.delete(prova);
//        assertTrue(status);
//        vuoto();
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Delete a list of entities.
//     *
//     * @param lista di elementi da cancellare
//     * @param clazz della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void delete2() {
//        long durata;
//        List<Prova> listaEntitiesUno = this.getLista("alfa");
//        List<Prova> listaEntitiesDue = this.getLista("beta");
//        DeleteResult result;
//        reset();
//
//        mongoService.insert(listaEntitiesUno, Prova.class);
//        previstoIntero = listaEntitiesUno.size();
//        result = mongoService.delete(listaEntitiesUno, PROVA_ENTITY_CLASS);
//        assertNotNull(result);
//        assertEquals(previstoIntero, result.getDeletedCount());
//        vuoto();
//
//        mongoService.insert(listaEntitiesDue, Prova.class);
//        previstoIntero = listaEntitiesDue.size();
//        long inizio = System.currentTimeMillis();
//        ottenutoIntero = provaService.delete(listaEntitiesDue, PROVA_ENTITY_CLASS);
//        long fine = System.currentTimeMillis();
//
//        assertEquals(previstoIntero, ottenutoIntero);
//        durata = fine - inizio;
//        System.out.println("");
//        System.out.println("Delete in AService di " + ottenutoIntero + " elementi di prova in " + durata + " millisec.");
//        vuoto();
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Delete a list of entities.
//     *
//     * @param lista di ObjectId da cancellare
//     * @param clazz della collezione
//     *
//     * @return lista
//     */
//    @Test
//    public void deleteBulk() {
//        long durata;
//        List<Prova> listaEntities = this.getLista("delta");
//        List<String> listaId = new ArrayList<String>();
//        reset();
//
//        mongoService.insert(listaEntities, Prova.class);
//
//        for (AEntity entity : listaEntities) {
//            listaId.add(entity.id);
//        }// end of for cycle
//
//        previstoIntero = listaId.size();
//        long inizio = System.currentTimeMillis();
//        ottenutoIntero = provaService.deleteBulk(listaId, PROVA_ENTITY_CLASS);
//        long fine = System.currentTimeMillis();
//
//        assertEquals(previstoIntero, ottenutoIntero);
//        durata = fine - inizio;
//        System.out.println("");
//        System.out.println("BulkDelete in AService di " + ottenutoIntero + " elementi di prova in " + durata + " millisec.");
//        vuoto();
//    }// end of single test
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Delete from a collection.
//     *
//     * @param clazz    della collezione
//     * @param property da controllare
//     * @param value    da considerare
//     */
//    @Test
//    public void deleteByProperty() {
//
//    }// end of single test
//
//
//    /**
//     * Deletes all entities of the collection.
//     */
//    /**
//     * Pulisce la collection con drop()
//     * Inserisce un notevole numero di entities
//     * Usa deleteAll() per controllare i tempi di esecuzione rispetto al ciclo delete(singleEntity)
//     */
//    @Test
//    public void deleteAll() {
////        int cicli = 1000;
//        int records;
//        boolean cancellati;
//        long durata;
//        List<Prova> listaSingleDelete = this.getLista("alfa");
//        List<Prova> listaBulkDelete = this.getLista("beta");
//
//
//        mongoService.insert(listaSingleDelete, Prova.class);
//
//        long inizio = System.currentTimeMillis();
//        for (AEntity entityBean : listaSingleDelete) {
//            provaService.delete(entityBean);
//        }// end of for cycle
//        long fine = System.currentTimeMillis();
//
//        mongoService.insert(listaBulkDelete, Prova.class);
//
//
//        long inizio2 = System.currentTimeMillis();
//        cancellati = provaService.deleteAll();
//        long fine2 = System.currentTimeMillis();
//
//
//        durata = fine - inizio;
//        System.out.println("");
//        System.out.println("");
//        System.out.println("Cancellati tutti = " + cancellati);
//        System.out.println("Tempo delete singolo di: " + listaSingleDelete.size() + " elementi di prova = " + durata + " millisec.");
//        durata = fine2 - inizio2;
//        System.out.println("Tempo per bulk delete di: " + listaSingleDelete.size() + " elementi di prova = " + durata + " millisec.");
//        System.out.println("");
//        System.out.println("");
//    }// end of single test
//
//    //    public void test(@Autowired MongoTemplate mongoTemplate) {
////
////        Prova objectToSave = new Prova();
////        Object beta = provaService.newEntity(3, "pippo");
////        mongoTemplate.save(objectToSave, "prova");
////
////        Object lista = mongoTemplate.findAll(Prova.class, "prova");
////        int a = 87;
////    }// end of single test
////
////    @SuppressWarnings("javadoc")
////    /**
////     * Inserts multiple documents into a collection.
////     *
////     * @param lista di elementi da inserire
////     * @param clazz della collezione
////     *
////     * @return lista
////     */
////    @Test
////    public void insert() {
////        List<Prova> lista = new ArrayList();
////        Prova entity;
////        lista = repository.findAll();
////        entity = repository.findByCode("destra");
////        service.deleteAll();
////
////        entity = service.newEntity(18, "venerdi");
////        entity.id = "ven";
////        lista.add(entity);
////
////        entity = service.newEntity(18, "martedi");
////        entity.id = "nmar";
////        lista.add(entity);
////
////        entity = service.newEntity(18, "giovedi");
////        entity.id = "gio";
////        lista.add(entity);
////
////        mongoService.insertMany(lista, Prova.class);
////        int a = 87;
////    }// end of single test
//
//
//    private void reset() {
//        mongoService.drop(PROVA_ENTITY_CLASS);
//        vuoto();
//    }// end of method
//
//
//    private void vuoto() {
//        ottenutoIntero = mongoService.count(PROVA_ENTITY_CLASS);
//        assertEquals(0, ottenutoIntero);
//    }// end of method
//
//
//    private List<Prova> getLista() {
//        return this.getLista(CICLI, "alfa");
//    }// end of method
//
//
//    private List<Prova> getLista(String nameCode) {
//        return this.getLista(CICLI, nameCode);
//    }// end of method
//
//
//    private List<Prova> getLista(int cicli, String nameCode) {
//        List<Prova> listaEntities = new ArrayList<>();
//
//        for (int k = 0; k < cicli; k++) {
//            prova = new Prova(k, nameCode + k, "", LocalDateTime.now(),new Mese(),new Mese());
//            prova.id = "alfa" + k;
//            listaEntities.add(prova);
//        }// end of for cycle
//
//        return listaEntities;
//    }// end of method
//
//
//    /**
//     * Recupera il valore del parametro internalQueryExecMaxBlockingSortBytes
//     *
//     * @return lista
//     */
//    @Test
//    public void getMaxBlockingSortBytes() {
//        mongoService.getMaxBlockingSortBytes();
//    }// end of method
//
//
//    @SuppressWarnings("javadoc")
//    /**
//     * Regola il valore del parametro internalQueryExecMaxBlockingSortBytes
//     *
//     * @param maxBytes da regolare
//     *
//     * @return true se il valore è stato acquisito dal database
//     */
//    @Test
//    public void setMaxBlockingSortBytes() {
//        int temporary = 22444777;
//
//        //--modifica
//        assertTrue(mongoService.setMaxBlockingSortBytes(temporary));
//
//        //--ripristina
//        assertTrue(mongoService.setMaxBlockingSortBytes(AMongoService.EXPECTED_ALGOS_MAX_BYTES));
//    }// end of method
//
//
//    //    @Test
//    public void checkRamSize() {
//        int hight = 50151432;
//        int low = 22444777;
//        MongoClient mongoClient = new MongoClient("localhost", 27017);
//        MongoDatabase database = mongoClient.getDatabase("admin");
//        Document param;
//
//        //--questo funziona sul terminale
//        // db.runCommand( { getParameter: '*'})
//        // db.runCommand( { getParameter : 1, "internalQueryExecMaxBlockingSortBytes" : 1 } )
//        //--end
//
//        //--questo funziona per tutti i parametri
//        param = database.runCommand(new Document("getParameter", "*"));
//        System.out.println("");
//        System.out.println("");
//        System.out.println("Parametri di mongoDB");
//        System.out.println(param.toJson());
//
//        //--questo funziona per un singolo parametro
//        Map<String, Object> mappaLegge = new LinkedHashMap<>();
//        mappaLegge.put("getParameter", 1);
//        mappaLegge.put("internalQueryExecMaxBlockingSortBytes", 1);
//        param = database.runCommand(new Document(mappaLegge));
//        System.out.println("");
//        System.out.println("");
//        System.out.println("Legge parametro internalQueryExecMaxBlockingSortBytes");
//        System.out.println(param.toJson());
//        System.out.println("");
//
//        Map<String, Object> mappaScrive = new LinkedHashMap<>();
//        mappaScrive.put("setParameter", 1);
//        mappaScrive.put("internalQueryExecMaxBlockingSortBytes", low);
//        param = database.runCommand(new Document(mappaScrive));
//        System.out.println("");
//        System.out.println("Scrive parametro internalQueryExecMaxBlockingSortBytes");
//        System.out.println(param.toJson());
//        param = database.runCommand(new Document(mappaLegge));
//        System.out.println("");
//        System.out.println("Controlla parametro internalQueryExecMaxBlockingSortBytes");
//        System.out.println(param.toJson());
//
//        //--rimette a posto
//        Map<String, Object> mappaScriveOld = new LinkedHashMap<>();
//        mappaScriveOld.put("setParameter", 1);
//        mappaScriveOld.put("internalQueryExecMaxBlockingSortBytes", 50151432);
//        param = database.runCommand(new Document(mappaScriveOld));
//        System.out.println("");
//        System.out.println("Riscrive parametro internalQueryExecMaxBlockingSortBytes");
//        System.out.println(param.toJson());
//        param = database.runCommand(new Document(mappaLegge));
//        System.out.println("");
//        System.out.println("Ricontrolla parametro internalQueryExecMaxBlockingSortBytes");
//        System.out.println(param.toJson());
//
//    }// end of single test

}// end of class
