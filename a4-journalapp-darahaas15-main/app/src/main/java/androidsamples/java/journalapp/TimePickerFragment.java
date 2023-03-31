package androidsamples.java.journalapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
  final Calendar c = Calendar.getInstance();

  @NonNull
  public static TimePickerFragment newInstance(Date time) {
    // TODO implement the method
    return null;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO implement the method
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // TODO implement the method
    int hr = c.get(Calendar.HOUR);
    int minute = c.get(Calendar.MINUTE);

    return new TimePickerDialog(getActivity(), TimePickerFragment.this, hr, minute, false);
  }

  @Override
  public void onTimeSet(TimePicker timePicker, int i, int i1) {
    c.set(Calendar.HOUR, i);
    c.set(Calendar.MINUTE, i1);
    String selectedDate = new SimpleDateFormat("HH:MM", Locale.ENGLISH).format(c.getTime());

    getTargetFragment().onActivityResult(
        getTargetRequestCode(),
        Activity.RESULT_OK,
        new Intent().putExtra("selectedDate", selectedDate));

  }
}
