package de.dhbw.binaeratops.view.mainviewtabs.configurator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.dhbw.binaeratops.service.api.configuration.ConfiguratorServiceI;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.*;
import de.dhbw.binaeratops.view.mainviewtabs.configurator.konfiguratormainviewtabs.dialog.NewDungeonDialog;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@PageTitle("Konfigurator")
@CssImport("./views/main/main-view.css")
public class ConfiguratorMainView extends Div {

    private Tabs configuratorTabs = new Tabs();

    private VerticalLayout dungeonsConfigurator ;
    private VerticalLayout characterConfigurator;
    private VerticalLayout npcConfigurator = new VerticalLayout();
    private VerticalLayout itemsConfigurator = new VerticalLayout();
    private VerticalLayout roomConfigurator;


    public ConfiguratorMainView(@Autowired ConfiguratorServiceI configuratorServiceI) {
        super();

        Dialog dungeonnameDialog = new NewDungeonDialog(configuratorServiceI);

        roomConfigurator = new RoomConfigurator();
        characterConfigurator = new CharacterConfiguration(configuratorServiceI);
        itemsConfigurator = new ItemsConfigurator();
        dungeonsConfigurator = new DungeonConfiguration();
        npcConfigurator = new NPCConfigurator(configuratorServiceI);
        //configuratorTabs.add(createMenuItems());
        createMenuItems();

        dungeonnameDialog.open();
    }

    public void createMenuItems() {
        Tab dungeonTab = new Tab("Dungeon");
        Tab characterTab = new Tab("Charaktereigenschaften");
        Tab npcTab = new Tab("NPC");
        Tab itemsTab = new Tab("Gegenstände");
        Tab roomTab = new Tab("Raum");



        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(dungeonTab, dungeonsConfigurator);
        tabsToPages.put(characterTab, characterConfigurator);
        tabsToPages.put(npcTab, npcConfigurator);
        tabsToPages.put(itemsTab, itemsConfigurator);
        tabsToPages.put(roomTab, roomConfigurator);


        configuratorTabs = new Tabs(dungeonTab, characterTab, npcTab, itemsTab, roomTab);
        Div pages = new Div( dungeonsConfigurator, characterConfigurator, npcConfigurator, itemsConfigurator, roomConfigurator);

        tabsToPages.values().forEach(page -> page.setVisible(false));
        configuratorTabs.setSelectedTab(dungeonTab);
        roomTab.setVisible(true);

        configuratorTabs.addSelectedChangeListener(e -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selected = tabsToPages.get(configuratorTabs.getSelectedTab());
            selected.setVisible(true);
        });
        add(configuratorTabs, pages);
    }

}



