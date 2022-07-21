package com.example.studify;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        mButtonStartPause.setOnClickListener(v -> {
            if (mTimerRunning) {
                pauseTimer();
            }else{
                startTimer();
            }


        });

        mButtonReset.setOnClickListener(v -> resetTimer());

        updateCountDownText();
        mTimerRunning = false;



        return view;



    }

    private void setTime(long milliseconds){
        mStartTimeInMillis = milliseconds;
        resetTimer();

    }


    private void startTimer (){

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);

            }

        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("Pause");
        mButtonReset.setVisibility(View.INVISIBLE);

    }


    private void pauseTimer (){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);

    }

    private void resetTimer(){
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);

    }

    private void updateCountDownText(){
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) (mTimeLeftInMillis / 1000 % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

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