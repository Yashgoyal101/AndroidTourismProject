package com.example.login_project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static android.content.ContentValues.TAG;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mauth;
    private EditText name, email, password ;
    private TextView go_to_login;
    private Button registerUser;
    //DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name = (EditText) findViewById(R.id.name_register);
        email = (EditText) findViewById(R.id.email_register);
        password = (EditText) findViewById(R.id.password_register);
        go_to_login = (TextView) findViewById(R.id.go_to_login);


        registerUser = (Button) findViewById(R.id.register_user);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        mauth = FirebaseAuth.getInstance();

        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,MainActivity.class );
                startActivity(intent);
            }
        });

    }

    private void register() {
        final String emailText = email.getText().toString().trim();
        final String nameText = name.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        if(nameText.isEmpty()){
            name.setError("Name is required");
            name.requestFocus();
            return;
        }
        if(emailText.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(passwordText.isEmpty()){
            password.setError("Password is required");
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


        mauth.createUserWithEmailAndPassword(emailText,passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"Hello Gupta " + nameText);
                            User user = new User(nameText,emailText);
                            Log.d(TAG,"Hello Mohil " + user.name);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this,"User has been registered successfully",Toast.LENGTH_LONG).show();

                                    }else{
                                        FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                        Toast.makeText(Register.this,"Failed to register" + e.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }else{
                            FirebaseAuthException e = (FirebaseAuthException )task.getException();
                            Toast.makeText(Register.this,"Failed to register task not successfull" + e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}