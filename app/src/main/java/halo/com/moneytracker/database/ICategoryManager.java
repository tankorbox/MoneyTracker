package halo.com.moneytracker.database;

import java.util.List;

import halo.com.moneytracker.models.Category;

/**
 * Created by HoVanLy on 7/27/2016.
 */
interface ICategoryManager {
    void addCategory(Category category);

    void editCategory(Category category);

    void deleteCategory(String name);

    List<Category> getCategories();
}
