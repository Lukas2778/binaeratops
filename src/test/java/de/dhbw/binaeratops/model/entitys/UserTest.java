package de.dhbw.binaeratops.model.entitys;


import de.dhbw.binaeratops.groups.UserGroup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;

@Category({UserGroup.class})
public class UserTest {
    String password = "12345";
    String falsePassword = "54321";

    private User user;

    @Before
    public void before() {
        user = new User("Hans", "i19036@hb.dhbw-stuttgart.de", password, 345433, true);
    }

    @Test
    public void checkPasswordCorrectTest() {
        assertTrue(user.checkPassword(password));
    }

    @Test
    public void checkPasswordWrongTest() {
        assertFalse(user.checkPassword(falsePassword));
    }



    @Test
    public void testEquals() {
        User u = new User();
        u.setUserId(23L);
        u.setUsername("Pedro");

        User u2 = new User();
        u2.setUserId(23L);
        u2.setUsername("pedro");

        Assert.assertEquals(true, u.equals(u2));
    }
}
