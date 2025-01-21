package com.example.petcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up buttons
        Button btnFetchAll = findViewById(R.id.btn_fetch_all);
        Button btnSearchPet = findViewById(R.id.btn_search_pet);
        Button btnAddPet = findViewById(R.id.btn_add_pet);
        Button btnDeletePet = findViewById(R.id.btn_delete_pet);
        Button btnUpdatePet = findViewById(R.id.btn_update_pet);
        Button btnVaciPet = findViewById(R.id.btn_recently_vaccinated);


        // Set up listeners
        btnFetchAll.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FetchAllActivity.class);
            startActivity(intent);
        });

        btnSearchPet.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        btnAddPet.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddPetActivity.class);
            startActivity(intent);
        });

        btnDeletePet.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DeletePetActivity.class);
            startActivity(intent);
        });

        btnUpdatePet.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UpdatePetActivity.class);
            startActivity(intent);
        });

        btnVaciPet.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RecentlyVaccinatedActivity.class);
            startActivity(intent);
        });

    }
}