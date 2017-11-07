package halo.com.moneytracker.evenbus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by HoVanLy on 8/3/2016.
 */
public final class BusProvider {
    private static Bus sBus;

    public static Bus getInstance() {
        if (sBus == null) {
            sBus = new Bus(ThreadEnforcer.ANY);
        }
        return sBus;
    }
}
