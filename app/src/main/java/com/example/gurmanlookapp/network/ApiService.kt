package com.example.gurmanlookapp.network

import com.example.gurmanlookapp.model.CategoriesResponse
import com.example.gurmanlookapp.model.MealDetailsResponse
import com.example.gurmanlookapp.model.MealsResponse
import com.example.gurmanlookapp.model.RandomMealResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val recipeService = retrofit.create(ApiService::class.java)


interface ApiService{
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealsResponse

    @GET("lookup.php")
    suspend fun getMealDetailsById(@Query("i") idMeal:String): MealDetailsResponse

    @GET("random.php")
    suspend fun getRandomMeal(): RandomMealResponse
}