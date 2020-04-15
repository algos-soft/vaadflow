package it.algos.vaadflow.wiz;

import it.algos.vaadflow.wiz.enumeration.Chiave;

import java.util.LinkedHashMap;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: lun, 13-apr-2020
 * Time: 05:20
 */
public interface WizRecipient {

    public void gotInput(LinkedHashMap<Chiave, Object> mappaInput);

}// end of interface
