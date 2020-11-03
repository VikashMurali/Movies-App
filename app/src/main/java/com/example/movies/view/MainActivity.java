package com.example.movies.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.movies.viewmodel.MainViewModel;
import com.example.movies.R;
import com.example.movies.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding bind = DataBindingUtil.setContentView(this, R.layout.activity_main);

        bind.button.setOnClickListener((view -> {
        MainViewModel viewModel = new MainViewModel(this);
        bind.setViewModal(viewModel);
        this.getLifecycle().addObserver(viewModel);
        bind.button.setVisibility(View.GONE);
        }));
    }

}