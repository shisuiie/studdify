package com.example.studify;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;



public class StudysetFragment extends Fragment {



    ///Referencing objects from XML file activity
    private ArrayList<String> goals;
    private ArrayAdapter<String> itemsAdap;
    private ListView listView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_studyset, container, false);

        //initialising objects with their ID's from XML file
        listView = view.findViewById(R.id.listView);
        Button button = view.findViewById(R.id.addtask_but);



        //setting button with setOnClicklistener() for the method of this view class,
        // add item Method "adds" text in EditText view to ListView
        button.setOnClickListener(v -> addItem(view));


        //Arraylist for items
        goals = new ArrayList<>();
        //ItemsAdapter + specifying items were passing in e.g Goals or To-do's
        itemsAdap = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, goals);
        //SetAdapter method on listview passing in adapter created - "itemsAdapter"
        listView.setAdapter(itemsAdap);
        //This method is created to DELETE items in listView
        setUpListViewListener();



        return view;
    }

    //Deleting task method with toast message confirming deleted task

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener((parent, view, i, id) -> {
            Context context = requireContext().getApplicationContext();
            Toast.makeText(context, "Item removed", Toast.LENGTH_LONG).show();

            goals.remove(i);
            itemsAdap.notifyDataSetChanged();
            return true;
        });
    }

    //Adding item method, error handling if no text is added

    private void addItem(View view) {
        EditText input = view.findViewById(R.id.todo_ET);
        String itemText = input.getText().toString();


        if(!(itemText.equals(""))){
            itemsAdap.add(itemText);
            input.setText("");
        }
        else{
            Context context = requireContext().getApplicationContext();
            Toast.makeText(context, "Please enter task first", Toast.LENGTH_LONG).show();
        }
    }
}