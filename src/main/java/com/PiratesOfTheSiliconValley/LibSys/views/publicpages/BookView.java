package com.PiratesOfTheSiliconValley.LibSys.views.publicpages;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/book", layout = Navbar.class)
@PageTitle("Bok")
@CssImport("./views/mainview/main-page.css")
@Tag("item-view")
@JsModule("./views/mainview/main-page.ts")
public class BookView extends VerticalLayout implements HasUrlParameter<String>{

    public void start(String bookID){
        H1 test = new H1("Bok: " + bookID);
        add(test);
    }

    @Override
    public void setParameter(BeforeEvent event, String param){
        start(param);
    }
}
