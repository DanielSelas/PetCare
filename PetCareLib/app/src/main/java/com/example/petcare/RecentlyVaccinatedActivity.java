package com.example.petcare;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class RecentlyVaccinatedActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_vaccinated);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        petAdapter = new PetAdapter();
        recyclerView.setAdapter(petAdapter);

        // Initialize input and button
        EditText monthsInput = findViewById(R.id.months_input);
        Button fetchButton = findViewById(R.id.fetch_vaccinated_button);
        Button backButton = findViewById(R.id.back_button);

        // Set up fetch button
        fetchButton.setOnClickListener(v -> {
            String monthsStr = monthsInput.getText().toString().trim();
            if (!monthsStr.isEmpty()) {
                int months = Integer.parseInt(monthsStr);
                fetchRecentlyVaccinatedPets(months);
            } else {
                Toast.makeText(RecentlyVaccinatedActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up back button
        backButton.setOnClickListener(v -> finish());
    }

    private void fetchRecentlyVaccinatedPets(int months) {
        PetAPI petAPI = PetRetrofit.getInstance().getPetCareApi();

        petAPI.getRecentlyVaccinated(months).enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pet> pets = response.body();
                    petAdapter.updatePets(pets);
                    Toast.makeText(RecentlyVaccinatedActivity.this, "Pets fetched successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RecentlyVaccinatedActivity.this, "No pets found vaccinated in the last " + months + " months.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Toast.makeText(RecentlyVaccinatedActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}