package de.dhbw.binaeratops.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.streammessages.RequestAnswer;
import de.dhbw.binaeratops.model.streammessages.UserRequest;
import de.dhbw.binaeratops.view.mainviewtabs.AboutUsView;
import de.dhbw.binaeratops.view.mainviewtabs.LobbyView;
import de.dhbw.binaeratops.view.mainviewtabs.MyDungeonsView;
import de.dhbw.binaeratops.view.mainviewtabs.NotificationView;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The main view is a top-level placeholder for other views.
 */
@Push
@JsModule("./styles/shared-styles.js")
@CssImport("./views/main/main-view.css")
public class MainView extends AppLayout {

    private final Flux<UserRequest> permissions;
    private final Flux<RequestAnswer> requestAnswers;
    private final Tabs menu;
    private H1 viewTitle;
    //private Select<Locale> languageSelect;
    private HorizontalLayout menuLayout;
    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private TranslationProvider transProv = new TranslationProvider();

    public MainView(Flux<UserRequest> permissions, Flux<RequestAnswer> requestAnswers) {
        this.permissions = permissions;
        this.requestAnswers = requestAnswers;
        setPrimarySection(Section.DRAWER);
        createTopRightMenu();
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private void createTopRightMenu() {
        menuLayout = new HorizontalLayout();
        Avatar avatar = new Avatar();

        // --- SPRACHE HINZUFÜGEN IN MENÜ --- NICHT ENTFERNEN!!! ---

//        languageSelect = new Select<>();
//        languageSelect.setPlaceholder(res.getString("view.main.select"));
//        List<Locale> locales = transProv.getProvidedLocales();
//
//        languageSelect.setItemLabelGenerator(Locale::getDisplayLanguage);
//        languageSelect.setItems(locales);
//        languageSelect.setValue(VaadinSession.getCurrent().getLocale());
//
//        languageSelect.addValueChangeListener(e -> {
//            if (VaadinSession.getCurrent().getLocale() == Locale.US) {
//                VaadinSession.getCurrent().setLocale(Locale.GERMANY);
//            } else if (VaadinSession.getCurrent().getLocale() == Locale.GERMANY) {
//                VaadinSession.getCurrent().setLocale(Locale.US);
//            }
//            res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
//            UI.getCurrent().getPage().reload();
//        });
        menuLayout.addClassName("menuRight");
        menuLayout.add(/*languageSelect,*/ avatar);

        /* OLD - Menubar Example */
//        menuBar = new MenuBar();
//        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
//        menuBar.setOpenOnHover(true);
//        menuBar.setClassName("menuRight");
//        MenuItem menuItem = menuBar.addItem(avatar);
//        SubMenu subMenu = menuItem.getSubMenu();
//        subMenu.addItem("Profile");
//        subMenu.addItem(languageSelect);
//        subMenu.addItem("Sign out");
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);
        layout.add(menuLayout);
        //layout.add(avatar);
        layout.add(new Button("logout",e -> UI.getCurrent().navigate("logout")));
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
        logoLayout.add(new Image("images/Binaeratops.png", "Binäratops logo"));
        logoLayout.add(new H1("Binäratops"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    Tab messageTab;
    private Component[] createMenuItems() {

        messageTab = createTab("Mitteilungen", NotificationView.class);
        requestAnswers.subscribe(message -> getUI().ifPresent(ui -> ui.access(() -> {
            //Prüfung ob der Spieler die Nachricht erhalten darf.
            if (!(message.getUser().getUserId() == VaadinSession.getCurrent().getAttribute(User.class).getUserId())) {
                menu.replace(messageTab, createTab("Mitteilungen", NotificationView.class, new Icon(VaadinIcon.ALARM)));
            };

        })));
        return new Tab[]{
                createTab("Über uns", AboutUsView.class),
                messageTab,
                createTab("Lobby", LobbyView.class),
                createTab("Eigene Dungeons", MyDungeonsView.class)
        };


    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }
    private static Tab createTab(String text, Class<? extends Component> navigationTarget, Icon AIcon) {
        final Tab tab = new Tab();
        tab.add(new HorizontalLayout(new RouterLink(text, navigationTarget), AIcon));
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
