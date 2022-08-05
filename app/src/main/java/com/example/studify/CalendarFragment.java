package com.example.studify;

import static com.example.studify.CalendarUtilis.daysInMonthArray;
import static com.example.studify.CalendarUtilis.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.strictmode.CredentialProtectedWhileLockedViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.studify.databinding.FragmentCalendarBinding;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class CalendarFragment extends Fragment implements CalendarAdapter.onItemListener {
    //Referencing objects from XML file activity
    public TextView monthYearText;
    public RecyclerView calendarRecyclerView;
    public Button btn;









    public static String formattedDate(LocalDate date) {
        //DatetimeFormatter class used for parsing dates in a specified format e.g "dd MM yyyy"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);


        //initialising objects with their ID's
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);


        CalendarUtilis.selectedDate = LocalDate.now();
        setMonthView();


        //changing view of calendar to previous month
        ImageButton imageButton = view.findViewById(R.id.left_arrow);
        imageButton.setOnClickListener(v -> {
            CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.minusMonths(1);
            setMonthView();

        });
        //changing view of calendar to next month
        ImageButton imageButton1 = view.findViewById(R.id.right_arrow);
        imageButton1.setOnClickListener(v -> {
            CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.plusMonths(1);
            setMonthView();
        });





        return view;
    }








    // Month View in a year, CalendarAdapter activity
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtilis.selectedDate));
        ArrayList<LocalDate> daysInMonth= daysInMonthArray(CalendarUtilis.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireActivity().getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }





//if condition statement for when user clicks on date it will send a toast message saying the date they have selected from the calendar

    @Override
    public void onItemClick(int position, LocalDate date) {

        if (date !=null)
        {

            CalendarUtilis.selectedDate = date;
            setMonthView();
        }


    }




}
