package entity;

import validation.ItemType;
import validation.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Stella on 11.08.2017.
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 1024)
    private String title;

    @NotEmpty
    @Size(min = 1, max = 1024)
    private String text;

    @NotNull
    @ManyToOne
    private User user;

    @ItemType
    private String type;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> usedByUsers;

    public List<User> getUsedByUsers() {
        if(usedByUsers == null){
            return new ArrayList<>();
        }
        return usedByUsers;
    }

    public void setUsedByUsers(List<User> usedByUsers) {
        this.usedByUsers = usedByUsers;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
