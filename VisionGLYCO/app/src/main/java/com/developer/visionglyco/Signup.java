package com.developer.visionglyco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Signup extends AppCompatActivity {
    EditText edt_username, edt_fname, edt_password, edt_confirm_password;
    Button btn_signup;
    String username, fname, password, confirmPassword;
    TextView visitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edt_username = findViewById(R.id.edt_username);
        edt_fname = findViewById(R.id.edt_fname);
        edt_password = findViewById(R.id.edt_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        visitLogin = findViewById(R.id.visitLogin);
        btn_signup = findViewById(R.id.btn_signup);
        visitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edt_username.getText().toString();
                fname = edt_fname.getText().toString();
                password = edt_password.getText().toString();
                confirmPassword = edt_confirm_password.getText().toString();
                if(!username.isEmpty() | !fname.isEmpty() | !password.isEmpty() | !confirmPassword.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(), OTPverification.class);
                    intent.putExtra("username", username);
                    intent.putExtra("name", fname);
                    intent.putExtra("password", password);
                    startActivity(intent);

                } else if (!password.equals(confirmPassword)) {
                    edt_password.setError("Not Matched");
                    edt_confirm_password.setError("Not Matched");
                } else {
                    edt_username.setError("Required");
                    edt_fname.setError("Required");
                    edt_password.setError("Required");
                    edt_confirm_password.setError("Required");
                }
            }
        });
    }
}