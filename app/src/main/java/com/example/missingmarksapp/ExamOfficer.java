package com.example.missingmarksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.missingmarksapp.Models.Marks;
import com.example.missingmarksapp.databinding.ActivityExamOfficerBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExamOfficer extends AppCompatActivity {
    ActivityExamOfficerBinding binding;
    RecyclerView recyclerView;
    ArrayList<Marks> registeredunits;

    MarksUnitsAdapter.OnUnitClicked onUnitClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExamOfficerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onUnitClicked = new MarksUnitsAdapter.OnUnitClicked() {
            @Override
            public void onUnitClicked(int position) {
                startActivity(new Intent(ExamOfficer.this, MarksDetails.class)
                        .putExtra("studentName", registeredunits.get(position).getStudentName())
                        .putExtra("studentReg", registeredunits.get(position).getStudentReg())
                        .putExtra("year", registeredunits.get(position).getYear())
                        .putExtra("marks", registeredunits.get(position).getMarks()));
            }
        };

        recyclerView = binding.registeredRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registeredunits = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Marks");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                registeredunits.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.child("studentName").getValue(String.class);
                    String reg = ds.child("studentReg").getValue(String.class);
                    String year = ds.child("year").getValue(String.class);
                    String marks = ds.child("marks").getValue(String.class);

                    Marks model = new Marks(name, reg, year, marks);
                    registeredunits.add(model);
                    binding.registeredRecyclerView.setVisibility(View.VISIBLE);
                    binding.registeredProgress.setVisibility(View.GONE);
                }
                recyclerView.setAdapter(new MarksUnitsAdapter(registeredunits, ExamOfficer.this, onUnitClicked));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}