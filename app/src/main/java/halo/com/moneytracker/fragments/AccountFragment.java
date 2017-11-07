package halo.com.moneytracker.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import halo.com.moneytracker.R;
import halo.com.moneytracker.adapters.AccountRecyclerAdapter;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.database.AccountManager;
import halo.com.moneytracker.models.Account;

/**
 * Copyright @2016 AsianTech Inc.
 * Created by HoVanLy on 8/4/2016.
 */
@EFragment(R.layout.account_fragment)
public class AccountFragment extends BaseFragment {
    @ViewById(R.id.recyclerViewAccount)
    RecyclerView mRecyclerViewAccount;
    private AccountManager mAccountManager;
    private List<Account> mAccounts;
    private AccountRecyclerAdapter mAccountRecyclerAdapter;

    @AfterViews
    public void init() {
        mAccountManager = new AccountManager();
        mAccounts = mAccountManager.getAccounts();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mAccountRecyclerAdapter = new AccountRecyclerAdapter(getContext(), mAccounts);
        mRecyclerViewAccount.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewAccount.setAdapter(mAccountRecyclerAdapter);
    }
    @OnActivityResult(Constant.REQUEST_CODE_DETAIL_ACCOUNT)
    public void resultDetailAccount() {

    }

}
