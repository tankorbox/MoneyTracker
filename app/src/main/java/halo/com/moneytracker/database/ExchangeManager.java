package halo.com.moneytracker.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.utils.TimeUtil;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by HoVanLy on 7/26/2016.
 */
public class ExchangeManager extends RealmHelper implements IExchangeManager {
    @Override
    public void addOrUpdateExchange(Exchange exchange) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(exchange);
        realm.commitTransaction();
    }

    @Override
    public List<Exchange> getExchangeYears(int year) {
        List<Exchange> exchanges = new ArrayList<>();
        RealmResults<Exchange> realmResults = realm.where(Exchange.class).equalTo(Constant.TRADED, true)
                .findAllSorted(Constant.CREATED_DATE, Sort.DESCENDING);
        for (Exchange exchange : realmResults) {
            if (TimeUtil.getInstance().checkDateOfYear(exchange.getCreatedDate(), year)) {
                exchanges.add(exchange);
            }
        }
        return exchanges;
    }

    @Override
    public List<Exchange> getExchangeMonths(int month, int year) {
        List<Exchange> exchanges = new ArrayList<>();
        RealmResults<Exchange> realmResults = realm.where(Exchange.class)
                .equalTo(Constant.TRADED, true).findAllSorted(Constant.CREATED_DATE, Sort.DESCENDING);
        for (Exchange exchange : realmResults) {
            if (TimeUtil.getInstance().checkDateOfMonthYear(exchange.getCreatedDate(), month, year)) {
                exchanges.add(exchange);
            }
        }
        return exchanges;
    }

    @Override
    public List<Exchange> getExchangeDays(Date date) {
        List<Exchange> exchanges = new ArrayList<>();
        RealmResults<Exchange> realmResults = realm.where(Exchange.class)
                .equalTo(Constant.TRADED, true).findAllSorted(Constant.CREATED_DATE, Sort.DESCENDING);
        for (Exchange exchange : realmResults) {
            if (TimeUtil.getInstance().checkSameDay(date, exchange.getCreatedDate())) {
                exchanges.add(exchange);
            }
        }
        return exchanges;
    }

    @Override
    public List<Exchange> getExchangesPlan() {
        List<Exchange> exchanges = new ArrayList<>();
        RealmResults<Exchange> realmResults = realm.where(Exchange.class)
                .equalTo(Constant.TRADED, false).findAllSorted(Constant.CREATED_DATE, Sort.ASCENDING);
        for (Exchange exchange : realmResults) {
            exchanges.add(exchange);
        }
        return exchanges;
    }

    @Override
    public long getMoneyTotal(List<Exchange> exchanges) {
        long money = 0;
        for (Exchange exchange : exchanges) {
            money += exchange.getMoney();
        }
        return money;
    }

    public List<Exchange> getExchanges() {
        List<Exchange> exchanges = new ArrayList<>();
        RealmResults<Exchange> realmResults = realm.where(Exchange.class)
                .equalTo(Constant.TRADED, true).findAllSorted(Constant.CREATED_DATE, Sort.DESCENDING);
        for (Exchange exchange : realmResults) {
            exchanges.add(exchange);
        }
        return exchanges;
    }

    @Override
    public void deleteExchange(String id) {
        realm.beginTransaction();
        Exchange exchange = realm.where(Exchange.class).equalTo(Constant.ID_EXCHANGE, id).findFirst();
        exchange.deleteFromRealm();
        realm.commitTransaction();
    }

    public void setTraded(Exchange exchange, boolean isTraded) {
        realm.beginTransaction();
        exchange.setTraded(isTraded);
        realm.copyToRealmOrUpdate(exchange);
        realm.commitTransaction();
    }

    public List<Exchange> sortDescendingExchanges(List<Exchange> exchanges) {
        Collections.sort(exchanges, new Comparator<Exchange>() {
            @Override
            public int compare(Exchange exchange1, Exchange exchange2) {
                return exchange2.getCreatedDate().compareTo(exchange1.getCreatedDate());
            }
        });
        return exchanges;
    }

    public List<Exchange> sortAscendingExchanges(List<Exchange> exchanges) {
        Collections.sort(exchanges, new Comparator<Exchange>() {
            @Override
            public int compare(Exchange exchange1, Exchange exchange2) {
                return exchange1.getCreatedDate().compareTo(exchange2.getCreatedDate());
            }
        });
        return exchanges;
    }
}
