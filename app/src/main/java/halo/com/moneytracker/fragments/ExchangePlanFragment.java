package halo.com.moneytracker.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import halo.com.moneytracker.R;
import halo.com.moneytracker.activities.AddOrUpdateExchangeActivity_;
import halo.com.moneytracker.adapters.ExchangeRecyclerPlansAdapter;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.recyclers.ClickItemRecyclerView;
import halo.com.moneytracker.recyclers.DividerItemDecoration;
import halo.com.moneytracker.recyclers.IClickItemRecyclerView;

/**
 * Created by HoVanLy on 7/25/2016.
 */
@EFragment(R.layout.fragment_plans)
public class ExchangePlanFragment extends BaseFragment {
    @ViewById(R.id.recyclerViewPlanExchange)
    RecyclerView mRecyclerView;
    @ViewById(R.id.fabAddExchangePlan)
    FloatingActionButton mFabAddExchangePlan;
    private ExchangeRecyclerPlansAdapter mExchangeRecyclerPlansAdapter;
    private List<Integer> positionsTraded;

    @AfterViews
    public void init() {
        exchanges = exchangeManager.getExchangesPlan();
        positionsTraded = new ArrayList<>();
        setUpRecyclerExchangesPlan();
    }

    private void setUpRecyclerExchangesPlan() {
        mExchangeRecyclerPlansAdapter = new ExchangeRecyclerPlansAdapter(getContext(), exchanges);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mExchangeRecyclerPlansAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new ClickItemRecyclerView(getContext(), mRecyclerView, new IClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position) {
                showPopupMenu(view, position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Click(R.id.fabAddExchangePlan)
    public void clickAddExchangePlane() {
        Intent intent = new Intent(getContext(), AddOrUpdateExchangeActivity_.class);
        intent.putExtra(getString(R.string.add_exchange_plan), Constant.ACTION_ADD_PLAN);
        startActivityForResult(intent, Constant.REQUEST_CODE_ADD_EXCHANGE);
    }

    @OnActivityResult(Constant.REQUEST_CODE_ADD_EXCHANGE)
    void onResultAdd(int resultCode, Intent intent) {
        if (resultCode == Constant.RESULT_CODE_ADD_EXCHANGE) {
            Exchange exchange = (Exchange) intent.getSerializableExtra(getString(R.string.serializable_exchange));
            exchanges.add(exchange);
            exchanges = exchangeManager.sortAscendingExchanges(exchanges);
            mExchangeRecyclerPlansAdapter.notifyDataSetChanged();
            exchangeManager.addOrUpdateExchange(exchange);
        }
    }


    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_plan, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MenuItemClickListener(position));
        popupMenu.show();
    }

    /**
     * Class for handler PopupMenu
     */
    private class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        private final int position;

        public MenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_detail:
                    //TODO
                    break;
                case R.id.action_traded:
                    exchangeManager.setTraded(exchanges.get(position), true);
                    exchanges.remove(position);
                    mExchangeRecyclerPlansAdapter.notifyItemRemoved(position);
                    break;
            }
            return false;
        }
    }
}
