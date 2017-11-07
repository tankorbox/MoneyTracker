package halo.com.moneytracker.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import halo.com.moneytracker.R;
import halo.com.moneytracker.common.Constant;
import halo.com.moneytracker.database.ExchangeManager;

/**
 * Created by HoVanLy on 7/30/2016.
 */
@EFragment
public class DeleteExchangeDialog extends DialogFragment {
    ExchangeManager mExchangeManager;
    @FragmentArg
    String mIdExchange;
    @FragmentArg
    int mPosition;

    public DeleteExchangeDialog() {
        mExchangeManager = new ExchangeManager();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.question_delete)
                .setPositiveButton(getString(R.string.dialog_button_cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.dialog_button_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mExchangeManager.deleteExchange(mIdExchange);
                        Intent intent = new Intent();
                        intent.putExtra(getString(R.string.position_exchanges), mPosition);
                        getActivity().setResult(Constant.RESULT_CODE_DELETE_EXCHANGE, intent);
                        getActivity().finish();
                    }
                });
        return builder.create();
    }

}
