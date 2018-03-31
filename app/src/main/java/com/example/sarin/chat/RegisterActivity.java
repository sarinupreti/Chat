package com.example.sarin.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout usernameTextInputLayout;
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private Button registerButton;
    private FirebaseAuth mAuth;

    private ProgressDialog registerProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        usernameTextInputLayout = findViewById(R.id.usernameTxtInput);
        emailTextInputLayout = findViewById(R.id.emailTxtInput);
        passwordTextInputLayout = findViewById(R.id.passwordTxtInput);
        registerButton = findViewById(R.id.registerButton);

        registerProgressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameTextInputLayout.getEditText().getText().toString();
                String email = emailTextInputLayout.getEditText().getText().toString();
                String password = passwordTextInputLayout.getEditText().getText().toString();


                if (!TextUtils.isEmpty(username) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    registerProgressDialog.setTitle("Registering User");
                    registerProgressDialog.setMessage("Please wait while we create an ancount");
                    registerProgressDialog.setCanceledOnTouchOutside(false);
                    registerProgressDialog.show();

                }
                registerUser(username, email, password);
            }
        });
    }

    private void registerUser(String username, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    registerProgressDialog.dismiss();
                    Intent mainIntent =new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                } else {
                    registerProgressDialog.hide();
                    Toast.makeText(RegisterActivity.this, "Register ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}
