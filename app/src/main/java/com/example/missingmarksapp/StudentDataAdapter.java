package com.example.missingmarksapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.missingmarksapp.Models.StudentData;

import java.util.List;

public class StudentDataAdapter extends RecyclerView.Adapter<StudentDataAdapter.ViewHolder> {
    private List<StudentData> studentDataList;

    public StudentDataAdapter(List<StudentData> studentDataList) {
        this.studentDataList = studentDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentData studentData = studentDataList.get(position);
        holder.bind(studentData);
    }

    @Override
    public int getItemCount() {
        return studentDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewRegNumber;
        TextView textViewUnitName;
        TextView textViewUnitCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewRegNumber = itemView.findViewById(R.id.textViewRegNumber);
            textViewUnitName = itemView.findViewById(R.id.textViewUnitName);
            textViewUnitCode = itemView.findViewById(R.id.textViewUnitCode);
        }

        public void bind(StudentData studentData) {
            textViewName.setText("Name: " + studentData.getName());
            textViewRegNumber.setText("Reg Number: " + studentData.getRegNumber());
            textViewUnitName.setText("Unit Name: " + studentData.getUnitName());
            textViewUnitCode.setText("Unit Code: " + studentData.getUnitCode());
        }
    }
}
