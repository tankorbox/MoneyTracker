package halo.com.moneytracker.models;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by HoVanLy on 7/24/2016.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Exchange extends RealmObject implements Serializable {
    @PrimaryKey
    private String id;
    private String nameCategory;
    private int idIconCategory;
    private String note;
    private long money;
    private Date createdDate;
    private boolean isTraded;
    private String nameAccount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public int getIdIconCategory() {
        return idIconCategory;
    }

    public void setIdIconCategory(int idIconCategory) {
        this.idIconCategory = idIconCategory;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isTraded() {
        return isTraded;
    }

    public void setTraded(boolean traded) {
        isTraded = traded;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }
}
