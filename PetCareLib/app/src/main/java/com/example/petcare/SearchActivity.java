package com.example.petcare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcarelib.Pet;
import com.example.petcarelib.PetRetrofit;
import com.example.petcarelib.PetAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private EditText petNameField;
    private TextView searchResult;
    private Button searchButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pet);

        // Link UI components
        petNameField = findViewById(R.id.pet_name_field);
        searchResult = findViewById(R.id.search_result);
        searchButton = findViewById(R.id.search_button);
        backButton = findViewById(R.id.back_button);

        // Set up back button
        backButton.setOnClickListener(v -> finish());

        // Set up search button
        searchButton.setOnClickListener(v -> searchPet());
    }

    private void searchPet() {
        String petName = petNameField.getText().toString().trim();

        if (petName.isEmpty()) {
            Toast.makeText(this, "Please enter a pet name", Toast.LENGTH_SHORT).show();
            return;
        }

        PetAPI petAPI = PetRetrofit.getInstance().getPetCareApi();
        petAPI.getPetByName(petName).enqueue(new Callback<Pet>() {
            @Override
            public void onResponse(Call<Pet> call, Response<Pet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Pet pet = response.body();
                    searchResult.setText(pet.toString());
                    Toast.makeText(SearchActivity.this, "Pet found!", Toast.LENGTH_SHORT).show();
                } else {
                    searchResult.setText("No pet found.");
                    Toast.makeText(SearchActivity.this, "No pet found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pet> call, Throwable t) {
                searchResult.setText("Error: " + t.getMessage());
                Toast.makeText(SearchActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SearchActivity", "API Error", t);
            }
        });
    }
}