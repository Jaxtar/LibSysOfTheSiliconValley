package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.PersonController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Person;
import com.PiratesOfTheSiliconValley.LibSys.editor.StaffPersonForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;


@PageTitle("Staffperson")

public class StaffPersonView extends VerticalLayout {

    private PersonController personController;
    private StaffPersonForm staffPersonForm;

    private Grid<Person> grid = new Grid<>(Person.class);
    private TextField filterText = new TextField();

    public StaffPersonView(PersonController personController) {
        this.personController = personController;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        staffPersonForm = new StaffPersonForm();
        staffPersonForm.addListener(StaffPersonForm.SaveEvent.class, this::savePerson);
        staffPersonForm.addListener(StaffPersonForm.DeleteEvent.class, this::deletePerson);
        staffPersonForm.addListener(StaffPersonForm.CloseEvent.class, e -> closeEditor());


        Div content = new Div(grid, staffPersonForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        addClassName("user-grid");
        grid.setSizeFull();
        grid.setColumns("userID", "personal_id_number",
                "firstname", "lastname", "streetadress",
                "postalcode", "city", "email", "phone");
        grid.getColumnByKey("personal_id_number").setHeader("Personal ID Number");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editPerson(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by lastname...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addPersonButton = new Button("Add Person");
        addPersonButton.addClickListener(click -> addPerson());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addPersonButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void addPerson() {
        grid.asSingleSelect().clear();
        editPerson(new Person());
    }

    private void closeEditor() {
        staffPersonForm.setUser(null);
        staffPersonForm.setVisible(false);
        removeClassName("editing");
    }

    public void editPerson(Person person) {
        if (person == null) {
            closeEditor();
        } else {
            staffPersonForm.setUser(person);
            staffPersonForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void savePerson(StaffPersonForm.SaveEvent event) {
        personController.save(event.getPerson());
        updateList();
        closeEditor();
    }

    private void deletePerson(StaffPersonForm.DeleteEvent event) {
        personController.delete(event.getPerson());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(personController.findAll(filterText.getValue()));
    }
}
