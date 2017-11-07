package halo.com.moneytracker.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import halo.com.moneytracker.database.ExchangeManager;
import halo.com.moneytracker.evenbus.BusProvider;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.utils.TimeUtil;

/**
 * Created by HoVanLy on 7/28/2016.
 */
abstract class BaseFragment extends Fragment {
    protected List<Exchange> exchanges;
    protected ExchangeManager exchangeManager;
    protected Date time;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        exchanges = new ArrayList<>();
        exchangeManager = new ExchangeManager();
        String stringTime = TimeUtil.getInstance().getShortStringTime(new Date());
        time = TimeUtil.getInstance().getDate(stringTime);
        BusProvider.getInstance().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }
}
