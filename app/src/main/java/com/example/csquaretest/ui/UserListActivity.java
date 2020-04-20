package com.example.csquaretest.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.csquaretest.R;
import com.example.csquaretest.databinding.UserListActivityBinding;
import com.example.csquaretest.model.UserList;
import com.example.csquaretest.model.Users;
import com.example.csquaretest.service.RetrofitInstance;
import com.example.csquaretest.service.UserService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.csquaretest.service.AddDataBase.getAddDataBase;

public class UserListActivity extends AppCompatActivity {

    UserListActivityBinding binding;
    private ProgressDialog progressDialog;

    public static Intent build(Activity activity) {
        return new Intent(activity, UserListActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.user_list_activity);
        setProgressDialog();
        getUsers(1);

        Fragment newFragment = new UserListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, newFragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    void getUsers(int pageNumber) {
        UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
        userService.getUsers(pageNumber).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()) {
                    insert(UserListActivity.this, response.body().getData());
                }
                if(response.body().getTotal_pages() != response.body().getPage()) {
                    getUsers(response.body().getPage() + 1);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserListActivity.this, "something went wrong" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void insert(final Context context, List<UserList> usersList) {
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    for (int i=0;i<usersList.size();i++) {
                        UserList userList = usersList.get(i);
                        userList.setAvatar(userList.getAvatar());
                        userList.setEmail(userList.getEmail());
                        userList.setFirst_name(userList.getFirst_name());
                        userList.setLast_name(userList.getLast_name());
                        getAddDataBase(context).storeDao().insert(userList);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

}
