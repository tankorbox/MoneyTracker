package halo.com.moneytracker.utils;

import halo.com.moneytracker.R;

/**
 * Created by HoVanLy on 7/29/2016.
 */
public final class IconUtil {
    private static IconUtil mIconUtil;

    public static IconUtil getInstance() {
        if (mIconUtil == null) {
            mIconUtil = new IconUtil();
        }
        return mIconUtil;
    }

    public int getIdResDrawable(int idIcon) {
        switch (idIcon) {
            case 0:
                return R.drawable.ic_food;
            case 1:
                return R.drawable.ic_house;
            case 2:
                return R.drawable.ic_shopping;
            case 3:
                return R.drawable.ic_entertainment;
            case 4:
                return R.drawable.ic_learning;
            case 5:
                return R.drawable.ic_travel;
            case 6:
                return R.drawable.ic_health;
            case 7:
                return R.drawable.ic_sports;
            case 8:
                return R.drawable.ic_pets;
            case 9:
                return R.drawable.ic_gift;
        }
        return R.drawable.ic_exchange;
    }
}
