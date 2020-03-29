package it.algos.vaadtest.alex;

import com.vaadin.flow.templatemodel.AllowClientUpdates;

import java.io.Serializable;

public class PolymerItem implements Serializable {

    private String name;

    private String icon;

    private String city;

    private String inizio;

    public PolymerItem() {
    }


    public PolymerItem(String name, String icon, String city, String inizio) {
        this.name = name;
        this.icon = icon;
        this.city = city;
        this.inizio = inizio;
    }


    public String getName() {
        return name;
    }


    @AllowClientUpdates
    public void setName(String name) {
        this.name = name;
    }


    public String getIcon() {
        return icon;
    }


    @AllowClientUpdates
    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getCity() {
        return city;
    }


    @AllowClientUpdates
    public void setCity(String city) {
        this.city = city;
    }


    public String getInizio() {
        return inizio;
    }

    @AllowClientUpdates
    public void setInizio(String inizio) {
        this.inizio = inizio;
    }



    @Override
    public String toString() {
        return "PolymerItem{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", city=" + city +
                '}';
    }

}
