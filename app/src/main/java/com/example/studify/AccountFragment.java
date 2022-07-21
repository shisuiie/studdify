package com.example.studify;



import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountFragment extends Fragment {

    //Referencing objects from XML file activity
    private Button logoutButton;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //initialising mAuth, "getinstance" returns instance of this class corresponding to the default FirebaseApp instance
        mAuth = FirebaseAuth.getInstance();

        //initialising log out button with corresponding id from xml layout
        logoutButton = view.findViewById(R.id.logoutbut);
        //creating method for logout button, when this is clicked the current user is signed out of account
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            //Method for sending user back to MainActivity or Log in page
            signOutUser();
        });



        return view;

    }

    //method for sending user back to MainActivity
    private void signOutUser() {

        Intent mainActivity = new Intent(AccountFragment.this.getActivity(), MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivity);
    }


}
