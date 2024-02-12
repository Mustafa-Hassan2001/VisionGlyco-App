package com.developer.visionglyco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {
    EditText edt_username, edt_fname, edt_age, edt_address;
    RadioButton rb_male, rb_female, type1, type2;
    Button continue_btn;
    String uid, name, username, email, age, address, phoneUid;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private RadioGroup radioGroupGender;
    private RadioGroup radioGroupDiabetesType;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        edt_username = findViewById(R.id.edt_username);
        edt_fname = findViewById(R.id.edt_fname);
        edt_age = findViewById(R.id.edt_age);
        edt_address = findViewById(R.id.edt_address);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        type1 = findViewById(R.id.type1);
        type2 = findViewById(R.id.type2);
        radioGroupGender = findViewById(R.id.radioGroup);
        radioGroupDiabetesType = findViewById(R.id.diabetesTypes);
        continue_btn = findViewById(R.id.continue_btn);
        SharedPreferences sharedPreferences = getSharedPreferences("ids", Context.MODE_PRIVATE);
        phoneUid = sharedPreferences.getString("phoneUid", "");

        db.collection("userData").document(phoneUid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    edt_username.setText(task.getResult().getString("username"));
                    edt_fname.setText(task.getResult().getString("name"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                age = edt_age.getText().toString();
                address = edt_address.getText().toString();
                username = edt_username.getText().toString();
                name = edt_fname.getText().toString();
                int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                int selectedDiabetesTypeId = radioGroupDiabetesType.getCheckedRadioButtonId();

                if (selectedGenderId == -1 || selectedDiabetesTypeId == -1) {
                    Toast.makeText(MyProfile.this, "Please select gender and diabetes type", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton genderRadioButton = findViewById(selectedGenderId);
                RadioButton diabetesTypeRadioButton = findViewById(selectedDiabetesTypeId);

                String selectedGender = genderRadioButton.getText().toString();
                String selectedDiabetesType = diabetesTypeRadioButton.getText().toString();

                if (!age.isEmpty() | !address.isEmpty()){
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("username", username);
                    userData.put("name", name);
                    userData.put("age", age);
                    userData.put("address", address);
                    userData.put("gender", selectedGender);
                    userData.put("diabetiesType", selectedDiabetesType);
                    db.collection("userData").document(phoneUid).update(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MyProfile.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    edt_age.setError("Required");
                    edt_address.setError("Required");
                }
            }
        });

    }
}