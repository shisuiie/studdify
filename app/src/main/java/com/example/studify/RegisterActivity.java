package com.example.studify;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {

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

    private void PerforAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmpassword = inputPassword2.getText().toString();

        if (!email.matches(emailPattern))

        {
            inputEmail.setError("Enter Valid Email address");

        }else if (password.isEmpty() || password.length()<6)
        {
            inputPassword.setError("Enter Password longer than 6 characters");

        }else if (!password.equals(confirmpassword))
        {
            inputPassword2.setError("Password Does not match feild");
        }else
        {
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
                }
                {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();


                }
            });
        }





    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}