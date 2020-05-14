package com.jedmay.simpledraft.db;

import android.content.Context;
import android.icu.util.Output;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jedmay.simpledraft.dao.OutputStateDao;
import com.jedmay.simpledraft.dao.UserSettingsDao;
import com.jedmay.simpledraft.model.OutputState;
import com.jedmay.simpledraft.model.UserSettings;

import java.util.ArrayList;
import java.util.List;
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
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SimpleDraftDb.class, "SimpleDraftDb").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
      @Override
      public void onOpen(@NonNull SupportSQLiteDatabase db) {
          super.onOpen(db);

          databaseWriteExecutor.execute(() -> {
              UserSettingsDao userSettingsDao = INSTANCE.userSettingsDao();
              userSettingsDao.deleteAll();

              UserSettings settings = new UserSettings();
              settings.setName("Test Name");
              userSettingsDao.insert(settings);

              OutputStateDao outputStateDao = INSTANCE.outputStateDao();
              outputStateDao.deleteAll();

              OutputState state = new OutputState();
              state.setName("K19J0208");
              List<Double> list = new ArrayList<>();
              list.add(12.0204);
              list.add(-11.0800);
              list.add(13.0911);

              state.setValues(list);
              outputStateDao.insert(state);
          });
      }
    };
}
