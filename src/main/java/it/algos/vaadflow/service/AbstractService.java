package it.algos.vaadflow.service;

import it.algos.vaadflow.application.StaticContextAccessor;

import javax.annotation.PostConstruct;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 18-ott-2018
 * Time: 20:04
 * <p>
 * Superclasse astratta delle librerie xxxService. <br>
 * Serve per 'dichiarare' in un posto solo i riferimenti ad altre classi ed usarli nelle sottoclassi concrete <br>
 * I riferimenti sono 'public' per poterli usare con TestUnit <br>
 */
public abstract class AbstractService {

    /**
     * Service (pattern SINGLETON) recuperato come istanza dalla classe <br>
     * The class MUST be an instance of Singleton Class and is created at the time of class loading <br>
     */
    public AAnnotationService annotation;

    /**
     * Service (pattern SINGLETON) recuperato come istanza dalla classe <br>
     * The class MUST be an instance of Singleton Class and is created at the time of class loading <br>
     */
    public AArrayService array;

    /**
     * Service (pattern SINGLETON) recuperato come istanza dalla classe <br>
     * The class MUST be an instance of Singleton Class and is created at the time of class loading <br>
     */
    public AColumnService column;

    /**
     * Service (pattern SINGLETON) recuperato come istanza dalla classe <br>
     * The class MUST be an instance of Singleton Class and is created at the time of class loading <br>
     */
    public ADateService date;

    /**
     * Service (pattern SINGLETON) recuperato come istanza dalla classe <br>
     * The class MUST be an instance of Singleton Class and is created at the time of class loading <br>
     */
    public AFieldService field;

    /**
     * Service (pattern SINGLETON) recuperato come istanza dalla classe <br>
     * The class MUST be an instance of Singleton Class and is created at the time of class loading <br>
     */
    public AReflectionService reflection;

    /**
     * Service (pattern SINGLETON) recuperato come istanza dalla classe <br>
     * The class MUST be an instance of Singleton Class and is created at the time of class loading <br>
     */
    public ATextService text;


    @PostConstruct
    protected  void postConstruct() {
        this.annotation = AAnnotationService.getInstance();
        this.array = AArrayService.getInstance();
        this.column = AColumnService.getInstance();
        this.date = ADateService.getInstance();
        this.field = AFieldService.getInstance();
        this.reflection = AReflectionService.getInstance();
        this.text = ATextService.getInstance();

        fixIncrociati();
    }// end of constructor


    protected  void fixIncrociati() {
        this.annotation.array = array;
        this.annotation.column = column;
        this.annotation.date = date;
        this.annotation.field = field;
        this.annotation.reflection = reflection;
        this.annotation.text = text;

        this.array.annotation = annotation;
        this.array.column = column;
        this.array.date = date;
        this.array.field = field;
        this.array.reflection = reflection;
        this.array.text = text;

        this.column.annotation = annotation;
        this.column.array = array;
        this.column.date = date;
        this.column.field = field;
        this.column.reflection = reflection;
        this.column.text = text;

        this.date.annotation = annotation;
        this.date.array = array;
        this.date.column = column;
        this.date.field = field;
        this.date.reflection = reflection;
        this.date.text = text;

        this.field.annotation = annotation;
        this.field.array = array;
        this.field.column = column;
        this.field.date = date;
        this.field.reflection = reflection;
        this.field.text = text;

        this.reflection.annotation = annotation;
        this.reflection.array = array;
        this.reflection.column = column;
        this.reflection.date = date;
        this.reflection.field = field;
        this.reflection.text = text;

        this.text.annotation = annotation;
        this.text.array = array;
        this.text.column = column;
        this.text.date = date;
        this.text.field = field;
        this.text.reflection = reflection;
    }// end of method

}// end of class
