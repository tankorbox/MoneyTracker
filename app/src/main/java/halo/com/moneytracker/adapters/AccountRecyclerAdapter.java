package halo.com.moneytracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import halo.com.moneytracker.R;
import halo.com.moneytracker.models.Account;
import halo.com.moneytracker.utils.StringUtil;

/**
 * Created by HoVanLy on 8/4/2016.
 */
public class AccountRecyclerAdapter extends RecyclerView.Adapter<AccountRecyclerAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<Account> mAccounts;

    public AccountRecyclerAdapter(Context mContext, List<Account> mAccounts) {
        this.mContext = mContext;
        this.mAccounts = mAccounts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_account, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvNameAccount.setText(mAccounts.get(position).getName());
        holder.mTvStartMoney.setText(StringUtil.getInstance().getStringMoney(mAccounts.get(position).getAccountMoney()));
        holder.mTvExchangeMoney.setText(StringUtil.getInstance().getStringMoney(mAccounts.get(position).getExchangeMoney()));
        holder.mTvAvailableMoney.setText(StringUtil.getInstance().getStringMoney(mAccounts.get(position).getAccountMoney()
                - mAccounts.get(position).getExchangeMoney()));
    }
    @Override
    public int getItemCount() {
        return (mAccounts == null) ? 0 : mAccounts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvNameAccount;
        private final TextView mTvStartMoney;
        private final TextView mTvExchangeMoney;
        private final TextView mTvAvailableMoney;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvStartMoney = (TextView) itemView.findViewById(R.id.tvStartMoney);
            mTvNameAccount = (TextView) itemView.findViewById(R.id.tvNameAccount);
            mTvExchangeMoney = (TextView) itemView.findViewById(R.id.tvExchangeMoney);
            mTvAvailableMoney = (TextView) itemView.findViewById(R.id.tvAvailableMoney);
        }
    }
}
