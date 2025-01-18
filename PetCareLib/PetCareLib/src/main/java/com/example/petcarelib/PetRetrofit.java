package com.example.petcarelib;

import android.telecom.Call;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetRetrofit {
    private static final String BASE_URL = "https://pet-care-five-phi.vercel.app/";
    private static PetRetrofit instance = null;
    private PetAPI petCareApi;

    private PetRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        petCareApi = retrofit.create(PetAPI.class);
    }



    public static synchronized PetRetrofit getInstance() {
        if (instance == null) {
            instance = new PetRetrofit();
        }
        return instance;
    }

    public PetAPI getPetCareApi() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(PetAPI.class);
    }

}
