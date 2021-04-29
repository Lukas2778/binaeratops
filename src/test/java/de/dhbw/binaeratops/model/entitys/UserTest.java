package de.dhbw.binaeratops.model.entitys;


import de.dhbw.binaeratops.groups.UserGroup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;

@Category({UserGroup.class})
public class UserTest {
    public User user;
    String password = "12345";
    String falsePassword = "54321";

    @Test
    public void checkPasswordTestCorrect() {
        assertTrue(user.checkPassword(password));
    }

    @Test
    public void checkPasswordWrong() {
        assertFalse(user.checkPassword(falsePassword));
    }

    @Before
    public void setup() {
        this.user = new User("Hans", "i19036@hb.dhbw-stuttgart.de", "12345", 345433, true);
    }

    @Test
    public void testEquals() {
        User u = new User();
        u.setUserId(23L);
        u.setName("Pedro");

        User u2 = new User();
        u2.setUserId(23L);
        u2.setName("pedro");

        Assert.assertEquals(true, u.equals(u2));
    }
}
