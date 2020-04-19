package it.algos.vaadflow.wiz.enumeration;

import static it.algos.vaadflow.application.FlowCost.VUOTA;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 18-apr-2020
 * Time: 06:27
 */
public enum EAWiz {
    flagSecurity(true, "Utilizza Spring Security", true, false, false, false, false),
    flagDocumentation(true, "Directory documentazione", true, true, false, false, true),
    flagLinks(true, "Directory links a web", true, true, false, false, true),
    flagSnippets(true, "Directory snippets di aiuto", true, true, false, false, true),
    flow(true, "Copia la cartella VaadFlow", true, true, false, false, true),
    flagProject(true, "Crea la cartella del nuovo progetto", true, false, false, false, true),
    flagResources(true, "Directory resources - ATTENZIONE", true, true, false, false, true),
    flagProperty(true, "File application.properties", true, true, false, false, true),
    flagRead(true, "File READ con note di testo", true, true, false, false, true),
    flagGit(true, "File GIT di esclusione", true, true, false, false, true),
    flagPom(true, "File Maven di POM.xml - ATTENZIONE", true, true, false, false, true),
    flagFile(true, "Sovrascrive il singolo FILE", false, false, false, false, true),
    flagDirectory(true, "Sovrascrive la DIRECTORY", false, false, false, false, true),
    //    flagBanner(true, "File banner di SpringBoot", true, true, false, false, true),
    pathUserDir(false, "Directory recuperata dal System dove gira il programma in uso", true, true, false, false, true, VUOTA),
    pathVaadFlow(false, "Directory che contiene il programma VaadFlow", true, true, false, false, true, VUOTA),
    pathProjectsDir(false, "Directory che contiene i nuovi programmi appena creati da Idea", true, true, false, false, true, VUOTA),
    pathVaadFlowSources(false, "Directory dei sorgenti testuali di VaadFlow da elaborare", true, true, false, false, true, VUOTA),
    newProjectName(false, "Nome breve del nuovo progetto", true, true, false, false, true, VUOTA),
    pathProjet(false, "Path del nuovo progetto", true, true, false, false, true, VUOTA),
    ;


    private boolean checkBox;

    private String labelBox;

    private String descrizione;

    private boolean newProject;

    private boolean updateProject;

    private boolean newPackage;

    private boolean updatePackage;

    private boolean defaultStatus = false;

    private boolean status;

    private String defaultValue = VUOTA;

    private String value;


    /**
     * Costruttore per i flag con valore booleano <br>
     */
    EAWiz(boolean checkBox, String unTesto, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, boolean defaultStatus) {
        this(checkBox, unTesto, newProject, updateProject, newPackage, updatePackage, defaultStatus, VUOTA);
    }// fine del costruttore


    /**
     * Costruttore per i path con valore stringa <br>
     */
    EAWiz(boolean checkBox, String unTesto, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, String defaultValue) {
        this(checkBox, unTesto, newProject, updateProject, newPackage, updatePackage, false, defaultValue);
    }// fine del costruttore


    /**
     * Costruttore completo <br>
     */
    EAWiz(boolean checkBox, String unTesto, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, boolean defaultStatus, String defaultValue) {
        this.checkBox = checkBox;
        if (checkBox) {
            this.labelBox = unTesto;
        } else {
            this.descrizione = unTesto;
        }// end of if/else cycle
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.newPackage = newPackage;
        this.updatePackage = updatePackage;

        if (checkBox) {
            this.defaultStatus = defaultStatus;
            this.status = defaultStatus;
        } else {
            this.defaultValue = defaultValue;
            this.value = defaultValue;
        }// end of if/else cycle
    }// fine del costruttore


    public static void reset() {
        for (EAWiz eaWiz : EAWiz.values()) {
            eaWiz.setStatus(eaWiz.defaultStatus);
            eaWiz.setValue(eaWiz.defaultValue);
        }// end of for cycle
    }// end of method


    public boolean isCheckBox() {
        return checkBox;
    }// end of method


    public String getLabelBox() {
        return labelBox;
    }// end of method


    public String getDescrizione() {
        if (checkBox) {
            if (descrizione != null && descrizione.length() > 0) {
                return descrizione;
            } else {
                return labelBox;
            }// end of if/else cycle
        } else {
            return descrizione;
        }// end of if/else cycle
    }// end of method


    public boolean isNewProject() {
        return newProject;
    }// end of method


    public boolean isUpdateProject() {
        return updateProject;
    }// end of method


    public boolean isNewPackage() {
        return newPackage;
    }// end of method


    public boolean isUpdatePackage() {
        return updatePackage;
    }// end of method


    public boolean isAbilitato() {
        return status;
    }// end of method


    public boolean isStatus() {
        return status;
    }// end of method


    public void setStatus(boolean status) {
        this.status = status;
    }// end of method


    public String getValue() {
        return value;
    }// end of method


    public void setValue(String value) {
        this.value = value;
    }// end of method


}// end of enumeration
