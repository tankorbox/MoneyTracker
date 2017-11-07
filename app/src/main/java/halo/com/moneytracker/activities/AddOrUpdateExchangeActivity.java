package halo.com.moneytracker.activities;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import halo.com.moneytracker.R;
import halo.com.moneytracker.adapters.CategoryRecyclerAdapter;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.database.AccountManager;
import halo.com.moneytracker.database.CategoryManager;
import halo.com.moneytracker.fragments.dialogs.DayPickerAddDialog;
import halo.com.moneytracker.fragments.dialogs.DayPickerAddDialog_;
import halo.com.moneytracker.models.Account;
import halo.com.moneytracker.models.Category;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.recyclers.ClickItemRecyclerView;
import halo.com.moneytracker.recyclers.IClickItemRecyclerView;
import halo.com.moneytracker.utils.IconUtil;
import halo.com.moneytracker.utils.TimeUtil;

/**
 * Created by HoVanLy on 7/25/2016.
 */
@EActivity(R.layout.add_exchange)
@OptionsMenu(R.menu.menu_add_activity)
public class AddOrUpdateExchangeActivity extends BaseActivity {
    @ViewById(R.id.toolbar)
    Toolbar mToolbar;
    @ViewById(R.id.edtMoney)
    EditText mEdtMoney;
    @ViewById(R.id.edtNote)
    EditText mEdtNote;
    @ViewById(R.id.tvDate)
    TextView mTvDate;
    @ViewById(R.id.recyclerViewCategory)
    RecyclerView mRecyclerView;
    @ViewById(R.id.imgCategory)
    ImageView mImgCategory;
    @ViewById(R.id.tvNameCategory)
    TextView mTvNameCategory;
    @ViewById(R.id.spinnerAccount)
    Spinner mSpinnerAccount;
    private CategoryRecyclerAdapter mAdapter;
    private List<Category> mCategories;
    private CategoryManager mCategoryManager;
    private Exchange mExchange;
    private AccountManager mAccountManager;
    private List<Account> mAccounts;
    private boolean mIsEdit = false;
    private long mMoneyCurrent = 0;
    private String mActionAddPlan;
    private String mNameAccount;
    private String mCurrentNameAccount;


    @AfterViews
    public void innit() {
        mCategoryManager = new CategoryManager();
        mAccountManager = new AccountManager();
        mCategories = mCategoryManager.getCategories();
        mAccounts = mAccountManager.getAccounts();
        loadSpinnerAccount();
        Intent intent = getIntent();
        mExchange = (Exchange) intent.getSerializableExtra(getString(R.string.serializable_exchange));
        mActionAddPlan = intent.getStringExtra(getString(R.string.add_exchange_plan));
        loadRecyclerView(mCategories);
        setSupportActionBar(mToolbar);
        //noinspection ConstantConditions,ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // if Edit
        if (mExchange != null) {
            mIsEdit = true;
            mEdtMoney.setText(String.valueOf(mExchange.getMoney()));
            mEdtNote.setText(mExchange.getNote());
            mTvDate.setText(TimeUtil.getInstance().getShortStringTime(mExchange.getCreatedDate()));
            mMoneyCurrent = mExchange.getMoney();
            mCurrentNameAccount = mExchange.getNameAccount();
            mSpinnerAccount.setSelection(getPosition(mCurrentNameAccount));
            mTvNameCategory.setText(mExchange.getNameCategory());
            mCurrentNameAccount = mExchange.getNameAccount();
            Glide.with(getApplicationContext()).load(IconUtil.getInstance()
                    .getIdResDrawable(mExchange.getIdIconCategory())).into(mImgCategory);
        } else {
            // if Add
            mTvDate.setText(TimeUtil.getInstance().getShortStringTime(new Date()));
            mExchange = new Exchange();
            mExchange.setTraded(mActionAddPlan == null);
        }
        handlerDatePicker(mTvDate);
    }

    @OptionsItem(R.id.menu_add_Exchange)
    public void updateExchange() {
        String money = mEdtMoney.getText().toString();
        String date = mTvDate.getText().toString();
        String note = mEdtNote.getText().toString();
        String nameCategory = mTvNameCategory.getText().toString();
        if (isValidate(money, nameCategory)) {
            mExchange.setCreatedDate(TimeUtil.getInstance().getDate(date));
            mExchange.setMoney(Long.parseLong(money));
            mExchange.setNote(note);
            mExchange.setNameCategory(nameCategory);
            mExchange.setNameAccount(mNameAccount);
            // if add
            if (!mIsEdit) {
                mExchange.setId(UUID.randomUUID().toString());
                mAccountManager.addMoneyExchange(mNameAccount, Long.parseLong(money));
            } else {
                // if edit
                if (TextUtils.equals(mCurrentNameAccount, mNameAccount)) {
                    mAccountManager.addMoneyExchange(mNameAccount, Long.parseLong(money) - mMoneyCurrent);
                } else {
                    mAccountManager.addMoneyExchange(mCurrentNameAccount, -mMoneyCurrent);
                    mAccountManager.addMoneyExchange(mNameAccount, Long.parseLong(money));
                }
            }
            // Check add plan
            if (!isValidateAddPlan(TimeUtil.getInstance().getDate(date)) && (mActionAddPlan != null)) {
                Toast.makeText(this, R.string.dates_future, Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent();
            intent.putExtra(getString(R.string.serializable_exchange), mExchange);
            setResult(Constant.RESULT_CODE_ADD_EXCHANGE, intent);
            finish();
        } else {
            Toast.makeText(this, R.string.fail_input, Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isValidateAddPlan(Date futureDate) {
        return new Date().before(futureDate);
    }

    public boolean isValidate(String money, String category) {
        return !TextUtils.isEmpty(money) && !TextUtils.isEmpty(category) && (Long.parseLong(money) > 0);
    }

    @Background
    public void loadRecyclerView(List<Category> categories) {
        setUiRecyclerView(categories);
    }

    @UiThread()
    public void setUiRecyclerView(final List<Category> categories) {
        mAdapter = new CategoryRecyclerAdapter(this, categories);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new ClickItemRecyclerView(this, mRecyclerView, new IClickItemRecyclerView() {
            @Override
            public void onClick(View view, int position) {
                mExchange.setIdIconCategory(categories.get(position).getIdICon());
                mTvNameCategory.setText(categories.get(position).getName());
                Glide.with(getApplicationContext()).load(IconUtil.getInstance()
                        .getIdResDrawable(categories.get(position).getIdICon())).into(mImgCategory);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    public void handlerDatePicker(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DayPickerAddDialog dayPickerDialog = new DayPickerAddDialog_();
                dayPickerDialog.setListenTextView(v);
                dayPickerDialog.show(getSupportFragmentManager(), getString(R.string.dialog_tag));
            }
        });
    }

    private void loadSpinnerAccount() {
        List<String> strings = new ArrayList<>();
        for (Account account : mAccounts) {
            strings.add(account.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAccount.setAdapter(adapter);
        mSpinnerAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNameAccount = mAccounts.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private int getPosition(String nameAccount) {
        int size = mAccounts.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(mAccounts.get(i).getName(), nameAccount)) {
                return i;
            }
        }
        return 0;
    }
}
