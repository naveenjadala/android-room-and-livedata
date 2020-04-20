package com.example.csquaretest.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.csquaretest.R;
import com.example.csquaretest.databinding.UserFragmentBinding;
import com.example.csquaretest.model.UserList;

public class UserActivity extends AppCompatActivity {

    UserFragmentBinding binding;

    public static Intent build(Activity activity, UserList userList) {
        return new Intent(activity, UserActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_fragment);
        UserList user = (UserList) getIntent().getSerializableExtra("user");
        Glide.with(this).load(user.getAvatar()).into(binding.imageView);
        binding.setUser(user);

    }

}
