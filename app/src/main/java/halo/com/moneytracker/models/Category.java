package halo.com.moneytracker.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by HoVanLy on 7/24/2016.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Category extends RealmObject {
    @PrimaryKey
    private String name;
    private String note;
    private int idICon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIdICon() {
        return idICon;
    }

    public void setIdICon(int idICon) {
        this.idICon = idICon;
    }
}
