package it.algos.vaadflow.enumeration;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mar, 07-apr-2020
 * Time: 09:27
 */
public enum EARegione {
    abruzzo("Abruzzo", "dell'Abruzzo", "Comune,Provincia,Popolazione"),
    basilicata("Basilicata", "della Basilicata", "Comune,Provincia,Popolazione"),
    calabria("Abruzzo", "dell'Abruzzo", ""),
    campania("Abruzzo", "dell'Abruzzo", ""),
    emilia("Abruzzo", "dell'Abruzzo", ""),
    friuli("Abruzzo", "dell'Abruzzo", ""),
    lazio("Abruzzo", "dell'Abruzzo", ""),
    liguria("Abruzzo", "dell'Abruzzo", ""),
    lombardia("Lombardia", "della Lombardia", "Comune,Provincia,Popolazione"),
    marche("Abruzzo", "dell'Abruzzo", ""),
    molise("Abruzzo", "dell'Abruzzo", ""),
    piemonte("Abruzzo", "dell'Abruzzo", ""),
    puglia("Abruzzo", "dell'Abruzzo", ""),
    sardegna("Abruzzo", "dell'Abruzzo", ""),
    sicilia("Abruzzo", "dell'Abruzzo", ""),
    toscana("Abruzzo", "dell'Abruzzo", ""),
    trentino("Abruzzo", "dell'Abruzzo", ""),
    umbria("Abruzzo", "dell'Abruzzo", ""),
    aosta("Abruzzo", "dell'Abruzzo", ""),
    veneto("Abruzzo", "dell'Abruzzo", ""),
    ;

    static String COMUNI = "Comuni ";

    String nome;

    String paginaWikiShort;

    String titoli;


    EARegione(String nome, String paginaWikiShort, String titoli) {
        this.nome = nome;
        this.paginaWikiShort = paginaWikiShort;
        this.titoli = titoli;
    }// end of constructor


    public String getNome() {
        return nome;
    }// end of method


    public String getPaginaWikiShort() {
        return paginaWikiShort;
    }// end of method


    public String getPaginaWiki() {
        return COMUNI + paginaWikiShort;
    }// end of method


    public String getTitoli() {
        return titoli;
    }// end of method
}// end of enum class
