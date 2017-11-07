package halo.com.moneytracker;

import android.app.Application;
import android.text.TextUtils;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Date;

import halo.com.moneytracker.database.AccountManager;
import halo.com.moneytracker.database.CategoryManager;
import halo.com.moneytracker.models.Account;
import halo.com.moneytracker.models.Category;
import halo.com.moneytracker.models.SessionFirstApp_;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by HoVanLy on 7/22/2016.
 */

@EApplication
public class MyApplication extends Application {
    @Pref
    SessionFirstApp_ mSessionFirstApp;
    private CategoryManager mCategoryManager;
    private AccountManager mAccountManager;

    @Override
    public void onCreate() {
        super.onCreate();
        configRealm();
        mCategoryManager = new CategoryManager();
        mAccountManager = new AccountManager();
        if (TextUtils.isEmpty(mSessionFirstApp.firstRunApp().get())) {
            createBaseCategory();
            createBaseAccount();
            mSessionFirstApp.firstRunApp().put(getString(R.string.first_app));
        }
    }

    private void createBaseAccount() {
        Account account = new Account();
        account.setName(getString(R.string.name_default_account));
        account.setCreatedDate(new Date());
        mAccountManager.createOrUpdateAccount(account);
    }

    public void createBaseCategory() {
        String[] nameCategories = getResources().getStringArray(R.array.list_category);
        int length = nameCategories.length;
        for (int i = 0; i < length; i++) {
            Category category = new Category();
            category.setName(nameCategories[i]);
            category.setIdICon(i);
            mCategoryManager.addCategory(category);
        }
    }

    private void configRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
    }
}
