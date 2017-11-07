package halo.com.moneytracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import halo.com.moneytracker.R;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.utils.IconUtil;
import halo.com.moneytracker.utils.StringUtil;
import halo.com.moneytracker.utils.TimeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by HoVanLy on 8/1/2016.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ExchangeRecyclerYearsAdapter extends RecyclerView.Adapter<ExchangeRecyclerYearsAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<Exchange> mExchanges;
    private int mIdIcon;

    public ExchangeRecyclerYearsAdapter(Context context, List<Exchange> exchanges) {
        this.mContext = context;
        this.mExchanges = exchanges;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_month_year_exchange, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvtMoney.setText(StringUtil.getInstance().getStringMoney(mExchanges.get(position).getMoney()));
        holder.mTvNote.setText(mExchanges.get(position).getNote());
        holder.mTvCategory.setText(mExchanges.get(position).getNameCategory());
        Date created = mExchanges.get(position).getCreatedDate();
        int day = TimeUtil.getInstance().getNumberDay(created);
        String nameMonth = TimeUtil.getInstance().getNameMonth(created);
        holder.mTvTime.setText(String.format("%s %d", nameMonth, day));
        mIdIcon = mExchanges.get(position).getIdIconCategory();
        Glide.with(mContext).load(IconUtil.getInstance().getIdResDrawable(mIdIcon)).into(holder.mImgCategory);
    }

    @Override
    public int getItemCount() {
        return (mExchanges == null) ? 0 : mExchanges.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvtMoney;
        private final TextView mTvCategory;
        private final TextView mTvNote;
        private final ImageView mImgCategory;
        private final TextView mTvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvTime = (TextView) itemView.findViewById(R.id.tvTime);
            mTvtMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            mTvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            mTvNote = (TextView) itemView.findViewById(R.id.tvNote);
            mImgCategory = (ImageView) itemView.findViewById(R.id.imgCategory);
        }
    }
}
