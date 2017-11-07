package halo.com.moneytracker.database;

import io.realm.Realm;

/**
 * Created by HoVanLy on 7/28/2016.
 */
abstract class RealmHelper {
    protected Realm realm;
    RealmHelper() {
        realm = Realm.getDefaultInstance();
    }
}
