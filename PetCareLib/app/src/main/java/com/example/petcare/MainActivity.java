package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarelib.Pet;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private FetchAllActivity petRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        petAdapter = new PetAdapter();
        recyclerView.setAdapter(petAdapter);

        // Initialize Buttons and Actions
        setupButtons();
    }


    private void setupButtons() {

        // Button for Fetch All Pets
        Button fetchAllButton = findViewById(R.id.btn_fetch_all);
        fetchAllButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FetchAllActivity.class);
            startActivity(intent);
        });

        // Add Pet button
        Button addPetButton = findViewById(R.id.btn_add_pet);
        addPetButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddPetActivity.class);
            startActivity(intent);
        });

        // Search Pet button
        Button searchPetButton = findViewById(R.id.btn_search_pet);
        searchPetButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        // Update Pet button
        Button updatePetButton = findViewById(R.id.btn_update_pet);
        updatePetButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UpdatePetActivity.class);
            startActivity(intent);
        });

        // Delete Pet button
        Button deletePetButton = findViewById(R.id.btn_delete_pet);
        deletePetButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DeletePetActivity.class);
            startActivity(intent);
        });

        // Back Button
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        Button recentlyVaccinatedButton = findViewById(R.id.btn_recently_vaccinated);
        recentlyVaccinatedButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecentlyVaccinatedActivity.class);
            startActivity(intent);
        });
    }
}