package com.by5388.demo.common;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Administrator  on 2020/9/19.
 */
public class ErrorFragment extends Fragment {
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";

    public static Fragment newInstance() {
        return ErrorFragment.newInstance(null, null);
    }

    public static Fragment newInstance(@Nullable String title, @Nullable String message) {
        final ErrorFragment errorFragment = new ErrorFragment();
        final Bundle args = new Bundle();
        if (!TextUtils.isEmpty(title)) {
            args.putString(KEY_TITLE, title);
        }
        if (!TextUtils.isEmpty(message)) {
            args.putString(KEY_MESSAGE, message);
        }
        errorFragment.setArguments(args);
        return errorFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_error, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle arguments = getArguments();
        final String title;
        final String message;
        if (arguments != null) {
            final String stringTitle = arguments.getString(KEY_TITLE, null);
            if (TextUtils.isEmpty(stringTitle)) {
                title = getString(R.string.error_un_know);
            } else {
                title = stringTitle;
            }

            final String stringMessage = arguments.getString(KEY_MESSAGE, null);
            if (TextUtils.isEmpty(stringMessage)) {
                message = getString(R.string.something_error);
            } else {
                message = stringMessage;
            }
        } else {
            title = getString(R.string.error_un_know);
            message = getString(R.string.something_error);
        }


        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requireActivity().finish();
                    }
                })
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        requireActivity().finish();
                    }
                })
                .show();
    }
}
