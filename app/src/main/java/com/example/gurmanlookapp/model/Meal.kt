package com.example.gurmanlookapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal (
    val idMeal: String,
    val strMeal : String,
    val strMealThumb : String,

): Parcelable


data class MealsResponse(val meals: List<Meal>)