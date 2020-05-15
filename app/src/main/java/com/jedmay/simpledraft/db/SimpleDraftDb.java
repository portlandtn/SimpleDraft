package com.jedmay.simpledraft.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.jedmay.simpledraft.dao.OutputStateDao;
import com.jedmay.simpledraft.model.OutputState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {OutputState.class}, version = 1, exportSchema = false)
public abstract class SimpleDraftDb extends RoomDatabase {

    public abstract OutputStateDao outputStateDao();

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

              OutputStateDao outputStateDao = INSTANCE.outputStateDao();
              outputStateDao.deleteAll();

              OutputState state = new OutputState();
              state.setName("K19J0208");
              List<Double> list = new ArrayList<>();
              list.add(12.0204);
              list.add(-11.0800);
              list.add(13.0911);

              state.setValues(list);
              state.setAngle1(1.1935);
              state.setAngle2(4.7636);
              state.setAngle3(2.3859);
              state.setAngle4(7.2323);
              outputStateDao.insert(state);
          });
      }
    };
}
