package com.PiratesOfTheSiliconValley.LibSys.views;


import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/staff/main", layout = StaffLayout.class)
@PageTitle("Biblioteket Jisho")
@CssImport("./views/staffview/staffcommon.css")
public class StaffMainView extends VerticalLayout {


}
