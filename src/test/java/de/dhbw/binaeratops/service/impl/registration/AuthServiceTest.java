package de.dhbw.binaeratops.service.impl.registration;

import de.dhbw.binaeratops.groups.Logger;
import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.repository.UserRepositoryI;
import org.aspectj.apache.bcel.classfile.Code;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class AuthServiceTest extends Logger {
    User myHans;

    @Mock
    UserRepositoryI myUserRepository;

    AuthService as = new AuthService();

    String password = "12345";

    @Before
    public void before() {
        myHans=new User("Hans", "i19036@hb.dhbw-stuttgart.de", password, 12345, false);
        as.userRepository=myUserRepository;
        Mockito.when(myUserRepository.findByName("Hans")).thenReturn(myHans);
        Mockito.when(myUserRepository.save(myHans)).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void confirmCorrectTest(){
        assertTrue(as.confirm(myHans.getName(), myHans.getCode()));
    }

    @Test
    public void confirmWrongTest(){
        assertFalse(as.confirm(myHans.getName(), 32234));
    }
}
