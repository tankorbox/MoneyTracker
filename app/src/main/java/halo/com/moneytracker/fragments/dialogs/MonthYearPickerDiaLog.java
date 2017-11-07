package halo.com.moneytracker.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;

import java.util.Calendar;
import java.util.List;

import halo.com.moneytracker.R;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.database.ExchangeManager;
import halo.com.moneytracker.evenbus.BusProvider;
import halo.com.moneytracker.evenbus.events.OttoExchanges;
import halo.com.moneytracker.models.Exchange;
import halo.com.moneytracker.utils.TimeUtil;

/**
 * Created by HoVanLy on 7/29/2016.
 */
@EFragment
public class MonthYearPickerDiaLog extends DialogFragment {
    private Calendar mCalendar;
    private TextView mTextView;
    private ExchangeManager mExchangeManager;

    public MonthYearPickerDiaLog() {
        mCalendar = Calendar.getInstance();
        mExchangeManager = new ExchangeManager();
    }

    public void setListenTextView(TextView textView) {
        this.mTextView = textView;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.month_year_picker, null);
        final NumberPicker mMonthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker mYearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);
        // Set value mMonth
        mMonthPicker.setMinValue(Constant.MIN_MONTH);
        mMonthPicker.setMaxValue(Constant.MAX_MONTH);
        mMonthPicker.setValue(mCalendar.get(Calendar.MONTH) + Constant.VALUE_PLUS_MONTH);
        // Set value mYear
        mYearPicker.setMinValue(Constant.MIN_YEAR);
        mYearPicker.setMaxValue(mCalendar.get(Calendar.YEAR) + Constant.VALUE_PLUS_YEAR);
        mYearPicker.setValue(mCalendar.get(Calendar.YEAR));

        builder.setView(dialog);
        builder.setPositiveButton(R.string.dialog_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                int month = mMonthPicker.getValue();
                int year = mYearPicker.getValue();
                mTextView.setText(String.format("%s %d", TimeUtil.getInstance().getNameMonth(month), year));
                sendOttoExchange(month, year);
            }
        })
                .setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    private void sendOttoExchange(int month, int year) {
        List<Exchange> exchanges = mExchangeManager.getExchangeMonths(month, year);
        BusProvider.getInstance().post(OttoExchanges.builder().month(month).year(year).exchanges(exchanges).build());
    }

}
