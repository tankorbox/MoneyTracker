package halo.com.moneytracker.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import halo.com.moneytracker.R;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.database.ExchangeManager;
import halo.com.moneytracker.fragments.dialogs.DeleteExchangeDialog;
import halo.com.moneytracker.fragments.dialogs.DeleteExchangeDialog_;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.utils.IconUtil;
import halo.com.moneytracker.utils.StringUtil;
import halo.com.moneytracker.utils.TimeUtil;

/**
 * Created by HoVanLy on 7/25/2016.
 */
@EActivity(R.layout.detail_exchange)
@OptionsMenu(R.menu.menu_detail_activity)
public class DetailExchangeActivity extends BaseActivity {
    @ViewById(R.id.tvNameAccount)
    TextView mTvNameAccount;
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.tvMoney)
    TextView mTvMoney;
    @ViewById(R.id.tvNote)
    TextView mTvNote;
    @ViewById(R.id.tvDate)
    TextView mTvDate;
    @ViewById(R.id.tvNameCategory)
    TextView mTvNameCategory;
    @ViewById(R.id.imgCategory)
    ImageView mImgCategory;
    private ExchangeManager mExchangeManager;
    private Exchange mExchange;
    private int mPosition;


    @AfterViews
    public void init() {
        mExchangeManager = new ExchangeManager();
        Intent intent = getIntent();
        mExchange = (Exchange) intent.getSerializableExtra(getString(R.string.detail_exchange));
        mPosition = intent.getIntExtra(getString(R.string.position_exchanges), -1);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.detail_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpViewExchange(mExchange);
    }

    @OptionsItem(R.id.action_edit)
    public void editExchange() {
        Intent intent = new Intent(this, AddOrUpdateExchangeActivity_.class);
        intent.putExtra(getString(R.string.serializable_exchange), mExchange);
        startActivityForResult(intent, Constant.RESULT_CODE_EDIT_EXCHANGE);
    }

    // Update database
    @OnActivityResult(Constant.RESULT_CODE_EDIT_EXCHANGE)
    void onResultEdit(int resultCode, Intent intent) {
        if (resultCode == Constant.RESULT_CODE_ADD_EXCHANGE) {
            mExchange = (Exchange) intent.getSerializableExtra(getString(R.string.serializable_exchange));
            setUpViewExchange(mExchange);
            mExchangeManager.addOrUpdateExchange(mExchange);
        }
    }

    @OptionsItem(R.id.action_delete)
    public void deleteExchange() {
        DeleteExchangeDialog deleteExchangeDialog = DeleteExchangeDialog_.builder()
                .mIdExchange(mExchange.getId())
                .mPosition(mPosition)
                .build();
        deleteExchangeDialog.show(getFragmentManager(), getString(R.string.dialog_tag));
    }

    @OptionsItem(android.R.id.home)
    public void backActivity() {
        Intent intent = new Intent();
        intent.putExtra(getString(R.string.position_exchanges), mPosition);
        intent.putExtra(getString(R.string.serializable_exchange), mExchange);
        setResult(Constant.RESULT_CODE_EDIT_EXCHANGE, intent);
        finish();
    }

    private void setUpViewExchange(Exchange exchange) {
        mImgCategory.setImageResource(IconUtil.getInstance().getIdResDrawable(exchange.getIdIconCategory()));
        mTvNameCategory.setText(exchange.getNameCategory());
        mTvMoney.setText(StringUtil.getInstance().getStringMoney(exchange.getMoney()));
        mTvNote.setText(exchange.getNote());
        mTvDate.setText(TimeUtil.getInstance().getShortStringTime(exchange.getCreatedDate()));
        mTvNameAccount.setText(exchange.getNameAccount());
    }

}
