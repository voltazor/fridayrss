package com.fridayrss.general.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.fridayrss.R;
import com.fridayrss.base.BaseDialogFragment;

public class ProgressDialogFragment extends BaseDialogFragment {

    private DialogInterface.OnCancelListener cancelListener;

    public static ProgressDialogFragment newInstance() {
        return new ProgressDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.please_wait));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setIndeterminate(true);
        return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelListener != null) {
            cancelListener.onCancel(dialog);
        }
    }

    public ProgressDialogFragment setCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.cancelListener = onCancelListener;
        return this;
    }

    @Override
    public String getFragmentTag() {
        return ProgressDialogFragment.class.getSimpleName();
    }

}
