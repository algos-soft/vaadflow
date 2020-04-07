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
    basilicata("Abruzzo", "dell'Abruzzo", ""),
    calabria("Abruzzo", "dell'Abruzzo", ""),
    campania("Abruzzo", "dell'Abruzzo", ""),
    emilia("Abruzzo", "dell'Abruzzo", ""),
    friuli("Abruzzo", "dell'Abruzzo", ""),
    lazio("Abruzzo", "dell'Abruzzo", ""),
    liguria("Abruzzo", "dell'Abruzzo", ""),
    lombardia("Abruzzo", "dell'Abruzzo", ""),
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

     String nome;

     String paginaWiki;

     String titoli;


    EARegione(String nome, String paginaWiki, String titoli) {
        this.nome = nome;
        this.paginaWiki = paginaWiki;
        this.titoli = titoli;
    }// end of constructor

}// end of enum class
