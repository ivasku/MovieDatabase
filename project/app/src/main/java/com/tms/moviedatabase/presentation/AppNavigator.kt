
package com.tms.moviedatabase.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tms.moviedatabase.presentation.ui.HomeScreen
import com.tms.moviedatabase.presentation.ui.MovieDetailsScreen
import com.tms.moviedatabase.presentation.viewmodel.HomeViewModel
import com.tms.moviedatabase.presentation.viewmodel.MovieDetailsViewModel


@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(homeViewModel) { movieId ->
                navController.navigate("details/$movieId")
            }
        }
        composable(
            route = "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            val detailsViewModel: MovieDetailsViewModel = hiltViewModel()
            MovieDetailsScreen(detailsViewModel, movieId)
        }
    }
}

