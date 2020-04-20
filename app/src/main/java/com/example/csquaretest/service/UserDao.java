package com.example.csquaretest.service;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.csquaretest.model.UserList;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(UserList userList);

    @Query("SELECT * FROM UserList")
    LiveData<List<UserList>> getAll();
}
