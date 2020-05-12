package com.jedmay.simpledraft.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jedmay.simpledraft.dao.OutputStateDao;
import com.jedmay.simpledraft.dao.UserSettingsDao;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.model.UserSettings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {OutputState.class, UserSettings.class}, version = 1, exportSchema = false)
public abstract class SimpleDraftDb extends RoomDatabase {

    public abstract OutputStateDao outputStateDao();
    public abstract UserSettingsDao userSettingsDao();

    private static volatile SimpleDraftDb INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static SimpleDraftDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SimpleDraftDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SimpleDraftDb.class, "SimpleDraftDb").build();
                }
            }
        }
        return INSTANCE;
    }
}
