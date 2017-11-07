package halo.com.moneytracker.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by HoVanLy on 7/28/2016.
 */
@EActivity
abstract class BaseActivity extends AppCompatActivity {
    protected FragmentManager fragmentManager;


    @AfterViews
    public void innit() {
        fragmentManager = getSupportFragmentManager();
    }

}
