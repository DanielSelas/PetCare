package com.example.petcare;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcarelib.Pet;
import com.example.petcarelib.PetRetrofit;
import com.example.petcarelib.PetAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddPetActivity extends AppCompatActivity {
    private EditText nameField, breedField, ageField, vaccinatedField, weightField, genderField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        // Initialize UI components
        nameField = findViewById(R.id.name_field);
        breedField = findViewById(R.id.breed_field);
        ageField = findViewById(R.id.age_field);
        vaccinatedField = findViewById(R.id.vaccinated_field);
        weightField = findViewById(R.id.weight_field);
        genderField = findViewById(R.id.gender_field);
        Button submitButton = findViewById(R.id.submit_button);

        // Add click listener for the submit button
        submitButton.setOnClickListener(v -> {
            if (validateFields()) {
                addPet();
            } else {
                Toast.makeText(this, "All fields must be filled correctly!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Validate all fields
    private boolean validateFields() {
        try {
            if (nameField.getText().toString().isEmpty() ||
                    breedField.getText().toString().isEmpty() ||
                    ageField.getText().toString().isEmpty() ||
                    vaccinatedField.getText().toString().isEmpty() ||
                    weightField.getText().toString().isEmpty() ||
                    genderField.getText().toString().isEmpty()) {
                return false;
            }

            // Check numeric inputs
            Integer.parseInt(ageField.getText().toString());
            Integer.parseInt(vaccinatedField.getText().toString());
            Double.parseDouble(weightField.getText().toString());
            return true;

        } catch (NumberFormatException e) {
            Log.e("AddPetActivity", "Invalid number format: " + e.getMessage());
            return false;
        }
    }

    // Add a new pet via API
    private void addPet() {
        String name = nameField.getText().toString();
        String breed = breedField.getText().toString();
        int age = Integer.parseInt(ageField.getText().toString());
        int vaccinated = Integer.parseInt(vaccinatedField.getText().toString());
        double weight = Double.parseDouble(weightField.getText().toString());
        String gender = genderField.getText().toString();

        // Create a Pet object
        Pet pet = new Pet(name, breed, age, vaccinated, weight, gender, true);

        // Get API instance
        PetAPI petAPI = PetRetrofit.getInstance().getPetCareApi();

        // Make API call to add the pet
        petAPI.addPet(pet).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Log the response or perform additional actions
                        String responseBody = response.body().string();
                        Log.d("AddPetActivity", "Response: " + responseBody);
                        Toast.makeText(AddPetActivity.this, "Pet added successfully!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e("AddPetActivity", "Error reading response body: " + e.getMessage());
                    } finally {
                        // Ensure the response body is closed
                        response.body().close();
                    }
                } else {
                    Toast.makeText(AddPetActivity.this, "Failed to add pet!", Toast.LENGTH_SHORT).show();
                    Log.e("AddPetActivity", "API Response Error: " + response.message());
                }
                finish(); // Close activity in all cases
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddPetActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("AddPetActivity", "API Call Failure: " + t.getMessage());
            }
        });
    }
}