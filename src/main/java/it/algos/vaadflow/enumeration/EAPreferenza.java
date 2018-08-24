package it.algos.vaadflow.enumeration;


import it.algos.vaadflow.application.FlowCost;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mer, 30-mag-2018
 * Time: 07:27
 */
public enum EAPreferenza {

    usaDebug(FlowCost.USA_DEBUG, "Flag generale di debug (ce ne possono essere di specifici, validi solo se questo è vero)", EAPrefType.bool, false),
    usaLogDebug(FlowCost.USA_LOG_DEBUG, "Uso del log di registrazione per il livello debug. Di default false", EAPrefType.bool, false),
    usaCompany(FlowCost.USA_COMPANY, "L'applicazione è multiCompany ", EAPrefType.bool, false),
    showCompany(FlowCost.SHOW_COMPANY, show(), EAPrefType.bool, false),
    showPreferenza(FlowCost.SHOW_PREFERENZA, show(), EAPrefType.bool, false),
    showWizard(FlowCost.SHOW_WIZARD, show(), EAPrefType.bool, false),
    showDeveloper(FlowCost.SHOW_DEVELOPER, show(), EAPrefType.bool, false),
    showAddress(FlowCost.SHOW_ADDRESS, show(), EAPrefType.bool, false),
    showPerson(FlowCost.SHOW_PERSON, show(), EAPrefType.bool, false),
    showRole(FlowCost.SHOW_ROLE, show(), EAPrefType.bool, false),
    showUser(FlowCost.SHOW_USER, show(), EAPrefType.bool, false),
    showVersione(FlowCost.SHOW_VERSION, show(), EAPrefType.bool, false),;


    private String code;
    private String desc;
    private EAPrefType type;
    private Object value;


    EAPreferenza(String code, String desc, EAPrefType type, Object value) {
        this.setCode(code);
        this.setDesc(desc);
        this.setType(type);
        this.setValue(value);
    }// fine del costruttore

    public static String show() {
        return "Flag per costruire la UI con il modulo visibile nel menu";
    }// end of method

    public String getCode() {
        return code;
    }// end of method

    public void setCode(String code) {
        this.code = code;
    }// end of method

    public String getDesc() {
        return desc;
    }// end of method

    public void setDesc(String desc) {
        this.desc = desc;
    }// end of method

    public EAPrefType getType() {
        return type;
    }// end of method

    public void setType(EAPrefType type) {
        this.type = type;
    }// end of method

    public Object getValue() {
        return value;
    }// end of method

    public void setValue(Object value) {
        this.value = value;
    }// end of method

} // end of enumeration
