package com.PiratesOfTheSiliconValley.LibSys.views;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/staff/books", layout = StaffLayout.class)
@PageTitle("Biblioteket Jisho")
@CssImport("./views/mainview/main-page.css")
@Tag("main-page")
@JsModule("./views/mainview/main-page.ts")
public class StaffBookView  extends LitTemplate {
}
