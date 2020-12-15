package com.example.login_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;


public class MainActivity extends AppCompatActivity {

    private EditText email,password;

    private Button login;
    private TextView register;

    private FirebaseAuth mauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // register = (Button) findViewById(R.id.register);
        register = (TextView) findViewById(R.id.register);

        email= (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        mauth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }

        });

    }

    private void loginUser() {

        final String emailText = email.getText().toString().trim();
        final String passwordText = password.getText().toString().trim();

        if(emailText.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(passwordText.isEmpty()){
            password.setError("password is required");
            password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            email.setError("Please provide valid Email");
            email.requestFocus();

        }
        if(passwordText.length() < 6){
            password.setError("At least 6 character passwords is required");
            password.requestFocus();
            return;
        }


        mauth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user profile
                    Intent intent = new Intent(MainActivity.this,
                            Dashboard.class);
                    startActivity(intent);

                }else{
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Toast.makeText(MainActivity.this,"Failed to login" + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}