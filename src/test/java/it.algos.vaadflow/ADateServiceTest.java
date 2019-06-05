package it.algos.vaadflow;

import it.algos.vaadflow.enumeration.EATime;
import it.algos.vaadflow.service.ADateService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: sab, 10-feb-2018
 * Time: 15:04
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("date")
@DisplayName("Test sul service di elaborazione date")
public class ADateServiceTest extends ATest {


    private static int GIORNO = 12;

    private static int MESE = 7;

    private static int ANNO = 2004;

    @InjectMocks
    public ADateService service;

    // alcuni parametri utilizzati
    private Date dataPrevista = null;

    private Date dataOttenuta = null;

    private LocalDate localDataPrevista = null;

    private LocalDate localDataOttenuta = null;

    private LocalDateTime localDateTimePrevista = null;

    private LocalDateTime localDateTimeOttenuta = null;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
    }// end of method


    @BeforeAll
    public void tearDown() {
        System.out.println("");
        System.out.println("");
        System.out.println("Data settimanale lunga: " + service.getWeekLong(LOCAL_DATE_TIME_DUE));
        System.out.println("Data settimanale breve: " + service.getDayWeekShort(LOCAL_DATE_TIME_DUE));
        System.out.println("");
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Convert java.util.Date to java.time.LocalDate
     * Date HA ore, minuti e secondi
     * LocalDate NON ha ore, minuti e secondi
     * Si perdono quindi le ore i minuti ed i secondi di Date
     *
     * @param data da convertire
     *
     * @return data locale (deprecated)
     */
    @Test
    public void dateToLocalDate() {
        localDataPrevista = LOCAL_DATE_UNO;
        localDataOttenuta = service.dateToLocalDate(DATE_UNO);
        assertEquals(localDataOttenuta, localDataPrevista);
        System.out.println("");
        System.out.println("Convert java.util.Date to java.time.LocalDate: " + DATE_UNO + " -> " + localDataOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.time.LocalDate to java.util.Date
     * LocalDate NON ha ore, minuti e secondi
     * Date HA ore, minuti e secondi
     * La Date ottenuta ha il tempo regolato a mezzanotte
     *
     * @param localDate da convertire
     *
     * @return data (deprecated)
     */
    @Test
    public void localDateToDate() {
        dataPrevista = DATE_UNO;
        dataOttenuta = service.localDateToDate(LOCAL_DATE_UNO);
//        assertEquals(dataOttenuta, dataPrevista);
        System.out.println("");
        System.out.println("Convert java.time.LocalDate to java.util.Date: " + LOCAL_DATE_UNO + " -> " + dataOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.util.Date to java.time.LocalDateTime
     * Date HA ore, minuti e secondi
     * LocalDateTime HA ore, minuti e secondi
     * Non si perde nulla
     *
     * @param data da convertire
     *
     * @return data e ora locale
     */
    @Test
    public void dateToLocalDateTime() {
        localDateTimePrevista = LOCAL_DATE_TIME_DUE;
        localDateTimeOttenuta = service.dateToLocalDateTime(DATE_UNO);
        assertEquals(localDataOttenuta, localDataPrevista);
        System.out.println("");
        System.out.println("Convert java.util.Date to java.time.LocalDateTime: " + DATE_UNO + " -> " + localDateTimeOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.time.LocalDateTime to java.util.Date
     * LocalDateTime HA ore, minuti e secondi
     * Date HA ore, minuti e secondi
     * Non si perde nulla
     *
     * @param localDateTime da convertire
     *
     * @return data (deprecated)
     */
    @Test
    public void localDateTimeToDate() {
        dataPrevista = DATE_DUE;
        dataOttenuta = service.localDateTimeToDate(LOCAL_DATE_TIME_DUE);
        assertEquals(dataOttenuta, dataPrevista);
        System.out.println("");
        System.out.println("Convert java.time.LocalDateTime to java.util.Date: " + LOCAL_DATE_TIME_DUE + " -> " + dataOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.time.LocalDate to java.time.LocalDateTime
     * LocalDate NON ha ore, minuti e secondi
     * LocalDateTime HA ore, minuti e secondi
     * La LocalDateTime ottenuta ha il tempo regolato a mezzanotte
     *
     * @param localDate da convertire
     *
     * @return data con ore e minuti alla mezzanotte
     */
    @Test
    public void localDateToLocalDateTime() {
        localDateTimePrevista = LOCAL_DATE_TIME_DUE;
        localDateTimeOttenuta = service.localDateToLocalDateTime(LOCAL_DATE_DUE);
//        assertEquals(localDateTimeOttenuta, localDateTimePrevista);
        System.out.println("");
        System.out.println("Convert java.time.LocalDate to java.time.LocalDateTime: " + LOCAL_DATE_UNO + " -> " + localDateTimeOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Convert java.time.LocalDateTime to java.time.LocalDate
     * LocalDateTime HA ore, minuti e secondi
     * LocalDate NON ha ore, minuti e secondi
     * Si perdono quindi le ore i minuti ed i secondi di Date
     *
     * @param localDateTime da convertire
     *
     * @return data con ore e minuti alla mezzanotte
     */
    @Test
    public void localDateTimeToLocalDate() {
        localDataPrevista = LOCAL_DATE_DUE;
        localDataOttenuta = service.localDateTimeToLocalDate(LOCAL_DATE_TIME_DUE);
        assertEquals(localDataOttenuta, localDataPrevista);
        System.out.println("");
        System.out.println("Convert java.time.LocalDateTime to java.time.LocalDate: " + LOCAL_DATE_TIME_DUE + " -> " + localDataOttenuta);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Restituisce una stringa nel formato d-M-yy
     * <p>
     * Returns a string representation of the date <br>
     * Not using leading zeroes in day <br>
     * Two numbers for year <b>
     *
     * @param localDate da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    @Test
    public void getWeekLong() {
        previsto = "domenica 5";

        ottenuto = service.getWeekLong(LOCAL_DATE_TIME_DUE);
        assertEquals(ottenuto, previsto);
        System.out.println("");
        System.out.println("Restituisce il giorno della settimana in forma estesa: " + LOCAL_DATE_TIME_DUE + " -> " + ottenuto);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Restituisce la data (senza tempo) in forma breve
     * <p>
     * Returns a string representation of the date <br>
     * Not using leading zeroes in day <br>
     * Two numbers for year <b>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    @Test
    public void getShort() {
        previsto = "05-04-14";

        ottenuto = service.getShort(LOCAL_DATE_TIME_DUE);
        assertEquals(ottenuto, previsto);
        System.out.println("");
        System.out.println("Restituisce la data (senza tempo) in forma breve: " + LOCAL_DATE_TIME_DUE + " -> " + ottenuto);
        System.out.println("");
    }// end of single test




    @SuppressWarnings("javadoc")
    /**
     * Restituisce la data (senza tempo) in forma normale
     * <p>
     * Returns a string representation of the date <br>
     * Not using leading zeroes in day <br>
     * Two numbers for year <b>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    @Test
    public void getDate() {
        previsto = "5-ott-14";

        ottenuto = service.getDate(LOCAL_DATE_TIME_DUE);
        assertEquals(ottenuto, previsto);
        System.out.println("");
        System.out.println("Restituisce la data/time (senza tempo) in forma normale: " + LOCAL_DATE_TIME_DUE + " -> " + ottenuto);
        System.out.println("");

        previsto = "21-ott-14";
        ottenuto = service.getDate(LOCAL_DATE_UNO);
        assertEquals(ottenuto, previsto);
        System.out.println("");
        System.out.println("Restituisce la data (senza tempo) in forma normale: " + LOCAL_DATE_UNO + " -> " + ottenuto);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Restituisce la data completa di tempo
     * <p>
     * Returns a string representation of the date <br>
     * Not using leading zeroes in day <br>
     * Two numbers for year <b>
     *
     * @param localDateTime da rappresentare
     *
     * @return la data sotto forma di stringa
     */
    @Test
    public void getTime() {
        previsto = "5-ott-14 7:04";


//         Date pippo = new Date(1412485480000L); // 5 ottobre 2014, 7 e 12
//long durata=pippo.getTime();
////        Date prova= new Date
//        Date b=DATE_UNO ;
//        Date c=DATE_DUE ;
//        Date a=DATE_TRE ;

        ottenuto = service.getTime(LOCAL_DATE_TIME_DUE);
        assertEquals(ottenuto, previsto);
        System.out.println("");
        System.out.println("Restituisce la data completa di tempo: " + LOCAL_DATE_TIME_DUE + " -> " + ottenuto);
        System.out.println("");
    }// end of single test


    /**
     * Restituisce ora e minuti
     *
     * @param localDateTime da rappresentare
     *
     * @return l'orario sotto forma di stringa
     */
    @Test
    public void getOrario() {
        previsto = "7:04";
        ottenuto = service.getOrario(LOCAL_DATE_TIME_DUE);
        assertEquals(ottenuto, previsto);
        System.out.println("");
        System.out.println("Restituisce l'orario: " + LOCAL_DATE_TIME_DUE + " -> " + ottenuto);
        System.out.println("");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero della settimana dell'anno di una data fornita.
     * Usa Calendar
     *
     * @param data fornita
     *
     * @return il numero della settimana dell'anno
     */
    @Test
    public void getWeekYear() {
        previstoIntero = 43;

        ottenutoIntero = service.getWeekYear(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero della settimana del mese di una data fornita.
     * Usa Calendar
     *
     * @param data fornita
     *
     * @return il numero della settimana del mese
     */
    @Test
    public void getWeekMonth() {
        previstoIntero = 4;

        ottenutoIntero = service.getWeekMonth(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of method


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero del giorno dell'anno di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero del giorno dell'anno
     */
    @Test
    public void getDayYear() {
        previstoIntero = 294;

        ottenutoIntero = service.getDayYear(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero del giorno del mese di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero del giorno del mese
     */
    @Test
    public void getDayOfMonth() {
        previstoIntero = 21;

        ottenutoIntero = service.getDayOfMonth(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero del giorno della settimana di una data fornita.
     * Usa Calendar
     *
     * @param data fornita
     *
     * @return il numero del giorno della settimana (1=dom, 7=sab)
     */
    @Test
    public void getDayWeek() {
        previstoIntero = 3;

        ottenutoIntero = service.getDayWeek(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana di una data fornita.
     *
     * @param localDate fornita
     *
     * @return il giorno della settimana in forma breve
     */
    @Test
    public void getDayWeekShort() {
        previsto = "mar";

        ottenuto = service.getDayWeekShort(LOCAL_DATE_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il giorno della settimana in forma breve
     */
    @Test
    public void getDayWeekShortDate() {
        previsto = "mar";

        ottenuto = service.getDayWeekShort(DATE_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il giorno della settimana in forma estesa
     */
    @Test
    public void getDayWeekFull() {
        previsto = "martedì";

        ottenuto = service.getDayWeekFull(LOCAL_DATE_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il giorno della settimana in forma estesa
     */
    @Test
    public void getDayWeekFullDate() {
        previsto = "martedì";

        ottenuto = service.getDayWeekFull(DATE_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero delle ore di una data fornita.
     * Usa LocalDateTime internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero delle ore
     */
    @Test
    public void getOre() {
        previstoIntero = 7;

        ottenutoIntero = service.getOre(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero dei minuti di una data fornita.
     * Usa LocalDateTime internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero dei minuti
     */
    @Test
    public void getMinuti() {
        previstoIntero = 12;

        ottenutoIntero = service.getMinuti(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero dei secondi di una data fornita.
     * Usa LocalDateTime internamente, perché Date è deprecato
     *
     * @param data fornita
     *
     * @return il numero dei secondi
     */
    @Test
    public void getSecondi() {
        previstoIntero = 0;

        ottenutoIntero = service.getSecondi(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il numero dell'anno di una data fornita.
     * Usa LocalDate internamente, perché Date è deprecato
     *
     * @return il numero dell'anno
     */
    @Test
    public void getYear() {
        previstoIntero = 2014;

        ottenutoIntero = service.getYear(DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Costruisce la data per il 1° gennaio dell'anno corrente.
     *
     * @return primo gennaio dell'anno
     */
    @Test
    public void getPrimoGennaio() {
        localDataPrevista = LocalDate.of(2019, 1, 1);
        localDataOttenuta = service.getPrimoGennaio();
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Costruisce la data per il 1° gennaio dell'anno indicato.
     *
     * @param anno di riferimento 1985
     *
     * @return primo gennaio dell'anno
     */
    @Test
    public void getPrimoGennaioAnno() {
        localDataPrevista = LocalDate.of(1985, 1, 1);
        localDataOttenuta = service.getPrimoGennaio(1985);
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Costruisce la localData per il giorno dell'anno indicato.
     *
     * @param giorno di riferimento (numero progressivo dell'anno)
     *
     * @return localData
     */
    @Test
    public void getLocalDateByDay() {
        localDataPrevista = LocalDate.now();
        int numGiorno = LocalDate.now().getDayOfYear();
        localDataOttenuta = service.getLocalDateByDay(numGiorno);
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    @Test
    public void formattazione() {
        System.out.println("LocalDate di riferimento " + LOCAL_DATE_DUE);
    }// end of single test


    @Test
    public void formattazione2() {
        System.out.println("LocalDateTime di riferimento " + LOCAL_DATE_TIME_DUE);
    }// end of single test


    /**
     * Restituisce la data nella forma del pattern ricevuto
     * <p>
     * Returns a string representation of the date <br>
     * Not using leading zeroes in day <br>
     * Two numbers for year <b>
     *
     * @param localDate   da rappresentare
     * @param patternEnum enumeration di pattern per la formattazione
     *
     * @return la data sotto forma di stringa
     */
    @Test
    public void patternEnumeration() {
        System.out.println("*************");
        System.out.println("Enumeration di possibili formattazioni della data: " + LOCAL_DATE_DUE);
        System.out.println("*************");

        for (EATime eaTime : EATime.values()) {
            ottenuto = service.get(LOCAL_DATE_DUE, eaTime);
            System.out.println("Tipo: " + eaTime.getTag() + " -> " + ottenuto);
        }// end of for cycle
        System.out.println("*************");
        System.out.println("");
    }// end of single test


    @Test
    public void deltaDiUnaOraTraServerWikiEBrowserGac() {
        long longUno = LocalDateTime.of(2019, 1, 15, 17, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long longDue = LocalDateTime.of(2019, 1, 15, 18, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long delta;
        delta = longDue - longUno;
        System.out.println("*************");
        System.out.println("Differenza di un'ora in long: " + text.format(delta));
        System.out.println("*************");
    }// end of single test


    /**
     * Restituisce come stringa (intelligente) una durata espressa in long
     * - Meno di 1 secondo
     * - Meno di 1 minuto
     * - Meno di 1 ora
     * - Meno di 1 giorno
     * - Meno di 1 anno
     *
     * @return durata (arrotondata e semplificata) in forma leggibile
     */
    @Test
    public void toText() {
        long durata = 0;
        int minuto = 60000;

        System.out.println("*************");
        System.out.println("Durata");
        System.out.println("Meno di 1 secondo: " + service.toText(87));
        System.out.println("Mezzo minuto: " + service.toText(30000));
        System.out.println("58 minuti: " + service.toText(58 * minuto));
        System.out.println("118 minuti: " + service.toText(118 * minuto));
        System.out.println("122 minuti: " + service.toText(122 * minuto));
        System.out.println("Meno di 1 giorno: " + service.toText(7500000));
        System.out.println("Meno di 1 anno: " + service.toText(86500000));
        System.out.println("*************");
        service.toText(durata);
    }// end of single test

}// end of class
