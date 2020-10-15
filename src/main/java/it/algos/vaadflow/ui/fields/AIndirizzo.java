package it.algos.vaadflow.ui.fields;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadflow.modules.address.Address;
import it.algos.vaadflow.modules.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: gio, 03-ott-2019
 * Time: 16:28
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AIndirizzo extends CustomField<Address> {

    private final ATextField indirizzo = new ATextField();

    private final ATextField localita = new ATextField();

    private final ATextField cap = new ATextField();

    @Autowired
    private AddressService service;

    private String keyId;

    //    private Address currentItem;


    public AIndirizzo() {
        this("");
    }// end of constructor


    public AIndirizzo(String keyId) {
        this.keyId = keyId;

        indirizzo.setLabel("Indirizzo");
        indirizzo.getStyle().set("width", "12em");
        indirizzo.setPlaceholder("indirizzo...");

        localita.setLabel("Località");
        localita.getStyle().set("width", "14em");
        localita.setPlaceholder("comune...");

        cap.setLabel("Cap");
        cap.setPattern("[0-9]*");
        cap.setPreventInvalidInput(true);
        cap.setMaxLength(5);
        cap.getStyle().set("width", "5em");
        cap.setPlaceholder("cap...");
    }


    @PostConstruct
    public void inizia() {
        //        currentItem = service.findByKeyUnica(keyId);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(indirizzo, localita, cap);
        add(horizontalLayout);

        //        if (currentItem != null) {
        //            indirizzo.setValue(currentItem.indirizzo);
        //            localita.setValue(currentItem.localita);
        //            cap.setValue(currentItem.cap);
        //        }// end of if cycle
    }// end of constructor


    @Override
    protected Address generateModelValue() {
        Address address = Address.builderAddress()

                .indirizzo(indirizzo.getValue())

                .localita(localita.getValue())

                .cap(cap.getValue())

                .build();

        return address;
    }// end of method


    @Override
    protected void setPresentationValue(Address currentItem) {
        //        currentItem = service.findByKeyUnica(keyId);

        if (currentItem != null) {
            indirizzo.setValue(currentItem.indirizzo);
            localita.setValue(currentItem.localita);
            cap.setValue(currentItem.cap);
        }// end of if cycle

        //        datePicker.setValue(newPresentationValue
        //                != null ?
        //                newPresentationValue.toLocalDate() :
        //                null);
        //        timePicker.setValue(newPresentationValue
        //                != null ?
        //                newPresentationValue.toLocalTime() :
        //                null);
    }// end of method

    //    @Override
    //    protected String generateModelValue() {
    //        return null;
    //    }// end of method
    //
    //
    //    @Override
    //    protected void setPresentationValue(String idKey) {
    //
    //    }// end of method

}// end of class
