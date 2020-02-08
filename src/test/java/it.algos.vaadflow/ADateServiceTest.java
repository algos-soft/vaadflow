package it.algos.vaadflow;

import it.algos.vaadflow.enumeration.EATime;
import it.algos.vaadflow.service.ADateService;
import it.algos.vaadflow.service.ATextService;
import lombok.extern.slf4j.Slf4j;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
@Slf4j
public class ADateServiceTest extends ATest {


    private static int GIORNO = 12;

    private static int MESE = 7;

    private static int ANNO = 2004;

    @InjectMocks
    public ADateService service;

    @InjectMocks
    public ATextService text;

    // alcuni parametri utilizzati
    private Date dataPrevista = null;

    private Date dataOttenuta = null;

    private LocalDate localData = null;

    private LocalDate localDataPrevista = null;

    private LocalDate localDataOttenuta = null;

    private LocalDateTime localDateTimePrevista = null;

    private LocalDateTime localDateTimeOttenuta = null;

    private LocalTime localTimePrevisto = null;

    private LocalTime localTimeOttenuto = null;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(text);
        service.text = text;
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
     * Convert java.util.Date to java.time.LocalTime
     * Estrae la sola parte di Time
     * Date HA anni, giorni, ore, minuti e secondi
     * LocalTime NON ha anni e giorni
     * Si perdono quindi gli anni ed i giorni di Date
     *
     * @param data da convertire
     *
     * @return time senza ilgiorno
     */
    @Test
    public void dateToLocalTime() {
        localTimePrevisto = LOCAL_TIME_UNO;
        localTimeOttenuto = service.dateToLocalTime(DATE_UNO);
        assertEquals(localTimeOttenuto, localTimePrevisto);
        System.out.println("");
        System.out.println("Convert java.util.Date to java.time.LocalTime: " + DATE_UNO + " -> " + localTimeOttenuto);
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
        previsto = "5-ott-14 alle 7:04";


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


    @SuppressWarnings("javadoc")
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
        System.out.println("Restituisce l'orario di una dateTime: " + LOCAL_DATE_TIME_DUE + " -> " + ottenuto);
        System.out.println("");

        ottenuto = service.getOrario(LOCAL_TIME_DUE);
        assertEquals(ottenuto, previsto);
        System.out.println("");
        System.out.println("Restituisce l'orario di un time: " + LOCAL_TIME_DUE + " -> " + ottenuto);
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
        previstoIntero = 42;

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


    @SuppressWarnings("javadoc")
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
            if (eaTime != EATime.iso8601 && eaTime != EATime.completaOrario) {
                try { // prova ad eseguire il codice
                    ottenuto = service.get(LOCAL_DATE_DUE, eaTime);
                } catch (Exception unErrore) { // intercetta l'errore
                    log.error(unErrore.toString());
                }// fine del blocco try-catch
                System.out.println("Tipo: " + eaTime.getTag() + " -> " + ottenuto);
            }// end of if cycle
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
     * bv
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
        System.out.println("Durata - ingresso in millisecondi");
        System.out.println("Meno di 1 secondo: " + service.toText(87));
        System.out.println("20 sec.: " + service.toText(20000));
        System.out.println("59 secondi: " + service.toText(59000));
        System.out.println("58 minuti: " + service.toText(58 * minuto));
        System.out.println("118 minuti: " + service.toText(118 * minuto));
        System.out.println("122 minuti: " + service.toText(122 * minuto));
        System.out.println("Meno di 1 giorno: " + service.toText(7500000));
        System.out.println("Meno di 1 anno: " + service.toText(86500000));
        System.out.println("*************");

        System.out.println("*************");
        System.out.println("Durata - ingresso in secondi");
        System.out.println("Meno di 1 secondo: " + service.toTextSecondi(0));
        System.out.println("20 sec.: " + service.toTextSecondi(20));
        System.out.println("59 secondi: " + service.toTextSecondi(59));
        System.out.println("58 minuti: " + service.toTextSecondi(58 * 60));
        System.out.println("118 minuti: " + service.toTextSecondi(118 * 60));
        System.out.println("122 minuti: " + service.toTextSecondi(122 * 60));
        System.out.println("Meno di 1 giorno: " + service.toTextSecondi(7500));
        System.out.println("Meno di 1 anno: " + service.toTextSecondi(86500));
        System.out.println("*************");

        System.out.println("*************");
        System.out.println("Durata - ingresso in minuti");
        System.out.println("Meno di 1 secondo: " + service.toTextMinuti(0));
        System.out.println("20 sec.: " + service.toTextMinuti(0));
        System.out.println("59 secondi: " + service.toTextMinuti(0));
        System.out.println("58 minuti: " + service.toTextMinuti(58));
        System.out.println("118 minuti: " + service.toTextMinuti(118));
        System.out.println("122 minuti: " + service.toTextMinuti(122));
        System.out.println("Meno di 1 giorno: " + service.toTextMinuti(7500 / 60));
        System.out.println("Meno di 1 anno: " + service.toTextMinuti(86500 / 60));
        System.out.println("*************");
    }// end of single test


    /**
     * Recupera il primo lunedì precedente al giorno indicato <br>
     */
    @Test
    public void getFirstLunedì() {
        localDataPrevista = LocalDate.of(2019, 6, 24);

        localData = LocalDate.of(2019, 6, 30);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);

        localData = LocalDate.of(2019, 6, 29);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);

        localData = LocalDate.of(2019, 6, 28);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);

        localData = LocalDate.of(2019, 6, 27);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);

        localData = LocalDate.of(2019, 6, 26);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);

        localData = LocalDate.of(2019, 6, 25);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);

        localData = LocalDate.of(2019, 6, 24);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);

        localDataPrevista = LocalDate.of(2019, 6, 17);
        localData = LocalDate.of(2019, 6, 23);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);

        localDataPrevista = LocalDate.of(2019, 7, 1);
        localData = LocalDate.of(2019, 7, 1);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);

        localDataPrevista = LocalDate.of(2019, 7, 1);
        localData = LocalDate.of(2019, 7, 2);
        localDataOttenuta = service.getFirstLunedì(localData);
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    /**
     * Recupera un giorno tot giorni prima o dopo la data corrente <br>
     */
    @Test
    public void getGiornoDelta() {
        sorgenteIntero = -3;
        localDataPrevista = LocalDate.now().minusDays(3);
        localDataOttenuta = service.getGiornoDelta(sorgenteIntero);
        assertEquals(localDataOttenuta, localDataPrevista);

        sorgenteIntero = 0;
        localDataPrevista = LocalDate.now();
        localDataOttenuta = service.getGiornoDelta(sorgenteIntero);
        assertEquals(localDataOttenuta, localDataPrevista);

        sorgenteIntero = 2;
        localDataPrevista = LocalDate.now().plusDays(2);
        localDataOttenuta = service.getGiornoDelta(sorgenteIntero);
        assertEquals(localDataOttenuta, localDataPrevista);

        sorgente = "-3";
        localDataPrevista = LocalDate.now().minusDays(3);
        localDataOttenuta = service.getGiornoDelta(sorgente);
        assertEquals(localDataOttenuta, localDataPrevista);

        sorgente = "0";
        localDataPrevista = LocalDate.now();
        localDataOttenuta = service.getGiornoDelta(sorgente);
        assertEquals(localDataOttenuta, localDataPrevista);

        sorgente = "+2";
        localDataPrevista = LocalDate.now().plusDays(2);
        localDataOttenuta = service.getGiornoDelta(sorgente);
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    /**
     * Durata tra due momenti individuati da ora e minuti <br>
     */
    @Test
    public void getDurata() {
        int oraFine;
        int oraIni;
        int minFine;
        int minIni;

        previstoIntero = 120;
        oraIni = 15;
        oraFine = 17;
        minIni = 0;
        minFine = 0;
        ottenutoIntero = service.getDurata(oraFine, oraIni, minFine, minIni);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 150;
        oraIni = 15;
        oraFine = 17;
        minIni = 0;
        minFine = 30;
        ottenutoIntero = service.getDurata(oraFine, oraIni, minFine, minIni);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 140;
        oraIni = 15;
        oraFine = 17;
        minIni = 10;
        minFine = 30;
        ottenutoIntero = service.getDurata(oraFine, oraIni, minFine, minIni);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 100;
        oraIni = 15;
        oraFine = 17;
        minIni = 30;
        minFine = 10;
        ottenutoIntero = service.getDurata(oraFine, oraIni, minFine, minIni);
        assertEquals(ottenutoIntero, previstoIntero);

    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Costruisce la data per il 1° gennaio dell'anno indicato.
     *
     * @param anno di riferimento
     *
     * @return primo gennaio dell'anno indicato
     */
    @Test
    public void primoGennaio() {
        sorgenteIntero = 2017;
        localDataPrevista = LocalDate.of(2017, 1, 1);
        localDataOttenuta = service.primoGennaio(sorgenteIntero);
        assertEquals(localDataOttenuta, localDataPrevista);

        sorgenteIntero = 2018;
        localDataPrevista = LocalDate.of(2018, 1, 1);
        localDataOttenuta = service.primoGennaio(sorgenteIntero);
        assertEquals(localDataOttenuta, localDataPrevista);

        sorgenteIntero = 2019;
        localDataPrevista = LocalDate.of(2019, 1, 1);
        localDataOttenuta = service.primoGennaio(sorgenteIntero);
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    /**
     * Costruisce la data per il 1° gennaio dell'anno corrente.
     *
     * @return primo gennaio dell'anno corrente
     */
    @Test
    public void primoGennaioCorrente() {
        sorgenteIntero = LocalDate.now().getYear();
        localDataPrevista = LocalDate.of(sorgenteIntero, 1, 1);
        localDataOttenuta = service.primoGennaio();
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Costruisce la data per il 31° dicembre dell'anno indicato.
     *
     * @param anno di riferimento
     *
     * @return ultimo giorno dell'anno indicato
     */
    @Test
    public void trentunDicembre() {
        sorgenteIntero = 2017;
        localDataPrevista = LocalDate.of(2017, 12, 31);
        localDataOttenuta = service.trentunDicembre(sorgenteIntero);
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    /**
     * Costruisce la data per il 31° dicembre dell'anno corrente.
     *
     * @return ultimo giorno dell'anno corrente
     */
    @Test
    public void trentunDicembreCorrente() {
        sorgenteIntero = LocalDate.now().getYear();
        localDataPrevista = LocalDate.of(sorgenteIntero, 12, 31);
        localDataOttenuta = service.trentunDicembre();
        assertEquals(localDataOttenuta, localDataPrevista);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana ed il giorno (numero) del mese di una data fornita.
     * <p>
     * sab 23
     *
     * @param localDate fornita
     *
     * @return il giorno della settimana in forma breve
     */
    @Test
    public void getWeekShort() {
        previsto = "dom 5";

        ottenuto = service.getWeekShort(LOCAL_DATE_DUE);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna il giorno (testo) della settimana ed il giorno (numero) del mese ed il nome del mese di una data fornita.
     * <p>
     * sab 23 apr
     *
     * @param localDate fornita
     *
     * @return il giorno della settimana in forma breve
     */
    @Test
    public void getWeekShortMese() {
        previsto = "dom, 5 ott";

        ottenuto = service.getWeekShortMese(LOCAL_DATE_DUE);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Ritorna la data nel formato standar ISO 8601.
     * <p>
     * 2017-02-16T21:00:00.000+01:00
     *
     * @param localDate fornita
     *
     * @return testo standard ISO
     */
    @Test
    public void getISO() {
        previsto = "2014-03-08T07:12:04";
        ottenuto = service.getISO(DATE_QUATTRO);
        assertEquals(ottenuto, previsto);

        previsto = "2014-10-05T00:00:00";
        ottenuto = service.getISO(LOCAL_DATE_DUE);
        assertEquals(ottenuto, previsto);

        previsto = "2014-10-21T07:42:00";
        ottenuto = service.getISO(LOCAL_DATE_TIME_UNO);
        assertEquals(ottenuto, previsto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Costruisce una data da una stringa in formato ISO 8601
     *
     * @param isoStringa da leggere
     *
     * @return data costruita
     */
    @Test
    public void parseFromISO() {
        dataPrevista = DATE_UNO;
        sorgente = service.getISO(DATE_UNO);
        dataOttenuta = service.dateFromISO(sorgente);
        assertEquals(dataOttenuta, dataPrevista);

        localDataPrevista = LOCAL_DATE_DUE;
        sorgente = service.getISO(LOCAL_DATE_DUE);
        localDataOttenuta = service.localDateFromISO(sorgente);
        assertEquals(localDataOttenuta, localDataPrevista);

        localDateTimePrevista = LOCAL_DATE_TIME_UNO;
        sorgente = service.getISO(LOCAL_DATE_TIME_UNO);
        localDateTimeOttenuta = service.localDateTimeFromISO(sorgente);
        assertEquals(localDateTimeOttenuta, localDateTimePrevista);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Differenza (in giorni) tra due date (LocalDate) <br>
     *
     * @param giornoFine data iniziale
     * @param giornoInizio   data finale
     *
     * @return giorni di differenza
     */
    @Test
    public void differenza() {
        previstoIntero = 16;
        ottenutoIntero = service.differenza(LOCAL_DATE_UNO, LOCAL_DATE_DUE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = -16;
        ottenutoIntero = service.differenza(LOCAL_DATE_DUE, LOCAL_DATE_UNO);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 154;
        ottenutoIntero = service.differenza(LOCAL_DATE_QUATTRO, LOCAL_DATE_DUE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 0;
        ottenutoIntero = service.differenza(LOCAL_DATE_UNO, null);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 0;
        ottenutoIntero = service.differenza(null, LOCAL_DATE_DUE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 0;
        ottenutoIntero = service.differenza((LocalDate) null, (LocalDate) null);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Differenza (in minuti) tra due orari (LocalTime) <br>
     *
     * @param orarioInizio orario iniziale
     * @param orarioFine   orario finale
     *
     * @return minuti di differenza
     */
    @Test
    public void durata() {
        previstoIntero = 38;
        ottenutoIntero = service.durata(LOCAL_TIME_UNO, LOCAL_TIME_DUE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = -38;
        ottenutoIntero = service.durata(LOCAL_TIME_DUE, LOCAL_TIME_UNO);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 858;
        ottenutoIntero = service.durata(LOCAL_TIME_TRE, LOCAL_TIME_UNO);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 896;
        ottenutoIntero = service.durata(LOCAL_TIME_TRE, LOCAL_TIME_DUE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 0;
        ottenutoIntero = service.durata(LOCAL_TIME_TRE, null);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 0;
        ottenutoIntero = service.durata(null, LOCAL_TIME_DUE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 0;
        ottenutoIntero = service.durata(null, null);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Differenza (in ore) tra due orari (LocalTime) <br>
     *
     * @param orarioInizio orario iniziale
     * @param orarioFine   orario finale
     *
     * @return minuti di differenza
     */
    @Test
    public void differenza2() {
        previstoIntero = 1;
        ottenutoIntero = service.differenza(LOCAL_TIME_UNO, LOCAL_TIME_DUE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 23;
        ottenutoIntero = service.differenza(LOCAL_TIME_DUE, LOCAL_TIME_UNO);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 8;
        ottenutoIntero = service.differenza(LOCAL_TIME_QUATTRO, LOCAL_TIME_TRE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 14;
        ottenutoIntero = service.differenza(LOCAL_TIME_TRE, LOCAL_TIME_UNO);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 10;
        ottenutoIntero = service.differenza(LOCAL_TIME_UNO, LOCAL_TIME_TRE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 15;
        ottenutoIntero = service.differenza(LOCAL_TIME_TRE, LOCAL_TIME_DUE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 9;
        ottenutoIntero = service.differenza(LOCAL_TIME_DUE, LOCAL_TIME_TRE);
        assertEquals(ottenutoIntero, previstoIntero);

        previstoIntero = 0;
        ottenutoIntero = service.differenza(LOCAL_TIME_VUOTO, LOCAL_TIME_VUOTO);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


    /**
     * Controlla la validità del localDate
     * Deve esistere (not null)
     * Deve avere valore più recente del 1 gennaio 1970
     *
     * @param localDate in ingresso da controllare
     *
     * @return vero se il localDate soddisfa le condizioni previste
     */
    @Test
    public void isValidDate() {
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(LOCAL_DATE_UNO);
        assertEquals(ottenutoBooleano, previstoBooleano);

        previstoBooleano = true;
        ottenutoBooleano = service.isValid(LOCAL_DATE_PRIMO_VALIDO);
        assertEquals(ottenutoBooleano, previstoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid((LocalDate) null);
        assertEquals(ottenutoBooleano, previstoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid(LOCAL_DATE_VUOTA);
        assertEquals(ottenutoBooleano, previstoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid(LOCAL_DATE_OLD);
        assertEquals(ottenutoBooleano, previstoBooleano);
    }// end of single test


    /**
     * Controlla la validità del localDateTime
     * Deve esistere (not null)
     * Deve avere valore più recente del 1 gennaio 1970, ore zero, minuti zero
     *
     * @param localDateTime in ingresso da controllare
     *
     * @return vero se il localDateTime soddisfa le condizioni previste
     */
    @Test
    public void isValidDateTime() {
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(LOCAL_DATE_TIME_UNO);
        assertEquals(ottenutoBooleano, previstoBooleano);

        previstoBooleano = true;
        ottenutoBooleano = service.isValid(LOCAL_DATE_TIME_PRIMO_VALIDO);
        assertEquals(ottenutoBooleano, previstoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid((LocalDateTime) null);
        assertEquals(ottenutoBooleano, previstoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid(LOCAL_DATE_TIME_VUOTA);
        assertEquals(ottenutoBooleano, previstoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid(LOCAL_DATE_TIME_OLD);
        assertEquals(ottenutoBooleano, previstoBooleano);
    }// end of single test


    /**
     * Controlla la validità del localTime
     * Deve esistere (not null)
     * Deve avere valori delle ore o dei minuti
     *
     * @param localTime in ingresso da controllare
     *
     * @return vero se il localTime soddisfa le condizioni previste
     */
    @Test
    public void isValidTime() {
        previstoBooleano = true;
        ottenutoBooleano = service.isValid(LOCAL_TIME_DUE);
        assertEquals(ottenutoBooleano, previstoBooleano);

        previstoBooleano = false;
        ottenutoBooleano = service.isValid(LOCAL_TIME_VUOTO);
        assertEquals(ottenutoBooleano, previstoBooleano);
    }// end of single test


    /**
     * Restituisce la data e l'ora attuali nella forma del pattern completo
     * <p>
     * Returns a string representation of the date <br>
     * Not using leading zeroes in day <br>
     * Two numbers for year <b>
     * Esempio: domenica, 5-ottobre-2014 <br>
     *
     * @return la data sotto forma di stringa
     */
    @Test
    public void getDataOraComplete() {
        ottenuto = service.getDataOraComplete();
        System.out.println("*************");
        System.out.println("Data e ora attuali nella forma " + EATime.completaOrario.getEsempio());
        System.out.println("*************");
        System.out.println(ottenuto);
        System.out.println("");
    }// end of single test


    /**
     * Sorgente è una data/timestamp del vecchi webambulanze (sql)
     * da cui estrarre l'ora (in formato LocalTime) che dovrebbe esser '7'
     * va gestita la differenza di ora legale (2 ore nell'esempio)
     */
    @Test
    public void oraLegale() {
        String sorgenteTime = "2019-10-04 09:00:00.0000";
        String sorgenteDate = "2019-10-04";
        previstoIntero = 0;

//        DateTime dt = new DateTime( "2010-01-01T12:00:00+01:00") ;
        Timestamp stamp = Timestamp.valueOf(sorgenteTime);
        java.sql.Date data = java.sql.Date.valueOf(sorgenteDate);
        ottenutoIntero = service.getOre(data);
        assertEquals(ottenutoIntero, previstoIntero);
    }// end of single test


}// end of class
