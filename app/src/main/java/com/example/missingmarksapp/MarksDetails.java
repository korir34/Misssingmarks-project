package com.example.missingmarksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.missingmarksapp.databinding.ActivityMarksDetailsBinding;

public class MarksDetails extends AppCompatActivity {
    ActivityMarksDetailsBinding binding;
    String name,reg,year,marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityMarksDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        name=getIntent().getStringExtra("studentName");
        reg=getIntent().getStringExtra("studentReg");
        year=getIntent().getStringExtra("year");
        marks=getIntent().getStringExtra("marks");

        binding.tvStudentName.setText(name);
        binding.tvReg.setText(reg);
        binding.tvYear.setText(year);
        binding.tvMarks.setText(marks);


    }
}