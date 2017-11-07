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

import halo.com.moneytracker.R;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.database.ExchangeManager;
import halo.com.moneytracker.evenbus.BusProvider;
import halo.com.moneytracker.evenbus.events.OttoExchanges;

/**
 * Created by HoVanLy on 7/28/2016.
 */
@EFragment
public class YearPickerDialog extends DialogFragment {
    private Calendar mCalendar;
    private TextView mTextView;
    private ExchangeManager mExchangeManager;

    public YearPickerDialog() {
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
        View dialog = inflater.inflate(R.layout.year_picker, null);
        final NumberPicker mYearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);
        // Set value mYear
        mYearPicker.setMinValue(Constant.MIN_YEAR);
        mYearPicker.setMaxValue(mCalendar.get(Calendar.YEAR) + Constant.VALUE_PLUS_YEAR);
        mYearPicker.setValue(mCalendar.get(Calendar.YEAR));
        builder.setView(dialog)
                .setPositiveButton(getString(R.string.dialog_button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int year = mYearPicker.getValue();
                        mTextView.setText(String.valueOf(year));
                        postOtto(year);
                    }
                })
                .setNegativeButton(getString(R.string.dialog_button_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    private void postOtto(int year) {
        BusProvider.getInstance().post(OttoExchanges.builder().year(year).exchanges(mExchangeManager.getExchangeYears(year)).build());
    }
}
