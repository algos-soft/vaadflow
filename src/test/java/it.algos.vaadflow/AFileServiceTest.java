package it.algos.vaadflow;

import it.algos.vaadflow.service.AArrayService;
import it.algos.vaadflow.service.AFileService;
import it.algos.vaadflow.service.ATextService;
import it.algos.vaadflow.wiz.scripts.WizCost;
import name.falgout.jeffrey.testing.junit5.MockitoExtension;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.VUOTA;
import static it.algos.vaadflow.service.AFileService.*;
import static it.algos.vaadflow.wiz.scripts.WizCost.*;
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

    static boolean FLAG_CANCELLAZIONE_FINALE = true;

    private static String PATH_DIRECTORY_TEST = "/Users/gac/Desktop/test/";

    private static String PATH_DIRECTORY_UNO = PATH_DIRECTORY_TEST + "Pippo/";

    private static String PATH_DIRECTORY_DUE = PATH_DIRECTORY_TEST + "Possibile/";

    private static String PATH_DIRECTORY_TRE = PATH_DIRECTORY_TEST + "Mantova/";

    private static String PATH_DIRECTORY_NON_ESISTENTE = PATH_DIRECTORY_TEST + "Genova/";

    private static String PATH_DIRECTORY_DA_COPIARE = PATH_DIRECTORY_TEST + "NuovaDirectory/";

    private static String PATH_DIRECTORY_MANCANTE = PATH_DIRECTORY_TEST + "CartellaCopiata/";


    private static String PATH_FILE_UNO = PATH_DIRECTORY_TEST + "Pluto.rtf";

    private static String PATH_FILE_DUE = PATH_DIRECTORY_TEST + "Secondo.rtf";

    private static String PATH_FILE_TRE = PATH_DIRECTORY_TRE + "/Topolino.txt";

    private static String PATH_FILE_QUATTRO = PATH_DIRECTORY_TRE + "/Paperino.txt";

    private static String PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA = "/Users/gac/Desktop/test/pluto.rtf";

    private static String PATH_FILE_NO_SUFFIX = PATH_DIRECTORY_TEST + "Topolino";

    private static String PATH_FILE_NON_ESISTENTE = PATH_DIRECTORY_TEST + "Topolino.txt";

    private static String PATH_FILE_AGGIUNTO = PATH_DIRECTORY_MANCANTE + "TerzaPossibilita.htm";

    private static String PATH_FILE_AGGIUNTO_TRE = PATH_DIRECTORY_TRE + "FileSorgenteAggiunto.ccs";

    private static String PATH_FILE_NO_PATH = "Users/gac/Desktop/test/Pluto.rtf";

    private static String PATH_DIRECTORY_NO_PATH = "Users/gac/Desktop/test/Mantova/";

    private static String PATH_FILE_NO_GOOD = "/Users/gac/Desktop/test/Pa perino/Topolino.abc";

    private static String PATH_FILE_ANOMALO = PATH_DIRECTORY_TEST + "Pluto.properties";

    private static String PATH_DIRECTORY_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA = "/Users/gac/desktop/test/Pippo/";

    private static String PATH_FILE_DELETE = "/Users/gac/Desktop/test/NonEsiste/Minni.txt";

    private static String PATH_MODULO_PROVA = PATH_VAADFLOW_DIR_STANDARD + DIR_ALGOS + "vaadtest/";


    private static String VALIDO = "TROVATO";

    private static String ESISTE_FILE = " isEsisteFile() ";

    private static String ESISTE_DIRECTORY = " isEsisteDirectory() ";

    private static String CREA_FILE = " creaFile() ";

    private static String CREA_DIRECTORY = " creaDirectory() ";

    private static String DELETE_FILE = " deleteFile() ";

    private static String DELETE_DIRECTORY = " deleteDirectory() ";

    @InjectMocks
    private AFileService service;

    @InjectMocks
    private ATextService text;

    @InjectMocks
    private AArrayService array;

    private File unFile;

    private File unaDirectory;

    private String ottenutoDaNome;

    private String ottenutoDaFile;

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

        listaFile = new ArrayList<>();
        listaFile.add(new File(PATH_FILE_UNO));
        listaFile.add(new File(PATH_FILE_DUE));
        listaFile.add(new File(PATH_FILE_TRE));
        listaFile.add(new File(PATH_FILE_QUATTRO));
        listaFile.add(new File(PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA));
    }// end of method


    /**
     * Creazioni di servizio per essere sicuri che ci siano tutti i files/directories richiesti <br>
     * Alla fine verranno cancellati tutti <br>
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
     */
    private void creazioneFiles() {
        if (array.isValid(listaFile)) {
            for (File unFile : listaFile) {
                try { // prova ad eseguire il codice
                    unFile.createNewFile();
                } catch (Exception unErrore) { // intercetta l'errore
                    if (service.creaDirectoryParentAndFile(unFile).equals(VUOTA)) {
                        listaDirectory.add(new File(unFile.getParent()));
                    }// end of if cycle
                }// fine del blocco try-catch
            }// end of for cycle
        }// end of if cycle
    }// end of method


    /**
     * Cancellazione finale di tutti i files/directories creati in questo test <br>
     * I files li cancella subito 'al volo', per essere sicuri di svuotare le directory <br>
     * Le directory, che sono vuote, le segnala al System che le chiude alla fine del test <br>
     */
    @Deprecated
    private void fineOld() {
        if (array.isValid(listaFile)) {
            for (File file : listaFile) {
                file.delete(); //--cancellazionew immediata
            }// end of for cycle
        }// end of if cycle

        if (array.isValid(listaDirectory)) {
            for (File directory : listaDirectory) {
                directory.deleteOnExit(); //--cancellazione alla fine del programma (in questo caso il test)
            }// end of for cycle
        }// end of if cycle
    }// end of method


    /**
     * Cancellazione finale di tutti i files/directories creati in questo test <br>
     */
    private void fine() {
        try { // prova ad eseguire il codice
            FileUtils.forceDelete(new File(PATH_DIRECTORY_TEST));
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch
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
    public void isEsisteFile() {
        nomeCompletoFile = "null";
        ottenutoDaNome = service.isEsisteFileStr((String) null);
        assertFalse(service.isEsisteFile((String) null));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        ottenutoDaFile = service.isEsisteFileStr((File) null);
        assertFalse(service.isEsisteFile((File) null));
        assertEquals(PARAMETRO_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = VUOTA;
        ottenutoDaNome = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = "nonEsiste";
        ottenutoDaNome = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = PATH_FILE_NO_SUFFIX;
        ottenutoDaNome = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = PATH_FILE_NON_ESISTENTE;
        ottenutoDaNome = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(NON_ESISTE_FILE, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(NON_ESISTE_FILE, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = PATH_FILE_NO_PATH;
        ottenutoDaNome = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = PATH_DIRECTORY_UNO;
        ottenutoDaNome = service.isEsisteFileStr(nomeCompletoFile);
        assertFalse(service.isEsisteFile(nomeCompletoFile));
        assertEquals(NON_E_FILE, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.isEsisteFileStr(unFile);
        assertFalse(service.isEsisteFile(unFile));
        assertEquals(NON_E_FILE, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = PATH_FILE_UNO;
        ottenutoDaNome = service.isEsisteFileStr(nomeCompletoFile);
        assertTrue(service.isEsisteFile(nomeCompletoFile));
        assertEquals(VUOTA, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.isEsisteFileStr(unFile);
        assertTrue(service.isEsisteFile(unFile));
        assertEquals(VUOTA, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = PATH_FILE_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA;
        ottenutoDaNome = service.isEsisteFileStr(nomeCompletoFile);
        assertTrue(service.isEsisteFile(nomeCompletoFile));
        assertEquals(VUOTA, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.isEsisteFileStr(unFile);
        assertTrue(service.isEsisteFile(unFile));
        assertEquals(VUOTA, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));
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
    public void isEsisteDirectory() {
        nomeCompletoDirectory = "null";
        ottenutoDaNome = service.isEsisteDirectoryStr((String) null);
        assertFalse(service.isEsisteDirectory((String) null));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        ottenutoDaFile = service.isEsisteDirectoryStr((File) null);
        assertFalse(service.isEsisteDirectory((File) null));
        assertEquals(PARAMETRO_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = VUOTA;
        ottenutoDaNome = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.isEsisteDirectoryStr((File) null);
        assertFalse(service.isEsisteDirectory((File) null));
        assertEquals(PARAMETRO_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = "nonEsiste";
        ottenutoDaNome = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = PATH_DIRECTORY_NON_ESISTENTE;
        ottenutoDaNome = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = PATH_FILE_UNO;
        ottenutoDaNome = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(NON_E_DIRECTORY, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(NON_E_DIRECTORY, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = PATH_DIRECTORY_NO_PATH;
        ottenutoDaNome = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.isEsisteDirectoryStr(unaDirectory);
        assertFalse(service.isEsisteDirectory(unaDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = PATH_DIRECTORY_UNO;
        ottenutoDaNome = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertTrue(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(VUOTA, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.isEsisteDirectoryStr(unaDirectory);
        assertTrue(service.isEsisteDirectory(unaDirectory));
        assertEquals(VUOTA, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = PATH_DIRECTORY_ESISTENTE_CON_MAIUSCOLA_SBAGLIATA;
        ottenutoDaNome = service.isEsisteDirectoryStr(nomeCompletoDirectory);
        assertTrue(service.isEsisteDirectory(nomeCompletoDirectory));
        assertEquals(VUOTA, ottenutoDaNome);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.isEsisteDirectoryStr(unaDirectory);
        assertTrue(service.isEsisteDirectory(unaDirectory));
        assertEquals(VUOTA, ottenutoDaFile);
        System.out.println("Risposta" + ESISTE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));
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
    public void creaFile() {
        nomeCompletoFile = "null";
        ottenutoDaNome = service.creaFileStr((String) null);
        assertFalse(service.creaFile((String) null));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + CREA_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        ottenutoDaFile = service.creaFileStr((File) null);
        assertFalse(service.creaFile((File) null));
        assertEquals(PARAMETRO_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + CREA_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = VUOTA;
        ottenutoDaNome = service.creaFileStr(nomeCompletoFile);
        assertFalse(service.creaFile(nomeCompletoFile));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + CREA_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.creaFileStr(unFile);
        assertFalse(service.creaFile(unFile));
        assertEquals(PATH_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + CREA_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = PATH_FILE_NO_SUFFIX;
        ottenutoDaNome = service.creaFileStr(nomeCompletoFile);
        assertFalse(service.creaFile(nomeCompletoFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenutoDaNome);
        System.out.println("Risposta" + CREA_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.creaFileStr(unFile);
        assertFalse(service.creaFile(unFile));
        assertEquals(PATH_SENZA_SUFFIX, ottenutoDaFile);
        System.out.println("Risposta" + CREA_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        creaFileValido(PATH_FILE_DELETE);
        creaFileValido(PATH_FILE_ANOMALO);
    }// end of single test


    /**
     * Creo un file e lo cancello subito dopo
     * Se esiste già, lo cancello al volo PRIMA di crearlo
     */
    private void creaFileValido(String nomeCompletoFile) {
        System.out.println("");

        //--controlla che non esista prima di crearlo
        service.deleteFile(nomeCompletoFile);

        //--creazione da testare
        ottenutoDaNome = service.creaFileStr(nomeCompletoFile);
        assertTrue(service.creaFile(nomeCompletoFile));
        assertEquals(VUOTA, ottenutoDaNome);
        System.out.println("Risposta" + CREA_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.creaFileStr(unFile);
        assertTrue(service.creaFile(unFile));
        assertEquals(VUOTA, ottenutoDaFile);
        System.out.println("Risposta" + CREA_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteFile(nomeCompletoFile));

        //--se è stato creato il file che qui viene cancellato,
        //--devo aggiungere allla lista la directory parent per poterla cancellare alla fine del test
        if (service.isEsisteFile(nomeCompletoFile)) {
            listaDirectory.add(new File(unFile.getParent()));
        }// end of if cycle

        //--cancellazione
        service.deleteFile(nomeCompletoFile);

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteFile(nomeCompletoFile));
    }// end of method


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
    public void creaDirectory() {
        nomeCompletoDirectory = "null";
        ottenutoDaNome = service.creaDirectoryStr((String) null);
        assertFalse(service.creaDirectory((String) null));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        ottenutoDaFile = service.creaDirectoryStr((File) null);
        assertFalse(service.creaDirectory((File) null));
        assertEquals(PARAMETRO_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = VUOTA;
        ottenutoDaNome = service.creaDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.creaDirectory(nomeCompletoDirectory));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.creaDirectoryStr(unaDirectory);
        assertFalse(service.creaDirectory(unaDirectory));
        assertEquals(PATH_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = "nonEsiste";
        ottenutoDaNome = service.creaDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.creaDirectory(nomeCompletoDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaNome);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.creaDirectoryStr(unaDirectory);
        assertFalse(service.creaDirectory(unaDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaFile);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = "/Users/gac/Desktop/test/Paperino/Topolino.abc";
        ottenutoDaNome = service.creaDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.creaDirectory(nomeCompletoDirectory));
        assertEquals(NON_E_DIRECTORY, ottenutoDaNome);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.creaDirectoryStr(unaDirectory);
        assertFalse(service.creaDirectory(unaDirectory));
        assertEquals(NON_E_DIRECTORY, ottenutoDaFile);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        creaDirectoryValida(PATH_DIRECTORY_TRE);
    }// end of single test


    /**
     * Crea al volo una directory (probabilmente) valida e la cancella subito dopo
     */
    private void creaDirectoryValida(String nomeCompletoDirectory) {
        System.out.println("");

        //--creazione da testare
        ottenutoDaNome = service.creaDirectoryStr(nomeCompletoDirectory);
        assertTrue(service.creaDirectory(nomeCompletoDirectory));
        assertEquals(VUOTA, ottenutoDaNome);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.creaDirectoryStr(unaDirectory);
        assertTrue(service.creaDirectory(unaDirectory));
        assertEquals(VUOTA, ottenutoDaFile);
        System.out.println("Risposta" + CREA_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        //--controlla dopo averlo creato - Esiste
        assertTrue(service.isEsisteDirectory(nomeCompletoDirectory));

        //--se è stato creato la directory, la aggiungo alla lista in modo da poterla camncellare alla fine del test
        if (!listaDirectory.contains(unaDirectory)) {
            listaDirectory.add(unaDirectory);
        }// end of if cycle
    }// end of method


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
    @Test
    public void deleteFile() {
        nomeCompletoFile = "null";
        ottenutoDaNome = service.deleteFileStr((String) null);
        assertFalse(service.deleteFile((String) null));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        ottenutoDaFile = service.deleteFileStr((File) null);
        assertFalse(service.deleteFile((File) null));
        assertEquals(PARAMETRO_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = VUOTA;
        ottenutoDaNome = service.deleteFileStr(nomeCompletoFile);
        assertFalse(service.deleteFile(nomeCompletoFile));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.deleteFileStr(unFile);
        assertFalse(service.deleteFile(unFile));
        assertEquals(PATH_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = "Pippo";
        ottenutoDaNome = service.deleteFileStr(nomeCompletoFile);
        assertFalse(service.deleteFile(nomeCompletoFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.deleteFileStr(unFile);
        assertFalse(service.deleteFile(unFile));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoFile = PATH_FILE_NON_ESISTENTE;
        ottenutoDaNome = service.deleteFileStr(nomeCompletoFile);
        assertFalse(service.deleteFile(nomeCompletoFile));
        assertEquals(NON_ESISTE_FILE, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.deleteFileStr(unFile);
        assertFalse(service.deleteFile(unFile));
        assertEquals(NON_ESISTE_FILE, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        deleFileValido(PATH_FILE_DELETE);
    }// end of single test


    /**
     * Cancello un file e lo ricreo subito dopo
     * Se non esiste, lo creo al volo PRIMA di cancellarlo
     */
    private void deleFileValido(String nomeCompletoFile) {
        System.out.println("");

        //--controlla che esista prima di cancellarlo
        service.creaFile(nomeCompletoFile);

        //--cancellazione da testare
        ottenutoDaNome = service.deleteFileStr(nomeCompletoFile);
        service.creaFile(nomeCompletoFile);
        assertTrue(service.deleteFile(nomeCompletoFile));
        assertEquals(VUOTA, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaNome: " + nomeCompletoFile + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        service.creaFile(nomeCompletoFile);
        unFile = new File(nomeCompletoFile);
        ottenutoDaFile = service.deleteFileStr(unFile);
        service.creaFile(nomeCompletoFile);
        assertTrue(service.deleteFile(unFile));
        assertEquals(VUOTA, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_FILE + "ottenutoDaFile: " + nomeCompletoFile + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteFile(nomeCompletoFile));
    }// end of method


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
    @Test
    public void deleteDirectory() {
        nomeCompletoDirectory = "null";
        ottenutoDaNome = service.deleteDirectoryStr((String) null);
        assertFalse(service.deleteDirectory((String) null));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        ottenutoDaFile = service.deleteDirectoryStr((File) null);
        assertFalse(service.deleteDirectory((File) null));
        assertEquals(PARAMETRO_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = VUOTA;
        ottenutoDaNome = service.deleteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.deleteDirectory(nomeCompletoDirectory));
        assertEquals(PATH_NULLO, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.deleteDirectoryStr(unaDirectory);
        assertFalse(service.deleteDirectory(unaDirectory));
        assertEquals(PATH_NULLO, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = "Pippo";
        ottenutoDaNome = service.deleteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.deleteDirectory(nomeCompletoDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.deleteDirectoryStr(unaDirectory);
        assertFalse(service.deleteDirectory(unaDirectory));
        assertEquals(PATH_NOT_ABSOLUTE, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        nomeCompletoDirectory = PATH_DIRECTORY_NON_ESISTENTE;
        ottenutoDaNome = service.deleteDirectoryStr(nomeCompletoDirectory);
        assertFalse(service.deleteDirectory(nomeCompletoDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        unaDirectory = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.deleteDirectoryStr(unaDirectory);
        assertFalse(service.deleteDirectory(unaDirectory));
        assertEquals(NON_ESISTE_DIRECTORY, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        deleteDirectoryValida(PATH_DIRECTORY_NON_ESISTENTE);
    }// end of single test


    /**
     * Cancello una directory e la ricreo subito dopo
     * Se non esiste, la creo al volo PRIMA di cancellarla
     */
    private void deleteDirectoryValida(String nomeCompletoDirectory) {
        System.out.println("");

        //--controlla che esista prima di cancellarla
        service.creaDirectory(nomeCompletoDirectory);

        //--cancellazione da testare
        ottenutoDaNome = service.deleteDirectoryStr(nomeCompletoDirectory);
        service.creaDirectory(nomeCompletoDirectory);
        assertTrue(service.deleteDirectory(nomeCompletoDirectory));
        assertEquals(VUOTA, ottenutoDaNome);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaNome: " + nomeCompletoDirectory + " = " + (ottenutoDaNome.equals(VUOTA) ? VALIDO : ottenutoDaNome));
        service.creaDirectory(nomeCompletoDirectory);
        unFile = new File(nomeCompletoDirectory);
        ottenutoDaFile = service.deleteDirectoryStr(unFile);
        service.creaDirectory(nomeCompletoDirectory);
        assertTrue(service.deleteDirectory(unFile));
        assertEquals(VUOTA, ottenutoDaFile);
        System.out.println("Risposta" + DELETE_DIRECTORY + "ottenutoDaFile: " + nomeCompletoDirectory + " = " + (ottenutoDaFile.equals(VUOTA) ? VALIDO : ottenutoDaFile));

        //--controlla dopo averlo cancellato - Non esiste
        assertFalse(service.isEsisteDirectory(nomeCompletoDirectory));
    }// end of method


    /**
     * Copia un file
     * <p>
     * Se manca il file sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste il file destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo del file sorgente
     * @param destPath nome completo del file destinazione
     *
     * @return true se è stato copiato
     */
    @Test
    public void copyFile() {
        String srcPathNonEsistente = PATH_FILE_NON_ESISTENTE;
        String srcPath = PATH_FILE_UNO;
        String destPath = PATH_DIRECTORY_TEST + "NuovoFile.rtf";
        String destPathEsistente = PATH_FILE_DUE;

        //--esegue con sorgente NON esistente
        ottenutoBooleano = service.copyFile(srcPathNonEsistente, destPath);
        assertFalse(ottenutoBooleano);
        ottenuto = service.copyFileStr(srcPathNonEsistente, destPath);
        assertEquals(NON_ESISTE_FILE, ottenuto);

        //--esegue con destinazione GIA esistente
        ottenutoBooleano = service.copyFile(srcPath, destPathEsistente);
        assertFalse(ottenutoBooleano);
        ottenuto = service.copyFileStr(srcPathNonEsistente, destPath);
        assertEquals(NON_ESISTE_FILE, ottenuto);

        //--controllo condizioni iniziali
        assertTrue(service.isEsisteFile(srcPath));
        assertFalse(service.isEsisteFile(destPath));

        //--esegue
        ottenutoBooleano = service.copyFile(srcPath, destPath);
        assertTrue(ottenutoBooleano);

        //--controllo condizioni finali
        assertTrue(service.isEsisteFile(srcPath));
        assertTrue(service.isEsisteFile(destPath));

        //--ripristina condizioni iniziali
        assertTrue(service.deleteFile(destPath));
    }// end of method


    /**
     * Copia una directory sostituendo integralmente quella eventualmente esistente <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, la cancella prima di ricopiarla <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    @Test
    public void copyDirectoryDeletingAll() {
        String srcPathValida = PATH_DIRECTORY_TRE;
        String srcPathNonEsistente = PATH_DIRECTORY_NON_ESISTENTE;
        String destPathDaSovrascrivere = PATH_DIRECTORY_MANCANTE;
        String destFileAggiunto = PATH_FILE_AGGIUNTO;

        //--esegue con sorgente NON esistente
        assertFalse(service.isEsisteDirectory(srcPathNonEsistente));
        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));
        ottenutoBooleano = service.copyDirectoryDeletingAll(srcPathNonEsistente, destPathDaSovrascrivere);
        assertFalse(ottenutoBooleano);
        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));

        //--esegue con destinazione NON esistente
        assertTrue(service.isEsisteDirectory(srcPathValida));
        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));
        ottenutoBooleano = service.copyDirectoryDeletingAll(srcPathValida, destPathDaSovrascrivere);
        assertTrue(ottenutoBooleano);
        assertTrue(service.isEsisteDirectory(destPathDaSovrascrivere));

        //--aggiunge nella cartella di destinazione un file per controllare che venga (come previsto) cancellato
        assertTrue(service.creaFile(destFileAggiunto));

        //--esegue con destinazione esistente
        assertTrue(service.isEsisteDirectory(destPathDaSovrascrivere));
        ottenutoBooleano = service.copyDirectoryDeletingAll(srcPathValida, destPathDaSovrascrivere);
        assertTrue(ottenutoBooleano);

        try { // prova ad eseguire il codice
            FileUtils.forceDelete(new File(destPathDaSovrascrivere));
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));
    }// end of single test


    /**
     * Copia una directory solo se non esisteva <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory di destinazione, non fa nulla <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    @Test
    public void copyDirectoryOnlyNotExisting() {
        String srcPathValida = PATH_DIRECTORY_TRE;
        String srcPathNonEsistente = PATH_DIRECTORY_NON_ESISTENTE;
        String destPathDaSovrascrivere = PATH_DIRECTORY_MANCANTE;
        String srcFileAggiunto = PATH_FILE_AGGIUNTO_TRE;

        //--esegue con sorgente NON esistente
        assertFalse(service.isEsisteDirectory(srcPathNonEsistente));
        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));
        ottenutoBooleano = service.copyDirectoryOnlyNotExisting(srcPathNonEsistente, destPathDaSovrascrivere);
        assertFalse(ottenutoBooleano);
        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));

        //--esegue con destinazione NON esistente
        assertTrue(service.isEsisteDirectory(srcPathValida));
        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));
        ottenutoBooleano = service.copyDirectoryOnlyNotExisting(srcPathValida, destPathDaSovrascrivere);
        assertTrue(ottenutoBooleano);
        assertTrue(service.isEsisteDirectory(destPathDaSovrascrivere));

        //--aggiunge nella cartella sorgente un file per controllare che NON venga aggiunto (come previsto)
        assertTrue(service.creaFile(srcFileAggiunto));

        //--esegue con destinazione esistente
        assertTrue(service.isEsisteDirectory(destPathDaSovrascrivere));
        assertTrue(service.isEsisteDirectory(destPathDaSovrascrivere));
        ottenutoBooleano = service.copyDirectoryOnlyNotExisting(srcPathValida, destPathDaSovrascrivere);
        assertFalse(ottenutoBooleano);

        try { // prova ad eseguire il codice
            FileUtils.forceDelete(new File(destPathDaSovrascrivere));
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));
    }// end of single test


    /**
     * Copia una directory aggiungendo files e subdirectories a quelli eventualmente esistenti <br>
     * Lascia inalterate subdirectories e files già esistenti <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory destinazione, aggiunge files e subdirectories <br>
     * Tutti i files e le subdirectories esistenti vengono mantenuti <br>
     * Tutte le aggiunte sono ricorsive nelle subdirectories <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    @Test
    public void copyDirectoryOnlyNotExisting2() {
//        String primo = "PrimoFileCheResta.txx";
//        String secondo = "SecondoFileCheResta.txx";
//        String terzo = "TerzoFileVariabile.txx";
//        String quarto = "QuartoIncerto.txx";
//        String dirCopiata = "VieneCopiata";
//        String dirRimane = "Rimane";
//        String sorgente = PATH_DIRECTORY_TEST + "Sorgente/";
//        String emptyDirCopiata = sorgente + dirCopiata;
//        String srcDirectoryUno = sorgente + "PrimaDirectory/";
//        String srcDirUnoFileUno = srcDirectoryUno + primo;
//        String srcDirUnoFileDue = srcDirectoryUno + secondo;
//        String srcDirUnoFileTre = srcDirectoryUno + terzo;
//        String srcDirectoryDue = sorgente + "SecondaDirectory/";
//        String srcDirDueFileUno = srcDirectoryDue + primo;
//        String srcDirDueFileDue = srcDirectoryDue + secondo;
//        String srcDirDueFileQuattro = srcDirectoryDue + quarto;
//
//        String destinazione = PATH_DIRECTORY_TEST + "Destinazione/";
//        String emptyDirRimane = destinazione + dirRimane;
//        String destDirectoryUno = destinazione + "PrimaDirectory/";
//        String destDirUnoFileUno = destDirectoryUno + primo;
//        String destDirUnoFileDue = destDirectoryUno + secondo;
//        String destDirUnoFileQuattro = destDirectoryUno + quarto;
//        String destDirectoryDue = destinazione + "SecondaDirectory/";
//        String destDirDueFileUno = destDirectoryDue + primo;
//        String destDirDueFileDue = destDirectoryDue + secondo;
//        String destDirDueFileTre = destDirectoryDue + terzo;
//
//        assertTrue(service.creaDirectory(emptyDirCopiata));
//        assertTrue(service.creaFile(srcDirUnoFileUno));
//        assertTrue(service.creaFile(srcDirUnoFileDue));
//        assertTrue(service.creaFile(srcDirUnoFileTre));
//        assertTrue(service.creaFile(srcDirDueFileUno));
//        assertTrue(service.creaFile(srcDirDueFileDue));
//        assertTrue(service.creaFile(srcDirDueFileQuattro));
//
//        assertTrue(service.creaDirectory(emptyDirRimane));
//        assertTrue(service.creaFile(destDirUnoFileUno));
//        assertTrue(service.creaFile(destDirUnoFileDue));
//        assertTrue(service.creaFile(destDirUnoFileQuattro));
//        assertTrue(service.creaFile(destDirDueFileUno));
//        assertTrue(service.creaFile(destDirDueFileDue));
//        assertTrue(service.creaFile(destDirDueFileTre));

        String sorgente = PATH_DIRECTORY_TEST + "Sorgente/";
        String destinazione = PATH_DIRECTORY_TEST + "Destinazione/";
        String dirUno = "PrimaDirectory/";
        String dirDue = "SecondaDirectory/";
        creaCartelleTemporanee(sorgente, destinazione, dirUno, dirDue);

        ottenutoBooleano = service.copyDirectoryAddingOnly(sorgente, destinazione);
        assertTrue(ottenutoBooleano);

        assertTrue(service.isEsisteDirectory(destinazione + "Rimane"));
        assertTrue(service.isEsisteDirectory(destinazione + "VieneCopiata"));

        assertTrue(service.isEsisteFile(destinazione + dirUno + "TerzoFileVariabile.txx"));
        assertTrue(service.isEsisteFile(destinazione + dirUno + "QuartoIncerto.txx"));

        assertTrue(service.isEsisteFile(destinazione + dirDue + "TerzoFileVariabile.txx"));
        assertTrue(service.isEsisteFile(destinazione + dirDue + "QuartoIncerto.txx"));
    }// end of single test


    private void creaCartelleTemporanee(String srcPath, String destPath, String dirUno, String dirDue) {
        String primo = "PrimoFileCheResta.txx";
        String secondo = "SecondoFileCheResta.txx";
        String terzo = "TerzoFileVariabile.txx";
        String quarto = "QuartoIncerto.txx";
        String fileConTesto = "FileConTesto.txt";
        String dirCopiata = "VieneCopiata";
        String dirRimane = "Rimane";

        String emptyDirCopiata = srcPath + dirCopiata;
        String srcDirectoryUno = srcPath + dirUno;
        String srcDirUnoFileUno = srcDirectoryUno + primo;
        String srcDirUnoFileDue = srcDirectoryUno + secondo;
        String srcDirUnoFileTre = srcDirectoryUno + terzo;
        String srcDirectoryDue = srcPath + dirDue;
        String srcDirDueFileUno = srcDirectoryDue + primo;
        String srcDirDueFileDue = srcDirectoryDue + secondo;
        String srcDirDueFileQuattro = srcDirectoryDue + quarto;

        String emptyDirRimane = destPath + dirRimane;
        String destDirectoryUno = destPath + dirUno;
        String destDirUnoFileUno = destDirectoryUno + primo;
        String destDirUnoFileDue = destDirectoryUno + secondo;
        String destDirUnoFileQuattro = destDirectoryUno + quarto;
        String destDirectoryDue = destPath + dirDue;
        String destDirDueFileUno = destDirectoryDue + primo;
        String destDirDueFileDue = destDirectoryDue + secondo;
        String destDirDueFileTre = destDirectoryDue + terzo;

        assertTrue(service.creaDirectory(emptyDirCopiata));
        assertTrue(service.creaFile(srcDirUnoFileUno));
        assertTrue(service.creaFile(srcDirUnoFileDue));
        assertTrue(service.creaFile(srcDirUnoFileTre));
        assertTrue(service.creaFile(srcDirDueFileUno));
        assertTrue(service.creaFile(srcDirDueFileDue));
        assertTrue(service.creaFile(srcDirDueFileQuattro));

        assertTrue(service.creaDirectory(emptyDirRimane));
        assertTrue(service.creaFile(destDirUnoFileUno));
        assertTrue(service.creaFile(destDirUnoFileDue));
        assertTrue(service.creaFile(destDirUnoFileQuattro));
        assertTrue(service.creaFile(destDirDueFileUno));
        assertTrue(service.creaFile(destDirDueFileDue));
        assertTrue(service.creaFile(destDirDueFileTre));

        //--aggiunge nella directory sorgente un file di testo contenete effettivamente del testo
        assertTrue(service.creaFile(srcPath + dirUno + fileConTesto));
        service.sovraScriveFile(srcPath + dirUno + fileConTesto, "Questo testo verrà copiato");
        assertTrue(service.creaFile(destPath + dirUno + fileConTesto));
        service.sovraScriveFile(destPath + dirUno + fileConTesto, "Questo testo verrà cancellato");
    }// end of method


    /**
     * Copia una directory aggiungendo files e subdirectories a quelli eventualmente esistenti <br>
     * Lascia inalterate subdirectories e files già esistenti <br>
     * <p>
     * Se manca la directory sorgente, non fa nulla <br>
     * Se manca la directory di destinazione, la crea <br>
     * Se esiste la directory destinazione, aggiunge files e subdirectories <br>
     * Tutti i files e le subdirectories esistenti vengono mantenuti <br>
     * Tutte le aggiunte sono ricorsive nelle subdirectories <br>
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     *
     * @return true se la directory  è stata copiata
     */
    @Test
    public void copyDirectoryAddingOnly() {
        String srcPathValida = PATH_DIRECTORY_TRE;
        String srcPathNonEsistente = PATH_DIRECTORY_NON_ESISTENTE;
        String destPathDaSovrascrivere = PATH_DIRECTORY_MANCANTE;
        String destFileAggiunto = PATH_FILE_AGGIUNTO;
        String srcFileAggiunto = PATH_FILE_AGGIUNTO_TRE;

        //--esegue con sorgente NON esistente
        assertFalse(service.isEsisteDirectory(srcPathNonEsistente));
        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));
        ottenutoBooleano = service.copyDirectoryAddingOnly(srcPathNonEsistente, destPathDaSovrascrivere);
        assertFalse(ottenutoBooleano);
        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));

        //--esegue con destinazione NON esistente
        assertTrue(service.isEsisteDirectory(srcPathValida));
        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));
        ottenutoBooleano = service.copyDirectoryAddingOnly(srcPathValida, destPathDaSovrascrivere);
        assertTrue(ottenutoBooleano);
        assertTrue(service.isEsisteDirectory(destPathDaSovrascrivere));

        //--aggiunge nella cartella sorgente un file per controllare che venga (come previsto) copiato
        assertTrue(service.creaFile(srcFileAggiunto));

        //--aggiunge nella cartella di destinazione un file per controllare che venga (come previsto) mantenuto
        assertTrue(service.creaFile(destFileAggiunto));

        //--esegue con destinazione esistente
        assertTrue(service.isEsisteDirectory(destPathDaSovrascrivere));
        assertTrue(service.isEsisteDirectory(destPathDaSovrascrivere));
        ottenutoBooleano = service.copyDirectoryAddingOnly(srcPathValida, destPathDaSovrascrivere);
        assertTrue(ottenutoBooleano);

        try { // prova ad eseguire il codice
            FileUtils.forceDelete(new File(destPathDaSovrascrivere));
        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        assertFalse(service.isEsisteDirectory(destPathDaSovrascrivere));
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
//        statusOttenuto = service.scriveNewFile(nomeFile, testo + "pippo", true);
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


    /**
     * Elimina l'ultima directory da un path <br>
     * <p>
     * Esegue solo se il path è valido
     * Elimina spazi vuoti iniziali e finali
     *
     * @param path in ingresso
     *
     * @return path ridotto in uscita
     */
    @Test
    public void levaDirectoryFinale() {
        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow/";
        previsto = "/Users/gac/Documents/IdeaProjects/operativi/";
        ottenuto = service.levaDirectoryFinale(sorgente);
        assertEquals(previsto, ottenuto);
    }// end of single test


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    @Test
    public void getSubDirectories() {
        sorgente = "/Users/gac/Documents/IdeaProjects/";
        listaDirectory = service.getSubDirectories(sorgente);
        if (listaDirectory != null) {
            for (File file : listaDirectory) {
                System.out.println(file.getName());
            }// end of for cycle
        }// end of if cycle
    }// end of single test


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param directoryToBeScanned della directory
     */
    @Test
    public void getSubDirectories2() {
        File fileSorgente = new File("/Users/gac/Documents/IdeaProjects/");
        listaDirectory = service.getSubDirectories(fileSorgente);
        if (listaDirectory != null) {
            for (File file : listaDirectory) {
                System.out.println(file.getName());
            }// end of for cycle
        }// end of if cycle
    }// end of single test


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    @Test
    public void getSubDirectoriesName() {
        sorgente = "/Users/gac/Documents/IdeaProjects/";
        List<String> ottenuto = service.getSubDirectoriesName(sorgente);
        if (ottenuto != null) {
            for (String directory : ottenuto) {
                System.out.println(directory);
            }// end of for cycle
        }// end of if cycle
    }// end of single test


    /**
     * Estrae le sub-directories da una directory <br>
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    @Test
    public void getSubDirectoriesAbsolutePathName() {
        sorgente = "/Users/gac/Documents/IdeaProjects/";
        List<String> ottenuto = service.getSubDirectoriesAbsolutePathName(sorgente);
        if (ottenuto != null) {
            for (String directory : ottenuto) {
                System.out.println(directory);
            }// end of for cycle
        }// end of if cycle
    }// end of single test


    /**
     * Estrae le sub-directories da un sotto-livello di una directory <br>
     * La dirInterna non è, ovviamente, al primo livello della directory altrimenti chiamerei getSubDirectories <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return lista di sub-directory SENZA files
     */
    @Test
    public void getSubSubDirectories() {
        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow";
        String dirInterna = "src/main";
        listaDirectory = service.getSubSubDirectories(sorgente, dirInterna);
        assertEquals(listaDirectory.size(), 3);
        if (listaDirectory != null) {
            for (File file : listaDirectory) {
                System.out.println(file.getName());
            }// end of for cycle
        }// end of if cycle

        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow/";
        dirInterna = "/src/main";
        listaDirectory = service.getSubSubDirectories(sorgente, dirInterna);
        assertEquals(listaDirectory.size(), 3);

        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow";
        dirInterna = "/src/main";
        listaDirectory = service.getSubSubDirectories(sorgente, dirInterna);
        assertEquals(listaDirectory.size(), 3);

        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow/";
        dirInterna = "src/main";
        listaDirectory = service.getSubSubDirectories(sorgente, dirInterna);
        assertEquals(listaDirectory.size(), 3);

    }// end of single test


    /**
     * Controlla se una sotto-directory è piena <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return true se è piena
     */
    @Test
    public void isEsisteSubDirectory() {
        sorgente = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow";
        String dirInterna = "src/main";
        ottenutoBooleano = service.isEsisteSubDirectory(new File(sorgente), dirInterna);
        assertTrue(ottenutoBooleano);
    }// end of single test


    /**
     * Controlla se una sotto-directory è vuota <br>
     *
     * @param directoryToBeScanned della directory
     * @param dirInterna           da scandagliare
     *
     * @return true se è vuota
     */
    @Test
    public void isVuotaSubDirectory() {
        sorgente = "/Users/gac/Documents/IdeaProjects/tutorial";
        String dirInterna = "src/main";
        ottenutoBooleano = service.isVuotaSubDirectory(new File(sorgente), dirInterna);
        assertTrue(ottenutoBooleano);
    }// end of single test


    /**
     * Sposta un file da una directoy ad un'altra <br>
     * Esegue solo se il path sorgente esiste <br>
     * Esegue solo se il path destinazione NON esiste <br>
     * Viene cancellato il file sorgente <br>
     *
     * @param pathFileToBeRead  posizione iniziale del file da spostare
     * @param pathFileToBeWrite posizione iniziale del file da spostare
     *
     * @return testo di errore, vuoto se il file è stato spostato
     */
    @Test
    public void spostaFile() {
        nomeFile = "PerAdessoNonEsisto" + WizCost.TXT_SUFFIX;
        String pathDirectoryUno = PATH_MODULO_PROVA + DIR_APPLICATION;
        String pathDirectoryDue = PATH_MODULO_PROVA + DIR_MODULES;
        String pathFileUno = pathDirectoryUno + nomeFile;
        String pathFileDue = pathDirectoryDue + nomeFile;

        //--situazione iniziale vuota
        assertFalse(service.isEsisteFile(pathFileUno));
        assertFalse(service.isEsisteFile(pathFileDue));

        //--provo con condizioni NON valide
        ottenuto = service.spostaFileStr(VUOTA, pathFileDue);
        assertEquals(PATH_NULLO, ottenuto);

        //--provo con condizioni NON valide
        ottenuto = service.spostaFileStr(pathFileUno, pathFileDue);
        assertEquals(NON_ESISTE_FILE, ottenuto);

        //--creo un file e controllo la situazione al momento
        service.creaFile(pathFileUno);
        assertTrue(service.isEsisteFile(pathFileUno));
        assertFalse(service.isEsisteFile(pathFileDue));

        //--eseguo lo spostamento
        ottenuto = service.spostaFileStr(pathFileUno, pathFileDue);
        assertEquals(VUOTA, ottenuto);

        //--ccontrollo la situazione dopo lo spostamento
        assertFalse(service.isEsisteFile(pathFileUno));
        assertTrue(service.isEsisteFile(pathFileDue));

        //--cancello il file temporaneo
        service.deleteFile(pathFileDue);

        //--situazione finale vuota
        assertFalse(service.isEsisteFile(pathFileUno));
        assertFalse(service.isEsisteFile(pathFileDue));
    }// end of single test


    /**
     * Copia tutta una directory ESCLUSO il file indicato <br>
     * <p>
     * Esegue solo se il il file esiste nel srcPath <br>
     * Esegue solo se il il file esiste nel destPath <br>
     * Esegue solo se la dir interna del file è la stessa in srcPath e destPath <br>
     * Viene ignorato il file sorgente srcPath + dirFileToBeKeep <br>
     * Viene mantenuto il file esistente destPath + dirFileToBeKeep <br>
     *
     * @param srcPath         nome completo della directory sorgente
     * @param destPath        nome completo della directory destinazione
     * @param dirFileToBeKeep dir interna del file da mantenere - deve essere uguale in srcPath e in destPath
     *
     * @return testo di errore, vuoto se la directory è stata copiata ed il il file mantenuto originale
     */
    @Test
    public void copyDirectoryLessFile() {
        String sorgente = PATH_DIRECTORY_TEST + "Alfetta/";
        String destinazione = PATH_DIRECTORY_TEST + "Beretta/";
        String dirUno = "PrimaDirectory/";
        String dirDue = "SecondaDirectory/";
        creaCartelleTemporanee(sorgente, destinazione, dirUno, dirDue);
    }// end of single test

}// end of class
