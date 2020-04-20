package com.example.csquaretest.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.csquaretest.R;
import com.example.csquaretest.model.UserList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Database(entities = {UserList.class}, version = 1, exportSchema = false)
public abstract class AddDataBase extends RoomDatabase {

    @SuppressLint("StaticFieldLeak")
    private static Context activity;
    private static final String DATABASE_NAME = "Store_Images";
    @SuppressLint("StaticFieldLeak")
    private static volatile AddDataBase INSTANCE;
    private static final Object LOCK = new Object();

    public abstract UserDao storeDao();

    public static AddDataBase getAddDataBase(Context context) {
        if(context != null) {
            activity = context.getApplicationContext();
            if(INSTANCE == null) {
                synchronized (LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AddDataBase.class, DATABASE_NAME)
                                .fallbackToDestructiveMigration()
//                                .addCallback(roomCallBack)
                                .build();
                    }
                }
            }
            return INSTANCE;
        } else {
            return null;
        }
    }

//    private static Callback roomCallBack = new Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new SetDataAsyncTask(INSTANCE).execute();
//        }
//    };
//
}
