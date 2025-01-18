package com.example.petcare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcarelib.PetRetrofit;
import com.example.petcarelib.PetAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeletePetActivity extends AppCompatActivity {
    private EditText petNameField;
    private Button deleteButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_pet);

        // Bind UI components
        petNameField = findViewById(R.id.delete_name);
        deleteButton = findViewById(R.id.delete_button);
        backButton = findViewById(R.id.back_button);

        // Set delete button listener
        deleteButton.setOnClickListener(v -> deletePet());

        // Set back button listener
        backButton.setOnClickListener(v -> finish());
    }

    private void deletePet() {
        String petName = petNameField.getText().toString().trim();

        // Validation check for empty input
        if (petName.isEmpty()) {
            Toast.makeText(this, "Please enter a pet name to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        // Log the pet name being deleted (debugging aid)
        Log.d("DeletePetActivity", "Pet name to delete: " + petName);

        PetAPI petAPI = PetRetrofit.getInstance().getPetCareApi();

        petAPI.deletePet(petName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DeletePetActivity.this, "Pet deleted successfully!", Toast.LENGTH_SHORT).show();
                    Log.d("DeletePetActivity", "Deleted pet name: " + petName);
                    finish();
                } else {
                    Toast.makeText(DeletePetActivity.this, "Failed to delete pet. Pet not found!", Toast.LENGTH_SHORT).show();
                    Log.e("DeletePetActivity", "Failed to delete pet. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DeletePetActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("DeletePetActivity", "Error: " + t.getMessage());
            }
        });
    }
}


