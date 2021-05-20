package com.PiratesOfTheSiliconValley.LibSys.views.user;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Useraccount")
@CssImport("./views/mainview/main-page.css")
@Tag("item-view")
@JsModule("./views/mainview/main-page.ts")
public class UserAccount extends LitTemplate {

    // This is the Java companion file of a design
    // You can find the design file inside /frontend/views/

    public UserAccount() {
    }
}