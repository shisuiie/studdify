package com.example.studify;

import static com.example.studify.CalendarUtilis.daysInMonthArray;
import static com.example.studify.CalendarUtilis.daysInWeekArray;
import static com.example.studify.CalendarUtilis.monthYearFromDate;
import static com.example.studify.CalendarUtilis.selectedDate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


public class WeeklyCalendarFragment extends Fragment implements CalendarAdapter.onItemListener{

    public TextView monthYearText;
    public RecyclerView calendarRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_weekly_calendar, container, false);


        setWeekView();
        initWidgets();




        ImageButton imageButton = view.findViewById(R.id.left_arrow);
        imageButton.setOnClickListener(v -> {
            CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.minusWeeks(1);
            setWeekView();

        });

        ImageButton imageButton1 = view.findViewById(R.id.right_arrow);
        imageButton1.setOnClickListener(v -> {
            CalendarUtilis.selectedDate = CalendarUtilis.selectedDate.plusWeeks(1);
            setWeekView();
        });






        return view;
    }

    private void initWidgets() {

        calendarRecyclerView = requireView().findViewById(R.id.calendarRecyclerView);
        monthYearText = requireView().findViewById(R.id.monthYearTV);


    }


    // change arraylist from string to localDate so selected date can have a different background color and its easier to
// pass that as a whole local date opposed to the dayOfMonth string
    //rename array to "arrays"
    private void setWeekView(){
        monthYearText.setText(monthYearFromDate(CalendarUtilis.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtilis.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireActivity().getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);



    }





    @Override
    public void onItemClick(int position, LocalDate date) {
        CalendarUtilis.selectedDate = date;
        setWeekView();




    }



    @Override
    public void onResume()
    {

        super.onResume();
    }
}