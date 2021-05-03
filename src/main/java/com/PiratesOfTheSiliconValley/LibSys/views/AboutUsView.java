package com.PiratesOfTheSiliconValley.LibSys.views;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "/about", layout = Navbar.class)
@PageTitle("Om oss")
@CssImport("./views/mainview/main-page.css")
@Tag("item-view")
@JsModule("./views/mainview/main-page.ts")
public class AboutUsView extends VerticalLayout {

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/

    public AboutUsView() {

        Accordion accordion = new Accordion();

        accordion.setWidth("1200px");

        VerticalLayout tab1 = new VerticalLayout();
        //tab1.addComponentAsFirst(new TextArea(" "));
        accordion.add("Med anledning av covid-19", tab1);
        VerticalLayout tab2 = new VerticalLayout();
        accordion.add("Om biblioteket", tab2);
        VerticalLayout tab3 = new VerticalLayout();
        accordion.add("Besök oss", tab3);
        VerticalLayout tab4 = new VerticalLayout();
        accordion.add("Kontakt", tab4);


        add(accordion);
    }
}
