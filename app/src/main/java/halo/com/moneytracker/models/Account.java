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
public class Account extends RealmObject implements Serializable {
    @PrimaryKey
    private String name;
    private long accountMoney;
    private long exchangeMoney;
    private String note;
    private Date createdDate;
    private boolean isLock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(long accountMoney) {
        this.accountMoney = accountMoney;
    }

    public long getExchangeMoney() {
        return exchangeMoney;
    }

    public void setExchangeMoney(long exchangeMoney) {
        this.exchangeMoney = exchangeMoney;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }
}
