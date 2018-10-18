package it.algos.vaadflow.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.backend.entity.AEntity;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mar, 21-ago-2018
 * Time: 16:04
 * Gestione degli accessi al database MongoDB <br>
 * Classe di libreria; NON deve essere astratta, altrimenti Spring non la costruisce <br>
 * Implementa il 'pattern' SINGLETON; l'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AMongoService.class); <br>
 * 2) AMongoService.getInstance(); <br>
 * 3) @Autowired private AMongoService mongoService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, basta il 'pattern') <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 */
@Service
@Slf4j
public class AMongoService extends AbstractService {

    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * Private final property
     */
    private static final AMongoService INSTANCE = new AMongoService();

    /**
     * Private constructor to avoid client applications to use constructor
     */
    private AMongoService() {
    }// end of constructor

    /**
     * Gets the unique instance of this Singleton.
     *
     * @return the unique instance of this Singleton
     */
    public static AMongoService getInstance() {
        return INSTANCE;
    }// end of static method


    /**
     * Inietta da Spring
     */
    @Autowired
    public MongoOperations mongo;

    @Autowired
    public MongoTemplate template;



    /**
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     * Si usa un @Qualifier(), per avere la sottoclasse specifica
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti
     */
//    public AMongoService(MongoRepository repository, MongoOperations mongo) {
//        this.repository = repository;
//        this.mongo = mongo;
//    }// end of Spring constructor


//    /**
//     * Returns the number of entities available.
//     *
//     * @return the number of entities
//     */
//    public int count() {
//        return (int) repository.count();
//    }// end of method

//    //save user object into "user" collection / table
//    //class name will be used as collection name
//	mongoOperation.save(user);
//
//    //save user object into "tableA" collection
//	mongoOperation.save(user,"tableA");
//
//    //insert user object into "user" collection
//    //class name will be used as collection name
//	mongoOperation.insert(user);
//
//    //insert user object into "tableA" collection
//	mongoOperation.insert(user, "tableA");
//
//    //insert a list of user objects
//	mongoOperation.insert(listofUser);


    /**
     * Count all
     *
     * @param clazz della collezione
     *
     * @return num recs
     */
    public int count(Class<? extends AEntity> clazz) {
        return (int) mongo.count(new Query(), clazz);
    }// end of method


    /**
     * Find single entity
     *
     * @param clazz      della collezione
     * @param entityBean da cercare
     *
     * @return entity
     */
    public AEntity findByEntity(Class<? extends AEntity> clazz, AEntity entityBean) {
        return findById(clazz, entityBean.getId());
    }// end of method


    /**
     * Find single entity
     *
     * @param clazz della collezione
     * @param keyId chiave id
     *
     * @return entity
     */
    public AEntity findById(Class<? extends AEntity> clazz, String keyId) {
        AEntity entity = null;
        Object obj = mongo.findById(keyId, clazz);

        if (obj != null && obj instanceof AEntity) {
            entity = (AEntity) obj;
        }// end of if cycle

        return entity;
    }// end of method


    /**
     * Find single entity
     *
     * @param clazz    della collezione
     * @param property da controllare
     * @param value    da considerare
     *
     * @return entity
     */
    public AEntity findByProperty(Class<? extends AEntity> clazz, String property, Object value) {
        AEntity entity = null;
        Object lista;

        Query searchQuery = new Query(Criteria.where(property).is(value));
        lista = mongo.find(searchQuery, clazz);

        if (lista != null && ((List) lista).size() == 1) {
            entity = (AEntity) ((List) lista).get(0);
        }// end of if cycle

        return entity;
    }// end of method


    /**
     * Find all
     *
     * @param clazz della collezione
     *
     * @return lista
     */
    public List findAll(Class<? extends AEntity> clazz) {
        return mongo.findAll(clazz);
    }// end of method


    /**
     * Insert single document into a collection.
     *
     * @param item  da inserire
     * @param clazz della collezione
     */
    public boolean insert(AEntity item, Class<? extends AEntity> clazz) {
        return insert(item, getCollectionName(clazz));
    }// end of method


    /**
     * Insert single document into a collection.
     *
     * @param item           da inserire
     * @param collectionName della collezione
     */
    public boolean insert(AEntity item, String collectionName) {
        boolean status = false;

        try { // prova ad eseguire il codice
            mongo.insert(item, collectionName);
            status = true;
        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
        }// fine del blocco try-catch

        return status;
    }// end of method


    /**
     * Inserts multiple documents into a collection.
     *
     * @param lista di elementi da inserire
     * @param clazz della collezione
     */
    public void insert(List<? extends AEntity> lista, Class<? extends AEntity> clazz) {
        mongo.insert(lista, clazz);
    }// end of method


    /**
     * Update single document into a collection.
     *
     * @param entityBean da modificare
     * @param clazz      della collezione
     */
    public boolean update(AEntity entityBean, Class<? extends AEntity> clazz) {
        boolean status = false;
        AEntity entity = null;
        entity = findByEntity(clazz, entityBean);

        if (entity != null) {
            mongo.remove(entity);
        }// end of if cycle

        return insert(entityBean, clazz);
    }// end of method


    /**
     * Update multiple documents of a collection.
     * Spazzola la lista e cancella/insert ogni singola entity
     *
     * @param listaEntities di elementi da modificare
     * @param clazz         della collezione
     */
    public boolean updateBulk(List<? extends AEntity> listaEntities, Class<? extends AEntity> clazz) {
        boolean status = false;
        DeleteResult result;

        try { // prova ad eseguire il codice
            result = delete(listaEntities, clazz);
            insert(listaEntities, clazz);
            status = true;
        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
        }// fine del blocco try-catch

        return status;
    }// end of method


    /**
     * Delete a single entity.
     *
     * @param entityBean da cancellare
     *
     * @return lista
     */
    public DeleteResult delete(AEntity entityBean) {
        return mongo.remove(entityBean);
    }// end of method


    /**
     * Delete a list of entities.
     *
     * @param listaEntities di elementi da cancellare
     * @param clazz         della collezione
     *
     * @return lista
     */
    public DeleteResult delete(List<? extends AEntity> listaEntities, Class<? extends AEntity> clazz) {
        List<String> listaId = new ArrayList<String>();

        for (AEntity entity : listaEntities) {
            if (entity != null) {
                listaId.add(entity.id);
            } else {
                log.error("Algos - Manca una entity in AMongoService.delete()");
            }// end of if/else cycle
        }// end of for cycle

        return deleteBulk(listaId, clazz);
    }// end of method


    /**
     * Delete a list of entities.
     *
     * @param listaId di keyID da cancellare
     * @param clazz   della collezione
     *
     * @return lista
     */
    public DeleteResult deleteBulk(List<String> listaId, Class<? extends AEntity> clazz) {
        Bson condition = new Document("$in", listaId);
        Bson filter = new Document("_id", condition);
        return getCollection(clazz).deleteMany(filter);
    }// end of method


    /**
     * Delete a collection.
     *
     * @param clazz della collezione
     *
     * @return lista
     */
    public void drop(Class<? extends AEntity> clazz) {
        this.drop(getCollectionName(clazz));
    }// end of method


    /**
     * Delete a collection.
     *
     * @param collectionName della collezione
     *
     * @return lista
     */
    public void drop(String collectionName) {
        this.mongo.dropCollection(collectionName);
    }// end of method


    /**
     * Delete from a collection.
     *
     * @param clazz    della collezione
     * @param property da controllare
     * @param value    da considerare
     */
    public DeleteResult deleteByProperty(Class<? extends AEntity> clazz, String property, String value) {
        Query searchQuery = new Query(Criteria.where(property).is(value));
        return this.mongo.remove(searchQuery, clazz);
    }// end of method


    /**
     * Nome (minuscolo) della collezione.
     *
     * @param clazz della collezione
     *
     * @return lista
     */
    public String getCollectionName(Class<? extends AEntity> clazz) {
        String collectionName = "";

        if (clazz != null) {
            collectionName = annotation.getCollectionName(clazz);
        }// end of if cycle

        return collectionName;
    }// end of method


    /**
     * Collezione
     *
     * @param clazz della collezione
     *
     * @return lista
     */
    public MongoCollection<Document> getCollection(Class<? extends AEntity> clazz) {
        return mongo.getCollection(getCollectionName(clazz));
    }// end of method


    /**
     * Collezione
     *
     * @param collectionName della collezione
     *
     * @return lista
     */
    public MongoCollection<Document> getCollection(String collectionName) {
        return mongo.getCollection(collectionName);
    }// end of method


}// end of class
