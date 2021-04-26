package de.dhbw.binaeratops.service.impl.registration;

import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;

import de.dhbw.binaeratops.model.entitys.User;
import de.dhbw.binaeratops.model.repository.UserRepository;
import de.dhbw.binaeratops.service.api.registration.AuthServiceI;
import de.dhbw.binaeratops.view.DummyView;
import de.dhbw.binaeratops.view.MainView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AuthService implements AuthServiceI {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Override
    public void authenticate(String AName, String APassword) throws AuthException, NotVerifiedException {
        User user=userRepository.findByName(AName);

        if(user != null && user.checkPassword(APassword)){
            if(!user.isVerified()){
                throw new NotVerifiedException();
            }
            else {
                VaadinSession.getCurrent().setAttribute(User.class, user);
                createRoutes();
            }
        }else {
            throw new AuthException();
        }
    }

    @Override
    public void register(String AName, String APassword, String AEMail) throws RegistrationException {
        int code=new Random().nextInt(999999);

        if(userRepository.findByName(AName)!=null){
            throw new RegistrationException();
        }

        User user= new User(AName,AEMail,APassword,code,false);
        userRepository.save(user);
        mailService.sendVerificationMail(user,code);
    }

    @Override
    public boolean confirm(String AUserName, int ACode) {
        User user = userRepository.findByName(AUserName);
        if(user.getCode()==ACode && !user.isVerified()){
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void sendConfirmationEmail(long AUserId) {

    }

    @Override
    public void changePassword(long AUserId, String ANewPassword, int ACode) {

    }

    @Override
    public void changePassword(long AUserId, String ANewPassword, String AOldPassword) {

    }

    /**
     * Seiten Verlinkung vornehmen. @TODO
     */
    private void createRoutes(){
        getRoutsForMenu().stream()
                .forEach(r->
                        RouteConfiguration.forSessionScope().setRoute(r.getRout(),r.getView(), MainView.class));
        getRouts().stream()
                .forEach(r->
                        RouteConfiguration.forSessionScope().setRoute(r.getRout(),r.getView()));
    }

    /**
     * Auflisten der Routen. @TODO
     * @return Rückgabe Routen.
     */
    private List<AuthorizedRoute> getRoutsForMenu(){
        List<AuthorizedRoute> returnList= new ArrayList<>();
        returnList.add(new AuthorizedRoute("dummy","Dumm", DummyView.class));
        return returnList;
    }

    /**
     * @TODO
     * @return @TODO
     */
    private  List<AuthorizedRoute> getRouts(){
        List<AuthorizedRoute> returnList= new ArrayList<>();
        return returnList;
    }
}
