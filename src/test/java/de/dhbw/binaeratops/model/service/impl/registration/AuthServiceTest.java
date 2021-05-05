package de.dhbw.binaeratops.model.service.impl.registration;

import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import de.dhbw.binaeratops.service.exceptions.AuthException;
import de.dhbw.binaeratops.service.impl.registration.AuthService;
import de.dhbw.binaeratops.service.exceptions.NotVerifiedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AuthServiceTest extends Logger {
    @Autowired
    UserRepositoryI userRepo;
    private AuthService as = new AuthService();

    String password = "12345";
    private User user;
    private User confUser;

    @Before
    public void before() {
        user = new User("Hans", "i19036@hb.dhbw-stuttgart.de", password, 345433, true);
        userRepo.save(user);
        confUser = new User("Hans2", "i19036@hb.dhbw-stuttgart.de", password, 345433, false);
        userRepo.save(confUser);
    }

    //@TODO tests mocken für datenbank

    @Test
    public void authenticateTest(){
        try {
            as.authenticate("Hans","pass");
        } catch (AuthException e) {
            e.printStackTrace();
        } catch (NotVerifiedException e){
            e.printStackTrace();
        }
        assertEquals(VaadinSession.getCurrent().getAttribute(User.class),user);
    }

    @Test
    public void confirmCorrectTest(){
        assertEquals(as.confirm(confUser.getName(), confUser.getCode()),true);
    }

    @Test
    public void confirmWrongTest(){
        assertEquals(!as.confirm(confUser.getName(), 32234),true);
    }

}
