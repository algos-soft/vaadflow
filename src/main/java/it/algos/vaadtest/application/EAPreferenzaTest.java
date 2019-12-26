package it.algos.vaadtest.application;


import it.algos.vaadflow.enumeration.EAPrefType;
import it.algos.vaadflow.modules.preferenza.IAPreferenza;
import it.algos.vaadflow.modules.role.EARole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static it.algos.vaadtest.application.TestCost.*;

/**
 * Project it.algos.vaadflow
 * Created by Algos
 * User: gac
 * Date: mer, 30-mag-2018
 * Time: 07:27
 */
public enum EAPreferenzaTest implements IAPreferenza {

    prefLocalData(PREF_LOCAL_DATA, "Test per una preferenza di tipo 'local data'", EAPrefType.localdate, LocalDate.now()),
    prefDataTime(PREF_LOCAL_DATA_TIME, "Test per una preferenza di tipo 'local data time'", EAPrefType.localdatetime, LocalDateTime.now()),
    prefTime(PREF_LOCAL_TIME, "Test per una preferenza di tipo 'local time'", EAPrefType.localtime, LocalTime.now()),
;


    private String code;

    private String desc;

    private EAPrefType type;

    private Object value;


    EAPreferenzaTest(String code, String desc, EAPrefType type, Object value) {
        this.setCode(code);
        this.setDesc(desc);
        this.setType(type);
        this.setValue(value);
    }// fine del costruttore


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


    public EARole getShow() {
        return null;
    }// end of method


    @Override
    public boolean isCompanySpecifica() {
        return false;
    }// end of method

} // end of enumeration
