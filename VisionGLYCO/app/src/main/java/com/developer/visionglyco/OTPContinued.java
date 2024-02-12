package com.developer.visionglyco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTPContinued extends AppCompatActivity {
    String username, email, password, phone, fname, autoVerificationCode, code, phoneUid, emailUid;
    EditText edt_otp;
    Button btn_continued;
    ProgressBar progreeBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpcontinued);
        edt_otp = findViewById(R.id.edt_otp);
        btn_continued = findViewById(R.id.btn_continued);
        progreeBar = findViewById(R.id.progreeBar);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        fname = intent.getStringExtra("name");
        password = intent.getStringExtra("password");
        phone = intent.getStringExtra("phone");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+92"+ phone,
                60,
                TimeUnit.SECONDS,
                (Activity) OTPContinued.this,
                mCallbacks
        );   btn_continued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = edt_otp.getText().toString();
                verifyCode(code);
            }
        });
    } private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            autoVerificationCode = s;
            progreeBar.setVisibility(View.VISIBLE);
//            verifyCode(code);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                progreeBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPContinued.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            progreeBar.setVisibility(View.GONE);
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(autoVerificationCode, code);
        signInUserByCredentials(phoneAuthCredential);
    }

    private void signInUserByCredentials(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progreeBar.setVisibility(View.GONE);
                    phoneUid = firebaseAuth.getCurrentUser().getUid();
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("username", username);
                    userData.put("email", email);
                    userData.put("name", fname);
                    userData.put("password", password);
                    userData.put("phoneuid", phoneUid);
                    userData.put("phoneverification", "true");
                    userData.put("emailverification", "true");
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("userData").document(phoneUid).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(OTPContinued.this, "Phone Verified", Toast.LENGTH_SHORT).show();
                                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            emailUid = firebaseAuth.getCurrentUser().getUid();
                                            SharedPreferences sharedPreferences = getSharedPreferences("ids", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("phoneUid", phoneUid);
                                            editor.putString("emailUid", emailUid);
                                            editor.apply();

                                            Map<String, Object> emailId = new HashMap<>();
                                            emailId.put("emailuid", emailUid);
                                            db.collection("userData").document(phoneUid).update(emailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        // ID saved
                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(OTPContinued.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Toast.makeText(OTPContinued.this, "Email Verified", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(OTPContinued.this, "Data Saved", Toast.LENGTH_SHORT).show();
                                            Intent in = new Intent(getApplicationContext(), Success.class);
                                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(in);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(OTPContinued.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(OTPContinued.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OTPContinued.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}