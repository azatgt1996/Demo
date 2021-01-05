package com.javamaster.demo.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.javamaster.demo.R;

public class UserInfoFragment extends Fragment {

    private UserInfoViewModel userInfoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user_info, container, false);

        final TextView txtView_login = root.findViewById(R.id.txtView_login);
        final TextView txtView_name = root.findViewById(R.id.txtView_name);
        final TextView txtView_email = root.findViewById(R.id.txtView_email);

        userInfoViewModel.getLogin().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txtView_login.setText(s);
            }
        });

        userInfoViewModel.getName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txtView_name.setText(s);
            }
        });

        userInfoViewModel.getEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txtView_email.setText(s);
            }
        });
        return root;
    }
}