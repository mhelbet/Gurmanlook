package com.example.gurmanlookapp.ui

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.gurmanlookapp.Screen
import com.example.gurmanlookapp.model.Category
import com.example.gurmanlookapp.model.Meal
import com.example.gurmanlookapp.model.MealDetails
import com.example.gurmanlookapp.model.RandomDetails
import com.example.gurmanlookapp.network.recipeService
import kotlinx.coroutines.launch


class MainViewModel(application: Application): AndroidViewModel(application) {

    private val _categoryState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoryState

    private val _mealsState = mutableStateOf(MealState())
    val mealsState: State<MealState> = _mealsState

    private val _mealDetailState = mutableStateOf(MealDetailState())
    val mealDetailState: State<MealDetailState> = _mealDetailState

    private val _randomMealState = mutableStateOf(RandomMealState())
    val randomMealState: State<RandomMealState> = _randomMealState

    private val mealDetailsMap = mutableMapOf<String, List<MealDetails>>()

    init {
        fetchCategories()
        fetchRandomMeal()
    }

    private fun fetchCategories() {
        _categoryState.value = RecipeState(loading = true)
        viewModelScope.launch {
            try {
                val categoriesResponse = recipeService.getCategories()
                val categoriesWithMeals =
                    categoriesResponse.categories.sortedBy { it.strCategory }.map { category ->
                        CategoryWithMeals(category, emptyList())
                    }

                _categoryState.value = RecipeState(
                    loading = false,
                    categoriesWithMeals = categoriesWithMeals,
                    error = null
                )
            } catch (e: Exception) {
                _categoryState.value = RecipeState(
                    loading = false,
                    error = "Error fetching categories: ${e.message}"
                )
            }
        }
    }

    fun fetchMealsForCategory(category: Category) {
        _mealsState.value = MealState(loading = true)
        viewModelScope.launch {
            try {
                val mealsResponse = recipeService.getMealsByCategory(category.strCategory)
                val mealDetailsList = mealsResponse.meals.map { meal ->
                    val mealDetailsResponse = recipeService.getMealDetailsById(meal.idMeal)
                    mealDetailsResponse.meals
                }.flatten().sortedBy { it.strMeal }

                mealDetailsMap[category.strCategory] = mealDetailsList

                val updatedCategoriesWithMeals =
                    _categoryState.value.categoriesWithMeals.map { categoryWithMeals ->
                        if (categoryWithMeals.category == category) {
                            categoryWithMeals.copy(meals = mealDetailsList)
                        } else {
                            categoryWithMeals
                        }
                    }

                _categoryState.value = _categoryState.value.copy(
                    categoriesWithMeals = updatedCategoriesWithMeals
                )

                _mealsState.value = MealState(
                    loading = false,
                    meals = mealDetailsList.map { Meal(it.idMeal, it.strMeal, it.strMealThumb) },
                    error = null
                )

            } catch (e: Exception) {
                _categoryState.value = RecipeState(
                    loading = false,
                    error = "Error fetching meals for category: ${e.message}"
                )
            }
        }
    }

    fun fetchMealDetails(idMeal: String) {
        _mealDetailState.value = MealDetailState(loading = true)
        viewModelScope.launch {
            try {
                val mealDetailsResponse = recipeService.getMealDetailsById(idMeal)
                val mealDetails = mealDetailsResponse.meals
                if (mealDetails.isNotEmpty()) {
                    _mealDetailState.value = MealDetailState(
                        loading = false,
                        mealDetails = mealDetails,
                        error = null
                    )
                } else {
                    _mealDetailState.value = MealDetailState(
                        loading = false,
                        error = "Meal details not found"
                    )
                }
            } catch (e: Exception) {
                _mealDetailState.value = MealDetailState(
                    loading = false,
                    error = "Error fetching meal details: ${e.message}"
                )
            }
        }
    }

    fun fetchRandomMeal() {
        viewModelScope.launch {
            try {
                val randomMealResponse = recipeService.getRandomMeal()
                _randomMealState.value = RandomMealState(
                    loading = false,
                    randomMeal = randomMealResponse.meals,
                    error = null
                )
            } catch (e: Exception) {
                _randomMealState.value = RandomMealState(
                    loading = false,
                    error = "Error fetching random meal: ${e.message}"
                )
            }
        }
    }

    data class RecipeState(
        val loading: Boolean = true,
        val categoriesWithMeals: List<CategoryWithMeals> = emptyList(),
        val error: String? = null
    )

    data class MealState(
        val loading: Boolean = true,
        val meals: List<Meal> = emptyList(),
        val error: String? = null
    )

    data class MealDetailState(
        val loading: Boolean = true,
        val mealDetails: List<MealDetails> = emptyList(),
        val error: String? = null
    )

    data class CategoryWithMeals(
        val category: Category,
        val meals: List<MealDetails>
    )

    data class RandomMealState(
        val loading: Boolean = true,
        val randomMeal: List<RandomDetails> = emptyList(),
        val error: String? = null
    )


    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.BottomScreen.Home)

    val currentScreen: MutableState<Screen>
        get() = _currentScreen
}

