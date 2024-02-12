package com.developer.visionglyco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPverification extends AppCompatActivity {
    String username, email, password, fname, phone, autoVerificationCode;
    EditText edt_email, edt_phone_number;
    Button continue_btn;
    ProgressBar progreeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        edt_email = findViewById(R.id.edt_email);
        edt_phone_number = findViewById(R.id.edt_phone_number);
        continue_btn = findViewById(R.id.continue_btn);
        progreeBar = findViewById(R.id.progreeBar);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        fname = intent.getStringExtra("name");
        password = intent.getStringExtra("password");

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = edt_phone_number.getText().toString();
                email = edt_email.getText().toString();
                if (!phone.isEmpty()) {

                    Intent intent = new Intent(getApplicationContext(), OTPContinued.class);
                    intent.putExtra("username", username);
                    intent.putExtra("name", fname);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("phone", phone);
                    startActivity(intent);

                } else {
                    edt_phone_number.setError("Required");
                }
            }
        });
    }

}