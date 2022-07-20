package com.example.studify;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;


public class EventEditActivity extends AppCompatActivity {

    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private LocalDate time;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        //button
        Button button = findViewById(R.id.newevent);{

            String eventName = eventNameET.getText().toString();
            Event newEvent = new Event(eventName, time);
            Event.eventsList.add(newEvent);
            finish();

        }

        //other
        initWidget();
        time = LocalDate.now();
        eventDateTV.setText("Date: " + CalendarFragment.formattedDate(CalendarFragment.selectedDate));

    }

    private void initWidget() {


        eventTimeTV = findViewById(R.id.eventTitleTV);
        eventDateTV = findViewById(R.id.dateTitleTV);
        eventNameET = findViewById(R.id.titleNameEV);
    }


}