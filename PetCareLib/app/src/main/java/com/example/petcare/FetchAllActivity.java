package com.example.petcare;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petcarelib.Pet;
import com.example.petcarelib.PetAPI;
import com.example.petcarelib.PetRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchAllActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        petAdapter = new PetAdapter();
        recyclerView.setAdapter(petAdapter);
        backButton = findViewById(R.id.back_button);

        fetchAllPets();

        backButton.setOnClickListener(v -> finish());

    }

    private void fetchAllPets() {
        PetAPI petAPI = PetRetrofit.getInstance().getPetCareApi();

        petAPI.getAllPets().enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pet> pets = response.body();
                    petAdapter.updatePets(pets);
                    Toast.makeText(FetchAllActivity.this, "Pets fetched successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FetchAllActivity.this, "Failed to fetch pets!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Toast.makeText(FetchAllActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("FetchAllActivity", "Error fetching pets: " + t.getMessage());
            }
        });
    }
}