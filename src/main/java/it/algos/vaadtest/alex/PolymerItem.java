package it.algos.vaadtest.alex;

import java.io.Serializable;

/**
 * Singola iscrizione.
 */
public class PolymerItem implements Serializable {


    private String name;

    private String icon;

    private int score;


    public PolymerItem() {
        reset();
    }


    public PolymerItem(String name, String icon, int score) {
        this.name = name;
        this.icon=icon;
        this.score = score;
    }



    /**
     * Resets all fields to their default values.
     */
    public void reset() {
        this.name = "";
        this.icon="";
        this.score = 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "PolymerItem{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", score=" + score +
                '}';
    }
}
