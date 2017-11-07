package halo.com.moneytracker.database;

import java.util.ArrayList;
import java.util.List;

import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.models.Account;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by HoVanLy on 8/4/2016.
 */
public class AccountManager extends RealmHelper implements IAccountManager {
    @Override
    public void createOrUpdateAccount(Account account) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(account);
        realm.commitTransaction();
    }

    @Override
    public void addMoneyAccount(String name, long money) {
        realm.beginTransaction();
        Account account = realm.where(Account.class).equalTo(Constant.NAME_ACCOUNT, name).findFirst();
        long newMoney = account.getAccountMoney() + money;
        account.setAccountMoney(newMoney);
        realm.copyToRealmOrUpdate(account);
        realm.commitTransaction();
    }

    @Override
    public void deleteAccount(String name) {
        realm.beginTransaction();
        Account account = realm.where(Account.class).equalTo(Constant.NAME_ACCOUNT, name).findFirst();
        account.deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void addMoneyExchange(String name, long money) {
        realm.beginTransaction();
        Account account = realm.where(Account.class).equalTo(Constant.NAME_ACCOUNT, name).findFirst();
        long newMoney = account.getExchangeMoney() + money;
        account.setExchangeMoney(newMoney);
        realm.copyToRealmOrUpdate(account);
        realm.commitTransaction();
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        RealmResults<Account> realmResults = realm.where(Account.class).equalTo(Constant.LOCK, false).findAllSorted(Constant.CREATED_DATE, Sort.DESCENDING);
        for (Account account : realmResults) {
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account getAccount(String name) {
        Account account = realm.where(Account.class).endsWith(Constant.NAME_ACCOUNT, name).findFirst();
        return account;
    }
}
