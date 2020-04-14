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

    private static String PATH_DIRECTORY_NEW = "/Users/gac/Desktop/test/Pippo/";

    private static String PATH_DIRECTORY_DUE = "/Users/gac/Desktop/test/Possibile/";

    private static String PATH_DIRECTORY_TRE = "/Users/gac/Desktop/test/Mantova/";

    private static String PATH_DIRECTORY_QUATTRO = "/Users/gac/Desktop/test/Genova/";

    private static String PATH_FILE_NO_SUFFIX = "/Users/gac/Desktop/test/Paperino/Topolino";

    private static String PATH_FILE_NEW = "/Users/gac/Desktop/test/Paperino/Topolino.txt";

    private static String PATH_FILE_ESISTENTE = "/Users/gac/Desktop/test/Pluto.rtf";

    private static String PATH_FILE_NON_ESISTENTE = "/Users/gac/Desktop/pippoz/Pluto.rtf";

    private static String PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA = "/Users/gac/Desktop/test/pluto.rtf";

    private static String PATH_DIRECTORY_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA = "/Users/gac/desktop/test";

    private static String PATH_FILE_DELETE = "/Users/gac/Desktop/test/Paperino/Minni.txt";

    @InjectMocks
    private AFileService service;

    @InjectMocks
    private ATextService text;

    @InjectMocks
    private AArrayService array;

    private boolean statusPrevisto;

    private boolean statusOttenuto;

    private File unFile;

    private File unaDirectory;

    private String nomeFile;

    private String nomeCompletoFile;

    private String nomeDirectory;

    private String nomeCompletoDirectory;

    private String testoPrevisto;

    private String testoOttenuto;


    @BeforeAll
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.initMocks(service);
        MockitoAnnotations.initMocks(text);
        MockitoAnnotations.initMocks(array);
        service.text = text;
        service.array = array;
        service.array.text = text;
        service.text.array = array;
    }// end of method


    @BeforeEach
    public void setUp2() {
        nomeFile = VUOTA;
        unFile = null;
        unaDirectory = null;
        nomeCompletoDirectory = PATH_DIRECTORY_TEST;
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

        ottenuto = service.isEsisteFileStr(VUOTA);
        assertFalse(service.isEsisteFile(VUOTA));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoFile = "nonEsiste";
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

        nomeCompletoFile = PATH_FILE_NON_ESISTENTE;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(NON_ESISTE_FILE, ottenuto);

        nomeCompletoFile = PATH_FILE_NO_SUFFIX;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenuto);

        nomeCompletoFile = PATH_FILE_NEW;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(NON_ESISTE_FILE, ottenuto);

        nomeCompletoFile = PATH_DIRECTORY_NEW;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(NON_E_FILE, ottenuto);

        nomeCompletoFile = PATH_FILE_ESISTENTE;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertTrue(service.isEsisteFile(nomeCompletoFile));
        assertEquals(VUOTA, ottenuto);

        nomeCompletoFile = PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA;
        ottenuto = service.isEsisteFileStr(nomeCompletoFile);
        assertTrue(service.isEsisteFile(nomeCompletoFile));
        assertEquals(VUOTA, ottenuto);
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
    @Test
    public void isEsisteFileTramiteFile() {
        ottenuto = service.isEsisteFileStr((File) null);
        assertFalse(service.isEsisteFile((File) null));
        assertEquals(PARAMETRO_NULLO, ottenuto);

        unFile = new File(VUOTA);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoFile = "nonEsiste";
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

        nomeCompletoFile = PATH_FILE_NON_ESISTENTE;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(NON_ESISTE_FILE, ottenuto);

        nomeCompletoFile = PATH_FILE_NO_SUFFIX;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenuto);

        nomeCompletoFile = PATH_FILE_NEW;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(NON_ESISTE_FILE, ottenuto);

        nomeCompletoFile = PATH_DIRECTORY_NEW;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(NON_E_FILE, ottenuto);

        nomeCompletoFile = PATH_FILE_ESISTENTE;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertTrue(service.isEsisteFile(unFile));
        assertEquals(VUOTA, ottenuto);

        nomeCompletoFile = PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA;
        unFile = new File(nomeCompletoFile);
        ottenuto = service.isEsisteFileStr(unFile);
        assertTrue(service.isEsisteFile(unFile));
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
    @Test
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

        nomeCompletoDirectory = PATH_FILE_NON_ESISTENTE;
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_FILE_NO_SUFFIX;
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_DIRECTORY_DUE;
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_FILE_NEW;
        ottenuto = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_FILE_ESISTENTE;
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
    @Test
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

        nomeCompletoDirectory = PATH_FILE_NON_ESISTENTE;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_FILE_NO_SUFFIX;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_DIRECTORY_DUE;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_FILE_NEW;
        unaDirectory = new File(nomeCompletoDirectory);
        ottenuto = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenuto);

        nomeCompletoDirectory = PATH_FILE_ESISTENTE;
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
    @Test
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
    @Test
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
    @Test
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
    @Test
    public void creaDirectoryTramiteFile() {
        ottenuto = service.creaDirectoryStr((File) null);
        assertFalse(service.creaDirectory((File) null));
        assertEquals(PARAMETRO_NULLO, ottenuto);

        unaDirectory = new File(VUOTA);
        ottenuto = service.creaDirectoryStr(unaDirectory);
        assertFalse(service.creaDirectory(unaDirectory));
        assertEquals(PATH_NULLO, ottenuto);

        nomeCompletoDirectory = "nonEsiste";
        unFile = new File(nomeCompletoDirectory);
        ottenuto = service.creaDirectoryStr(unFile);
        assertFalse(service.creaDirectory(unFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenuto);

        nomeCompletoDirectory = "/Users/gac/Desktop/test/Pa perino/Topolino.abc";
        unFile = new File(nomeCompletoDirectory);
        ottenuto = service.creaDirectoryStr(unFile);
        assertFalse(service.creaDirectory(unFile));
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
     * Cancella un file
     *
     * @param fileToBeDeleted file col path completo
     *
     * @return true se il file è stata cancellata
     * false se non è stato cancellato o se non esiste
     */
//    @Test
    public void deleteFile() {
        nomeFile = "pippo";

        statusOttenuto = service.deleteFile(VUOTA);
        assertFalse(statusOttenuto);

        statusOttenuto = service.deleteFile((File) null);
        assertFalse(statusOttenuto);

        statusOttenuto = service.deleteFile(nomeFile);
        assertFalse(statusOttenuto);

        //--controlla prima di crearlo - Non esiste
        assertFalse(service.isEsisteFile(PATH_FILE_NEW));

        //--creazione
        assertTrue(service.creaFile(PATH_FILE_NEW));

        //--cancellazione da testare
        statusOttenuto = service.deleteFile(PATH_FILE_NEW);
        assertTrue(statusOttenuto);

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteFile(PATH_FILE_NEW));
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Cancella una directory
     *
     * @param directoryToBeDeleted file col path completo
     *
     * @return true se la directory è stata cancellata
     * false se non è stata cancellata o se non esiste
     */
//    @Test
    public void deleteDirectory() {
        service.creaDirectory(PATH_DIRECTORY_DUE);

        statusPrevisto = true;
        statusOttenuto = service.deleteDirectory(PATH_DIRECTORY_DUE);
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = false;
        statusOttenuto = service.deleteDirectory("pippo");
        assertEquals(statusPrevisto, statusOttenuto);
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
