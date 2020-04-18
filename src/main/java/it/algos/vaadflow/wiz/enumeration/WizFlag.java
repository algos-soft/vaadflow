package it.algos.vaadflow.wiz.enumeration;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: sab, 18-apr-2020
 * Time: 06:27
 */
public enum WizFlag {
    security("Utilizza Spring Security", true, true, false, false, false),
    documentation("Directory documentazione", true, true, false, false, true),
    links("Directory links a web", true, true, false, false, true),
    snippets("Directory snippets di aiuto", true, true, false, false, true),
    flow("Copia la cartella VaadFlow", true, true, false, false, true),
    project("Crea la cartella del nuovo progetto", true, true, false, false, true),
    resources("Directory resources - ATTENZIONE", true, true, false, false, true),
    property("File application.properties", true, true, false, false, true),
    read("File READ con note di testo", true, true, false, false, true),
    git("File GIT di esclusione", true, true, false, false, true),
    pom("File Maven di POM.xml - ATTENZIONE", true, true, false, false, true),
    file("Sovrascrive il singolo FILE", false, true, false, false, true),
    directory("Sovrascrive la DIRECTORY", false, true, false, false, true),
    ;


    private String labelBox;

    private boolean newProject;

    private boolean updateProject;

    private boolean newPackage;

    private boolean updatePackage;

    private boolean status;


    WizFlag(String labelBox, boolean newProject, boolean updateProject, boolean newPackage, boolean updatePackage, boolean status) {
        this.labelBox = labelBox;
        this.newProject = newProject;
        this.updateProject = updateProject;
        this.newPackage = newPackage;
        this.updatePackage = updatePackage;
        this.status = status;
    }// fine del costruttore


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
}// end of enumeration
