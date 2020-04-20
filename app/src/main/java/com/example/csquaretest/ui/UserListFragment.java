package com.example.csquaretest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.csquaretest.R;
import com.example.csquaretest.databinding.UserListFragmentBinding;
import com.example.csquaretest.model.UserList;
import com.example.csquaretest.viewmodel.UserViewModel;

import java.util.List;

public class UserListFragment extends Fragment {

    private UserListFragmentBinding binding;
    private List<UserList> userLists;
    private UserViewModel userViewModel;
    private ListViewAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_list_fragment, container, false);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        listAdapter = new ListViewAdapter();

        UserViewModel.Factory factory = new UserViewModel.Factory(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(this, factory).get(UserViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        subscribeToModel(userViewModel);
        binding.recyclerView.setAdapter(listAdapter);

        listAdapter.setOnItemClickListener(userList -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), UserActivity.class);
            intent.putExtra("user", userList);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    private void subscribeToModel(final UserViewModel model) {
        model.getAllList().observe(getViewLifecycleOwner(), commentEntities -> {
            if (commentEntities != null) {
                listAdapter.setPost(commentEntities);
            }
        });
    }
}
