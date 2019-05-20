package it.algos.vaadflow.ui.list;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import it.algos.vaadflow.backend.entity.AEntity;
import it.algos.vaadflow.presenter.IAPresenter;
import it.algos.vaadflow.ui.dialog.IADialog;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.List;

/**
 * Project vaadflow
 * Created by Algos
 * User: gac
 * Date: Mon, 20-May-2019
 * Time: 08:24
 * <p>
 * Sottoclasse di servizio per gestire la Grid di AViewList in una classe 'dedicata' <br>
 * Alleggerisce la 'lettura' della classe principale <br>
 * Le property sono regolarmente disponibili in AViewList ed in tutte le sue sottoclassi <br>
 * Costruisce e regola la Grid <br>
 * Nelle sottoclassi concrete la Grid può essere modificata. <br>
 */
@Slf4j
public abstract class AGridViewList extends ALayoutViewList {

    /**
     * Costruttore <br>
     *
     * @param presenter per gestire la business logic del package
     * @param dialog    per visualizzare i fields
     */
    public AGridViewList(IAPresenter presenter, IADialog dialog) {
        super(presenter, dialog);
    }// end of Spring constructor


    /**
     * Crea il corpo centrale della view
     * Componente grafico obbligatorio
     * Alcune regolazioni vengono (eventualmente) lette da mongo e (eventualmente) sovrascritte nella sottoclasse
     * Costruisce la Grid con le colonne. Gli items vengono caricati in updateView()
     * Facoltativo (presente di default) il bottone Edit (flag da mongo eventualmente sovrascritto)
     */
    protected void creaGrid() {
        FlexLayout layout = new FlexLayout();
//        layout.setHeight("30em");

        //--Costruisce una lista di nomi delle properties della Grid nell'ordine:
        //--1) Cerca nell'annotation @AIList della Entity e usa quella lista (con o senza ID)
        //--2) Utilizza tutte le properties della Entity (properties della classe e superclasse)
        //--3) Sovrascrive la lista nella sottoclasse specifica di xxxService
        List<String> gridPropertyNamesList = service != null ? service.getGridPropertyNamesList(context) : null;
        if (entityClazz != null && AEntity.class.isAssignableFrom(entityClazz)) {
            try { // prova ad eseguire il codice
                //--Costruisce la Grid SENZA creare automaticamente le colonne
                //--Si possono così inserire colonne manuali prima e dopo di quelle automatiche
                grid = new Grid(entityClazz, false);
            } catch (Exception unErrore) { // intercetta l'errore
                log.error(unErrore.toString());
                return;
            }// fine del blocco try-catch
        } else {
            grid = new Grid();
        }// end of if/else cycle

//        //--@todo solo per la versione 10.0.5
//        //--@todo dalla versione 12.0.0, si può levare ed aggiungere 'false' come secondo parametro a new Grid(...,false)
//        for (Grid.Column column : grid.getColumns()) {
//            grid.removeColumn(column);
//        }// end of for cycle

        //--Apre il dialog di detail
        if (isBottoneEditBefore) {
            this.addDetailDialog();
        }// end of if cycle

        //--Eventuali colonne calcolate aggiunte PRIMA di quelle automatiche
        this.addSpecificColumnsBefore();

        //--Eventuale modifica dell'ordine di presentazione delle colonne automatiche
        gridPropertyNamesList = this.reorderingColumns(gridPropertyNamesList);

        //--Colonne normali aggiunte in automatico
        if (gridPropertyNamesList != null) {
            for (String propertyName : gridPropertyNamesList) {
                column.create(grid, entityClazz, propertyName);
            }// end of for cycle
        }// end of if cycle

        //--Eventuali colonne calcolate aggiunte DOPO quelle automatiche
        this.addSpecificColumnsAfter();

        //--Apre il dialog di detail
        if (!isBottoneEditBefore) {
            this.addDetailDialog();
        }// end of if cycle

        //--Regolazioni finali sulla grid e sulle colonne
        this.fixLayout();

        layout.add(grid);
        this.add(layout);
        layout.setFlexGrow(1, grid);
        this.setFlexGrow(1, layout);

        grid.addSelectionListener(new SelectionListener<Grid<AEntity>, AEntity>() {

            @Override
            public void selectionChange(SelectionEvent<Grid<AEntity>, AEntity> selectionEvent) {
                boolean enabled = selectionEvent != null && selectionEvent.getAllSelectedItems().size() > 0;
                sincroBottoniMenu(enabled);
            }// end of inner method
        });//end of lambda expressions and anonymous inner class

        fixGridHeader();
    }// end of method

}// end of class
