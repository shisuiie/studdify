package com.example.studify;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class CalendarFragment extends Fragment implements CalendarAdapter.onItemListener{

    public TextView monthYearText;
    public RecyclerView calendarRecyclerView;
    public static LocalDate selectedDate;
    private ListView eventListView;

    public static String formattedDate(LocalDate date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);



        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
        eventListView = view.findViewById(R.id.eventListView);

        selectedDate = LocalDate.now();
        setMonthView();

        Button button = view.findViewById(R.id.left_arrow);
        button.setOnClickListener(v -> {
            selectedDate = selectedDate.minusMonths(1);
            setMonthView();

        });

        Button button1 = view.findViewById(R.id.right_arrow);
        button1.setOnClickListener(v -> {
            selectedDate = selectedDate.plusMonths(1);
            setMonthView();
        });

        Button button2 = view.findViewById(R.id.newevent);
        button2.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), EventEditActivity.class);
            startActivity(intent);


                });


        return view;
    }



    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(requireActivity().getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);







    }




    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++ )
        {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }

        }

        return daysInMonthArray;
    }



    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }


    @Override
    public void onItemClick(int position, String dayText)

    {

    }

    @Override
    public void onResume() {

        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarFragment.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(requireContext().getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);

    }


}
