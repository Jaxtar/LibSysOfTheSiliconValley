package com.PiratesOfTheSiliconValley.LibSys.views;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
@PageTitle("Main View")
@CssImport("./views/mainview/main-view-view.css")
@Tag("main-view-view")
@JsModule("./views/mainview/main-view-view.ts")
public class MainViewView extends LitTemplate {

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/

    public MainViewView() {
    }
}
