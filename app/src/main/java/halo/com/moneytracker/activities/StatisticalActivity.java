package halo.com.moneytracker.activities;

import android.text.TextUtils;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import halo.com.moneytracker.R;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.database.CategoryManager;
import halo.com.moneytracker.database.ExchangeManager;
import halo.com.moneytracker.models.Category;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.models.Statistical;
import halo.com.moneytracker.utils.StringUtil;
import halo.com.moneytracker.utils.TimeUtil;

/**
 * Created by HoVanLy on 8/2/2016.
 */
@EActivity(R.layout.statistical_exchange)
public class StatisticalActivity extends BaseActivity {
    @ViewById(R.id.pieChartExchange)
    PieChart mPieChartExchange;
    @ViewById(R.id.tvTimeView)
    TextView mTvTimeView;
    @Extra
    Statistical mStatistical;
    private Date mTime;
    private ExchangeManager mExchangeManager;
    private CategoryManager mCategoryManager;
    private List<Exchange> exchanges;
    private List<Category> categories;
    private long mTotalMoney;
    private int mYear;
    private int mMonth;


    @AfterViews
    public void init() {
        mExchangeManager = new ExchangeManager();
        mCategoryManager = new CategoryManager();
        categories = mCategoryManager.getCategories();
        mTime = mStatistical.getDate();
        mYear = mStatistical.getYear();
        mMonth = mStatistical.getMonth();
        switch (mStatistical.getType()) {
            case Constant.STATISTICAL_DAY:
                exchanges = getExchangeDays();
                setTextDay(mTime);
                break;
            case Constant.STATISTICAL_MONTH:
                exchanges = mExchangeManager.getExchangeMonths(mMonth, mYear);
                setTextMonthYear(mMonth, mYear);
                break;
            case Constant.STATISTICAL_YEAR:
                exchanges = mExchangeManager.getExchangeYears(mYear);
                setTextYear(mYear);
                break;
        }
        mTotalMoney = mExchangeManager.getMoneyTotal(exchanges);
        showPieChart();
    }

    private void setTextDay(Date date) {
        String nameMonth = TimeUtil.getInstance().getNameMonth(date);
        int mDay = TimeUtil.getInstance().getNumberDay(date);
        int mYear = TimeUtil.getInstance().getNumberYear(date);
        mTvTimeView.setText(String.format("%s %d %d", nameMonth, mDay, mYear));
    }

    private void setTextMonthYear(int month, int year) {
        String nameMonth = String.valueOf(TimeUtil.getInstance().getNameMonth(month));
        mTvTimeView.setText(String.format("%s %d", nameMonth, year));
    }

    private void setTextYear(int year) {
        mTvTimeView.setText(String.format("%s", year));
    }

    private List<Exchange> getExchangeDays() {
        String dateStringDay = TimeUtil.getInstance().getShortStringTime(mTime);
        Date dateConvert = TimeUtil.getInstance().getDate(dateStringDay);
        return mExchangeManager.getExchangeDays(dateConvert);
    }

    public void showPieChart() {
        List<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList();
        for (Category category : categories) {
            for (Exchange exchange : exchanges) {
                if (TextUtils.equals(category.getName(), exchange.getNameCategory())) {
                    labels.add(category.getName());
                    break;
                }
            }
        }
        //
        for (int i = 0; i < labels.size(); i++) {
            long money = 0;
            for (Exchange exchange : exchanges) {
                if (TextUtils.equals(labels.get(i), exchange.getNameCategory())) {
                    money += exchange.getMoney();
                }
            }
            entries.add(new Entry((float) money, i));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, getString(R.string.chart_text_category_hub));
        PieData pieData = new PieData(labels, pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        mPieChartExchange.setData(pieData);
        mPieChartExchange.setCenterText(StringUtil.getInstance().getStringMoney(mTotalMoney));
        mPieChartExchange.animateY(5000);
    }
}
