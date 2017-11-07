package halo.com.moneytracker.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import halo.com.moneytracker.R;
import halo.com.moneytracker.activities.AddOrUpdateExchangeActivity_;
import halo.com.moneytracker.activities.DetailExchangeActivity_;
import halo.com.moneytracker.activities.StatisticalActivity_;
import halo.com.moneytracker.adapters.ExchangeRecyclerMonthsAdapter;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.evenbus.events.OttoExchanges;
import halo.com.moneytracker.fragments.dialogs.MonthYearPickerDiaLog;
import halo.com.moneytracker.fragments.dialogs.MonthYearPickerDiaLog_;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.models.Statistical;
import halo.com.moneytracker.recyclers.ClickItemRecyclerView;
import halo.com.moneytracker.recyclers.DividerItemDecoration;
import halo.com.moneytracker.recyclers.IClickItemRecyclerView;
import halo.com.moneytracker.utils.StringUtil;
import halo.com.moneytracker.utils.TimeUtil;

/**
 * Created by HoVanLy on 7/25/2016.
 */
@EFragment(R.layout.fragment_exchange)
public class ExchangeMonthsFragment extends BaseFragment {
    @ViewById(R.id.fabAddAccount)
    FloatingActionButton mFloatingActionButton;
    @ViewById(R.id.tvTime)
    TextView mTvTime;
    @ViewById(R.id.recyclerViewExchangeMain)
    RecyclerView mRecyclerView;
    @ViewById(R.id.tvTotalMoney)
    TextView mTvTotalMoney;
    @ViewById(R.id.llDetailStatistical)
    LinearLayout mLlDetailStatistical;
    private ExchangeRecyclerMonthsAdapter mExchangeRecyclerMonthsAdapter;
    private int mYear;
    private int mMonth;

    @AfterViews
    public void setUpUi() {
        exchanges = getExchanges();
        setUpRecyclerExchangeMonth();
        setViewMenuMonth();
    }

    @Click(R.id.fabAddAccount)
    public void clickAddExchange() {
        Intent intent = new Intent(getContext(), AddOrUpdateExchangeActivity_.class);
        startActivityForResult(intent, Constant.REQUEST_CODE_ADD_EXCHANGE);
    }

    @OnActivityResult(Constant.REQUEST_CODE_ADD_EXCHANGE)
    void onResultAdd(int resultCode, Intent intent) {
        if (resultCode == Constant.RESULT_CODE_ADD_EXCHANGE) {
            Exchange exchange = (Exchange) intent.getSerializableExtra(getString(R.string.serializable_exchange));
            if (TimeUtil.getInstance().getNumberYear(exchange.getCreatedDate()) == mYear
                    && TimeUtil.getInstance().getNumberMonth(exchange.getCreatedDate()) == mMonth) {
                exchanges.add(exchange);
                exchanges = exchangeManager.sortDescendingExchanges(exchanges);
                mExchangeRecyclerMonthsAdapter.notifyDataSetChanged();
            }
            exchangeManager.addOrUpdateExchange(exchange);
            mTvTotalMoney.setText(StringUtil.getInstance().getStringMoney(exchangeManager.getMoneyTotal(exchanges)));
        }
    }

    private List<Exchange> getExchanges() {
        mYear = TimeUtil.getInstance().getNumberYear(time);
        mMonth = TimeUtil.getInstance().getNumberMonth(time);
        return exchangeManager.getExchangeMonths(mMonth, mYear);
    }

    private void setViewMenuMonth() {
        String year = String.valueOf(TimeUtil.getInstance().getNumberYear(time));
        String nameMonth = String.valueOf(TimeUtil.getInstance().getNameMonth(time));
        mTvTime.setText(String.format("%s %s", nameMonth, year));
        mTvTotalMoney.setText(StringUtil.getInstance().getStringMoney(exchangeManager.getMoneyTotal(exchanges)));
    }

    private void setUpRecyclerExchangeMonth() {
        mExchangeRecyclerMonthsAdapter = new ExchangeRecyclerMonthsAdapter(getContext(), exchanges);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView.setAdapter(mExchangeRecyclerMonthsAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new ClickItemRecyclerView(getContext(), mRecyclerView, new IClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position) {
                clickItemRecyclerExchange(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        mExchangeRecyclerMonthsAdapter.notifyDataSetChanged();
    }

    @OnActivityResult(Constant.REQUEST_CODE_DETAIL_EXCHANGE)
    void onResultDeleteOrUpdate(int resultCode, Intent intent) {
        if (resultCode == Constant.RESULT_CODE_DELETE_EXCHANGE) {
            int position = intent.getIntExtra(getString(R.string.position_exchanges), -1);
            exchanges.remove(position);
            mExchangeRecyclerMonthsAdapter.notifyItemRemoved(position);
        }
        if (resultCode == Constant.RESULT_CODE_EDIT_EXCHANGE) {
            Exchange exchange = (Exchange) intent.getSerializableExtra(getString(R.string.serializable_exchange));
            int position = intent.getIntExtra(getString(R.string.position_exchanges), -1);
            if (TimeUtil.getInstance().getNumberYear(exchange.getCreatedDate()) == mYear
                    && TimeUtil.getInstance().getNumberMonth(exchange.getCreatedDate()) == mMonth) {
                exchanges.remove(position);
                exchanges.add(position, exchange);
                exchanges = exchangeManager.sortDescendingExchanges(exchanges);
                mExchangeRecyclerMonthsAdapter.notifyDataSetChanged();
            } else {
                exchanges.remove(position);
                mExchangeRecyclerMonthsAdapter.notifyItemChanged(position);
            }

        }
        mTvTotalMoney.setText(StringUtil.getInstance().getStringMoney(exchangeManager.getMoneyTotal(exchanges)));
    }


    private void clickItemRecyclerExchange(int position) {
        // Click for detail exchange
        Exchange exchange = new Exchange();
        exchange.setId(exchanges.get(position).getId());
        exchange.setMoney(exchanges.get(position).getMoney());
        exchange.setNameCategory(exchanges.get(position).getNameCategory());
        exchange.setCreatedDate(exchanges.get(position).getCreatedDate());
        exchange.setIdIconCategory(exchanges.get(position).getIdIconCategory());
        exchange.setNote(exchanges.get(position).getNote());
        exchange.setNameAccount(exchanges.get(position).getNameAccount());
        exchange.setTraded(exchanges.get(position).isTraded());
        Intent intent = new Intent(getContext(), DetailExchangeActivity_.class);
        intent.putExtra(getString(R.string.detail_exchange), exchange);
        intent.putExtra(getString(R.string.position_exchanges), position);
        startActivityForResult(intent, Constant.REQUEST_CODE_DETAIL_EXCHANGE);
    }

    @Click(R.id.tvTime)
    public void clickTime() {
        MonthYearPickerDiaLog monthYearPickerDiaLog = MonthYearPickerDiaLog_.builder().build();
        monthYearPickerDiaLog.setListenTextView(mTvTime);
        monthYearPickerDiaLog.show(getActivity().getFragmentManager(), getString(R.string.dialog_tag));
    }

    @Subscribe
    public void getExchange(OttoExchanges ottoExchanges) {
        exchanges.clear();
        exchanges.addAll(ottoExchanges.getExchanges());
        mExchangeRecyclerMonthsAdapter.notifyDataSetChanged();
        mYear = ottoExchanges.getYear();
        mMonth = ottoExchanges.getMonth();
        mTvTotalMoney.setText(StringUtil.getInstance().getStringMoney(exchangeManager.getMoneyTotal(exchanges)));
    }

    @Click(R.id.llDetailStatistical)
    public void statisticalExchange() {
        StatisticalActivity_.intent(getContext())
                .mStatistical(Statistical.builder().year(mYear).month(mMonth).type(Constant.STATISTICAL_MONTH).build())
                .start();
    }
}
