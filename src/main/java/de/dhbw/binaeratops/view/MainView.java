package de.dhbw.binaeratops.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.impl.game.GameService;
import de.dhbw.binaeratops.view.mainviewtabs.AboutUsView;
import de.dhbw.binaeratops.view.mainviewtabs.LobbyView;
import de.dhbw.binaeratops.view.mainviewtabs.MyDungeonsView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Oberfl??che f??r die Komponente "Hauptmen??".
 * <p>
 * Diese Ansicht stellt alle View-Komponenten f??r das Navigieren durch den MUD-Server bereit.
 * <p>
 * Das Hauptmen?? unterteilt sich in die Tab-Oberfl??chen:
 * <ul>
 *     <li>??ber uns</li>
 *     <li>Mitteilungen</li>
 *     <li>Lobby</li>
 *     <li>Eigene Dungeons</li>
 * </ul>
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug
 */
@Push
@JsModule("./styles/shared-styles.js")
@CssImport("./views/main/main-view.css")
public class MainView extends AppLayout {

    private final Tabs menu;
    private H1 viewTitle;
    private Select<Locale> languageSelect;
    private HorizontalLayout menuLayout;
    private ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
    private TranslationProvider transProv = new TranslationProvider();

    private GameService gameService;

    public MainView(@Autowired GameService AGameService) {
        this.gameService = AGameService;
        setPrimarySection(Section.DRAWER);
        createTopRightMenu();
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }

    private void createTopRightMenu() {
        menuLayout = new HorizontalLayout();
        Avatar avatar = new Avatar();

        // --- SPRACHE HINZUF??GEN IN MEN?? --- NICHT ENTFERNEN!!! ---

        languageSelect = new Select<>();
        languageSelect.setLabel(res.getString("view.main.select"));
        languageSelect.setPlaceholder(res.getString("view.main.select"));
        List<Locale> locales = transProv.getProvidedLocales();

        languageSelect.setItemLabelGenerator(Locale::getDisplayLanguage);
        languageSelect.setItems(locales);
        languageSelect.setValue(VaadinSession.getCurrent().getLocale());

        languageSelect.addValueChangeListener(e -> {
            if (VaadinSession.getCurrent().getLocale() == Locale.US) {
                VaadinSession.getCurrent().setLocale(Locale.GERMANY);
            } else if (VaadinSession.getCurrent().getLocale() == Locale.GERMANY) {
                VaadinSession.getCurrent().setLocale(Locale.US);
            }
            res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());
            UI.getCurrent().getPage().reload();
        });
        Icon icon = new Icon(VaadinIcon.GLOBE);
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        menuBar.setOpenOnHover(true);
        MenuItem menuItem = menuBar.addItem(icon);
        SubMenu subMenu = menuItem.getSubMenu();
        subMenu.addItem(languageSelect);


        menuLayout.addClassName("menuRight");

        menuLayout.add(menuBar, avatar);
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
        layout.add(new Button("logout", e -> UI.getCurrent().navigate("logout")));
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
        logoLayout.add(new Image("images/Binaeratops.png", "Bin??ratops logo"));
        logoLayout.add(new H1("Bin??ratops"));
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

    private Component[] createMenuItems() {
        return new Tab[]{
                createTab(res.getString("view.main.tab.about.us"), AboutUsView.class),
                createTab(res.getString("view.main.tab.lobby"), LobbyView.class),
                createTab(res.getString("view.main.tab.my.dungeons"), MyDungeonsView.class)
        };
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

        var user = VaadinSession.getCurrent().getAttribute(User.class);
        var myUser = gameService.getUser(user.getUserId());
        var dungeon = myUser.getCurrentDungeon();
        if(dungeon != null){
            var avatars = user.getAvatars();
            avatars.forEach(avatar -> {
                gameService.removeActivePlayer(dungeon.getDungeonId(), user.getUserId(), avatar.getAvatarId(), false);
            });
            VaadinSession.getCurrent().setAttribute("Noti", true);
            UI.getCurrent().navigate("logout");
        }

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
