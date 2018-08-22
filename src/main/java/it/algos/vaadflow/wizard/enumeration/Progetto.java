package it.algos.vaadflow.wizard.enumeration;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: gio, 08-mar-2018
 * Time: 08:23
 */
@Slf4j
public enum Progetto {

    vaadin("vaadflow", "vaadflow", "vaadtest", "FlowCost"),
    test("vaadflow", "vaadtest", "vaadtest", "TestCost"),
    bio("vaadbio", "vaadbio", "vaadbio", "BioCost"),
    wam("vaadwam", "vaadwam", "vaadwam", "WamCost"),
    ;

    private String nameProject;
    private String nameModule;
    private String nameLayout;
    private String nameCost;


    Progetto(String nameProject, String nameModule, String nameLayout, String nameCost) {
        this.setNameProject(nameProject);
        this.setNameModule(nameModule);
        this.setNameLayout(nameLayout);
        this.setNameCost(nameCost);
    }// fine del costruttore

    public static List<String> getNames() {
        List<String> nomi = new ArrayList<>();
        Progetto[] allProgetti = Progetto.values();

        for (Progetto progetto : Progetto.values()) {
            nomi.add(progetto.nameProject);
        }// end of for cycle

        return nomi;
    }// end of method

    public String getNameProject() {
        return nameProject;
    }// end of method

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }// end of method

    public String getNameModule() {
        return nameModule;
    }// end of method

    public void setNameModule(String nameModule) {
        this.nameModule = nameModule;
    }// end of method

    public String getNameLayout() {
        return nameLayout;
    }// end of method

    public void setNameLayout(String nameLayout) {
        this.nameLayout = nameLayout;
    }// end of method

    public String getNameCost() {
        return nameCost;
    }// end of method

    public void setNameCost(String nameCost) {
        this.nameCost = nameCost;
    }// end of method

}// end of enumeration class
