package com.jedmay.simpledraft.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.jedmay.simpledraft.dao.OutputStateDao;
import com.jedmay.simpledraft.model.OutputState;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = OutputState.class, version = 2, exportSchema = false)
public abstract class SimpleDraftDbBadCompany extends RoomDatabase {

    public abstract OutputStateDao outputStateDao();

    private static SimpleDraftDbBadCompany instance;
    private static final String DB_NAME = "SimpleDraftDbBadPractice";

    public static synchronized SimpleDraftDbBadCompany getDatabase(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SimpleDraftDbBadCompany.class,DB_NAME)
                    .fallbackToDestructiveMigrationFrom(1, 2)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


}
