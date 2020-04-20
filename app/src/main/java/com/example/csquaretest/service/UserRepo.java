package com.example.csquaretest.service;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.csquaretest.model.UserList;

import java.util.List;

public class UserRepo {

    private UserDao userDao;
    private LiveData<List<UserList>> listLiveData;

    public UserRepo(Application application) {
        AddDataBase db = AddDataBase.getAddDataBase(application);
        userDao = db.storeDao();
        listLiveData = userDao.getAll();
    }

    public LiveData<List<UserList>> getAllList() {
        return listLiveData;
    }

    public void insert (UserList userList) {
        new insertAsyncTask(userDao).execute(userList);
    }

    private static class insertAsyncTask extends AsyncTask<UserList, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(UserList... userLists) {
            mAsyncTaskDao.insert(userLists[0]);
            return null;
        }
    }
}
