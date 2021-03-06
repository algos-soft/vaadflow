package it.algos.vaadflow;


import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.enumeration.EAFieldType;
import it.algos.vaadflow.modules.log.LogService;
import it.algos.vaadflow.modules.logtype.Logtype;
import it.algos.vaadflow.modules.logtype.LogtypeList;
import it.algos.vaadflow.modules.role.Role;
import it.algos.vaadflow.modules.role.RoleList;
import it.algos.vaadflow.service.*;
import it.algos.vaadflow.ui.IAView;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: lun, 25-dic-2017
 * Time: 09:54
 */
public class ATest {

    protected final static String FIELD_NAME_KEY = "id";

    protected final static String FIELD_NAME_ORDINE = "ordine";

    protected final static String FIELD_NAME_CODE = "code";

    protected final static String FIELD_NAME_COMPANY = "company2";

    protected final static String FIELD_NAME_NICKNAME = "nickname";

    protected final static String FIELD_NAME_PASSWORD = "password";

    protected final static String FIELD_NAME_ROLE = "role";

    protected final static String FIELD_NAME_ENABLED = "enabled";

    protected final static String FIELD_NAME_NOTE = "note";

    protected final static String FIELD_NAME_CREAZIONE = "creazione";

    protected final static String FIELD_NAME_MODIFICA = "modifica";

    protected final static String NAME_ORDINE = "ordine";

    protected final static String NAME_CODE = "code";

    protected final static String NAME_ROLE = "role";

    protected final static String HEADER_ORDINE = "#";

    protected final static String HEADER_CODE = "code";

    // alcune date di riferimento
    protected final static Date DATE_UNO = new Date(1413870120000L); // 21 ottobre 2014, 7 e 42

    protected final static Date DATE_DUE = new Date(1412485440000L); // 5 ottobre 2014, 7 e 04

    protected final static Date DATE_TRE = new Date(1412485920000L); // 5 ottobre 2014, 7 e 12

    protected final static Date DATE_QUATTRO = new Date(1394259124000L); // 8 marzo 2014, 7 e 12 e 4

    protected final static LocalDate LOCAL_DATE_UNO = LocalDate.of(2014, 10, 21);

    protected final static LocalDate LOCAL_DATE_DUE = LocalDate.of(2014, 10, 5);

    protected final static LocalDate LOCAL_DATE_TRE = LocalDate.of(2015, 10, 5);

    protected final static LocalDate LOCAL_DATE_QUATTRO = LocalDate.of(2015, 3, 8);

    protected final static LocalDate LOCAL_DATE_VUOTA = LocalDate.of(1970, 1, 1);

    protected final static LocalDate LOCAL_DATE_PRIMO_VALIDO = LocalDate.of(1970, 1, 2);

    protected final static LocalDate LOCAL_DATE_OLD = LocalDate.of(1946, 10, 28);

    protected final static LocalDateTime LOCAL_DATE_TIME_UNO = LocalDateTime.of(2014, 10, 21, 7, 42);

    protected final static LocalDateTime LOCAL_DATE_TIME_DUE = LocalDateTime.of(2014, 10, 5, 7, 4);

    protected final static LocalDateTime LOCAL_DATE_TIME_VUOTA = LocalDateTime.of(1970, 1, 1, 0, 0);

    protected final static LocalDateTime LOCAL_DATE_TIME_PRIMO_VALIDO = LocalDateTime.of(1970, 1, 1, 0, 1);

    protected final static LocalDateTime LOCAL_DATE_TIME_OLD = LocalDateTime.of(1946, 10, 28, 0, 0);

    protected final static LocalTime LOCAL_TIME_UNO = LocalTime.of(7, 42);

    protected final static LocalTime LOCAL_TIME_DUE = LocalTime.of(7, 4);

    protected final static LocalTime LOCAL_TIME_TRE = LocalTime.of(22, 0);

    protected final static LocalTime LOCAL_TIME_QUATTRO = LocalTime.of(6, 0);

    protected final static LocalTime LOCAL_TIME_VUOTO = LocalTime.of(0, 0);

    protected static Field FIELD_ORDINE;

    protected static Field FIELD_CODE;

    protected static Field FIELD_ROLE;

    protected static Class<? extends IAView> ROLE_VIEW_CLASS = RoleList.class;

    protected static Class<? extends AEntity> ROLE_ENTITY_CLASS = Role.class;

    protected static Class<? extends IAView> LOG_VIEW_CLASS = LogtypeList.class;

    protected static Class<? extends AEntity> LOG_ENTITY_CLASS = Logtype.class;

    protected static String TITOLO_WEB = "http://www.algos.it/estudio";

    protected static String TITOLO_WEB_ERRATO = "http://www.pippozbelloz.it/";

    protected static String SEP3 = "/";

    private static String SEP1 = ": ";

    private static String SEP2 = " -> ";

    @InjectMocks
    public AArrayService array;

    @InjectMocks
    public ATextService text;
//    protected EAFieldAccessibility previstaAccessibilità;
    //    protected EAFieldAccessibility ottenutaAccessibilità;
    //    protected EACompanyRequired previstoCompany;
    //    protected EACompanyRequired ottenutoCompany;

    @InjectMocks
    protected AAnnotationService annotation;

    @InjectMocks
    protected AReflectionService reflection;

    @InjectMocks
    LogService logger;

    @InjectMocks
    ADateService date;

    protected Field reflectionJavaField;

    protected String sorgente = "";

    protected String previsto = "";

    protected String ottenuto = "";

    protected boolean previstoBooleano;

    protected boolean ottenutoBooleano;

    protected int sorgenteIntero = 0;

    protected int previstoIntero = 0;

    protected int ottenutoIntero = 0;

    protected long sorgenteLungo = 0;

    protected long previstoLungo = 0;

    protected long ottenutoLungo = 0;

    protected double previstoDouble = 0;

    protected double ottenutoDouble = 0;

    protected List<String> previstoList;

    protected List<String> sorgenteList;

    protected List<String> ottenutoList;

    protected String[] sorgenteMatrice = null;

    protected String[] previstoMatrice = null;

    protected String[] ottenutoMatrice = null;


    protected ArrayList<Field> previstoFieldList;

    protected ArrayList<Field> ottenutoFieldList;

    protected EAFieldType previstoType;

    protected EAFieldType ottenutoType;


    protected void setUpTest() {
        MockitoAnnotations.initMocks(annotation);
        MockitoAnnotations.initMocks(reflection);
        MockitoAnnotations.initMocks(array);
        MockitoAnnotations.initMocks(text);
        MockitoAnnotations.initMocks(logger);
        MockitoAnnotations.initMocks(date);
        annotation.reflection = reflection;
        annotation.text = text;
        array.text = text;
        annotation.array = array;
        reflection.array = array;
        annotation.logger = logger;
        logger.date = date;
        logger.text = text;
        FIELD_ORDINE = reflection.getField(ROLE_ENTITY_CLASS, NAME_ORDINE);
        FIELD_CODE = reflection.getField(ROLE_ENTITY_CLASS, NAME_CODE);
    }// end of method


    protected void print(String message, String sorgente, Object ottenuto) {
        System.out.println(message + SEP1 + sorgente + SEP2 + ottenuto);
    }// end of single test

}// end of class
