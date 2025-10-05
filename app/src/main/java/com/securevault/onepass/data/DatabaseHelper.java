package com.securevault.onepass.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {PasswordItem.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase {
    private static final String DATABASE_NAME = "passwords_db";
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract PasswordDao passwordDao();
}