package halo.com.moneytracker.fragments.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;

import java.util.Calendar;

/**
 * Created by HoVanLy on 4/23/2016.
 */
@EFragment
public class DayPickerAddDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    TextView mTextView;
    private DatePickerDialog mDatePickerDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mDatePickerDialog = new DatePickerDialog(getContext(), this, year, month, day);
        return mDatePickerDialog;
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear++;
        String viewDay = String.format("%d%s%d%s%d", dayOfMonth, "/", monthOfYear, "/", year);
        mTextView.setText(viewDay);

    }

    public void setListenTextView(View view) {
        this.mTextView = (TextView) view;
    }

}
