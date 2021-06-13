package de.dhbw.binaeratops.view.configurator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.view.configurator.tabs.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Oberfläche für die Komponente "Konfigurator".
 * <p>
 * Diese Ansicht stellt alle View-Komponenten für die Konfiguration des Dungeons bereit.
 * <p>
 * Der Konfigurator unterteilt sich in die Tab-Oberflächen:
 *     <ul>
 *         <li>Dungeoneigenschaften</li>
 *         <li>Charaktereigenschaften</li>
 *         <li>Gegenstände</li>
 *         <li>NPCs</li>
 *         <li>Räume</li>
 *     </ul>
 * <p>
 * Dafür sendet sie die Benutzerangaben direkt an den entsprechenden Service.
 *
 * @author Pedro Treuer, Timon Gartung, Nicolas Haug
 */
@CssImport("./views/main/main-view.css")
public class ConfiguratorMainView extends Div implements HasDynamicTitle, HasUrlParameter<Long> {

    private final ResourceBundle res = ResourceBundle.getBundle("language", VaadinSession.getCurrent().getLocale());

    private Tabs configuratorTabs = new Tabs();

    private VerticalLayout dungeonsConfigurator;
    private VerticalLayout characterConfigurator;
    private VerticalLayout npcConfigurator;
    private VerticalLayout itemsConfigurator;
    private VerticalLayout roomConfigurator;

    private ConfiguratorServiceI configuratorServiceI;
    private MapServiceI mapServiceI;

    private Long dungeonId;
    public ConfiguratorMainView(@Autowired ConfiguratorServiceI AConfiguratorServiceI, @Autowired MapServiceI AMapServiceI) {
        configuratorServiceI = AConfiguratorServiceI;
        mapServiceI = AMapServiceI;

        setSizeFull();
    }

    public void createMenuItems() {
        //wir setzen dem Service den Dungeon mit der übergebenen ID aus der URL

        configuratorServiceI.setDungeon(dungeonId);
        if (!configuratorServiceI.getDungeon().getUser().getUserId()
                .equals(VaadinSession.getCurrent().getAttribute(User.class).getUserId())) {
            UI.getCurrent().navigate("logout");
            Span label = new Span(res.getString("view.configurator.main.invalid.permission"));
            showErrorNotification(label);
        }

        //Dialog dungeonnameDialog = new NewDungeonDialog(AConfiguratorServiceI);
        dungeonsConfigurator = new DungeonConfigurationTab(configuratorServiceI);
        characterConfigurator = new CharacterConfigurationTab(configuratorServiceI);
        itemsConfigurator = new ItemConfigurationTab(configuratorServiceI);
        npcConfigurator = new NPCConfigurationTab(configuratorServiceI);
        roomConfigurator = new RoomConfigurationTab(configuratorServiceI, mapServiceI);

        //dungeonnameDialog.open();

        Tab dungeonTab = new Tab(res.getString("view.configurator.main.tab.general"));
        Tab characterTab = new Tab(res.getString("view.configurator.main.tab.character.traits"));
        Tab itemsTab = new Tab(res.getString("view.configurator.main.tab.create.items"));
        Tab npcTab = new Tab(res.getString("view.configurator.main.tab.create.npcs"));
        Tab roomTab = new Tab(res.getString("view.configurator.main.tab.room"));

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dungeonTab, dungeonsConfigurator);
        tabsToPages.put(characterTab, characterConfigurator);
        tabsToPages.put(itemsTab, itemsConfigurator);
        tabsToPages.put(npcTab, npcConfigurator);
        tabsToPages.put(roomTab, roomConfigurator);

        configuratorTabs = new Tabs(dungeonTab, characterTab, itemsTab, npcTab, roomTab);
        Div pages = new Div(dungeonsConfigurator, characterConfigurator, itemsConfigurator, npcConfigurator, roomConfigurator);

        add(configuratorTabs, pages);

        tabsToPages.values().forEach(page -> page.setVisible(false));
        configuratorTabs.setSelectedTab(dungeonTab);
        tabsToPages.get(dungeonTab).setVisible(true);

        configuratorTabs.addSelectedChangeListener(e -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selected = tabsToPages.get(configuratorTabs.getSelectedTab());
            selected.setVisible(true);
        });


    }

    private void showErrorNotification(Span ALabel) {
        Notification notification = new Notification();
        Button closeButton = new Button("", e -> {
            notification.close();
        });
        closeButton.setIcon(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        notification.add(ALabel, closeButton);
        ALabel.getStyle().set("margin-right", "0.3rem");
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setDuration(10000);
        notification.setPosition(Notification.Position.TOP_END);
        notification.open();
    }

    @Override
    public void setParameter(BeforeEvent ABeforeEvent, Long ALong) {
        this.dungeonId = ALong;
        createMenuItems();
    }

    @Override
    public String getPageTitle() {
        return res.getString("view.configurator.main.pagetitle");
    }
}



