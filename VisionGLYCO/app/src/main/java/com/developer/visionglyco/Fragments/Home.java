package com.developer.visionglyco.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.visionglyco.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends Fragment {
    LineChart lineChart;
    TextView username;
    String phoneUid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Home() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        username = view.findViewById(R.id.username);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ids", Context.MODE_PRIVATE);
        phoneUid = sharedPreferences.getString("phoneUid", "");
        DocumentReference docRef = db.collection("userData").document(phoneUid);

        // Fetch the document
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    username.setText(task.getResult().getString("username"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                username.setText("Welcome User");
                Toast.makeText(getContext(), "Failed to get name", Toast.LENGTH_SHORT).show();
            }
        });
        lineChart = view.findViewById(R.id.line_chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(9, 90)); // 9:00 AM, glucose level 90
        entries.add(new Entry(10, 95)); // 10:00 AM, glucose level 95
        entries.add(new Entry(11, 100)); // 11:00 AM, glucose level 100
        entries.add(new Entry(12, 105)); // 12:00 PM, glucose level 105
        entries.add(new Entry(13, 110)); // 1:00 PM, glucose level 110

        LineDataSet dataSet = new LineDataSet(entries, "Glucose Level");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.RED);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        lineChart.invalidate();
        return view;
    }
}