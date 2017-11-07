package halo.com.moneytracker.database;

import java.util.Date;
import java.util.List;

import halo.com.moneytracker.models.Exchange;

/**
 * Created by HoVanLy on 7/27/2016.
 */
public interface IExchangeManager {
    void addOrUpdateExchange(Exchange exchange);

    List<Exchange> getExchangeYears(int year);

    List<Exchange> getExchangeMonths(int month, int year);

    List<Exchange> getExchangeDays(Date date);

    List<Exchange> getExchangesPlan();

    long getMoneyTotal(List<Exchange> exchanges);

    void deleteExchange(String id);
}
