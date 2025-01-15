package com.example.petcarelib;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PetRetrofit {
    private static final String BASE_URL = "http://127.0.0.1:8088/";
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
        return petCareApi;
    }
}
