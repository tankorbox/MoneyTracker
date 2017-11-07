package halo.com.moneytracker.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import halo.com.moneytracker.adapters.ExchangeRecyclerYearsAdapter;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.evenbus.events.OttoExchanges;
import halo.com.moneytracker.fragments.dialogs.YearPickerDialog;
import halo.com.moneytracker.fragments.dialogs.YearPickerDialog_;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.models.Statistical;
import halo.com.moneytracker.recyclers.ClickItemRecyclerView;
import halo.com.moneytracker.recyclers.DividerItemDecoration;
import halo.com.moneytracker.recyclers.IClickItemRecyclerView;
import halo.com.moneytracker.utils.StringUtil;
import halo.com.moneytracker.utils.TimeUtil;


@EFragment(R.layout.fragment_exchange)
public class ExchangeYearsFragment extends BaseFragment {
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
    private ExchangeRecyclerYearsAdapter mExchangeRecyclerYearsAdapter;
    private int mYear;

    @AfterViews
    public void setUpUi() {
        exchanges = getExchanges();
        setUpRecyclerExchangeYears();
        setViewMenuYear();
    }

    @Click(R.id.fabAddAccount)
    public void clickAddExchange() {
        Intent intent = new Intent(getContext(), AddOrUpdateExchangeActivity_.class);
        startActivityForResult(intent, Constant.REQUEST_CODE_ADD_EXCHANGE);
    }

    @OnActivityResult(Constant.REQUEST_CODE_ADD_EXCHANGE)
    void onResultAdd(int resultCode, Intent intent) {
        if (resultCode == Constant.RESULT_CODE_ADD_EXCHANGE) {
            Exchange exchangeYear = (Exchange) intent.getSerializableExtra(getString(R.string.serializable_exchange));
            if (TimeUtil.getInstance().getNumberYear(exchangeYear.getCreatedDate()) == mYear) {
                exchanges.add(exchangeYear);
                exchanges = exchangeManager.sortDescendingExchanges(exchanges);
                mExchangeRecyclerYearsAdapter.notifyDataSetChanged();
            }
            exchangeManager.addOrUpdateExchange(exchangeYear);
            mTvTotalMoney.setText(StringUtil.getInstance().getStringMoney(exchangeManager.getMoneyTotal(exchanges)));
        }
    }

    private List<Exchange> getExchanges() {
        mYear = TimeUtil.getInstance().getNumberYear(time);
        return exchangeManager.getExchangeYears(mYear);
    }


    private void setViewMenuYear() {
        mTvTime.setText(String.valueOf(TimeUtil.getInstance().getNumberYear(time)));
        mTvTotalMoney.setText(StringUtil.getInstance().getStringMoney(exchangeManager.getMoneyTotal(exchanges)));
    }


    private void setUpRecyclerExchangeYears() {
        mExchangeRecyclerYearsAdapter = new ExchangeRecyclerYearsAdapter(getContext(), exchanges);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mExchangeRecyclerYearsAdapter);
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
        mExchangeRecyclerYearsAdapter.notifyDataSetChanged();
    }

    @OnActivityResult(Constant.REQUEST_CODE_DETAIL_EXCHANGE)
    void onResultDeleteOrUpdate(int resultCode, Intent intent) {
        if (resultCode == Constant.RESULT_CODE_DELETE_EXCHANGE) {
            int position = intent.getIntExtra(getString(R.string.position_exchanges), -1);
            exchanges.remove(position);
            mExchangeRecyclerYearsAdapter.notifyItemRemoved(position);
        }
        if (resultCode == Constant.RESULT_CODE_EDIT_EXCHANGE) {
            Exchange exchange = (Exchange) intent.getSerializableExtra(getString(R.string.serializable_exchange));
            int position = intent.getIntExtra(getString(R.string.position_exchanges), -1);
            if (TimeUtil.getInstance().getNumberYear(exchange.getCreatedDate()) == mYear) {
                exchanges.remove(position);
                exchanges.add(position, exchange);
                exchanges = exchangeManager.sortDescendingExchanges(exchanges);
                mExchangeRecyclerYearsAdapter.notifyDataSetChanged();
            } else {
                exchanges.remove(position);
                mExchangeRecyclerYearsAdapter.notifyItemChanged(position);
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
    public void pickTimeExchange() {
        YearPickerDialog yearPickerDialog = YearPickerDialog_.builder().build();
        yearPickerDialog.setListenTextView(mTvTime);
        yearPickerDialog.show(getActivity().getFragmentManager(), getString(R.string.dialog_tag));
    }

    @Subscribe
    public void getExchange(OttoExchanges ottoExchanges) {
        exchanges.clear();
        exchanges.addAll(ottoExchanges.getExchanges());
        mExchangeRecyclerYearsAdapter.notifyDataSetChanged();
        mYear = ottoExchanges.getYear();
        mTvTotalMoney.setText(StringUtil.getInstance().getStringMoney(exchangeManager.getMoneyTotal(exchanges)));
    }

    @Click(R.id.llDetailStatistical)
    public void statisticalExchange() {
        StatisticalActivity_.intent(getContext())
                .mStatistical(Statistical.builder().year(mYear).type(Constant.STATISTICAL_YEAR).build())
                .start();
    }

}
