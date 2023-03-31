package androidsamples.java.journalapp;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JournalRepository implements JournalEntryDao{
    private static final String DATABASE_NAME = "journal_table";
    private final JournalEntryDao mJournalEntryDao;
    private static JournalRepository sInstance;
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    public JournalRepository(Context context) {
        JournalRoomDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                JournalRoomDatabase.class,DATABASE_NAME).build();
        mJournalEntryDao = db.journalEntryDao();
    }

    public static void init(Context context) {
        if (sInstance == null){
            sInstance = new JournalRepository(context);
        }
    }

    public static JournalRepository getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Repository must be initialized");
        }
        return sInstance;
    }

    public void insert(JournalEntry entry) {
        mExecutor.execute(() -> mJournalEntryDao.insert(entry));
    }

    public LiveData<List<JournalEntry>> getAllEntries() {
        return mJournalEntryDao.getAllEntries();
    }

    public void update(JournalEntry entry) {
        mExecutor.execute(() -> mJournalEntryDao.update(entry));
    }

    public LiveData<JournalEntry> getEntry(UUID id) {
        return mJournalEntryDao.getEntry(id);
    }

    public void delete(JournalEntry entry) {
        mExecutor.execute(() -> mJournalEntryDao.delete(entry));
    }
}