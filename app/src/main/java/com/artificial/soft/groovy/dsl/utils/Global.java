package com.artificial.soft.groovy.dsl.utils;

import android.content.Context;

public class Global {

    public static void showDialog(Context context, int drawable, String title, String message){
        new android.app.AlertDialog.Builder(context)
                .setIcon(drawable)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Okay", (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }
}
