package com.jedmay.simpledraft.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.jedmay.simpledraft.dao.OutputStateDao;
import com.jedmay.simpledraft.model.OutputState;

@Database(entities = OutputState.class, version = 5, exportSchema = false)
public abstract class SimpleDraftDb extends RoomDatabase {

    public abstract OutputStateDao outputStateDao();

    private static SimpleDraftDb instance;
    private static final String DB_NAME = "SimpleDraftDb";

    public static synchronized SimpleDraftDb getDatabase(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SimpleDraftDb.class,DB_NAME)
                    .fallbackToDestructiveMigrationFrom(4, 5)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


}
