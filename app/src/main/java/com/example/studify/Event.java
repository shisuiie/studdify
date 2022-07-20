package com.example.studify;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event {


    public  static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date){

        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList){
            if (event.getDate().equals(date))
            events.add(event);
        }
        return events;
    }

    private final String title;
    private final LocalDate date;

    public Event(String title, LocalDate date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

}
