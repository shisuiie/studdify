package com.example.studify;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;


public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    //Referencing objects from XML file activity
    public final ArrayList<LocalDate> days;
    public final View parentView;
    public final TextView dayOfMonth;
    private final CalendarAdapter.onItemListener onItemListener;



    public CalendarViewHolder( @NonNull View itemView, CalendarAdapter.onItemListener onItemListener, ArrayList<LocalDate> days) {
    //initialising objects with their corresponding ID's
        super(itemView);
        parentView = itemView.findViewById(R.id.parentView);
        dayOfMonth  = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        this.days = days;
    }
// onclick method to present message of user clicked date on calendar
    @Override
    public void onClick(View view)
    {
        onItemListener.onItemClick(getAdapterPosition(), days.get(getAdapterPosition()));

    }
}
