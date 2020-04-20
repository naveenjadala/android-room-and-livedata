package com.example.csquaretest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.csquaretest.databinding.ActivityMainBinding;
import com.example.csquaretest.model.Respons;
import com.example.csquaretest.model.UserModel;
import com.example.csquaretest.service.RetrofitInstance;
import com.example.csquaretest.service.UserService;
import com.example.csquaretest.ui.UserListActivity;
import com.example.csquaretest.utils.UtilManager;


import br.com.ilhasoft.support.validation.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ProgressDialog progressDialog;
    Validator validator;

    public static Intent build(Activity activity) {
        return new Intent(activity, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        validator = new Validator(binding);

        binding.btnLogin.setOnClickListener(v -> {
            if (validator.validate()) {
                setProgressDialog();
                loginApi(binding.inputEmail.getText().toString(), binding.inputPassword.getText().toString());
            }
        });
    }

    void loginApi(String email, String password) {
        UserService userService = RetrofitInstance.getRetrofitInstance().create(UserService.class);
        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(password);
        userService.loginUser(userModel).enqueue(new Callback<Respons>() {
            @Override
            public void onResponse(Call<Respons> call, Response<Respons> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()) {
                    Intent intent = UserListActivity.build(MainActivity.this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    String str = UtilManager.showErrorMessage(response.errorBody(), MainActivity.this);
                    Toast.makeText(MainActivity.this, str , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Respons> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }
}
