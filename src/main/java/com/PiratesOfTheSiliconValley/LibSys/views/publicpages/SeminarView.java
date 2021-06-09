package com.PiratesOfTheSiliconValley.LibSys.views.publicpages;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/seminars", layout = Navbar.class)
@PageTitle("Seminarium")
@CssImport("./views/mainview/main-page.css")
@Tag("item-view")
@JsModule("./views/mainview/main-page.ts")
public class SeminarView extends LitTemplate {
    public SeminarView() {
    }
}
