package de.dhbw.binaeratops.view.configurator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.service.impl.player.map.MapService;
import de.dhbw.binaeratops.view.configurator.tabs.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@PageTitle("Konfigurator")
@CssImport("./views/main/main-view.css")
public class ConfiguratorMainView extends Div {

    private Tabs configuratorTabs = new Tabs();

    private VerticalLayout dungeonsConfigurator ;
    private VerticalLayout characterConfigurator;
    private VerticalLayout npcConfigurator;
    private VerticalLayout itemsConfigurator;
    private VerticalLayout roomConfigurator;


    public ConfiguratorMainView(@Autowired ConfiguratorServiceI configuratorServiceI, @Autowired MapService mapService) {

        //Dialog dungeonnameDialog = new NewDungeonDialog(configuratorServiceI);
        roomConfigurator = new RoomConfigurationTab(configuratorServiceI, mapService);
        characterConfigurator = new CharacterConfigurationTab(configuratorServiceI);
        itemsConfigurator = new ItemConfigurationTab(configuratorServiceI);
        dungeonsConfigurator = new DungeonConfigurationTab(configuratorServiceI);
        npcConfigurator = new NPCConfigurationTab(configuratorServiceI);
        createMenuItems();

        //dungeonnameDialog.open();
    }

    public void createMenuItems() {
        Tab dungeonTab = new Tab("Allgemein");
        Tab characterTab = new Tab("Charaktereigenschaften festlegen");
        Tab npcTab = new Tab("NPCs erstellen");
        Tab itemsTab = new Tab("Gegenstände erstellen");
        Tab roomTab = new Tab("Räume konfigurieren");

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dungeonTab, dungeonsConfigurator);
        tabsToPages.put(characterTab, characterConfigurator);
        tabsToPages.put(npcTab, npcConfigurator);
        tabsToPages.put(itemsTab, itemsConfigurator);
        tabsToPages.put(roomTab, roomConfigurator);

        configuratorTabs = new Tabs(dungeonTab, characterTab, npcTab, itemsTab, roomTab);
        Div pages = new Div( dungeonsConfigurator, characterConfigurator, npcConfigurator, itemsConfigurator, roomConfigurator);

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

}



