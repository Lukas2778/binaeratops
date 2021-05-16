package de.dhbw.binaeratops.groups;

import de.dhbw.binaeratops.model.entitys.UserTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(UserGroup.class)
@Suite.SuiteClasses({UserTest.class})
public interface UserGroup {
}
