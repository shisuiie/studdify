package com.example.studify;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



public class HomeFragment extends Fragment {


    //Referencing objects from XML file activity
    ImageView nzqa, mrgs, nbt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        ////initialising objects with their corresponding ID's from XML file
        nzqa = view.findViewById(R.id.nzqa_tool);
        mrgs = view.findViewById(R.id.onlineres_tool);
        nbt = view.findViewById(R.id.imageView8);

        //setOnclickListener method, when this imageview is clicked the user is sent to the URL
        //gotoUrl method created to send user to URL
        nzqa.setOnClickListener(v -> gotoUrl("https://www.nzqa.govt.nz/ncea/subjects/"));

        mrgs.setOnClickListener(v -> gotoUrl("https://sites.google.com/mrgs.school.nz/mrgs-learning-hub/home"));

        nbt.setOnClickListener(v -> gotoUrl("http://www.nobraintoosmall.co.nz/index.html"));




        return view;
    }

    //method for gotoURL holds
    //Creates a Uri which parses the given encoded URI string.
    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));

    }
}
