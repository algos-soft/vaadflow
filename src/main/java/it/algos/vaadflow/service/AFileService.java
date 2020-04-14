package it.algos.vaadflow.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

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

    public static final String PATH_SENZA_SUFFIX = "Manca il 'suffix' terminale";

    public static final String NON_ESISTE_FILE = "Il file non esiste";

    public static final String NON_ESISTE_DIRECTORY = "La directory non esiste";

    public static final String NON_E_FILE = "Non è un file";


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
//        File directoryToBeChecked;
//
//        if (text.isEmpty(absolutePathDirectoryToBeChecked)) {
//            System.out.println("Il parametro 'absolutePathDirectoryToBeChecked' è nullo o vuoto");
//            return false;
//        }// end of if cycle
//
//        directoryToBeChecked = new File(absolutePathDirectoryToBeChecked);
//        return isEsisteDirectory(directoryToBeChecked);
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
//        boolean esiste = false;
//
//        if (directoryToBeChecked == null) {
//            System.out.println("La Directory 'directoryToBeChecked' è nulla");
//            return false;
//        }// end of if cycle
//
//        if (directoryToBeChecked.getPath().equals(directoryToBeChecked.getAbsolutePath())) {
//            if (directoryToBeChecked.exists()) {
//                if (directoryToBeChecked.isDirectory()) {
//                    esiste = true;
//                    System.out.println("La directory " + directoryToBeChecked + " esiste");
//                } else {
//                    System.out.println(directoryToBeChecked + " non è una directory");
//                }// end of if/else cycle
//            } else {
//                System.out.println("La directory " + directoryToBeChecked + " non esiste");
//            }// end of if/else cycle
//        } else {
//            System.out.println("Il path della directory non è assoluto");
//        }// end of if/else cycle
//
//        return esiste;
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
            if (text.isNotSuffix(directoryToBeChecked.getAbsolutePath())) {
                return PATH_SENZA_SUFFIX;
            }// end of if cycle

            if (!directoryToBeChecked.exists()) {
                return NON_ESISTE_DIRECTORY;
            }// end of if cycle

            return VUOTA;
        }// end of if/else cycle
    }// end of method


    /**
     * Crea un nuovo file
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
    public boolean creaFile(String absolutePathFileWithSuffixToBeCreated) {
        File fileCreated;
        String primoCarattere;

        if (text.isEmpty(absolutePathFileWithSuffixToBeCreated)) {
            System.out.println("Il parametro 'absolutePathFileToBeCreated' è nullo o vuoto");
            return false;
        }// end of if cycle

        primoCarattere = absolutePathFileWithSuffixToBeCreated.substring(0, 1);
        if (!primoCarattere.equals("/")) {
            System.out.println("Il primo carattere di 'absolutePathFileToBeCreated' NON è uno '/' (slash)");
            return false;
        }// end of if cycle

        fileCreated = new File(absolutePathFileWithSuffixToBeCreated);
        return creaFile(fileCreated);
    }// end of method


    /**
     * Crea un nuovo file
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Deve cominciare con lo slash
     * Deve comprendere anche l'estensione
     * La richiesta NON è CASE sensitive
     * Se manca la directory, la crea (il sistema in automatico)
     *
     * @param fileToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file è stato creato oppure esisteva già
     * false se non è un file o se non esiste
     */
    public boolean creaFile(File fileToBeCreated) {
        if (fileToBeCreated.exists()) {
            System.out.println("Il file " + fileToBeCreated + " non è stato creato, perché esiste già");
        } else {
            try { // prova ad eseguire il codice
                fileToBeCreated.createNewFile();
            } catch (Exception unErrore) { // intercetta l'errore
                log.error(unErrore.toString());
            }// fine del blocco try-catch
        }// end of if/else cycle

        return fileToBeCreated.exists();
    }// end of method


    /**
     * Crea una nuova directory
     * La directory DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Deve cominciare con lo slash
     * Deve comprendere anche l'estensione
     * La richiesta NON è CASE sensitive
     *
     * @param absolutePathDirectoryToBeCreated path completo della directory che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stata creata oppure esisteva già
     * false se non è una directory o se non esiste
     */
    public boolean creaDirectory(String absolutePathDirectoryToBeCreated) {
        boolean creata;
        String primoCarattere;

        if (text.isEmpty(absolutePathDirectoryToBeCreated)) {
            System.out.println("Il parametro 'absolutePathDirectoryToBeCreated' è vuoto");
            return false;
        }// end of if cycle

        primoCarattere = absolutePathDirectoryToBeCreated.substring(0, 1);
        if (!primoCarattere.equals("/")) {
            System.out.println("Il primo carattere di 'absolutePathDirectoryToBeCreated' NON è uno '/' (slash)");
            return false;
        }// end of if cycle

        creata = new File(absolutePathDirectoryToBeCreated).mkdirs();
        if (creata) {
            System.out.println("La directory " + absolutePathDirectoryToBeCreated + " è stata creata");
        } else {
            if (isEsisteDirectory(absolutePathDirectoryToBeCreated)) {
                creata = true;
                System.out.println("La directory " + absolutePathDirectoryToBeCreated + " esisteva già");
            } else {
                System.out.println("La directory " + absolutePathDirectoryToBeCreated + " non è stato possibile crearla");
            }// end of if/else cycle
        }// end of if/else cycle

        return creata;
    }// end of method


    /**
     * Crea una nuova directory
     * La directory DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Deve cominciare con lo slash
     * Deve comprendere anche l'estensione
     * La richiesta NON è CASE sensitive
     *
     * @param directoryToBeCreated con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se la directory è stata creata oppure esisteva già
     * false se non è una directory o se non esiste
     */
    public boolean creaDirectory(File directoryToBeCreated) {
        if (directoryToBeCreated.exists()) {
            System.out.println("La directory " + directoryToBeCreated + " non è stata creata, perché esiste già");
        } else {
            try { // prova ad eseguire il codice
                String absolutePathDirectoryToBeCreated = directoryToBeCreated.getAbsolutePath();
                new File(absolutePathDirectoryToBeCreated).mkdirs();
            } catch (Exception unErrore) { // intercetta l'errore
                log.error(unErrore.toString());
            }// fine del blocco try-catch
        }// end of if/else cycle

        return directoryToBeCreated.exists();
    }// end of method


    /**
     * Cancella un file
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Deve cominciare con lo slash
     * Deve comprendere anche l'estensione
     * La richiesta NON è CASE sensitive
     *
     * @param absolutePathFileWithSuffixToBeCanceled path completo del file che DEVE cominciare con '/' SLASH e compreso il suffisso
     *
     * @return true se il file è stato cancellato oppure non esisteva
     */
    public boolean deleteFile(String absolutePathFileWithSuffixToBeCanceled) {
        File fileCanceled;
        String primoCarattere;

        if (text.isEmpty(absolutePathFileWithSuffixToBeCanceled)) {
            System.out.println("Il parametro 'absolutePathDirectoryToBeCreated' è vuoto");
            return false;
        }// end of if cycle

        primoCarattere = absolutePathFileWithSuffixToBeCanceled.substring(0, 1);
        if (!primoCarattere.equals("/")) {
            System.out.println("Il primo carattere di 'absolutePathDirectoryToBeCreated' NON è uno '/' (slash)");
            return false;
        }// end of if cycle

        fileCanceled = new File(absolutePathFileWithSuffixToBeCanceled);
        return deleteFile(fileCanceled);
    }// end of method


    /**
     * Cancella un file
     * Il file DEVE essere costruita col path completo, altrimenti assume che sia nella directory in uso corrente
     * Deve cominciare con lo slash
     * Deve comprendere anche l'estensione
     * La richiesta NON è CASE sensitive
     *
     * @param fileToBeDeleted con path completo che DEVE cominciare con '/' SLASH
     *
     * @return true se il file è stato cancellato oppure non esisteva
     */
    public boolean deleteFile(File fileToBeDeleted) {
        boolean status = false;

        status = fileToBeDeleted.exists();
        if (status) {
            if (fileToBeDeleted.isFile()) {
                status = fileToBeDeleted.delete();
                if (status) {
                    System.out.println("Il file " + fileToBeDeleted + " è stata cancellato");
                } else {
                    System.out.println("Il file " + fileToBeDeleted + " non è stata cancellato, perché non ci sono riuscito");
                }// end of if/else cycle
            } else {
                System.out.println(fileToBeDeleted + " non è stato cancellato, perché non è un file");
                status = false;
            }// end of if/else cycle
        } else {
            System.out.println("Il file " + fileToBeDeleted + " non è stata cancellato, perché non esiste");
        }// end of if/else cycle

        return status;
    }// end of method


    /**
     * Cancella una directory
     *
     * @param pathDirectoryToBeDeleted nome completo della directory
     */
    public boolean deleteDirectory(String pathDirectoryToBeDeleted) {
        return deleteDirectory(new File(pathDirectoryToBeDeleted.toLowerCase()));
    }// end of method


    /**
     * Cancella una directory
     *
     * @param directoryToBeDeleted file col path completo
     *
     * @return true se la directory è stata cancellata
     * false se non è stata cancellata o se non esiste
     */
    public boolean deleteDirectory(File directoryToBeDeleted) {
        boolean status = false;

        status = directoryToBeDeleted.exists();
        if (status) {
            if (directoryToBeDeleted.isDirectory()) {
                status = FileSystemUtils.deleteRecursively(directoryToBeDeleted);
                if (status) {
                    System.out.println("La directory " + directoryToBeDeleted + " è stata cancellata");
                } else {
                    System.out.println("La directory " + directoryToBeDeleted + " non è stata cancellata, perché non ci sono riuscito");
                }// end of if/else cycle
            } else {
                System.out.println(directoryToBeDeleted + " non è stato cancellato, perché non è una directory");
                status = false;
            }// end of if/else cycle
        } else {
            System.out.println("La directory " + directoryToBeDeleted + " non è stata cancellata, perché non esiste");
        }// end of if/else cycle

        return status;
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
