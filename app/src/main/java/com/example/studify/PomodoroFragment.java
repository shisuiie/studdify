package com.example.studify;


import android.app.Notification;
import android.content.Context;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class PomodoroFragment extends Fragment {

    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private Boolean mTimerRunning;
    private  long mStartTimeInMillis ;
    private long mTimeLeftInMillis = mStartTimeInMillis;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pomodoro, container, false);

        mEditTextInput = view.findViewById(R.id.edit_text_input);
        Button mButtonSet = view.findViewById(R.id.button_set);

        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);

        mButtonStartPause = view.findViewById(R.id.button_start_pause);
        mButtonReset = view.findViewById(R.id.button_reset);




        //setOnClickListener method, checking input for minutes entered in
        // EditText field/ validating the input and error handling with if statement

        mButtonSet.setOnClickListener(v -> {
            String input = mEditTextInput.getText().toString();
            if (input.length()==0){
                Context context = requireContext().getApplicationContext();
                Toast.makeText(context,"Feild can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            long millisInput = Long.parseLong(input) * 60000;
            if (millisInput == 0){
                Context context = requireContext().getApplicationContext();
                Toast.makeText(context,"Please enter a positive number", Toast.LENGTH_SHORT).show();
                return;

            }

            setTime(millisInput);
            mEditTextInput.setText("");

        });

        //Actions user can take if and after user presses the start timer button
        //pause or continue

        mButtonStartPause.setOnClickListener(v -> {
            if (mTimerRunning) {
                pauseTimer();
            }else{
                startTimer();
            }


        });


        //Action user can take if and after user presses the start timer button
        //---> Reset Timer, takes back to original number user set int the beginning

        mButtonReset.setOnClickListener(v -> resetTimer());

        updateCountDownText();

        mTimerRunning = false;





        return view;



    }

    //setTime method used to accepts a single parameter time which specifies the number of milliseconds.

    private void setTime(long milliseconds){
        mStartTimeInMillis = milliseconds;
        resetTimer();



    }



    //startTimer method used to start the timer initiated by the start button
    private void startTimer (){

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }
            //When paused, Start and Reset button become visible and Pause button is invisible as it is already clicked
            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
                mEditTextInput.setVisibility(View.VISIBLE);




            }



        }.start();

        mTimerRunning = true;
        //when Timer is started pause button is visible and reset button is invisible this
        // means it cannot be used unless the timer is paused
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.VISIBLE);
        mEditTextInput.setVisibility(View.INVISIBLE);



    }

    //pauseTimer method countdown timer is put on pause/or stops counting down
    //StartPause button displays "start" text so that when clicked it resumes from the time it was stopped
    //Reset button is Set visible so that it can be used to reset timer to initial starting number
    private void pauseTimer (){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);
        mEditTextInput.setVisibility(View.VISIBLE);

    }

    //resetTimer method is used to reset Timer to its initial digit
    //Reset button is invisible since it has already been clicked
    //StartPause button is set to visible, it can be used to start or pause the timer while it is running

    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
        mEditTextInput.setVisibility(View.VISIBLE);

    }
    //updateCountDownText method used to integrate seconds into minutes and minutes into hours, this is for the clock view
    private void updateCountDownText(){
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) (mTimeLeftInMillis / 1000 % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        //
        String timeLeftFormatted;
        if (hours > 0 ){

            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);

        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

}