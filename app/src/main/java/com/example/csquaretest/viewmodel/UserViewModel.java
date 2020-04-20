package com.example.csquaretest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.csquaretest.model.UserList;
import com.example.csquaretest.service.UserRepo;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepo userRepo;

    private LiveData<List<UserList>> allList;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepo = new UserRepo(application);
        allList = userRepo.getAllList();
    }

    public LiveData<List<UserList>> getAllList() { return allList; }

    public void insert(UserList userList) { userRepo.insert(userList); }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserViewModel(mApplication);
        }
    }
}
