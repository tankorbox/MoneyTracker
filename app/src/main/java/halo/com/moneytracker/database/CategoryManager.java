package halo.com.moneytracker.database;

import java.util.ArrayList;
import java.util.List;

import halo.com.moneytracker.models.Category;
import io.realm.RealmResults;

/**
 * Created by HoVanLy on 7/27/2016.
 */
public class CategoryManager extends RealmHelper implements ICategoryManager {
    @Override
    public void addCategory(Category category) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(category);
        realm.commitTransaction();
    }

    @Override
    public void editCategory(Category category) {

    }

    @Override
    public void deleteCategory(String name) {

    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        RealmResults<Category> realmResults = realm.where(Category.class).findAll();
        for (Category category : realmResults) {
            categories.add(category);
        }
        return categories;
    }
}
