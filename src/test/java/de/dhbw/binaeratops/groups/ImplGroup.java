package de.dhbw.binaeratops.groups;

import de.dhbw.binaeratops.service.impl.configurator.ConfigurationServiceTest;
import de.dhbw.binaeratops.service.impl.configurator.DungeonServiceTest;
import de.dhbw.binaeratops.service.impl.player.map.MapServiceTest;
import de.dhbw.binaeratops.service.impl.registration.AuthServiceTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Lars Rösel
 * Date: 13.05.2021
 * Time: 14:58
 */

@RunWith(Categories.class)
@Categories.IncludeCategory(ImplGroup.class)
@Suite.SuiteClasses({
        ConfigurationServiceTest.class,
        MapServiceTest.class,
        AuthServiceTest.class,
        DungeonServiceTest.class,
//        ParserServiceTest.class
})
public interface ImplGroup {
}
