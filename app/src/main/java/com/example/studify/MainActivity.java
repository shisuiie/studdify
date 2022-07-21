package com.example.studify;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Referencing objects from XML file activity

    private static final String TAG = "MainActivity";
    TextView textView3;
    EditText inputEmail,inputPassword;
    Button button2;
    String emailPattern = "[a-zA-Z0-9._]+0[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button btnGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView3=findViewById(R.id.textView3);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //initialising objects with their ID's
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        button2=findViewById(R.id.button2);
        progressDialog=new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        btnGoogle=findViewById(R.id.button8);

        //"Go" button to start the Authentication on validity of User info in Login page
        button2.setOnClickListener(v -> performAuth());

        //To direct new users to Sign up form "New user? Sign up"
        textView3.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,RegisterActivity.class)));

        //Google authentication starts when this button is clicked by user
        //The GoogleAuthActivity holds authentication process, if successful they move to the home fragment
        btnGoogle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GoogleAuthActivity.class);
            startActivity(intent);
        });



    }

    //Performing authentication for inputEmail and inputPassword, checking if user enters a credible fields

    private void performAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        //Error handling

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(MainActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            inputEmail.setError("Email Address is required");
            inputEmail.requestFocus();

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            Toast.makeText(MainActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
            inputEmail.setError("Valid email address is required");
            inputEmail.requestFocus();

        }else if (TextUtils.isEmpty(password)){

            Toast.makeText(MainActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
            inputPassword.setError("Password is required");
            inputPassword.requestFocus();
        } else   {
            progressDialog.setMessage("Confirming Login...");
            progressDialog.setTitle("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            //mAuth Function confirms the users information seeing if the inputEmail and inputPassword fields
            // can be identified in the Firebase database,if yes then sent to the next activity if no
            // they are required to fix the mistake they made or sign up in register activity

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    sendUserToNextActivity();
                    Toast.makeText(MainActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                }else {
                    try {

                        throw task.getException();

                    }catch (FirebaseAuthInvalidUserException e) {
                        Toast.makeText(MainActivity.this, "User does not exist.. Please register", Toast.LENGTH_LONG).show();
                        inputEmail.setError("Invalid email address");
                        inputEmail.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){

                        Toast.makeText(MainActivity.this, "Invalid Info. Please re-check Email and Password and try again.", Toast.LENGTH_LONG).show();
                        inputEmail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();



                }
            });
        }



    }




    //Sends user to Home Fragment inside HomeActivity

    private void sendUserToNextActivity() {
        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}