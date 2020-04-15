package it.algos.vaadflow.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadflow.application.FlowCost.VUOTA;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: mar, 06-mar-2018
 * Time: 09:54
 * <p>
 * Gestione dei file di sistema
 * <p>
 * Classe di libreria; NON deve essere astratta, altrimenti Spring non la costruisce <br>
 * Implementa il 'pattern' SINGLETON; l'istanza può essere richiamata con: <br>
 * 1) StaticContextAccessor.getBean(AFileService.class); <br>
 * 2) AFileService.getInstance(); <br>
 * 3) @Autowired private AFileService fileService; <br>
 * <p>
 * Annotated with @Service (obbligatorio, se si usa la catena @Autowired di SpringBoot) <br>
 * NOT annotated with @SpringComponent (inutile, esiste già @Service) <br>
 * NOT annotated with @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON) (inutile, basta il 'pattern') <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 * <p>
 */
@Service
@Slf4j
public class AFileService extends AbstractService {

    public static final String PARAMETRO_NULLO = "Il parametro in ingresso è nullo";

    public static final String PATH_NULLO = "Il path in ingresso è nullo o vuoto";

    public static final String PATH_NOT_ABSOLUTE = "Il primo carattere del path NON è uno '/' (slash)";

    public static final String NON_ESISTE_FILE = "Il file non esiste";

    public static final String PATH_SENZA_SUFFIX = "Manca il 'suffix' terminale";


    public static final String NON_E_FILE = "Non è un file";

    public static final String NON_CREATO_FILE = "Il file non è stato creato";

    public static final String NON_CANCELLATO_FILE = "Il file non è stato cancellato";

    public static final String NON_ESISTE_DIRECTORY = "La directory non esiste";

    public static final String NON_CREATA_DIRECTORY = "La directory non è stata creata";


    public static final String NON_E_DIRECTORY = "Non è una directory";


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    /**
     * Private final property
     */
    private static final AFileService INSTANCE = new AFileService();


    /**
     * Private constructor to avoid client applications to use constructor
     */
    private AFileService() {
    }// end of constructor


    /**
     * Gets the unique instance of this Singleton.
     *
     * @return the unique instance of this Singleton
     */
    public static AFileService getInstance() {
        return INSTANCE;
    }// end of static method


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeChecked path completo del file che DEVE cominciare con '/' SLASH
     *
     * @return true se il file esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteFile(String absolutePathFileWithSuffixToBeChecked) {
        return isEsisteFileStr(absolutePathFileWithSuffixToBeChecked).equals(VUOTA);
    }// end of method


    /**
     * Controlla l'esistenza di un file <br>
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param absolutePathFileWithSuffixToBeChecked path completo del file che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file esiste
     */
    public String isEsisteFileStr(String absolutePathFileWithSuffixToBeChecked) {
        String risposta = VUOTA;

        if (text.isEmpty(absolutePathFileWithSuffixToBeChecked)) {
            return PATH_NULLO;
        }// end of if cycle

        if (text.isNotSlasch(absolutePathFileWithSuffixToBeChecked)) {
            return PATH_NOT_ABSOLUTE;
        }// end of if cycle

        risposta = isEsisteFileStr(new File(absolutePathFileWithSuffixToBeChecked));
        if (!risposta.equals(VUOTA)) {
            if (isEsisteDirectory(new File(absolutePathFileWithSuffixToBeChecked))) {
                return NON_E_FILE;
            }// end of if cycle

            if (text.isNotSuffix(absolutePathFileWithSuffixToBeChecked)) {
                return PATH_SENZA_SUFFIX;
            }// end of if cycle
        }// end of if cycle

        return risposta;
    }// end of method


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
    public boolean isEsisteFile(File fileToBeChecked) {
        return isEsisteFileStr(fileToBeChecked).equals(VUOTA);
    }// end of method


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
     * @return testo di errore, vuoto se il file esiste
     */
    public String isEsisteFileStr(File fileToBeChecked) {
        if (fileToBeChecked == null) {
            return PARAMETRO_NULLO;
        }// end of if cycle

        if (text.isEmpty(fileToBeChecked.getName())) {
            return PATH_NULLO;
        }// end of if cycle

        if (!fileToBeChecked.getPath().equals(fileToBeChecked.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }// end of if/else cycle

        if (fileToBeChecked.exists()) {
            if (fileToBeChecked.isFile()) {
                return VUOTA;
            } else {
                return NON_E_FILE;
            }// end of if/else cycle
        } else {
            if (text.isNotSuffix(fileToBeChecked.getAbsolutePath())) {
                return PATH_SENZA_SUFFIX;
            }// end of if cycle

            if (!fileToBeChecked.exists()) {
                return NON_ESISTE_FILE;
            }// end of if cycle

            return VUOTA;
        }// end of if/else cycle
    }// end of method


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
    public boolean isEsisteDirectory(String absolutePathDirectoryToBeChecked) {
        return isEsisteDirectoryStr(absolutePathDirectoryToBeChecked).equals(VUOTA);
    }// end of method


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
     * @return testo di errore, vuoto se la directory esiste
     */
    public String isEsisteDirectoryStr(String absolutePathDirectoryToBeChecked) {
        if (text.isEmpty(absolutePathDirectoryToBeChecked)) {
            return PATH_NULLO;
        }// end of if cycle

        if (text.isNotSlasch(absolutePathDirectoryToBeChecked)) {
            return PATH_NOT_ABSOLUTE;
        }// end of if cycle

        return isEsisteDirectoryStr(new File(absolutePathDirectoryToBeChecked));
    }// end of method


    /**
     * Controlla l'esistenza di una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param directoryToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory esiste, false se non sono rispettate le condizioni della richiesta
     */
    public boolean isEsisteDirectory(File directoryToBeChecked) {
        return isEsisteDirectoryStr(directoryToBeChecked).equals(VUOTA);
    }// end of method


    /**
     * Controlla l'esistenza di una directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     * Una volta costruita la directory, getPath() e getAbsolutePath() devono essere uguali
     *
     * @param directoryToBeChecked con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file esiste
     */
    public String isEsisteDirectoryStr(File directoryToBeChecked) {
        if (directoryToBeChecked == null) {
            return PARAMETRO_NULLO;
        }// end of if cycle

        if (text.isEmpty(directoryToBeChecked.getName())) {
            return PATH_NULLO;
        }// end of if cycle

        if (!directoryToBeChecked.getPath().equals(directoryToBeChecked.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }// end of if/else cycle

        if (directoryToBeChecked.exists()) {
            if (directoryToBeChecked.isDirectory()) {
                return VUOTA;
            } else {
                return NON_E_DIRECTORY;
            }// end of if/else cycle
        } else {
            return NON_ESISTE_DIRECTORY;
        }// end of if/else cycle
    }// end of method


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
     * @return true se il file è stato creato, false se non sono rispettate le condizioni della richiesta
     */
    public boolean creaFile(String absolutePathFileWithSuffixToBeCreated) {
        return creaFileStr(absolutePathFileWithSuffixToBeCreated).equals(VUOTA);
    }// end of method


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
     * @return testo di errore, vuoto se il file è stato creato
     */
    public String creaFileStr(String absolutePathFileWithSuffixToBeCreated) {

        if (text.isEmpty(absolutePathFileWithSuffixToBeCreated)) {
            return PATH_NULLO;
        }// end of if cycle

        return creaFileStr(new File(absolutePathFileWithSuffixToBeCreated));
    }// end of method


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
     * @param fileToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file è stato creato, false se non sono rispettate le condizioni della richiesta
     */
    public boolean creaFile(File fileToBeCreated) {
        return creaFileStr(fileToBeCreated).equals(VUOTA);
    }// end of method


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
     * @param fileToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public String creaFileStr(File fileToBeCreated) {
        if (fileToBeCreated == null) {
            return PARAMETRO_NULLO;
        }// end of if cycle

        if (text.isEmpty(fileToBeCreated.getName())) {
            return PATH_NULLO;
        }// end of if cycle

        if (!fileToBeCreated.getPath().equals(fileToBeCreated.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }// end of if/else cycle

        if (text.isNotSuffix(fileToBeCreated.getAbsolutePath())) {
            return PATH_SENZA_SUFFIX;
        }// end of if cycle

        try { // prova ad eseguire il codice
            fileToBeCreated.createNewFile();
        } catch (Exception unErrore) { // intercetta l'errore
            return NON_CREATO_FILE;
        }// fine del blocco try-catch

        return fileToBeCreated.exists() ? VUOTA : NON_CREATO_FILE;
    }// end of method


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
     * @return true se la directory è stata creata, false se non sono rispettate le condizioni della richiesta
     */
    public boolean creaDirectory(String absolutePathDirectoryToBeCreated) {
        return creaDirectoryStr(absolutePathDirectoryToBeCreated).equals(VUOTA);
    }// end of method


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
    public String creaDirectoryStr(String absolutePathDirectoryToBeCreated) {
        if (text.isEmpty(absolutePathDirectoryToBeCreated)) {
            return PATH_NULLO;
        }// end of if cycle

        return creaDirectoryStr(new File(absolutePathDirectoryToBeCreated));
    }// end of method


    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param directoryToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stata creata, false se non sono rispettate le condizioni della richiesta
     */
    public boolean creaDirectory(File directoryToBeCreated) {
        return creaDirectoryStr(directoryToBeCreated).equals(VUOTA);
    }// end of method


    /**
     * Crea una nuova directory
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param directoryToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public String creaDirectoryStr(File directoryToBeCreated) {
        if (directoryToBeCreated == null) {
            return PARAMETRO_NULLO;
        }// end of if cycle

        if (text.isEmpty(directoryToBeCreated.getName())) {
            return PATH_NULLO;
        }// end of if cycle

        if (!directoryToBeCreated.getPath().equals(directoryToBeCreated.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }// end of if/else cycle

        if (!text.isNotSuffix(directoryToBeCreated.getAbsolutePath())) {
            return NON_E_DIRECTORY;
        }// end of if cycle

        try { // prova ad eseguire il codice
            directoryToBeCreated.mkdirs();
        } catch (Exception unErrore) { // intercetta l'errore
            return NON_CREATA_DIRECTORY;
        }// fine del blocco try-catch

        return directoryToBeCreated.exists() ? VUOTA : NON_CREATA_DIRECTORY;
    }// end of method


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
    public boolean deleteFile(String absolutePathFileWithSuffixToBeCanceled) {
        return deleteFileStr(absolutePathFileWithSuffixToBeCanceled).equals(VUOTA);
    }// end of method


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
     * @return testo di errore, vuoto se il file è stato cancellato
     */
    public String deleteFileStr(String absolutePathFileWithSuffixToBeCanceled) {
        if (text.isEmpty(absolutePathFileWithSuffixToBeCanceled)) {
            return PATH_NULLO;
        }// end of if cycle

        return deleteFileStr(new File(absolutePathFileWithSuffixToBeCanceled));
    }// end of method


    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file è stato cancellato oppure non esisteva
     */
    public boolean deleteFile(File fileToBeDeleted) {
        return deleteFileStr(fileToBeDeleted).equals(VUOTA);
    }// end of method


    /**
     * Cancella un file
     * <p>
     * Il path non deve essere nullo <br>
     * Il path non deve essere vuoto <br>
     * Il path deve essere completo ed inziare con uno 'slash' <br>
     * Il path deve essere completo e terminare con un 'suffix' <br>
     * La richiesta è CASE INSENSITIVE (maiuscole e minuscole SONO uguali) <br>
     *
     * @param fileToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato creato
     */
    public String deleteFileStr(File fileToBeDeleted) {

        if (fileToBeDeleted == null) {
            return PARAMETRO_NULLO;
        }// end of if cycle

        if (text.isEmpty(fileToBeDeleted.getName())) {
            return PATH_NULLO;
        }// end of if cycle

        if (!fileToBeDeleted.getPath().equals(fileToBeDeleted.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }// end of if/else cycle

        if (text.isNotSuffix(fileToBeDeleted.getAbsolutePath())) {
            return PATH_SENZA_SUFFIX;
        }// end of if cycle

        if (!fileToBeDeleted.exists()) {
            return NON_ESISTE_FILE;
        }// end of if cycle

        if (fileToBeDeleted.delete()) {
            return VUOTA;
        } else {
            return NON_CANCELLATO_FILE;
        }// end of if/else cycle

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
    public boolean deleteDirectory(String absolutePathDirectoryToBeDeleted) {
        return deleteDirectoryStr(absolutePathDirectoryToBeDeleted).equals(VUOTA);
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
     * @return testo di errore, vuoto se il file è stato cancellato
     */
    public String deleteDirectoryStr(String absolutePathDirectoryToBeDeleted) {
        if (text.isEmpty(absolutePathDirectoryToBeDeleted)) {
            return PATH_NULLO;
        }// end of if cycle

        return deleteDirectoryStr(new File(absolutePathDirectoryToBeDeleted));
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
     * @param directoryToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file è stato cancellato oppure non esisteva
     */
    public boolean deleteDirectory(File directoryToBeDeleted) {
        return deleteDirectoryStr(directoryToBeDeleted).equals(VUOTA);
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
     * @param directoryToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return testo di errore, vuoto se il file è stato cancellato
     */
    public String deleteDirectoryStr(File directoryToBeDeleted) {
        if (directoryToBeDeleted == null) {
            return PARAMETRO_NULLO;
        }// end of if cycle

        if (text.isEmpty(directoryToBeDeleted.getName())) {
            return PATH_NULLO;
        }// end of if cycle

        if (!directoryToBeDeleted.getPath().equals(directoryToBeDeleted.getAbsolutePath())) {
            return PATH_NOT_ABSOLUTE;
        }// end of if/else cycle

        if (!directoryToBeDeleted.exists()) {
            return NON_ESISTE_FILE;
        }// end of if cycle

        if (directoryToBeDeleted.delete()) {
            return VUOTA;
        } else {
            return NON_CANCELLATO_FILE;
        }// end of if/else cycle
    }// end of method

    /**
     * Copia una directory
     *
     * @param srcPath  nome completo della directory sorgente
     * @param destPath nome completo della directory destinazione
     */
    public boolean copyDirectory(String srcPath, String destPath) {
        boolean copiata = false;
        File srcDir = new File(srcPath);
        File destDir = new File(destPath);

        try { // prova ad eseguire il codice
            FileUtils.copyDirectory(srcDir, destDir);
            copiata = true;
        } catch (IOException e) {
            e.printStackTrace();
        }// fine del blocco try-catch

        return copiata;
    }// end of method


    /**
     * Copia un file
     *
     * @param srcPath  nome completo del file sorgente
     * @param destPath nome completo del file destinazione
     */
    public boolean copyFile(String srcPath, String destPath) {
        boolean copiato = false;
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);

        try { // prova ad eseguire il codice
            FileUtils.copyFile(srcFile, destFile);
            copiato = true;
        } catch (IOException e) {
            e.printStackTrace();
        }// fine del blocco try-catch

        return copiato;
    }// end of method


    /**
     * Scrive un file
     * Se non esiste, lo crea
     *
     * @param pathFileToBeWritten nome completo del file
     * @param text                contenuto del file
     */
    public boolean scriveFile(String pathFileToBeWritten, String text) {
        return scriveFile(pathFileToBeWritten, text, false);
    }// end of method


    /**
     * Scrive un file
     * Se non esiste, lo crea
     *
     * @param pathFileToBeWritten nome completo del file
     * @param text                contenuto del file
     * @param sovrascrive         anche se esiste già
     */
    public boolean scriveFile(String pathFileToBeWritten, String text, boolean sovrascrive) {
        boolean status = false;
        File fileToBeWritten;
        FileWriter fileWriter;

        if (isEsisteFile(pathFileToBeWritten)) {
            if (sovrascrive) {
                status = sovraScriveFile(pathFileToBeWritten, text);
                System.out.println("Il file " + pathFileToBeWritten + " esisteva già ed è stato aggiornato");
            } else {
                System.out.println("Il file " + pathFileToBeWritten + " esisteva già e non è stato modificato");
                return false;
            }// end of if/else cycle
        } else {
            status = creaFile(pathFileToBeWritten);
            if (status) {
                status = sovraScriveFile(pathFileToBeWritten, text);
                System.out.println("Il file " + pathFileToBeWritten + " non esisteva ed è stato creato");
            } else {
                System.out.println("Il file " + pathFileToBeWritten + " non esisteva e non è stato creato");
                return false;
            }// end of if/else cycle
        }// end of if/else cycle

        return status;
    }// end of method


    /**
     * Sovrascrive un file
     *
     * @param pathFileToBeWritten nome completo del file
     * @param text                contenuto del file
     */
    public boolean sovraScriveFile(String pathFileToBeWritten, String text) {
        boolean status = false;
        File fileToBeWritten;
        FileWriter fileWriter = null;

        if (isEsisteFile(pathFileToBeWritten)) {
            fileToBeWritten = new File(pathFileToBeWritten);
            try { // prova ad eseguire il codice
                fileWriter = new FileWriter(fileToBeWritten);
                fileWriter.write(text);
                fileWriter.flush();
                status = true;
            } catch (Exception unErrore) { // intercetta l'errore
                log.error(unErrore.toString());
            } finally {
                try { // prova ad eseguire il codice
                    if (fileWriter != null) {
                        fileWriter.close();
                    }
                } catch (Exception unErrore) { // intercetta l'errore
                    log.error(unErrore.toString());
                }// fine del blocco try-catch
            }// fine del blocco try-catch-finally
        } else {
            System.out.println("Il file " + pathFileToBeWritten + " non esiste e non è stato creato");
        }// end of if/else cycle

        return status;
    }// end of method


    /**
     * Legge un file
     *
     * @param pathFileToBeRead nome completo del file
     */
    public String leggeFile(String pathFileToBeRead) {
        String testo = "";
        String aCapo = "\n";
        String currentLine;

        //-- non va, perché se arriva it/algos/Alfa.java becca anche il .java
//        nameFileToBeRead=  nameFileToBeRead.replaceAll("\\.","/");

        try (BufferedReader br = new BufferedReader(new FileReader(pathFileToBeRead))) {
            while ((currentLine = br.readLine()) != null) {
                testo += currentLine;
                testo += "\n";
            }// fine del blocco while

            testo = text.levaCoda(testo, aCapo);
        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
        }// fine del blocco try-catch

        return testo;
    }// end of method


    /**
     * Estrae le sub-directories da una directory
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<String> getSubDirectories(String pathDirectoryToBeScanned) {
        List<String> subDirectory = null;
        File[] allFiles = null;
        File directory = new File(pathDirectoryToBeScanned);

        if (directory != null) {
            allFiles = directory.listFiles();
        }// end of if cycle

        if (allFiles != null) {
            subDirectory = new ArrayList<>();
            for (File file : allFiles) {
                if (file.isDirectory()) {
                    subDirectory.add(file.getName());
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        return subDirectory;
    }// end of method


    /**
     * Estrae i files da una directory
     *
     * @param pathDirectoryToBeScanned nome completo della directory
     */
    public List<String> getFiles(String pathDirectoryToBeScanned) {
        List<String> files = null;
        File[] allFiles = null;
        File directory = new File(pathDirectoryToBeScanned);

        if (directory != null) {
            allFiles = directory.listFiles();
        }// end of if cycle

        if (allFiles != null) {
            files = new ArrayList<>();
            for (File file : allFiles) {
                if (!file.isDirectory()) {
                    files.add(file.getName());
                }// end of if cycle
            }// end of for cycle
        }// end of if cycle

        return files;
    }// end of method

}// end of class
