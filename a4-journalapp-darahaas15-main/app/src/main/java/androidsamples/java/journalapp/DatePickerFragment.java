package androidsamples.java.journalapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
  final Calendar c = Calendar.getInstance();

  @NonNull
  public static DatePickerFragment newInstance(Date date) {
    // TODO implement the method
    return newInstance(date);
  }

  @Override
  public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
    c.set(Calendar.YEAR, i);
    c.set(Calendar.MONTH, i1);
    c.set(Calendar.DAY_OF_MONTH, i2);
    String selectedDate = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.ENGLISH).format(c.getTime());

    getTargetFragment().onActivityResult(
        getTargetRequestCode(),
        Activity.RESULT_OK,
        new Intent().putExtra("selectedDate", selectedDate));

  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    int hr = c.get(Calendar.YEAR);
    int minute = c.get(Calendar.MONTH);
    int day_of_month = c.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(getActivity(), DatePickerFragment.this, hr, minute, day_of_month);
  }
}
