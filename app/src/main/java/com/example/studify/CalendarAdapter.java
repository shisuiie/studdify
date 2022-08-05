package com.example.studify;

import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {


    //ArrayList passing in LocalDate object
    private final ArrayList<LocalDate> days;
    private final onItemListener onItemListener;
//changing daysInMonth to just days as we only want to view a day and not a day of month
    public CalendarAdapter(ArrayList<LocalDate> days, CalendarAdapter.onItemListener onItemListener)
    // Inflate the layout for this fragment
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }


    @NonNull
    //Calendar ViewHolder holds the recycler of Calendar e.g 4 weeks in a month so it will show 4-5 rows of days from
    // 1 - 30 or 28 in that specific month calendar view
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)

    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (days.size() > 15) //month view
             layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        else//week view
            layoutParams.height = parent.getHeight();



        return new CalendarViewHolder( view, onItemListener, days);
    }

    //onBindViewHolder method is used so that instead of creating a new view for each new row, an old view is recycled and reused by
    //by binding new data
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        if (date == null)
            holder.dayOfMonth.setText("");
        else {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if (date.equals(CalendarUtilis.selectedDate))
                holder.parentView.setBackgroundColor(Color.LTGRAY);
        }

    }
//this method returns the size of the days that contains the items we want to display
    // it returns the number of items currently available in adapter
    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface onItemListener
    {


        //if condition statement for when user clicks on date it will send a toast message saying the date they have selected from the calendar
        void onItemClick(int position, LocalDate date);


    }
}
