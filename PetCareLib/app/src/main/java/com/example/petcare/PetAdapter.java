package com.example.petcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarelib.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {
    private List<Pet> pets;

    // Constructor to initialize the pet list
    // Empty constructor to initialize with an empty list
    public PetAdapter() {
        this.pets = new ArrayList<>();
    }

    // Constructor to initialize the pet list
    public PetAdapter(List<Pet> pets) {
        this.pets = pets != null ? pets : new ArrayList<>(); // Handle null safety
    }

    public void updatePets(List<Pet> newPets) {
        this.pets.clear();
        this.pets.addAll(newPets);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = pets.get(position);
        holder.nameTextView.setText(pet.getName());
        holder.breedTextView.setText(pet.getBreed());
    }

    @Override
    public int getItemCount() {
        return pets != null ? pets.size() : 0;
    }

    // ViewHolder class to hold references to the pet item views
    static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, breedTextView;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.pet_name);
            breedTextView = itemView.findViewById(R.id.pet_breed);
        }
    }
}