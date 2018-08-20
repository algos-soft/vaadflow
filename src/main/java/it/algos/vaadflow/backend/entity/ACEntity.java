package it.algos.vaadflow.backend.entity;

import it.algos.vaadflow.annotation.AIColumn;
import it.algos.vaadflow.annotation.AIField;
import it.algos.vaadflow.enumeration.EAFieldAccessibility;
import it.algos.vaadflow.enumeration.EAFieldType;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: ven, 22-dic-2017
 * Time: 12:14
 */
@Getter
public abstract class ACEntity extends AEntity {


//    /**
//     * Riferimento alla company2 (per le sottoclassi che usano questa classe)
//     * - Nullo se il flag AlgosApp.USE_MULTI_COMPANY=false
//     * - Facoltativo od obbligatorio a seconda della sottoclasse, se il flag AlgosApp.USE_MULTI_COMPANY=true
//     * riferimento dinamico CON @DBRef
//     */
//    @DBRef
//    @AIField(type = EAFieldType.combo, clazz = CompanyService.class, dev = EAFieldAccessibility.newOnly, admin = EAFieldAccessibility.showOnly)
//    @AIColumn(name = "Company", width = 115)
//    public Company company;


}// end of class
