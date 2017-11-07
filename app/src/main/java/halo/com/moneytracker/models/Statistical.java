package halo.com.moneytracker.models;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Builder;

/**
 * Created by HoVanLy on 8/9/2016.
 */
@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class Statistical implements Serializable {
    private Date date;
    private int year;
    private int month;
    private int type;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
