package com.example.petcare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcarelib.Pet;
import com.example.petcarelib.PetRetrofit;
import com.example.petcarelib.PetAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePetActivity extends AppCompatActivity {
    private EditText nameField, breedField, ageField, vaccinatedField, weightField, genderField;
    private Button updateButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pet);

        nameField = findViewById(R.id.update_name);
        breedField = findViewById(R.id.update_breed);
        ageField = findViewById(R.id.update_age);
        vaccinatedField = findViewById(R.id.update_vaccinated);
        weightField = findViewById(R.id.update_weight);
        genderField = findViewById(R.id.update_gender);

        updateButton = findViewById(R.id.update_button);
        backButton = findViewById(R.id.back_button);

        updateButton.setOnClickListener(v -> updatePet());
        backButton.setOnClickListener(v -> finish()); // Navigate back to the previous activity
    }

    private void updatePet() {
        String name = nameField.getText().toString();
        String breed = breedField.getText().toString();
        String ageStr = ageField.getText().toString();
        String vaccinatedStr = vaccinatedField.getText().toString();
        String weightStr = weightField.getText().toString();
        String gender = genderField.getText().toString();

        if (name.isEmpty() || breed.isEmpty() || ageStr.isEmpty() || vaccinatedStr.isEmpty() || weightStr.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);
        int vaccinated = Integer.parseInt(vaccinatedStr);
        double weight = Double.parseDouble(weightStr);

        Pet updatedPet = new Pet(name, breed, age, vaccinated, weight, gender, true);

        PetAPI petAPI = PetRetrofit.getInstance().getPetCareApi();

        petAPI.updatePet(name, updatedPet).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdatePetActivity.this, "Pet updated successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("UpdatePetActivity", "Pet updated: " + updatedPet.toString());
                    finish(); // Close the activity after a successful update
                } else {
                    Toast.makeText(UpdatePetActivity.this, "Failed to update pet!", Toast.LENGTH_SHORT).show();
                    Log.e("UpdatePetActivity", "Update failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdatePetActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("UpdatePetActivity", "API Call Failure: " + t.getMessage());
            }
        });
    }
}