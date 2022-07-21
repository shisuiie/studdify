package com.example.studify;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    TextView textView4;
    EditText inputEmail,inputPassword,inputPassword2;
    Button button2;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textView4=findViewById(R.id.textView4);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputPassword2=findViewById(R.id.inputPassword2);
        button2=findViewById(R.id.button2);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();




        textView4.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this,MainActivity.class)));

        button2.setOnClickListener(v -> PerforAuth());
    }

    //Performing Authentication for inputEmail, inputPassword, and inputConfirmpassword

    private void PerforAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmpassword = inputPassword2.getText().toString();

        //Error handling

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(RegisterActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            inputEmail.setError("Email Address is required");
            inputEmail.requestFocus();

        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            Toast.makeText(RegisterActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
            inputEmail.setError("Valid email address is required");
            inputEmail.requestFocus();

        }else if (TextUtils.isEmpty(password)){

            Toast.makeText(RegisterActivity.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
            inputPassword.setError("Password is required");
            inputPassword.requestFocus();
        } else if (!confirmpassword.matches(password)){
            Toast.makeText(RegisterActivity.this, "Please re-enter your Password", Toast.LENGTH_SHORT).show();
            inputPassword2.setError("Please match password");
            inputPassword2.requestFocus();

        }


        else {
            progressDialog.setMessage("Confirming Registration...");
            progressDialog.setTitle("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                {
                    progressDialog.dismiss();
                    sendUserToNextActivity();
                    Toast.makeText(RegisterActivity.this, "Registration Succesful", Toast.LENGTH_SHORT).show();
                }else {
                    try {

                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e){

                        inputPassword.setError("This password is too weak.");
                        inputPassword.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        inputEmail.setError("This email is already in use. Please try a different email");
                        inputEmail.requestFocus();
                    }catch (FirebaseAuthUserCollisionException e){

                        inputEmail.setError("This email is already registered to an Account. Please try a different email");
                        inputEmail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this, "Login with new account", Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();


                }
            });
        }





    }



    private void sendUserToNextActivity() {

        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
        Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}