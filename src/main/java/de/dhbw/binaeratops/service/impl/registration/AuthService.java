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
    public void authenticate(String name, String password) throws AuthException  {
        User user=userRepository.findByName(name);

        if(user != null && user.checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(User.class,user);
            createRoutes();
        }else {
            throw new AuthException();
        }

    }

    @Override
    public void register(String name, String password, String eMail) throws RegistrationException {
        int code=new Random().nextInt(999999);

        if(userRepository.findByName(name)!=null){
            throw new RegistrationException();
        }

        User user= new User(name,eMail,password,code,false);
        mailService.sendVerificationMail(user,code);
        userRepository.save(user);
    }

    @Override
    public boolean confirm(String userName, int code) {
        User user = userRepository.findByName(userName);
        if(user.getCode()==code && !user.isVerified())
            return true;
        return false;
    }

    @Override
    public void sendConfirmationEmail(long userID) {

    }

    @Override
    public void changePassword(long userID, String newPassword, int code) {

    }

    @Override
    public void changePassword(long userID, String newPassword, String oldPassword) {

    }


    private void createRoutes(){
        getRoutsForMenue().stream()
                .forEach(r->
                        RouteConfiguration.forSessionScope().setRoute(r.getRout(),r.getView(), MainView.class));
        getRouts().stream()
                .forEach(r->
                        RouteConfiguration.forSessionScope().setRoute(r.getRout(),r.getView()));
    }

    private List<AuthorizedRoute> getRoutsForMenue(){
        List<AuthorizedRoute> returnList= new ArrayList<>();

        returnList.add(new AuthorizedRoute("dummy","Dumm", DummyView.class));

        return returnList;
    }

    private  List<AuthorizedRoute> getRouts(){
        List<AuthorizedRoute> returnList= new ArrayList<>();



        return returnList;
    }
}
