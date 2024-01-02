package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// PharmacyAdapter.java
public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.ViewHolder> {

    private List<Pharmacy> pharmacyList;

    public void updateData(List<Pharmacy> pharmacyList) {
    }

    // Constructor, methods to update data, etc.

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewAddress;
        // Add more views as needed

        ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            // Initialize more views
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pharmacy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pharmacy pharmacy = pharmacyList.get(position);
        holder.textViewName.setText(pharmacy.getName());
        holder.textViewAddress.setText(pharmacy.getAddress());
        // Bind more data to views
    }

    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }
}
