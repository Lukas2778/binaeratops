package de.dhbw.binaeratops.groups;

import de.dhbw.binaeratops.model.repository.*;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Testsuite für die Repository-Tests.
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(RepositoryGroup.class)
@Suite.SuiteClasses({AvatarRepositoryTest.class,
        DungeonRepositoryTest.class,
        ItemRepositoryTest.class,
        NpcRepositoryTest.class,
        RaceRepositoryTest.class,
        RoleRepositoryTest.class,
        RoomRepositoryTest.class,
        UserRepositoryTest.class})
public interface RepositoryGroup {
}
