package it.algos.vaadflow;

import it.algos.vaadflow.service.AArrayService;
import it.algos.vaadflow.service.AFileService;
import it.algos.vaadflow.service.ATextService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.VUOTA;
import static it.algos.vaadflow.service.AFileService.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: mar, 06-mar-2018
 * Time: 09:57
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("file")
@DisplayName("Test sul service di accesso ai files")
public class AFileServiceTest extends ATest {

    static boolean FLAG_CREAZIONE_INIZIALE = true;

    static boolean FLAG_CANCELLAZIONE_FINALE = false;
//    private static String PATH = "/Users/gac/Documents/IdeaProjects/it.algos.vaadflow/src/main/java/it/algos/it.algos.vaadflow/";
//
//    private static String SOURCES = PATH + "wizard/sources/";
//
//    private static String MODULES = PATH + "modules/";
//
//    private static String DIR = SOURCES + "Prova";
//
//    private static String SLASH = "/";
//
//    private static String READ = "README.txt";
//
//    private static String PACKAGE = "package it.algos.it.algos.vaadflow;\n";
//
//    private static String TESTO = PACKAGE + "public class Alfa {}";
//
//    private static String NOME_FILE_TEST = "TestNonCancellare";

    private static String PATH_DIRECTORY_TEST = "/Users/gac/Desktop/test/";

    private static String PATH_DIRECTORY_UNO = PATH_DIRECTORY_TEST + "Pippo/";

    private static String PATH_DIRECTORY_DUE = PATH_DIRECTORY_TEST + "Possibile/";

    private static String PATH_DIRECTORY_TRE = PATH_DIRECTORY_TEST + "Mantova/";

    private static String PATH_DIRECTORY_QUATTRO = PATH_DIRECTORY_TEST + "Genova/";


    private static String PATH_FILE_UNO = PATH_DIRECTORY_TEST + "Pluto.rtf";

    private static String PATH_FILE_DUE = PATH_DIRECTORY_TEST + "CartellaMancante/Secondo.rtf";

    private static String PATH_FILE_TRE = PATH_DIRECTORY_TEST + "Paperino/Topolino.txt";

    private static String PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA = "/Users/gac/Desktop/test/pluto.rtf";


    private static String PATH_FILE_NO_SUFFIX = PATH_DIRECTORY_TEST + "Topolino";

    private static String PATH_FILE_NON_ESISTENTE = PATH_DIRECTORY_TEST + "Topolino.txt";

    private static String PATH_FILE_NO_PATH = "Users/gac/Desktop/test/Pluto.rtf";


    private static String PATH_DIRECTORY_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA = "/Users/gac/desktop/test";

    private static String PATH_FILE_DELETE = "/Users/gac/Desktop/test/Paperino/Minni.txt";

    private static String VALIDO = "TROVATO";

    @InjectMocks
    private AFileService service;

    @InjectMocks
    private ATextService text;

    @InjectMocks
    private AArrayService array;

    private File unFile;

    private File unaDirectory;

    private String nomeFile;

    private String nomeCompletoFile;

    private String nomeDirectory;

    private String nomeCompletoDirectory;

    private List<File> listaDirectory;

    private List<File> listaFile;


    /**
     * Qui passa una volta sola <br>
     */
    @BeforeAll
    public void setUpIniziale() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(text);
        MockitoAnnotations.initMocks(array);
        service.text = text;
        service.array = array;
        service.array.text = text;
        service.text.array = array;

        if (FLAG_CREAZIONE_INIZIALE) {
            creazioneListe();
            creazioneDirectory();
            creazioneFiles();
        }// end of if cycle

    }// end of method


    /**
     * Qui passa ad ogni test <br>
     */
    @BeforeEach
    public void setUpEach() {
        nomeFile = VUOTA;
        unFile = null;
        unaDirectory = null;
        nomeCompletoDirectory = PATH_DIRECTORY_TEST;
    }// end of method


    /**
     * Qui passa una volta sola <br>
     */
    @AfterAll
    public void setDown() {
        if (FLAG_CANCELLAZIONE_FINALE) {
            fine();
        }// end of if cycle
    }// end of method


    /**
     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
     */
    private void creazioneListe() {
        listaDirectory = new ArrayList<>();
        listaDirectory.add(new File(PATH_DIRECTORY_TEST));
        listaDirectory.add(new File(PATH_DIRECTORY_UNO));
        listaDirectory.add(new File(PATH_DIRECTORY_DUE));
        listaDirectory.add(new File(PATH_DIRECTORY_TRE));
        listaDirectory.add(new File(PATH_DIRECTORY_QUATTRO));

        listaFile = new ArrayList<>();
        listaFile.add(new File(PATH_FILE_UNO));
        listaFile.add(new File(PATH_FILE_DUE));
        listaFile.add(new File(PATH_FILE_TRE));
        listaFile.add(new File(PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA));

    }// end of method


    /**
     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
     * Alla fine verranno cancellati tutti <br>
     * //        nomeCompletoDirectory = PATH_DIRECTORY_TEST;
     * //        unaDirectory = new File(nomeCompletoDirectory);
     * //        unaDirectory.mkdirs();
     */
    private void creazioneDirectory() {
        if (array.isValid(listaDirectory)) {
            for (File directory : listaDirectory) {
                directory.mkdirs();
            }// end of for cycle
        }// end of if cycle
    }// end of method


    /**
     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
     * Alla fine verranno cancellati tutti <br>
     * //        nomeCompletoFile = PATH_FILE_UNO;
     * //        unFile = new File(nomeCompletoFile);
     * //        try { // prova ad eseguire il codice
     * //            unFile.createNewFile();
     * //        } catch (Exception unErrore) { // intercetta l'errore
     * //            System.out.println(unErrore);
     * //        }// fine del blocco try-catch
     */
    private void creazioneFiles() {
        if (array.isValid(listaFile)) {
            for (File unFile : listaFile) {
                try { // prova ad eseguire il codice
                    unFile.createNewFile();
                } catch (Exception unErrore) { // intercetta l'errore
                    creaDirectoryParentAndFile(unFile);
                }// fine del blocco try-catch
            }// end of for cycle
        }// end of if cycle
    }// end of method


    /**
     * Creazioni di una directory 'parent' <br>
     * Se manca il path completo alla creazione di un file, creo la directory 'parent' di quel file <br>
     * Aggiungo la directory alla lista per assicurarne la cancellazione alla fine del test <br>
     * Riprovo la creazione del file <br>
     */
    private void creaDirectoryParentAndFile(File unFile) {
        String parentDirectoryName;
        File parentDirectoryFile = null;
        boolean parentDirectoryCreata = false;

        if (unFile != null) {
            parentDirectoryName = unFile.getParent();
            parentDirectoryFile = new File(parentDirectoryName);
            parentDirectoryCreata = parentDirectoryFile.mkdirs();
        }// end of if cycle

        if (parentDirectoryCreata) {
            try { // prova ad eseguire il codice
                unFile.createNewFile();
                listaDirectory.add(parentDirectoryFile);
            } catch (Exception unErrore) { // intercetta l'errore
                System.out.println("Errore nel path per la creazione di un file");
            }// fine del blocco try-catch
        }// end of if cycle

    }// end of method


    /**
     * Cancellazione finale di tutti i files/directories creati in questo test <br>
     * I files li cancella subito 'al volo', per essere sicuri di svuotare le directory <br>
     * Le directory, che sono vuote, le segnala al System che le chiude alla fine del test <br>
     */
    private void fine() {
        if (array.isValid(listaFile)) {
            for (File file : listaFile) {
                file.delete(); //--cancellazionew immediata
            }// end of for cycle
        }// end of if cycle
        if (array.isValid(listaDirectory)) {
            for (File directory : listaDirectory) {
                directory.deleteOnExit(); //--cancellazionew alla fine del programma (in questo caso il test)
            }// end of for cycle
        }// end of if cycle
    }// end of method


    //    @Test
    public void isEsisteFileZero() {
        nomeFile = "nonEsiste";
        unFile = new File(nomeFile);
        System.out.println(" ");
        System.out.println("file.getName() = " + unFile.getName());
        System.out.println("file.getPath() = " + unFile.getPath());
        System.out.println("file.getAbsolutePath() = " + unFile.getAbsolutePath());

        try { // prova ad eseguire il codice
            System.out.println("file.getCanonicalPath() = " + unFile.getCanonicalPath());
        } catch (Exception unErrore) { // intercetta l'errore
            System.out.println("Errore");
        }// fine del blocco try-catch

        nomeFile = "Maiuscola";
        unFile = new File(nomeFile);
        System.out.println(" ");
        System.out.println("file.getName() = " + unFile.getName());
        System.out.println("file.getPath() = " + unFile.getPath());
        System.out.println("file.getAbsolutePath() = " + unFile.getAbsolutePath());

        try { // prova ad eseguire il codice
            System.out.println("file.getCanonicalPath() = " + unFile.getCanonicalPath());
        } catch (Exception unErrore) { // intercetta l'errore
            System.out.println("Errore");
        }// fine del blocco try-catch

        nomeFile = "/User/pippoz";
        unFile = new File(nomeFile);
        System.out.println(" ");
        System.out.println("file.getName() = " + unFile.getName());
        System.out.println("file.getPath() = " + unFile.getPath());
        System.out.println("file.getAbsolutePath() = " + unFile.getAbsolutePath());

        try { // prova ad eseguire il codice
            System.out.println("file.getCanonicalPath() = " + unFile.getCanonicalPath());
        } catch (Exception unErrore) { // intercetta l'errore
            System.out.println("Errore");
        }// fine del blocco try-catch

        nomeFile = "/User/pippoz/Pluto.rtf";
        unFile = new File(nomeFile);
        System.out.println(" ");
        System.out.println("file.getName() = " + unFile.getName());
        System.out.println("file.getPath() = " + unFile.getPath());
        System.out.println("file.getAbsolutePath() = " + unFile.getAbsolutePath());

        try { // prova ad eseguire il codice
            System.out.println("file.getCanonicalPath() = " + unFile.getCanonicalPath());
        } catch (Exception unErrore) { // intercetta l'errore
            System.out.println("Errore");
        }// fine del blocco try-catch
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
    @Test
    public void isEsisteFileTramiteNome() {
        ottenuto = service.isEsisteFileStr((String) null);
        assertFalse(service.isEsisteFile((String) null));
        assertEquals(PATH_NULLO, ottenuto);
        System.out.println("Risposta se esiste file di nome: " + "null" + " = " + ottenuto);

        nomeCompletoFile = VUOTA;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_NULLO, ottenuto);
        System.out.println("Risposta se esiste file di nome: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = "nonEsiste";
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);
        System.out.println("Risposta se esiste file di nome: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_FILE_NO_SUFFIX;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenuto);
        System.out.println("Risposta se esiste file di nome: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_FILE_NON_ESISTENTE;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(NON_ESISTE_FILE, ottenuto);
        System.out.println("Risposta se esiste file di nome: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_FILE_NO_PATH;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);
        System.out.println("Risposta se esiste file di nome: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_DIRECTORY_UNO;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(NON_E_FILE, ottenuto);
        System.out.println("Risposta se esiste file di nome: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_FILE_UNO;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertTrue(service.isEsisteFile(nomeCompletoFile));
        assertEquals(VUOTA, ottenuto);
        System.out.println("Risposta se esiste file di nome: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertTrue(service.isEsisteFile(nomeCompletoFile));
        assertEquals(VUOTA, ottenuto);
        System.out.println("Risposta se esiste file di nome: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));
    }// end of single test


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
//    @Test
    public void isEsisteFileTramiteFile() {
        ottenuto = service.isEsisteFileStr((File) null);
        assertFalse(service.isEsisteFile((File) null));
        assertEquals(PARAMETRO_NULLO, ottenuto);
        System.out.println("Risposta se esiste File : " + "null" + " = " + ottenuto);

        nomeCompletoFile = VUOTA;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_NULLO, ottenuto);
        System.out.println("Risposta se esiste File: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = "nonEsiste";
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);
        System.out.println("Risposta se esiste File: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_FILE_NO_SUFFIX;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(NON_ESISTE_FILE, ottenuto);
        System.out.println("Risposta se esiste File: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_DIRECTORY_QUATTRO;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenuto);
        System.out.println("Risposta se esiste File: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_FILE_NEW;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(NON_ESISTE_FILE, ottenuto);
        System.out.println("Risposta se esiste File: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_DIRECTORY_NEW;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(NON_E_FILE, ottenuto);
        System.out.println("Risposta se esiste File: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_FILE_ESISTENTE;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertTrue(service.isEsisteFile(unFile));
        assertEquals(VUOTA, ottenuto);
        System.out.println("Risposta se esiste File: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));

        nomeCompletoFile = PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertTrue(service.isEsisteFile(unFile));
        assertEquals(VUOTA, ottenuto);
        System.out.println("Risposta se esiste File: " + nomeCompletoFile + " = " + (ottenuto.equals(VUOTA) ? VALIDO : ottenuto));
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla l'esistenza di una directory <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathDirectoryToBeChecked path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory esiste, false se non sono rispettate le condizioni della richiesta
     */
//    @Test
    public void isEsisteDirectoryTramiteNome() {
        ottenuto = service.isEsisteDirectoryStr((String) null);
        assertFalse(service.isEsisteDirectory((String) null));
        assertEquals(PATH_NULLO, ottenuto);

        ottenuto = service.isEsisteDirectoryStr(VUOTA);
        assertFalse(service.isEsisteDirectory(VUOTA));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoDirectory = "nonEsiste";
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

//        nomeCompletoDirectory = PATH_FILE_ESISTENTE_DUE;
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_DIRECTORY_DUE;
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

//        nomeCompletoDirectory = PATH_FILE_NEW;
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

//        nomeCompletoDirectory = PATH_FILE_ESISTENTE;
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_E_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_DIRECTORY_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA;
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertTrue(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(VUOTA, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla l'esistenza di una directory <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * Controlla che getPath() e getAbsolutePath() siano uguali <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathDirectoryToBeChecked path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory esiste, false se non sono rispettate le condizioni della richiesta
     */
//    @Test
    public void isEsisteDirectoryTramiteFile() {
        ottenuto = service.isEsisteDirectoryStr((File) null);
        assertFalse(service.isEsisteDirectory((File) null));
        assertEquals(PARAMETRO_NULLO, ottenuto);

        unaDirectory = new File(VUOTA);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoDirectory = "nonEsiste";
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

//        nomeCompletoDirectory = PATH_FILE_ESISTENTE_DUE;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_DIRECTORY_DUE;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

//        nomeCompletoDirectory = PATH_FILE_NEW;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

//        nomeCompletoDirectory = PATH_FILE_ESISTENTE;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_E_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_DIRECTORY_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertTrue(service.isEsisteDirectory(unaDirectory));
        assertEquals(VUOTA, ottenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Crea un nuovo file
     * <p>
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Se manca la directory, viene creata dal System <br>
     *
     * @param absolutePathFileWithSuffixToBeCreated path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
//    @Test
    public void creaFileTramiteNome() {
        ottenuto = service.creaFileStr((String) null);
        assertFalse(service.creaFile((String) null));
        assertEquals(PATH_NULLO, ottenuto);

        ottenuto = service.creaFileStr(VUOTA);
        assertFalse(service.creaFile(VUOTA));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoFile = "nonEsiste";
        ottenuto = service.creaFileStr(nomeCompletoFile);
        assertFalse(service.creaFile(nomeCompletoFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

        nomeCompletoFile = PATH_FILE_NO_SUFFIX;
        ottenuto = service.creaFileStr(nomeCompletoFile);
        assertFalse(service.creaFile(nomeCompletoFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenuto);

        nomeCompletoFile = "/Users/gac/Desktop/test/Pa perino/Topolino.abc";
        ottenuto = service.creaFileStr(nomeCompletoFile);
        assertFalse(service.creaFile(nomeCompletoFile));
        assertEquals(NON_CREATO_FILE, ottenuto);


        //--************************
        nomeCompletoFile = PATH_FILE_DELETE;

        //--controlla prima di crearlo - Non esiste
        assertFalse(service.isEsisteFile(nomeCompletoFile));

        //--creazione da testare
        ottenuto = service.creaFileStr(nomeCompletoFile);
        assertTrue(service.creaFile(nomeCompletoFile));

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteFile(nomeCompletoFile));

        //--cancellazione
        service.deleteFile(nomeCompletoFile);

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        //--************************

    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Crea un nuovo file
     * <p>
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Se manca la directory, viene creata dal System <br>
     *
     * @param absolutePathFileWithSuffixToBeCreated path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
//    @Test
    public void creaFileTramiteFile() {
        ottenuto = service.creaFileStr((File) null);
        assertFalse(service.creaFile((File) null));
        assertEquals(PARAMETRO_NULLO, ottenuto);

        unFile = new File(VUOTA);
        ottenuto = service.creaFileStr(unFile);
        assertFalse(service.creaFile(unFile));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoFile = "nonEsiste";
        unFile = new File(nomeCompletoFile);
        ottenuto = service.creaFileStr(unFile);
        assertFalse(service.creaFile(unFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

        nomeCompletoFile = PATH_FILE_NO_SUFFIX;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.creaFileStr(unFile);
        assertFalse(service.creaFile(unFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenuto);

        nomeCompletoFile = "/Users/gac/Desktop/test/Pa perino/Topolino.abc";
        unFile = new File(nomeCompletoFile);
        ottenuto = service.creaFileStr(unFile);
        assertFalse(service.creaFile(unFile));
        assertEquals(NON_CREATO_FILE, ottenuto);

        //--************************
        nomeCompletoFile = PATH_FILE_DELETE;
        unFile = new File(nomeCompletoFile);

        //--controlla prima di crearlo - Non esiste
        assertFalse(service.isEsisteFile(unFile));

        //--creazione da testare
        ottenuto = service.creaFileStr(unFile);
        assertTrue(service.creaFile(unFile));

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteFile(unFile));

        //--cancellazione
        service.deleteFile(unFile);

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteFile(unFile));
        //--************************

    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeCreated path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
//    @Test
    public void creaDirectoryTramiteNome() {
        ottenuto = service.creaDirectoryStr((String) null);
        assertFalse(service.creaDirectory((String) null));
        assertEquals(PATH_NULLO, ottenuto);

        ottenuto = service.creaDirectoryStr(VUOTA);
        assertFalse(service.creaDirectory(VUOTA));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoDirectory = "nonEsiste";
        ottenuto = service.creaDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.creaDirectory(nomeCompletoDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

        nomeCompletoDirectory = "/Users/gac/Desktop/test/Paperino/Topolino.abc";
        ottenuto = service.creaDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.creaDirectory(nomeCompletoDirectory));
        assertEquals(NON_E_DIRECTORY, ottenuto);


        //--************************
        nomeCompletoDirectory = PATH_DIRECTORY_TRE;
        unFile = new File(nomeCompletoDirectory);

        //--controlla prima di crearlo - Non esiste
        assertFalse(service.isEsisteDirectory(unFile));

        //--creazione da testare
        ottenuto = service.creaDirectoryStr(unFile);
        assertTrue(service.creaDirectory(unFile));

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteDirectory(unFile));

        //--cancellazione
        service.deleteDirectory(unFile);

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteDirectory(unFile));
        //--************************

    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeCreated path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
//    @Test
    public void creaDirectoryTramiteFile() {
        ottenuto = service.creaDirectoryStr((File) null);
        assertFalse(service.creaDirectory((File) null));
        assertEquals(PARAMETRO_NULLO, ottenuto);

        unaDirectory = new File(VUOTA);
        ottenuto = service.creaDirectoryStr(unaDirectory);
        assertFalse(service.creaDirectory(unaDirectory));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoDirectory = "nonEsiste";
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.creaDirectoryStr(unaDirectory);
        assertFalse(service.creaDirectory(unaDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

        nomeCompletoDirectory = "/Users/gac/Desktop/test/Pa perino/Topolino.abc";
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.creaDirectoryStr(unaDirectory);
        assertFalse(service.creaDirectory(unaDirectory));
        assertEquals(NON_E_DIRECTORY, ottenuto);

        //--************************
        nomeCompletoDirectory = PATH_DIRECTORY_TRE;
        unaDirectory = new File(nomeCompletoDirectory);

        //--controlla prima di crearlo - Non esiste
        assertFalse(service.isEsisteDirectory(unaDirectory));

        //--creazione da testare
        ottenuto = service.creaDirectoryStr(unaDirectory);
        assertTrue(service.creaDirectory(unaDirectory));

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteDirectory(unaDirectory));

        //--cancellazione
        service.deleteDirectory(unaDirectory);

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteDirectory(unaDirectory));
        //--************************

    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeCanceled path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return true se il file è stato cancellato oppure non esisteva
     */
//    @Test
    public void deleteFileTramiteNome() {
        ottenuto = service.deleteFileStr((String) null);
        assertFalse(service.deleteFile((String) null));
        assertEquals(PATH_NULLO, ottenuto);

        ottenuto = service.deleteFileStr(VUOTA);
        assertFalse(service.deleteFile(VUOTA));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoFile = "pippo";
        ottenuto = service.deleteFileStr(nomeCompletoFile);
        assertFalse(service.deleteFile(nomeCompletoFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

//        nomeCompletoFile = PATH_FILE_NEW;
        ottenuto = service.deleteFileStr(nomeCompletoFile);
        assertFalse(service.deleteFile(nomeCompletoFile));
        assertEquals(NON_ESISTE_FILE, ottenuto);


        //--************************
//        nomeCompletoFile = PATH_FILE_ESISTENTE_TRE;

        //--controlla prima di cancellarlo - Esiste
        if (!service.isEsisteFile(nomeCompletoFile)) {
            service.creaFile(nomeCompletoFile);
        }// end of if cycle
        assertTrue(service.isEsisteFile(nomeCompletoFile));

        //--cancellazione da testare
        assertTrue(service.deleteFile(nomeCompletoFile));

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteFile(nomeCompletoFile));

        //--creazione
        service.creaFile(nomeCompletoFile);

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteFile(nomeCompletoFile));
        //--************************

    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeCanceled path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return true se il file è stato cancellato oppure non esisteva
     */
//    @Test
    public void deleteFileTramiteFile() {
        ottenuto = service.deleteFileStr((File) null);
        assertFalse(service.deleteFile((File) null));
        assertEquals(PARAMETRO_NULLO, ottenuto);

        unFile = new File(VUOTA);
        ottenuto = service.deleteFileStr(unFile);
        assertFalse(service.deleteFile(unFile));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoFile = "pippo";
        unFile = new File(nomeCompletoFile);
        ottenuto = service.deleteFileStr(unFile);
        assertFalse(service.deleteFile(unFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

//        nomeCompletoFile = PATH_FILE_NEW;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.deleteFileStr(unFile);
        assertFalse(service.deleteFile(unFile));
        assertEquals(NON_ESISTE_FILE, ottenuto);

        //--************************
//        nomeCompletoFile = PATH_FILE_ESISTENTE_TRE;
        unFile = new File(nomeCompletoFile);

        //--controlla prima di cancellarlo - Esiste
        if (!service.isEsisteFile(unFile)) {
            service.creaFile(unFile);
        }// end of if cycle
        assertTrue(service.isEsisteFile(unFile));

        //--cancellazione da testare
        assertTrue(service.deleteFile(unFile));

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteFile(unFile));

        //--creazione
        service.creaFile(unFile);

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteFile(unFile));
        //--************************
    }// end of single test


    /**
     * Cancella una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeDeleted path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stato cancellato oppure non esisteva
     */
//    @Test
    public void deleteDirectoryTramiteNome() {
        ottenuto = service.deleteDirectoryStr((String) null);
        assertFalse(service.deleteDirectory((String) null));
        assertEquals(PATH_NULLO, ottenuto);

        ottenuto = service.deleteDirectoryStr(VUOTA);
        assertFalse(service.deleteDirectory(VUOTA));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoDirectory = "pippo";
        ottenuto = service.deleteFileStr(nomeCompletoDirectory);
        assertFalse(service.deleteFile(nomeCompletoDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

//        nomeCompletoDirectory = PATH_FILE_NEW;
        ottenuto = service.deleteFileStr(nomeCompletoDirectory);
        assertFalse(service.deleteFile(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_FILE, ottenuto);


        //--************************
        nomeCompletoDirectory = PATH_FILE_NO_SUFFIX;

        //--controlla prima di cancellarlo - Esiste
        if (!service.isEsisteDirectory(nomeCompletoDirectory)) {
            service.creaDirectory(nomeCompletoDirectory);
        }// end of if cycle
        assertTrue(service.isEsisteDirectory(nomeCompletoDirectory));

        //--cancellazione da testare
        assertTrue(service.deleteDirectory(nomeCompletoDirectory));

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));

        //--creazione
        service.creaDirectory(nomeCompletoDirectory);

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteDirectory(nomeCompletoDirectory));
        //--************************

    }// end of single test


    /**
     * Cancella una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathDirectoryToBeDeleted path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stato cancellato oppure non esisteva
     */
//    @Test
    public void deleteDirectoryTramiteFile() {
        ottenuto = service.deleteDirectoryStr((File) null);
        assertFalse(service.deleteDirectory((File) null));
        assertEquals(PARAMETRO_NULLO, ottenuto);

        ottenuto = service.deleteDirectoryStr(VUOTA);
        assertFalse(service.deleteDirectory(VUOTA));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoDirectory = "pippo";
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.deleteFileStr(unaDirectory);
        assertFalse(service.deleteFile(unaDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

//        nomeCompletoDirectory = PATH_FILE_NEW;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.deleteFileStr(unaDirectory);
        assertFalse(service.deleteFile(unaDirectory));
        assertEquals(NON_ESISTE_FILE, ottenuto);


        //--************************
        nomeCompletoDirectory = PATH_FILE_NO_SUFFIX;
        unaDirectory = new File(nomeCompletoDirectory);

        //--controlla prima di cancellarlo - Esiste
        if (!service.isEsisteDirectory(unaDirectory)) {
            service.creaDirectory(unaDirectory);
        }// end of if cycle
        assertTrue(service.isEsisteDirectory(unaDirectory));

        //--cancellazione da testare
        assertTrue(service.deleteDirectory(unaDirectory));

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteDirectory(unaDirectory));

        //--creazione
        service.creaDirectory(unaDirectory);

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteDirectory(unaDirectory));
        //--************************

    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Scrive un file
     * Se non esiste, lo crea
     *
     * @param nameFileToBeWritten file col path completo
     * @param text                contenuto del file
     * @param sovrascrive         anche se esiste già
     */
//    @Test
    public void scriveFile() {
//        System.out.println("*scriveFile - inizio");
//        statusPrevisto = true;
//        String nomeFile = NOME_FILE_TEST;
//        String testo = TESTO;
//        statusOttenuto = service.scriveFile(nomeFile, testo);
//        assertEquals(statusPrevisto, statusOttenuto);
//
//        statusPrevisto = true;
//        statusOttenuto = service.scriveFile(nomeFile, testo + "pippo", true);
//        assertEquals(statusPrevisto, statusOttenuto);
//
//        service.deleteFile(nomeFile);
//        System.out.println("*scriveFile - fine");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Legge un file
     *
     * @param pathFileToBeWritten nome completo del file
     */
//    @Test
    public void leggeFile() {
//        System.out.println("*leggeFile - inizio");
//        service.scriveFile(nomeCompletoDirectory + NOME_FILE_TEST, TESTO);
//        String nomeFile = NOME_FILE_TEST;
//        String testo = TESTO;
//
//        testoPrevisto = testo;
//        testoOttenuto = service.leggeFile(nomeFile);
//        assertEquals(testoPrevisto, testoOttenuto);
//        System.out.println(testoOttenuto);
//
//        service.deleteFile(nomeFile);
//        System.out.println("*leggeFile - fine");
//
//        nomeFile = "/Users/gac/Documents/IdeaProjects/it.algos.vaadflow/src/main/java/it/algos/it.algos.vaadflow/application/BaseCost.java";
//        testoOttenuto = service.leggeFile(nomeFile);
//        assertNotNull(testoOttenuto);
//        assertTrue(testoOttenuto.length() > 0);
//        System.out.println(testoOttenuto);
//
//        nomeFile = "/Users/gac/Documents/IdeaProjects/it.algos.vaadflow/src/main/java/it/algos/it.algos.vaadflow/application/BaseCostDeleted.java";
//        testoOttenuto = service.leggeFile(nomeFile);
//        assertEquals("", testoOttenuto);
//        assertTrue(testoOttenuto.length() == 0);
    }// end of single test


    //    @Test
    public void getSubdiretories() {
//        ottenutoList = service.getSubDirectories(MODULES);
//        ottenutoList = service.getSubDirectories(MODULES);
    }// end of single test


}// end of class
