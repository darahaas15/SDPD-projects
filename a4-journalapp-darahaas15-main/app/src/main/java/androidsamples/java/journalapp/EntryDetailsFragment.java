package androidsamples.java.journalapp;

import static android.content.ContentValues.TAG;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {
    private EntryDetailsViewModel mEntryDetailsViewModel;
    private JournalEntry mEntry;
    private EditText mEditTitle;
    private Button mDate;
    private Button mStartTime;
    private Button mEndTime;
    private String startStr;
    private String endStr;
    private String dateStr;
    private String titleStr;
    private UUID entryId;
    private static final String DIALOG_SHOW_1 = "Date";
    private static final String DIALOG_SHOW_2 = "Start_Time";
    private static final String DIALOG_SHOW_3 = "End_Time";
    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODE2 = 2;
    public static final int REQUEST_CODE3 = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mEntryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);
        assert getArguments() != null;
        entryId = EntryDetailsFragmentArgs.fromBundle(getArguments()).getEntryId();
        // Log.d("myDebugging", "Loading: " + entryId);
        if (entryId != null)
            mEntryDetailsViewModel.loadEntry(entryId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_entry_details, container, false);
        mEditTitle = view1.findViewById(R.id.edit_title);
        mDate = view1.findViewById(R.id.btn_entry_date);
        mStartTime = view1.findViewById(R.id.btn_start_time);
        mEndTime = view1.findViewById(R.id.btn_end_time);
        Button mSave = view1.findViewById(R.id.btn_save);
        dateStr = EntryDetailsFragmentArgs.fromBundle(getArguments()).getSelectedDate();
        startStr = EntryDetailsFragmentArgs.fromBundle(getArguments()).getStartTime();
        endStr = EntryDetailsFragmentArgs.fromBundle(getArguments()).getEndTime();
        titleStr = EntryDetailsFragmentArgs.fromBundle(getArguments()).getTitle();
        mDate.setOnClickListener(view -> {
            DatePickerFragment d = new DatePickerFragment();
            d.setTargetFragment(EntryDetailsFragment.this, REQUEST_CODE);
            d.show(getFragmentManager(), DIALOG_SHOW_1);
        });

        mStartTime.setOnClickListener(view -> {
            TimePickerFragment t = new TimePickerFragment();
            t.setTargetFragment(EntryDetailsFragment.this, REQUEST_CODE2);
            t.show(getFragmentManager(), DIALOG_SHOW_2);
        });

        mEndTime.setOnClickListener(view -> {
            TimePickerFragment t = new TimePickerFragment();
            t.setTargetFragment(EntryDetailsFragment.this, REQUEST_CODE3);
            t.show(getFragmentManager(), DIALOG_SHOW_3);
        });

        mSave.setOnClickListener(view -> {
            if (mDate.getText().toString().equals("Date") || mStartTime.getText().toString().equals("Start Time")
                    || mEndTime.getText().toString().equals("End Time")) {
                Toast.makeText(getActivity(), "Enter all fields!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    saveEntry(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getFragmentManager().popBackStackImmediate();
            }
        });

        return view1;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.entry_details_actionbar, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                AlertDialog dialog = new AlertDialog.Builder(requireContext())
                        .setTitle("Delete Entry")
                        .setMessage(R.string.delete_entry)
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                            mEntryDetailsViewModel.deleteEntry(mEntry);
                            requireActivity().onBackPressed();
                        }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss()).create();
                dialog.show();
                return true;

            case R.id.share_action:
                String s = "Look what I have been up to: " + mEditTitle.getText().toString() + " on " + dateStr + ", "
                        + startStr + " to " + endStr;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                // type of the content to be shared
                sharingIntent.setType("text/plain");

                // Body of the content
                String shareBody = s;

                // subject of the content. you can share anything
                String shareSubject = "Share";

                // passing body of the content
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

                // passing subject of the content
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (entryId != null) {
            Log.d(TAG, "onViewCreated: " + entryId);
            mEntryDetailsViewModel.getEntryLiveData().observe(requireActivity(), entry -> {
                this.mEntry = entry;
                updateUI();
            });
        } else {
            this.mEntry = new JournalEntry("", "", "", "");
            updateUI();
        }
    }

    private void saveEntry(View v) {
        mEntry.setTitle(mEditTitle.getText().toString());
        dateStr = mEditTitle.getText().toString();
        String d1 = mStartTime.getText().toString();
        String d2 = mDate.getText().toString();
        String d3 = mEndTime.getText().toString();
        mEntry.setStartTime(d1);
        mEntry.setDate(d2);
        mEntry.setEndTime(d3);
        if (EntryDetailsFragmentArgs.fromBundle(getArguments()).getAdded()) {
            mEntryDetailsViewModel.addNewEntry(mEntry);
        } else {
            mEntryDetailsViewModel.saveEntry(mEntry);
        }
        requireActivity().onBackPressed();
    }

    public void updateUI() {
        if (titleStr == null || titleStr.equals(""))
            titleStr = "End Time";
        mEditTitle.setText(titleStr);
        if (dateStr == null || dateStr.equals(""))
            dateStr = "Date";
        mDate.setText(dateStr);
        if (startStr == null || startStr.equals(""))
            startStr = "Start Time";
        mStartTime.setText(startStr);
        if (endStr == null || endStr.equals(""))
            endStr = "End Time";
        mEndTime.setText(endStr);
        Log.d(TAG, "date: " + dateStr);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            dateStr = data.getStringExtra("selectedDate");
        }
        if (requestCode == REQUEST_CODE2 && resultCode == Activity.RESULT_OK) {
            startStr = data.getStringExtra("selectedDate");
        }
        if (requestCode == REQUEST_CODE3 && resultCode == Activity.RESULT_OK) {
            endStr = data.getStringExtra("selectedDate");
        }
        updateUI();
    }
}