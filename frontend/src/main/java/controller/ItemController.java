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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Stella on 11.08.2017.
 */
@Named
@SessionScoped
public class ItemController implements Serializable {

    public enum Types {Car, Film, Music, Sport, Diverse}

    public enum Collection {Indoor, Outdoor, All}

    private boolean showOnlyOwnItems;

    @EJB
    private ItemEJB itemEJB;

    @EJB
    private UserEJB userEJB;

    @Inject
    private LoggingController loggingController;

    private Types types = Types.Car;

    private Collection collection = Collection.All;


    @PostConstruct
    public void init() {
        showOnlyOwnItems = true;
    }

    public List<Item> getItems() {
        List<Item> items;
        if (!loggingController.isLoggedIn() || !showOnlyOwnItems) {
            items = itemEJB.getAllItems();
        } else {
            items = itemEJB.getAllItemsByUser(loggingController.getRegisteredUser());
        }

        return items;
    }


    public static String excerpt(String text) {
        int limit = 30;

        if (text.length() > limit) {
            return text.substring(0, limit - 4) + " ...";
        }
        return text;
    }

    public String getItemExcerpt(Item item) {
        String text = item.getText();
        return excerpt(text);
    }

    public int getUserItems() {
        return itemEJB.getAllItemsByUser(loggingController.getRegisteredUser()).size();
    }

    public int getItemTypeCount() {
        List<Item> items;

        if (types.equals(Types.Car)) {
            items = itemEJB.getAllItemsByType("Car");
        } else if (types.equals(Types.Diverse)) {
            items = itemEJB.getAllItemsByType("Diverse");
        } else if (types.equals(Types.Film)) {
            items = itemEJB.getAllItemsByType("Film");
        } else if (types.equals(Types.Music)) {
            items = itemEJB.getAllItemsByType("Music");
        } else {
            items = itemEJB.getAllItemsByType("Sport");

        }
        return items.size();

    }

    public List<Types> getItemTypeModes() {
        return Arrays.asList(Types.Car, Types.Film, Types.Music, Types.Sport, Types.Diverse);

    }

    public List<Collection> getCollectionModes() {
        return Arrays.asList(Collection.Indoor, Collection.Outdoor, Collection.All);

    }

    public List<Item> getItemCollection() {
        List<Item> items;

        if (collection.equals(Collection.All)) {
            items = itemEJB.getAllItems();
        } else if (collection.equals(Collection.Indoor)) {
            List<Item> film = itemEJB.getAllItemsByType("Film");
            List<Item> movie = itemEJB.getAllItemsByType("Music");
            items = Stream.concat(film.stream(), movie.stream()).collect(Collectors.toList());
        } else {
            List<Item> sport = itemEJB.getAllItemsByType("Sport");
            List<Item> car = itemEJB.getAllItemsByType("Car");
            items = Stream.concat(sport.stream(), car.stream()).collect(Collectors.toList());

        }
        return items;

    }

    public boolean isShowOnlyOwnItems() {
        return showOnlyOwnItems;
    }

    public void setShowOnlyOwnItems(boolean showOnlyOwnItems) {
        this.showOnlyOwnItems = showOnlyOwnItems;
    }

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }


}
