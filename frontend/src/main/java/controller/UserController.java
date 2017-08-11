package controller;

/**
 * Created by Stella on 11.08.2017.
 */

import ejb.ItemEJB;
import ejb.UserEJB;
import entity.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class UserController {

    @EJB
    private UserEJB userEJB;

    @EJB
    private ItemEJB itemEJB;

    public User getUser(String userId){

        return userEJB.getUser(userId);
    }

    public int getUserItems(String userId) {
        return itemEJB.getAllItemsByUser(userId).size();
    }

}
