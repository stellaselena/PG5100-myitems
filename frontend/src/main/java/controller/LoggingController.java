package controller;

import ejb.UserEJB;
import entity.User;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by Stella on 11.08.2017.
 */
@Named
@SessionScoped
public class LoggingController implements Serializable {

    @EJB
    private UserEJB userEJB;

    private String formUserName;
    private String formPassword;
    private String formConfirmPassword;
    private String formFirstName;
    private String formMiddleName;
    private String formLastName;

    private String registeredUser;

    public LoggingController() {
    }

    public User getUser() {
        return userEJB.getUser(registeredUser);
    }

    public boolean isLoggedIn() {

        return registeredUser != null;
    }

    public String logIn() {
        boolean valid = userEJB.login(formUserName, formPassword);
        if (valid) {
            registeredUser = formUserName;
            return "home.jsf";
        } else {
            return "login.jsf";
        }
    }

    public String logOut() {
        registeredUser = null;
        return "home.jsf";
    }

    public String registerNew() {
        if (!formPassword.equals(formConfirmPassword)) {
            return "newUser.jsf";
        }

        boolean registered = false;
        try {
            registered = userEJB.createUser(formUserName, formPassword, formFirstName, formMiddleName, formLastName);
        } catch (Exception e) {

        }
        if (registered) {
            registeredUser = formUserName;
            return "home.jsf";
        } else {
            return "newUser.jsf";
        }
    }

    public void setFormUserName(String formUserName) {
        this.formUserName = formUserName;
    }

    public String getFormUserName() {
        return formUserName;
    }

    public String getFormPassword() {
        return formPassword;
    }

    public void setFormPassword(String formPassword) {
        this.formPassword = formPassword;
    }

    public String getFormConfirmPassword() {
        return formConfirmPassword;
    }

    public void setFormConfirmPassword(String formConfirmPassword) {
        this.formConfirmPassword = formConfirmPassword;
    }

    public String getFormFirstName() {
        return formFirstName;
    }

    public void setFormFirstName(String formFirstName) {
        this.formFirstName = formFirstName;
    }

    public String getFormMiddleName() {
        return formMiddleName;
    }

    public void setFormMiddleName(String formMiddleName) {
        this.formMiddleName = formMiddleName;
    }

    public String getFormLastName() {
        return formLastName;
    }

    public void setFormLastName(String formLastName) {
        this.formLastName = formLastName;
    }

    public String getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(String registeredUser) {
        this.registeredUser = registeredUser;
    }
}
