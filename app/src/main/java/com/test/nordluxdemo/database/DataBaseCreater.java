package com.test.nordluxdemo.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.test.nordluxdemo.database.entity.HomeLights;
import com.test.nordluxdemo.database.entity.Location;



@Database(entities = {HomeLights.class,Location.class},version = 1)
public class DataBaseCreater extends RoomDatabase {
    private static volatile DataBaseCreater INSTANCE;
    public static DataBaseCreater getDatabase(Context context){
        if (INSTANCE == null) {
            synchronized (DataBaseCreater.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataBaseCreater.class, "yankon_database").build();
                }
            }
        }
        return INSTANCE;
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
