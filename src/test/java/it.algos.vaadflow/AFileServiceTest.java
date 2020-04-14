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

import static it.algos.vaadflow.application.FlowCost.SLASH;
import static it.algos.vaadflow.application.FlowCost.VUOTA;
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

    private static String PATH_FILE_NEW = "/Users/gac/Desktop/test/Paperino/Topolino.txt";

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


    @Test
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
     * Controlla l'esistenza di un file
     * Il path deve essere completo, altrimenti assume che sia nella directory in uso corrente
     * Deve comprendere anche l'estensione
     * Una volta costruito il file, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathFileToBeChecked path completo del file che DEVE cominciare con '/' SLASH
     *
     * @return true se il file esiste
     * false se non è un file o se non esiste
     */
    @Test
    public void isEsisteFile() {
        nomeFile = "nonEsiste";
        statusPrevisto = false;

        statusOttenuto = service.isEsisteFile((String) null);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.isEsisteFile(VUOTA);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.isEsisteFile(nomeFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = "src";
        statusPrevisto = false;
        statusOttenuto = service.isEsisteFile(nomeFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = "pippoz";
        nomeCompletoFile = nomeCompletoDirectory + SLASH + nomeFile;
        statusPrevisto = false;
        statusOttenuto = service.isEsisteFile(nomeCompletoFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = "Pluto";
        nomeCompletoFile = nomeCompletoDirectory + nomeFile;
        statusPrevisto = false;
        statusOttenuto = service.isEsisteFile(nomeCompletoFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = "pluto.rtf";
        nomeCompletoFile = nomeCompletoDirectory + nomeFile;
        statusPrevisto = true;
        statusOttenuto = service.isEsisteFile(nomeCompletoFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = "Pluto.rtf";
        nomeCompletoFile = nomeCompletoDirectory + nomeFile;
        statusPrevisto = true;
        statusOttenuto = service.isEsisteFile(nomeCompletoFile);
        assertEquals(statusPrevisto, statusOttenuto);
    }// end of single test


    /**
     * Controlla l'esistenza di un file
     * Il path deve essere completo, altrimenti assume che sia nella directory in uso corrente
     * Deve comprendere anche l'estensione
     * Una volta costruito il file, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathFileToBeChecked path completo del file che DEVE cominciare con '/' SLASH
     *
     * @return true se il file esiste
     * false se non è un file o se non esiste
     */
    @Test
    public void isEsisteFile2() {
        nomeFile = "nonEsiste";
        unFile = new File(nomeFile);
        statusPrevisto = false;

        statusOttenuto = service.isEsisteFile((File) null);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.isEsisteFile(unFile);
        assertEquals(statusPrevisto, statusOttenuto);
        print("Esiste file", nomeFile, statusOttenuto);

        unFile = null;
        statusOttenuto = service.isEsisteFile(unFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = "src";
        unFile = new File(nomeFile);
        statusPrevisto = false;
        statusOttenuto = service.isEsisteFile(unFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = "pippoz";
        nomeCompletoFile = nomeCompletoDirectory + SLASH + nomeFile;
        unFile = new File(nomeCompletoFile);
        statusPrevisto = false;
        statusOttenuto = service.isEsisteFile(unFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = "Pluto.rtf";
        nomeCompletoFile = nomeCompletoDirectory + nomeFile;
        unFile = new File(nomeCompletoFile);
        statusPrevisto = true;
        statusOttenuto = service.isEsisteFile(unFile);
        assertEquals(statusPrevisto, statusOttenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla l'esistenza di una directory
     * Il path deve essere completo, altrimenti assume che sia nella directory in uso corrente
     * Deve comprendere anche l'estensione
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathDirectoryToBeChecked nome completo della directory
     *
     * @return true se la directory esiste
     * false se non è una directory o se non esiste
     */
    @Test
    public void isEsisteDirectory() {
        nomeDirectory = "nonEsiste";
        statusPrevisto = false;

        statusOttenuto = service.isEsisteDirectory((String) null);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.isEsisteDirectory(VUOTA);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.isEsisteDirectory(nomeDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.isEsisteDirectory(new File(nomeDirectory));
        assertEquals(statusPrevisto, statusOttenuto);

        nomeDirectory = "src";
        statusPrevisto = false;

        statusOttenuto = service.isEsisteDirectory(nomeDirectory);
        assertEquals(statusPrevisto, statusOttenuto);
        System.out.println("*isEsisteDirectory - fine");

        nomeCompletoDirectory = PATH_DIRECTORY_TEST;
        statusPrevisto = true;
        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeCompletoDirectory = PATH_DIRECTORY_TEST + "Pippo";
        statusPrevisto = true;
        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeCompletoDirectory = PATH_DIRECTORY_TEST + "Pluto";
        statusPrevisto = false;
        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Controlla l'esistenza di una directory
     * Il path deve essere completo, altrimenti assume che sia nella directory in uso corrente
     * Deve comprendere anche l'estensione
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param absolutePathDirectoryToBeChecked nome completo della directory
     *
     * @return true se la directory esiste
     * false se non è una directory o se non esiste
     */
    @Test
    public void isEsisteDirectory2() {
        nomeDirectory = "nonEsiste";
        unaDirectory = new File(nomeDirectory);
        statusPrevisto = false;

        statusOttenuto = service.isEsisteFile((File) null);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.isEsisteFile(nomeDirectory);
        assertEquals(statusPrevisto, statusOttenuto);
        print("Esiste directory", nomeDirectory, statusOttenuto);

        unaDirectory = null;
        statusOttenuto = service.isEsisteFile(unaDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeDirectory = "src";
        unaDirectory = new File(nomeDirectory);
        statusPrevisto = false;
        statusOttenuto = service.isEsisteFile(unaDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeDirectory = "pippoz";
        nomeCompletoDirectory = PATH_DIRECTORY_TEST + nomeDirectory;
        unaDirectory = new File(nomeCompletoDirectory);
        statusPrevisto = false;
        statusOttenuto = service.isEsisteFile(unaDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeDirectory = "Pluto.rtf";
        nomeCompletoDirectory = PATH_DIRECTORY_TEST + nomeDirectory;
        unaDirectory = new File(nomeCompletoDirectory);
        statusPrevisto = true;
        statusOttenuto = service.isEsisteFile(unaDirectory);
        assertEquals(statusPrevisto, statusOttenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Crea una nuova directory
     * La directory DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     *
     * @param absolutePathDirectoryToBeCreated file col path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stata creata oppure esisteva già
     * false se non è una directory o se non esiste
     */
    @Test
    public void creaDirectory() {
        statusPrevisto = false;
        nomeCompletoDirectory = PATH_DIRECTORY_TEST + "Topolino";

        statusOttenuto = service.creaDirectory((String) null);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.creaDirectory(VUOTA);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.creaDirectory("Pippo");
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = false;
        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = true;
        statusOttenuto = service.creaDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = true;
        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        service.deleteDirectory(nomeCompletoDirectory);

        statusPrevisto = false;
        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Crea un nuovo file
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Se manca la directory, la crea
     *
     * @param absolutePathFileToBeCreatedWithSuffix path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return true se il file è stato creato oppure esisteva già
     * false se non è un file o se non esiste
     */
    @Test
    public void creaFile() {
        nomeFile = PATH_FILE_DELETE;
        statusPrevisto = true;
        statusOttenuto = service.creaFile(nomeFile);
        assertEquals(statusPrevisto, statusOttenuto);
        assertTrue(service.deleteFile(nomeFile));

        nomeFile = "alfa.java";
        statusPrevisto = false;
        statusOttenuto = service.creaFile(nomeFile);
        assertEquals(statusPrevisto, statusOttenuto);
        assertFalse(service.deleteFile(nomeFile));
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
        statusPrevisto = false;
        statusOttenuto = service.deleteFile("pippo");
        assertEquals(statusPrevisto, statusOttenuto);

        service.creaFile(PATH_FILE_NEW);
        statusPrevisto = true;
        statusOttenuto = service.deleteFile("pippo");
        assertEquals(statusPrevisto, statusOttenuto);
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
