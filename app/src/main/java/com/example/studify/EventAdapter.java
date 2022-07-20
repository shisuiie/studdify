package com.example.studify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(@NonNull Context context, List<Event> events) {
        super(context, 0, events);

    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){

        Event event = getItem(position);
         if(convertView == null)
             convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);
        TextView eventCellTv = convertView.findViewById(R.id.eventCellTV);

        String eventTitle = event.getTitle()+ " "+CalendarFragment.formattedDate(event.getDate());
        eventCellTv.setText(eventTitle);
        return convertView;

    }
}
