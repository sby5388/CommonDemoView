package com.by5388.demo.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


/**
 * @author Administrator  on 2020/11/28.
 */
public class ErrorDialog extends DialogFragment {
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_CLOSE_ACTIVITY = "close";

    public ErrorDialog() {
    }

    public static DialogFragment newInstance(String title, String message, boolean closeActivity) {
        final ErrorDialog errorDialog = new ErrorDialog();
        final Bundle bundle = new Bundle();
        errorDialog.setArguments(bundle);
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_MESSAGE, message);
        bundle.putBoolean(KEY_CLOSE_ACTIVITY, closeActivity);
        return errorDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Bundle arguments = getArguments();
        final String title;
        final String message;
        final boolean closeActivity;
        if (arguments == null) {
            title = getString(R.string.error_un_know);
            message = getString(R.string.something_error);
            closeActivity = false;
        } else {
            title = arguments.getString(KEY_TITLE, getString(R.string.error_un_know));
            message = arguments.getString(KEY_MESSAGE, getString(R.string.something_error));
            closeActivity = arguments.getBoolean(KEY_CLOSE_ACTIVITY, false);
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (closeActivity) {
                            requireActivity().finish();
                        }
                    }
                })
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (closeActivity) {
                            requireActivity().finish();
                        }
                    }
                })
                .create();

    }
}
