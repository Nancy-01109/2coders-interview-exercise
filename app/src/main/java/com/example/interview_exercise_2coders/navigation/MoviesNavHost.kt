package com.example.interview_exercise_2coders.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.interview_exercise_2coders.ui.routes.MovieDetailsRoute
import com.example.interview_exercise_2coders.ui.routes.MovieListRoute
import com.example.interview_exercise_2coders.view_model.MoviesViewModel

@Composable
fun MoviesNavHost(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    val projectsViewModel = hiltViewModel<MoviesViewModel>()

    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.MovieList.route,
        modifier = modifier
    ) {
        composable(NavigationRoutes.MovieList.route) {
            MovieListRoute(navController = navController, viewModel = projectsViewModel)
        }

        composable(
            route = NavigationRoutes.MovieDetail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailsRoute(movieId = movieId, viewModel = projectsViewModel)
        }
    }
}
