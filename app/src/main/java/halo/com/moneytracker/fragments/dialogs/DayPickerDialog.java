package halo.com.moneytracker.fragments.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import halo.com.moneytracker.database.ExchangeManager;
import halo.com.moneytracker.evenbus.BusProvider;
import halo.com.moneytracker.evenbus.events.OttoExchanges;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.utils.TimeUtil;

/**
 * Created by HoVanLy on 4/23/2016.
 */
@EFragment
public class DayPickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private TextView mTextView;
    private ExchangeManager mExchangeManager;

    public DayPickerDialog() {
        mExchangeManager = new ExchangeManager();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month, day);
        return datePickerDialog;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String nameMonth = TimeUtil.getInstance().getNameMonth(monthOfYear);
        String viewDay = String.format("%s %d %d", nameMonth, dayOfMonth, year);
        mTextView.setText(viewDay);
        Date date = getDay(dayOfMonth, monthOfYear, year);
        List<Exchange> mExchanges = mExchangeManager.getExchangeDays(date);
        BusProvider.getInstance().post(OttoExchanges.builder().exchanges(mExchanges).createdDate(date).build());
    }

    public void setListenTextView(View view) {
        this.mTextView = (TextView) view;
    }

    public Date getDay(int day, int month, int year) {
        String dateString = String.format("%d%s%d%s%d", day, "/", month, "/", year);
        return TimeUtil.getInstance().getDate(dateString);
    }

}
