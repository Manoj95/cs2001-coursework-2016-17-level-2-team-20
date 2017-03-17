package g20.brunelplanner.views.activities.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class WeekDialogFragment extends DialogFragment {

    int currentWeek;

    public WeekDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    // Defines the listener interface
    public interface WeekDialogListener {
        void onFinishWeekDialog(int week);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] weekList = new String[52];

        for (int i = 0; i < 52; i++) {
            weekList[i] = (String.valueOf(i));
        }

        builder.setTitle("Select Week")
                .setItems(weekList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendBackResult(which);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("Dialog", "onClick: Cancel");
                    }
                });
//        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void sendBackResult(int week) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        WeekDialogListener listener = (WeekDialogListener) getTargetFragment();
        listener.onFinishWeekDialog(week);
        dismiss();
    }


}
