package halo.com.moneytracker.database;

import java.util.List;

import halo.com.moneytracker.models.Account;

/**
 * Created by HoVanLy on 8/4/2016.
 */
interface IAccountManager {
    void createOrUpdateAccount(Account account);

    void addMoneyAccount(String name, long money);

    void deleteAccount(String name);

    void addMoneyExchange(String name, long money);

    List<Account> getAccounts();

    Account getAccount(String name);
}
