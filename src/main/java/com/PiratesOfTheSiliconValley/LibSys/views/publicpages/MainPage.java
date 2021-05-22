package com.PiratesOfTheSiliconValley.LibSys.views.publicpages;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = Navbar.class)
@PageTitle("Mainpage")
@CssImport("./views/mainview/main-page.css")
@Tag("main-page")
@JsModule("./views/mainview/main-page.ts")
public class MainPage extends LitTemplate {

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/

    public MainPage() {
    }
}
