package androidsamples.java.journalapp;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class JournalRoomDatabaseTest {
    @Rule
    InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    private JournalEntryDao jdao;
    private JournalRoomDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, JournalRoomDatabase.class).build();
        jdao = db.journalEntryDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        UUID mUid;
        final JournalEntry[] t1 = new JournalEntry[1];
        CountDownLatch latch = new CountDownLatch(1);
        JournalEntry entry = new JournalEntry("Test", "11:00", "12:00", "THU, NOV 10, 2022");
        mUid = entry.getUid();
        jdao.insert(entry);
        MutableLiveData<JournalEntry> jld = new MutableLiveData<>();
        jld.postValue(entry);
        Log.d(TAG, "writeUserAndReadInList: " + mUid);
        Observer<JournalEntry> ob = new Observer<JournalEntry>() {
            @Override
            public void onChanged(JournalEntry journalEntry) {
                t1[0] = journalEntry;
            }
        };
        jdao.getEntry(mUid).observeForever(ob);
        latch.await(5, TimeUnit.SECONDS);
        Log.d(TAG, "writeUserAndReadInList: " + t1[0].mUid);
        JournalEntry t2 = jld.getValue();
        latch.await(2, TimeUnit.SECONDS);
        assertEquals(t1[0], t2);
    }
}