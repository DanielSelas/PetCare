package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarelib.Pet;
import com.example.petcarelib.PetRetrofit;
import com.example.petcarelib.PetAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        petAdapter = new PetAdapter();
        recyclerView.setAdapter(petAdapter);

        // Fetch all pets on activity start
        fetchAllPets();

        // Initialize Buttons and Actions
        setupButtons();


    }

    private void fetchAllPets() {
        PetAPI petAPI = PetRetrofit.getInstance().getPetCareApi();

        Log.d("MainActivity", "Starting fetchAllPets API call...");
        petAPI.getAllPets().enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pet> pets = response.body();
                    Log.d("MainActivity", "API call succeeded. Pets fetched: " + pets.size());
                    petAdapter.updatePets(pets);
                    Toast.makeText(MainActivity.this, "Pets fetched successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("MainActivity", "API response error: " + response.message());
                    Toast.makeText(MainActivity.this, "Failed to fetch pets!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Log.e("MainActivity", "API call failed: " + t.getMessage(), t);
                Toast.makeText(MainActivity.this, "Error fetching pets!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupButtons() {
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
    }
}