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
    security(Chiave.flagSecurity, "Utilizza Spring Security", true, true, false, false, false, VUOTA),
    documentation(Chiave.flagDocumentation, "Directory documentazione", true, true, false, false, true, VUOTA),
    links(Chiave.flagLinks, "Directory links a web", true, true, false, false, true, VUOTA),
    snippets(Chiave.flagSnippets, "Directory snippets di aiuto", true, true, false, false, true, VUOTA),
    flow(Chiave.flagDirectoryFlow, "Copia la cartella VaadFlow", true, true, false, false, true, VUOTA),
    project(Chiave.flagDirectoryNewProject, "Crea la cartella del nuovo progetto", true, true, false, false, true, VUOTA),
    resources(Chiave.flagResources, "Directory resources - ATTENZIONE", true, true, false, false, true, VUOTA),
    property(Chiave.flagProperties, "File application.properties", true, true, false, false, true, VUOTA),
    read(Chiave.flagRead, "File READ con note di testo", true, true, false, false, true, VUOTA),
    git(Chiave.flagGit, "File GIT di esclusione", true, true, false, false, true, VUOTA),
    pom(Chiave.flagPom, "File Maven di POM.xml - ATTENZIONE", true, true, false, false, true, VUOTA),
    file(Chiave.flagSovrascriveFile, "Sovrascrive il singolo FILE", false, true, false, false, true, VUOTA),
    directory(Chiave.flagSovrascriveDirectory, "Sovrascrive la DIRECTORY", false, true, false, false, true, VUOTA),
    ;


    private Chiave chiave;

    private String labelBox;

    private boolean newProject;

    private boolean updateProject;

    private boolean newPackage;

    private boolean updatePackage;

    private boolean defaultStatus;

    private boolean status;

    private String defaultValue;

    private String value;


    EAWiz(Chiave chiave, String labelBox, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, boolean defaultStatus, String defaultValue) {
        this.chiave = chiave;
        this.labelBox = labelBox;
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.newPackage = newPackage;
        this.updatePackage = updatePackage;
        this.defaultStatus = defaultStatus;
        this.status = defaultStatus;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }// fine del costruttore


    public static void reset() {
        for (EAWiz eaWiz : EAWiz.values()) {
            eaWiz.setStatus(eaWiz.defaultStatus);
            eaWiz.setValue(eaWiz.defaultValue);
        }// end of for cycle
    }// end of method


    public Chiave getChiave() {
        return chiave;
    }// end of method


    public String getLabelBox() {
        return labelBox;
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
