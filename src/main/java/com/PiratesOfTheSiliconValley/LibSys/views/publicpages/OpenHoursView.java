package com.PiratesOfTheSiliconValley.LibSys.views.publicpages;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "/hours", layout = Navbar.class)
@PageTitle("Öppettider")
@CssImport("./views/mainview/main-page.css")
@Tag("item-view")
@JsModule("./views/mainview/main-page.ts")
public class OpenHoursView extends VerticalLayout {

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/
    public OpenHoursView() {

        TextArea area = new TextArea();
        area.setWidth("400px");

        area.setValue("""
               MÅNDAG     10:00 - 19:00
               TISDAG         10:00 - 19:00
               ONSDAG      10:00 - 19:00
               TORSDAG     10:00 - 19:00
               FREDAG        10:00 - 19:00
               LÖRDAG       stängt
               SÖNDAG      10:00 - 16:00""");
        add(area);
    }
}
