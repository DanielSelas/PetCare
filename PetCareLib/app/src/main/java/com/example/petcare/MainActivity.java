package com.example.petcare;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        petAdapter = new PetAdapter();
        recyclerView.setAdapter(petAdapter);

        fetchAllPets();

        findViewById(R.id.add_pet_button).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddPetActivity.class));
        });
    }

    private void fetchAllPets() {
        PetAPI petAPI = PetRetrofit.getInstance().getPetCareApi();
        petAPI.getAllPets().enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    petAdapter.setPets(response.body());
                } else {
                    Log.e("MainActivity", "Failed to fetch pets");
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });
    }
}