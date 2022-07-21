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

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_studyset, container, false);

        listView = view.findViewById(R.id.listView);
        Button button = view.findViewById(R.id.addtask_but);

        button.setOnClickListener(v -> addItem(view));

        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
        //this, android.R.layout.simple_list_item_1, items


        return view;
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener((parent, view, i, id) -> {
            Context context = requireContext().getApplicationContext();
            Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();

            items.remove(i);
            itemsAdapter.notifyDataSetChanged();
            return  true;
        });
    }

    private void addItem(View view) {
        EditText input = view.findViewById(R.id.add_task_ET);
        String itemText = input.getText().toString();


        if(!(itemText.equals(""))){
            itemsAdapter.add(itemText);
            input.setText("");
        }
        else{
            Context context = requireContext().getApplicationContext();
            Toast.makeText(context, "Please enter Task first", Toast.LENGTH_LONG).show();
        }
    }
}