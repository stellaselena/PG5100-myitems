package ejb;

import entity.Item;
import entity.User;
import validation.ItemTypes;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by Stella on 11.08.2017.
 */

@Stateless
public class ItemEJB {

    @PersistenceContext
    private EntityManager em;

    public long createItem(String userId, String itemType, String title, String text){
        User user = em.find(User.class, userId);
        if(user == null){
            throw new IllegalArgumentException("No user with id: "+userId);
        }
        if(!ItemTypes.getTypes().contains(itemType)){
            throw new IllegalArgumentException("Invalid item type: "+itemType);
        }

        Item item = new Item();
        item.setUser(user);
        item.setType(itemType);
        item.setTitle(title);
        item.setText(text);
        item.setCreatedTime(new Date());

        em.persist(item);

        return item.getId();
    }

    public Item getItem(long itemId) {

        return em.find(Item.class, itemId);
    }

    public List<Item> getAllItems(){
        Query query = em.createQuery("select i from Item i order by i.createdTime desc");
        return query.getResultList();

    }

    public List<Item>getAllItemsByType(String type){
        Query query = em.createQuery("select i from Item i where i.type = ?1");
        query.setParameter(1, type);
        return query.getResultList();
    }

    public List<Item>getAllItemsByUser(String userId){
        Query query = em.createQuery("select i from Item i where i.user.userId = ?1");
        query.setParameter(1, userId);
        return query.getResultList();
    }


}
