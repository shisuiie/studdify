package com.example.studify;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.studify.databinding.ActivityHomeBinding;



public class HomeActivity extends AppCompatActivity {

    //
    ActivityHomeBinding binding;




      @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


         binding.BottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.study_set) {
                replaceFragment(new StudysetFragment());
            } else if (id == R.id.calendar){
                replaceFragment(new CalendarFragment());
            } else if (id == R.id.pomodoro_timer){
                replaceFragment(new PomodoroFragment());
            }else if (id == R.id.account){
                replaceFragment(new AccountFragment());
            }


            return true;




        });









    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }




}