package com.example.missingmarksapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.missingmarksapp.Models.Marks;

import java.util.ArrayList;

public class MarksUnitsAdapter extends RecyclerView.Adapter<MarksUnitsAdapter.ViewHolder> {

    private final ArrayList<Marks> registeredunits;
    Context context;
    static OnUnitClicked onUnitClicked;


    public MarksUnitsAdapter(ArrayList<Marks> jobs, Context context, OnUnitClicked onMessageClicked) {
        this.registeredunits = jobs;
        this.context = context;
        MarksUnitsAdapter.onUnitClicked = onMessageClicked;
    }

    interface OnUnitClicked {
        void onUnitClicked(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.marks_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Marks model = registeredunits.get(position);
        holder.textView1.setText(model.getStudentName());
        holder.textView2.setText(model.getStudentReg());
        holder.textView3.setText(model.getYear());
        holder.textView4.setText(model.getMarks());

    }

    @Override
    public int getItemCount() {
        return registeredunits.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, textView4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUnitClicked.onUnitClicked(getAdapterPosition());

                }
            });
            textView1 = itemView.findViewById(R.id.line_a);
            textView2 = itemView.findViewById(R.id.line_b);
            textView3 = itemView.findViewById(R.id.line_c);
            textView4 = itemView.findViewById(R.id.line_d);

        }
    }

}




