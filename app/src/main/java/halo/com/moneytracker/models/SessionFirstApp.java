package halo.com.moneytracker.models;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by HoVanLy on 4/23/2016.
 */
@SharedPref
public interface SessionFirstApp {
    @DefaultString("")
    String firstRunApp();

    @DefaultInt(0)
    int typeView();

}
