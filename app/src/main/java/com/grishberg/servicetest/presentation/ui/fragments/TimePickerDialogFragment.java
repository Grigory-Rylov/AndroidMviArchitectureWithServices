package com.grishberg.servicetest.presentation.ui.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by grishberg on 02.04.17.
 */

public class TimePickerDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public static final String TIME_PICKER_STATE = "TIME_PICKER_STATE";
    private TimePickerDialog timePickerDialog;
    @Nullable
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        timePickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        if (savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle(TIME_PICKER_STATE);
            timePickerDialog.onRestoreInstanceState(bundle);
        }
        return timePickerDialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle pickerState = timePickerDialog.onSaveInstanceState();
        outState.putBundle(TIME_PICKER_STATE, pickerState);
    }

    public void setOnTimeSetListener(@Nullable TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        this.onTimeSetListener = onTimeSetListener;
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (onTimeSetListener != null) {
            onTimeSetListener.onTimeSet(view, hourOfDay, minute);
        }
    }
}
