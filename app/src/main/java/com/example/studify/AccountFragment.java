package com.example.studify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class AccountFragment extends Fragment {
    private TextView name_textview;
    private TextView email_textview, password_textview, number_textview;
    private ImageView email_imageview, password_imageview, number_imageview;
    private String email, password;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // name_textview = findViewById(R.id.name_textview);



    }
}