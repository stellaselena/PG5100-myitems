package entity;

import validation.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stella on 11.08.2017.
 */
@Entity
public class User {
    @Id
    @Pattern(regexp = "[A-Za-z0-9]{1,32}")
    private String userId;

    @NotEmpty
    private String hash;

    @NotEmpty @Size(max = 26)
    private String salt;

    @NotEmpty @Size(min=1 , max = 32)
    private String firstName;

    @Size(min=0 , max = 32)
    private String middleName;

    @NotEmpty @Size(min=1 , max = 32)
    private String lastName;

    @ManyToMany(mappedBy = "usedByUsers", fetch = FetchType.EAGER)
    private List<Item> itemsUsed;

    public User() {
    }

    public List<Item> getItemsUsed() {
        if(itemsUsed == null){
            return new ArrayList<>();
        }
        return itemsUsed;
    }

    public void setItemsUsed(List<Item> itemsUsed) {
        this.itemsUsed = itemsUsed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
