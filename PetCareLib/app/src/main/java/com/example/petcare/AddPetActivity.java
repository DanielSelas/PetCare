package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcarelib.Pet;
import com.example.petcarelib.PetAPI;
import com.example.petcarelib.PetRetrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPetActivity extends AppCompatActivity {

    private EditText nameField, breedField, ageField, vaccinatedField, weightField, genderField;
    private Button addPetButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        // Initialize views
        nameField = findViewById(R.id.pet_name_field);
        breedField = findViewById(R.id.pet_breed_field);
        ageField = findViewById(R.id.pet_age_field);
        vaccinatedField = findViewById(R.id.pet_vaccinated_field);
        weightField = findViewById(R.id.pet_weight_field);
        genderField = findViewById(R.id.pet_gender_field);
        addPetButton = findViewById(R.id.add_pet_button);
        backButton = findViewById(R.id.back_button);

        // Set up the Add Pet button
        addPetButton.setOnClickListener(v -> addPet());

        // Set up the Back button
        backButton.setOnClickListener(this::goBackToHome);
    }

    private void addPet() {
        String name = nameField.getText().toString();
        String breed = breedField.getText().toString();
        String ageText = ageField.getText().toString();
        String vaccinatedText = vaccinatedField.getText().toString();
        String weightText = weightField.getText().toString();
        String gender = genderField.getText().toString();

        // Validate fields
        if (name.isEmpty() || breed.isEmpty() || ageText.isEmpty() || vaccinatedText.isEmpty() || weightText.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageText);
        int vaccinated = Integer.parseInt(vaccinatedText);
        double weight = Double.parseDouble(weightText);

        // Create a Pet object
        Pet pet = new Pet(name, breed, age, vaccinated, weight, gender, true);

        // Get API instance
        PetAPI petAPI = PetRetrofit.getInstance().getPetCareApi();

        // Make API call to add the pet
        petAPI.addPet(pet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddPetActivity.this, "Pet added successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("AddPetActivity", "Pet added: " + pet.toString());
                    finish(); // Close activity
                } else {
                    Toast.makeText(AddPetActivity.this, "Failed to add pet!", Toast.LENGTH_SHORT).show();
                    Log.e("AddPetActivity", "API Response Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddPetActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AddPetActivity", "API Call Failure: " + t.getMessage());
            }
        });
    }

    public void goBackToHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}