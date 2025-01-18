package com.example.petcarelib;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PetAPI {


    @GET("/get_all_pets")
    Call<List<Pet>> getAllPets();

    @GET("get_pet/{name}")
    Call<Pet> getPetByName(@Path("name") String name);

    @POST("/add_pet")
    Call<ResponseBody> addPet(@Body Pet pet);

    @PUT("/update_pet/{name}")
    Call<Void> updatePet(@Path("name") String name, @Body Pet pet);

    @DELETE("/delete_pet/{name}")
    Call<ResponseBody> deletePet(@Path("name") String petName);

    @GET("/pets_by_breed/{breed}")
    Call<List<Pet>> getPetsByBreed(@Path("breed") String breed);

    @GET("/pets_by_age/{age}")
    Call<List<Pet>> getPetsByAge(@Path("age") int age);

    @GET("/recently_vaccinated/{months}")
    Call<List<Pet>> getRecentlyVaccinated(@Path("months") int months);

    @GET("/pets_by_weight")
    Call<List<Pet>> getPetsByWeight(@Query("min") double min, @Query("max") double max);
}
