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
    private List<Pet> pets = new ArrayList<>();

    public void setPets(List<Pet> pets) {
        this.pets = pets;
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
        holder.name.setText(pet.getName());
        holder.breed.setText(pet.getBreed());
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView name, breed;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pet_name);
            breed = itemView.findViewById(R.id.pet_breed);
        }
    }
}