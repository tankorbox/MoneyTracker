package halo.com.moneytracker.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import halo.com.moneytracker.R;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.fragments.AccountFragment_;
import halo.com.moneytracker.fragments.ExchangeDaysFragment_;
import halo.com.moneytracker.fragments.ExchangeMonthsFragment_;
import halo.com.moneytracker.fragments.ExchangePlanFragment_;
import halo.com.moneytracker.fragments.ExchangeYearsFragment_;
import halo.com.moneytracker.models.SessionFirstApp_;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main_menu)
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private int mTypeView;
    @ViewById(R.id.drawLayout)
    DrawerLayout mDrawerLayout;
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.idContent)
    FrameLayout mFrameLayout;
    @ViewById(R.id.nav_view)
    NavigationView mNavigationView;
    @Pref
    SessionFirstApp_ mSessionFirstApp;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Fragment mFragmentExchange;
    boolean isFragmentExchange = true;

    @AfterViews
    public void init() {
        mTypeView = mSessionFirstApp.typeView().get();
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView.setNavigationItemSelectedListener(this);
        initViewExchange();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (isFragmentExchange) {
                super.onBackPressed();
            } else {
                isFragmentExchange = true;
                replaceFragment(mFragmentExchange);
            }
        }
    }

    private void initViewExchange() {
        switch (mTypeView) {
            case Constant.TYPE_VIEW_YEAR:
                mFragmentExchange = ExchangeYearsFragment_.builder().build();
                break;
            case Constant.TYPE_VIEW_MONTH:
                mFragmentExchange = ExchangeMonthsFragment_.builder().build();
                break;
            case Constant.TYPE_VIEW_DAY:
                mFragmentExchange = ExchangeDaysFragment_.builder().build();
                break;
        }
        replaceFragment(mFragmentExchange);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        Fragment fragment = new Fragment();
        switch (idItem) {
            case R.id.nav_exchange:
                fragment = mFragmentExchange;
                break;
            case R.id.nav_account:
                fragment = AccountFragment_.builder().build();
                break;
            case R.id.nav_plan:
                fragment = ExchangePlanFragment_.builder().build();
        }
        replaceFragment(fragment);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        isFragmentExchange = (idItem == R.id.nav_exchange);
        return true;
    }

    @OptionsItem(R.id.action_day)
    public void selectDay() {
        mFragmentExchange = ExchangeDaysFragment_.builder().build();
        mTypeView = Constant.TYPE_VIEW_DAY;
        mSessionFirstApp.typeView().put(mTypeView);
        replaceFragment(mFragmentExchange);
    }

    @OptionsItem(R.id.action_month)
    public void selectMonth() {
        mFragmentExchange = ExchangeMonthsFragment_.builder().build();
        mTypeView = Constant.TYPE_VIEW_MONTH;
        mSessionFirstApp.typeView().put(mTypeView);
        replaceFragment(mFragmentExchange);
    }

    @OptionsItem(R.id.action_year)
    public void selectYear() {
        mFragmentExchange = ExchangeYearsFragment_.builder().build();
        mTypeView = Constant.TYPE_VIEW_YEAR;
        mSessionFirstApp.typeView().put(mTypeView);
        replaceFragment(mFragmentExchange);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.idContent, fragment);
        mFragmentTransaction.commit();
    }

}
