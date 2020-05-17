package com.jedmay.simpledraft.helper;

import android.app.AlertDialog;
import android.content.Context;

import com.jedmay.simpledraft.db.SimpleDraftDbBadCompany;
import com.jedmay.simpledraft.model.OutputState;

public class AlertHelper {

    public static void deleteAlert(OutputState state, SimpleDraftDbBadCompany db, Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to delete this entry?");
        alertDialogBuilder.setPositiveButton("Delete",
                (arg0, arg1) -> db.outputStateDao().delete(state));
        alertDialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
            //do nothing
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
