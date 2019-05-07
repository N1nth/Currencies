package com.n1nth.currencies.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.n1nth.currencies.Fragments.MainFragment;
import com.n1nth.currencies.R;


public class MainActivity extends AppCompatActivity {
    private MainFragment mMainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, mMainFragment)
                .commit();

    }

}
