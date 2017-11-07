package halo.com.moneytracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import halo.com.moneytracker.R;
import halo.com.moneytracker.models.Category;
import halo.com.moneytracker.utils.IconUtil;

/**
 * Created by HoVanLy on 7/27/2016.
 */

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.MyViewHolder> {
    private final Context mContext;
    private final List<Category> mCategories;
    private int mIdIcon;

    public CategoryRecyclerAdapter(Context context, List<Category> categories) {
        this.mContext = context;
        this.mCategories = categories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvNameCategory.setText(mCategories.get(position).getName());
        mIdIcon = mCategories.get(position).getIdICon();
        Glide.with(mContext).load(IconUtil.getInstance().getIdResDrawable(mIdIcon)).into(holder.mImgCategory);
    }

    @Override
    public int getItemCount() {
        return (mCategories == null) ? 0 : mCategories.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgCategory;
        private final TextView mTvNameCategory;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImgCategory = (ImageView) itemView.findViewById(R.id.imgCategory);
            mTvNameCategory = (TextView) itemView.findViewById(R.id.tvNameCategory);
        }
    }
}
