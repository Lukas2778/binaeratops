package de.dhbw.binaeratops.model.service.impl.registration;

import com.vaadin.flow.server.VaadinSession;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.service.impl.registration.AuthException;
import de.dhbw.binaeratops.service.impl.registration.AuthService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class AuthServiceTest {
    AuthService as=new AuthService();
    User user;

    @Before
    public void setup(){
        user=new User("Hans", "hans@gmail.com","pass",1594,true);

    }

    //@TODO tests mocken für datenbank

//    @Test
//    void authenticateTest(){
//
//        try {
//            as.authenticate("Hans","pass");
//        } catch (AuthException e) {
//            e.printStackTrace();
//        }
//        assertEquals(VaadinSession.getCurrent().getAttribute(User.class),user);
//    }
//
//    @Test
//    void bestätigeTest(){
//
//
//    }

}
