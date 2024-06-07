package com.example.gurmanlookapp

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.filled.Info


sealed class Screen (val title: String, val route: String) {

    sealed class BottomScreen(val bTitle: String, val bRoute: String, @DrawableRes val icon: Int) :
        Screen(bTitle, bRoute) {
        object Home : BottomScreen(
            "Home",
            "home",
            R.drawable.baseline_home_24
        )

        object Random : BottomScreen(
            "Random",
            "random",
            R.drawable.baseline_sync_24
        )

        object Info : BottomScreen(
            "Info",
            "info",
            R.drawable.baseline_info_24
        )
    }
    object MealView : Screen("Meal View", "mealview")
    object MealDetailView : Screen("Meal Detail View", "mealdetailview")
}

val screensInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Random,
    Screen.BottomScreen.Info
)