package com.PiratesOfTheSiliconValley.LibSys.views.publicpages;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.security.AuthService;
import com.PiratesOfTheSiliconValley.LibSys.views.login.LoginView;
import com.PiratesOfTheSiliconValley.LibSys.views.logout.LogoutView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffBookView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.UsersView;
import com.PiratesOfTheSiliconValley.LibSys.views.user.UserAccount;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "LIBSYS", shortName = "LIBSYS", enableInstallPrompt = false)
@JsModule("./styles/shared-styles.js")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./views/main/navbar.css")
public class Navbar extends AppLayout {

    private Tabs menu;
    private H1 viewTitle;
    private AuthService authService;


    public Navbar() {
        menu = new Tabs();
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);
        layout.add(new Avatar());
        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", "LIBSYS logo"));
        logoLayout.add(new H1("LIBSYS"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private void createMenu() {
        menu.setOrientation(Tabs.Orientation.VERTICAL);
        menu.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        menu.setId("tabs");
        menu.add(createMenuItems());
    }

    private Component[] createMenuItems() {
        User user = VaadinSession.getCurrent().getAttribute(User.class);

        List<Tab> tabs = new ArrayList<>();
        
        tabs.add(createTab("Huvudsida", MainPage.class));
        
        if (user != null && user.getRole().equals(Role.USER)){
            tabs.add(createTab("Boklista", BookCatalogueView.class));
            tabs.add(createTab("Seminarium", SeminarView.class));
            tabs.add(createTab("Öppettider", OpenHoursView.class));
            tabs.add(createTab("Om oss", AboutUsView.class));
            tabs.add(createTab("Account", UserAccount.class));
            tabs.add(createTab("Logout", LogoutView.class));
        } else if (user != null && user.getRole().equals(Role.ADMIN)) {
            tabs.add(createTab("Books", StaffBookView.class));
            tabs.add(createTab("Seminarium", SeminarView.class));
            tabs.add(createTab( "User List", UsersView.class));
            tabs.add(createTab( "Logout", LogoutView.class));
        } else  {
            tabs.add(createTab("Boklista", BookCatalogueView.class));
            tabs.add(createTab("Seminarium", SeminarView.class));
            tabs.add(createTab("Öppettider", OpenHoursView.class));
            tabs.add(createTab("Om oss", AboutUsView.class));
            tabs.add(createTab("Login", LoginView.class));
        }

        return tabs.toArray(Component[]::new);
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
