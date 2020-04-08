package it.algos.vaadflow.enumeration;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: mar, 07-apr-2020
 * Time: 09:27
 */
public enum EARegione {
    abruzzo("Abruzzo", "dell'Abruzzo", "Comune,Provincia,Popolazione", 305),
    basilicata("Basilicata", "della Basilicata", "Comune,Provincia,Popolazione",131),
    calabria("Calabria", "della Calabria", "Comune,Provincia,Popolazione",404),
    campania("Campania", "della Campania", "Comune,Area,Popolazione",550),
    emilia("Emilia-Romagna", "dell'Emilia-Romagna", "Comune,Provincia,Popolazione",328),
    friuli("Friuli-Venezia Giulia", "del Friuli-Venezia Giulia", "Comune,ex Provincia,Popolazione",215),
    lazio("Lazio", "del Lazio", "Comune,Provincia,Popolazione",378),
    liguria("Liguria", "della Liguria", "Comune,Provincia,Popolazione,Superficie<br />(km<sup>2</sup>)",234),
    lombardia("Lombardia", "della Lombardia", "Comune,Provincia,Popolazione",1506),
    marche("Marche", "delle Marche", "Comune,Provincia,Popolazione,Superficie<br />(km<sup>2</sup>)",228),
    molise("Molise", "del Molise", "pos.,comune,provincia,abitanti<sup id=\"cite_ref-1\" class=\"reference\"><a href=\"#cite_note-1\">&#91;1&#93;</a></sup>",136),
    piemonte("Abruzzo", "dell'Abruzzo", "",88),
    puglia("Abruzzo", "dell'Abruzzo", "",99),
    sardegna("Abruzzo", "dell'Abruzzo", "",77),
    sicilia("Abruzzo", "dell'Abruzzo", "",88),
    toscana("Abruzzo", "dell'Abruzzo", "",77),
    trentino("Abruzzo", "dell'Abruzzo", "",77),
    umbria("Abruzzo", "dell'Abruzzo", "",77),
    aosta("Abruzzo", "dell'Abruzzo", "",88),
    veneto("Abruzzo", "dell'Abruzzo", "",55),
    ;

    static String COMUNI = "Comuni ";

    String nome;

    String paginaWikiShort;

    String titoli;

    int comuni;


    EARegione(String nome, String paginaWikiShort, String titoli, int comuni) {
        this.nome = nome;
        this.paginaWikiShort = paginaWikiShort;
        this.titoli = titoli;
        this.comuni = comuni;
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


    public int getComuni() {
        return comuni;
    }// end of method
}// end of enum class
