package halo.com.moneytracker.activities;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import halo.com.moneytracker.R;
import halo.com.moneytracker.models.Account;
import halo.com.moneytracker.utils.StringUtil;

/**
 * Created by HoVanLy on 8/5/2016.
 */
@EActivity(R.layout.detail_account)
@OptionsMenu(R.menu.menu_detail_account)
public class DetailAccountActivity extends BaseActivity {
    private Account mAccount;
    @ViewById(R.id.tvNameAccount)
    TextView mTvNameAccount;
    @ViewById(R.id.tvStartMoney)
    TextView mTvStartMoney;
    @ViewById(R.id.tvExchangeMoney)
    TextView mTvExchangeMoney;
    @ViewById(R.id.tvAvailableMoney)
    TextView mTvAvailableMoney;
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @AfterViews
    public void innit() {
        mAccount = getIntentAccount();
        loadViewAccount(mAccount);
    }

    private void loadViewAccount(Account account) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTvNameAccount.setText(account.getName());
        mTvStartMoney.setText(StringUtil.getInstance().getStringMoney(account.getAccountMoney()));
        mTvExchangeMoney.setText(StringUtil.getInstance().getStringMoney(account.getExchangeMoney()));
        mTvAvailableMoney.setText(StringUtil.getInstance().getStringMoney(account.getAccountMoney()-account.getExchangeMoney()));
    }

    public Account getIntentAccount() {
        return (Account) getIntent().getSerializableExtra(getString(R.string.serializableExtra_account));
    }
    @OptionsItem(android.R.id.home)
    public void backActivity() {
        finish();
    }
}
