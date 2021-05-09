package de.dhbw.binaeratops.view.configurator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.api.map.MapServiceI;
import de.dhbw.binaeratops.service.impl.map.MapService;
import de.dhbw.binaeratops.view.configurator.tabs.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@PageTitle("Konfigurator")
@CssImport("./views/main/main-view.css")
public class ConfiguratorMainView extends Div implements HasUrlParameter<Long> {

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
        configuratorServiceI=AConfiguratorServiceI;
        mapServiceI=AMapServiceI;

        setSizeFull();
    }

    public void createMenuItems() {
        //wir setzen dem Service den dungeon mit der übergebenen ID aus der URL

        configuratorServiceI.setDungeon(dungeonId);
        if(!configuratorServiceI.getDungeon().getUser().getUserId()
                .equals(VaadinSession.getCurrent().getAttribute(User.class).getUserId())){
            Notification.show("Du hast keine Berechtigung diesen Dungeon zu bearbeiten.\n nice try ;)");
            UI.getCurrent().navigate("logout");
        }

        //Dialog dungeonnameDialog = new NewDungeonDialog(AConfiguratorServiceI);
        dungeonsConfigurator = new DungeonConfigurationTab(configuratorServiceI);
        characterConfigurator = new CharacterConfigurationTab(configuratorServiceI);
        itemsConfigurator = new ItemConfigurationTab(configuratorServiceI);
        npcConfigurator = new NPCConfigurationTab(configuratorServiceI);
        roomConfigurator = new RoomConfigurationTab(configuratorServiceI, mapServiceI);

        //dungeonnameDialog.open();

        Tab dungeonTab = new Tab("Allgemein");
        Tab characterTab = new Tab("Charaktereigenschaften festlegen");
        Tab itemsTab = new Tab("Gegenstände erstellen");
        Tab npcTab = new Tab("NPCs erstellen");
        Tab roomTab = new Tab("Räume konfigurieren");

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dungeonTab, dungeonsConfigurator);
        tabsToPages.put(characterTab, characterConfigurator);
        tabsToPages.put(itemsTab, itemsConfigurator);
        tabsToPages.put(npcTab, npcConfigurator);
        tabsToPages.put(roomTab, roomConfigurator);

        configuratorTabs = new Tabs(dungeonTab, characterTab, itemsTab, npcTab, roomTab);
        Div pages = new Div( dungeonsConfigurator, characterConfigurator, itemsConfigurator, npcConfigurator, roomConfigurator);

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

    @Override
    public void setParameter(BeforeEvent ABeforeEvent, Long ALong) {
        this.dungeonId=ALong;
        createMenuItems();
    }


}



