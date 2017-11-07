package halo.com.moneytracker.recyclers;

import android.view.View;

/**
 * Created by HoVanLy on 6/14/2016.
 */
 public interface IClickItemRecyclerView {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
