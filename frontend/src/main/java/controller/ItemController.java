package controller;

import ejb.ItemEJB;
import ejb.UserEJB;
import entity.Item;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Stella on 11.08.2017.
 */
@Named
@SessionScoped
public class ItemController implements Serializable {

    private boolean showOnlyOwnItems;

    @EJB
    private ItemEJB itemEJB;

    @EJB
    private UserEJB userEJB;

    @Inject
    private LoggingController loggingController;

    @PostConstruct
    public void init(){
        showOnlyOwnItems = true;
    }

    public List<Item> getItems(){
        List<Item> items;
        if(! loggingController.isLoggedIn() || ! showOnlyOwnItems){
            items = itemEJB.getAllItems();
        } else {
            items = itemEJB.getAllItemsByUser(loggingController.getRegisteredUser());
        }

        return items;
    }

    public boolean isShowOnlyOwnItems() {
        return showOnlyOwnItems;
    }

    public void setShowOnlyOwnItems(boolean showOnlyOwnItems) {
        this.showOnlyOwnItems = showOnlyOwnItems;
    }

    public static String excerpt(String text){
        int limit = 30;

        if(text.length() > limit){
            return text.substring(0, limit - 4) + " ...";
        }
        return text;
    }

    public String getItemExcerpt(Item item){
        String text = item.getText();
        return excerpt(text);
    }

    public int getUserItems(){
        return itemEJB.getAllItemsByUser(loggingController.getRegisteredUser()).size();
    }
}
