package com.example.gurmanlookreal.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gurmanlookapp.ui.MainViewModel
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.example.gurmanlookapp.R
import com.example.gurmanlookapp.Screen
import com.example.gurmanlookapp.model.Category
import com.example.gurmanlookapp.screensInBottom
import com.example.gurmanlookapp.ui.components.Home
import com.example.gurmanlookapp.ui.components.InfoView
import com.example.gurmanlookapp.ui.components.MealDetailView
import com.example.gurmanlookapp.ui.components.MealView
import com.example.gurmanlookapp.ui.components.RandomView
import com.example.gurmanlookapp.ui.theme.creamy_white
import com.example.gurmanlookapp.ui.theme.dark_green
import com.example.gurmanlookapp.ui.theme.hunter_green
import com.example.gurmanlookapp.ui.theme.soft_pale_red
import com.example.gurmanlookapp.ui.theme.soft_pale_red_darker




@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(modifier: Modifier = Modifier) {
  val scaffoldState: ScaffoldState = rememberScaffoldState()
  val viewModel: MainViewModel = viewModel()
  val controller: NavController = rememberNavController()
  val navBackStackEntry by controller.currentBackStackEntryAsState()
  val currentRoute = navBackStackEntry?.destination?.route
  val isSheetFullScreen by remember { mutableStateOf(false) }
  val modifier = if (isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()

  val currentScreen: Screen =  remember {
    viewModel.currentScreen.value
  }

  val title: MutableState<String> = remember {
    mutableStateOf(currentScreen.title)
  }

  val modalSheetState = rememberModalBottomSheetState(
    initialValue = ModalBottomSheetValue.Hidden,
    confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
  )

  val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp

  val bottomBar: @Composable () -> Unit = {
    if (currentScreen is Screen.BottomScreen.Home) {
      BottomNavigation(
        Modifier
          .wrapContentSize()
          .padding(bottom = 50.dp),
        backgroundColor = creamy_white
      ) {
        screensInBottom.forEach { item ->
          val tint = if (currentRoute == item.bRoute) hunter_green else dark_green
          BottomNavigationItem(
            selected = currentRoute == item.bRoute,
            onClick = {
              controller.navigate(item.bRoute)
              title.value = item.bTitle
            },
            icon = {
              Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.bTitle,
                tint = tint
              )
            },
            label = { Text(text = item.bTitle, color = tint) },
            selectedContentColor = soft_pale_red_darker,
            unselectedContentColor = soft_pale_red
          )
        }
      }
    }
  }

  ModalBottomSheetLayout(
    sheetState = modalSheetState,
    sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
    sheetContent = {
      MoreBottomSheet(modifier = modifier)
    }) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = {
            Image(
              painter = painterResource(id = R.drawable.photo_1717507044866),
              contentScale = ContentScale.FillWidth,
              contentDescription = "logo"
            )
          },
          backgroundColor = dark_green
        )
      },
      bottomBar = bottomBar,
      scaffoldState = scaffoldState,
    ) {
      Navigation(navController = controller, viewModel = viewModel, pd = it)
    }
  }


}

@Composable
fun MoreBottomSheet(modifier: Modifier) {
  Box(
    Modifier
      .fillMaxWidth()
      .height(300.dp)
      .background(color = soft_pale_red)
  ) {
    Column(
      modifier = Modifier.padding(16.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(modifier = Modifier.padding(16.dp)) {
        Icon(
          modifier = Modifier.padding(end = 8.dp),
          painter = painterResource(id = R.drawable.baseline_settings_24),
          contentDescription = "Settings",
          tint = soft_pale_red_darker
        )
        Text(text = "Settings", fontSize = 20.sp, color = creamy_white)
      }
      Row(modifier = Modifier.padding(16.dp)) {
        Icon(
          modifier = Modifier.padding(end = 8.dp),
          painter = painterResource(id = R.drawable.baseline_share_24),
          contentDescription = "Share",
          tint = soft_pale_red_darker
        )

        Row(modifier=Modifier.padding(end=8.dp)),
        painter= painterResource(id = (
                var id
                = R.drawablebaseline:share_24:))
        Text(text = "Share", fontSize = 20.sp, color = creamy_white)
      }
      Row(modifier = Modifier.padding(16.dp)) {
        Icon(
          modifier = Modifier.padding(end = 8.dp),
          painter = painterResource(id = R.drawable.baseline_help_24),
          contentDescription = "Help",
          tint = soft_pale_red_darker
        )

        tint = soft_pale_red_darker,
        Text(text = "Help", fontSize = 20.sp, color = creamy_white)
      }
    }
  }
}

@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd: PaddingValues) {
  val recipeViewModel: MainViewModel = viewModel()
  val viewState by viewModel.categoriesState
  val mealsState by viewModel.mealsState
  val mealDetailState by viewModel.mealDetailState
  val randomMealState by viewModel.randomMealState
  val randomDetailsState by viewModel.randomMealState
  val randomAccess by viewModel.randomMealState
  NavHost(
    navController = navController as NavHostController,
    startDestination = Screen.BottomScreen.Home.bRoute,
    modifier = Modifier.padding(pd),
  ) {
    composable(Screen.BottomScreen.Home.bRoute) {
      Home(
        viewState = viewState,
        navigateToMeal = { category ->
          navController.currentBackStackEntry?.savedStateHandle?.set("cat", category)
          navController.navigate(Screen.MealView.route)
          Log.d("Navigation", "Navigating to MealView with category: $category")
        },
        fetchMealsForCategory = recipeViewModel::fetchMealsForCategory
      )
    }
    composable(Screen.MealView.route) {
      val category = navController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat")
      category?.let { selectedCategory ->
        val meals = viewState.categoriesWithMeals.find { it.category == selectedCategory }?.meals.orEmpty()
        MealView(
          mealsState = mealsState,
          meals = meals,
          navigateToDetail = { idMeal ->
            navController.currentBackStackEntry?.savedStateHandle?.set("idMeal", idMeal)
            navController.navigate(Screen.MealDetailView.route)
            Log.d("Navigation1", "Navigating to MealDetailView with meal ID: $idMeal")
          },
          fetchMealDetails = { idMeal ->
            recipeViewModel.fetchMealDetails(idMeal)
            Log.d("Navigation2", "Fetching meal details for meal ID: $idMeal")
          }
        )
      }
    }
    composable(Screen.MealDetailView.route) {
      MealDetailView(
        mealDetailState = mealDetailState
      )
    }

    composable(Screen.BottomScreen.Random.bRoute) {
      RandomView(
        randomMealState = randomMealState,
        fetchRandomMeal = viewModel::fetchRandomMeal
      )
    }

    composable(Screen.BottomScreen.Info.bRoute) {
      InfoView()


      composable(Screen.BottomScreen.Info.bRoute) {
        MealDetailView()

      }
        composable(Screen.BottomScreen.Info.bRoute){
          MealDetailView(mealDetailState )
        }
      }
    }
  }
}

