package it.algos.vaadflow;

import it.algos.vaadflow.service.AFileService;
import it.algos.vaadflow.service.ATextService;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;

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


    private static String PATH = "/Users/gac/Documents/IdeaProjects/it.algos.vaadflow/src/main/java/it/algos/it.algos.vaadflow/";

    private static String SOURCES = PATH + "wizard/sources/";

    private static String MODULES = PATH + "modules/";

    private static String DIR = SOURCES + "Prova";

    private static String SLASH = "/";

    private static String READ = "README.txt";

    private static String PACKAGE = "package it.algos.it.algos.vaadflow;\n";

    private static String TESTO = PACKAGE + "public class Alfa {}";

    private static String NOME_FILE_TEST = "TestNonCancellare";

    private static String PATH_DIRECTORY_TEST = "/Users/gac/Desktop/test/";

    @InjectMocks
    private AFileService service;

    @InjectMocks
    private ATextService textService;

    private boolean statusPrevisto;

    private boolean statusOttenuto;

    private File unFile;

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
        MockitoAnnotations.initMocks(textService);
        service.text = textService;
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
        System.out.println("");
        System.out.println("Controllo l'esistenza del file");
        nomeFile = "nonEsiste";
        statusPrevisto = false;

        statusOttenuto = service.isEsisteFile(nomeFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = READ;
        statusPrevisto = false;
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
        System.out.println("");
        System.out.println("Controllo l'esistenza del file");

        nomeFile = "nonEsiste";
        unFile = new File(nomeFile);
        statusPrevisto = false;

        statusOttenuto = service.isEsisteFile(unFile);
        assertEquals(statusPrevisto, statusOttenuto);
        print("Esiste file", nomeFile, statusOttenuto);

        unFile = null;
        statusOttenuto = service.isEsisteFile(unFile);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeFile = READ;
        unFile = new File(nomeFile);
        statusPrevisto = false;
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
        System.out.println("*isEsisteDirectory - inizio");
        nomeDirectory = "nonEsiste";
        statusPrevisto = false;

        statusOttenuto = service.isEsisteDirectory(nomeDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        statusOttenuto = service.isEsisteDirectory(new File(nomeDirectory));
        assertEquals(statusPrevisto, statusOttenuto);

        nomeDirectory = "src";
        statusPrevisto = false;

        statusOttenuto = service.isEsisteDirectory(nomeDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        nomeDirectory = READ;
        statusPrevisto = false;
        statusOttenuto = service.isEsisteDirectory(nomeDirectory);
        assertEquals(statusPrevisto, statusOttenuto);
        System.out.println("*isEsisteDirectory - fine");


        nomeCompletoDirectory = PATH_DIRECTORY_TEST;
        statusPrevisto = true;
        statusOttenuto = service.isEsisteDirectory(nomeCompletoDirectory);
        assertEquals(statusPrevisto, statusOttenuto);
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
        System.out.println("*deleteFile - inizio");
        statusPrevisto = false;
        statusOttenuto = service.deleteFile("pippo");
        assertEquals(statusPrevisto, statusOttenuto);

        service.creaFile("pippo");
        statusPrevisto = true;
        statusOttenuto = service.deleteFile("pippo");
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = false;
        statusOttenuto = service.deleteFile(DIR);
        assertEquals(statusPrevisto, statusOttenuto);

        System.out.println("*deleteFile - fine");
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
        System.out.println("*deleteDirectory - inizio");
        statusPrevisto = false;
        statusOttenuto = service.deleteDirectory(new File(DIR.toLowerCase()));
        assertEquals(statusPrevisto, statusOttenuto);

        service.creaDirectory(DIR);

        statusPrevisto = true;
        statusOttenuto = service.deleteDirectory(DIR);
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = false;
        statusOttenuto = service.deleteDirectory("pippo");
        assertEquals(statusPrevisto, statusOttenuto);

        System.out.println("*deleteDirectory - fine");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Crea una directory
     *
     * @param nomeCompletoDirectory
     */
//    @Test
    public void creaDirectory() {
        System.out.println("*creaDirectory - inizio");
        statusPrevisto = true;
        String nomeDirectory = DIR;
        statusOttenuto = service.creaDirectory(nomeDirectory);
        assertEquals(statusPrevisto, statusOttenuto);

        service.deleteDirectory(DIR);

        System.out.println("*creaDirectory - fine");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Crea un file
     *
     * @param nameFileWithSuffix nome completo del file, compreso il suffisso
     * @param text     completo del file
     */
//    @Test
    public void creaFile() {
        System.out.println("*creaFile - inizio");
        statusPrevisto = true;
        String nomeFile = "alfa.java";
        statusOttenuto = service.creaFile(nomeFile);
        assertEquals(statusPrevisto, statusOttenuto);

        service.deleteFile(nomeFile);
        System.out.println("*creaFile - fine");
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
        System.out.println("*scriveFile - inizio");
        statusPrevisto = true;
        String nomeFile = NOME_FILE_TEST;
        String testo = TESTO;
        statusOttenuto = service.scriveFile(nomeFile, testo);
        assertEquals(statusPrevisto, statusOttenuto);

        statusPrevisto = true;
        statusOttenuto = service.scriveFile(nomeFile, testo + "pippo", true);
        assertEquals(statusPrevisto, statusOttenuto);

        service.deleteFile(nomeFile);
        System.out.println("*scriveFile - fine");
    }// end of single test


    @SuppressWarnings("javadoc")
    /**
     * Legge un file
     *
     * @param pathFileToBeWritten nome completo del file
     */
//    @Test
    public void leggeFile() {
        System.out.println("*leggeFile - inizio");
        service.scriveFile(nomeCompletoDirectory + NOME_FILE_TEST, TESTO);
        String nomeFile = NOME_FILE_TEST;
        String testo = TESTO;

        testoPrevisto = testo;
        testoOttenuto = service.leggeFile(nomeFile);
        assertEquals(testoPrevisto, testoOttenuto);
        System.out.println(testoOttenuto);

        service.deleteFile(nomeFile);
        System.out.println("*leggeFile - fine");

        nomeFile = "/Users/gac/Documents/IdeaProjects/it.algos.vaadflow/src/main/java/it/algos/it.algos.vaadflow/application/BaseCost.java";
        testoOttenuto = service.leggeFile(nomeFile);
        assertNotNull(testoOttenuto);
        assertTrue(testoOttenuto.length() > 0);
        System.out.println(testoOttenuto);

        nomeFile = "/Users/gac/Documents/IdeaProjects/it.algos.vaadflow/src/main/java/it/algos/it.algos.vaadflow/application/BaseCostDeleted.java";
        testoOttenuto = service.leggeFile(nomeFile);
        assertEquals("", testoOttenuto);
        assertTrue(testoOttenuto.length() == 0);
    }// end of single test


    //    @Test
    public void getSubdiretories() {
        ottenutoList = service.getSubDirectories(MODULES);
        ottenutoList = service.getSubDirectories(MODULES);
    }// end of single test


}// end of class
