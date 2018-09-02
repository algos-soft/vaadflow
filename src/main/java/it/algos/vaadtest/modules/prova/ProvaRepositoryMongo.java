package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 01-set-2018
 * Time: 17:26
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
public class ProvaRepositoryMongo implements ProvaRepository {


    @Override
    public <S extends Prova> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public List<Prova> findAll() {
        return null;
    }

    @Override
    public List<Prova> findAll(Sort sort) {
        return null;
    }

    @Override
    public <S extends Prova> S insert(S s) {
        return null;
    }

    @Override
    public <S extends Prova> List<S> insert(Iterable<S> iterable) {
        return null;
    }

    @Override
    public <S extends Prova> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Prova> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public Page<Prova> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Prova> S save(S s) {
        return null;
    }

    @Override
    public Optional<Prova> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Prova> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Prova prova) {

    }

    @Override
    public void deleteAll(Iterable<? extends Prova> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Prova> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Prova> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Prova> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Prova> boolean exists(Example<S> example) {
        return false;
    }

    public MongoOperations mongoOperations;

    @Autowired
    public ProvaRepositoryMongo(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }


    public Prova findByCode(String code) {
        return mongoOperations.findOne(new Query(Criteria.where("id").is(code)), Prova.class);

//        Optional risultato = this.findById(code);
//        Assert.notNull(risultato, "The given id must not be null!");
//        return (Prova) risultato.get();
    }// end of method

    public List<Prova> findAllByOrderByOrdineAsc() {
//        List risultato = this.findAll();
//        Assert.notNull(risultato, "The collection is empty!");
        return null;
    }// end of method

}// end of class
