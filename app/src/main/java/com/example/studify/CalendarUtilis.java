package com.example.studify;

import android.content.Intent;
import android.view.View;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtilis {

    public static LocalDate selectedDate;

    //monthYearFromDate method is being used to parsing dates in a format e.g "MMMM yyyy"
    // I want present on the screen using the DateTimeFormatter class
    public static String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
    //daysInMonthArray method
    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date)
    {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = CalendarUtilis.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++ )
        {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add(null);
            } else
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(),i - dayOfWeek));


        }

        return daysInMonthArray;
    }

    /**
     *
     * @param selectedDate
     * @return
     */
    //
    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate) {

        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);


        while (current.isBefore(endDate))
        {
            days.add(current);
            current = current.plusDays(1);
        }


        return days;
    }

    /**
     *
     * @param current
     * @return
     */
    //
    private static LocalDate sundayForDate(LocalDate current) {

        LocalDate oneWeekAgo = current.minusWeeks(1);

        while(current.isAfter(oneWeekAgo)){

            if (current.getDayOfWeek() == DayOfWeek.SUNDAY)
                return current;

            current = current.minusDays(1);
        }
        return  null;

    }


}
