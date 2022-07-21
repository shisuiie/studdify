package com.example.studify;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

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

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        button2=findViewById(R.id.button2);
        progressDialog=new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        btnGoogle=findViewById(R.id.button8);



        button2.setOnClickListener(v -> performAuth());




        textView3.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,RegisterActivity.class)));

        btnGoogle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GoogleAuthActivity.class);
            startActivity(intent);
        });



    }

    private void performAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (!email.matches(emailPattern))
        {
            inputEmail.setError("Enter a Valid Email Address");

        }if (password.length()<6) {
            inputPassword.setError("Enter Password longer than 6 characters");

        } if (password.isEmpty()){
            inputPassword.setError("Please enter password before continuing");
        } if (email.isEmpty()){
            inputEmail.setError("Please enter Email before continuing");
        } else  {
            progressDialog.setMessage("Confirming Login...");
            progressDialog.setTitle("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    sendUserToNextActivity();
                    Toast.makeText(MainActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                }else
                {

                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();


                }
            });
        }



    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}